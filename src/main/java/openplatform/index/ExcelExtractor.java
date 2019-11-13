package openplatform.index;

import org.apache.poi.poifs.filesystem.*;
import org.apache.poi.hssf.usermodel.*;
import java.util.*;
import java.io.*;

/**
 * ExcelExtractor.java
 *
 *
 * Created: Thu May 12 12:44:07 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class ExcelExtractor 
{
    public static String extractText(InputStream in) throws Exception
    {
        StringBuffer sb = new StringBuffer();
        POIFSFileSystem fs = new POIFSFileSystem(in);
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        
        int len = wb.getNumberOfSheets();
        for(int i=0; i<len; i++)
        {
            HSSFSheet sheet = wb.getSheetAt(i);
            Iterator it = sheet.rowIterator();
            
            while(it.hasNext())
            {
                HSSFRow row = (HSSFRow)it.next();
                Iterator itCell = row.cellIterator();
                while(itCell.hasNext())
                {
                    HSSFCell cell = (HSSFCell)itCell.next();

                    int type = cell.getCellType();
                    if(type == HSSFCell.CELL_TYPE_STRING 
                       || type == HSSFCell.CELL_TYPE_FORMULA)
                    {
                        String value = cell.getStringCellValue();
                        sb.append(" ").append(value);
                    }
                    else if(type == HSSFCell.CELL_TYPE_NUMERIC)
                    {
                        double d = cell.getNumericCellValue();
                        sb.append(" ").append(d);
                    }
                }
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) throws Exception
    {
        FileInputStream fis = new FileInputStream("TicketsRestaurant.xls");
        System.out.println(extractText(fis));
    }
}
