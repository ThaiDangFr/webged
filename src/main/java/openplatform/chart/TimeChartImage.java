package openplatform.chart;

import org.jfree.data.time.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import java.io.*;
import java.awt.image.*;
import java.awt.*;
import openplatform.tools.*;
import org.jibble.epsgraphics.EpsGraphics2D;

/**
 * Class TimeChartImage
 *
 * TimeChart permits to construct a time based graphics.
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class TimeChartImage
{
    private String title;
    private String timelabel;
    private String valuelabel;

    private TimePeriodValuesCollection dataset;

    private static final Color white = new Color(255, 255, 255, 255);


    public TimeChartImage(String title, String timelabel, String valuelabel)
    {
        this.title = title;
        this.timelabel = timelabel;
        this.valuelabel = valuelabel;
        dataset = new TimePeriodValuesCollection();
    }


    public void addTimeChart(TimeChart timechart)
    {
        dataset.addSeries(timechart.getSeries());
    }


    /**
     * Get a PNG image
     **/
    public byte[] getPNGBytes(int width, int height)
    {
        try
        {
            JFreeChart chart = ChartFactory.createTimeSeriesChart(title, timelabel, valuelabel, dataset, true, true, false);
            chart.setBackgroundPaint(white);

            BufferedImage bi = chart.createBufferedImage(width, height);
            return ChartUtilities.encodeAsPNG(bi, true, 9);
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            return null;
        }
    }

    
    /**
     * Get a EPS image (vectorial)
     */
    public byte[] getEPSBytes(int width, int height)
    {
        try
        {
            JFreeChart chart = ChartFactory.createTimeSeriesChart(title, timelabel, valuelabel, dataset, true, true, false);
            chart.setBackgroundPaint(white);
//             StandardLegend sl = new StandardLegend();
//             sl.setItemFont(new Font("Arial", Font.PLAIN, 13));
//             sl.setOutlineStroke(new BasicStroke(1));
//             chart.setLegend(sl);
            


            EpsGraphics2D eps = new EpsGraphics2D("DangConsulting");

            /*
              Sets whether to use accurate text mode when rendering text in EPS. This is enabled (true) by default. When accurate text mode is used, all text will be rendered in EPS to appear exactly the same as it would do when drawn with a Graphics2D context. With accurate text mode enabled, it is not necessary for the EPS viewer to have the required font installed.
              Turning off accurate text mode will require the EPS viewer to have the necessary fonts installed. If you are using a lot of text, you will find that this significantly reduces the file size of your EPS documents. AffineTransforms can only affect the starting point of text using this simpler text mode - all text will be horizontal.
            
              eps.setAccurateTextMode(false);
              eps.setFont(new Font("Arial", Font.BOLD, 13));
              eps.setStroke(new BasicStroke(1));
            */

            chart.draw(eps, new Rectangle(width,height)); 
            //             String seps = eps.toString().replaceAll("0.0 setmiterlimit", "1 setmiterlimit");
            String seps = eps.toString();

            StringBuffer sb = new StringBuffer(seps);
            StringBuffer nsb = new StringBuffer();

            int begin = sb.indexOf("%%Creator");
            if(begin != -1)
            {
                int end = sb.indexOf("\n", begin);
                if(end != -1)
                {
                    nsb.append(sb.substring(0, begin));
                    nsb.append("%%Creator: DangConsulting http://www.dangconsulting.fr\n");
                    nsb.append(sb.substring(end+1));
                }
                else
                    nsb = sb;
            }
            else
                nsb = sb;

            return nsb.toString().getBytes("UTF-8");
        }
        catch(Exception e)
        {
            return null;
        }
    }


    /**
     * Save the EPS on disk
     */
    public void saveEPS(int width, int height, File file)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(file, false);
            byte[] epsb = getEPSBytes(width, height);
            if(epsb==null)
            {
                Debug.println(this, Debug.ERROR, "EPS bytes is null");
                return;
            }
            
            fos.write(epsb);
            fos.flush();
            fos.close();
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }


    /**
     * Save the PNG to a file on disk
     **/
    public void savePNG(int width, int height, File file)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(file, false);
            byte[] pngb = getPNGBytes(width, height);
            if(pngb==null)
            {
                Debug.println(this, Debug.ERROR, "PNG bytes is null");
                return;
            }
            
            fos.write(pngb);
            fos.flush();
            fos.close();
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }
}
