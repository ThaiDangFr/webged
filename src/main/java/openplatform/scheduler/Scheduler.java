package openplatform.scheduler;

import java.util.*;
import openplatform.tools.*;

/**
 * Scheduler.java
 *
 *
 * Created: Wed Jun  8 14:09:36 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class Scheduler 
{
    private static Timer timer = new Timer(true);
    private static long counter = 0;
    private static Hashtable taskList = new Hashtable();
    


    public static String everyDayAt(TimerTask task, int hour, int min)
    {
        Calendar cal = Calendar.getInstance();
        int actualmin = cal.get(Calendar.HOUR_OF_DAY)*60 + cal.get(Calendar.MINUTE);
        int schedulemin = hour*60+min;

        if(schedulemin >= actualmin)
        {
            long delay = (long)(schedulemin-actualmin)*60*1000;
            long period = 24*60*60*1000;

            timer.schedule(task, delay, period);
            taskList.put(Long.toString(counter), task);
            Debug.println(null, Debug.DEBUG, "Scheduler:delay="+delay+" period="+period+" task="+task.toString());
        }
        else
        {
            long delay = (long)(24*60-actualmin+schedulemin)*60*1000;
            long period = 24*60*60*1000;

            timer.schedule(task, delay, period);
            taskList.put(Long.toString(counter), task);
            Debug.println(null, Debug.DEBUG, "Scheduler:delay="+delay+" period="+period+" task="+task.toString());
        }
        
        counter++;
        return Long.toString(counter-1);
    }


    public static String everyDayAt(String sclass, String smethod, int hour, int min)
    {
        Task task = new Task(sclass, smethod);
        return everyDayAt(task, hour, min);
    }


    public static String every(TimerTask task, int secDelay, int secPeriod)
    {
        long ldelay = (long)secDelay*1000;
        long lperiod = (long)secPeriod*1000;

        timer.schedule(task, ldelay, lperiod);
        taskList.put(Long.toString(counter), task);
        Debug.println(null, Debug.DEBUG, "Scheduler:delay="+ldelay+" period="+lperiod+" task="+task.toString());

        counter++;
        return Long.toString(counter-1);
    }


    public static String every(String sclass, String smethod, int secDelay, int secPeriod)
    {
        Task task = new Task(sclass, smethod);
        return every(task, secDelay, secPeriod);
    }
    

    public static void stopAllTask()
    {
        Enumeration enum0 = taskList.keys();
        while(enum0.hasMoreElements())
        {
            TimerTask t = (TimerTask)taskList.remove(enum0.nextElement());
            t.cancel();
            t=null;
        }
    }

    public static void stopTask(String id)
    {
        TimerTask task = (TimerTask)taskList.remove(id);
        if(task != null)
        {
            task.cancel();
            task = null;
        }
    }



    public static void dumpTask()
    {
        Debug.println(null, Debug.DEBUG, "Scheduler: task list:");

        Enumeration enum0 = taskList.keys();
        while(enum0.hasMoreElements())
        {
            String id = (String)taskList.get(enum0.nextElement());
            TimerTask t = (TimerTask)taskList.get(id);
            Debug.println(null, Debug.DEBUG, t.toString());
        }
    }
}
