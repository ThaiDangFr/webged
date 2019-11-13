package dang.cms;

import openplatform.tools.*;
import openplatform.database.dbean.*;
import java.util.*;
import openplatform.database.*;

/**
 * Class UserNavig
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class UserNavig
{
    private String currentModuleId; // fill when clicking on MainMenu list
    private String userId; // fill at index.jsp

//     private String blockId;

    private DBCmsModule mmask = new DBCmsModule();
    private DBUserGroups ugmask = new DBUserGroups();
    private DBCmsBlocks bmask = new DBCmsBlocks();
    private DBCmsModuleAuthorization mamask = new DBCmsModuleAuthorization();
    private DBCmsBLockVisibility bvmask = new DBCmsBLockVisibility();
   
    private DBCmsPreferences pref;
    private SQLConstraint blockConstraint = new SQLConstraint();


    public UserNavig()
    {
        DBCmsPreferences[] p = DBCmsPreferences.load(null);
        if(p != null) pref = p[0];

        blockConstraint.setOrderBy(DBCmsBlocks.WEIGHT);
    }

    // GET BEG //
    public String getFirstPageURL()
    {
        String id = pref.getFirstPageId();
        if(id == null) return null;

        mmask.clear();
        mmask.setId(id);
        DBCmsModule m = DBCmsModule.loadByKey(mmask);
        if(m == null) return null;
        else return m.getJsp();
    }
 

    public String getAdminMail()
    {
        DBUser umask = new DBUser();
        umask.setUserId("1");
        DBUser u = DBUser.loadByKey(umask);
        if(u == null) return null;
        else return u.getMail();
    }

    
    public String getCmsLang() { return pref.getCmsLang(); }
    public String getSiteTitle() { return pref.getSiteTitle(); }
    public String getIpAddress() { return pref.getIpAddress(); }
    public boolean getRefreshPage() { pref.refresh(); return true; }
    public String getCurrentModuleId() { return currentModuleId; }
    public String getUserId() { return userId; }
    public String getHeader() { return pref.getHeader(); }
    public String getFooter() { return pref.getFooter(); }
    public String getCss() { return pref.getCss(); }
    public String getLogoId() { return pref.getLogoId(); }
    public ArrayList getLeftBlocks() { return getCommonBlocks(DBCmsBlocks.POSITION_LEFT); }
    public ArrayList getCenterBlocks() { return getCommonBlocks(DBCmsBlocks.POSITION_CENTER); }
    public ArrayList getRightBlocks() { return getCommonBlocks(DBCmsBlocks.POSITION_RIGHT); }


    /**
     * @return a List of DBCmsModule
     */
    public ArrayList getModules()
    {
        ArrayList modules = new ArrayList();

        mmask.clear();
        mmask.setVisible("1");
        DBCmsModule[] m = DBCmsModule.load(mmask);
        
        if(m==null) return modules;

        ArrayList groups = getGroups();
        int len = m.length;
        for(int i=0; i<len; i++)
        {
            DBCmsModule mod = m[i];
            
            if(DBCmsModule.AUTH_ANONYMOUS.equals(mod.getAuth())) modules.add(mod);
            else if(DBCmsModule.AUTH_ALLGROUPS.equals(mod.getAuth()) && userId!=null) modules.add(mod);
            else if(DBCmsModule.AUTH_SPECIFIC.equals(mod.getAuth()))
            {
                int leng = groups.size();

                for(int j=0; j<leng; j++)
                {
                    String groupId = (String)groups.get(j);
                    mamask.clear();
                    mamask.setModuleId(mod.getId());
                    mamask.setGroupId(groupId);

                    if(DBCmsModuleAuthorization.count(mamask, null) != 0 && !modules.contains(mod)) 
                        modules.add(mod);
                }
            }
        }

        return modules;
    }
    

    /**
     * @return a list of groups that the user belongs to
     */
    private ArrayList getGroups()
    {
        ArrayList groups = new ArrayList();

        if(userId == null) return groups;

        ugmask.clear();
        ugmask.setUserId(userId);
        
        DBUserGroups[] ug = DBUserGroups.load(ugmask);
        if(ug != null)
        {
            int len = ug.length;
            for(int i=0; i<len; i++)
            {
                String gId = ug[i].getGroupId();
                if(!groups.contains(gId))
                    groups.add(gId);
            }
        }

        return groups;
    }
    
    /**
     * @return a list of blocks that can be seen in the current module view
     */
    private ArrayList getCommonBlocks(String position)
    {
        ArrayList blocks = new ArrayList();
        
        bmask.clear();
        bmask.setVisible("1");
        bmask.setPosition(position);
     
        DBCmsBlocks[] b = DBCmsBlocks.load(blockConstraint, bmask);
        if(b == null) return blocks;

        int len = b.length;
        

        for(int i=0; i<len; i++)
        {
            DBCmsBlocks blk = b[i];
            if(DBCmsBlocks.VISIBLEIN_ALL.equals(blk.getVisibleIn())) blocks.add(blk);
            else if(DBCmsBlocks.VISIBLEIN_FIRST.equals(blk.getVisibleIn()) && currentModuleId==null) blocks.add(blk);
            else if(DBCmsBlocks.VISIBLEIN_LIST.equals(blk.getVisibleIn()) && currentModuleId!=null)
            {
                bvmask.clear();
                bvmask.setBlockId(blk.getId());
                bvmask.setModuleId(currentModuleId);
              
                if(DBCmsBLockVisibility.count(bvmask, null) != 0) blocks.add(blk);
            }
        }

        //Debug.println(this, Debug.WARNING, "block len="+len+" position="+position+" return len="+blocks.size()+" currentModuleId="+currentModuleId);
        

        return blocks;        
    }
    // GET END //







    // SET BEG //
    public void setCurrentModuleId(String id)
    {
        Debug.println(this, Debug.DEBUG, "currentModuleId="+id);
        this.currentModuleId = id;
    }
    public void setUserId(String id) { this.userId = id; }
    // SET END //
}
