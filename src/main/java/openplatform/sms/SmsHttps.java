package openplatform.sms;
import openplatform.http.*;
import java.util.*;
import openplatform.database.dbean.*;
import openplatform.tools.*;
import openplatform.database.*;
import openplatform.license.*;
import openplatform.regexp.*;

/**
 * SmsHttps.java
 *
 *
 * Created: Sat Jun 25 18:19:50 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class SmsHttps extends SmsCore
{
    private OpHttp http = new OpHttp();
    private HttpRequest req = new HttpRequest();

    public SmsHttps()
    {
        req.setURL("https://api.clickatell.com/http/sendmsg");
    }
    

    public void updateStatus()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(DBSms.STATUS).append(SQLConstraint.IS_NULL);
        SQLConstraint constr = new SQLConstraint();
        constr.setCustomWhere(sb.toString());

        DBSms[] sms = DBSms.load(constr, null);
        if(sms == null) return;

        int len = sms.length;
        Regexp ok = new Regexp("ID:(.+) Status:(.+)");
        Regexp err = new Regexp("ERR:(.+)");

        for(int i=0; i<len; i++)
        {
            HttpRequest reqstat = new HttpRequest();
            reqstat.setURL("https://api.clickatell.com/http/querymsg");
            reqstat.addParamValuePair("api_id",LicenseState.getSmsApiId());
            reqstat.addParamValuePair("user", LicenseState.getSmsUser());
            reqstat.addParamValuePair("password", LicenseState.getSmsPass()); 
            reqstat.addParamValuePair("apimsgid", sms[i].getApimsgid());

            HttpResponse resp = http.POST(reqstat);
            String sresp = resp.getDataAsString("UTF-8");

            if(ok.matches(sresp))
            {
                StringArrayList sal = ok.getMatchingResults();
                String status = sal.getString(2).trim();
                sms[i].setStatus(status);
                int st = Integer.parseInt(status);
                switch(st)
                {
                case 1: sms[i].setStatusMsg("Message unknown"); break;
                case 2: sms[i].setStatusMsg("Message queued"); break;
                case 3: sms[i].setStatusMsg("Delivered"); break;
                case 4: sms[i].setStatusMsg("Received by recipient"); break;
                case 5: sms[i].setStatusMsg("Error with message"); break;
                case 6: sms[i].setStatusMsg("User cancelled message delivery"); break;
                case 7: sms[i].setStatusMsg("Error delivering message"); break;
                case 8: sms[i].setStatusMsg("OK"); break;
                case 9: sms[i].setStatusMsg("Routing error"); break;
                case 10: sms[i].setStatusMsg("Message expired"); break;
                case 11: sms[i].setStatusMsg("Message queued for later delivery"); break;
                case 12: sms[i].setStatusMsg("out of credit"); break;
                }

                sms[i].store();
            }
            else if(err.matches(sresp))
            {
                StringArrayList sal = err.getMatchingResults();
                sms[i].setErr(sal.getString(1).trim());
                sms[i].setStatus("0");
                sms[i].store();
            }
        }
    }



    /**
     * @return ArrayList of SmsResponse
     */
    public void send()
    {
        if(!LicenseState.isSmsServiceOpened())
        {
            Debug.println(this, Debug.WARNING, "SMS Service is not opened");
            return;
        }

        if(req_feat != null)
            req.addParamValuePair("req_feat", req_feat);

        if(api_id != null)
            req.addParamValuePair("api_id", api_id);

        if(callback != null)
            req.addParamValuePair("callback", callback);

        if(concat != null)
            req.addParamValuePair("concat", concat);

        if(deliv_ack != null)
            req.addParamValuePair("deliv_ack", deliv_ack);

        if(deliv_time != null)
            req.addParamValuePair("deliv_time", deliv_time);

        if(escalate != null)
            req.addParamValuePair("escalate", escalate);

        if(from != null)
            req.addParamValuePair("from", from);

        if(max_credits != null)
            req.addParamValuePair("max_credits", max_credits);

        if(msg_type != null)
            req.addParamValuePair("msg_type", msg_type);

        if(password != null)
            req.addParamValuePair("password", password);

        if(queue != null)
            req.addParamValuePair("queue", queue);

        if(session_id != null)
            req.addParamValuePair("session_id", session_id);

        if(text != null)
            req.addParamValuePair("text", text);

        if(data != null)
            req.addParamValuePair("data", data);

        if(to != null)
            req.addParamValuePair("to", to);

        if(udh != null)
            req.addParamValuePair("udh", udh);

        if(unicode != null)
            req.addParamValuePair("unicode", unicode);

        if(user != null)
            req.addParamValuePair("user", user);

        if(validity != null)
            req.addParamValuePair("validity", validity);

         if(mo != null)
            req.addParamValuePair("mo", mo); 

         HttpResponse resp = http.POST(req);
         String sresp = resp.getDataAsString("UTF-8");

         DBSms sms = new DBSms();
         sms.setDate(SQLTime.getSQLTime(System.currentTimeMillis()));
         sms.setFromTel(this.from);
         sms.setToTel(this.to);
         sms.store();

         processResponse(sresp, sms);
    }
}
