package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBImage
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBImage
{
    protected boolean inError = false;
    protected String imageId;
    protected String width;
    protected String height;
    protected String extension;

    public static String IMAGEID = "imageId";
    public static String WIDTH = "width";
    public static String HEIGHT = "height";
    public static String EXTENSION = "extension";
    public static String TABLE = "Image";

    private static Database db = Database.getInstance();


    public void clear()
    {
        imageId = null;
        width = null;
        height = null;
        extension = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBImage a = (DBImage)obj;
        return (imageId==null?a.getImageId()==null:imageId.equals(a.getImageId())) && (width==null?a.getWidth()==null:width.equals(a.getWidth())) && (height==null?a.getHeight()==null:height.equals(a.getHeight())) && (extension==null?a.getExtension()==null:extension.equals(a.getExtension()));    }

    public int hashCode()
    {
        return (imageId!=null?imageId.hashCode():0) + (width!=null?width.hashCode():0) + (height!=null?height.hashCode():0) + (extension!=null?extension.hashCode():0);
    }

    public String toString()
    {
        return "imageId="+imageId+"|"+"width="+width+"|"+"height="+height+"|"+"extension="+extension;
    }

    public void refresh()
    {
        if(imageId != null)
        {
            DBImage mask = new DBImage();
            mask.setImageId(imageId);
            DBImage var = DBImage.loadByKey(mask);
            if(var != null)
            {
                imageId = var.getImageId();
                width = var.getWidth();
                height = var.getHeight();
                extension = var.getExtension();
            }
        }
    }

    public void initBean(DBImage db)
    {
        this.imageId = db.getImageId();
        this.width = db.getWidth();
        this.height = db.getHeight();
        this.extension = db.getExtension();
    }

    public String getImageId()
    {
        return imageId;
    }

    public void setImageId(String imageId)
    {
        this.imageId = imageId;
    }

    public String getWidth()
    {
        return width;
    }

    public void setWidth(String width)
    {
        this.width = width;
    }

    public String getHeight()
    {
        return height;
    }

    public void setHeight(String height)
    {
        this.height = height;
    }

    public String getExtension()
    {
        return extension;
    }

    public void setExtension(String extension)
    {
        this.extension = extension;
    }

    /**
     * @return a DBImage table or null if nothing found or if an error occured
     **/
    public static DBImage[] load(DBImage mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBImage table or null if nothing found or if an error occured
     **/
    public static DBImage[] load(SQLConstraint sqlconstraint, DBImage mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
            if(mask.getWidth() != null) filter.set(WIDTH, mask.getWidth());
            if(mask.getHeight() != null) filter.set(HEIGHT, mask.getHeight());
            if(mask.getExtension() != null) filter.set(EXTENSION, mask.getExtension());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{IMAGEID, WIDTH, HEIGHT, EXTENSION}, filter);
            if(pvp == null) return null;
            else
            {
                DBImage[] result = new DBImage[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBImage();
                    result[i].setImageId(pvp[i].get(IMAGEID));
                    result[i].setWidth(pvp[i].get(WIDTH));
                    result[i].setHeight(pvp[i].get(HEIGHT));
                    result[i].setExtension(pvp[i].get(EXTENSION));
                }
                return result;
            }
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
            return null;
        }
    }

    /**
     * @return a DBImage object or null if nothing found or if an error occured
     **/
    public static DBImage loadByKey(DBImage mask)
    {
        DBImage[] res = DBImage.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBImage object or null if nothing found or if an error occured
     **/
    public static DBImage loadByKey(DBImage mask, SQLConstraint sqlconstraint)
    {
        DBImage[] res = DBImage.load(sqlconstraint, mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * Store the object in database
     **/
    public void store()
    {
        inError = false;
        ParamValuePair toinsert = new ParamValuePair();
        toinsert.set(IMAGEID,imageId);
        toinsert.set(WIDTH,width);
        toinsert.set(HEIGHT,height);
        toinsert.set(EXTENSION,extension);

        // Store a new entry
        if(imageId == null)
        {
            try
            {
                imageId = db.insert(TABLE, toinsert);
            }
            catch(Exception e)
            {
                inError=true;
                Debug.println(this, Debug.ERROR, e);
            }
        }
        // update entry
        else
        {
            ParamValuePair filter = new ParamValuePair();
            filter.set(IMAGEID, imageId);

            try
            {
                db.update(TABLE, filter, toinsert);
            }
            catch(Exception e)
            {
                inError=true;
                Debug.println(this, Debug.ERROR, e);
            }
        }
    }

    /**
     * Delete the object in database (if primary key is not null)
     **/
    public void delete()
    {
        if(imageId != null)
        {
            inError = false;
            ParamValuePair filter = new ParamValuePair();
            filter.set(IMAGEID, imageId);

            try
            {
                db.delete(TABLE, filter);
            }
            catch(Exception e)
            {
                inError=true;
                Debug.println(this, Debug.ERROR, e);
            }
        }
    }

    /**
     * Delete many files
     **/
    public static void delete(DBImage mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
            if(mask.getWidth() != null) filter.set(WIDTH, mask.getWidth());
            if(mask.getHeight() != null) filter.set(HEIGHT, mask.getHeight());
            if(mask.getExtension() != null) filter.set(EXTENSION, mask.getExtension());
        }

        try
        {
            db.delete(TABLE, filter);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }
    }

    /**
     * Delete many files
     **/
    public static int count(DBImage mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
            if(mask.getWidth() != null) filter.set(WIDTH, mask.getWidth());
            if(mask.getHeight() != null) filter.set(HEIGHT, mask.getHeight());
            if(mask.getExtension() != null) filter.set(EXTENSION, mask.getExtension());
        }

        try
        {
            return db.count(TABLE, sqlconst, filter);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }

        return 0;
    }

    /**
     * Return an average
     **/
    public static double avg(String field, DBImage mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
            if(mask.getWidth() != null) filter.set(WIDTH, mask.getWidth());
            if(mask.getHeight() != null) filter.set(HEIGHT, mask.getHeight());
            if(mask.getExtension() != null) filter.set(EXTENSION, mask.getExtension());
        }

        try
        {
            return db.average(TABLE, field, sqlconst, filter);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }

        return 0;
    }

    /**
     * Return a minimum
     **/
    public static double min(String field, DBImage mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
            if(mask.getWidth() != null) filter.set(WIDTH, mask.getWidth());
            if(mask.getHeight() != null) filter.set(HEIGHT, mask.getHeight());
            if(mask.getExtension() != null) filter.set(EXTENSION, mask.getExtension());
        }

        try
        {
            return db.min(TABLE, field, sqlconst, filter);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }

        return 0;
    }

    /**
     * Return a max
     **/
    public static double max(String field, DBImage mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
            if(mask.getWidth() != null) filter.set(WIDTH, mask.getWidth());
            if(mask.getHeight() != null) filter.set(HEIGHT, mask.getHeight());
            if(mask.getExtension() != null) filter.set(EXTENSION, mask.getExtension());
        }

        try
        {
            return db.max(TABLE, field, sqlconst, filter);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }

        return 0;
    }

    /**
     * Return a standard deviation (french ecart type)
     **/
    public static double std(String field, DBImage mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
            if(mask.getWidth() != null) filter.set(WIDTH, mask.getWidth());
            if(mask.getHeight() != null) filter.set(HEIGHT, mask.getHeight());
            if(mask.getExtension() != null) filter.set(EXTENSION, mask.getExtension());
        }

        try
        {
            return db.std(TABLE, field, sqlconst, filter);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }

        return 0;
    }

    /**
     * Return distinct entries)
     **/
    public static String[] distinct(String field, DBImage mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
            if(mask.getWidth() != null) filter.set(WIDTH, mask.getWidth());
            if(mask.getHeight() != null) filter.set(HEIGHT, mask.getHeight());
            if(mask.getExtension() != null) filter.set(EXTENSION, mask.getExtension());
        }

        try
        {
            return db.selectDistinct(TABLE, sqlconst, field, filter);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }

        return null;
    }
}
