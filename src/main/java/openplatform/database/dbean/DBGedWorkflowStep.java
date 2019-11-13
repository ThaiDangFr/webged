package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBGedWorkflowStep
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBGedWorkflowStep
{
    protected boolean inError = false;
    protected String id;
    protected String name;
    protected String sendTo1;
    protected String sendTo2;
    protected String timelimit1;
    protected String timelimit2;
    protected String weight;
    protected String workflowId;

    public static String ID = "id";
    public static String NAME = "name";
    public static String SENDTO1 = "sendTo1";
    public static String SENDTO2 = "sendTo2";
    public static String TIMELIMIT1 = "timelimit1";
    public static String TIMELIMIT2 = "timelimit2";
    public static String WEIGHT = "weight";
    public static String WORKFLOWID = "workflowId";
    public static String TABLE = "GedWorkflowStep";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        name = null;
        sendTo1 = null;
        sendTo2 = null;
        timelimit1 = null;
        timelimit2 = null;
        weight = null;
        workflowId = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBGedWorkflowStep a = (DBGedWorkflowStep)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (name==null?a.getName()==null:name.equals(a.getName())) && (sendTo1==null?a.getSendTo1()==null:sendTo1.equals(a.getSendTo1())) && (sendTo2==null?a.getSendTo2()==null:sendTo2.equals(a.getSendTo2())) && (timelimit1==null?a.getTimelimit1()==null:timelimit1.equals(a.getTimelimit1())) && (timelimit2==null?a.getTimelimit2()==null:timelimit2.equals(a.getTimelimit2())) && (weight==null?a.getWeight()==null:weight.equals(a.getWeight())) && (workflowId==null?a.getWorkflowId()==null:workflowId.equals(a.getWorkflowId()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (name!=null?name.hashCode():0) + (sendTo1!=null?sendTo1.hashCode():0) + (sendTo2!=null?sendTo2.hashCode():0) + (timelimit1!=null?timelimit1.hashCode():0) + (timelimit2!=null?timelimit2.hashCode():0) + (weight!=null?weight.hashCode():0) + (workflowId!=null?workflowId.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"name="+name+"|"+"sendTo1="+sendTo1+"|"+"sendTo2="+sendTo2+"|"+"timelimit1="+timelimit1+"|"+"timelimit2="+timelimit2+"|"+"weight="+weight+"|"+"workflowId="+workflowId;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBGedWorkflowStep mask = new DBGedWorkflowStep();
            mask.setId(id);
            DBGedWorkflowStep var = DBGedWorkflowStep.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                name = var.getName();
                sendTo1 = var.getSendTo1();
                sendTo2 = var.getSendTo2();
                timelimit1 = var.getTimelimit1();
                timelimit2 = var.getTimelimit2();
                weight = var.getWeight();
                workflowId = var.getWorkflowId();
            }
        }
    }

    public void initBean(DBGedWorkflowStep db)
    {
        this.id = db.getId();
        this.name = db.getName();
        this.sendTo1 = db.getSendTo1();
        this.sendTo2 = db.getSendTo2();
        this.timelimit1 = db.getTimelimit1();
        this.timelimit2 = db.getTimelimit2();
        this.weight = db.getWeight();
        this.workflowId = db.getWorkflowId();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSendTo1()
    {
        return sendTo1;
    }

    public void setSendTo1(String sendTo1)
    {
        this.sendTo1 = sendTo1;
    }

    public String getSendTo2()
    {
        return sendTo2;
    }

    public void setSendTo2(String sendTo2)
    {
        this.sendTo2 = sendTo2;
    }

    public String getTimelimit1()
    {
        return timelimit1;
    }

    public void setTimelimit1(String timelimit1)
    {
        this.timelimit1 = timelimit1;
    }

    public String getTimelimit2()
    {
        return timelimit2;
    }

    public void setTimelimit2(String timelimit2)
    {
        this.timelimit2 = timelimit2;
    }

    public String getWeight()
    {
        return weight;
    }

    public void setWeight(String weight)
    {
        this.weight = weight;
    }

    public String getWorkflowId()
    {
        return workflowId;
    }

    public void setWorkflowId(String workflowId)
    {
        this.workflowId = workflowId;
    }

    /**
     * @return a DBGedWorkflowStep table or null if nothing found or if an error occured
     **/
    public static DBGedWorkflowStep[] load(DBGedWorkflowStep mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBGedWorkflowStep table or null if nothing found or if an error occured
     **/
    public static DBGedWorkflowStep[] load(SQLConstraint sqlconstraint, DBGedWorkflowStep mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getSendTo1() != null) filter.set(SENDTO1, mask.getSendTo1());
            if(mask.getSendTo2() != null) filter.set(SENDTO2, mask.getSendTo2());
            if(mask.getTimelimit1() != null) filter.set(TIMELIMIT1, mask.getTimelimit1());
            if(mask.getTimelimit2() != null) filter.set(TIMELIMIT2, mask.getTimelimit2());
            if(mask.getWeight() != null) filter.set(WEIGHT, mask.getWeight());
            if(mask.getWorkflowId() != null) filter.set(WORKFLOWID, mask.getWorkflowId());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, NAME, SENDTO1, SENDTO2, TIMELIMIT1, TIMELIMIT2, WEIGHT, WORKFLOWID}, filter);
            if(pvp == null) return null;
            else
            {
                DBGedWorkflowStep[] result = new DBGedWorkflowStep[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBGedWorkflowStep();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setName(pvp[i].get(NAME));
                    result[i].setSendTo1(pvp[i].get(SENDTO1));
                    result[i].setSendTo2(pvp[i].get(SENDTO2));
                    result[i].setTimelimit1(pvp[i].get(TIMELIMIT1));
                    result[i].setTimelimit2(pvp[i].get(TIMELIMIT2));
                    result[i].setWeight(pvp[i].get(WEIGHT));
                    result[i].setWorkflowId(pvp[i].get(WORKFLOWID));
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
     * @return a DBGedWorkflowStep object or null if nothing found or if an error occured
     **/
    public static DBGedWorkflowStep loadByKey(DBGedWorkflowStep mask)
    {
        DBGedWorkflowStep[] res = DBGedWorkflowStep.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBGedWorkflowStep object or null if nothing found or if an error occured
     **/
    public static DBGedWorkflowStep loadByKey(DBGedWorkflowStep mask, SQLConstraint sqlconstraint)
    {
        DBGedWorkflowStep[] res = DBGedWorkflowStep.load(sqlconstraint, mask);
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
        toinsert.set(NAME,name);
        toinsert.set(SENDTO1,sendTo1);
        toinsert.set(SENDTO2,sendTo2);
        toinsert.set(TIMELIMIT1,timelimit1);
        toinsert.set(TIMELIMIT2,timelimit2);
        toinsert.set(WEIGHT,weight);
        toinsert.set(WORKFLOWID,workflowId);

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
    public static void delete(DBGedWorkflowStep mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getSendTo1() != null) filter.set(SENDTO1, mask.getSendTo1());
            if(mask.getSendTo2() != null) filter.set(SENDTO2, mask.getSendTo2());
            if(mask.getTimelimit1() != null) filter.set(TIMELIMIT1, mask.getTimelimit1());
            if(mask.getTimelimit2() != null) filter.set(TIMELIMIT2, mask.getTimelimit2());
            if(mask.getWeight() != null) filter.set(WEIGHT, mask.getWeight());
            if(mask.getWorkflowId() != null) filter.set(WORKFLOWID, mask.getWorkflowId());
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
    public static int count(DBGedWorkflowStep mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getSendTo1() != null) filter.set(SENDTO1, mask.getSendTo1());
            if(mask.getSendTo2() != null) filter.set(SENDTO2, mask.getSendTo2());
            if(mask.getTimelimit1() != null) filter.set(TIMELIMIT1, mask.getTimelimit1());
            if(mask.getTimelimit2() != null) filter.set(TIMELIMIT2, mask.getTimelimit2());
            if(mask.getWeight() != null) filter.set(WEIGHT, mask.getWeight());
            if(mask.getWorkflowId() != null) filter.set(WORKFLOWID, mask.getWorkflowId());
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
    public static double avg(String field, DBGedWorkflowStep mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getSendTo1() != null) filter.set(SENDTO1, mask.getSendTo1());
            if(mask.getSendTo2() != null) filter.set(SENDTO2, mask.getSendTo2());
            if(mask.getTimelimit1() != null) filter.set(TIMELIMIT1, mask.getTimelimit1());
            if(mask.getTimelimit2() != null) filter.set(TIMELIMIT2, mask.getTimelimit2());
            if(mask.getWeight() != null) filter.set(WEIGHT, mask.getWeight());
            if(mask.getWorkflowId() != null) filter.set(WORKFLOWID, mask.getWorkflowId());
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
    public static double min(String field, DBGedWorkflowStep mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getSendTo1() != null) filter.set(SENDTO1, mask.getSendTo1());
            if(mask.getSendTo2() != null) filter.set(SENDTO2, mask.getSendTo2());
            if(mask.getTimelimit1() != null) filter.set(TIMELIMIT1, mask.getTimelimit1());
            if(mask.getTimelimit2() != null) filter.set(TIMELIMIT2, mask.getTimelimit2());
            if(mask.getWeight() != null) filter.set(WEIGHT, mask.getWeight());
            if(mask.getWorkflowId() != null) filter.set(WORKFLOWID, mask.getWorkflowId());
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
    public static double max(String field, DBGedWorkflowStep mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getSendTo1() != null) filter.set(SENDTO1, mask.getSendTo1());
            if(mask.getSendTo2() != null) filter.set(SENDTO2, mask.getSendTo2());
            if(mask.getTimelimit1() != null) filter.set(TIMELIMIT1, mask.getTimelimit1());
            if(mask.getTimelimit2() != null) filter.set(TIMELIMIT2, mask.getTimelimit2());
            if(mask.getWeight() != null) filter.set(WEIGHT, mask.getWeight());
            if(mask.getWorkflowId() != null) filter.set(WORKFLOWID, mask.getWorkflowId());
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
    public static double std(String field, DBGedWorkflowStep mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getSendTo1() != null) filter.set(SENDTO1, mask.getSendTo1());
            if(mask.getSendTo2() != null) filter.set(SENDTO2, mask.getSendTo2());
            if(mask.getTimelimit1() != null) filter.set(TIMELIMIT1, mask.getTimelimit1());
            if(mask.getTimelimit2() != null) filter.set(TIMELIMIT2, mask.getTimelimit2());
            if(mask.getWeight() != null) filter.set(WEIGHT, mask.getWeight());
            if(mask.getWorkflowId() != null) filter.set(WORKFLOWID, mask.getWorkflowId());
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
    public static String[] distinct(String field, DBGedWorkflowStep mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getSendTo1() != null) filter.set(SENDTO1, mask.getSendTo1());
            if(mask.getSendTo2() != null) filter.set(SENDTO2, mask.getSendTo2());
            if(mask.getTimelimit1() != null) filter.set(TIMELIMIT1, mask.getTimelimit1());
            if(mask.getTimelimit2() != null) filter.set(TIMELIMIT2, mask.getTimelimit2());
            if(mask.getWeight() != null) filter.set(WEIGHT, mask.getWeight());
            if(mask.getWorkflowId() != null) filter.set(WORKFLOWID, mask.getWorkflowId());
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
