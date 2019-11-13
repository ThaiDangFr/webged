package dang.ged;

import openplatform.database.dbean.*;

/**
 * GedWorkflowStepExt.java
 *
 *
 * Created: Wed Jul 13 17:01:22 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class GedWorkflowStepExt extends GedWorkflowStep
{
    private String fileId;

    public GedWorkflowStepExt(GedWorkflowStep ws, String fileId)
    {
        super();
        initBean(ws);
        this.fileId = fileId;
    }
    
    
    public String getCurrentSendToLogin()
    {
        if(this.fileId == null) return null;

        DBGedWorkflowProc wpmask = new DBGedWorkflowProc();
        wpmask.setFileId(this.fileId);

        DBGedWorkflowProc wp = DBGedWorkflowProc.loadByKey(wpmask);
        
        if(wp == null) return null;

        if(DBGedWorkflowProc.STATUS_SENT.equals(wp.getStatus())) return getSendTo1Login();
        else if(DBGedWorkflowProc.STATUS_SENTTOBACKUP.equals(wp.getStatus())) return getSendTo2Login();
        else return null;
    }
    
    
    public String getCurrentTimeLimit()
    {
        if(this.fileId == null) return null;

        DBGedWorkflowProc wpmask = new DBGedWorkflowProc();
        wpmask.setFileId(this.fileId);

        DBGedWorkflowProc wp = DBGedWorkflowProc.loadByKey(wpmask);
        
        if(wp == null) return null;

        if(DBGedWorkflowProc.STATUS_SENT.equals(wp.getStatus())) return getTimelimit1();
        else if(DBGedWorkflowProc.STATUS_SENTTOBACKUP.equals(wp.getStatus())) return getTimelimit2();
        else return null;
    }
}
