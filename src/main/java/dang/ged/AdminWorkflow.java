package dang.ged;

import java.util.*;

import dang.cms.CmsLanguage;
import openplatform.database.dbean.*;
import openplatform.tools.*;
import openplatform.database.*;

/**
 * AdminWorkflow.java
 *
 *
 * Created: Sun Apr 10 20:39:41 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class AdminWorkflow extends GedWorkflowStep
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    private DBGedWorkflow gwmask = new DBGedWorkflow();
    private DBGedWorkflowStep gwsmask = new DBGedWorkflowStep();

    private ArrayList itemList = new ArrayList();
    private ArrayList itemListToDelete = new ArrayList();
    
    private int itemIndex;
    
    private String workflowId;
    private String workflowName;

    private SQLConstraint constraint = new SQLConstraint();

    public AdminWorkflow()
    {
        constraint.setOrderBy(DBGedWorkflowStep.WEIGHT);
    }


    // PRIVATE B
    private void resetItem()
    {
        itemIndex = -1;
        name = null;
        sendTo1 = null;
        sendTo2 = null;
        timelimit1 = null;
        timelimit2 = null;
        weight = null;
    }

    private void resetWorkflow()
    {
        workflowId = null;
        workflowName = null;
        resetItem();
        itemList.clear();
        itemListToDelete.clear();
    }
    // PRIVATE E


    // DO B
    public String doUpItem() throws Exception
    {
        if(itemIndex==-1) throw new Exception(cmsLang.translate("error.selected"));

        Object obj = itemList.remove(itemIndex);
        itemList.add(itemIndex-1,obj);

        return null;
    }

    public String doDownItem() throws Exception
    {
        if(itemIndex==-1) throw new Exception(cmsLang.translate("error.selected"));

        Object obj = itemList.remove(itemIndex);
        itemList.add(itemIndex+1,obj);

        return null;
    }

    public String doSaveItem() throws Exception
    {
        if(name == null || name.trim().equals("")) throw new Exception(cmsLang.translate("error.empty.name"));

        if(sendTo1 == null || "-1".equals(sendTo1)) throw new Exception(cmsLang.translate("error.empty.to"));

        GedWorkflowStep item = null;
        
        if(itemIndex==-1) // new entry
        {
            item = new GedWorkflowStep();
            item.setName(name);
            item.setSendTo1(sendTo1);
            item.setTimelimit1(timelimit1);
            item.setWeight(weight);
            item.setWorkflowId(workflowId);

            if(!sendTo2.equals("-1"))
            {
                item.setSendTo2(sendTo2);
                item.setTimelimit2(timelimit2);
            }
            else
            {
                item.setSendTo2(null);
                item.setTimelimit2(null);
            }

            itemList.add(item);
        }
        else // edit
        {
            item = (GedWorkflowStep)itemList.get(itemIndex);
            item.setName(name);
            item.setSendTo1(sendTo1);
            item.setTimelimit1(timelimit1);
            item.setWeight(weight);
            item.setWorkflowId(workflowId);

            if(!sendTo2.equals("-1"))
            {
                item.setSendTo2(sendTo2);
                item.setTimelimit2(timelimit2);
            }
            else
            {
                item.setSendTo2(null);
                item.setTimelimit2(null);
            }
        }

        resetItem();

        return item.getName()+" "+cmsLang.translate("ok.saved");
    }

    public String doDeleteItem() throws Exception
    {
        if(itemIndex == -1) throw new Exception(cmsLang.translate("error.selected"));

        DBGedWorkflowStep item = (DBGedWorkflowStep)itemList.remove(itemIndex);
        if(item.getId() != null)
            itemListToDelete.add(item);

        return cmsLang.translate("ok.deleted");
    }

    public String doNewWorkflow() throws Exception
    {
        resetWorkflow();
        return null;
    }

    /**
     * Save the workflow, and all the modification done with WorkflowStep
     */
    public String doSaveWorkflow() throws Exception
    {
        if(workflowName == null || workflowName.trim().equals("")) throw new Exception(cmsLang.translate("error.empty.name"));

        int len = itemList.size();

        if(len == 0) throw new Exception(cmsLang.translate("error.itemlist.empty"));

        if(workflowId == null)
        {
            gwmask.clear();
            gwmask.setName(workflowName);
            gwmask.store();
            workflowId = gwmask.getId();
        }
        else
        {
            gwmask.clear();
            gwmask.setId(workflowId);
            DBGedWorkflow w = DBGedWorkflow.loadByKey(gwmask);
            w.setName(workflowName);
            w.store();
        }
        

        for(int i=0; i<len; i++)
        {
            DBGedWorkflowStep item = (DBGedWorkflowStep)itemList.get(i);
            item.setWorkflowId(workflowId);
            item.setWeight(Integer.toString(i));
            item.store();
        }
        
        len = itemListToDelete.size();
        for(int i=0; i<len; i++)
        {
            DBGedWorkflowStep item = (DBGedWorkflowStep)itemListToDelete.get(i);
            if(item.getId() != null)
                item.delete();
        }

        resetWorkflow();

        return cmsLang.translate("ok.saved");
    }

    public String doEditWorkflow() throws Exception
    {
        Debug.println(this, Debug.DEBUG, "doEditWorkflow");

        if(workflowId == null) throw new Exception(cmsLang.translate("error.selected"));        
        gwmask.clear();
        gwmask.setId(workflowId);
        
        DBGedWorkflow workflow = DBGedWorkflow.loadByKey(gwmask);
        workflowId = workflow.getId();
        workflowName = workflow.getName();


        gwsmask.clear();
        gwsmask.setWorkflowId(workflowId);
        DBGedWorkflowStep[] items = DBGedWorkflowStep.load(constraint, gwsmask);

        if(items == null) return null;

        int len = items.length;
        
        for(int i=0; i<len; i++)
            itemList.add(new GedWorkflowStep(items[i]));
       
        return null;
    }

    public String doDeleteWorkflow() throws Exception
    {
        if(workflowId == null) throw new Exception(cmsLang.translate("error.selected"));

        gwmask.clear();
        gwmask.setId(workflowId);
        DBGedWorkflow.delete(gwmask);

        return cmsLang.translate("ok.deleted");
    }
    // DO E


    // GET B
    public DBGedWorkflow[] getWorkflowList()
    {
        return DBGedWorkflow.load(null);
    }
    // GET E


    
    // GET - SET

    /**
     * Gets the value of gwmask
     *
     * @return the value of gwmask
     */
    public final DBGedWorkflow getGwmask()
    {
        return this.gwmask;
    }

    /**
     * Sets the value of gwmask
     *
     * @param argGwmask Value to assign to this.gwmask
     */
    public final void setGwmask(final DBGedWorkflow argGwmask)
    {
        this.gwmask = argGwmask;
    }

    /**
     * Gets the value of gwsmask
     *
     * @return the value of gwsmask
     */
    public final DBGedWorkflowStep getGwsmask()
    {
        return this.gwsmask;
    }

    /**
     * Sets the value of gwsmask
     *
     * @param argGwsmask Value to assign to this.gwsmask
     */
    public final void setGwsmask(final DBGedWorkflowStep argGwsmask)
    {
        this.gwsmask = argGwsmask;
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
     * Gets the value of workflowId
     *
     * @return the value of workflowId
     */
    public final String getWorkflowId()
    {
        return this.workflowId;
    }

    /**
     * Sets the value of workflowId
     *
     * @param argWorkflowId Value to assign to this.workflowId
     */
    public final void setWorkflowId(final String argWorkflowId)
    {
        this.workflowId = argWorkflowId;
    }

    /**
     * Gets the value of workflowName
     *
     * @return the value of workflowName
     */
    public final String getWorkflowName()
    {
        return this.workflowName;
    }

    /**
     * Sets the value of workflowName
     *
     * @param argWorkflowName Value to assign to this.workflowName
     */
    public final void setWorkflowName(final String argWorkflowName)
    {
        this.workflowName = argWorkflowName;
    }

}
