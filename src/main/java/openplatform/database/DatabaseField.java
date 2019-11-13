package openplatform.database;

import openplatform.tools.*;
import java.util.StringTokenizer;


/**
 * Class DatabaseEntry
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class DatabaseField
{
    private String field;
    private String type;
    private boolean canBeNull;
    private boolean isPrimaryKey;
    private String defaultValue;
    private String extra;

    public String getExtra()
    {
        return extra;
    }

    public void setExtra(String aextra)
    {
        extra=aextra;
    }

    public String getDefaultValue()
    {
        return defaultValue;
    }

    public void setDefaultValue(String adefaultValue)
    {
        defaultValue=adefaultValue;
    }

    public boolean getIsPrimaryKey()
    {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(boolean aisPrimaryKey)
    {
        isPrimaryKey=aisPrimaryKey;
    }

    public boolean getCanBeNull()
    {
        return canBeNull;
    }

    public void setCanBeNull(boolean acanBeNull)
    {
        canBeNull=acanBeNull;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String atype)
    {
        type=atype;
    }

    public String getField()
    {
        return field;
    }

    public void setField(String afield)
    {
        field=afield;
    }



    /** ACTIONS **/

    /**
     * is the type an enumeration
     */
    public boolean isEnumeration()
    {
        if(type == null) return false;

        String stype = type.trim().toLowerCase();
        if(stype.indexOf("enum")==0) return true;
        else return false;
    }


    /**
     * if it is an enumeration, get the Enumeration list
     */
    public StringArrayList getEnumeration()
    {
        StringArrayList sal = new StringArrayList();
        String stype = type.trim();
        stype = stype.substring(5, stype.length()-1);
        StringTokenizer st = new StringTokenizer(stype, ",");
        while(st.hasMoreTokens())
        {
            String tok = st.nextToken();
            if(tok != null)
            {
                tok = tok.trim();
                if(tok.charAt(0)=='\'' && tok.charAt(tok.length()-1)=='\'')
                    tok = tok.substring(1, tok.length()-1);
                sal.addString(tok);
            }
        }

        return sal;
    }


}
