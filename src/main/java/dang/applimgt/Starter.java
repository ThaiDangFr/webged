package dang.applimgt;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import openplatform.database.Database;
import openplatform.file.Repository;
import openplatform.scheduler.Scheduler;
import openplatform.tools.Debug;
import dang.ged.WDaemon;


/**
 * Class Starter
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Starter extends GenericServlet
{

    public void init(ServletConfig config)
        throws ServletException
    {
        super.init(config);

        try
        {
            Debug.println(this, Debug.DEBUG, System.getProperties());

            Repository.init();
            
            //Locale.setDefault(Locale.ENGLISH);

            // web snmp
            /*
            SnmpFactory.instanciate();
            SnmpScheduler.instanciate();
            SnmpFormulaFactory.instanciate();
            ReportDaemonMan.startAllTask();
            Scheduler.every(new Watchdog(), (int)SnmpScheduler.FREQUENCE,  (int)SnmpScheduler.FREQUENCE * 8);
            */
            // web snmp


            // ged workflow
            WDaemon wd = new WDaemon();
            wd.checkWorkflowProc();
            wd.checkExpiredFiles();
            Scheduler.everyDayAt("dang.ged.WDaemon",               "checkWorkflowProc", 23,     30);
            Scheduler.everyDayAt("dang.ged.WDaemon",               "checkExpiredFiles", 23,     45);
            //

            Scheduler.every("openplatform.sms.SmsHttps",           "updateStatus",      5*60,   5*60);
            Scheduler.every("openplatform.file.TempFileDaemon",    "checkExpiry",       60,     12*60*60);
            Scheduler.every("dang.ged.TrashUtils",                 "checkExpiredTrash", 60,     24*60*60);
            Scheduler.every("openplatform.license.LicenseState",   "parse",             0,      5*60);


            Debug.println(this, Debug.DEBUG,
                          "\n"
                          +"==============\n"
                          +"SERVER STARTED\n"
                          +"==============");
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
    }

    public void destroy()
    {
        try
        {
        	/*
            SnmpFactory.stopProcess();
            */
            Scheduler.stopAllTask();
            Database.getInstance().stopDatabase();
            
            Debug.println(this, Debug.DEBUG,
                          "\n"
                          +"==============\n"
                          +"SERVER STOPPED\n"
                          +"==============");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void service(ServletRequest request, ServletResponse response)
        throws ServletException, IOException
    {
    }
}
