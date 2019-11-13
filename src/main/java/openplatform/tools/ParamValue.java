package openplatform.tools;

/**
 * ParamValue.java
 *
 *
 * Created: Thu Apr  7 18:21:47 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class ParamValue 
{
    private String param;
    private String value;

    
    public Object clone()
        throws CloneNotSupportedException
    {
        ParamValue pv = new ParamValue();
        pv.setParam(param);
        pv.setValue(value);
        return pv;
    }

    /**
     * Gets the value of param
     *
     * @return the value of param
     */
    public final String getParam()
    {
        return this.param;
    }

    /**
     * Sets the value of param
     *
     * @param argParam Value to assign to this.param
     */
    public final void setParam(final String argParam)
    {
        this.param = argParam;
    }

    /**
     * Gets the value of value
     *
     * @return the value of value
     */
    public final String getValue()
    {
        return this.value;
    }

    /**
     * Sets the value of value
     *
     * @param argValue Value to assign to this.value
     */
    public final void setValue(final String argValue)
    {
        this.value = argValue;
    }

    
}
