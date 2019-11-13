package openplatform.sms;

import java.util.*;
import openplatform.license.*;
import openplatform.tools.*;
import openplatform.scheduler.*;

/**
 * Sms.java
 *
 * SmsHttps wrapper
 *
 * Created: Sat Jun 25 17:24:17 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class Sms 
{
    private SmsHttps shttps = new SmsHttps();




    public void send(String from, String to, String message)
    {   
        if(from == null || to == null || message == null) return;

        if(message.length() > 140)
            message = message.substring(0, 133)+ " (...)";


        String user = LicenseState.getSmsUser();
        String pass = LicenseState.getSmsPass();
        String apiId = LicenseState.getSmsApiId();
        
        shttps.setUser(user);
        shttps.setPassword(pass);
        shttps.setApi_id(apiId);

        shttps.setFrom(from);
        shttps.setTo(to);
        shttps.setText(message);
        shttps.send();
    }

    /*
    public static void main(String[] args)
        throws Exception
    {
        //java.security.Security.addProvider(new com.sun.crypto.provider.SunJCE());
        LicenseState.parse();
        Sms sms = new Sms();
        //sms.send("WEBSNMP","33661798637",null);
        //sms.send("WEBSNMP","33661798637", "éàouô ne me rappelle pas et n'efface pas le message ;)");
        new SmsHttps().updateStatus();
    }
    */
}
