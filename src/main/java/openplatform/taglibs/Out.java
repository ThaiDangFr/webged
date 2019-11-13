package openplatform.taglibs;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import openplatform.session.*;
import openplatform.tools.*;
import java.util.*;
import openplatform.database.dbean.*;
import openplatform.http.NavigCodec;
import openplatform.regexp.*;
import java.text.*;

/**
 * Class Out
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Out extends OPTagSupport
{
    private String value;
    private String unit;
    private String specialtag;
    private ArrayList array;

    public final static String URL_STR = "([\\w\\W]*)\\[url=(.*)\\](.*)\\[/url\\]([\\w\\W]*)";



    public void initVars()
    {
        value = null;
        array = null;
    }

    public int doStartTag() throws JspException
    {
        try
        {
        	if("".equals(value)) value = null;
        	if("".equals(unit)) unit = null;
        	if("".equals(specialtag)) specialtag = null;
        	
        	
            StringBuffer sb = new StringBuffer();
            JspWriter out = getOut();
            String terminalType = getTerminalType();


            if(unit != null)
            {
                try
                {
                    DecimalFormat twoDigits = new DecimalFormat("0.00");
                    double d = Double.parseDouble(value);
                    if(d > 1000000000)
                    {
                        double newd = d/1000000000;
                        value = (twoDigits.format(newd, new StringBuffer(), new FieldPosition(0)).append(" G").append(unit)).toString();
                    }
                    else if(d > 1000000)
                    {
                        double newd = d/1000000;
                        value = (twoDigits.format(newd, new StringBuffer(), new FieldPosition(0)).append(" M").append(unit)).toString();
                    }
                    else if(d > 1000)
                    {
                        double newd = d/1000;
                        value = (twoDigits.format(newd, new StringBuffer(), new FieldPosition(0)).append(" K").append(unit)).toString();
                    }
                    else
                    {
                        value = value + " "+unit;
                    }
                }
                catch(Exception e)
                {
                    Debug.println(this, Debug.ERROR, e);
                }
            }
            

            if(array!=null)
            {
                int len = array.size();
                for(int i=0; i<len; i++)
                {
                    String text = StringFilter.replaceSpecialChars((String)array.get(i));

                    if(DBTerminal.NAVTYPE_PC.equals(terminalType))
                    {
                        sb.append("<p>").append(text).append("</p>");
                    }
                    else if(DBTerminal.NAVTYPE_WAP.equals(terminalType))
                    {
                        sb.append(text).append("<br/>");
                    }
                    else
                        sb.append(text).append("<br>");
                }

                //array.clear();
            }
            else if(value != null)
            {
                if("true".equalsIgnoreCase(specialtag))
                {
                    Regexp urlreg = new Regexp(URL_STR);

                    if(urlreg.matches(value))
                    {
                        StringArrayList sal = urlreg.getMatchingResults();
                        
                        sb.append(StringFilter.replaceSpecialChars(sal.getString(1)));
                        sb.append("<a href=\"").append(NavigCodec.code(sal.getString(2))).append("\">");
                        sb.append(sal.getString(3));
                        sb.append("</a>");
                        sb.append(StringFilter.replaceSpecialChars(sal.getString(4)));
                    }
                    else
                        sb.append(StringFilter.replaceSpecialChars(value));
                }
                else
                    sb.append(StringFilter.replaceSpecialChars(value));
            }

            out.print(sb.toString());
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            throw new JspTagException(e.getMessage());            
        }
        finally { initVars(); }

        return SKIP_BODY;
    }


    public String getValue()
    {
        return value;
    }

    public void setValue(String avalue)
    {
        value=avalue;
    }
    
    public ArrayList getArray()
    {
        return array;
    }

    public void setArray(ArrayList aarray)
    {
        array=aarray;
    }

    public String getSpecialtag() { return specialtag; }
    public void setSpecialtag(String s) { this.specialtag = s; }


    // GET / SET

    /**
     * Gets the value of unit
     *
     * @return the value of unit
     */
    public final String getUnit()
    {
        return this.unit;
    }

    /**
     * Sets the value of unit
     *
     * @param argUnit Value to assign to this.unit
     */
    public final void setUnit(final String argUnit)
    {
        this.unit = argUnit;
    }

}
