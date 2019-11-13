package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBSms
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBSms
{
    protected boolean inError = false;
    protected String id;
    protected String date;
    protected String fromTel;
    protected String toTel;
    protected String err;
    protected String apimsgid;
    protected String status;
    protected String statusMsg;

    public static String ID = "id";
    public static String DATE = "date";
    public static String FROMTEL = "fromTel";
    public static String TOTEL = "toTel";
    public static String ERR = "err";
    public static String APIMSGID = "apimsgid";
    public static String STATUS = "status";
    public static String STATUSMSG = "statusMsg";
    public static String TABLE = "Sms";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        date = null;
        fromTel = null;
        toTel = null;
        err = null;
        apimsgid = null;
        status = null;
        statusMsg = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBSms a = (DBSms)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (date==null?a.getDate()==null:date.equals(a.getDate())) && (fromTel==null?a.getFromTel()==null:fromTel.equals(a.getFromTel())) && (toTel==null?a.getToTel()==null:toTel.equals(a.getToTel())) && (err==null?a.getErr()==null:err.equals(a.getErr())) && (apimsgid==null?a.getApimsgid()==null:apimsgid.equals(a.getApimsgid())) && (status==null?a.getStatus()==null:status.equals(a.getStatus())) && (statusMsg==null?a.getStatusMsg()==null:statusMsg.equals(a.getStatusMsg()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (date!=null?date.hashCode():0) + (fromTel!=null?fromTel.hashCode():0) + (toTel!=null?toTel.hashCode():0) + (err!=null?err.hashCode():0) + (apimsgid!=null?apimsgid.hashCode():0) + (status!=null?status.hashCode():0) + (statusMsg!=null?statusMsg.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"date="+date+"|"+"fromTel="+fromTel+"|"+"toTel="+toTel+"|"+"err="+err+"|"+"apimsgid="+apimsgid+"|"+"status="+status+"|"+"statusMsg="+statusMsg;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBSms mask = new DBSms();
            mask.setId(id);
            DBSms var = DBSms.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                date = var.getDate();
                fromTel = var.getFromTel();
                toTel = var.getToTel();
                err = var.getErr();
                apimsgid = var.getApimsgid();
                status = var.getStatus();
                statusMsg = var.getStatusMsg();
            }
        }
    }

    public void initBean(DBSms db)
    {
        this.id = db.getId();
        this.date = db.getDate();
        this.fromTel = db.getFromTel();
        this.toTel = db.getToTel();
        this.err = db.getErr();
        this.apimsgid = db.getApimsgid();
        this.status = db.getStatus();
        this.statusMsg = db.getStatusMsg();
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

    public String getFromTel()
    {
        return fromTel;
    }

    public void setFromTel(String fromTel)
    {
        this.fromTel = fromTel;
    }

    public String getToTel()
    {
        return toTel;
    }

    public void setToTel(String toTel)
    {
        this.toTel = toTel;
    }

    public String getErr()
    {
        return err;
    }

    public void setErr(String err)
    {
        this.err = err;
    }

    public String getApimsgid()
    {
        return apimsgid;
    }

    public void setApimsgid(String apimsgid)
    {
        this.apimsgid = apimsgid;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatusMsg()
    {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg)
    {
        this.statusMsg = statusMsg;
    }

    /**
     * @return a DBSms table or null if nothing found or if an error occured
     **/
    public static DBSms[] load(DBSms mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBSms table or null if nothing found or if an error occured
     **/
    public static DBSms[] load(SQLConstraint sqlconstraint, DBSms mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getFromTel() != null) filter.set(FROMTEL, mask.getFromTel());
            if(mask.getToTel() != null) filter.set(TOTEL, mask.getToTel());
            if(mask.getErr() != null) filter.set(ERR, mask.getErr());
            if(mask.getApimsgid() != null) filter.set(APIMSGID, mask.getApimsgid());
            if(mask.getStatus() != null) filter.set(STATUS, mask.getStatus());
            if(mask.getStatusMsg() != null) filter.set(STATUSMSG, mask.getStatusMsg());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, DATE, FROMTEL, TOTEL, ERR, APIMSGID, STATUS, STATUSMSG}, filter);
            if(pvp == null) return null;
            else
            {
                DBSms[] result = new DBSms[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBSms();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setDate(pvp[i].get(DATE));
                    result[i].setFromTel(pvp[i].get(FROMTEL));
                    result[i].setToTel(pvp[i].get(TOTEL));
                    result[i].setErr(pvp[i].get(ERR));
                    result[i].setApimsgid(pvp[i].get(APIMSGID));
                    result[i].setStatus(pvp[i].get(STATUS));
                    result[i].setStatusMsg(pvp[i].get(STATUSMSG));
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
     * @return a DBSms object or null if nothing found or if an error occured
     **/
    public static DBSms loadByKey(DBSms mask)
    {
        DBSms[] res = DBSms.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBSms object or null if nothing found or if an error occured
     **/
    public static DBSms loadByKey(DBSms mask, SQLConstraint sqlconstraint)
    {
        DBSms[] res = DBSms.load(sqlconstraint, mask);
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
        toinsert.set(FROMTEL,fromTel);
        toinsert.set(TOTEL,toTel);
        toinsert.set(ERR,err);
        toinsert.set(APIMSGID,apimsgid);
        toinsert.set(STATUS,status);
        toinsert.set(STATUSMSG,statusMsg);

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
    public static void delete(DBSms mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getFromTel() != null) filter.set(FROMTEL, mask.getFromTel());
            if(mask.getToTel() != null) filter.set(TOTEL, mask.getToTel());
            if(mask.getErr() != null) filter.set(ERR, mask.getErr());
            if(mask.getApimsgid() != null) filter.set(APIMSGID, mask.getApimsgid());
            if(mask.getStatus() != null) filter.set(STATUS, mask.getStatus());
            if(mask.getStatusMsg() != null) filter.set(STATUSMSG, mask.getStatusMsg());
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
    public static int count(DBSms mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getFromTel() != null) filter.set(FROMTEL, mask.getFromTel());
            if(mask.getToTel() != null) filter.set(TOTEL, mask.getToTel());
            if(mask.getErr() != null) filter.set(ERR, mask.getErr());
            if(mask.getApimsgid() != null) filter.set(APIMSGID, mask.getApimsgid());
            if(mask.getStatus() != null) filter.set(STATUS, mask.getStatus());
            if(mask.getStatusMsg() != null) filter.set(STATUSMSG, mask.getStatusMsg());
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
    public static double avg(String field, DBSms mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getFromTel() != null) filter.set(FROMTEL, mask.getFromTel());
            if(mask.getToTel() != null) filter.set(TOTEL, mask.getToTel());
            if(mask.getErr() != null) filter.set(ERR, mask.getErr());
            if(mask.getApimsgid() != null) filter.set(APIMSGID, mask.getApimsgid());
            if(mask.getStatus() != null) filter.set(STATUS, mask.getStatus());
            if(mask.getStatusMsg() != null) filter.set(STATUSMSG, mask.getStatusMsg());
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
    public static double min(String field, DBSms mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getFromTel() != null) filter.set(FROMTEL, mask.getFromTel());
            if(mask.getToTel() != null) filter.set(TOTEL, mask.getToTel());
            if(mask.getErr() != null) filter.set(ERR, mask.getErr());
            if(mask.getApimsgid() != null) filter.set(APIMSGID, mask.getApimsgid());
            if(mask.getStatus() != null) filter.set(STATUS, mask.getStatus());
            if(mask.getStatusMsg() != null) filter.set(STATUSMSG, mask.getStatusMsg());
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
    public static double max(String field, DBSms mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getFromTel() != null) filter.set(FROMTEL, mask.getFromTel());
            if(mask.getToTel() != null) filter.set(TOTEL, mask.getToTel());
            if(mask.getErr() != null) filter.set(ERR, mask.getErr());
            if(mask.getApimsgid() != null) filter.set(APIMSGID, mask.getApimsgid());
            if(mask.getStatus() != null) filter.set(STATUS, mask.getStatus());
            if(mask.getStatusMsg() != null) filter.set(STATUSMSG, mask.getStatusMsg());
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
    public static double std(String field, DBSms mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getFromTel() != null) filter.set(FROMTEL, mask.getFromTel());
            if(mask.getToTel() != null) filter.set(TOTEL, mask.getToTel());
            if(mask.getErr() != null) filter.set(ERR, mask.getErr());
            if(mask.getApimsgid() != null) filter.set(APIMSGID, mask.getApimsgid());
            if(mask.getStatus() != null) filter.set(STATUS, mask.getStatus());
            if(mask.getStatusMsg() != null) filter.set(STATUSMSG, mask.getStatusMsg());
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
    public static String[] distinct(String field, DBSms mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getFromTel() != null) filter.set(FROMTEL, mask.getFromTel());
            if(mask.getToTel() != null) filter.set(TOTEL, mask.getToTel());
            if(mask.getErr() != null) filter.set(ERR, mask.getErr());
            if(mask.getApimsgid() != null) filter.set(APIMSGID, mask.getApimsgid());
            if(mask.getStatus() != null) filter.set(STATUS, mask.getStatus());
            if(mask.getStatusMsg() != null) filter.set(STATUSMSG, mask.getStatusMsg());
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
