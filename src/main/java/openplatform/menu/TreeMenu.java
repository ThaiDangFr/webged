package openplatform.menu;

import java.util.*;
import openplatform.database.dbean.*;
import openplatform.tools.*;

/**
 * Class TreeMenu
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class TreeMenu
{
    private DBTreeMenu tmask = new DBTreeMenu();
    private String rootId;
    private String menuId;

    /**
     * Construct a new TreeMenu with that menuId or load it from the database
     */
    public TreeMenu(String menuId)
    {
        this.menuId = menuId;

        // check root
        tmask.setMenuId(menuId);
        tmask.setRoot("1");
        if(DBTreeMenu.count(tmask,null) == 0)
        {
            tmask.setName("ROOT");
            tmask.store();
            rootId = tmask.getId();
        }
        else
        {
            DBTreeMenu tm = DBTreeMenu.loadByKey(tmask);
            rootId = tm.getId();
        }
        Debug.println(this, Debug.DEBUG, "menuId="+menuId+" rootId="+rootId);
    }

    /**
     * get the rootId (unique)
     */
    public String getRootId()
    {
        return rootId;
    }

    public String getMenuId()
    {
        return menuId;
    }

    /**
     * List all children id
     */
    public ArrayList listChildren(String id)
    {
        ArrayList al = new ArrayList();
        
        tmask.clear();
        tmask.setMenuId(menuId);
        tmask.setParentId(id);

        DBTreeMenu[] tms = DBTreeMenu.load(tmask);

        if(tms==null) return al;

        int len = tms.length;
        for(int i=0; i<len; i++)
            al.add(tms[i].getId());

        return al;
    }

    /**
     * Delete the node
     */
    public void deleteNode(String id)
    {
        tmask.clear();
        tmask.setMenuId(menuId);
        tmask.setId(id);
        DBTreeMenu.delete(tmask);
    }

    /**
     * Add a node
     */
    public String addNode(String name, String parentId, String href)
    {
        DBTreeMenu tm = new DBTreeMenu();
        tm.setMenuId(menuId);
        tm.setName(name);
        tm.setParentId(parentId);
        tm.setHref(href);
        tm.store();
        
        return tm.getId();
    }

//     /**
//      * Generate on the fly the XHTML UL LI
//      */
//     public String toHTML()
//     {
//         return toHTMLNode(rootId);
//     }


//     private String toHTMLNode(String id)
//     {
//         StringBuffer sb = new StringBuffer();
        
//         tmask.clear();
//         tmask.setMenuId(menuId);
//         tmask.setParentId(id);

//         DBTreeMenu[] tms = DBTreeMenu.load(tmask);
        
//         if(tms != null)
//         {
//             sb.append("<ul>");
 
//             int len = tms.length;
//             for(int i=0; i<len; i++)
//             {
//                 sb.append("<li onMouseOver=\"hover(this);\" onMouseOut=\"hover(this);\"><a href=\"").append(tms[i].getHref()).append("\">").append(tms[i].getName()).append("</a>");
                
//                 String ssMenu = toHTMLNode(tms[i].getId());
//                 if(!ssMenu.equals("")) sb.append(ssMenu);

//                 sb.append("</li>");
//             }

//             sb.append("</ul>");
//         }
        
//         return sb.toString();
//     }

/*
    public static void main(String[] args)
    {
        TreeMenu tm = new TreeMenu("1");
        String rootId = tm.getRootId();
        String id1 = tm.addNode("coucou", rootId, "http://www.dangconsulting.fr");
        String id2 = tm.addNode("coucou2", rootId, "http://www.dangconsulting.fr");
        String id3 = tm.addNode("fils de coucou2", id2, "http://www.dangconsulting.fr");
        String id4 = tm.addNode("fils2 de coucou2", id2, "http://www.dangconsulting.fr");
        String id5 = tm.addNode("fils de fils", id3, "http://www.dangconsulting.fr");

        System.out.println(tm.listChildren(rootId));
    }
    */
}
