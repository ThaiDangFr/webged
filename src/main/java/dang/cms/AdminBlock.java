package dang.cms;

import openplatform.database.dbean.*;
import java.util.*;

/**
 * Class AdminBlock
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class AdminBlock
{
    private final static String ALL = "-1";
    private final static String TOP = "-2";

    private CmsLanguage cmsLang = new CmsLanguage();
    
    private DBCmsBlocks bmask = new DBCmsBlocks();
    private DBCmsBLockVisibility vmask = new DBCmsBLockVisibility();
    private DBCmsModule mmask = new DBCmsModule();
    private String visibleIn; // moduleId
    private String visible;
    private static HashMap visOpt = new HashMap();
    private HashMap visInOpt = new HashMap();
    private ArrayList blockList = new ArrayList();
    private String moduleId;






    // GET BEG //
    public String getModuleName()
    {
        mmask.setId(moduleId);
        DBCmsModule mod = DBCmsModule.loadByKey(mmask);
        if(mod == null) return null;
        else return mod.getName();
    }

    public HashMap getVisibleOption() 
    {
    	HashMap visOpt = new HashMap();
        visOpt.put("0", cmsLang.translate("not.visible"));
        visOpt.put("1", cmsLang.translate("visible"));
    	return visOpt;
    }
    
    public String getVisible() { return visible; }
    public String getVisibleIn() { return visibleIn; }

    public HashMap getVisibleInOption()
    {
        visInOpt.put(ALL, "[ "+cmsLang.translate("all.pages")+" ]");
        visInOpt.put(TOP, "[ "+cmsLang.translate("top.page") +" ]");
        
        DBCmsModule[] mod = DBCmsModule.load(null);
        if(mod != null)
        {
            int len = mod.length;
            for(int i=0; i<len; i++)
                visInOpt.put(mod[i].getId(), mod[i].getName());
        }

        return visInOpt;
    }

    public ArrayList getBlockList()
    {
        boolean isVisibleInList = false;
        blockList.clear();
        bmask.clear();
        
        bmask.setVisible(visible);

        if(ALL.equals(visibleIn)) bmask.setVisibleIn(DBCmsBlocks.VISIBLEIN_ALL);
        else if(TOP.equals(visibleIn)) bmask.setVisibleIn(DBCmsBlocks.VISIBLEIN_FIRST);
        else if(visibleIn != null)
        {
            bmask.setVisibleIn(DBCmsBlocks.VISIBLEIN_LIST);
            isVisibleInList = true;
        }
        
        DBCmsBlocks[] bl = DBCmsBlocks.load(bmask);
        if(bl != null)
        {
            int len = bl.length;

            if(isVisibleInList)
            {
                for(int i=0; i<len; i++)
                {
                    vmask.clear();
                    vmask.setBlockId(bl[i].getId());
                    vmask.setModuleId(visibleIn);
                    if(DBCmsBLockVisibility.count(vmask, null) != 0) blockList.add(bl[i]);
                }
            }
            else
            {
                for(int i=0; i<len; i++)
                {
                    blockList.add(bl[i]);
                }
            }
        }

        return blockList;
    }
    // GET END //


    // SET BEG //
    public void setVisible(String visible) { this.visible = visible; }
    public void setVisibleIn(String visibleIn) { this.visibleIn = visibleIn; }
    public void setModuleId(String moduleId) { this.moduleId = moduleId; }
    // SET END //
}
