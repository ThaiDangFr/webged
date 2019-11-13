package dang.cmsbase;

import openplatform.database.dbean.DBCmsInbox;
import openplatform.database.dbean.DBCmsInboxAttachment;
import openplatform.database.dbean.DBFileBase;
import openplatform.database.dbean.DBUser;
import openplatform.file.Repository;

/**
 * Class Inbox
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Inbox
{
    private String userId; // mandatory
    private String fromUserId;

    private DBCmsInbox imask = new DBCmsInbox();
    private DBUser umask = new DBUser();
    private DBCmsInboxAttachment iamask = new DBCmsInboxAttachment();
    //private DBFileBase fbmask = new DBFileBase();
    private DBCmsInbox inbox;



    // GET BEG //
    public int getNewMessages()
    {
        if(userId == null) return 0;

        imask.clear();
        imask.setToUserId(userId);
        imask.setIsRead("0");
        return DBCmsInbox.count(imask, null);
    }

    public DBCmsInbox[] getInbox()
    {
        if(userId == null) return null;
        imask.clear();
        imask.setToUserId(userId);
        return DBCmsInbox.load(imask);
    }

    public String getDate() { return inbox.getDate(); }

    public String getFromUserLogin()
    {
        if(fromUserId == null) return null;
        umask.clear();
        umask.setUserId(fromUserId);
        DBUser u = DBUser.loadByKey(umask);
        if(u == null) return null;
        else return u.getLogin();
    }

    public String getSubject() { return inbox.getSubject(); }
    public String getText() { return inbox.getText(); }

    public DBCmsInboxAttachment[] getReceivedAttachments()
    {
        iamask.clear();
        iamask.setInboxId(inbox.getId());
        return DBCmsInboxAttachment.load(iamask);    
    }
    

    public String getFromUserId() { return inbox.getFromUserId(); }
    public String getPriority() { return inbox.getPriority(); }
    public String getInboxId() { return inbox.getId(); }
    // GET END



    // SET BEG //
    public void setFromUserId(String id) { this.fromUserId = id; }
    public void setUserId(String id) { this.userId = id; }
    public void setInboxId(String id)
    {
        if(id==null || userId==null) return;

        imask.clear();
        imask.setToUserId(userId); // to protect from hacking
        imask.setId(id);

        inbox = DBCmsInbox.loadByKey(imask);
        inbox.setIsRead("1");
        inbox.store();
    }

    public void setDoDeleteInbox(String[] id)
    {
        if(id == null) return;
        int len = id.length;      
        
        for(int i=0; i<len; i++)
        {
            iamask.clear();
            iamask.setInboxId(id[i]);
            DBCmsInboxAttachment[] ia = DBCmsInboxAttachment.load(iamask);
            if(ia != null)
            {
                int lenia = ia.length;
                for(int j=0; j<lenia; j++)
                {
                    String fileBaseId = ia[j].getFileBaseId();
                    Repository.deleteFile(fileBaseId);
                }
            }


            imask.clear();
            imask.setId(id[i]);
            DBCmsInbox.delete(imask);
        }
    }
    // SET END //
}
