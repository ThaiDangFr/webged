package dang.applimgt;

import java.util.*;

import dang.cms.CmsLanguage;
import openplatform.database.dbean.*;
import openplatform.tools.*;


/**
 * Class AdminMimeTypes
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class AdminMimeTypes extends DBMimeTypes
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    public String doAdd()
        throws Exception

    {
        if(extension == null || extension.trim().equals("")
           ||  primaryType == null || primaryType.trim().equals("")
           || secondaryType == null || secondaryType.trim().equals(""))
        {
            throw new Exception(cmsLang.translate("miss.parameter"));
        }

        this.mimetypesId = null;

        this.store();
        return cmsLang.translate("item.added");
    }


    public String doDelete()
        throws Exception
    {
        if(mimetypesId == null)
            throw new Exception(cmsLang.translate("no.item.selected"));

        DBMimeTypes mmask = new DBMimeTypes();
        mmask.setMimetypesId(mimetypesId);
        DBMimeTypes.delete(mmask);

        return cmsLang.translate("item.deleted");
    }


    public HashMap getMimeTypesOption()
    {
        HashMap map = new OrdHashMap();
        DBMimeTypes[] mt = DBMimeTypes.load(null);
        if(mt == null) return map;

        int len = mt.length;
        for(int i=0; i<len; i++)
        {
            map.put(mt[i].getMimetypesId(), mt[i].getExtension()+ " ("
                    + mt[i].getPrimaryType()+ "/" + mt[i].getSecondaryType()
                    + ")");
        }

        return map;
    }


}
