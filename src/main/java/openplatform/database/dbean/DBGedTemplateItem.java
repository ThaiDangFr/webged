package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBGedTemplateItem
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBGedTemplateItem
{
    protected boolean inError = false;
    protected String id;
    protected String fieldname;
    protected String size;
    protected String type;
    protected String enumeration;
    protected String defaultValue;
    protected String weight;
    protected String templateId;

    public static String ID = "id";
    public static String FIELDNAME = "fieldname";
    public static String SIZE = "size";
    public static String TYPE = "type";
    /** free **/
    public static final String TYPE_FREE = "free";
    /** date **/
    public static final String TYPE_DATE = "date";
    /** enumeration **/
    public static final String TYPE_ENUMERATION = "enumeration";
    /** Contains the SQL enumeration for TYPE **/
    public static final ArrayList _TYPE_LIST = new ArrayList();
    static {_TYPE_LIST.add(TYPE_FREE);_TYPE_LIST.add(TYPE_DATE);_TYPE_LIST.add(TYPE_ENUMERATION);}
    /** Contains the SQL HASHMAP for TYPE **/
    public static final OrdHashMap _TYPE_MAP = new OrdHashMap();
    static {_TYPE_MAP.put(TYPE_FREE,TYPE_FREE);_TYPE_MAP.put(TYPE_DATE,TYPE_DATE);_TYPE_MAP.put(TYPE_ENUMERATION,TYPE_ENUMERATION);}
    public static String ENUMERATION = "enumeration";
    public static String DEFAULTVALUE = "defaultValue";
    public static String WEIGHT = "weight";
    public static String TEMPLATEID = "templateId";
    public static String TABLE = "GedTemplateItem";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        fieldname = null;
        size = null;
        type = null;
        enumeration = null;
        defaultValue = null;
        weight = null;
        templateId = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBGedTemplateItem a = (DBGedTemplateItem)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (fieldname==null?a.getFieldname()==null:fieldname.equals(a.getFieldname())) && (size==null?a.getSize()==null:size.equals(a.getSize())) && (type==null?a.getType()==null:type.equals(a.getType())) && (enumeration==null?a.getEnumeration()==null:enumeration.equals(a.getEnumeration())) && (defaultValue==null?a.getDefaultValue()==null:defaultValue.equals(a.getDefaultValue())) && (weight==null?a.getWeight()==null:weight.equals(a.getWeight())) && (templateId==null?a.getTemplateId()==null:templateId.equals(a.getTemplateId()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (fieldname!=null?fieldname.hashCode():0) + (size!=null?size.hashCode():0) + (type!=null?type.hashCode():0) + (enumeration!=null?enumeration.hashCode():0) + (defaultValue!=null?defaultValue.hashCode():0) + (weight!=null?weight.hashCode():0) + (templateId!=null?templateId.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"fieldname="+fieldname+"|"+"size="+size+"|"+"type="+type+"|"+"enumeration="+enumeration+"|"+"defaultValue="+defaultValue+"|"+"weight="+weight+"|"+"templateId="+templateId;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBGedTemplateItem mask = new DBGedTemplateItem();
            mask.setId(id);
            DBGedTemplateItem var = DBGedTemplateItem.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                fieldname = var.getFieldname();
                size = var.getSize();
                type = var.getType();
                enumeration = var.getEnumeration();
                defaultValue = var.getDefaultValue();
                weight = var.getWeight();
                templateId = var.getTemplateId();
            }
        }
    }

    public void initBean(DBGedTemplateItem db)
    {
        this.id = db.getId();
        this.fieldname = db.getFieldname();
        this.size = db.getSize();
        this.type = db.getType();
        this.enumeration = db.getEnumeration();
        this.defaultValue = db.getDefaultValue();
        this.weight = db.getWeight();
        this.templateId = db.getTemplateId();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getFieldname()
    {
        return fieldname;
    }

    public void setFieldname(String fieldname)
    {
        this.fieldname = fieldname;
    }

    public String getSize()
    {
        return size;
    }

    public void setSize(String size)
    {
        this.size = size;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getEnumeration()
    {
        return enumeration;
    }

    public void setEnumeration(String enumeration)
    {
        this.enumeration = enumeration;
    }

    public String getDefaultValue()
    {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    public String getWeight()
    {
        return weight;
    }

    public void setWeight(String weight)
    {
        this.weight = weight;
    }

    public String getTemplateId()
    {
        return templateId;
    }

    public void setTemplateId(String templateId)
    {
        this.templateId = templateId;
    }

    /**
     * @return a DBGedTemplateItem table or null if nothing found or if an error occured
     **/
    public static DBGedTemplateItem[] load(DBGedTemplateItem mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBGedTemplateItem table or null if nothing found or if an error occured
     **/
    public static DBGedTemplateItem[] load(SQLConstraint sqlconstraint, DBGedTemplateItem mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFieldname() != null) filter.set(FIELDNAME, mask.getFieldname());
            if(mask.getSize() != null) filter.set(SIZE, mask.getSize());
            if(mask.getType() != null) filter.set(TYPE, mask.getType());
            if(mask.getEnumeration() != null) filter.set(ENUMERATION, mask.getEnumeration());
            if(mask.getDefaultValue() != null) filter.set(DEFAULTVALUE, mask.getDefaultValue());
            if(mask.getWeight() != null) filter.set(WEIGHT, mask.getWeight());
            if(mask.getTemplateId() != null) filter.set(TEMPLATEID, mask.getTemplateId());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, FIELDNAME, SIZE, TYPE, ENUMERATION, DEFAULTVALUE, WEIGHT, TEMPLATEID}, filter);
            if(pvp == null) return null;
            else
            {
                DBGedTemplateItem[] result = new DBGedTemplateItem[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBGedTemplateItem();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setFieldname(pvp[i].get(FIELDNAME));
                    result[i].setSize(pvp[i].get(SIZE));
                    result[i].setType(pvp[i].get(TYPE));
                    result[i].setEnumeration(pvp[i].get(ENUMERATION));
                    result[i].setDefaultValue(pvp[i].get(DEFAULTVALUE));
                    result[i].setWeight(pvp[i].get(WEIGHT));
                    result[i].setTemplateId(pvp[i].get(TEMPLATEID));
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
     * @return a DBGedTemplateItem object or null if nothing found or if an error occured
     **/
    public static DBGedTemplateItem loadByKey(DBGedTemplateItem mask)
    {
        DBGedTemplateItem[] res = DBGedTemplateItem.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBGedTemplateItem object or null if nothing found or if an error occured
     **/
    public static DBGedTemplateItem loadByKey(DBGedTemplateItem mask, SQLConstraint sqlconstraint)
    {
        DBGedTemplateItem[] res = DBGedTemplateItem.load(sqlconstraint, mask);
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
        toinsert.set(FIELDNAME,fieldname);
        toinsert.set(SIZE,size);
        toinsert.set(TYPE,type);
        toinsert.set(ENUMERATION,enumeration);
        toinsert.set(DEFAULTVALUE,defaultValue);
        toinsert.set(WEIGHT,weight);
        toinsert.set(TEMPLATEID,templateId);

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
    public static void delete(DBGedTemplateItem mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFieldname() != null) filter.set(FIELDNAME, mask.getFieldname());
            if(mask.getSize() != null) filter.set(SIZE, mask.getSize());
            if(mask.getType() != null) filter.set(TYPE, mask.getType());
            if(mask.getEnumeration() != null) filter.set(ENUMERATION, mask.getEnumeration());
            if(mask.getDefaultValue() != null) filter.set(DEFAULTVALUE, mask.getDefaultValue());
            if(mask.getWeight() != null) filter.set(WEIGHT, mask.getWeight());
            if(mask.getTemplateId() != null) filter.set(TEMPLATEID, mask.getTemplateId());
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
    public static int count(DBGedTemplateItem mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFieldname() != null) filter.set(FIELDNAME, mask.getFieldname());
            if(mask.getSize() != null) filter.set(SIZE, mask.getSize());
            if(mask.getType() != null) filter.set(TYPE, mask.getType());
            if(mask.getEnumeration() != null) filter.set(ENUMERATION, mask.getEnumeration());
            if(mask.getDefaultValue() != null) filter.set(DEFAULTVALUE, mask.getDefaultValue());
            if(mask.getWeight() != null) filter.set(WEIGHT, mask.getWeight());
            if(mask.getTemplateId() != null) filter.set(TEMPLATEID, mask.getTemplateId());
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
    public static double avg(String field, DBGedTemplateItem mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFieldname() != null) filter.set(FIELDNAME, mask.getFieldname());
            if(mask.getSize() != null) filter.set(SIZE, mask.getSize());
            if(mask.getType() != null) filter.set(TYPE, mask.getType());
            if(mask.getEnumeration() != null) filter.set(ENUMERATION, mask.getEnumeration());
            if(mask.getDefaultValue() != null) filter.set(DEFAULTVALUE, mask.getDefaultValue());
            if(mask.getWeight() != null) filter.set(WEIGHT, mask.getWeight());
            if(mask.getTemplateId() != null) filter.set(TEMPLATEID, mask.getTemplateId());
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
    public static double min(String field, DBGedTemplateItem mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFieldname() != null) filter.set(FIELDNAME, mask.getFieldname());
            if(mask.getSize() != null) filter.set(SIZE, mask.getSize());
            if(mask.getType() != null) filter.set(TYPE, mask.getType());
            if(mask.getEnumeration() != null) filter.set(ENUMERATION, mask.getEnumeration());
            if(mask.getDefaultValue() != null) filter.set(DEFAULTVALUE, mask.getDefaultValue());
            if(mask.getWeight() != null) filter.set(WEIGHT, mask.getWeight());
            if(mask.getTemplateId() != null) filter.set(TEMPLATEID, mask.getTemplateId());
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
    public static double max(String field, DBGedTemplateItem mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFieldname() != null) filter.set(FIELDNAME, mask.getFieldname());
            if(mask.getSize() != null) filter.set(SIZE, mask.getSize());
            if(mask.getType() != null) filter.set(TYPE, mask.getType());
            if(mask.getEnumeration() != null) filter.set(ENUMERATION, mask.getEnumeration());
            if(mask.getDefaultValue() != null) filter.set(DEFAULTVALUE, mask.getDefaultValue());
            if(mask.getWeight() != null) filter.set(WEIGHT, mask.getWeight());
            if(mask.getTemplateId() != null) filter.set(TEMPLATEID, mask.getTemplateId());
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
    public static double std(String field, DBGedTemplateItem mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFieldname() != null) filter.set(FIELDNAME, mask.getFieldname());
            if(mask.getSize() != null) filter.set(SIZE, mask.getSize());
            if(mask.getType() != null) filter.set(TYPE, mask.getType());
            if(mask.getEnumeration() != null) filter.set(ENUMERATION, mask.getEnumeration());
            if(mask.getDefaultValue() != null) filter.set(DEFAULTVALUE, mask.getDefaultValue());
            if(mask.getWeight() != null) filter.set(WEIGHT, mask.getWeight());
            if(mask.getTemplateId() != null) filter.set(TEMPLATEID, mask.getTemplateId());
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
    public static String[] distinct(String field, DBGedTemplateItem mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFieldname() != null) filter.set(FIELDNAME, mask.getFieldname());
            if(mask.getSize() != null) filter.set(SIZE, mask.getSize());
            if(mask.getType() != null) filter.set(TYPE, mask.getType());
            if(mask.getEnumeration() != null) filter.set(ENUMERATION, mask.getEnumeration());
            if(mask.getDefaultValue() != null) filter.set(DEFAULTVALUE, mask.getDefaultValue());
            if(mask.getWeight() != null) filter.set(WEIGHT, mask.getWeight());
            if(mask.getTemplateId() != null) filter.set(TEMPLATEID, mask.getTemplateId());
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
