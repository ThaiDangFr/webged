package dang.ged;

import openplatform.database.dbean.*;

/**
 * GedFilesVersion.java
 *
 *
 * Created: Tue May  3 15:50:28 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class GedFilesVersion extends DBGedFilesVersion
{
    public GedFilesVersion(DBGedFilesVersion fv)
    {
        initBean(fv);
    }

    public String getLogin()
    {
        if(authorId == null) return null;

        DBUser umask = new DBUser();
        umask.setUserId(authorId);
        DBUser u = DBUser.loadByKey(umask);
        if(u == null) return null;

        return u.getLogin();
    }


    public boolean equals(Object o)
    {
        if(o == null) return false;
        
        if(o instanceof GedFilesVersion)
        {
            GedFilesVersion gf = (GedFilesVersion)o;
            if(gf.getId().equals(this.id)) return true;
        }

        return false;
    }

    
    public String getPathName()
    {
    	DBGedFiles fmask = new DBGedFiles();
    	fmask.setId(this.fileId);
        DBGedFiles f = DBGedFiles.loadByKey(fmask);
        
        if(f == null) return this.name;
        
        DBGedDirectory dmask = new DBGedDirectory();
        dmask.setId(f.getDirectoryId());

        DBGedDirectory d = DBGedDirectory.loadByKey(dmask);
        
        return d.getPath()+this.name;
    }
    
}
