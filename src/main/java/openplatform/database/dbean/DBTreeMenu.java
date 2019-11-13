package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBTreeMenu
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBTreeMenu
{
    protected boolean inError = false;
    protected String id;
    protected String menuId;
    protected String name;
    protected String parentId;
    protected String href;
    protected String root;

    public static String ID = "id";
    public static String MENUID = "menuId";
    public static String NAME = "name";
    public static String PARENTID = "parentId";
    public static String HREF = "href";
    public static String ROOT = "root";
    public static String TABLE = "TreeMenu";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        menuId = null;
        name = null;
        parentId = null;
        href = null;
        root = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBTreeMenu a = (DBTreeMenu)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (menuId==null?a.getMenuId()==null:menuId.equals(a.getMenuId())) && (name==null?a.getName()==null:name.equals(a.getName())) && (parentId==null?a.getParentId()==null:parentId.equals(a.getParentId())) && (href==null?a.getHref()==null:href.equals(a.getHref())) && (root==null?a.getRoot()==null:root.equals(a.getRoot()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (menuId!=null?menuId.hashCode():0) + (name!=null?name.hashCode():0) + (parentId!=null?parentId.hashCode():0) + (href!=null?href.hashCode():0) + (root!=null?root.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"menuId="+menuId+"|"+"name="+name+"|"+"parentId="+parentId+"|"+"href="+href+"|"+"root="+root;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBTreeMenu mask = new DBTreeMenu();
            mask.setId(id);
            DBTreeMenu var = DBTreeMenu.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                menuId = var.getMenuId();
                name = var.getName();
                parentId = var.getParentId();
                href = var.getHref();
                root = var.getRoot();
            }
        }
    }

    public void initBean(DBTreeMenu db)
    {
        this.id = db.getId();
        this.menuId = db.getMenuId();
        this.name = db.getName();
        this.parentId = db.getParentId();
        this.href = db.getHref();
        this.root = db.getRoot();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getMenuId()
    {
        return menuId;
    }

    public void setMenuId(String menuId)
    {
        this.menuId = menuId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getHref()
    {
        return href;
    }

    public void setHref(String href)
    {
        this.href = href;
    }

    public String getRoot()
    {
        return root;
    }

    public void setRoot(String root)
    {
        this.root = root;
    }

    /**
     * @return a DBTreeMenu table or null if nothing found or if an error occured
     **/
    public static DBTreeMenu[] load(DBTreeMenu mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBTreeMenu table or null if nothing found or if an error occured
     **/
    public static DBTreeMenu[] load(SQLConstraint sqlconstraint, DBTreeMenu mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getMenuId() != null) filter.set(MENUID, mask.getMenuId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getParentId() != null) filter.set(PARENTID, mask.getParentId());
            if(mask.getHref() != null) filter.set(HREF, mask.getHref());
            if(mask.getRoot() != null) filter.set(ROOT, mask.getRoot());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, MENUID, NAME, PARENTID, HREF, ROOT}, filter);
            if(pvp == null) return null;
            else
            {
                DBTreeMenu[] result = new DBTreeMenu[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBTreeMenu();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setMenuId(pvp[i].get(MENUID));
                    result[i].setName(pvp[i].get(NAME));
                    result[i].setParentId(pvp[i].get(PARENTID));
                    result[i].setHref(pvp[i].get(HREF));
                    result[i].setRoot(pvp[i].get(ROOT));
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
     * @return a DBTreeMenu object or null if nothing found or if an error occured
     **/
    public static DBTreeMenu loadByKey(DBTreeMenu mask)
    {
        DBTreeMenu[] res = DBTreeMenu.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBTreeMenu object or null if nothing found or if an error occured
     **/
    public static DBTreeMenu loadByKey(DBTreeMenu mask, SQLConstraint sqlconstraint)
    {
        DBTreeMenu[] res = DBTreeMenu.load(sqlconstraint, mask);
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
        toinsert.set(MENUID,menuId);
        toinsert.set(NAME,name);
        toinsert.set(PARENTID,parentId);
        toinsert.set(HREF,href);
        toinsert.set(ROOT,root);

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
    public static void delete(DBTreeMenu mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getMenuId() != null) filter.set(MENUID, mask.getMenuId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getParentId() != null) filter.set(PARENTID, mask.getParentId());
            if(mask.getHref() != null) filter.set(HREF, mask.getHref());
            if(mask.getRoot() != null) filter.set(ROOT, mask.getRoot());
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
    public static int count(DBTreeMenu mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getMenuId() != null) filter.set(MENUID, mask.getMenuId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getParentId() != null) filter.set(PARENTID, mask.getParentId());
            if(mask.getHref() != null) filter.set(HREF, mask.getHref());
            if(mask.getRoot() != null) filter.set(ROOT, mask.getRoot());
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
    public static double avg(String field, DBTreeMenu mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getMenuId() != null) filter.set(MENUID, mask.getMenuId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getParentId() != null) filter.set(PARENTID, mask.getParentId());
            if(mask.getHref() != null) filter.set(HREF, mask.getHref());
            if(mask.getRoot() != null) filter.set(ROOT, mask.getRoot());
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
    public static double min(String field, DBTreeMenu mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getMenuId() != null) filter.set(MENUID, mask.getMenuId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getParentId() != null) filter.set(PARENTID, mask.getParentId());
            if(mask.getHref() != null) filter.set(HREF, mask.getHref());
            if(mask.getRoot() != null) filter.set(ROOT, mask.getRoot());
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
    public static double max(String field, DBTreeMenu mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getMenuId() != null) filter.set(MENUID, mask.getMenuId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getParentId() != null) filter.set(PARENTID, mask.getParentId());
            if(mask.getHref() != null) filter.set(HREF, mask.getHref());
            if(mask.getRoot() != null) filter.set(ROOT, mask.getRoot());
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
    public static double std(String field, DBTreeMenu mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getMenuId() != null) filter.set(MENUID, mask.getMenuId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getParentId() != null) filter.set(PARENTID, mask.getParentId());
            if(mask.getHref() != null) filter.set(HREF, mask.getHref());
            if(mask.getRoot() != null) filter.set(ROOT, mask.getRoot());
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
    public static String[] distinct(String field, DBTreeMenu mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getMenuId() != null) filter.set(MENUID, mask.getMenuId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getParentId() != null) filter.set(PARENTID, mask.getParentId());
            if(mask.getHref() != null) filter.set(HREF, mask.getHref());
            if(mask.getRoot() != null) filter.set(ROOT, mask.getRoot());
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
