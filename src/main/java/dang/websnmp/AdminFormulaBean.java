package dang.websnmp;

import openplatform.database.dbean.*;
import openplatform.pushlet.NotifPushlet;
import openplatform.snmp.SnmpJSPBeanBase;
import openplatform.tools.*;
import java.util.*;

import dang.cms.CmsLanguage;
import openplatform.database.SQLConstraint;
import openplatform.snmp.*;


/**
 * Class AdminFormulaBean
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class AdminFormulaBean
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    private DBSnmpFormula fmask = new DBSnmpFormula();
    private DBSnmpOID oidmask = new DBSnmpOID();

    private DBSnmpFormula f;

    private String formulaId;
    private String mibName;
    private String formula;
    private String name;
    private String description;
    private String legendMibName;
    private String legendOidName;
    
    private OrdHashMap mibNameOptions = new OrdHashMap();
    private ArrayList oidNameList = new ArrayList();
    private OrdHashMap legendOidNameOptions = new OrdHashMap();

    private ArrayList operators = new ArrayList();
    private SQLConstraint sqlconstr = new SQLConstraint();

    private String formulaError;
    
    private String[] formulaToDelete;

    public AdminFormulaBean()
    {
        String[] st = SnmpFormulaFactory.getConstAndFunct();
        if(st != null)
        {
            int len = st.length;
            for(int i=0; i<len; i++)
            {
                operators.add(st[i]);
            }
        }
    }


    // DO BEG
    public String doSave()
        throws Exception
    {
        if(legendMibName==null || legendOidName==null)
            throw new Exception(cmsLang.translate("error.empty")+" Legend");

        if(!isFormulaOK())
            throw new Exception(cmsLang.translate("syntax.error"));

        if(name == null || name.trim().equals(""))
        {
            throw new Exception(cmsLang.translate("error.empty.name"));
        }

        if(description == null || description.trim().equals(""))
        {
            throw new Exception(cmsLang.translate("error.empty")+" Description");
        }
        

        if(!isNewName())
        {
            if((formulaId == null)
               || (formulaId != null && !name.equals(f.getDisplayName())))
            {
                throw new Exception(cmsLang.translate("duplicate.value")+" Name");
            }
        }


        if(!isNewFormula())
        {
            if((formulaId == null)
               || (formulaId != null && !formula.equals(f.getFormula())))
            {
                throw new Exception(cmsLang.translate("duplicate.value")+" Formula");
            }
        }
        



        if(formulaId == null || f == null)
        {
            Debug.println(this, Debug.DEBUG, "Create a new formula");
            f = new DBSnmpFormula();
        }

        f.setDisplayName(name);
        f.setDescription(description);
        f.setFormula(formula);
        f.setLegend(legendMibName+"_"+legendOidName);
        // f.setLegendId(legendOID.getId());
        f.store();

        if(f.isInError())
        {
            throw new Exception(cmsLang.translate("duplicate.value")+" Name-Formula");
        }
        else
            return null;
    }


    public String doDeleteFormula()
        throws Exception
    {
        if(formulaToDelete == null)
        {
            throw new Exception(cmsLang.translate("no.item.selected"));
        }

        int len = formulaToDelete.length;
        for(int i=0; i<len; i++)
        {
            fmask.clear();
            fmask.setFormulaId(formulaToDelete[i]);
            DBSnmpFormula.delete(fmask);
        }

        if(len > 0) return cmsLang.translate("item.deleted");
        else return null;
    }
    // DO END







    private boolean isNewFormula()
    {
        if(formula == null) return false;

        sqlconstr.clear();
        sqlconstr.setCaseSensitive(true);
        fmask.clear();
        fmask.setFormula(formula);

        if(DBSnmpFormula.count(fmask, sqlconstr) == 0)
            return true;
        else
            return false;
    }


    private boolean isNewName()
    {
        if(name == null) return false;

        sqlconstr.clear();
        sqlconstr.setCaseSensitive(true);   
        fmask.clear();
        fmask.setDisplayName(name);

        if(DBSnmpFormula.count(fmask, sqlconstr) == 0)
            return true;
        else
            return false;
    }


    public ArrayList getOidNameList()
    {
        oidNameList.clear();
        if(mibName == null) return null;

        String[] oidnames = SnmpJSPBeanBase.listSensorName(mibName);
        if(oidnames != null)
        {
            int len = oidnames.length;
            for(int i=0; i<len; i++)
            {
                oidNameList.add(oidnames[i]);
            }
        }

        return oidNameList;
    }


    public OrdHashMap getLegendOidNameOptions()
    {
        legendOidNameOptions.clear();
        if(legendMibName == null) return null;

        String[] oidnames = SnmpJSPBeanBase.listSensorName(legendMibName);

        if(oidnames != null)
        {
            int len = oidnames.length;
            for(int i=0; i<len; i++)
            {
                legendOidNameOptions.put(oidnames[i],oidnames[i]);
            }
        }

        return legendOidNameOptions;
    }


    public DBSnmpFormula[] getAllFormula()
    {
        return DBSnmpFormula.load(null);
    }

    
    /**
     * When this method is called, all fields are initialized
     */
    public void setFormulaId(String formId)
    {
        Debug.println(this, Debug.DEBUG, "FormulaId="+formId);

        // <Init mibNameOptions>
        mibNameOptions.clear();
        String[] allmibs = SnmpJSPBeanBase.listMib();

        if(allmibs != null)
        {
            int len = allmibs.length;
            for(int i=0; i<len; i++)
            {
                // display - value
                mibNameOptions.put(allmibs[i], allmibs[i]);
            }
        }
        // </Init mibNameOptions>




        this.formulaId = formId;
        init();


        if(formId == null)
            return;
        

        fmask.clear();
        fmask.setFormulaId(formId);
        f = DBSnmpFormula.loadByKey(fmask);

        if(f == null)
            return;
        
        formula = f.getFormula();
        name = f.getDisplayName();
        description = f.getDescription();
        
        
        DBSnmpOID oid = SnmpUtils.parseSimpleFormula(f.getLegend());
        if(oid != null)
        {
            legendMibName = oid.getMibName();
            legendOidName = oid.getCaptorName();
        }
        else
        {
            legendMibName = null;
            legendOidName = null;
        }


    }


    public void setDescription(String adescription)
    {
        description=adescription;
        
        if(description != null) description = description.trim();
    }

    public void setName(String aname)
    {
        name=aname;

        if(name != null) name = name.trim();
    }

    public void setFormula(String aformula)
    {
        formula=aformula;

        if(formula != null) formula = formula.trim();
    }


    /** PRIVATE **/


    /**
     * Initialize the mibName and legendMibName default value
     */
    private void init()
    {
        if(mibName == null && legendMibName == null)
        {
            sqlconstr.clear();
            sqlconstr.setLimit(1);
            sqlconstr.setOrderBy(DBSnmpOID.MIBNAME);
            String[] res = DBSnmpOID.distinct(DBSnmpOID.MIBNAME, null, sqlconstr);
            if(res != null)
            {
                mibName = res[0];
                legendMibName = res[0];
            }
        }
    }


    private boolean isFormulaOK()
    {
        SnmpFormulaParser sfp = SnmpFormulaFactory.formulaParser(formula);
        boolean ok = sfp.checkSyntax();
        if(!ok) formulaError = sfp.getErrorString();

        return ok;
    }



    // GET / SET


    /**
     * Gets the value of fmask
     *
     * @return the value of fmask
     */
    public final DBSnmpFormula getFmask()
    {
        return this.fmask;
    }

    /**
     * Sets the value of fmask
     *
     * @param argFmask Value to assign to this.fmask
     */
    public final void setFmask(final DBSnmpFormula argFmask)
    {
        this.fmask = argFmask;
    }

    /**
     * Gets the value of oidmask
     *
     * @return the value of oidmask
     */
    public final DBSnmpOID getOidmask()
    {
        return this.oidmask;
    }

    /**
     * Sets the value of oidmask
     *
     * @param argOidmask Value to assign to this.oidmask
     */
    public final void setOidmask(final DBSnmpOID argOidmask)
    {
        this.oidmask = argOidmask;
    }

    /**
     * Gets the value of f
     *
     * @return the value of f
     */
    public final DBSnmpFormula getF()
    {
        return this.f;
    }

    /**
     * Sets the value of f
     *
     * @param argF Value to assign to this.f
     */
    public final void setF(final DBSnmpFormula argF)
    {
        this.f = argF;
    }

    /**
     * Gets the value of formulaId
     *
     * @return the value of formulaId
     */
    public final String getFormulaId()
    {
        return this.formulaId;
    }

    /**
     * Gets the value of mibName
     *
     * @return the value of mibName
     */
    public final String getMibName()
    {
        return this.mibName;
    }

    /**
     * Sets the value of mibName
     *
     * @param argMibName Value to assign to this.mibName
     */
    public final void setMibName(final String argMibName)
    {
        this.mibName = argMibName;
    }

    /**
     * Gets the value of formula
     *
     * @return the value of formula
     */
    public final String getFormula()
    {
        return this.formula;
    }

    /**
     * Gets the value of name
     *
     * @return the value of name
     */
    public final String getName()
    {
        return this.name;
    }

    /**
     * Gets the value of description
     *
     * @return the value of description
     */
    public final String getDescription()
    {
        return this.description;
    }

    /**
     * Gets the value of legendMibName
     *
     * @return the value of legendMibName
     */
    public final String getLegendMibName()
    {
        return this.legendMibName;
    }

    /**
     * Sets the value of legendMibName
     *
     * @param argLegendMibName Value to assign to this.legendMibName
     */
    public final void setLegendMibName(final String argLegendMibName)
    {
        this.legendMibName = argLegendMibName;
    }

    /**
     * Gets the value of legendOidName
     *
     * @return the value of legendOidName
     */
    public final String getLegendOidName()
    {
        return this.legendOidName;
    }

    /**
     * Sets the value of legendOidName
     *
     * @param argLegendOidName Value to assign to this.legendOidName
     */
    public final void setLegendOidName(final String argLegendOidName)
    {
        this.legendOidName = argLegendOidName;
    }

    /**
     * Gets the value of mibNameOptions
     *
     * @return the value of mibNameOptions
     */
    public final OrdHashMap getMibNameOptions()
    {
        return this.mibNameOptions;
    }

    /**
     * Sets the value of mibNameOptions
     *
     * @param argMibNameOptions Value to assign to this.mibNameOptions
     */
    public final void setMibNameOptions(final OrdHashMap argMibNameOptions)
    {
        this.mibNameOptions = argMibNameOptions;
    }

    /**
     * Sets the value of oidNameList
     *
     * @param argOidNameList Value to assign to this.oidNameList
     */
    public final void setOidNameList(final ArrayList argOidNameList)
    {
        this.oidNameList = argOidNameList;
    }

    /**
     * Sets the value of legendOidNameOptions
     *
     * @param argLegendOidNameOptions Value to assign to this.legendOidNameOptions
     */
    public final void setLegendOidNameOptions(final OrdHashMap argLegendOidNameOptions)
    {
        this.legendOidNameOptions = argLegendOidNameOptions;
    }

    /**
     * Gets the value of operators
     *
     * @return the value of operators
     */
    public final ArrayList getOperators()
    {
        return this.operators;
    }

    /**
     * Sets the value of operators
     *
     * @param argOperators Value to assign to this.operators
     */
    public final void setOperators(final ArrayList argOperators)
    {
        this.operators = argOperators;
    }

    /**
     * Gets the value of sqlconstr
     *
     * @return the value of sqlconstr
     */
    public final SQLConstraint getSqlconstr()
    {
        return this.sqlconstr;
    }

    /**
     * Sets the value of sqlconstr
     *
     * @param argSqlconstr Value to assign to this.sqlconstr
     */
    public final void setSqlconstr(final SQLConstraint argSqlconstr)
    {
        this.sqlconstr = argSqlconstr;
    }

    /**
     * Gets the value of formulaError
     *
     * @return the value of formulaError
     */
    public final String getFormulaError()
    {
        return this.formulaError;
    }

    /**
     * Sets the value of formulaError
     *
     * @param argFormulaError Value to assign to this.formulaError
     */
    public final void setFormulaError(final String argFormulaError)
    {
        this.formulaError = argFormulaError;
    }

    /**
     * Gets the value of formulaToDelete
     *
     * @return the value of formulaToDelete
     */
    public final String[] getFormulaToDelete()
    {
        return this.formulaToDelete;
    }

    /**
     * Sets the value of formulaToDelete
     *
     * @param argFormulaToDelete Value to assign to this.formulaToDelete
     */
    public final void setFormulaToDelete(final String[] argFormulaToDelete)
    {
        this.formulaToDelete = argFormulaToDelete;
    }

}
