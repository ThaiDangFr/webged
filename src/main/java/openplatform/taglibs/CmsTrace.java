package openplatform.taglibs;

import java.sql.Timestamp;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import openplatform.database.dbean.DBTrace;
import openplatform.tools.Debug;

/**
 * Class CmsTrace
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class CmsTrace extends OPTagSupport
{
    private DBTrace trace = new DBTrace();

    private String moduleId;
    private String object;
    private String action;
    private String statusMsg;
    private String statusUserId;
    private String statusGroupId;
    private String ipAddress;

    
    public void initVars()
    {
        moduleId = null;
        object = null;
        action = null;
        statusMsg = null;
        statusUserId = null;
        statusGroupId = null;
        ipAddress = null;
    }

    public int doStartTag() throws JspException
    {
        try
        {   
        	if("".equals(moduleId)) moduleId = null;
            if("".equals(object)) object = null;
            if("".equals(action)) action = null;
            if("".equals(statusMsg)) statusMsg = null;
            if("".equals(statusUserId)) statusUserId = null;
            if("".equals(statusGroupId)) statusGroupId = null;
            if("".equals(ipAddress)) ipAddress = null;
        	
            trace.clear();
            trace.setDate((new Timestamp(System.currentTimeMillis())).toString());
            trace.setModuleId(moduleId);
            trace.setObject(object);
            trace.setAction(action);
            trace.setStatusMsg(statusMsg);
            trace.setStatusUserId(statusUserId);
            trace.setStatusGroupId(statusGroupId);
            trace.setIpAddress(ipAddress);
            trace.store();
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            throw new JspTagException(e.getMessage());
        }
        finally { initVars(); }
    
        return SKIP_BODY;
        
    }

    /**
     * Gets the value of moduleId
     *
     * @return the value of moduleId
     */
    public final String getModuleId()
    {
        return this.moduleId;
    }

    /**
     * Sets the value of moduleId
     *
     * @param argModuleId Value to assign to this.moduleId
     */
    public final void setModuleId(final String argModuleId)
    {
        this.moduleId = argModuleId;
    }

    /**
     * Gets the value of object
     *
     * @return the value of object
     */
    public final String getObject()
    {
        return this.object;
    }

    /**
     * Sets the value of object
     *
     * @param argObject Value to assign to this.object
     */
    public final void setObject(final String argObject)
    {
        this.object = argObject;
    }

    /**
     * Gets the value of action
     *
     * @return the value of action
     */
    public final String getAction()
    {
        return this.action;
    }

    /**
     * Sets the value of action
     *
     * @param argAction Value to assign to this.action
     */
    public final void setAction(final String argAction)
    {
        this.action = argAction;
    }

    /**
     * Gets the value of statusMsg
     *
     * @return the value of statusMsg
     */
    public final String getStatusMsg()
    {
        return this.statusMsg;
    }

    /**
     * Sets the value of statusMsg
     *
     * @param argStatusMsg Value to assign to this.statusMsg
     */
    public final void setStatusMsg(final String argStatusMsg)
    {
        this.statusMsg = argStatusMsg;
    }

    /**
     * Gets the value of statusUserId
     *
     * @return the value of statusUserId
     */
    public final String getStatusUserId()
    {
        return this.statusUserId;
    }

    /**
     * Sets the value of statusUserId
     *
     * @param argStatusUserId Value to assign to this.statusUserId
     */
    public final void setStatusUserId(final String argStatusUserId)
    {
        this.statusUserId = argStatusUserId;
    }

    /**
     * Gets the value of statusGroupId
     *
     * @return the value of statusGroupId
     */
    public final String getStatusGroupId()
    {
        return this.statusGroupId;
    }

    /**
     * Sets the value of statusGroupId
     *
     * @param argStatusGroupId Value to assign to this.statusGroupId
     */
    public final void setStatusGroupId(final String argStatusGroupId)
    {
        this.statusGroupId = argStatusGroupId;
    }

    /**
     * Gets the value of ipAddress
     *
     * @return the value of ipAddress
     */
    public final String getIpAddress()
    {
        return this.ipAddress;
    }

    /**
     * Sets the value of ipAddress
     *
     * @param argIpAddress Value to assign to this.ipAddress
     */
    public final void setIpAddress(final String argIpAddress)
    {
        this.ipAddress = argIpAddress;
    }

}
