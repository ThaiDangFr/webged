package dang.cms;

import openplatform.database.dbean.*;
import java.util.*;

/**
 * Class AdminBlockEdit
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class AdminBlockEdit
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    private final static String ALL = "-1";
    private final static String TOP = "-2";    

    private DBCmsBlocks bmask = new DBCmsBlocks();
    private DBCmsBLockVisibility vmask = new DBCmsBLockVisibility();
    private DBCmsBlocks block; // the current block
    private ArrayList array = new ArrayList();

    private HashMap visInOpt = new HashMap();






    // SET BEG //
    public void setBlockId(String id)
    {
        if(id == null) return;
        
        bmask.clear();
        bmask.setId(id);
        block = DBCmsBlocks.loadByKey(bmask);
    }
    
    public void setTitle(String title) { block.setTitle(title); }
    public void setWeight(String w) { block.setWeight(w); }
    public void setVisibleIn(String[] mods)
    {
        if(mods == null) return;

        vmask.clear();
        vmask.setBlockId(block.getId());
        DBCmsBLockVisibility.delete(vmask);

        array.clear();
        boolean all = false;
        boolean top = false;
        int len = mods.length;
        for(int i=0; i<len; i++)
        {
            String v = mods[i];
            if(ALL.equals(v)) all = true;
            else if(TOP.equals(v)) top = true;
            else array.add(v); // add moduleIds
        }

        if(all) block.setVisibleIn(DBCmsBlocks.VISIBLEIN_ALL);
        else if(top) block.setVisibleIn(DBCmsBlocks.VISIBLEIN_FIRST);
        else
        {
            block.setVisibleIn(DBCmsBlocks.VISIBLEIN_LIST);
            len = array.size();
            for(int i=0; i<len; i++)
            {
                vmask.clear();
                vmask.setBlockId(block.getId());
                vmask.setModuleId((String)array.get(i));
                vmask.store();
            }
        }
    }
    public void setVisible(String v) { block.setVisible(v); }
    public void setPosition(String p) { block.setPosition(p); }
    // SET END //





    // GET BEG //
    public HashMap getVisibleOption() 
    {
    	HashMap visOpt = new HashMap();
    	
        visOpt.put("0", cmsLang.translate("not.visible"));
        visOpt.put("1", cmsLang.translate("visible"));
        
    	return visOpt; 	
    }
    
    public HashMap getPositionOption()
    {
    	HashMap posOpt = new HashMap();
    	
        posOpt.put(DBCmsBlocks.POSITION_LEFT, 	cmsLang.translate("left"));
        posOpt.put(DBCmsBlocks.POSITION_CENTER, cmsLang.translate("center"));
        posOpt.put(DBCmsBlocks.POSITION_RIGHT,  cmsLang.translate("right"));
        
    	return posOpt;	
    }
    
    public HashMap getVisibleInOption()
    {
        visInOpt.clear();
        visInOpt.put(ALL, "[ "+cmsLang.translate("all.pages") +" ]");
        visInOpt.put(TOP, "[ "+cmsLang.translate("top.page")  +" ]");
        
        DBCmsModule[] m = DBCmsModule.load(null);
        if(m != null)
        {
            int len = m.length;
            for(int i=0; i<len; i++)
            {
                visInOpt.put(m[i].getId(), m[i].getName());
            }
        }

        return visInOpt;
    }

    public String getTitle() { return block.getTitle(); }
    public String getWeight() { return block.getWeight(); }
    public String[] getVisibleIn()
    {
        array.clear();
        if(DBCmsBlocks.VISIBLEIN_ALL.equals(block.getVisibleIn())) array.add(ALL);
        else if(DBCmsBlocks.VISIBLEIN_FIRST.equals(block.getVisibleIn())) array.add(TOP);
        else
        {
            vmask.clear();
            vmask.setBlockId(block.getId());
            DBCmsBLockVisibility[] v = DBCmsBLockVisibility.load(vmask);
            if(v != null)
            {
                int len = v.length;
                for(int i=0; i<len; i++)
                {
                    array.add(v[i].getModuleId());
                }
            }
        }

        Object[] obj = array.toArray();
        String[] res = new String[obj.length];
        int len = res.length;
        for(int i=0; i<len; i++)
            res[i] = (String)obj[i];

        return res;
    }
    public String getVisible() { return block.getVisible(); }
    public String getPosition() { return block.getPosition(); }
    //public boolean getStore() { block.store(); return !block.isInError(); }
    
    public String doStore() throws Exception
    {
    	block.store();
    	return block.getTitle() + " " + cmsLang.translate("ok.saved");
    }
    // GET END //
}
