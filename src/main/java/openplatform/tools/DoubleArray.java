package openplatform.tools;

import java.util.Vector;

/**
 * Class DoubleArray
 *
 * An array of Double
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class DoubleArray extends Vector
{

    public DoubleArray()
    {
        super();
    }


    public DoubleArray(Vector vect)
    {
        super(vect);
    }


    public DoubleArray(double[] array)
    {
        super();

        if(array!=null)
        {
            int len = array.length;
            for(int i=0; i<len; i++)
                add(array[i]);
        }
    }



    public void add(double d)
    {
        super.add(new Double(d));
    }

    
    public boolean contains(double d)
    {
        return super.contains(new Double(d));
    }

    
    public double getAt(int index)
    {
        Object obj = super.elementAt(index);

        if(obj==null) return Double.NaN;
        else return ((Double)obj).doubleValue();
    }


    public double removeAt(int index)
    {
        Object obj = super.remove(index);
        if(obj==null) return Double.NaN;
        else return ((Double)obj).doubleValue();
    }


    public int size()
    {
        return super.size();
    }

}
