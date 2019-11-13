package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBSnmpFormula
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBSnmpFormula
{
    protected boolean inError = false;
    protected String formulaId;
    protected String displayName;
    protected String description;
    protected String formula;
    protected String legend;

    public static String FORMULAID = "formulaId";
    public static String DISPLAYNAME = "displayName";
    public static String DESCRIPTION = "description";
    public static String FORMULA = "formula";
    public static String LEGEND = "legend";
    public static String TABLE = "SnmpFormula";

    private static Database db = Database.getInstance();


    public void clear()
    {
        formulaId = null;
        displayName = null;
        description = null;
        formula = null;
        legend = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBSnmpFormula a = (DBSnmpFormula)obj;
        return (formulaId==null?a.getFormulaId()==null:formulaId.equals(a.getFormulaId())) && (displayName==null?a.getDisplayName()==null:displayName.equals(a.getDisplayName())) && (description==null?a.getDescription()==null:description.equals(a.getDescription())) && (formula==null?a.getFormula()==null:formula.equals(a.getFormula())) && (legend==null?a.getLegend()==null:legend.equals(a.getLegend()));    }

    public int hashCode()
    {
        return (formulaId!=null?formulaId.hashCode():0) + (displayName!=null?displayName.hashCode():0) + (description!=null?description.hashCode():0) + (formula!=null?formula.hashCode():0) + (legend!=null?legend.hashCode():0);
    }

    public String toString()
    {
        return "formulaId="+formulaId+"|"+"displayName="+displayName+"|"+"description="+description+"|"+"formula="+formula+"|"+"legend="+legend;
    }

    public void refresh()
    {
        if(formulaId != null)
        {
            DBSnmpFormula mask = new DBSnmpFormula();
            mask.setFormulaId(formulaId);
            DBSnmpFormula var = DBSnmpFormula.loadByKey(mask);
            if(var != null)
            {
                formulaId = var.getFormulaId();
                displayName = var.getDisplayName();
                description = var.getDescription();
                formula = var.getFormula();
                legend = var.getLegend();
            }
        }
    }

    public void initBean(DBSnmpFormula db)
    {
        this.formulaId = db.getFormulaId();
        this.displayName = db.getDisplayName();
        this.description = db.getDescription();
        this.formula = db.getFormula();
        this.legend = db.getLegend();
    }

    public String getFormulaId()
    {
        return formulaId;
    }

    public void setFormulaId(String formulaId)
    {
        this.formulaId = formulaId;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getFormula()
    {
        return formula;
    }

    public void setFormula(String formula)
    {
        this.formula = formula;
    }

    public String getLegend()
    {
        return legend;
    }

    public void setLegend(String legend)
    {
        this.legend = legend;
    }

    /**
     * @return a DBSnmpFormula table or null if nothing found or if an error occured
     **/
    public static DBSnmpFormula[] load(DBSnmpFormula mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBSnmpFormula table or null if nothing found or if an error occured
     **/
    public static DBSnmpFormula[] load(SQLConstraint sqlconstraint, DBSnmpFormula mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getFormulaId() != null) filter.set(FORMULAID, mask.getFormulaId());
            if(mask.getDisplayName() != null) filter.set(DISPLAYNAME, mask.getDisplayName());
            if(mask.getDescription() != null) filter.set(DESCRIPTION, mask.getDescription());
            if(mask.getFormula() != null) filter.set(FORMULA, mask.getFormula());
            if(mask.getLegend() != null) filter.set(LEGEND, mask.getLegend());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{FORMULAID, DISPLAYNAME, DESCRIPTION, FORMULA, LEGEND}, filter);
            if(pvp == null) return null;
            else
            {
                DBSnmpFormula[] result = new DBSnmpFormula[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBSnmpFormula();
                    result[i].setFormulaId(pvp[i].get(FORMULAID));
                    result[i].setDisplayName(pvp[i].get(DISPLAYNAME));
                    result[i].setDescription(pvp[i].get(DESCRIPTION));
                    result[i].setFormula(pvp[i].get(FORMULA));
                    result[i].setLegend(pvp[i].get(LEGEND));
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
     * @return a DBSnmpFormula object or null if nothing found or if an error occured
     **/
    public static DBSnmpFormula loadByKey(DBSnmpFormula mask)
    {
        DBSnmpFormula[] res = DBSnmpFormula.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBSnmpFormula object or null if nothing found or if an error occured
     **/
    public static DBSnmpFormula loadByKey(DBSnmpFormula mask, SQLConstraint sqlconstraint)
    {
        DBSnmpFormula[] res = DBSnmpFormula.load(sqlconstraint, mask);
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
        toinsert.set(FORMULAID,formulaId);
        toinsert.set(DISPLAYNAME,displayName);
        toinsert.set(DESCRIPTION,description);
        toinsert.set(FORMULA,formula);
        toinsert.set(LEGEND,legend);

        // Store a new entry
        if(formulaId == null)
        {
            try
            {
                formulaId = db.insert(TABLE, toinsert);
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
            filter.set(FORMULAID, formulaId);

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
        if(formulaId != null)
        {
            inError = false;
            ParamValuePair filter = new ParamValuePair();
            filter.set(FORMULAID, formulaId);

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
    public static void delete(DBSnmpFormula mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getFormulaId() != null) filter.set(FORMULAID, mask.getFormulaId());
            if(mask.getDisplayName() != null) filter.set(DISPLAYNAME, mask.getDisplayName());
            if(mask.getDescription() != null) filter.set(DESCRIPTION, mask.getDescription());
            if(mask.getFormula() != null) filter.set(FORMULA, mask.getFormula());
            if(mask.getLegend() != null) filter.set(LEGEND, mask.getLegend());
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
    public static int count(DBSnmpFormula mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getFormulaId() != null) filter.set(FORMULAID, mask.getFormulaId());
            if(mask.getDisplayName() != null) filter.set(DISPLAYNAME, mask.getDisplayName());
            if(mask.getDescription() != null) filter.set(DESCRIPTION, mask.getDescription());
            if(mask.getFormula() != null) filter.set(FORMULA, mask.getFormula());
            if(mask.getLegend() != null) filter.set(LEGEND, mask.getLegend());
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
    public static double avg(String field, DBSnmpFormula mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getFormulaId() != null) filter.set(FORMULAID, mask.getFormulaId());
            if(mask.getDisplayName() != null) filter.set(DISPLAYNAME, mask.getDisplayName());
            if(mask.getDescription() != null) filter.set(DESCRIPTION, mask.getDescription());
            if(mask.getFormula() != null) filter.set(FORMULA, mask.getFormula());
            if(mask.getLegend() != null) filter.set(LEGEND, mask.getLegend());
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
    public static double min(String field, DBSnmpFormula mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getFormulaId() != null) filter.set(FORMULAID, mask.getFormulaId());
            if(mask.getDisplayName() != null) filter.set(DISPLAYNAME, mask.getDisplayName());
            if(mask.getDescription() != null) filter.set(DESCRIPTION, mask.getDescription());
            if(mask.getFormula() != null) filter.set(FORMULA, mask.getFormula());
            if(mask.getLegend() != null) filter.set(LEGEND, mask.getLegend());
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
    public static double max(String field, DBSnmpFormula mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getFormulaId() != null) filter.set(FORMULAID, mask.getFormulaId());
            if(mask.getDisplayName() != null) filter.set(DISPLAYNAME, mask.getDisplayName());
            if(mask.getDescription() != null) filter.set(DESCRIPTION, mask.getDescription());
            if(mask.getFormula() != null) filter.set(FORMULA, mask.getFormula());
            if(mask.getLegend() != null) filter.set(LEGEND, mask.getLegend());
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
    public static double std(String field, DBSnmpFormula mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getFormulaId() != null) filter.set(FORMULAID, mask.getFormulaId());
            if(mask.getDisplayName() != null) filter.set(DISPLAYNAME, mask.getDisplayName());
            if(mask.getDescription() != null) filter.set(DESCRIPTION, mask.getDescription());
            if(mask.getFormula() != null) filter.set(FORMULA, mask.getFormula());
            if(mask.getLegend() != null) filter.set(LEGEND, mask.getLegend());
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
    public static String[] distinct(String field, DBSnmpFormula mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getFormulaId() != null) filter.set(FORMULAID, mask.getFormulaId());
            if(mask.getDisplayName() != null) filter.set(DISPLAYNAME, mask.getDisplayName());
            if(mask.getDescription() != null) filter.set(DESCRIPTION, mask.getDescription());
            if(mask.getFormula() != null) filter.set(FORMULA, mask.getFormula());
            if(mask.getLegend() != null) filter.set(LEGEND, mask.getLegend());
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
