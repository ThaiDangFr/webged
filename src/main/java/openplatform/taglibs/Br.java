package openplatform.taglibs;

import openplatform.database.dbean.*;
import openplatform.tools.*;
import javax.servlet.jsp.*;
import java.util.*;

/**
 * Class Br
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Br extends OPTagSupport
{
    public void initVars() {}

    public int doStartTag() throws JspException
    {
        try
        {
            JspWriter out = getOut();
            String terminalType = getTerminalType();

            if(DBTerminal.NAVTYPE_WAP.equals(terminalType))
            {
                out.print("<br/>");
            }
            else
            {
                out.print("<br>");
            }
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            throw new JspTagException(e.getMessage());
        }

        return SKIP_BODY;
    }
}
