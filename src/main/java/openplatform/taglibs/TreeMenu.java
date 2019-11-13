package openplatform.taglibs;

import openplatform.database.dbean.*;
import openplatform.tools.*;
import javax.servlet.jsp.*;

/**
 * Class TreeMenu
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class TreeMenu extends OPTagSupport
{
    private String menuId;
    private DBTreeMenu tmask = new DBTreeMenu();

    public String getMenuId() { return menuId; }
    public void setMenuId(String menuId) { this.menuId = menuId; }
    
    public void initVars()
    {
        menuId = null;
    }

    public int doStartTag() throws JspException
    {
        try
        {
        	if("".equals(menuId)) menuId = null;

        	
            JspWriter out = getOut();
            String terminalType = getTerminalType();
            StringBuffer sb = new StringBuffer();

            if(DBTerminal.NAVTYPE_PC.equals(terminalType))
            {
                sb.append(toPC());
            }
            else if(DBTerminal.NAVTYPE_WAP.equals(terminalType))
                sb.append(toMOBILE(true));
            else
                sb.append(toMOBILE(false));

            out.print(sb.toString());
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            throw new JspTagException(e.getMessage());
        }

        return SKIP_BODY;
    }


    // MOBILE VIEW BEGIN //
    private String toMOBILE(boolean wap)
    {
        openplatform.menu.TreeMenu tm = new openplatform.menu.TreeMenu(menuId);
        return toMOBILENode(tm.getRootId(), 0, wap);
    }

    private String toMOBILENode(String id, int depth, boolean wap)
    {
        StringBuffer sb = new StringBuffer();
        tmask.clear();
        tmask.setMenuId(menuId);
        tmask.setParentId(id);
        tmask.setRoot("0");

        DBTreeMenu[] tms = DBTreeMenu.load(tmask);
        
        if(tms != null)
        {
            StringBuffer space = new StringBuffer();

            for(int i=0; i<depth; i++)
                space.append(".");

            int len = tms.length;
            for(int i=0; i<len; i++)
            {
                if(tms[i].getHref() != null)
                    sb.append(space)
                        .append("<a href=\"")
                        .append(encodeURL(tms[i].getHref())).append("\">").append(tms[i].getName()).append("</a>");
                else
                    sb.append(space).append(tms[i].getName());;
                
                if(wap)
                    sb.append("<br/>");
                else
                    sb.append("<br>");

                String ssMenu = toMOBILENode(tms[i].getId(), depth+1, wap);
                if(!ssMenu.equals("")) sb.append(ssMenu);
            }

        }
        
        return sb.toString();        
    }
    // MOBILE VIEW END //


    // PC VIEW BEGIN //
    private String toPC()
    {
        openplatform.menu.TreeMenu tm = new openplatform.menu.TreeMenu(menuId);
        return toPCNode(tm.getRootId(), 0);
    }

    private String toPCNode(String id, int depth)
    {
        StringBuffer sb = new StringBuffer();
        
        tmask.clear();
        tmask.setMenuId(menuId);
        tmask.setParentId(id);
        tmask.setRoot("0");

        DBTreeMenu[] tms = DBTreeMenu.load(tmask);
        
        if(tms != null)
        {
            if(depth==0)
                sb.append("<ul id=\"menu\">");
            else
                sb.append("<ul>");
 
            int len = tms.length;
            for(int i=0; i<len; i++)
            {
                if(tms[i].getHref() != null)
                    sb.append("<li><a href=\"")
                        .append(tms[i].getHref()).append("\"><span>").append(tms[i].getName()).append("</span></a>");
                else
                    sb.append("<li><a href=\"#\"><span>").append(tms[i].getName()).append("</span></a>");                    
                
                String ssMenu = toPCNode(tms[i].getId(),depth+1);
                if(!ssMenu.equals("")) sb.append(ssMenu);

                sb.append("</li>");
            }

            sb.append("</ul>");
        }
        
        return sb.toString();
    }
    // PC VIEW END //

}
