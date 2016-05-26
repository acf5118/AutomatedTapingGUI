package GCodeUtil;

import java.util.ArrayList;
import java.util.HashMap;

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

        lines.add("$H");
        lines.add("G20");
        lines.add("G91 G01 Y" + params[4] + " F" + params[8]);
        // Pause command
        //lines.add("G91 G00 Y0.0000\n");

        lines.add(PAUSE);

        lines.add("G91 G01 X" + -params[1] + " Y" + params[5] + " F" + params[8]);
        lines.add("G91 G01 X" + -params[2] + " Y0.0000" + " F" + params[7]);
        lines.add("G91 G01 X" + -params[3] + " Y" + -params[6] + " F" + params[8]);
        lines.add("G91 G01 X" + -params[2] + " Y0.0000" + " F" + params[7]);
        lines.add("G91 G01 X" + -params[1] + " Y" + params[5] + " F" + params[8]);

        return lines;
    }

    /**
     * Based on the input values from the users
     * create a set of values for the machine
     * @param params - the user input parameters
     * @return the values for the machine program to use
     */
    public static double[] modifyParameters(HashMap<String, Double> params)
    {
        double tHalfLength = (params.get(Strings.END) - params.get(Strings.START))/2;
        // first and fourth (technically) y displacement the motor goes to
        double y1 = params.get(Strings.START) + tHalfLength - params.get(Strings.TAPE_WIDTH)/2;
        // second y displacement
        double y2 = tHalfLength - params.get(Strings.TAPE_WIDTH)/2;
        // third y displacement
        double y3 = 2*y2;
        // first x displacement
        double x1 = ((tHalfLength + params.get(Strings.TAPE_WIDTH)/2)
                *params.get(Strings.DIAMETER) * Math.PI)
                /(params.get(Strings.TAPE_WIDTH)*((100-params.get(Strings.TAPE_OL_PERCENT))/100));
        // second x displacement
        double x2 = Math.PI*params.get(Strings.DIAMETER)*1.5;
        // third x displacement
        double x3 = 2*x1;
        // feedrate as a function of both translation and rotational motor
        double f1 = Math.PI*params.get(Strings.RPM)*params.get(Strings.DIAMETER);
        // feedrate to keep the roational motor at a constant RPM when translational
        // motor stops moving.
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
