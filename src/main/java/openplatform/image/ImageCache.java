package openplatform.image;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import openplatform.database.dbean.DBFileBase;
import openplatform.database.dbean.DBFileBaseCache;
import openplatform.database.dbean.DBImage;
import openplatform.file.Repository;
import openplatform.tools.Debug;
import openplatform.tools.StreamConverter;

/**
 * Class ImageCache
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class ImageCache
{
    public static final long MAX_SIZE = 128*1024*1024; // 128M


    /**
     * Create a cache file for the file and specified image profile
     * Can be used to recreate cache
     * Used to pre-fill some cache image
     */
    public static boolean createCache(String fileBaseId, String imageId)
    {
        try
        {
            if(fileBaseId==null || imageId==null) return false;

            InputStream input = Repository.getStream(fileBaseId);
            byte[] data = StreamConverter.inputStreamToBytes(input);

            if(data.length >= MAX_SIZE) return false;


            DBImage imask = new DBImage();
            imask.setImageId(imageId);
            DBImage image = DBImage.loadByKey(imask);
            if(image == null) return false;

            // prepare the output
            String formatOut = image.getExtension();
            int width = Integer.parseInt(image.getWidth());
            int height = Integer.parseInt(image.getHeight());


            // recalculate dimension
            int newwidth = width;
            int newheight = height;
            ImageDimension dim = ImageConverter.getDimension(data);
            int oricol = dim.getWidth();
            int orirow = dim.getHeight();
            if(oricol <= width && orirow <= height)
            {
                newwidth = oricol;
                newheight = orirow;
            }
            else
            {
                float ratiocol = (float)oricol/(float)width;
                float ratiorow = (float)orirow/(float)height;

                float max = ratiocol>ratiorow?ratiocol:ratiorow;

                newwidth = (int)(oricol/max);
                newheight = (int)(orirow/max);
            }
            Debug.println(null, Debug.DEBUG, "Asked "+width+"x"+height+", proceed "+newwidth+"x"+newheight+ ", "+image.getExtension());
            //

            byte[] cacheByte = ImageConverter.convertResize(data, formatOut, newwidth, newheight);
            String cacheFileBaseId = Repository.storeTempFile(
                getCacheFileName(fileBaseId,formatOut),new ByteArrayInputStream(cacheByte));

            if(cacheFileBaseId != null)
            {
                DBFileBaseCache cache = new DBFileBaseCache();
                cache.setImageId(imageId);
                cache.setFileBase(fileBaseId);
                cache.setCacheFile(cacheFileBaseId);
                cache.store();
                return true;
            }
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }


        return false;
    }


    /**
     * Return the cache file. If the cache does not exist, try to create one. If nothing can be generated, return null
     */
    public static InputStream getCache(String fileBaseId, String imageId)
    {
        if(fileBaseId == null || imageId == null) return null;
        
        DBFileBaseCache cmask = new DBFileBaseCache();
        cmask.setImageId(imageId);
        cmask.setFileBase(fileBaseId);


        if(DBFileBaseCache.count(cmask, null) == 0) createCache(fileBaseId, imageId);

        DBFileBaseCache c = DBFileBaseCache.loadByKey(cmask);
        if(c == null) return null;

        DBFileBase fbmask = new DBFileBase();
        fbmask.setFileBaseId(c.getCacheFile());
        DBFileBase fb = DBFileBase.loadByKey(fbmask);

        if(fb == null) return null;

        return Repository.getStream(fb.getFileBaseId());
    }


    /**
     * delete a cache file
     */
    public static void deleteCache(String fileBaseId, String imageId)
    {
        if(fileBaseId == null || imageId == null) return;

        DBFileBaseCache cmask = new DBFileBaseCache();
        cmask.setImageId(imageId);
        cmask.setFileBase(fileBaseId);


        DBFileBaseCache c = DBFileBaseCache.loadByKey(cmask);
        if(c == null) return;

        DBFileBase fbmask = new DBFileBase();
        fbmask.setFileBaseId(c.getCacheFile());
        DBFileBase fb = DBFileBase.loadByKey(fbmask);

        if(fb == null) return;
        
        fb.delete();
    }


    // PRIVATE CALLS


    private static String getCacheFileName(String fileBaseId, String extension)
    {
        DBFileBase mask = new DBFileBase();
        mask.setFileBaseId(fileBaseId);
        DBFileBase fb = DBFileBase.loadByKey(mask);
        String filename = fb.getFilename();
        int idx = filename.lastIndexOf(".");

        if(idx == -1)
        {
            double d = Math.random() * 10000000000D;
            int i = (int)d;
            String temp = Integer.toString(i) + "." + extension;
            return temp;
        }

        filename = filename.substring(0, idx) + "." + extension;
        return filename;
    }


    
//     public static void main(String[] args)
//         throws Exception
//     {
//         String fileBaseId = Repository.storeFile("04juillet.gif", new FileInputStream("/home/thai/images/04juillet.gif"), false);

//         if(fileBaseId != null)
//         {        
//             File file = ImageCache.getCache(fileBaseId, "1");
//             System.out.println(file.getPath());
//         }

// //        ImageCache.deleteCache(fileBaseId, "1");
//     }

}
