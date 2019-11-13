package dang.mailarchive;

/**
 * MailArchiveAttachments.java
 *
 *
 * Created: Mon Oct 24 20:21:42 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class MailArchiveAttachments 
{
    private String b64message;
    private String filename;
    private String fileBaseId;
    /**
     * Gets the value of fileBaseId
     *
     * @return the value of fileBaseId
     */
    public final String getFileBaseId()
    {
        return this.fileBaseId;
    }

    /**
     * Sets the value of fileBaseId
     *
     * @param argFileBaseId Value to assign to this.fileBaseId
     */
    public final void setFileBaseId(final String argFileBaseId)
    {
        this.fileBaseId = argFileBaseId;
    }



    /**
     * Gets the value of b64message
     *
     * @return the value of b64message
     */
    public final String getB64message()
    {
        return this.b64message;
    }

    /**
     * Sets the value of b64message
     *
     * @param argB64message Value to assign to this.b64message
     */
    public final void setB64message(final String argB64message)
    {
        this.b64message = argB64message;
    }

    /**
     * Gets the value of filename
     *
     * @return the value of filename
     */
    public final String getFilename()
    {
        return this.filename;
    }

    /**
     * Sets the value of filename
     *
     * @param argFilename Value to assign to this.filename
     */
    public final void setFilename(final String argFilename)
    {
        this.filename = argFilename;
    }

    
}
