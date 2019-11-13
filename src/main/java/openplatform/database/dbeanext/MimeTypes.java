package openplatform.database.dbeanext;
import openplatform.database.dbean.DBMimeTypes;

/**
 * Class MimeTypes
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class MimeTypes
{
    public static final String DEFAULT_MIME_TYPE = "application/octet-stream";
    public static final String IMAGE = "image";
    
    private static MimeTypes _mimetypes = new MimeTypes();
    //private Hashtable extMTBuffer; // extension mimetypes buffer
    public DBMimeTypes DEFAULT_MIME_TYPE_CLASS=null;
    


    private MimeTypes()
    {
        //Debug.println(this,Debug.DEBUG,"constructor called");
        //extMTBuffer = new Hashtable();

        DBMimeTypes mask = new DBMimeTypes();
        mask.setExtension("exe");
        DEFAULT_MIME_TYPE_CLASS = DBMimeTypes.loadByKey(mask); 
    }


    public static MimeTypes getInstance()
    {
        return _mimetypes;
    }


    public DBMimeTypes getMimetypes(String extension)
    {
        if(extension==null)
            return DEFAULT_MIME_TYPE_CLASS;

        extension = extension.toLowerCase();

        DBMimeTypes mask = new DBMimeTypes();
        mask.setExtension(extension);
        DBMimeTypes mt = DBMimeTypes.loadByKey(mask);
        
        if(mt != null) return mt;
        else return DEFAULT_MIME_TYPE_CLASS;
        
        /*
        Object obj = extMTBuffer.get(extension);
        if(obj==null)
        {
            DBMimeTypes mask = new DBMimeTypes();
            mask.setExtension(extension);
            DBMimeTypes[] mt = DBMimeTypes.load(mask);
            if(mt!=null && mt.length==1)
            {
                Debug.println(this, Debug.DEBUG, "Adding Mimetypes for "+extension+" to memory cache");
                extMTBuffer.put(extension, mt[0]);
                return mt[0];
            }
            else
            {
                Debug.println(this, Debug.DEBUG, "Adding Mimetypes for "+extension+" to memory cache");
                extMTBuffer.put(extension, DEFAULT_MIME_TYPE_CLASS);
                return DEFAULT_MIME_TYPE_CLASS;
            }
        }
        else
        {
            Debug.println(this, Debug.DEBUG,"Returning Mimetypes for "+extension+" from memory cache (size "+extMTBuffer.size()+")");
            return (DBMimeTypes)obj;
        }
        */
    }


    public String getExtension(String filename)
    {
        int idx = filename.lastIndexOf(".");
        if(idx == -1)
            return null;

        return filename.substring(idx+1);
    }


    public DBMimeTypes[] getMimetypes(String primarytype, String secondarytype)
    {
        DBMimeTypes mask = new DBMimeTypes();
        mask.setPrimaryType(primarytype);
        mask.setSecondaryType(secondarytype);

        return DBMimeTypes.load(mask);
    }


    public DBMimeTypes[] getMimetypesFromString(String mimetypes)
    {
        if(mimetypes == null) return null;
        
        int idx = mimetypes.indexOf("/");
        if(idx == -1) return null;
        String p = mimetypes.substring(0, idx);
        String s = mimetypes.substring(idx+1);
        return getMimetypes(p, s);
    }

    /*
    public boolean isImage(String filename)
    {
    	String extension = getExtension(filename);
    	if(extension == null) return false;
    	DBMimeTypes mt = getMimetypes(extension);
    	if(mt == null) return false;
    	
    	String pt = mt.getPrimaryType();
    	if(pt == null) return false;
    	
    	if(IMAGE.equalsIgnoreCase(pt)) return true;
    	else return false;
    }
     */
    
    
/*
    public void clearBuffer()
    {
        extMTBuffer.clear();
    }
    */
    
}
