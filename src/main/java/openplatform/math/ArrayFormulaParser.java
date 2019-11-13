package openplatform.math;

import org.nfunk.jep.*;
import java.util.*;
import openplatform.tools.*;

/**
 * Class ArrayFormulaParser
 *
<pre>
        String f1 = "(RFC1213_IFINOCTETS_T1-RFC1213_IFINOCTETS_T0)*100/(8*RFC1213_IFSPEED_T0*deltatime)";

        ArrayFormulaParser fp = new ArrayFormulaParser(f1);

        System.out.println("========== Calculate array values ==========");
        fp.set("RFC1213_IFINOCTETS_T0", new DoubleArray(new double[]{3,2,1}));
        fp.set("deltatime",new DoubleArray(new double[]{1,2,3}));
        fp.set("RFC1213_IFSPEED_T0",new DoubleArray(new double[]{1,2,3}));
        fp.set("RFC1213_IFINOCTETS_T1",new DoubleArray(new double[]{1,2,3}));

        try
        {
            System.out.println("RESULT="+fp.popResult());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("ERROR ? (false) "+fp.hasError()+ " "+fp);


        System.out.println("========== Calculate array values with an array smaller ==========");
        fp.set("RFC1213_IFINOCTETS_T0", new DoubleArray(new double[]{3,2}));
        fp.set("deltatime",new DoubleArray(new double[]{1,2,3}));
        fp.set("RFC1213_IFSPEED_T0",new DoubleArray(new double[]{1,2,3}));
        fp.set("RFC1213_IFINOCTETS_T1",new DoubleArray(new double[]{1,2,3}));

        try
        {
            System.out.println("RESULT="+fp.popResult());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("ERROR ? (false) "+fp.hasError()+ " "+fp);


        System.out.println("========== Calculate array values with more arrays ==========");
        fp.set("RFC1213_IFINOCTETS_T0", new DoubleArray(new double[]{3,2,1,2}));
        fp.set("deltatime",new DoubleArray(new double[]{1,2,3,8}));
        fp.set("RFC1213_IFSPEED_T0",new DoubleArray(new double[]{1,2,3,43}));
        fp.set("RFC1213_IFINOCTETS_T1",new DoubleArray(new double[]{1,2,3,43}));

        try
        {
            System.out.println("RESULT="+fp.popResult());
            System.out.println("RESULT="+fp.popResult());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("ERROR ? (false) "+fp.hasError()+ " "+fp);
    }
</pre>
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class ArrayFormulaParser extends FormulaParser
{
    private HashMap map = new HashMap();
    private int size=0;

    public ArrayFormulaParser(String formula)
    {
        super(formula);
    }
    

    /**
     * set a an array for a variable
     */
    public void set(String variable, DoubleArray value)
    {
        if(variable!=null && value!=null)
        {
            map.put(variable, value);
            if(size==0 || value.size() < size)
                size = value.size();
        }
    }


    /**
     * Pop a result (be carefull, it will be empty if you recall just after)
     */
    public DoubleArray popResult() throws Exception
    {
        DoubleArray result = new DoubleArray();
        for(int i=0; i<size; i++)
        {
            Set variableSet = map.keySet();
            Iterator iter = variableSet.iterator();

            while(iter.hasNext())
            {
                Object obj = iter.next();
                if(obj!=null && obj instanceof String)
                {
                    String name = (String)obj;
                    DoubleArray da = (DoubleArray)map.get(name);
                    double value = da.getAt(i);

                    super.set(name, value);
                }
            }

            double dres = super.evaluate();
            result.add(dres);
        }

        size=0;

        return result;
    }


    /*
    public static void main(String[] args)
    {
        String f1 = "(RFC1213_IFINOCTETS_T1-RFC1213_IFINOCTETS_T0)*100/(8*RFC1213_IFSPEED_T0*deltatime)";

        ArrayFormulaParser fp = new ArrayFormulaParser(f1);

        System.out.println("========== Calculate array values ==========");
        fp.set("RFC1213_IFINOCTETS_T0", new DoubleArray(new double[]{3,2,1}));
        fp.set("deltatime",new DoubleArray(new double[]{1,2,3}));
        fp.set("RFC1213_IFSPEED_T0",new DoubleArray(new double[]{1,2,3}));
        fp.set("RFC1213_IFINOCTETS_T1",new DoubleArray(new double[]{1,2,3}));

        try
        {
            System.out.println("RESULT="+fp.popResult());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("ERROR ? (false) "+fp.hasError()+ " "+fp);


        System.out.println("========== Calculate array values with an array smaller ==========");
        fp.set("RFC1213_IFINOCTETS_T0", new DoubleArray(new double[]{3,2}));
        fp.set("deltatime",new DoubleArray(new double[]{1,2,3}));
        fp.set("RFC1213_IFSPEED_T0",new DoubleArray(new double[]{1,2,3}));
        fp.set("RFC1213_IFINOCTETS_T1",new DoubleArray(new double[]{1,2,3}));

        try
        {
            System.out.println("RESULT="+fp.popResult());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("ERROR ? (false) "+fp.hasError()+ " "+fp);


        System.out.println("========== Calculate array values with more arrays ==========");
        fp.set("RFC1213_IFINOCTETS_T0", new DoubleArray(new double[]{3,2,1,2}));
        fp.set("deltatime",new DoubleArray(new double[]{1,2,3,8}));
        fp.set("RFC1213_IFSPEED_T0",new DoubleArray(new double[]{1,2,3,43}));
        fp.set("RFC1213_IFINOCTETS_T1",new DoubleArray(new double[]{1,2,3,Double.NaN}));

        try
        {
            System.out.println("RESULT="+fp.popResult());
            System.out.println("RESULT="+fp.popResult());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("ERROR ? (false) "+fp.hasError()+ " "+fp);
    }
    */
}
