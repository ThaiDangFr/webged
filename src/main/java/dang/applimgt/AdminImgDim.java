package dang.applimgt;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import openplatform.database.dbean.DBFileBaseCache;
import openplatform.database.dbean.DBImage;
import openplatform.database.dbean.DBMimeTypes;
import openplatform.file.Repository;
import dang.cms.CmsLanguage;



/**
 * Class AdminImgDim
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class AdminImgDim
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    private final static int MAX_WIDTH = 65535;
    private final static int MAX_HEIGHT = 65535;

    private DBImage image = new DBImage();
    
    private DBMimeTypes mtmask = new DBMimeTypes();
    private HashMap buffer = new HashMap();
    private DBImage imask = new DBImage();
    private DBFileBaseCache fbcmask = new DBFileBaseCache();
    private HashSet hashset = new HashSet();


    public AdminImgDim()
    {
        mtmask.setPrimaryType("image");
    }


    public String doInit()
        throws Exception
    {
        DBMimeTypes[] images = DBMimeTypes.load(mtmask);
        buffer.clear();

        if(images != null)
        {
            int len = images.length;
            for(int i=0; i<len; i++)
            {
                buffer.put(images[i].getExtension(), images[i].getExtension());
            }
        }

        return null;
    }


    public String doAdd()
        throws Exception
    {
        if(image.getWidth()!=null && image.getHeight()!=null && image.getExtension()!=null)
        {
            if(!checkDim(image.getWidth()) || !checkDim(image.getHeight()))
            {
                throw new Exception(cmsLang.translate("bad.value")+":"+image.getWidth()+"x"+image.getHeight());
            }

            if(Integer.parseInt(image.getWidth()) > MAX_WIDTH || Integer.parseInt(image.getHeight()) > MAX_HEIGHT)
            {
                throw new Exception(cmsLang.translate("bad.value")+":"+image.getWidth()+"x"+image.getHeight());
            }

            // Check if the dimension already exists
           
            if(DBImage.count(image, null) != 0)
            {
                throw new Exception(cmsLang.translate("duplicate.value"));
            } 
            
            image.store();

            if(image.isInError())
            {
                throw new Exception(cmsLang.translate("duplicate.value"));
            }
            else
            {
                String pattern = image.getWidth()+"x"+image.getHeight()+"-"+image.getExtension();
            }
        }
        else
        {
            throw new Exception(cmsLang.translate("bad.value"));
        }

        return null;
    }


    public String doUpdate()
        throws Exception
    {
        if(image.getImageId()==null || image.getWidth()==null || image.getHeight()==null || image.getExtension()==null)
        {
            throw new Exception(cmsLang.translate("bad.value"));
        }

        if(!checkDim(image.getWidth()) || !checkDim(image.getHeight()))
        {
            throw new Exception(cmsLang.translate("bad.value")+":"+image.getWidth()+"x"+image.getHeight());
        }

        if(Integer.parseInt(image.getWidth()) > MAX_WIDTH || Integer.parseInt(image.getHeight()) > MAX_HEIGHT)
        {
            throw new Exception(cmsLang.translate("bad.value")+":"+image.getWidth()+"x"+image.getHeight());
        }

        
        // Check if the dimension already exists
        imask.clear();
        imask.setWidth(image.getWidth());
        imask.setHeight(image.getHeight());
        imask.setExtension(image.getExtension());

        if(DBImage.count(imask, null) != 0)
        {
            throw new Exception(cmsLang.translate("duplicate.value"));
        } 
        imask.setImageId(image.getImageId());
        imask.store();
        return cmsLang.translate("item.updated");
    }


    public String doDelete()
        throws Exception
    {
        imask.clear();
        imask.setImageId(image.getImageId());

        DBImage image = DBImage.loadByKey(imask);
        
        if(image == null)
        {
            throw new Exception(cmsLang.translate("not.found"));
        }

        // Delete FileBaseCache
        fbcmask.clear();
        fbcmask.setImageId(image.getImageId());
        DBFileBaseCache[] fbc = DBFileBaseCache.load(fbcmask);

        if(fbc != null)
        {
            hashset.clear();
            int len = fbc.length;
            for(int i=0; i<len; i++)
            {
                String fileBaseId = fbc[i].getCacheFile();
                hashset.add(fbc[i].getFileBase());
                Repository.deleteFile(fileBaseId);
            }

            Object[] obj = hashset.toArray();
            len = obj.length;
            for(int i=0; i<len; i++)
            {
                String fileBaseId = (String)obj[i];
                Repository.deleteFile(fileBaseId);
            }
        }

        // Delete Image
        image.delete();

        return cmsLang.translate("item.deleted");
    }


    private boolean checkDim(String dim)
    {
        try
        {
            int i = Integer.parseInt(dim);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    

    public DBImage[] getImageItems()
    {
    	return DBImage.load(null);
    }

    public String doCreateImage()
        throws Exception
    {
        image.clear();
    	return null;
    }
    
    
    public Map getExtensionList()  
    {
        return buffer;
    }        
    
    public void setImageId(String strVal) { image.setImageId(strVal); }
    public void setWidth(String strVal)	  { image.setWidth(strVal);   }
    public void setHeight(String strVal)  { image.setHeight(strVal);  }
    public void setExtension(String ext)  { image.setExtension(ext); }


}
