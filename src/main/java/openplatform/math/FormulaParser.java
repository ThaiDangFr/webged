package openplatform.math;

import org.nfunk.jep.*;
import java.util.*;
import openplatform.tools.*;


/**
 * Class FormulaParser
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class FormulaParser
{
    protected JEP parser;
    private StringArrayList sal;
    private boolean error = false;
    private String errorMsg = null;

    private final static String[] funct = new String[]
    {
        "+",
        "-",
        "*",
        "%",
        "/",
        "^",
        "sin(x)",
        "cos(x)",
        "tan(x)",
        "asin(x)",
        "acos(x)",
        "atan(x)",
        "sinh(x)",
        "cosh(x)",
        "tanh(x)",
        "asinh(x)",
        "acosh(x)",
        "atanh(x)",
        "ln(x)",
        "log(x)",
        "angle(x)",
        "abs(x)",
        "rand()",
        "mod(x)",
        "sqrt(x)",
        "sum(x)",
    };
    

    /**
     * @param formula the formula
     */
    public FormulaParser(String formula)
    {
        sal = new StringArrayList();

        parser = new JEP();
        parser.addStandardFunctions();
        //parser.addStandardConstants();
        parser.setAllowUndeclared(true);
        parser.setImplicitMul(false);
        parser.parseExpression(formula);
    }


    /**
     * @return if the parsing failed or not
     */
    public boolean hasError()
    {
        return parser.hasError() || error;
    }


    /**
     * set a variable value
     */
    public void set(String variable, double value)
    {
        if(variable!=null)
            parser.addVariable(variable, value);
    }



    /**
     * take the result
     */
    public double evaluate()
    {
        
//         double db = parser.getValue();

//         if(!Double.isNaN(db))
//         {
//             error = false;
//             errorMsg = null;
//             return db;
//         }
//         else
//         {
//             error = true;
//             errorMsg = "All the constants are not initialized";
//             throw new Exception(errorMsg);
//         }
        

        double db = parser.getValue();

        return db;
    }




    /**
     * return an array of all variables
     */
    public StringArrayList variableList()
    {
        sal.clear();

        SymbolTable st = parser.getSymbolTable();
        Enumeration enum0 = st.keys();

        while(enum0.hasMoreElements())
        {
            Object obj = enum0.nextElement();
            if(obj instanceof String)
                sal.addString((String)obj);
        }

        return sal;
    }


    /**
     * @return the state of the object (error message)
     */
    public String toString()
    {
        String msg = "";
        String errInf = parser.getErrorInfo();
        if(errInf!=null) msg+=errInf;
        if(errorMsg!=null) msg+=" "+errorMsg;

        return msg;
    }


    /**
     * @return a list of available functions
     */
    public static String[] getAvailableFunctions()
    {
        return funct;
    }


    /*
    public static void main(String[] args)
    {
        System.out.println("========== Calculate simple value ==========");
        String f1 = "(RFC1213_IFINOCTETS_T1-RFC1213_IFINOCTETS_T0)*100/(8*RFC1213_IFSPEED_T0*deltatime)";

        FormulaParser fp = new FormulaParser(f1);
        System.out.println("ERROR (false) ? "+fp.hasError());
        System.out.println(fp.variableList());
        try
        {
            System.out.println("RESULT="+fp.evaluate());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("ERROR (true) ? "+fp.hasError()+ " "+fp);

        fp.set("RFC1213_IFINOCTETS_T0", 3);
        fp.set("deltatime",5);
        fp.set("RFC1213_IFSPEED_T0",10);
        fp.set("RFC1213_IFINOCTETS_T1",2);

        try
        {
            System.out.println("RESULT="+fp.evaluate());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("ERROR (false) ? "+fp.hasError()+ " "+fp);
    }
    */
}
