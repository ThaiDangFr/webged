package dang.websnmp;

import openplatform.tools.*;
import java.text.*;

/**
 * Class ConstantTime
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class ConstantTime extends TimeConverter implements openplatform.chart.TimeConstants
{

    public static int[] REPORT_TIME_RANGE = new int[]
    {
        30*MINUTE,
        HOUR,
        2*HOUR,
        3*HOUR,
        4*HOUR,
        5*HOUR,
        6*HOUR,
        12*HOUR,
        DAY,
        WEEK,
        MONTH
    };


    public static int[] REPORT_FREQUENCY = new int[]
    {
        5*MINUTE,
        10*MINUTE,
        30*MINUTE,
        HOUR,
        2*HOUR,
        3*HOUR,
        4*HOUR,
        5*HOUR,
        6*HOUR,
        12*HOUR,
        DAY,
        WEEK,
        MONTH
    };

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd'/'MM'/'yyyy'@'HH':'mm");

    public static final FieldPosition fieldpos0 = new FieldPosition(0);
}
