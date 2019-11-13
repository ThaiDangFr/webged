package dang.ged;

import java.util.*;

import dang.cms.CmsLanguage;
import openplatform.database.dbean.*;
import openplatform.tools.*;
import openplatform.database.*;

/**
 * AdminTemplate.java
 *
 *
 * Created: Fri Apr  8 17:18:49 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class AdminTemplate extends GedTemplateItem
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    private DBGedTemplate gtmask = new DBGedTemplate();
    private DBGedTemplateItem gtimask = new DBGedTemplateItem();

    private ArrayList itemList = new ArrayList(); // list of GedTemplateItem
    private ArrayList itemListToDelete = new ArrayList(); // list of GedTemplateItem to delete
    
    private int itemIndex;

    private String templateId;
    private String templateName;

    private SQLConstraint constraint = new SQLConstraint();


    public AdminTemplate()
    {
        constraint.setOrderBy(DBGedWorkflowStep.WEIGHT);
    }


    // PRIVATE B
    private void resetItem()
    {
        itemIndex=-1;
        id=null;
        fieldname=null;
        size=null;
        type=null;
        enumeration=null;
        defaultValue=null;
    }

    private void resetTemplate()
    {
        templateId=null;
        templateName=null;
        resetItem();
        itemList.clear();
        itemListToDelete.clear();
    }
    // PRIVATE E



    // DO B
    public String doUpItem() throws Exception
    {
        if(itemIndex==-1)
        	throw new Exception(cmsLang.translate("error.selected"));

        Object obj = itemList.remove(itemIndex);
        itemList.add(itemIndex-1,obj);

        return null;
    }

    public String doDownItem() throws Exception
    {
        if(itemIndex==-1)
        	throw new Exception(cmsLang.translate("error.selected"));

        Object obj = itemList.remove(itemIndex);
        itemList.add(itemIndex+1,obj);

        return null;
    }

    public String doSaveItem() throws Exception
    {
        if(fieldname == null || fieldname.trim().equals(""))
        	throw new Exception(cmsLang.translate("error.empty.name"));

        GedTemplateItem item = null;

        if(itemIndex==-1) // new entry
        {
            item = new GedTemplateItem();
            item.setFieldname(fieldname);
            item.setSize(size);
            item.setType(type);
            item.setEnumeration(enumeration);
            item.setDefaultValue(defaultValue);
            itemList.add(item);
        }
        else // edit
        {
            item = (GedTemplateItem)itemList.get(itemIndex);
            item.setFieldname(fieldname);
            item.setSize(size);
            item.setType(type);
            item.setEnumeration(enumeration);
            item.setDefaultValue(defaultValue);
            
        }

        resetItem();

        return item.getFieldname()+" "+cmsLang.translate("ok.saved");
    }

    public String doDeleteItem() throws Exception
    {
        if(itemIndex == -1) throw new Exception(cmsLang.translate("error.selected"));

        DBGedTemplateItem item = (DBGedTemplateItem)itemList.remove(itemIndex);
        if(item.getId() != null)
            itemListToDelete.add(item);

        return cmsLang.translate("ok.deleted");
    }

    public String doNewTemplate() throws Exception
    {
        resetTemplate();
        return null;
    }

    /**
     * Save the template, and all the modification done with TemplateItem
     */
    public String doSaveTemplate() throws Exception
    {
        if(templateName == null || templateName.trim().equals(""))
        	throw new Exception(cmsLang.translate("error.empty.name"));

        int len = itemList.size();

        if(len == 0)
        	throw new Exception(cmsLang.translate("error.itemlist.empty"));

        if(templateId == null)
        {
            gtmask.clear();
            gtmask.setName(templateName);
            gtmask.store();
            templateId = gtmask.getId();
        }
        else
        {
            gtmask.clear();
            gtmask.setId(templateId);
            DBGedTemplate t = DBGedTemplate.loadByKey(gtmask);
            t.setName(templateName);
            t.store();
        }

        
        for(int i=0; i<len; i++)
        {
            DBGedTemplateItem item = (DBGedTemplateItem)itemList.get(i);
            item.setTemplateId(templateId);
            item.setWeight(Integer.toString(i));
            item.store();
        }
        
        len = itemListToDelete.size();
        for(int i=0; i<len; i++)
        {
            DBGedTemplateItem item = (DBGedTemplateItem)itemListToDelete.get(i);
            if(item.getId() != null)
                item.delete();
        }

        resetTemplate();

        return cmsLang.translate("ok.saved");
    }

    public String doEditTemplate() throws Exception
    {
        Debug.println(this, Debug.DEBUG, "doEditTemplate");

        if(templateId == null) throw new Exception(cmsLang.translate("error.selected"));        
        gtmask.clear();
        gtmask.setId(templateId);
        DBGedTemplate template = DBGedTemplate.loadByKey(gtmask);
        templateId = template.getId();
        templateName = template.getName();


        gtimask.clear();
        gtimask.setTemplateId(templateId);
        DBGedTemplateItem[] items = DBGedTemplateItem.load(constraint, gtimask);

        if(items == null) return null;

        int len = items.length;
        
        for(int i=0; i<len; i++)
            itemList.add(new GedTemplateItem(items[i]));
       
        return null;
    }

    public String doDeleteTemplate() throws Exception
    {
        if(templateId == null) throw new Exception(cmsLang.translate("error.selected"));

        gtmask.clear();
        gtmask.setId(templateId);
        DBGedTemplate.delete(gtmask);

        return cmsLang.translate("ok.deleted");
    }
    // DO E





    // GET B
    public DBGedTemplate[] getTemplateList()
    {
        return DBGedTemplate.load(null);
    }
    // GET E





    // GET - SET



    /**
     * Gets the value of gtmask
     *
     * @return the value of gtmask
     */
    public final DBGedTemplate getGtmask()
    {
        return this.gtmask;
    }

    /**
     * Sets the value of gtmask
     *
     * @param argGtmask Value to assign to this.gtmask
     */
    public final void setGtmask(final DBGedTemplate argGtmask)
    {
        this.gtmask = argGtmask;
    }

    /**
     * Gets the value of gtimask
     *
     * @return the value of gtimask
     */
    public final DBGedTemplateItem getGtimask()
    {
        return this.gtimask;
    }

    /**
     * Sets the value of gtimask
     *
     * @param argGtimask Value to assign to this.gtimask
     */
    public final void setGtimask(final DBGedTemplateItem argGtimask)
    {
        this.gtimask = argGtimask;
    }

    /**
     * Gets the value of itemList
     *
     * @return the value of itemList
     */
    public final ArrayList getItemList()
    {
        return this.itemList;
    }

    /**
     * Sets the value of itemList
     *
     * @param argItemList Value to assign to this.itemList
     */
    public final void setItemList(final ArrayList argItemList)
    {
        this.itemList = argItemList;
    }

    /**
     * Gets the value of itemListToDelete
     *
     * @return the value of itemListToDelete
     */
    public final ArrayList getItemListToDelete()
    {
        return this.itemListToDelete;
    }

    /**
     * Sets the value of itemListToDelete
     *
     * @param argItemListToDelete Value to assign to this.itemListToDelete
     */
    public final void setItemListToDelete(final ArrayList argItemListToDelete)
    {
        this.itemListToDelete = argItemListToDelete;
    }

    /**
     * Gets the value of itemIndex
     *
     * @return the value of itemIndex
     */
    public final int getItemIndex()
    {
        return this.itemIndex;
    }

    /**
     * Sets the value of itemIndex
     *
     * @param argItemIndex Value to assign to this.itemIndex
     */
    public final void setItemIndex(final int argItemIndex)
    {
        this.itemIndex = argItemIndex;
    }

    /**
     * Gets the value of templateId
     *
     * @return the value of templateId
     */
    public final String getTemplateId()
    {
        return this.templateId;
    }

    /**
     * Sets the value of templateId
     *
     * @param argTemplateId Value to assign to this.templateId
     */
    public final void setTemplateId(final String argTemplateId)
    {
        this.templateId = argTemplateId;
    }

    /**
     * Gets the value of templateName
     *
     * @return the value of templateName
     */
    public final String getTemplateName()
    {
        return this.templateName;
    }

    /**
     * Sets the value of templateName
     *
     * @param argTemplateName Value to assign to this.templateName
     */
    public final void setTemplateName(final String argTemplateName)
    {
        this.templateName = argTemplateName;
    }

}
