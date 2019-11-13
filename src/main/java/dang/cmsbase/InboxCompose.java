package dang.cmsbase;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import openplatform.database.dbean.DBCmsInbox;
import openplatform.database.dbean.DBCmsInboxAttachment;
import openplatform.database.dbean.DBFileBase;
import openplatform.database.dbean.DBUser;
import openplatform.file.Repository;
import openplatform.http.OpHttp;
import openplatform.http.UploadedFile;
import openplatform.tools.Debug;
import openplatform.tools.OrdHashMap;
import dang.cms.CmsLanguage;


/**
 * Class InboxCompose
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class InboxCompose
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    private HttpServletRequest request;
    private DBCmsInbox inbox = new DBCmsInbox();
    private DBUser umask = new DBUser();
    private ArrayList attachment = new ArrayList(); // List of UploadedFile or DBCmsInboxAttachment
    private DBFileBase fbmask = new DBFileBase();


    private static HashMap priorityOpt = new HashMap();
    




    public String doSend()
        throws Exception
    {
        if(inbox.getSubject() == null || inbox.getSubject().trim().equals(""))
            throw new Exception(cmsLang.translate("error.empty.subject"));

        if(inbox.getToUserId() == null || inbox.getToUserId().trim().equals(""))
            throw new Exception(cmsLang.translate("error.empty.to"));

        Timestamp ts = new Timestamp(System.currentTimeMillis());
        inbox.setDate(ts.toString());
        inbox.setIsRead("0");
        inbox.store();

        int len = attachment.size();
        DBCmsInboxAttachment cia = null;

        if(len != 0) cia = new DBCmsInboxAttachment();
        for(int i=0; i<len; i++)
        {
            Object obj = attachment.get(i);
            if(obj instanceof UploadedFile)
            {
                UploadedFile f = (UploadedFile)obj;
                String id = Repository.storeFile(f.getFileName(), f.getInput(), false, Repository.APPLI_INBOX);
                cia.clear();
                cia.setFileBaseId(id);
                cia.setInboxId(inbox.getId());
                cia.store();
            }
            else if(obj instanceof DBCmsInboxAttachment)
            {
                DBCmsInboxAttachment ia = (DBCmsInboxAttachment)obj;
                
                String newId = Repository.cloneFile(ia.getFileBaseId()); // to avoid having the same reference !

                cia.clear();
                cia.setFileBaseId(newId);
                cia.setInboxId(inbox.getId());
                cia.store();
            }
        }

        Notificator.process(inbox);

        return cmsLang.translate("mail.sent");
    }




    // GET BEG //
    public String getToUserId() { return inbox.getToUserId(); }
    public boolean getDoInit()
    {
        attachment.clear();
        inbox.clear();
        inbox.setPriority(DBCmsInbox.PRIORITY_NORMAL);
        return true;
    }

    public ArrayList getInboxAttachmentName()
    {
        ArrayList array = new ArrayList();
        int len = attachment.size();
        for(int i=0; i<len; i++)
        {
            Object obj = attachment.get(i);
            if(obj instanceof UploadedFile)
                array.add(((UploadedFile)obj).getFileName());
            else if(obj instanceof DBCmsInboxAttachment)
            {
                DBCmsInboxAttachment ia = (DBCmsInboxAttachment)obj;
                String id = ia.getFileBaseId();
                fbmask.clear();
                fbmask.setFileBaseId(id);
                DBFileBase fb = DBFileBase.loadByKey(fbmask);
                if(fb != null) array.add(fb.getFilename());
            }
        }
        return array;
    }
    
    public boolean getDoUploadAttachment()
    {
        if(request == null) return false;

        UploadedFile u = OpHttp.uploadFileHtmlForm(request, null);
        
        if(u != null && u.getSize() != 0)
        {
            attachment.add(u);
        }
        else
        {
            Debug.println(this, Debug.WARNING, "No data to upload");
            return false;
        }

        return true;
    }

    public String getToUserLogin()
    {
        if(inbox.getToUserId() == null) return null;

        umask.clear();
        umask.setUserId(inbox.getToUserId());
        DBUser u = DBUser.loadByKey(umask);
        if(u==null) return null;
        else return u.getLogin();
    }

    public String getSubject() { return inbox.getSubject(); }
    public String getPriority() { return inbox.getPriority(); }
    public String getText() { return inbox.getText(); }
    public HashMap getPriorityOptions()
    { 
    	HashMap priorityOpt = new OrdHashMap();
        priorityOpt.put(DBCmsInbox.PRIORITY_NORMAL, cmsLang.translate("normal"));
        priorityOpt.put(DBCmsInbox.PRIORITY_HIGH, cmsLang.translate("high"));
        return priorityOpt;
    }
    // GET END


    // SET BEG
    public void setRequest(HttpServletRequest request) { this.request = request; }

    public void setDoDeleteInboxAttachment(int index)
    {
        try
        {
            attachment.remove(index-1);
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }


    public void setFromUserId(String id) { inbox.setFromUserId(id); }
    public void setToUserId(String id) { inbox.setToUserId(id); }
    public void setSubject(String s) { inbox.setSubject(s); }
    public void setPriority(String p) { inbox.setPriority(p); }
    public void setText(String t) { inbox.setText(t); }

    public void setInboxAttachment(DBCmsInboxAttachment[] ia)
    {
        attachment.clear();
        if(ia == null) return;
        int len = ia.length;
        for(int i=0; i<len; i++) attachment.add(ia[i]);
    }
    // SET END
}
