package dang.applimgt;

import openplatform.database.dbean.*;
import java.util.*;

import dang.cms.CmsLanguage;
import openplatform.tools.*;

/**
 * Class AdminTerminal
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class AdminTerminal extends DBTerminal
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    public DBTerminal[] getTerminals()
    {
        return DBTerminal.load(null);
    }

    public HashMap getImageOptions()
    {
        HashMap map = new OrdHashMap();
        
        DBImage[] im = DBImage.load(null);
        if(im == null) return map;

        int len = im.length;
        for(int i=0; i<len; i++)
        {
            map.put(im[i].getImageId(), im[i].getWidth()+"x"+im[i].getHeight()+"-"+im[i].getExtension());
        }

        return map;
    }

    public HashMap getNavTypeOptions()
    {
        return this._NAVTYPE_MAP;
    }

    public String doUpdate()
        throws Exception
    {
        if(this.terminalId == null) throw new Exception("Invalid id");
        this.store();
        return cmsLang.translate("item.updated");
    }

    public String doDelete()
        throws Exception
    {
        if(this.terminalId == null) throw new Exception("Invalid id");
        this.delete();
        return cmsLang.translate("item.deleted");
    }

    public String doAdd()
        throws Exception
    {
        this.terminalId = null;
        this.counter = "0";
        this.store();
        return cmsLang.translate("item.added");
    }
}
