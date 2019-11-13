package openplatform.mail;

import javax.activation.*;
import java.io.*;
import openplatform.database.dbean.*;

/**
 * Class BinaryDataSource
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class BinaryDataSource implements DataSource
{
    private String name;
    private String contentType = "application/octet-stream";
    private ByteArrayOutputStream baos = new ByteArrayOutputStream();



    public BinaryDataSource(String name, byte[] data)
    {
        this.name = name;

        baos.write(data, 0, data.length);

        if(name != null)
        {
            int index = name.lastIndexOf(".");
            if(index != -1)
            {
                String ext = name.substring(index+1);
                DBMimeTypes mask = new DBMimeTypes();
                mask.setExtension(ext);
                DBMimeTypes mt = DBMimeTypes.loadByKey(mask);
                if(mt != null)
                {
                    contentType = mt.getPrimaryType()+"/"+mt.getSecondaryType();
                }
            }
        }
        
    }


    /**
     * @return the mime types according to the table DBMimeTypes
     */
    public String getContentType()
    {
        return contentType;
    }

    public InputStream getInputStream()
        throws java.io.IOException
    {
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public String getName()
    {
        return name;
    }

    public OutputStream getOutputStream()
        throws java.io.IOException
    {
        return baos;
    }
}
