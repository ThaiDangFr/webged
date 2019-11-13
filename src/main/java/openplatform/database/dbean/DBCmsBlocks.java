package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBCmsBlocks
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBCmsBlocks
{
    protected boolean inError = false;
    protected String id;
    protected String weight;
    protected String visibleIn;
    protected String visible;
    protected String position;
    protected String title;
    protected String moduleId;
    protected String jsp;

    public static String ID = "id";
    public static String WEIGHT = "weight";
    public static String VISIBLEIN = "visibleIn";
    /** all **/
    public static final String VISIBLEIN_ALL = "all";
    /** first **/
    public static final String VISIBLEIN_FIRST = "first";
    /** list **/
    public static final String VISIBLEIN_LIST = "list";
    /** Contains the SQL enumeration for VISIBLEIN **/
    public static final ArrayList _VISIBLEIN_LIST = new ArrayList();
    static {_VISIBLEIN_LIST.add(VISIBLEIN_ALL);_VISIBLEIN_LIST.add(VISIBLEIN_FIRST);_VISIBLEIN_LIST.add(VISIBLEIN_LIST);}
    /** Contains the SQL HASHMAP for VISIBLEIN **/
    public static final OrdHashMap _VISIBLEIN_MAP = new OrdHashMap();
    static {_VISIBLEIN_MAP.put(VISIBLEIN_ALL,VISIBLEIN_ALL);_VISIBLEIN_MAP.put(VISIBLEIN_FIRST,VISIBLEIN_FIRST);_VISIBLEIN_MAP.put(VISIBLEIN_LIST,VISIBLEIN_LIST);}
    public static String VISIBLE = "visible";
    public static String POSITION = "position";
    /** left **/
    public static final String POSITION_LEFT = "left";
    /** center **/
    public static final String POSITION_CENTER = "center";
    /** right **/
    public static final String POSITION_RIGHT = "right";
    /** Contains the SQL enumeration for POSITION **/
    public static final ArrayList _POSITION_LIST = new ArrayList();
    static {_POSITION_LIST.add(POSITION_LEFT);_POSITION_LIST.add(POSITION_CENTER);_POSITION_LIST.add(POSITION_RIGHT);}
    /** Contains the SQL HASHMAP for POSITION **/
    public static final OrdHashMap _POSITION_MAP = new OrdHashMap();
    static {_POSITION_MAP.put(POSITION_LEFT,POSITION_LEFT);_POSITION_MAP.put(POSITION_CENTER,POSITION_CENTER);_POSITION_MAP.put(POSITION_RIGHT,POSITION_RIGHT);}
    public static String TITLE = "title";
    public static String MODULEID = "moduleId";
    public static String JSP = "jsp";
    public static String TABLE = "CmsBlocks";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        weight = null;
        visibleIn = null;
        visible = null;
        position = null;
        title = null;
        moduleId = null;
        jsp = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBCmsBlocks a = (DBCmsBlocks)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (weight==null?a.getWeight()==null:weight.equals(a.getWeight())) && (visibleIn==null?a.getVisibleIn()==null:visibleIn.equals(a.getVisibleIn())) && (visible==null?a.getVisible()==null:visible.equals(a.getVisible())) && (position==null?a.getPosition()==null:position.equals(a.getPosition())) && (title==null?a.getTitle()==null:title.equals(a.getTitle())) && (moduleId==null?a.getModuleId()==null:moduleId.equals(a.getModuleId())) && (jsp==null?a.getJsp()==null:jsp.equals(a.getJsp()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (weight!=null?weight.hashCode():0) + (visibleIn!=null?visibleIn.hashCode():0) + (visible!=null?visible.hashCode():0) + (position!=null?position.hashCode():0) + (title!=null?title.hashCode():0) + (moduleId!=null?moduleId.hashCode():0) + (jsp!=null?jsp.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"weight="+weight+"|"+"visibleIn="+visibleIn+"|"+"visible="+visible+"|"+"position="+position+"|"+"title="+title+"|"+"moduleId="+moduleId+"|"+"jsp="+jsp;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBCmsBlocks mask = new DBCmsBlocks();
            mask.setId(id);
            DBCmsBlocks var = DBCmsBlocks.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                weight = var.getWeight();
                visibleIn = var.getVisibleIn();
                visible = var.getVisible();
                position = var.getPosition();
                title = var.getTitle();
                moduleId = var.getModuleId();
                jsp = var.getJsp();
            }
        }
    }

    public void initBean(DBCmsBlocks db)
    {
        this.id = db.getId();
        this.weight = db.getWeight();
        this.visibleIn = db.getVisibleIn();
        this.visible = db.getVisible();
        this.position = db.getPosition();
        this.title = db.getTitle();
        this.moduleId = db.getModuleId();
        this.jsp = db.getJsp();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getWeight()
    {
        return weight;
    }

    public void setWeight(String weight)
    {
        this.weight = weight;
    }

    public String getVisibleIn()
    {
        return visibleIn;
    }

    public void setVisibleIn(String visibleIn)
    {
        this.visibleIn = visibleIn;
    }

    public String getVisible()
    {
        return visible;
    }

    public void setVisible(String visible)
    {
        this.visible = visible;
    }

    public String getPosition()
    {
        return position;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getModuleId()
    {
        return moduleId;
    }

    public void setModuleId(String moduleId)
    {
        this.moduleId = moduleId;
    }

    public String getJsp()
    {
        return jsp;
    }

    public void setJsp(String jsp)
    {
        this.jsp = jsp;
    }

    /**
     * @return a DBCmsBlocks table or null if nothing found or if an error occured
     **/
    public static DBCmsBlocks[] load(DBCmsBlocks mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBCmsBlocks table or null if nothing found or if an error occured
     **/
    public static DBCmsBlocks[] load(SQLConstraint sqlconstraint, DBCmsBlocks mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getWeight() != null) filter.set(WEIGHT, mask.getWeight());
            if(mask.getVisibleIn() != null) filter.set(VISIBLEIN, mask.getVisibleIn());
            if(mask.getVisible() != null) filter.set(VISIBLE, mask.getVisible());
            if(mask.getPosition() != null) filter.set(POSITION, mask.getPosition());
            if(mask.getTitle() != null) filter.set(TITLE, mask.getTitle());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
            if(mask.getJsp() != null) filter.set(JSP, mask.getJsp());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, WEIGHT, VISIBLEIN, VISIBLE, POSITION, TITLE, MODULEID, JSP}, filter);
            if(pvp == null) return null;
            else
            {
                DBCmsBlocks[] result = new DBCmsBlocks[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBCmsBlocks();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setWeight(pvp[i].get(WEIGHT));
                    result[i].setVisibleIn(pvp[i].get(VISIBLEIN));
                    result[i].setVisible(pvp[i].get(VISIBLE));
                    result[i].setPosition(pvp[i].get(POSITION));
                    result[i].setTitle(pvp[i].get(TITLE));
                    result[i].setModuleId(pvp[i].get(MODULEID));
                    result[i].setJsp(pvp[i].get(JSP));
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
     * @return a DBCmsBlocks object or null if nothing found or if an error occured
     **/
    public static DBCmsBlocks loadByKey(DBCmsBlocks mask)
    {
        DBCmsBlocks[] res = DBCmsBlocks.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBCmsBlocks object or null if nothing found or if an error occured
     **/
    public static DBCmsBlocks loadByKey(DBCmsBlocks mask, SQLConstraint sqlconstraint)
    {
        DBCmsBlocks[] res = DBCmsBlocks.load(sqlconstraint, mask);
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
        toinsert.set(WEIGHT,weight);
        toinsert.set(VISIBLEIN,visibleIn);
        toinsert.set(VISIBLE,visible);
        toinsert.set(POSITION,position);
        toinsert.set(TITLE,title);
        toinsert.set(MODULEID,moduleId);
        toinsert.set(JSP,jsp);

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
    public static void delete(DBCmsBlocks mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getWeight() != null) filter.set(WEIGHT, mask.getWeight());
            if(mask.getVisibleIn() != null) filter.set(VISIBLEIN, mask.getVisibleIn());
            if(mask.getVisible() != null) filter.set(VISIBLE, mask.getVisible());
            if(mask.getPosition() != null) filter.set(POSITION, mask.getPosition());
            if(mask.getTitle() != null) filter.set(TITLE, mask.getTitle());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
            if(mask.getJsp() != null) filter.set(JSP, mask.getJsp());
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
    public static int count(DBCmsBlocks mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getWeight() != null) filter.set(WEIGHT, mask.getWeight());
            if(mask.getVisibleIn() != null) filter.set(VISIBLEIN, mask.getVisibleIn());
            if(mask.getVisible() != null) filter.set(VISIBLE, mask.getVisible());
            if(mask.getPosition() != null) filter.set(POSITION, mask.getPosition());
            if(mask.getTitle() != null) filter.set(TITLE, mask.getTitle());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
            if(mask.getJsp() != null) filter.set(JSP, mask.getJsp());
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
    public static double avg(String field, DBCmsBlocks mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getWeight() != null) filter.set(WEIGHT, mask.getWeight());
            if(mask.getVisibleIn() != null) filter.set(VISIBLEIN, mask.getVisibleIn());
            if(mask.getVisible() != null) filter.set(VISIBLE, mask.getVisible());
            if(mask.getPosition() != null) filter.set(POSITION, mask.getPosition());
            if(mask.getTitle() != null) filter.set(TITLE, mask.getTitle());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
            if(mask.getJsp() != null) filter.set(JSP, mask.getJsp());
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
    public static double min(String field, DBCmsBlocks mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getWeight() != null) filter.set(WEIGHT, mask.getWeight());
            if(mask.getVisibleIn() != null) filter.set(VISIBLEIN, mask.getVisibleIn());
            if(mask.getVisible() != null) filter.set(VISIBLE, mask.getVisible());
            if(mask.getPosition() != null) filter.set(POSITION, mask.getPosition());
            if(mask.getTitle() != null) filter.set(TITLE, mask.getTitle());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
            if(mask.getJsp() != null) filter.set(JSP, mask.getJsp());
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
    public static double max(String field, DBCmsBlocks mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getWeight() != null) filter.set(WEIGHT, mask.getWeight());
            if(mask.getVisibleIn() != null) filter.set(VISIBLEIN, mask.getVisibleIn());
            if(mask.getVisible() != null) filter.set(VISIBLE, mask.getVisible());
            if(mask.getPosition() != null) filter.set(POSITION, mask.getPosition());
            if(mask.getTitle() != null) filter.set(TITLE, mask.getTitle());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
            if(mask.getJsp() != null) filter.set(JSP, mask.getJsp());
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
    public static double std(String field, DBCmsBlocks mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getWeight() != null) filter.set(WEIGHT, mask.getWeight());
            if(mask.getVisibleIn() != null) filter.set(VISIBLEIN, mask.getVisibleIn());
            if(mask.getVisible() != null) filter.set(VISIBLE, mask.getVisible());
            if(mask.getPosition() != null) filter.set(POSITION, mask.getPosition());
            if(mask.getTitle() != null) filter.set(TITLE, mask.getTitle());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
            if(mask.getJsp() != null) filter.set(JSP, mask.getJsp());
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
    public static String[] distinct(String field, DBCmsBlocks mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getWeight() != null) filter.set(WEIGHT, mask.getWeight());
            if(mask.getVisibleIn() != null) filter.set(VISIBLEIN, mask.getVisibleIn());
            if(mask.getVisible() != null) filter.set(VISIBLE, mask.getVisible());
            if(mask.getPosition() != null) filter.set(POSITION, mask.getPosition());
            if(mask.getTitle() != null) filter.set(TITLE, mask.getTitle());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
            if(mask.getJsp() != null) filter.set(JSP, mask.getJsp());
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
