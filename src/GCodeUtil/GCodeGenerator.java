package GCodeUtil;

import java.util.ArrayList;

/**
 * Created by Adam Fowles on 1/10/2016.
 */
public class GCodeGenerator
{
    // Real time
    public static final String PAUSE = "!";
    public static final String RESUME = "~";
    public static final byte RESET = 0x18;
    // Non real time
    public static final String UNLOCK = "$X";
    public static final String HOME = "$H";


    /**
     * Generate the  G code lines for the arduino program
     * @param params - the parameters used to generate the code
     * @return - a list of strings for each line of code
     */
    public static ArrayList<String> generateLines(double[] params)
    {
        ArrayList<String> lines = new ArrayList<>();

        lines.add("G20");
        lines.add("G90 G01 Y0.0000");
        lines.add("G91 G01 Y" + params[4]);
        // Pause command
        //lines.add("G91 G00 Y0.0000\n");

        lines.add(PAUSE);

        lines.add("G91 G01 X" + params[1] + " Y" + params[5] + " F" + params[8]);
        lines.add("G91 G01 X" + params[2] + " Y0.0000" + " F" + params[7]);
        lines.add("G91 G01 X" + params[3] + " Y" + params[6] + " F" + params[8]);
        lines.add("G91 G01 X" + params[2] + " Y0.0000" + " F" + params[7]);
        lines.add("G91 G01 X" + params[1] + " Y" + params[5] + " F" + params[8]);

        return lines;
    }

    public static double[] modifyParameters(double[] params)
    {
        double tHalfLength = (params[5] - params[4])/2;
        double y1 = params[4] + tHalfLength - params[2]/2;
        double y2 = tHalfLength - params[2]/2;
        double y3 = -2*y2;
        double x1 = ((tHalfLength + params[2]/2)*params[1] * Math.PI)
                /(params[2]*((100-params[3])/100));
        double x2 = Math.PI*params[1]*1.5;
        double x3 = 2*x1;
        double f1 = Math.PI*params[6]*params[1];
        double f2 = (Math.sqrt(x1*x1 + y2*y2)/x1)*f1;
        if (f2 > 200*1.5*Math.PI)
        {
            f2 = 200*1.5*Math.PI;
            f1 = f2*(x1/Math.sqrt(x1*x1 + y2*y2));
        }
        return new double[]{tHalfLength, x1, x2, x3, y1, y2, y3, f1, f2};
    }

    public static String getGCodeRevolveMessage(double increment, double speed, int sign)
    {
        return "G20 G01 G91 X" + increment*sign + " F" + speed;
    }

    public static String getGCodeTranslateMessage(double increment, double speed, int sign)
    {
        return "G20 G01 G91 Y" + increment*sign + " F" + speed;
    }

    public static String getGCodeGoToZeroMessage(){return "G20 G90 Y0";}


    public static String[] getGCodeStopMessage(){return new String[]{"!", "M0"};}

}
