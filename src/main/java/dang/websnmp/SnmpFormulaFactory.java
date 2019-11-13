package dang.websnmp;

import openplatform.tools.*;
import openplatform.database.dbean.*;


/**
 * Class SnmpFormulaFactory
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class SnmpFormulaFactory extends SnmpFormulaParser
{
    protected static OrdHashtable hash = new OrdHashtable();
    public static int CACHEMAX = 1;

    
    private SnmpFormulaFactory(String formula)
    {
        super(formula);
    }



    /**
     * Run the factory. Must be called at start.
     */
    public static void instanciate()
    {
        CACHEMAX = 20;
        DBSnmpFormula[] snmpForm = DBSnmpFormula.load(null);
        if(snmpForm != null)
        {
            int len = snmpForm.length;
            for(int i=0; i<len; i++)
            {
                formulaParser(snmpForm[i].getFormula());
            }
        }
    }

    


    /**
     * @return a SnmpFormulaParser from cache or from instanciation
     */
    public static SnmpFormulaParser formulaParser(String formula)
    {
        if(formula==null) return null;

        formula = formula.trim();

        Object obj = hash.get(formula);
        if(obj != null)
        {
            Debug.println(null, Debug.DEBUG, "Returning SnmpFormulaParser from cache");
            return (SnmpFormulaParser)obj;
        }

        Debug.println(null, Debug.DEBUG, "Creating new SnmpFormulaParser");
        SnmpFormulaFactory sfp = new SnmpFormulaFactory(formula);
        addFormula(formula, sfp);
        return sfp;
    }



    private static void addFormula(String formula, SnmpFormulaParser parser)
    {
        if(!parser.checkSyntax()) return;

        while(hash.size() >= CACHEMAX) hash.remove(0);

        hash.put(formula, parser);
    }


}
