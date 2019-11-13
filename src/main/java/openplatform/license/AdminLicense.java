package openplatform.license;

import javax.servlet.http.*;
import openplatform.http.*;
import openplatform.tools.*;
import java.text.*;
import java.util.*;
import openplatform.database.*;
import openplatform.database.dbean.*;

/**
 * AdminLicense.java
 *
 *
 * Created: Fri Jun 24 14:18:12 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class AdminLicense 
{
    private HttpServletRequest request;
    private UploadedFile uploadedFile;
    
    

    public String doUpload()
        throws Exception
    {
        if(request == null) throw new Exception("HttpServletRequest is null");

        uploadedFile = OpHttp.uploadFileHtmlForm(request, null);

        SQLConstraint constr = new SQLConstraint();
        constr.setLimit(1);
        DBLicense lic = DBLicense.loadByKey(null, constr);

        if(lic == null)
        {
            lic = new DBLicense();
        }

        lic.setLicenseKey(new String(StreamConverter.inputStreamToBytes(uploadedFile.getInput()), "UTF-8"));
        lic.store();

        LicenseState.parse();
        LicenseState.updateModules();

        return null;
    }


    public String getLicenseId()
    {
        String m = FindMac.getMacAddress();
        if(m == null) return "000";
        DesEncrypter des = new DesEncrypter("zaedsq14");
        String enc = des.encrypt(m);
        return enc.toUpperCase();
    }


    /**
     * The Main method to determine if the license is OK
     */
    public boolean isLicensed()
    {
        if(LicenseState.getLicenseId() == null) return false;

        long exp = LicenseState.getExpiryDate();
        if(exp != -1)
        {
            if(System.currentTimeMillis() >= exp) return false;
        }

        // Check WebSNMP hosts
        int maxHost = LicenseState.getMaxHost();
        if(maxHost != -1)
            if(DBSnmpHost.count(null,null) > maxHost) return false;
        
        // Check maxUser
        int maxUser = LicenseState.getMaxUser();
        if(maxUser != -1)
            if(DBUser.count(null,null) > maxUser) return false;

        if(!getLicenseId().equals(LicenseState.getLicenseId())) return false;

        return true;
    }

    public int getCurrentHost()
    {
        return DBSnmpHost.count(null,null);
    }

    public String getMaxHost()
    {
        int maxHost = LicenseState.getMaxHost();
        if(maxHost == -1) return null;
        else return Integer.toString(maxHost);
    }

    public int getCurrentUser()
    {
        return DBUser.count(null,null);
    }

    public String getMaxUser()
    {
        int maxUser = LicenseState.getMaxUser();
        if(maxUser == -1) return null;
        else return Integer.toString(maxUser);
    }


    public String getExpiryDate()
    {
        long exp = LicenseState.getExpiryDate();
        if(exp == -1) return null;

        SimpleDateFormat dateFormat
            = new SimpleDateFormat("dd'/'MM'/'yyyy HH':'mm");
        StringBuffer sb = dateFormat.format(new Date(exp), new StringBuffer(), new FieldPosition(0));
        return sb.toString();
    }

    public String getCompanyName()
    {
        return LicenseState.getCompanyName();
    }

    public String getVersion()
    {
    	return Configuration.getVERSION();
    }
    
    
    /*
    public String getCompiledOn()
    {
        ResourceBundle props = ResourceBundle.getBundle("compilation");
        String date = props.getString("COMPILEDON");
        if(date != null) return date;
        else return "Unknown";
    }
*/
    
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

}
