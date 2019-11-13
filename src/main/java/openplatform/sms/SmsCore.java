package openplatform.sms;

import java.io.*;
import openplatform.regexp.*;
import openplatform.tools.*;
import java.util.*;
import openplatform.database.dbean.*;
import openplatform.license.*;



/**
 * SmsCore.java
 *
<pre>
Field status in DBSms :
0 - general error
001 Message unknown The delivering network did not recognise the message type or content.
002 Message queued The message could not be delivered and has been queued for attempted redelivery.
003 Delivered Delivered to the network or gateway (delivered to the recipient).
004 Received by recipient Confirmation of receipt on the handset of the recipient.
005 Error with message There was an error with the message, probably caused by the content of the message itself.
006 User cancelled message delivery Client cancelled the message by setting the validity period, or the message was terminated by an internal mechanism.
007 Error delivering message An error occurred delivering the message to the handset.
008 OK Message received by gateway.
009 Routing error The routing gateway or network has had an error routing the message.
010 Message expired Message has expired at the network due to the handset being off, or out of reach.
011 Message queued for later delivery Message has been queued at the Clickatell gateway for delivery at a later time (delayed delivery).
012 Out of credit The message cannot be delivered due to a lack of funds in your account. Please re-purchase credits.
</pre>
 * API to converse with http://www.clickatell.com service
 *
 * Created: Sat Jun 25 18:09:22 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public abstract class SmsCore 
{
    protected String req_feat;
    protected String api_id;
    protected String callback;
    protected String concat;
    protected String deliv_ack;
    protected String deliv_time;
    protected String escalate;
    protected String from;
    protected String max_credits;
    protected String msg_type;
    protected String password;
    protected String queue;
    protected String session_id;
    protected String text;
    protected String data;
    protected String to;
    protected String udh;
    protected String unicode;
    protected String user;
    protected String validity;
    protected String mo;
    
    public abstract void send();

    
    

    /**
     * ArrayList of SmsResponse
     */
    protected void processResponse(String message, DBSms sms)
    {
        try
        {
            BufferedReader br = new BufferedReader(new StringReader(message));
            String line;

//             Regexp ok1 = new Regexp("ID:(.+) To:(.+)");
            Regexp ok2 = new Regexp("ID:(.+)");
        
//             Regexp err1 = new Regexp("ERR:(.+) To:(.+)");
            Regexp err2 = new Regexp("ERR:(.+),(.+)");   

//             ArrayList array = new ArrayList();
            while((line = br.readLine()) != null)
            {
//                 if(ok1.matches(line))
//                 {
//                     StringArrayList sal = ok1.getMatchingResults();
//                     SmsResponseOk rok = new SmsResponseOk();
//                     rok.setApimsgid(sal.getString(1).trim());
//                     rok.setTo(sal.getString(2).trim());
//                     array.add(rok);
//                 }
//                 else
                if(ok2.matches(line))
                {
                    StringArrayList sal = ok2.getMatchingResults();
                    sms.setApimsgid(sal.getString(1).trim());
                    sms.store();
//                     SmsResponseOk rok = new SmsResponseOk();
//                     rok.setApimsgid(sal.getString(1).trim());
//                     array.add(rok);
                }
//                 else if(err1.matches(line))
//                 {
//                     StringArrayList sal = err1.getMatchingResults();
//                     SmsResponseErr err = new SmsResponseErr();
//                     err.setErrNumber(Integer.parseInt(sal.getString(1).trim()));
//                     err.setTo(sal.getString(2).trim());
//                     array.add(err);
//                 }
                else if(err2.matches(line))
                {
                    StringArrayList sal = err2.getMatchingResults();
                    sms.setErr(sal.getString(1).trim());
                    sms.setStatus("0");
                    sms.setStatusMsg(sal.getString(2).trim());
                    sms.store();
//                     SmsResponseErr err = new SmsResponseErr();
//                     err.setErrNumber(Integer.parseInt(sal.getString(1).trim()));
//                     err.setErrMessage(sal.getString(2).trim());
//                     array.add(err);
                }
            }
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }


    // GET / SET

    /**
     * Gets the value of req_feat
     *
     * @return the value of req_feat
     */
    public final String getReq_feat()
    {
        return this.req_feat;
    }

    /**
     * Sets the value of req_feat
     *
     * @param argReq_feat Value to assign to this.req_feat
     */
    public final void setReq_feat(final String argReq_feat)
    {
        this.req_feat = argReq_feat;
    }

    /**
     * Gets the value of api_id
     *
     * @return the value of api_id
     */
    public final String getApi_id()
    {
        return this.api_id;
    }

    /**
     * Sets the value of api_id
     *
     * @param argApi_id Value to assign to this.api_id
     */
    public final void setApi_id(final String argApi_id)
    {
        this.api_id = argApi_id;
    }

    /**
     * Gets the value of callback
     *
     * @return the value of callback
     */
    public final String getCallback()
    {
        return this.callback;
    }

    /**
     * Sets the value of callback
     *
     * @param argCallback Value to assign to this.callback
     */
    public final void setCallback(final String argCallback)
    {
        this.callback = argCallback;
    }

    /**
     * Gets the value of concat
     *
     * @return the value of concat
     */
    public final String getConcat()
    {
        return this.concat;
    }

    /**
     * Sets the value of concat
     *
     * @param argConcat Value to assign to this.concat
     */
    public final void setConcat(final String argConcat)
    {
        this.concat = argConcat;
    }

    /**
     * Gets the value of deliv_ack
     *
     * @return the value of deliv_ack
     */
    public final String getDeliv_ack()
    {
        return this.deliv_ack;
    }

    /**
     * Sets the value of deliv_ack
     *
     * @param argDeliv_ack Value to assign to this.deliv_ack
     */
    public final void setDeliv_ack(final String argDeliv_ack)
    {
        this.deliv_ack = argDeliv_ack;
    }

    /**
     * Gets the value of deliv_time
     *
     * @return the value of deliv_time
     */
    public final String getDeliv_time()
    {
        return this.deliv_time;
    }

    /**
     * Sets the value of deliv_time
     *
     * @param argDeliv_time Value to assign to this.deliv_time
     */
    public final void setDeliv_time(final String argDeliv_time)
    {
        this.deliv_time = argDeliv_time;
    }

    /**
     * Gets the value of escalate
     *
     * @return the value of escalate
     */
    public final String getEscalate()
    {
        return this.escalate;
    }

    /**
     * Sets the value of escalate
     *
     * @param argEscalate Value to assign to this.escalate
     */
    public final void setEscalate(final String argEscalate)
    {
        this.escalate = argEscalate;
    }

    /**
     * Gets the value of from
     *
     * @return the value of from
     */
    public final String getFrom()
    {
        return this.from;
    }

    /**
     * Sets the value of from
     *
     * @param argFrom Value to assign to this.from
     */
    public final void setFrom(final String argFrom)
    {
        this.from = argFrom;
    }

    /**
     * Gets the value of max_credits
     *
     * @return the value of max_credits
     */
    public final String getMax_credits()
    {
        return this.max_credits;
    }

    /**
     * Sets the value of max_credits
     *
     * @param argMax_credits Value to assign to this.max_credits
     */
    public final void setMax_credits(final String argMax_credits)
    {
        this.max_credits = argMax_credits;
    }

    /**
     * Gets the value of msg_type
     *
     * @return the value of msg_type
     */
    public final String getMsg_type()
    {
        return this.msg_type;
    }

    /**
     * Sets the value of msg_type
     *
     * @param argMsg_type Value to assign to this.msg_type
     */
    public final void setMsg_type(final String argMsg_type)
    {
        this.msg_type = argMsg_type;
    }

    /**
     * Gets the value of password
     *
     * @return the value of password
     */
    public final String getPassword()
    {
        return this.password;
    }

    /**
     * Sets the value of password
     *
     * @param argPassword Value to assign to this.password
     */
    public final void setPassword(final String argPassword)
    {
        this.password = argPassword;
    }

    /**
     * Gets the value of queue
     *
     * @return the value of queue
     */
    public final String getQueue()
    {
        return this.queue;
    }

    /**
     * Sets the value of queue
     *
     * @param argQueue Value to assign to this.queue
     */
    public final void setQueue(final String argQueue)
    {
        this.queue = argQueue;
    }

    /**
     * Gets the value of session_id
     *
     * @return the value of session_id
     */
    public final String getSession_id()
    {
        return this.session_id;
    }

    /**
     * Sets the value of session_id
     *
     * @param argSession_id Value to assign to this.session_id
     */
    public final void setSession_id(final String argSession_id)
    {
        this.session_id = argSession_id;
    }

    /**
     * Gets the value of text
     *
     * @return the value of text
     */
    public final String getText()
    {
        return this.text;
    }

    /**
     * Sets the value of text
     *
     * @param argText Value to assign to this.text
     */
    public final void setText(final String argText)
    {
        this.text = argText;
    }

    /**
     * Gets the value of data
     *
     * @return the value of data
     */
    public final String getData()
    {
        return this.data;
    }

    /**
     * Sets the value of data
     *
     * @param argData Value to assign to this.data
     */
    public final void setData(final String argData)
    {
        this.data = argData;
    }

    /**
     * Gets the value of to
     *
     * @return the value of to
     */
    public final String getTo()
    {
        return this.to;
    }

    /**
     * Sets the value of to
     *
     * @param argTo Value to assign to this.to
     */
    public final void setTo(final String argTo)
    {
        this.to = argTo;
    }

    /**
     * Gets the value of udh
     *
     * @return the value of udh
     */
    public final String getUdh()
    {
        return this.udh;
    }

    /**
     * Sets the value of udh
     *
     * @param argUdh Value to assign to this.udh
     */
    public final void setUdh(final String argUdh)
    {
        this.udh = argUdh;
    }

    /**
     * Gets the value of unicode
     *
     * @return the value of unicode
     */
    public final String getUnicode()
    {
        return this.unicode;
    }

    /**
     * Sets the value of unicode
     *
     * @param argUnicode Value to assign to this.unicode
     */
    public final void setUnicode(final String argUnicode)
    {
        this.unicode = argUnicode;
    }

    /**
     * Gets the value of user
     *
     * @return the value of user
     */
    public final String getUser()
    {
        return this.user;
    }

    /**
     * Sets the value of user
     *
     * @param argUser Value to assign to this.user
     */
    public final void setUser(final String argUser)
    {
        this.user = argUser;
    }

    /**
     * Gets the value of validity
     *
     * @return the value of validity
     */
    public final String getValidity()
    {
        return this.validity;
    }

    /**
     * Sets the value of validity
     *
     * @param argValidity Value to assign to this.validity
     */
    public final void setValidity(final String argValidity)
    {
        this.validity = argValidity;
    }

    /**
     * Gets the value of mo
     *
     * @return the value of mo
     */
    public final String getMo()
    {
        return this.mo;
    }

    /**
     * Sets the value of mo
     *
     * @param argMo Value to assign to this.mo
     */
    public final void setMo(final String argMo)
    {
        this.mo = argMo;
    }

}
