package dang.websnmp;

import openplatform.database.dbean.*;

/**
 * SendAlertBean.java
 *
 * Internal class that is used to store an alert before batching
 *
 * Created: Tue Jun 28 10:17:16 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class SendAlertBean 
{
    private DBSnmpGraphicFormula graphicFormula;
    private double value;
    private long date;

    // GET / SET



    /**
     * Gets the value of graphicFormula
     *
     * @return the value of graphicFormula
     */
    public final DBSnmpGraphicFormula getGraphicFormula()
    {
        return this.graphicFormula;
    }

    /**
     * Sets the value of graphicFormula
     *
     * @param argGraphicFormula Value to assign to this.graphicFormula
     */
    public final void setGraphicFormula(final DBSnmpGraphicFormula argGraphicFormula)
    {
        this.graphicFormula = argGraphicFormula;
    }


    /**
     * Gets the value of value
     *
     * @return the value of value
     */
    public final double getValue()
    {
        return this.value;
    }

    /**
     * Sets the value of value
     *
     * @param argValue Value to assign to this.value
     */
    public final void setValue(final double argValue)
    {
        this.value = argValue;
    }

    /**
     * Gets the value of date
     *
     * @return the value of date
     */
    public final long getDate()
    {
        return this.date;
    }

    /**
     * Sets the value of date
     *
     * @param argDate Value to assign to this.date
     */
    public final void setDate(final long argDate)
    {
        this.date = argDate;
    }

    
}
