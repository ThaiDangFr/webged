package openplatform.tools;

import java.awt.*;

/**
 * Class ColorUtils
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class ColorUtils
{
    public static Color getColor(String hexa)
    {
         String hR, hG, hB;
         int r, g, b;

         hR = hexa.substring(0, 2);
         hG = hexa.substring(2, 4);
         hB = hexa.substring(4, 6);

         try
         {
              r = Integer.parseInt(hR, 16);
              g = Integer.parseInt(hG, 16);
              b = Integer.parseInt(hB, 16);
              return new Color(r,g,b);
         }
         catch(Exception e)
         {
             Debug.println(null, Debug.ERROR, e);
         }
         return null;
    }
}
