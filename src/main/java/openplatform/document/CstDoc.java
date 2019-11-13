package openplatform.document;


import com.lowagie.text.*;
import com.lowagie.text.rtf.*;
import openplatform.tools.*;
import java.awt.Color;


public interface CstDoc
{
    public final static Color TABLE_BGTITLECOLOR = ColorUtils.getColor("92afcb");
    public final static Color TABLE_BGCOLOR = ColorUtils.getColor("d7dfe6");
    public final static Color TABLE_BORDERCOLOR = ColorUtils.getColor("6499cb");
    public final static Font TABLE_TITLE_FONT = new RtfFont(FontFactory.COURIER, 12, Font.BOLD, Color.WHITE);


    public final static Color COLOR_NORMAL = ColorUtils.getColor("11197f");
    public final static Color COLOR_ITALIC = ColorUtils.getColor("343ca0");
    public final static Color COLOR_BOLD = ColorUtils.getColor("8c0101");
    public final static Color COLOR_UNDERLINE = ColorUtils.getColor("0a8c01");


    public static final Font NORMAL_FONT = new RtfFont(FontFactory.COURIER, 12, Font.NORMAL,COLOR_NORMAL);
    public static final Font ITALIC_FONT = new RtfFont(FontFactory.COURIER, 12, Font.ITALIC,COLOR_ITALIC);
    public static final Font BOLD_FONT = new RtfFont(FontFactory.COURIER, 12, Font.BOLD,COLOR_BOLD);
    public static final Font BOLD_ITALIC_FONT = new RtfFont(FontFactory.COURIER, 12, Font.BOLD|Font.ITALIC,COLOR_BOLD);
    public static final Font UNDERLINE_FONT = new RtfFont(FontFactory.COURIER, 12, Font.UNDERLINE,COLOR_UNDERLINE);
    public static final Font BOLD_UNDERLINE_FONT = new RtfFont(FontFactory.COURIER, 12, Font.BOLD|Font.UNDERLINE,COLOR_BOLD);


    


}
