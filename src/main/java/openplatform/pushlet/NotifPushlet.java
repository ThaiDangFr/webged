package openplatform.pushlet;

import openplatform.database.dbean.*;
import openplatform.tools.*;
import javax.servlet.jsp.JspWriter;
import java.util.*;

/**
 * Class NotifPushlet
 *
<pre>
1.
Put that javascript in the html code :
function rewrite(text) 
{
    if(text!=null)
        document.getElementById("status").innerHTML = text;

}

2.
Put that code in your html : &lt;span id="status"&gt;&lt;/span&gt;

3.
Then, you have to extends your bean with NotifPushlet.

4.
In the JSP, init your usebean with the method : initWriter(out).

5.
Finally, in the java bean, you can use the method printStatusMessage
</pre>
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public abstract class NotifPushlet
{
    protected JspWriter out = null;

    
    public void initWriter(JspWriter out)
    {
        setWriter(out);
    }


    public void setWriter(JspWriter out)
    {
        this.out = out;
    }

    public void printStatusMessage(String message)
    {
        try
        {
            if(out!=null)
            {
                // replace '
                message=message.replaceAll("'","\\\\'");

                // PUSHLET => change the message dynamically
                out.println("<script language=\"JavaScript\" type=\"text/JavaScript\">rewrite('"+message+"')</script>");
                //out.flush(); // important
            }
                
            Debug.println(this, Debug.DEBUG, "PUSHLET "+message);
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.DEBUG, e);
        }         
    }


    /*
     * @deprecated
     */
    protected void printStatusMessage(String message, JspWriter out)
    {
        initWriter(out);
        printStatusMessage(message);
    }

}
