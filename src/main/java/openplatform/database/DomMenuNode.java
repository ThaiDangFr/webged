package openplatform.database;

import java.util.*;
import openplatform.tools.*;

/**
 * Class DomMenuNode
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class DomMenuNode
{
    private String content="";
    private String uri = "";
    private String statusText = "";
    private String target = "";
    private ArrayList sonDomMenuNode=null;


   

    /**
     * for debuging purpose
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(" (");
        sb.append(content);

        for(int i=0;sonDomMenuNode!=null && i<sonDomMenuNode.size();i++)
        {
            DomMenuNode son = (DomMenuNode)sonDomMenuNode.get(i);
            sb.append(son.toString());
        }
   
        sb.append(") ");
        return sb.toString();
    }


    /**
     * @return the Javascript to display the menu
     **/
    public String generateJS()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("'contents','"+StringFilter.replaceSpecialChars(content)+"',");
        sb.append("'uri','"+uri+"',");
        sb.append("'statusText','"+StringFilter.replaceSpecialChars(statusText)+"',");
        sb.append("'target','"+target+"'");

        for(int i=0;sonDomMenuNode!=null && i<sonDomMenuNode.size();i++)
        {
            sb.append(",");

            DomMenuNode son = (DomMenuNode)sonDomMenuNode.get(i);
            sb.append(i+1);
            sb.append(",new domMenu_Hash(");
            sb.append(son.generateJS());
            sb.append(")");
        }
   
        return sb.toString();
    }
    

    public String getContent()
    {
        return content;
    }

    public void setContent(String acontent)
    {
        content=acontent;
    }

    public ArrayList getSonDomMenuNode()
    {
        return sonDomMenuNode;
    }

    public void setSonDomMenuNode(ArrayList asonDomMenuNode)
    {
        sonDomMenuNode=asonDomMenuNode;
    }   

    public String getTarget()
    {
        return target;
    }

    public void setTarget(String atarget)
    {
        target=atarget;
    }

    public String getStatusText()
    {
        return statusText;
    }

    public void setStatusText(String astatusText)
    {
        statusText=astatusText;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri(String auri)
    {
        uri=auri;
    }
}
