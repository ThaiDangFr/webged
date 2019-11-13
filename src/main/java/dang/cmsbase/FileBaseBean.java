package dang.cmsbase;

import openplatform.database.dbean.DBFileBase;


/**
 * Class FileBaseBean
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class FileBaseBean
{
    private DBFileBase fbmask = new DBFileBase();

    public void setFileBaseId(String id)
    {
        fbmask.clear();
        fbmask.setFileBaseId(id);
    }

    public String getFileName()
    {
        DBFileBase fb = DBFileBase.loadByKey(fbmask);
        if(fb == null) return null;
        else return fb.getFilename();
    }
}
