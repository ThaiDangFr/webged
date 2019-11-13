package dang.ged;

import javax.servlet.http.*;

import dang.cms.CmsLanguage;
import openplatform.http.*;
import openplatform.index.Engine;
import openplatform.database.dbean.*;
import openplatform.database.dbeanext.MimeTypes;
import openplatform.database.*;
import openplatform.document.FormatConverter;

import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;

import openplatform.file.*;
import openplatform.tools.*;
import java.util.*;
import java.util.Date;


/**
 * UserFile.java
 *
 *
 * Created: Tue Apr 19 17:35:56 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class UserFile extends GedFiles
{
	private WDaemon wdaemon = new WDaemon();
	private CmsLanguage cmsLang = new CmsLanguage();
	
    public HashMap templateItemMap = new HashMap(); // id / value
    

    private HttpServletRequest request;
    private UploadedFile uploadedFile;
    private String commentSubject;
    private String commentText;
    private String templateItemId;


    // DO B
    public String doRecreateCache()
    	throws Exception
    {   	
    	if(id == null) throw new Exception(cmsLang.translate("error.empty")+":id");
    	
    	DBGedFilesVersion gfvmask = new DBGedFilesVersion();
    	gfvmask.setFileId(id);
    	DBGedFilesVersion[] gfv = DBGedFilesVersion.load(gfvmask);
    	
    	if(gfv != null)
    	{
    		int len = gfv.length;
    		for(int i=0; i<len; i++)
    		{
    			String fbi = gfv[i].getFileBaseId();
    			
    			DBConversionCache ccmask = new DBConversionCache();
    			ccmask.setOrifile(fbi);
    			DBConversionCache[] cc = DBConversionCache.load(ccmask);
    	
    			if(cc != null)
    			{
    				int len2 = cc.length;
    				for(int j=0; j<len2; j++)
    					Repository.deleteFile(cc[j].getCachefile());
    			}
    		}
    	}
    	
    	return cmsLang.translate("job.done");
    }
    
    
    public String doApprove()
        throws Exception
    {
        if(id == null) throw new Exception(cmsLang.translate("error.empty"));
        else if(directoryId == null) throw new Exception(cmsLang.translate("error.empty"));

        wpmask.clear();
        wpmask.setFileId(id);
        DBGedWorkflowProc wp = DBGedWorkflowProc.loadByKey(wpmask);
        if(wp == null) throw new Exception(cmsLang.translate("error.empty"));

        wp.setStatus(DBGedWorkflowProc.STATUS_APPROVED);
        wp.store();

        String stepId = wp.getWorkflowStepId();
        wsmask.clear();
        wsmask.setId(stepId);
        DBGedWorkflowStep ws = DBGedWorkflowStep.loadByKey(wsmask);

        String wId = ws.getWorkflowId();

        wdaemon.checkWorkflow(wId,id);

        return cmsLang.translate("file.approved");
    }


    public String doDisapprove()
        throws Exception
    {
        if(id == null) throw new Exception(cmsLang.translate("error.empty"));
        else if(directoryId == null) throw new Exception(cmsLang.translate("error.empty"));

        wpmask.clear();
        wpmask.setFileId(id);
        DBGedWorkflowProc wp = DBGedWorkflowProc.loadByKey(wpmask);
        if(wp == null) throw new Exception(cmsLang.translate("error.empty"));

        wp.setStatus(DBGedWorkflowProc.STATUS_DISAPPROVED);
        wp.store();

        String stepId = wp.getWorkflowStepId();
        wsmask.clear();
        wsmask.setId(stepId);
        DBGedWorkflowStep ws = DBGedWorkflowStep.loadByKey(wsmask);

        String wId = ws.getWorkflowId();

        wdaemon.checkWorkflow(wId,id);

        return cmsLang.translate("file.disapproved");
    }


    public String doUpload()
        throws Exception
    {
        if(request == null) throw new Exception(cmsLang.translate("error.empty"));

        long begin = System.currentTimeMillis();
        
        uploadedFile = OpHttp.uploadFileHtmlForm(request, null);

        long end = System.currentTimeMillis();
        
        long diff = end-begin;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss:SSS");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String diffstr = format.format(new Date(diff));
        
        Debug.println(this, Debug.DEBUG, "File uploaded in:"+diffstr);
        
        
        if(uploadedFile == null
           || uploadedFile.getInput() == null)
        {
            uploadedFile = null; // so we can't save later
            this.name = null;
            throw new Exception(cmsLang.translate("error.empty"));
        }
        else
        {        	           
            // check if all files are stored in PDF
        	// if yes => modify uploadedFile
            if(this.directoryId != null)
            {
            	String ext = MimeTypes.getInstance().getExtension(uploadedFile.getFileName());
            	
            	dmask.clear();
            	dmask.setId(this.directoryId);
            	DBGedDirectory gd = DBGedDirectory.loadByKey(dmask);
            	if(gd != null 
            			&& DBGedDirectory.STORETYPE_ALLINPDF.equals(gd.getStoreType())
            			&& FormatConverter.isConvertible(ext, "pdf"))
            	{
            		InputStream newInput = FormatConverter.convert(uploadedFile.getInput(), ext, "pdf");
            		uploadedFile.setInput(newInput);
            		
            		int idx = uploadedFile.getFileName().lastIndexOf(".");
            		if(idx != -1)
            		{
            			String newName = uploadedFile.getFileName().substring(0,idx) + ".pdf";
            			uploadedFile.setFileName(newName);
            		}
            	}
            }
            
            
            
            this.name = uploadedFile.getFileName();
        }

        return null;
    }

    public String doAddComment()
        throws Exception
    {
        if(commentSubject == null || commentSubject.trim().equals("")
           || commentText == null || commentText.trim().equals(""))
            throw new Exception(cmsLang.translate("error.empty"));

        if(id == null) throw new Exception(cmsLang.translate("error.empty"));

        if(userId == null) throw new Exception(cmsLang.translate("error.empty"));

        DBGedFilesComment fc = new DBGedFilesComment();
        fc.setFileId(id);
        fc.setUserId(userId);
        fc.setSubject(commentSubject);
        fc.setText(commentText);
        fc.store();

        return null;
    }

    public String doEdit()
        throws Exception
    {
        if(id == null) throw new Exception(cmsLang.translate("error.empty"));
        if(userId == null) throw new Exception(cmsLang.translate("error.empty"));
       
        uploadedFile = null;

        fmask.clear();
        fmask.setId(id);
        DBGedFiles f = DBGedFiles.loadByKey(fmask);
        
        if(f==null) throw new Exception(cmsLang.translate("file.notexists"));
        
        if("2".equals(f.getDirectoryId())) // File is in Trash
        	throw new Exception(cmsLang.translate("file.notexists"));
        
        
        init(f, userId);
        
        return null;
    }


    public String doNew()
        throws Exception
    {
        String bakDir = this.directoryId;
        clear();
        this.directoryId = bakDir;
        return null;
    }

    /**
     * Save the file
     */
    public String doSave()
        throws Exception
    {       
        boolean notifyRevised = isExpired();
                
        // Save the file first       
        if(directoryId == null) throw new Exception(cmsLang.translate("error.empty"));

        if(this.name == null || this.name.trim().equals(""))
            throw new Exception(cmsLang.translate("error.empty.name"));

        dmask.clear();
        dmask.setId(directoryId);
        DBGedDirectory d = DBGedDirectory.loadByKey(dmask);
        
        if(d == null) throw new Exception(cmsLang.translate("error.empty"));

        this.lockBySyst = "0";
        
        store();

        
        
        if(!isInError() && notifyRevised) // notify the revision
            GroupNotif.notifyRevised(this.id, this.userId);



        // save the templates
        Set set = templateItemMap.keySet();
        Iterator it = set.iterator();
        DBGedFilesDescr ftimask = new DBGedFilesDescr();
        
        while(it.hasNext())
        {
            String id = (String)it.next();
            String value = (String)templateItemMap.get(id);

            ftimask.clear();
            ftimask.setFileId(this.id);
            ftimask.setTemplateItemId(id);
            DBGedFilesDescr fd = DBGedFilesDescr.loadByKey(ftimask);
            
            if(fd != null)
            {
                fd.setValue(value);
                fd.store();
            }
            else
            {
                ftimask.setValue(value);
                ftimask.store();
            }
        }
        templateItemMap.clear();



        // update the last version (file name)
        if(uploadedFile == null)
        {
            DBGedFilesVersion fv = getLastVersion();

            if(fv != null)
            {
                fv.setName(this.name);
                fv.setVersionDate(SQLTime.getSQLTime(System.currentTimeMillis()));
                fv.store();
            }
        }
        // If there is an uploaded file, create a version, call the workflow manager
        else
        {
            // create a version
            this.size = Long.toString(uploadedFile.getSize());
            
            
            String fileBaseId = Repository.storeFile(uploadedFile.getFileName()
                                                     , uploadedFile.getInput(), true, Repository.APPLI_GED);

            
            if(fileBaseId == null)
            {
            	this.delete();
            	wdaemon.writeTrace(getPathName(), DBTrace.ACTION_SAVE, DBTrace.STATUSMSG_FAIL,
                        userId, null);
            	throw new Exception(cmsLang.translate("no.memory"));
            }
            
            
            String versionNB = "0";
            DBGedFilesVersion lastversion = getLastVersion();
            if(lastversion != null)
            {
                long l = Long.parseLong(lastversion.getVersion());
                versionNB = Long.toString(l+1);
            }

            DBGedFilesVersion version = new DBGedFilesVersion();
            version.setFileId(this.id);
            version.setFileBaseId(fileBaseId);
            version.setVisible("1");
            version.setVersion(versionNB);
            version.setSize(size);
            version.setName(name);
            version.setAuthorId(authorId);
            version.setVersionDate(SQLTime.getSQLTime(System.currentTimeMillis()));



            String reference = d.getReference(); // [id] [year] [version]
            reference = StringUtils.replaceAll(reference,"[year]",
                                               Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
            reference = StringUtils.replaceAll(reference, "[version]", version.getVersion());
            version.setReference(reference);
            version.setKeywords(createKeywords(fileBaseId));
            version.store();

            if(reference.indexOf("[id]") != -1)
            {
                reference = version.getReference();
                reference = StringUtils.replaceAll(reference, "[id]", this.id);
                version.setReference(reference);
                version.store();            
            }

            // Dereference previous version
            Engine.deferencePreviousGedVersion(fileBaseId);
            

            // create a workflow if needed
            String workflowId = d.getFileAddModifyWorkflow();
            if(workflowId != null)
            {
            	wdaemon.checkWorkflow(workflowId, this.id);
                lockBySyst = "1";
                store();
            }
            else
                GroupNotif.notifyUpdated(this.id);
        }
        
        wdaemon.writeTrace(getPathName(), DBTrace.ACTION_SAVE, DBTrace.STATUSMSG_OK,
                           userId, null);
     

        return null;
    }

    
    public String doMoveInTrash()
        throws Exception
    {
        if(this.id == null) throw new Exception(cmsLang.translate("error.empty"));
        if(this.userId == null) throw new Exception(cmsLang.translate("error.empty"));

        fmask.clear();
        fmask.setId(this.id);

        DBGedFiles f = DBGedFiles.loadByKey(fmask);
        if(f != null)
        {
            f.setDirectoryId("2");
            f.store();
            TrashUtils.putFileInTrash(this.id, this.userId);
            return (new GedFiles(f, this.userId)).getName()+" "+cmsLang.translate("ok.deleted");
        }
        else
            throw new Exception(cmsLang.translate("error.empty"));
    }

    public String doLock()
        throws Exception
    {
        if(this.id == null) throw new Exception(cmsLang.translate("error.empty"));
        if(this.userId == null) throw new Exception(cmsLang.translate("error.empty"));

        fmask.clear();
        fmask.setId(this.id);
        DBGedFiles f = DBGedFiles.loadByKey(fmask);

        if(f == null) throw new Exception(cmsLang.translate("error.empty"));

        f.setLockByUser(this.userId);
        f.store();

        return null;
    }

    public String doUnlock()
        throws Exception
    {
        if(this.id == null) throw new Exception(cmsLang.translate("error.empty"));
        if(this.userId == null) throw new Exception(cmsLang.translate("error.empty"));

        fmask.clear();
        fmask.setId(this.id);
        DBGedFiles f = DBGedFiles.loadByKey(fmask);

        if(f == null) throw new Exception(cmsLang.translate("error.empty"));

        f.setLockByUser(null);
        f.setLockBySyst("0");
        f.store();

        wpmask.clear();
        wpmask.setFileId(f.getId());
        DBGedWorkflowProc.delete(wpmask); // delete the process if we unlock

        return null;
    }

    public String doMoveFile()
        throws Exception
    {
        if(this.id == null) throw new Exception(cmsLang.translate("error.empty"));
        if(this.directoryId == null) throw new Exception(cmsLang.translate("error.empty"));

        fmask.clear();
        fmask.setId(this.id);

        DBGedFiles f = DBGedFiles.loadByKey(fmask);
        if(f != null)
        {
            f.setDirectoryId(this.directoryId);
            f.store();
        }

        return cmsLang.translate("file.moved");
    }
    // DO E
    

    public void clear()
    {
        super.clear();
        request = null;
        uploadedFile = null;
        commentSubject = null;
        commentText = null;
        templateItemId = null;
    }

//     public final String getUploadedName()
//     {
//         if(uploadedFile == null) return null;
//         else return uploadedFile.getFileName();
//     }


    public void setTemplateItemValue(String value)
    {
        templateItemMap.put(templateItemId, value);
    }

    /**
     * if the user is in the workflow => return all
     * if not => dont return the latest version
     */
    public final ArrayList getFilesVersion()
    {
        ArrayList array = new ArrayList();

        if(this.id == null) return array;

        SQLConstraint constr = new SQLConstraint();
        constr.setOrderBy(DBGedFilesVersion.VERSION);
        constr.setOrderByBiggestFirst(true);

        fvmask.clear();
        fvmask.setFileId(this.id);

        DBGedFilesVersion[] fv = DBGedFilesVersion.load(constr, fvmask);

        if(fv == null) return array;

        int len = fv.length;

        int begin = 0;

        if(isInWorkflow()) begin = 1;

        for(int i=begin; i<len; i++)
        {
            array.add(new dang.ged.GedFilesVersion(fv[i]));
        }

        return array;
    }   

    /**
     * TODO
     **/
    private String createKeywords(String fileBaseId)
    {
        return null;
    }

    private void registerWorkflow(String workflowId, String fileId)
    {
        if(workflowId == null || fileId == null) return;

        SQLConstraint constr = new SQLConstraint();
        constr.setLimit(1);

        wsmask.clear();
        wsmask.setWorkflowId(workflowId);
        DBGedWorkflowStep[] ws = DBGedWorkflowStep.load(constr, wsmask);
        
        if(ws == null) return;
        
        wpmask.clear();
        wpmask.setFileId(fileId);
        wpmask.setWorkflowStepId(ws[0].getId());
        wpmask.setStatus(DBGedWorkflowProc.STATUS_NEW);

        Timestamp ts = new Timestamp(System.currentTimeMillis());

        wpmask.setBeginDate(ts.toString());
        wpmask.store();
    }







    // GET / SET


    /**
     * Gets the value of templateItemId
     *
     * @return the value of templateItemId
     */
    public final String getTemplateItemId()
    {
        return this.templateItemId;
    }

    /**
     * Sets the value of templateItemId
     *
     * @param argTemplateItemId Value to assign to this.templateItemId
     */
    public final void setTemplateItemId(final String argTemplateItemId)
    {
        this.templateItemId = argTemplateItemId;
    }

    /**
     * Gets the value of request
     *
     * @return the value of request
     */
    public final HttpServletRequest getRequest()
    {
        return this.request;
    }

    /**
     * Sets the value of request
     *
     * @param argRequest Value to assign to this.request
     */
    public final void setRequest(final HttpServletRequest argRequest)
    {
        this.request = argRequest;
    }

    /**
     * Gets the value of uploadedFile
     *
     * @return the value of uploadedFile
     */
    public final UploadedFile getUploadedFile()
    {
        return this.uploadedFile;
    }

    /**
     * Sets the value of uploadedFile
     *
     * @param argUploadedFile Value to assign to this.uploadedFile
     */
    public final void setUploadedFile(final UploadedFile argUploadedFile)
    {
        this.uploadedFile = argUploadedFile;
    }

    /**
     * Gets the value of commentSubject
     *
     * @return the value of commentSubject
     */
    public final String getCommentSubject()
    {
        return this.commentSubject;
    }

    /**
     * Sets the value of commentSubject
     *
     * @param argCommentSubject Value to assign to this.commentSubject
     */
    public final void setCommentSubject(final String argCommentSubject)
    {
        this.commentSubject = argCommentSubject;
    }

    /**
     * Gets the value of commentText
     *
     * @return the value of commentText
     */
    public final String getCommentText()
    {
        return this.commentText;
    }

    /**
     * Sets the value of commentText
     *
     * @param argCommentText Value to assign to this.commentText
     */
    public final void setCommentText(final String argCommentText)
    {
        this.commentText = argCommentText;
    }

}
