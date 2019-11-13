package openplatform.scheduler;

import java.lang.reflect.*;
import java.util.*;
import openplatform.tools.*;

/**
 * Task.java
 *
 *
 * Created: Wed Jun  8 14:15:21 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class Task extends TimerTask
{
    private Object object;
    private Method method;

    public Task(String sclass, String smethod)
    {
        try
        {
            Class cl = Class.forName(sclass);
            method = cl.getMethod(smethod ,null);
            
            object = cl.newInstance();
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }
 
    public boolean cancel()
    {
        Debug.println(this, Debug.DEBUG, "Task "+toString()+" stopped");
        return super.cancel();
    }

    public void run()
    {
        try
        {
            Debug.println(this, Debug.DEBUG, "Task "+toString()+" waking up !");
            method.invoke(object, null);
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }

    public String toString()
    {
        if(object == null || method == null) return "Unknown class name";
        else return object.getClass().getName()+"#"+method.getName();
    }
}
