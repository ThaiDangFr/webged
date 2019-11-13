package dang.websnmp;

import openplatform.database.dbean.*;
import openplatform.tools.*;
import java.text.*;
import java.util.*;

/**
 * SnmpAlert.java
 *
 *
 * Created: Tue Jun 21 14:38:27 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class SnmpAlert extends DBSnmpAlert
{
    public SnmpAlert(DBSnmpAlert sa)
    {
        initBean(sa);
    }
    

    public boolean isPollingType()
    {
        if(DBSnmpAlert.TYPE_POLLING.equals(this.type)) return true;
        else return false;
    }


    public String getBeginTime()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
        long d = SQLTime.getJavaTime(this.date)-10*60*1000;
        StringBuffer sb = formatter.format(new Date(d), new StringBuffer(), new FieldPosition(0));
        return sb.toString();
    }

    public String getEndTime()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
        long d = SQLTime.getJavaTime(this.date)+10*60*1000;
        StringBuffer sb = formatter.format(new Date(d), new StringBuffer(), new FieldPosition(0));
        return sb.toString();
    }
    

    public String getHostName()
    {
        if(this.reportId == null) return this.source;

        DBSnmpReport rmask = new DBSnmpReport();
        rmask.setReportId(this.reportId);
        DBSnmpReport r = DBSnmpReport.loadByKey(rmask);

        if(r == null) return this.source;

        DBSnmpHost hmask = new DBSnmpHost();
        hmask.setSnmpHostId(r.getHostId());
        DBSnmpHost h = DBSnmpHost.loadByKey(hmask);

        if(h == null) return this.source;

        return h.getDisplayName();
    }
}
