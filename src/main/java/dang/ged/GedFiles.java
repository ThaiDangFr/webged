package dang.ged;

import java.util.ArrayList;

import openplatform.database.Database;
import openplatform.database.ParamValuePair;
import openplatform.database.SQLConstraint;
import openplatform.database.dbean.DBGedDirectory;
import openplatform.database.dbean.DBGedFiles;
import openplatform.database.dbean.DBGedFilesComment;
import openplatform.database.dbean.DBGedFilesDescr;
import openplatform.database.dbean.DBGedFilesVersion;
import openplatform.database.dbean.DBGedTemplateItem;
import openplatform.database.dbean.DBGedWorkflowProc;
import openplatform.database.dbean.DBGedWorkflowStep;
import openplatform.database.dbean.DBUser;
import openplatform.database.dbean.DBUserGroups;
import openplatform.database.dbeanext.MimeTypes;
import openplatform.document.FormatConverter;
import openplatform.tools.Debug;
import openplatform.tools.SQLTime;
import openplatform.tools.TimeConverter;


/**
 * GedFiles.java
 *
 *
 * Created: Fri Apr 15 16:20:32 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class GedFiles extends DBGedFiles
{
    public DBGedFilesVersion fvmask = new DBGedFilesVersion();
    public DBGedWorkflowProc wpmask = new DBGedWorkflowProc();
    public DBGedWorkflowStep wsmask = new DBGedWorkflowStep();
    public DBUserGroups ugmask = new DBUserGroups();
    public DBGedDirectory dmask = new DBGedDirectory();
    public DBGedTemplateItem timask = new DBGedTemplateItem();
    public DBGedFilesComment fcmask = new DBGedFilesComment();
    public DBUser umask = new DBUser();
    public DBGedFiles fmask = new DBGedFiles();


    protected String userId; // mandatory for Unlock rights
    protected String size;
    protected String name;
    protected String authorId;
    protected String versionDate;
    protected String fileBaseId;
    protected String reference;
    protected String version;

    public GedFiles()
    {
        super();
    }
    
    public GedFiles(DBGedFiles g, String userId)
    {
        super();
        init(g, userId);
    }
    
    protected void init(DBGedFiles g, String userId)
    {
        initBean(g);
        this.userId = userId;

        DBGedFilesVersion fv = getLastVersion();


        if(fv != null)
        {
            size = fv.getSize();
            name = fv.getName();
            authorId = fv.getAuthorId();
            versionDate = fv.getVersionDate();
            fileBaseId = fv.getFileBaseId();
            reference = fv.getReference();
            version = fv.getVersion();
        }
        else   
        {
            clear(); // a file that does not contain a valid version for the user is not shown
        }
    }

    public void clear()
    {
        super.clear();
        userId = null; // ?
        size = null;
        name = null;
        authorId = null;
        versionDate = null;
        fileBaseId = null;
        reference = null;
        version = null;
    }

    
    public boolean equals(Object o)
    {
        if(o == null) return false;
        
        if(o instanceof GedFiles)
        {
            GedFiles gf = (GedFiles)o;
            if(gf.getId().equals(this.id)) return true;
        }

        return false;
    }


    public boolean isInWorkflow()
    {
        if(this.id == null) return false;

        wpmask.clear();
        wpmask.setFileId(this.id);

        wpmask.setStatus(DBGedWorkflowProc.STATUS_SENT);
        if(DBGedWorkflowProc.count(wpmask, null) != 0) return true;

        wpmask.setStatus(DBGedWorkflowProc.STATUS_SENTTOBACKUP);
        if(DBGedWorkflowProc.count(wpmask, null) != 0) return true;

        return false;
    }


    public boolean isDisplayApproveDisapprove()
    {
        if(userId == null || id == null) return false;

        wpmask.clear();
        wpmask.setFileId(id);

        DBGedWorkflowProc[] wp = DBGedWorkflowProc.load(wpmask);

        if(wp == null) return false;

        int len = wp.length;

        wsmask.clear();
        for(int i=0; i<len; i++)
        {
            if(DBGedWorkflowProc.STATUS_SENT.equals(wp[i].getStatus())
               || DBGedWorkflowProc.STATUS_SENTTOBACKUP.equals(wp[i].getStatus()))
            { }
            else continue;

            // check if it is the right user
            String workflowStepId = wp[i].getWorkflowStepId();
            wsmask.setId(workflowStepId);
            DBGedWorkflowStep ws = DBGedWorkflowStep.loadByKey(wsmask);

            if(ws == null) continue;

            if(DBGedWorkflowProc.STATUS_SENT.equals(wp[i].getStatus()))
            {
                if(userId.equals(ws.getSendTo1())) return true;
            }
            else // STATUS_SENTTOBACKUP
            {
                if(userId.equals(ws.getSendTo2())) return true;
            }

        }

        return false;
    }
    

    /**
     * The file is lock or not ?
     * @return
     */
    public boolean isLock()
    {    	
        if("1".equals(lockBySyst)) return true;
        if(lockByUser != null) return true;

        if(id != null)
        {
            wpmask.clear();
            wpmask.setFileId(id);
            if(DBGedWorkflowProc.count(wpmask, null) != 0) return true;
        }

        return false;
    }

    public boolean isMove()
    {
        return !isLock();
    }

    public boolean isDelete()
    {
        return !isLock();
    }

    /**
     * Can always modify if you are the lockuser
     * else it depends of isLock state
     * @return
     */
    public boolean isModify()
    {    	
    	if(userId.equals(lockByUser)) return true;
    	
        return !isLock();
    }

    /**
     * Show or not the button to unlock ?
     * @return
     */
    public boolean isShowUnlockButton()
    {
        if(!isLock()) return false;

        if(userId == null) return false;

        if(userId.equals(lockByUser)) return true;

        ugmask.clear();
        ugmask.setUserId(userId);
        ugmask.setGroupId("1"); // admin group

        if(DBUserGroups.count(ugmask, null) != 0) return true;

        return false;
    }


    public boolean isExpired()
    {
    	try
    	{
	    	Database db = Database.getInstance();
	    	String query = db.getSpecificSQL("IsExpiredGedFiles");
	    	
	    	ArrayList param = new ArrayList();
			param.add(userId);
			param.add(id);
	    	
			
	    	ArrayList res = db.loadSQL(query, param);
	    	if(res == null) return false;
	    	
	    	ParamValuePair pvp = (ParamValuePair)res.get(0);
	    	String id = pvp.get("COUNT");
	    	
	    	if("0".equals(id)) return false;
	    	else return true;
    	}
    	catch(Exception e)
    	{
    		Debug.println(this, Debug.ERROR, e);
    		return false;
    	}
    	
    	
    	/*
    	dmask.clear();
    	dmask.setId(this.directoryId);
    	DBGedDirectory d = DBGedDirectory.loadByKey(dmask);
    	
    	if(d == null) return false;
    	
    	if(d.getFileExpiryGroup() == null || getVersionDate() == null)
    	{
    		return false;
    	}
    	else
    	{
    		long lastupdate = SQLTime.getJavaTime(getVersionDate());
    		long expirydate = lastupdate + Long.parseLong(d.getFileExpiryTime())*24*60*60*1000;
    		if(System.currentTimeMillis() > expirydate) return true;
    		else return false;
    	}
    	*/
    }


    /**
     * If the user can view the previous version of the document:true
     * @return
     */
    public boolean isVersionViewable()
    {
    	if(userId==null) return false;
    
    	dmask.clear();
    	dmask.setId(this.directoryId);
    	DBGedDirectory d = DBGedDirectory.loadByKey(dmask);
    	if(d == null) return false;
    	GedDirectory gd = new GedDirectory(d);
    	gd.setUserId(userId);
    	return gd.isWriteRight();
    }
    
    
    public boolean isViewable()
    {
    	if(userId==null) return false;
    
    	dmask.clear();
    	dmask.setId(this.directoryId);
    	DBGedDirectory d = DBGedDirectory.loadByKey(dmask);
    	if(d == null) return false;
    	GedDirectory gd = new GedDirectory(d);
    	gd.setUserId(userId);
    	return gd.isReadRight();
    }
    
    public boolean isWritable()
    {
    	//System.out.println("USERID="+userId);
    	
    	if(userId==null) return false;
    
    	dmask.clear();
    	dmask.setId(this.directoryId);
    	DBGedDirectory d = DBGedDirectory.loadByKey(dmask);
    	if(d == null) return false;
    	GedDirectory gd = new GedDirectory(d);
    	gd.setUserId(userId);
    	return gd.isWriteRight();
    }
    
    
    /**
     * Convert this file to PDF ?
     * @return
     */
    public boolean isConvert2pdf()
    {
    	if(this.directoryId == null) return false;
    	
    	dmask.clear();
    	dmask.setId(this.directoryId);
    	DBGedDirectory d = DBGedDirectory.loadByKey(dmask);
    	
    	if(d == null) return false;
    	
    	if(DBGedDirectory.STORETYPE_READONLYINPDF.equals(d.getStoreType()))
    	{
    		String ext = MimeTypes.getInstance().getExtension(name);	
    		return FormatConverter.isConvertible(ext, "pdf");
    	}
    	else
    		return false;
    }
    
    
    /**
     * @return GedTemplateItem objects
     */
    public final ArrayList getTemplateItem()
    {
        ArrayList array = new ArrayList();

        if(this.directoryId == null) return array;

        dmask.clear();
        dmask.setId(directoryId);

        DBGedDirectory d = DBGedDirectory.loadByKey(dmask);
        if(d == null) return null;

        String templateId = d.getTemplateId();

        if(templateId == null) return array;

        timask.clear();
        timask.setTemplateId(templateId);

        SQLConstraint constr = new SQLConstraint();
        constr.setOrderBy(DBGedTemplateItem.WEIGHT);

        DBGedTemplateItem[] ti = DBGedTemplateItem.load(constr,timask);
        
        if(ti == null) return array;

        int len = ti.length;

        DBGedFilesDescr ftimask = new DBGedFilesDescr();
        ftimask.setFileId(this.id);

        for(int i=0; i<len; i++)
        {
            DBGedFilesDescr fti = null;
            String value = ti[i].getDefaultValue();

            if(this.id != null)
            {
                ftimask.setTemplateItemId(ti[i].getId());
                fti = DBGedFilesDescr.loadByKey(ftimask);
                if(fti != null) value = fti.getValue();
            }          

            GedTemplateItem gti = new GedTemplateItem(ti[i]);
            gti.setValue(value);
            array.add(gti);
        }

        return array;
    }


    /**
     * Arraylist of GedFilesComment
     */
    public final ArrayList getComments()
    {
        if(id == null) return null;

        ArrayList array = new ArrayList();

        fcmask.setFileId(id);
        DBGedFilesComment[] gfc = DBGedFilesComment.load(fcmask);

        if(gfc == null) return array;

        int len = gfc.length;

        for(int i=0; i<len; i++)
            array.add(new GedFilesComment(gfc[i]));

        return array;
    }

    public final String getLogin()
    {
        if(authorId == null) return null;

        umask.clear();
        umask.setUserId(authorId);
        DBUser u = DBUser.loadByKey(umask);
        
        if(u == null) return null;

        return u.getLogin();
    }
    
    public final String getWorkflowStatus()
    {
        wpmask.clear();
        wpmask.setFileId(id);
        DBGedWorkflowProc wp = DBGedWorkflowProc.loadByKey(wpmask);

        if(wp == null) return "Validated";
        else return wp.getStatus();
    }

    public final String getLockUser()
    {
        if("1".equals(lockBySyst)) return "System";

        if(lockByUser == null) return null;

        umask.clear();
        umask.setUserId(lockByUser);
        DBUser u = DBUser.loadByKey(umask);
        if(u == null) return null;
        
        return u.getLogin();
    }

    
    /**
     * if "valid" => return last version
     * if in workflow => if user in process => return last version
     * if in workflow => if other user => return (last version - 1)
     */
    public DBGedFilesVersion getLastVersion()
    {
        if(id == null || userId == null) return null;
        
        boolean isValid = false;
        boolean isAdmin = false;
        boolean isAuthor = false;

        wpmask.clear();
        wpmask.setFileId(id); // unique
        DBGedWorkflowProc wp = DBGedWorkflowProc.loadByKey(wpmask);   
        if(wp == null) isValid = true;


        ugmask.clear();
        ugmask.setUserId(userId);
        ugmask.setGroupId("1"); // admin group
        if(DBUserGroups.count(ugmask, null) != 0) isAdmin=true;


        fvmask.clear();
        fvmask.setFileId(id);

        SQLConstraint constr = new SQLConstraint();
        constr.setOrderBy(DBGedFilesVersion.VERSION);
        constr.setOrderByBiggestFirst(true);
        constr.setLimit(2);
            
        DBGedFilesVersion[] fv = DBGedFilesVersion.load(constr,fvmask);

        if(fv == null || fv.length == 0)
        {
            return null;
        }

        DBGedFilesVersion lastVer = fv[0];
        DBGedFilesVersion beforeLast = fv.length < 2 ? null:fv[1];
        
        if(userId.equals(lastVer.getAuthorId())) isAuthor = true;

        if(isValid || isAdmin || isAuthor)
        {
            return lastVer;
        }
        else // in a workflow
        {
            wsmask.clear();
            wsmask.setId(wp.getWorkflowStepId());
            DBGedWorkflowStep step = DBGedWorkflowStep.loadByKey(wsmask);
            if(step == null) return null;

            String stepUserId = null;
            
            if(DBGedWorkflowProc.STATUS_SENT.equals(wp.getStatus())) stepUserId = step.getSendTo1();
            else if(DBGedWorkflowProc.STATUS_SENTTOBACKUP.equals(wp.getStatus())) stepUserId = step.getSendTo2();

            if(userId.equals(stepUserId)) return lastVer;
            else return beforeLast;
        }
    }


    public GedWorkflowStepExt getWorkflowStepExt()
    {
        wpmask.clear();
        wpmask.setFileId(this.id);
        DBGedWorkflowProc wp = DBGedWorkflowProc.loadByKey(wpmask);
        
        if(wp == null) return null;

        wsmask.clear();
        wsmask.setId(wp.getWorkflowStepId());

        DBGedWorkflowStep step = DBGedWorkflowStep.loadByKey(wsmask);
        GedWorkflowStep ws = new GedWorkflowStep(step);
        GedWorkflowStepExt wsext = new GedWorkflowStepExt(ws, this.id);
        return wsext;
    }

    
    public String getPathName()
    {
        if(this.name == null) return null;

        dmask.clear();
        dmask.setId(this.directoryId);

        DBGedDirectory d = DBGedDirectory.loadByKey(dmask);
        return d.getPath()+this.name;
    }

    
    public String getWorkflowTimeleft()
    {
    	try
    	{
    		wpmask.clear();
    		wpmask.setFileId(this.id);
    		DBGedWorkflowProc wp = DBGedWorkflowProc.loadByKey(wpmask);

    		if(wp == null) return null;

    		wsmask.clear();
    		wsmask.setId(wp.getWorkflowStepId());

    		DBGedWorkflowStep step = DBGedWorkflowStep.loadByKey(wsmask);
    		GedWorkflowStep ws = new GedWorkflowStep(step);
    		GedWorkflowStepExt wsext = new GedWorkflowStepExt(ws, this.id);

    		long timelimit = Long.parseLong(wsext.getCurrentTimeLimit())*86400000;
    		long begindate = SQLTime.getJavaTime(wp.getBeginDate());
    		long left = begindate+timelimit-System.currentTimeMillis();
    		
    		return TimeConverter.humanRead(left/1000);
    	}
    	catch(Exception e)
    	{
    		Debug.println(this, Debug.ERROR, e);
    	}
    	return null;
    }
    // GET - SET

    /**
     * Gets the value of size
     *
     * @return the value of size
     */
    public final String getSize()
    {
        return this.size;
    }

    /**
     * Sets the value of size
     *
     * @param argSize Value to assign to this.size
     */
    public final void setSize(final String argSize)
    {
        this.size = argSize;
    }

    /**
     * Gets the value of name
     *
     * @return the value of name
     */
    public final String getName()
    {
        return this.name;
    }

    /**
     * Sets the value of name
     *
     * @param argName Value to assign to this.name
     */
    public final void setName(final String argName)
    {
        this.name = argName;
    }

    /**
     * Gets the value of authorId
     *
     * @return the value of authorId
     */
    public final String getAuthorId()
    {
        return this.authorId;
    }

    /**
     * Sets the value of authorId
     *
     * @param argAuthorId Value to assign to this.authorId
     */
    public final void setAuthorId(final String argAuthorId)
    {
        this.authorId = argAuthorId;
    }

    /**
     * Gets the value of userId
     *
     * @return the value of userId
     */
    public final String getUserId()
    {
        return this.userId;
    }

    /**
     * Sets the value of userId
     *
     * @param argUserId Value to assign to this.userId
     */
    public final void setUserId(final String argUserId)
    {
        this.userId = argUserId;
    }

    /**
     * Gets the value of versionDate
     *
     * @return the value of versionDate
     */
    public final String getVersionDate()
    {
        return this.versionDate;
    }

    /**
     * Sets the value of versionDate
     *
     * @param argVersionDate Value to assign to this.versionDate
     */
    public final void setVersionDate(final String argVersionDate)
    {
        this.versionDate = argVersionDate;
    }


    /**
     * Gets the value of fileBaseId
     *
     * @return the value of fileBaseId
     */
    public final String getFileBaseId()
    {
        return this.fileBaseId;
    }

    /**
     * Sets the value of fileBaseId
     *
     * @param argFileBaseId Value to assign to this.fileBaseId
     */
    public final void setFileBaseId(final String argFileBaseId)
    {
        this.fileBaseId = argFileBaseId;
    }


    /**
     * Gets the value of reference
     *
     * @return the value of reference
     */
    public final String getReference()
    {
        return this.reference;
    }

    /**
     * Sets the value of reference
     *
     * @param argReference Value to assign to this.reference
     */
    public final void setReference(final String argReference)
    {
        this.reference = argReference;
    }

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

    
}
