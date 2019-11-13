package dang.ged;

import java.util.ArrayList;

import openplatform.database.Database;
import openplatform.database.ParamValuePair;
import openplatform.database.SQLConstraint;
import openplatform.database.dbean.DBGedFiles;
import openplatform.database.dbean.DBGedFilesComment;
import openplatform.database.dbean.DBGedFilesVersion;
import openplatform.database.dbean.DBGedWorkflowProc;
import openplatform.database.dbean.DBGedWorkflowStep;
import openplatform.database.dbean.DBTrace;
import openplatform.database.dbean.DBUser;
import openplatform.database.dbean.DBUserGroups;
import openplatform.taglibs.CmsTrace;
import openplatform.tools.Debug;
import openplatform.tools.SQLTime;
import dang.cms.CmsLanguage;
import dang.cmsbase.Notificator;

/**
 * WDaemon.java
 *
 *
 * Created: Thu Apr 21 17:14:23 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class WDaemon 
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
	
	public static void main(String[] args)
		throws Exception
	{
		WDaemon d = new WDaemon();
		d.checkExpiredFiles();
	}
	
	
    public void checkExpiredFiles()
    	throws Exception
    {
        Debug.println(this, Debug.DEBUG, "checkExpiredFiles BEGIN");
                
		Database db = Database.getInstance();
		String query = db.getSpecificSQL("ExpiredGedFilesAllUsers");
		ArrayList res = db.loadSQL(query);
		
		if(res == null) return;
		
		int len = res.size();
		
		DBGedFiles gfmask = new DBGedFiles();
		
		for(int i=0; i<len; i++)
		{
			ParamValuePair pvp = (ParamValuePair)res.get(i);
			String id = pvp.get("gedfilesid");
			String groupId = pvp.get("fileExpiryGroup");
			String name = pvp.get("gfvname");
			
			if(id == null || groupId == null) continue;
			
			gfmask.clear();
			gfmask.setId(id);
			DBGedFiles gf = DBGedFiles.loadByKey(gfmask);
			notifyExpiredFile(groupId, gf);
			Debug.println(null, Debug.DEBUG, "notify group id:"+groupId+" for file:"+name);
		}
    }


    public void checkWorkflowProc()
    {
        Debug.println(this, Debug.DEBUG, "checkWorkflowProc BEGIN");

        DBGedWorkflowProc[] wp = DBGedWorkflowProc.load(null);
        
        if(wp == null) return;

        int len = wp.length;

        for(int i=0; i<len; i++)
            checkWorkflowProc(wp[i]);
    }


  


    /**
     * Create a new workflow
     */
    public void checkWorkflow(String workflowId, String fileId)
    {
        if(workflowId == null || fileId == null) return;
        
        DBGedWorkflowProc wpmask = new DBGedWorkflowProc();
        wpmask.setFileId(fileId);

        if(DBGedWorkflowProc.count(wpmask,null) == 0) // no workflow process, need to create one
        {
            SQLConstraint constr = new SQLConstraint();
            constr.setLimit(1);

            DBGedWorkflowStep wsmask = new DBGedWorkflowStep();
            wsmask.setWorkflowId(workflowId);
            DBGedWorkflowStep[] ws = DBGedWorkflowStep.load(constr, wsmask);
        
            if(ws == null) return;

            wpmask.setWorkflowStepId(ws[0].getId());
            wpmask.setStatus(DBGedWorkflowProc.STATUS_NEW);

            String time = SQLTime.getSQLTime(System.currentTimeMillis());
            
            wpmask.setBeginDate(time);
            wpmask.store();
        }
        else
            wpmask = DBGedWorkflowProc.loadByKey(wpmask);

        checkWorkflowProc(wpmask);
    }
    // PRIVATE //



    // notifyUser at the end of a wp.store(); (BUGFIX)
    private void checkWorkflowProc(DBGedWorkflowProc wp)
    {
        if(wp == null)
        {
            Debug.println(null, Debug.ERROR, "Can't checkWorkflowProc because it is null");
        }

        if(DBGedWorkflowProc.STATUS_APPROVED.equals(wp.getStatus()))
        {
            DBGedWorkflowStep wsmask = new DBGedWorkflowStep();
            wsmask.setId(wp.getWorkflowStepId());
            DBGedWorkflowStep s = DBGedWorkflowStep.loadByKey(wsmask);
                
            if(s == null) return;

            SQLConstraint constr = new SQLConstraint();
            constr.setOrderBy(DBGedWorkflowStep.WEIGHT);
            constr.setCustomWhere(DBGedWorkflowStep.WEIGHT
                                  +SQLConstraint.GREATER_THAN+s.getWeight());
            constr.setLimit(1);

            wsmask.clear();
            wsmask.setWorkflowId(s.getWorkflowId());

            DBGedWorkflowStep ws = DBGedWorkflowStep.loadByKey(wsmask, constr);

            if(ws != null)
            {
                wp.setWorkflowStepId(ws.getId());
                wp.setBeginDate(SQLTime.getSQLTime(System.currentTimeMillis()));
                wp.setStatus(DBGedWorkflowProc.STATUS_SENT);
                wp.store();

                notifyUser(ws.getSendTo1(), wp.getFileId());
            }
            else // WORKFLOW END
            {
                endWorkflow(wp);

                // Send a notification if necessary
                GroupNotif.notifyUpdated(wp.getFileId());
            }
        }
        else if(DBGedWorkflowProc.STATUS_DISAPPROVED.equals(wp.getStatus())
        		|| DBGedWorkflowProc.STATUS_EXPIRED.equals(wp.getStatus()))
        {
            endWorkflow(wp);
        }
        else if(DBGedWorkflowProc.STATUS_NEW.equals(wp.getStatus()))
        {
            DBGedWorkflowStep wsmask = new DBGedWorkflowStep();
            wsmask.setId(wp.getWorkflowStepId());

            DBGedWorkflowStep s = DBGedWorkflowStep.loadByKey(wsmask);

            if(s == null) return;

            wp.setBeginDate(SQLTime.getSQLTime(System.currentTimeMillis()));
            wp.setStatus(DBGedWorkflowProc.STATUS_SENT);
            wp.store();

            notifyUser(s.getSendTo1(), wp.getFileId());
        }
        else if(DBGedWorkflowProc.STATUS_SENT.equals(wp.getStatus()))
        {
            DBGedWorkflowStep wsmask = new DBGedWorkflowStep();
            wsmask.clear();
            wsmask.setId(wp.getWorkflowStepId());

            DBGedWorkflowStep s = DBGedWorkflowStep.loadByKey(wsmask);

            if(s == null) return;

            long maxtime = SQLTime.getJavaTime(wp.getBeginDate())+Long.parseLong(s.getTimelimit1())*24*60*60*1000;

            if(System.currentTimeMillis() > maxtime) // expired
            {
                if(s.getSendTo2() != null && s.getTimelimit2() != null)
                { 
                    wp.setBeginDate(SQLTime.getSQLTime(System.currentTimeMillis()));
                    wp.setStatus(DBGedWorkflowProc.STATUS_SENTTOBACKUP);
                    wp.store();

                    notifyUser(s.getSendTo2(), wp.getFileId());
                }
                else // WORKFLOW END
                {
                	wp.setStatus(DBGedWorkflowProc.STATUS_EXPIRED);
                    checkWorkflowProc(wp);
                }
            }
            else // remind it
            {
                notifyUser(s.getSendTo1(), wp.getFileId(), cmsLang.translate("workflow.reminder"));
            }
        }
        else if(DBGedWorkflowProc.STATUS_SENTTOBACKUP.equals(wp.getStatus()))
        {
            DBGedWorkflowStep wsmask = new DBGedWorkflowStep();
            wsmask.clear();
            wsmask.setId(wp.getWorkflowStepId());

            DBGedWorkflowStep s = DBGedWorkflowStep.loadByKey(wsmask);

            if(s == null) return;

            long maxtime = SQLTime.getJavaTime(wp.getBeginDate())+Long.parseLong(s.getTimelimit2())*24*60*60*1000;

            // WORKFLOW END
            if(System.currentTimeMillis() > maxtime) // expired
            {
                wp.setStatus(DBGedWorkflowProc.STATUS_EXPIRED);
                checkWorkflowProc(wp);                
            }
            else // remind it
            {
                notifyUser(s.getSendTo2(), wp.getFileId(), cmsLang.translate("workflow.reminder"));
            }
        }
        
    }


    private void notifyUser(String userId, String fileId)
    {
        notifyUser(userId, fileId, cmsLang.translate("workflow"));
    }

    private void notifyUser(String userId, String fileId, String subject)
    {
        if(userId == null || fileId == null) return;

        DBGedFiles fmask = new DBGedFiles();
        fmask.setId(fileId);
        DBGedFiles f = DBGedFiles.loadByKey(fmask);
        dang.ged.GedFiles gf = new dang.ged.GedFiles(f, userId);
        

        String message =
        	cmsLang.translate("hello")+", \n"
        	+ cmsLang.translate("workflow.review.need")+"\n"
            + "[url=/openplatform/cms/user/pc/modules/ged/browser.jsp?action=seeFile&fileId="
            + fileId+"]"+gf.getPathName()+"[/url]"+"\n"
            + cmsLang.translate("timeleft")+":"+gf.getWorkflowTimeleft();

        Notificator.send("1", userId, Notificator.PRIORITY_HIGH, subject, message);

        writeTrace("Workflow:"+gf.getPathName(), DBTrace.ACTION_NOTIFY, DBTrace.STATUSMSG_OK, userId, null);
    }


    private void endWorkflow(DBGedWorkflowProc wp)
    {
        if(wp == null) return;

        DBGedFilesVersion fvmask = new DBGedFilesVersion();
        fvmask.setFileId(wp.getFileId());
            
        SQLConstraint constr = new SQLConstraint();
        constr.setOrderBy(DBGedFilesVersion.VERSION);
        constr.setOrderByBiggestFirst(true);
        constr.setLimit(1);
            
        GedFilesVersion fv = new GedFilesVersion(DBGedFilesVersion.loadByKey(fvmask, constr)); // only one result

        // delete the last version if disapprove
        if(DBGedWorkflowProc.STATUS_DISAPPROVED.equals(wp.getStatus())
        		|| DBGedWorkflowProc.STATUS_EXPIRED.equals(wp.getStatus()))
        {           
            fv.delete();
        }

        wp.delete();
        

        String fileId = wp.getFileId();

        if(fileId == null) return;

        DBGedFiles fmask = new DBGedFiles();
        fmask.setId(fileId);

        DBGedFiles f = DBGedFiles.loadByKey(fmask);

        if(f == null) return;

        String message = 
        	cmsLang.translate("hello")+", \n"
        	+cmsLang.translate("workflow.ended")+" \n"
        	+cmsLang.translate("filename")+":"+fv.getPathName()+", "
        	+cmsLang.translate("workflow.status")+":"+cmsLang.translate(wp.getStatus());
        	
        	//"Hi, the workflow for the file "+fv.getPathName()+" ended with the status : "+wp.getStatus();

        // put the comments in the message
        DBGedFilesComment fcmask = new DBGedFilesComment();
        fcmask.setFileId(fileId);
        DBGedFilesComment[] fc = DBGedFilesComment.load(fcmask);
        if(fc != null)
        {
            message+="\n"+cmsLang.translate("comments")+" :";
            DBUser umask = new DBUser();
            int len = fc.length;
            for(int i=0; i<len; i++)
            {
                umask.setUserId(fc[i].getUserId());
                DBUser u = DBUser.loadByKey(umask);
                if(u != null)
                {
                    message+="\n";
                    message+=u.getLogin()+" - "+fc[i].getSubject()+" - "+fc[i].getText();
                }
                
                fc[i].delete();
            }
        }

        Notificator.send("1", fv.getAuthorId(), Notificator.PRIORITY_NORMAL, cmsLang.translate("workflow.ended"), message);

        writeTrace("Workflow:"+fv.getPathName(), DBTrace.ACTION_NOTIFY, DBTrace.STATUSMSG_OK,
                   fv.getAuthorId(), null);
                   

        // if there is no version
        if(DBGedFilesVersion.count(fvmask,null) == 0)
        {
            f.delete();
            return;
        }

        f.setLockBySyst("0"); // unlock the file
        f.store();
    }


    public void writeTrace (String fileName, String action, String statusMsg,
                                    String statusUserId, String statusGroupId)
    {
        try
        {
            CmsTrace cmstrace = new CmsTrace();
            cmstrace.setModuleId("2");
            cmstrace.setObject(fileName);
            cmstrace.setAction(action);
            cmstrace.setStatusMsg(statusMsg);
            cmstrace.setStatusUserId(statusUserId);
            cmstrace.setStatusGroupId(statusGroupId);
            cmstrace.doStartTag();
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }
    }



    private void notifyExpiredFile(String groupId, DBGedFiles f)
    {
        if(groupId == null || f == null) return;
        
        dang.ged.GedFiles gf = new dang.ged.GedFiles(f, "1");

        String message =
        	""
        	+ "[url=/openplatform/cms/user/pc/modules/ged/browser.jsp?action=modifyFile&fileId="
            + f.getId()
            + "]" + gf.getPathName()
            + "[/url]";

        String subject = cmsLang.translate("file.need.revised")+":"+gf.getName();

        DBUserGroups ugmask = new DBUserGroups();
        ugmask.setGroupId(groupId);
        DBUserGroups[] ug = DBUserGroups.load(ugmask);

        if(ug == null) return;

        int len = ug.length;
        for(int i=0; i<len; i++)
        {
            Notificator.send("1", ug[i].getUserId(), Notificator.PRIORITY_HIGH, subject, message);
        }

        writeTrace(gf.getPathName(), DBTrace.ACTION_NOTIFY, DBTrace.STATUSMSG_OK, 
                   null, groupId);
    }
}
