package openplatform.document;

import java.awt.Point;
import java.io.InputStream;

import openplatform.tools.Debug;
import openplatform.tools.StreamConverter;

import com.lowagie.text.Cell;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;


/**
 * Class OpTable
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class OpTable extends StreamConverter implements CstDoc
{
    private Table table;

    public OpTable(String title, int rows, int columns)
    {
        try
        {
            table = new Table(columns, rows+1);
            table.setAutoFillEmptyCells(true);
            table.setPadding(4);
            table.setSpacing(0);
            table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
            table.setDefaultVerticalAlignment(Table.ALIGN_CENTER);
            table.setBorderColor(TABLE_BORDERCOLOR); // border color
            table.setDefaultCellBorderColor(TABLE_BORDERCOLOR); // border color
            table.setBackgroundColor(TABLE_BGCOLOR); // bg color
            table.setDefaultCellBackgroundColor(TABLE_BGCOLOR); // bg color

            Cell cell = new Cell(new Paragraph(title.toUpperCase(), TABLE_TITLE_FONT));
            cell.setHeader(true);
            cell.setColspan(columns);
            cell.setBackgroundColor(TABLE_BGTITLECOLOR); // bg color
            table.addCell(cell);
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }


    public void addPNGImage(int rows, int columns, byte[] b)
    {
        try
        {
            Image img = Image.getInstance(b);
            table.addCell(new Cell(img), new Point(rows+1, columns));
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }


    public void addPNGImage(int rows, int columns, InputStream is)
    {
        byte[] b = inputStreamToBytes(is);
        addPNGImage(rows, columns, b);
    }


    public void addText(int rows, int columns, String text, Font font)
    {
        if(font == null) font=NORMAL_FONT;

        try
        {
            Paragraph p = new Paragraph(text, font);
            table.addCell(p, new Point(rows+1, columns));
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }


    public void addText(int rows, int columns, String text)
    {
        addText(rows, columns, text, null);
    }



    protected Table getTable()
    {
        return table;
    }

}
