package GCodeUtil;

/**
 * Created by Adam Fowles on 1/10/2016.
 */
public class GCodeMessages
{
    public static String getGCodeRevolveMessage(double increment, double speed, int sign)
    {
        return "G20 G01 G91 X" + increment*sign + " F" + speed;
    }

    public static String getGCodeTranslateMessage(double increment, double speed, int sign)
    {
        return "G20 G01 G91 Y" + increment*sign + " F" + speed;
    }

    public static String getGCodeZeroMessage(double speed){return "G20 G01 G90 Y0.0000 F" + speed; }
}
