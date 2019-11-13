package dang.ged;

import openplatform.database.dbean.*;
import java.util.*;
import openplatform.tools.*;

/**
 * GedWorkflowStep.java
 *
 *
 * Created: Sun Apr 10 20:40:05 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class GedWorkflowStep extends DBGedWorkflowStep
{
    private static HashMap timelimit1Option = new OrdHashMap();
    private HashMap userMap = new OrdHashMap();

    static
    {
        for(int i=1;i<366;i++)
            timelimit1Option.put(Integer.toString(i), Integer.toString(i));
    }


    private void contructUserMap()
    {
        // Fill the user
        DBUser[] user = DBUser.load(null);
        if(user != null)
        {
            int len = user.length;
            userMap.clear();
            userMap.put("-1", "-----");
            
            for(int i=0; i<len; i++)
                userMap.put(user[i].getUserId(), user[i].getLogin());
        }
    }


    public GedWorkflowStep()
    {
        super();
        contructUserMap();

    }
 
    public GedWorkflowStep(DBGedWorkflowStep step)
    {
        super();
        initBean(step);
        contructUserMap();
    }

    
    public String getSendTo1Login()
    {
        if(this.sendTo1 == null) return null;

        DBUser umask = new DBUser();
        umask.setUserId(this.sendTo1);
        DBUser u = DBUser.loadByKey(umask);
        
        if(u == null) return null;
        else return u.getLogin();
    }

    public String getSendTo2Login()
    {
        if(this.sendTo2 == null) return null;

        DBUser umask = new DBUser();
        umask.setUserId(this.sendTo2);
        DBUser u = DBUser.loadByKey(umask);
        
        if(u == null) return null;
        else return u.getLogin();
    }

    public String getWorkflowName()
    {
        DBGedWorkflow wmask = new DBGedWorkflow();
        wmask.setId(this.workflowId);
        DBGedWorkflow w = DBGedWorkflow.loadByKey(wmask);

        if(w == null) return null;
        else return w.getName();
    }


    public final HashMap getSendTo1Option()
    {
        return userMap;
    }

    public final HashMap getSendTo2Option()
    {
        return userMap;
    }

    public static final HashMap getTimelimit1Option()
    {
        return GedWorkflowStep.timelimit1Option;
    }  

    public static final HashMap getTimelimit2Option()
    {
        return GedWorkflowStep.timelimit1Option;
    }
}
