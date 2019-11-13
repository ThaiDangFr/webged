package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBArticle
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBArticle
{
    protected boolean inError = false;
    protected String id;
    protected String date;
    protected String title;
    protected String categoryId;
    protected String summary;
    protected String text;
    protected String validate;
    protected String imageId;
    protected String imageLegend;
    protected String authorId;
    protected String moderatorId;

    public static String ID = "id";
    public static String DATE = "date";
    public static String TITLE = "title";
    public static String CATEGORYID = "categoryId";
    public static String SUMMARY = "summary";
    public static String TEXT = "text";
    public static String VALIDATE = "validate";
    public static String IMAGEID = "imageId";
    public static String IMAGELEGEND = "imageLegend";
    public static String AUTHORID = "authorId";
    public static String MODERATORID = "moderatorId";
    public static String TABLE = "Article";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        date = null;
        title = null;
        categoryId = null;
        summary = null;
        text = null;
        validate = null;
        imageId = null;
        imageLegend = null;
        authorId = null;
        moderatorId = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBArticle a = (DBArticle)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (date==null?a.getDate()==null:date.equals(a.getDate())) && (title==null?a.getTitle()==null:title.equals(a.getTitle())) && (categoryId==null?a.getCategoryId()==null:categoryId.equals(a.getCategoryId())) && (summary==null?a.getSummary()==null:summary.equals(a.getSummary())) && (text==null?a.getText()==null:text.equals(a.getText())) && (validate==null?a.getValidate()==null:validate.equals(a.getValidate())) && (imageId==null?a.getImageId()==null:imageId.equals(a.getImageId())) && (imageLegend==null?a.getImageLegend()==null:imageLegend.equals(a.getImageLegend())) && (authorId==null?a.getAuthorId()==null:authorId.equals(a.getAuthorId())) && (moderatorId==null?a.getModeratorId()==null:moderatorId.equals(a.getModeratorId()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (date!=null?date.hashCode():0) + (title!=null?title.hashCode():0) + (categoryId!=null?categoryId.hashCode():0) + (summary!=null?summary.hashCode():0) + (text!=null?text.hashCode():0) + (validate!=null?validate.hashCode():0) + (imageId!=null?imageId.hashCode():0) + (imageLegend!=null?imageLegend.hashCode():0) + (authorId!=null?authorId.hashCode():0) + (moderatorId!=null?moderatorId.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"date="+date+"|"+"title="+title+"|"+"categoryId="+categoryId+"|"+"summary="+summary+"|"+"text="+text+"|"+"validate="+validate+"|"+"imageId="+imageId+"|"+"imageLegend="+imageLegend+"|"+"authorId="+authorId+"|"+"moderatorId="+moderatorId;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBArticle mask = new DBArticle();
            mask.setId(id);
            DBArticle var = DBArticle.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                date = var.getDate();
                title = var.getTitle();
                categoryId = var.getCategoryId();
                summary = var.getSummary();
                text = var.getText();
                validate = var.getValidate();
                imageId = var.getImageId();
                imageLegend = var.getImageLegend();
                authorId = var.getAuthorId();
                moderatorId = var.getModeratorId();
            }
        }
    }

    public void initBean(DBArticle db)
    {
        this.id = db.getId();
        this.date = db.getDate();
        this.title = db.getTitle();
        this.categoryId = db.getCategoryId();
        this.summary = db.getSummary();
        this.text = db.getText();
        this.validate = db.getValidate();
        this.imageId = db.getImageId();
        this.imageLegend = db.getImageLegend();
        this.authorId = db.getAuthorId();
        this.moderatorId = db.getModeratorId();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(String categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getValidate()
    {
        return validate;
    }

    public void setValidate(String validate)
    {
        this.validate = validate;
    }

    public String getImageId()
    {
        return imageId;
    }

    public void setImageId(String imageId)
    {
        this.imageId = imageId;
    }

    public String getImageLegend()
    {
        return imageLegend;
    }

    public void setImageLegend(String imageLegend)
    {
        this.imageLegend = imageLegend;
    }

    public String getAuthorId()
    {
        return authorId;
    }

    public void setAuthorId(String authorId)
    {
        this.authorId = authorId;
    }

    public String getModeratorId()
    {
        return moderatorId;
    }

    public void setModeratorId(String moderatorId)
    {
        this.moderatorId = moderatorId;
    }

    /**
     * @return a DBArticle table or null if nothing found or if an error occured
     **/
    public static DBArticle[] load(DBArticle mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBArticle table or null if nothing found or if an error occured
     **/
    public static DBArticle[] load(SQLConstraint sqlconstraint, DBArticle mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getTitle() != null) filter.set(TITLE, mask.getTitle());
            if(mask.getCategoryId() != null) filter.set(CATEGORYID, mask.getCategoryId());
            if(mask.getSummary() != null) filter.set(SUMMARY, mask.getSummary());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
            if(mask.getValidate() != null) filter.set(VALIDATE, mask.getValidate());
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
            if(mask.getImageLegend() != null) filter.set(IMAGELEGEND, mask.getImageLegend());
            if(mask.getAuthorId() != null) filter.set(AUTHORID, mask.getAuthorId());
            if(mask.getModeratorId() != null) filter.set(MODERATORID, mask.getModeratorId());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, DATE, TITLE, CATEGORYID, SUMMARY, TEXT, VALIDATE, IMAGEID, IMAGELEGEND, AUTHORID, MODERATORID}, filter);
            if(pvp == null) return null;
            else
            {
                DBArticle[] result = new DBArticle[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBArticle();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setDate(pvp[i].get(DATE));
                    result[i].setTitle(pvp[i].get(TITLE));
                    result[i].setCategoryId(pvp[i].get(CATEGORYID));
                    result[i].setSummary(pvp[i].get(SUMMARY));
                    result[i].setText(pvp[i].get(TEXT));
                    result[i].setValidate(pvp[i].get(VALIDATE));
                    result[i].setImageId(pvp[i].get(IMAGEID));
                    result[i].setImageLegend(pvp[i].get(IMAGELEGEND));
                    result[i].setAuthorId(pvp[i].get(AUTHORID));
                    result[i].setModeratorId(pvp[i].get(MODERATORID));
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
     * @return a DBArticle object or null if nothing found or if an error occured
     **/
    public static DBArticle loadByKey(DBArticle mask)
    {
        DBArticle[] res = DBArticle.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBArticle object or null if nothing found or if an error occured
     **/
    public static DBArticle loadByKey(DBArticle mask, SQLConstraint sqlconstraint)
    {
        DBArticle[] res = DBArticle.load(sqlconstraint, mask);
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
        toinsert.set(ID,id);
        toinsert.set(DATE,date);
        toinsert.set(TITLE,title);
        toinsert.set(CATEGORYID,categoryId);
        toinsert.set(SUMMARY,summary);
        toinsert.set(TEXT,text);
        toinsert.set(VALIDATE,validate);
        toinsert.set(IMAGEID,imageId);
        toinsert.set(IMAGELEGEND,imageLegend);
        toinsert.set(AUTHORID,authorId);
        toinsert.set(MODERATORID,moderatorId);

        // Store a new entry
        if(id == null)
        {
            try
            {
                id = db.insert(TABLE, toinsert);
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
            filter.set(ID, id);

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
        if(id != null)
        {
            inError = false;
            ParamValuePair filter = new ParamValuePair();
            filter.set(ID, id);

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
    public static void delete(DBArticle mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getTitle() != null) filter.set(TITLE, mask.getTitle());
            if(mask.getCategoryId() != null) filter.set(CATEGORYID, mask.getCategoryId());
            if(mask.getSummary() != null) filter.set(SUMMARY, mask.getSummary());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
            if(mask.getValidate() != null) filter.set(VALIDATE, mask.getValidate());
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
            if(mask.getImageLegend() != null) filter.set(IMAGELEGEND, mask.getImageLegend());
            if(mask.getAuthorId() != null) filter.set(AUTHORID, mask.getAuthorId());
            if(mask.getModeratorId() != null) filter.set(MODERATORID, mask.getModeratorId());
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
    public static int count(DBArticle mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getTitle() != null) filter.set(TITLE, mask.getTitle());
            if(mask.getCategoryId() != null) filter.set(CATEGORYID, mask.getCategoryId());
            if(mask.getSummary() != null) filter.set(SUMMARY, mask.getSummary());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
            if(mask.getValidate() != null) filter.set(VALIDATE, mask.getValidate());
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
            if(mask.getImageLegend() != null) filter.set(IMAGELEGEND, mask.getImageLegend());
            if(mask.getAuthorId() != null) filter.set(AUTHORID, mask.getAuthorId());
            if(mask.getModeratorId() != null) filter.set(MODERATORID, mask.getModeratorId());
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
    public static double avg(String field, DBArticle mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getTitle() != null) filter.set(TITLE, mask.getTitle());
            if(mask.getCategoryId() != null) filter.set(CATEGORYID, mask.getCategoryId());
            if(mask.getSummary() != null) filter.set(SUMMARY, mask.getSummary());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
            if(mask.getValidate() != null) filter.set(VALIDATE, mask.getValidate());
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
            if(mask.getImageLegend() != null) filter.set(IMAGELEGEND, mask.getImageLegend());
            if(mask.getAuthorId() != null) filter.set(AUTHORID, mask.getAuthorId());
            if(mask.getModeratorId() != null) filter.set(MODERATORID, mask.getModeratorId());
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
    public static double min(String field, DBArticle mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getTitle() != null) filter.set(TITLE, mask.getTitle());
            if(mask.getCategoryId() != null) filter.set(CATEGORYID, mask.getCategoryId());
            if(mask.getSummary() != null) filter.set(SUMMARY, mask.getSummary());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
            if(mask.getValidate() != null) filter.set(VALIDATE, mask.getValidate());
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
            if(mask.getImageLegend() != null) filter.set(IMAGELEGEND, mask.getImageLegend());
            if(mask.getAuthorId() != null) filter.set(AUTHORID, mask.getAuthorId());
            if(mask.getModeratorId() != null) filter.set(MODERATORID, mask.getModeratorId());
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
    public static double max(String field, DBArticle mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getTitle() != null) filter.set(TITLE, mask.getTitle());
            if(mask.getCategoryId() != null) filter.set(CATEGORYID, mask.getCategoryId());
            if(mask.getSummary() != null) filter.set(SUMMARY, mask.getSummary());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
            if(mask.getValidate() != null) filter.set(VALIDATE, mask.getValidate());
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
            if(mask.getImageLegend() != null) filter.set(IMAGELEGEND, mask.getImageLegend());
            if(mask.getAuthorId() != null) filter.set(AUTHORID, mask.getAuthorId());
            if(mask.getModeratorId() != null) filter.set(MODERATORID, mask.getModeratorId());
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
    public static double std(String field, DBArticle mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getTitle() != null) filter.set(TITLE, mask.getTitle());
            if(mask.getCategoryId() != null) filter.set(CATEGORYID, mask.getCategoryId());
            if(mask.getSummary() != null) filter.set(SUMMARY, mask.getSummary());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
            if(mask.getValidate() != null) filter.set(VALIDATE, mask.getValidate());
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
            if(mask.getImageLegend() != null) filter.set(IMAGELEGEND, mask.getImageLegend());
            if(mask.getAuthorId() != null) filter.set(AUTHORID, mask.getAuthorId());
            if(mask.getModeratorId() != null) filter.set(MODERATORID, mask.getModeratorId());
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
    public static String[] distinct(String field, DBArticle mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getTitle() != null) filter.set(TITLE, mask.getTitle());
            if(mask.getCategoryId() != null) filter.set(CATEGORYID, mask.getCategoryId());
            if(mask.getSummary() != null) filter.set(SUMMARY, mask.getSummary());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
            if(mask.getValidate() != null) filter.set(VALIDATE, mask.getValidate());
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
            if(mask.getImageLegend() != null) filter.set(IMAGELEGEND, mask.getImageLegend());
            if(mask.getAuthorId() != null) filter.set(AUTHORID, mask.getAuthorId());
            if(mask.getModeratorId() != null) filter.set(MODERATORID, mask.getModeratorId());
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
