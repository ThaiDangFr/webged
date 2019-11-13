package dang.websnmp;

import openplatform.database.dbean.*;
import javax.servlet.http.*;

import dang.cms.CmsLanguage;
import openplatform.http.*;
import java.util.*;
import openplatform.tools.*;
import openplatform.snmp.*;

/**
 * AdminMib.java
 *
 *
 * Created: Thu Jun 23 11:45:21 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class AdminMib 
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    private HttpServletRequest request;
    private UploadedFile uploadedFile;
    private String mibId;
   
    public String doUpload()
        throws Exception
    {
        if(request == null)
            throw new Exception(cmsLang.translate("error.empty"));

        uploadedFile = OpHttp.uploadFileHtmlForm(request, null);

        String filename = uploadedFile.getFileName();
        MibManager.upload(filename, uploadedFile.getInput());

        return cmsLang.translate("ok.saved")+" "+filename;
    }

    public String doDelete()
        throws Exception
    {
        if(this.mibId == null) throw new Exception(cmsLang.translate("error.empty"));

        DBSnmpMibFile fmask = new DBSnmpMibFile();
        fmask.setId(mibId);
        DBSnmpMibFile f = DBSnmpMibFile.loadByKey(fmask);
        if(f == null) throw new Exception(cmsLang.translate("error.empty"));
        f.delete();
        return cmsLang.translate("item.deleted");
    }

    public HashMap getMibFileOptions()
    {
        HashMap map = new OrdHashMap();
        DBSnmpMibFile[] mf =  DBSnmpMibFile.load(null);
        if(mf == null) return map;

        int len = mf.length;
        for(int i=0; i<len; i++)
        {
            map.put(mf[i].getId(), mf[i].getName());
        }

        return map;
    }

    // GET / SET

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
     * Gets the value of mibId
     *
     * @return the value of mibId
     */
    public final String getMibId()
    {
        return this.mibId;
    }

    /**
     * Sets the value of mibId
     *
     * @param argMibId Value to assign to this.mibId
     */
    public final void setMibId(final String argMibId)
    {
        this.mibId = argMibId;
    }


}
