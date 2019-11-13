package dang.ged;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import openplatform.database.dbean.DBGedDirectory;
import openplatform.database.dbean.DBGedFiles;
import openplatform.database.dbean.DBGedFilesDescr;
import openplatform.database.dbean.DBGedFilesVersion;
import openplatform.database.dbean.DBGedState;
import openplatform.database.dbean.DBGedWorkflowProc;
import openplatform.database.dbean.DBUser;
import openplatform.tools.SQLTime;

public class GedStateFiles extends GedFiles {
	protected String dirName;
	protected String author;
	protected String procStatus;
	protected String expiryDate;
	protected String procStep;
	protected String procCurrentUser;
	protected String lockBy;
	

	
	
	public GedStateFiles(DBGedFiles gf)
	{
		super(gf, "1");
		
		dmask.clear();
		dmask.setId(this.getDirectoryId());
		DBGedDirectory gd = DBGedDirectory.loadByKey(dmask);
		if(gd != null)
		{
			dirName = gd.getName();
			
			if(gd.getFileExpiryTime() != null)
			{
				long lastupdate = SQLTime.getJavaTime(getVersionDate());
				long lexpiryDate = lastupdate + Long.parseLong(gd.getFileExpiryTime())*24*60*60*1000;
				//expiryDate = Long.toString(lexpiryDate);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
				expiryDate = sdf.format(new Date(lexpiryDate));
			}
		}
		
		umask.clear();
		DBGedFilesVersion gfv = getLastVersion();
		if(gfv != null)
		{
			umask.setUserId(gfv.getAuthorId());
			DBUser u = DBUser.loadByKey(umask);
			if(u != null) author = u.getLogin();
		}
		
		
		wpmask.clear();
		wpmask.setFileId(this.id);
		DBGedWorkflowProc gwp = DBGedWorkflowProc.loadByKey(wpmask);
		if(gwp != null)
		{
			procStatus = gwp.getStatus();
		}
		
		//procStatus = getWorkflowStatus();
		
		
		GedWorkflowStepExt gwse = getWorkflowStepExt();
		if(gwse != null)
		{
			procStep = gwse.getName();
			procCurrentUser = gwse.getCurrentSendToLogin();
		}
		
		
		if(getLockBySyst().equals("1"))
			lockBy = "System";
		else if(getLockByUser() != null)
		{
			umask.clear();
			umask.setUserId(getLockByUser());
			DBUser u = DBUser.loadByKey(umask);
			lockBy = u.getLogin();
		}
	}

	
	
	
	
	
	/**
	 * GedState ID - String value
	 * 
	 * @return
	 */
	public HashMap getIdValueMap()
	{
		HashMap map = new HashMap();
		
		DBGedState[] dbgedstate = DBGedState.load(null);
		
		if(dbgedstate == null) return map;
		
		int len = dbgedstate.length;
		for(int i=0; i<len; i++)
		{
			
			GedState gs = new GedState(dbgedstate[i]);
			
			if(gs.getGsType() == 0) // general column
			{
				if(DBGedState.GENERALCOLUMN_FILENAME.equals(gs.getGsName()))
				{
					map.put(gs.getGsId(), getName());
				}
				else if(DBGedState.GENERALCOLUMN_DIRNAME.equals(gs.getGsName()))
				{
					map.put(gs.getGsId(), getDirName());
				}
				else if(DBGedState.GENERALCOLUMN_AUTHOR.equals(gs.getGsName()))
				{
					map.put(gs.getGsId(), getAuthor());
				}
				else if(DBGedState.GENERALCOLUMN_DATE.equals(gs.getGsName()))
				{
					map.put(gs.getGsId(), getVersionDate());
				}
				else if(DBGedState.GENERALCOLUMN_REFERENCE.equals(gs.getGsName()))
				{
					map.put(gs.getGsId(), getReference());
				}
				else if(DBGedState.GENERALCOLUMN_VERSION.equals(gs.getGsName()))
				{
					map.put(gs.getGsId(), getVersion());
				}
				else if(DBGedState.GENERALCOLUMN_PROCSTATUS.equals(gs.getGsName()))
				{
					map.put(gs.getGsId(), getProcStatus());
				}
				else if(DBGedState.GENERALCOLUMN_EXPIRYDATE.equals(gs.getGsName()))
				{
					map.put(gs.getGsId(), getExpiryDate());
				}
				else if(DBGedState.GENERALCOLUMN_PROCSTEP.equals(gs.getGsName()))
				{
					map.put(gs.getGsId(), getProcStep());
				}
				else if(DBGedState.GENERALCOLUMN_PROCCURRENTUSER.equals(gs.getGsName()))
				{
					map.put(gs.getGsId(), getProcCurrentUser());
				}
				else if(DBGedState.GENERALCOLUMN_LOCKBY.equals(gs.getGsName()))
				{
					map.put(gs.getGsId(), getLockBy());
				}
			}
			else if(gs.getGsType() == 1) // template item
			{
				DBGedFilesDescr gfdmask = new DBGedFilesDescr();
				gfdmask.setFileId(getId());
				gfdmask.setTemplateItemId(gs.getGedTemplateItem());
				DBGedFilesDescr gfd = DBGedFilesDescr.loadByKey(gfdmask);
				if(gfd != null)
					map.put(gs.getGsId(), gfd.getValue());
			}
		}
		
		
		return map;
	}
	
	
	
	
	
	
	
	
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getLockBy() {
		return lockBy;
	}

	public void setLockBy(String lockBy) {
		this.lockBy = lockBy;
	}

	public String getProcCurrentUser() {
		return procCurrentUser;
	}

	public void setProcCurrentUser(String procCurrentUser) {
		this.procCurrentUser = procCurrentUser;
	}

	public String getProcStatus() {
		return procStatus;
	}

	public void setProcStatus(String procStatus) {
		this.procStatus = procStatus;
	}

	public String getProcStep() {
		return procStep;
	}

	public void setProcStep(String procStep) {
		this.procStep = procStep;
	}
	
	
	
}
