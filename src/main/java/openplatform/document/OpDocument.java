package openplatform.document;

import java.io.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.rtf.*;
import openplatform.tools.*;


/**
 * Class OpDocument
 * 
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class OpDocument extends StreamConverter implements CstDoc
{
    // http://www.lowagie.com/iText/ for more informations

    private Document doc = new Document(PageSize.A4);
    private ByteArrayOutputStream osPdf = new ByteArrayOutputStream();
    private ByteArrayOutputStream osRtf = new ByteArrayOutputStream();
   
    private Paragraph paragraph;

    /**
     * Constructor
     */
    public OpDocument()
    {
        try
        {
            paragraph = newParagraph();

            doc.addAuthor("DANG CONSULTING");
            doc.addCreator("DANG CONSULTING");
            doc.addCreationDate();
            PdfWriter pw = PdfWriter.getInstance(doc, osPdf);
            RtfWriter rw = RtfWriter.getInstance(doc, osRtf);

            PdfDictionary pd = pw.getInfo();
            pd.put(PdfName.AUTHOR, new PdfString("DANG CONSULTING"));
            pd.put(PdfName.CREATOR, new PdfString("DANG CONSULTING"));
            pd.put(PdfName.PRODUCER, new PdfString("DANG CONSULTING"));

            doc.open();
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }


    /**
     * @return a new paragraph with the style
     */
    private Paragraph newParagraph()
    {
        Paragraph p = new Paragraph();
        p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
        return p;
    }


    /**
     * Add a PNG image 
     */
    public void addPNGImage(InputStream is)
    {
        byte[] b = inputStreamToBytes(is);
        addPNGImage(b);
    }


    /**
     * Add a PNG image 
     */
    public void addPNGImage(byte[] b)
    {
        try
        {
            Image img = Image.getInstance(b);
            img.setAlignment(Image.ALIGN_LEFT);
            paragraph.add(img);

            doc.add(paragraph);
            paragraph = newParagraph();
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }


    /**
     * Add a text 
     */
    public void addText(String s, Font font)
    {
        if(font == null) font=NORMAL_FONT;

        try
        {
            paragraph.add(new Phrase(s, font));
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }


    /**
     * Add text with normal font
     */
    public void addText(String s)
    {
        addText(s, null);
    }


    /**
     * Add a newline
     */
    public void addNewLine()
    {
        addText("\n", null);
    }


    /**
     * Add a table
     */
    public void addTable(OpTable table)
    {
        try
        {
            Table t = table.getTable();
            paragraph.add(t);
            doc.add(paragraph);

            // create a new paragraph (workaround for keeping style)
            paragraph = newParagraph();
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }





    /**
     * ByteArrayOutputStream are generated
     */
    private void endOfDocument()
    {
        try
        {
            if(doc.isOpen())
            {
                if(!paragraph.isEmpty())
                    doc.add(paragraph);

                doc.close();
            }
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }
    


    /**
     * The length of the stream
     */
    public int rtfLength()
    {
        try
        {
            endOfDocument();
            return osRtf.size();
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            return 0;
        }      
    }


    /**
     * The length of the stream
     */
    public int pdfLength()
    {
        try
        {
            endOfDocument();
            return osPdf.size();
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            return 0;
        }      
    }


    /**
     * Generate a RTF to a stream
     */
    public void generateRTF(OutputStream os)
    {   
        try
        {
            endOfDocument();
            osRtf.writeTo(os);
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }


    /**
     * Generate a PDF to a stream
     */
    public void generatePDF(OutputStream os)
    {
        try
        {
            endOfDocument();
            osPdf.writeTo(os);
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }


    /**
     * Save RTF to file
     */
    public void saveRTF(File file)
    {
        try
        {
            endOfDocument();
            FileOutputStream fos = new FileOutputStream(file, false);
            osRtf.writeTo(fos);
            fos.close();
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }


    /**
     * Save PDF to file
     */
    public void savePDF(File file)
    {
        try
        {
            endOfDocument();
            FileOutputStream fos = new FileOutputStream(file, false);
            osPdf.writeTo(fos);
            fos.close();
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }


    /**
     * Close all streams and liberate resource.
     * Once called, you cannot do nothing with that OpDocument
     */
    public void close()
    {
        try
        {
            doc.close();
            osPdf.close();
            osRtf.close();
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }

        doc=null;
        osPdf=null;
        osRtf=null;
    }





    public static void main(String[] args)
        throws Exception
    {
        OpDocument d = new OpDocument();
        
        File file = new File("/opt/kde/share/icons/crystalsvg/128x128/actions/artsaudiomanager.png");

        d.addPNGImage(new FileInputStream(file));
        d.addNewLine();
        d.addText("[texte normal]\n", OpDocument.NORMAL_FONT);
        d.addText("[texte en gras] ", OpDocument.BOLD_FONT);
        d.addText("[texte italic gras]\n", OpDocument.BOLD_ITALIC_FONT);
        d.addText("[texte italic]\n", OpDocument.ITALIC_FONT);
        d.addText("[souligné]\n", OpDocument.UNDERLINE_FONT);
        d.addText("[gras souligné]\n", OpDocument.BOLD_UNDERLINE_FONT);

        //          OpTable t = new OpTable("titre",4,3);
        //          t.addText(0,1,"nom", OpTable.BOLD_FONT);
        //          t.addText(0,2,"prenom",OpTable.BOLD_FONT);
        //          t.addText(1,0,"1", OpTable.BOLD_FONT);
        //          t.addText(2,0,"2", OpTable.BOLD_FONT);
        //          t.addText(3,0,"3", OpTable.BOLD_FONT);
        //          t.addText(1,1, "dang");
        //          t.addPNGImage(1,2, new FileInputStream(file));
        //          t.addText(2,1, "dang");
        //          t.addText(2,2, "minh");
        //          t.addText(3,1, "touboul");
        //          t.addText(3,2, "diane");
        
        OpTable t = new OpTable("titre",1,2);
        t.addPNGImage(0,0, new FileInputStream(file));
        t.addText(0,1, "test\ncoucou");


        d.addTable(t);

        d.addText("[texte normal]\n");

        d.savePDF(new File("coucou.pdf"));
        d.saveRTF(new File("coucou.rtf"));

        d.close();
    }
}
