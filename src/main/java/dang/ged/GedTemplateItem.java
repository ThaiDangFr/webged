package dang.ged;

import openplatform.database.dbean.*;
import java.util.*;

import dang.cms.CmsLanguage;
import openplatform.tools.*;

/**
 * GedTemplateItem.java
 *
 *
 * Created: Sun Apr 10 02:44:36 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class GedTemplateItem extends DBGedTemplateItem
{
    //private static final HashMap typeOption = _TYPE_MAP;
	private CmsLanguage cmsLang = new CmsLanguage();
    private static HashMap sizeOption = new OrdHashMap();

    private String value;

    static
    {
        for(int i=1; i<101; i++)
            sizeOption.put(Integer.toString(i), Integer.toString(i));
    }

    public GedTemplateItem()
    {
        super();
    }

    public GedTemplateItem(DBGedTemplateItem it)
    {
        super();
        initBean(it);
    }


    public boolean isFreeType()
    {
        if(DBGedTemplateItem.TYPE_FREE.equals(type)) return true;
        else return false;
    }

    public boolean isDateType()
    {
        if(DBGedTemplateItem.TYPE_DATE.equals(type)) return true;
        else return false;
    }

    public boolean isEnumerationType()
    {
        if(DBGedTemplateItem.TYPE_ENUMERATION.equals(type)) return true;
        else return false;
    }


    public HashMap getEnumerationOption()
    {
        HashMap map = new OrdHashMap();
        
        if(enumeration == null) return map;

        String delim;
        if(enumeration.indexOf(",") != -1) delim = ",";
        else if(enumeration.indexOf(";") != -1) delim = ";";
        else delim = "|";

        StringTokenizer st = new StringTokenizer(enumeration, delim);
        while(st.hasMoreTokens())
        {
            String tok = st.nextToken().trim();
            map.put(tok,tok);
        }

        return map;
    }
    

    /**
     * Gets the value of typeOption
     *
     * @return the value of typeOption
     */
    public final HashMap getTypeOption()
    {
        //return GedTemplateItem.typeOption;
    	
    	HashMap map = new HashMap();
    	
    	Iterator it = _TYPE_MAP.keySet().iterator();
    	while(it.hasNext())
    	{
    		String key = (String)it.next();
    		String val = cmsLang.translate((String)_TYPE_MAP.get(key));
    		map.put(key, val);
    	}
    	
    	return map;
    }

    /**
     * Gets the value of sizeOption
     *
     * @return the value of sizeOption
     */
    public static final HashMap getSizeOption()
    {
        return GedTemplateItem.sizeOption;
    }

    /**
     * Sets the value of sizeOption
     *
     * @param argSizeOption Value to assign to this.sizeOption
     */
    public static final void setSizeOption(final HashMap argSizeOption)
    {
        GedTemplateItem.sizeOption = argSizeOption;
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
