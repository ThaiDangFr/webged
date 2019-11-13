package openplatform.mail;

import java.io.*;
import openplatform.tools.*;

/**
 * Class MailAttachmentPart
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class MailAttachmentPart extends MailPartInterface
{
    private byte[] data;


    public void setData(byte[] data)
    {
        this.data = data;
    }

    public byte[] getData()
    {
        return data;
    }

//     public void setData(File file)
//     {
//         if(file != null && file.exists())
//         {
//             data = StreamConverter.fileToBytes(file);
            
//             if(fileName == null)
//                 fileName = file.getName();
//         }
//     }


    public void setData(InputStream input, String fileName)
    {
        this.fileName = fileName;

        if(input != null)
        {
            data = StreamConverter.inputStreamToBytes(input);
        }
    }
}
