package dang.websnmp;

import openplatform.math.*;
import openplatform.tools.*;
import openplatform.snmp.*;
import java.util.ArrayList;
import openplatform.database.dbean.*;
import openplatform.database.dbeanext.*;
import openplatform.snmp.process.SnmpScheduler;
import java.util.Date;
import openplatform.regexp.*;

/**
 * Class SnmpFormulaParser
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
abstract public class SnmpFormulaParser
{   
    private static Regexp deltaPattern;
    private static Regexp mibCaptorPattern;
    private static Regexp mibCaptorTPattern;


    private ArrayFormulaParser parser;
    private StringArrayList oids = new StringArrayList();
    private ArrayList formulaVars = new ArrayList(); // A list of SnmpFormulaVars
    

    public static final double DELTATIME = (double)SnmpScheduler.FREQUENCE;

    private final static String[] constAndFunct;

    private boolean containsUnknownVars = false;
    private ErrorList errorList = new ErrorList();


    static
    {
        String[] inner = FormulaParser.getAvailableFunctions();
        constAndFunct = new String[inner.length+2];
        constAndFunct[0]="deltatime";
        constAndFunct[1]="delta(x)";
        System.arraycopy(inner, 0, constAndFunct, 2, inner.length);
    }



    static
    {
        String mibCaptor = "(\\w+)_(\\w+)";
        String mibCaptorT = mibCaptor + "_(T\\d)";
        String delta = "delta\\("+mibCaptor+"\\)";

        mibCaptorPattern = new Regexp(mibCaptor);
        mibCaptorTPattern = new Regexp(mibCaptorT);
        deltaPattern = new Regexp(delta);
    }







    /**
     * Constructor
     */
    public SnmpFormulaParser(String formula)
    {
        String newformula = formula;

        // 1. Uncollapse the function
        while(deltaPattern.hasMoreContains(formula))
        {
            Debug.println(this, Debug.DEBUG, "loop deltaPattern");
            StringArrayList result = deltaPattern.getMatchingResults();
            int nb = result.size();
            if(nb==3)
            {
                String func = result.getString(0);
                String mibname = result.getString(1);
                String captor = result.getString(2);
                String newfunc = mibname+"_"+captor+"_T1-"+mibname+"_"+captor+"_T0";
                
                newformula = StringUtils.replaceAll(newformula,func,newfunc);
            }
        }


        // 2. Clean
        while(mibCaptorPattern.hasMoreContains(newformula))
        {
            StringArrayList result = mibCaptorPattern.getMatchingResults();
            int nb = result.size();
            if(nb==3)
            {
                String mibcapt = result.getString(0);
                String newmibcapt;
                if(!mibcapt.endsWith("T0") && !mibcapt.endsWith("T1"))
                {
                    newmibcapt=mibcapt+"_T0";
                    newformula = specialReplaceAll(newformula,mibcapt,newmibcapt);
                }
            }

        }

        Debug.println(this, Debug.DEBUG, formula+"=>"+newformula);

        parser = new ArrayFormulaParser(newformula);


        // 3. Set constants
        parser.set("deltatime",DELTATIME);


        // 4. Extract variables
        StringArrayList allvars = parser.variableList();
        int len = allvars.size();
        for(int i=0; i<len; i++)
        {
            String var = allvars.getString(i);

            Debug.println(this, Debug.DEBUG, "Will analyze var:"+var);
            if("deltatime".equals(var)) continue;

            if(mibCaptorTPattern.matches(var))
            {
                StringArrayList result = mibCaptorTPattern.getMatchingResults();
                int nb = result.size();

                if(nb==4)
                {
                    String mibname = result.getString(1);
                    String sensor = result.getString(2);
                    String t = result.getString(3);
                    String oid = SnmpJSPBeanBase.getOid(mibname,sensor);

                    if(oid == null)
                    {
                        // The formula contains unknown variables
                        containsUnknownVars = true;
                        errorList.add(mibname+"_"+sensor+" has incorrect syntax");
                        Debug.println(this, Debug.WARNING, var + " is NOK");
                        break;
                    }

                    if(!oids.contains(oid))
                        oids.addString(oid);

                    SnmpFormulaVars fvar = new SnmpFormulaVars();
                    fvar.setMibname(mibname);
                    fvar.setSensorname(sensor);
                    if(t!=null && t.equals("T1")) fvar.setT1(true);
                    fvar.setOid(oid);
                        
                    formulaVars.add(fvar);

                    Debug.println(this, Debug.DEBUG, var + " is OK");
                }
            }
            else
            {
                // The formula contains unknown variables
                containsUnknownVars = true;
                errorList.add(var+" is not a valid variable");
                Debug.println(this, Debug.WARNING, var + " is NOK");
                break;
            }
        }

    }


    /**
     * Tell if the syntax of the formula is correct
     */
    public boolean checkSyntax()
    {
        if(deltaPattern==null || mibCaptorPattern==null 
           || mibCaptorTPattern==null) return false;

        if(containsUnknownVars) return false;

        return !parser.hasError();
    }


    public String getErrorString()
    {
        String st = errorList.toString();
        if(parser.hasError())
        {
            if(errorList.size() != 0) st += ", ";
            st += "mathematical syntax error";
        }

        return st;
    }


    /**
     * Calculate with the formula given the DBSnmpHostId, DBSnmpGraphicFormulaId and the begin date
     * @return lastupdate date
     */
    public String calculate(String hostid, String gformulaid, long begindate)
    {
        if(hostid==null || gformulaid==null) return null;

        int len = formulaVars.size();
        int min = 0;
        String lastupdate = null;


        long realBeginDate = begindate;

        // 1. Load the snmpValues
        for(int i=0; i<len; i++)
        {
            Object obj = formulaVars.get(i);
            
            if(obj==null) continue;

            SnmpFormulaVars sfv = (SnmpFormulaVars)obj;
            
            sfv.loadSnmpValues(hostid, begindate);

            if(min==0 || sfv.length() < min)
                min = sfv.length();
            
            // Take the max for the real begin date
            // The pb is that begindate can be too low compared to the reality !
            if(sfv.getBeginDate() > realBeginDate)
                realBeginDate = sfv.getBeginDate();
        }


        // 2. Calculate and store
        for(int i=0; i< min; i++) // number of snmpvalues
        {
            for(int j=0; j<len; j++) // loop on variables
            {
                Object obj = formulaVars.get(j);
                
                if(obj==null) continue;

                SnmpFormulaVars sfv = (SnmpFormulaVars)obj;
                Debug.println(this, Debug.DEBUG, "formulavars "+sfv.toString()+" len="+sfv.length());
                parser.set(sfv.toString(), sfv.next());
            }

            try
            {
                DoubleArray da = parser.popResult();
                int inbofvalues = da.size();
                //String date = Long.toString(realBeginDate+i*SnmpScheduler.FREQUENCE*1000);
                String date = SQLTime.getSQLTime(realBeginDate+i*SnmpScheduler.FREQUENCE*1000);

                DBSnmpGFValue gfvalue = new DBSnmpGFValue();
                gfvalue.setGFormulaId(gformulaid);
                gfvalue.setDateTime(date);
                gfvalue.store();

                DBSnmpGFValueData gfvdata = new DBSnmpGFValueData();
                for(int k=0; k<inbofvalues; k++)
                {
                    double doub = da.getAt(k);
                    if(!Double.isNaN(doub) && !Double.isInfinite(doub))
                    {
                        gfvdata.setGfValueId(gfvalue.getGfValueId());
                        gfvdata.setValue(Double.toString(doub));
                        gfvdata.store();
                        gfvdata.clear();
                    }
                }

                if(Debug.DEBUGON)
                {
                    StringBuffer sbdate = ConstantTime.dateFormat.format(new Date(SQLTime.getJavaTime(date)), new StringBuffer(), ConstantTime.fieldpos0);
                    Debug.println(this, Debug.DEBUG, "Calculation done for index "+i+"/"+(min-1)+" date="+sbdate);
                }

                if(i==(min-1)) lastupdate=date;
            }
            catch(Exception e)
            {
                Debug.println(this, Debug.ERROR, e);
            }
        }

        
        
        // 3. Unload to free memories
        for(int i=0; i<len; i++)
        {
            Object obj = formulaVars.get(i);
            
            if(obj==null) continue;

            SnmpFormulaVars sfv = (SnmpFormulaVars)obj;
            
            sfv.unloadSnmpValues();
        }

        return lastupdate;
    }


    /**
     * @return an array of needed OID
     */
    public StringArrayList parseOID()
    {
        return oids;
    }


    /**
     * @return a list of useable constants and functions
     */
    public static String[] getConstAndFunct()
    {
        return constAndFunct;
    }


    /** PRIVATE CALLS **/

    
    /**
     * Replace pattern only if they don't ends with _T0 or _T1
     */
    private String specialReplaceAll(String initial, String oldst, String newst)
    {
        if(oldst==null || newst==null) return initial;
        
        if(oldst.equals(newst)) return initial;

        String newstring = initial;
        
        int offset = 0;
        int idx;

        while((idx=newstring.indexOf(oldst, offset)) != -1)
        {
            if((idx+oldst.length()) < (newstring.length())
               && newstring.charAt(idx+oldst.length()) == '_')
            {
                offset = idx + oldst.length();
            }
            else
            {
                newstring = newstring.substring(0, idx) + newst + newstring.substring(idx+oldst.length());
                offset = idx+newst.length();
            }
        }

        return newstring;
    }
}
