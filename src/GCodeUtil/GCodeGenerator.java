package GCodeUtil;

import java.util.ArrayList;

/**
 * Created by Adam Fowles on 1/10/2016.
 */
public class GCodeGenerator
{
    public GCodeGenerator()
    {

    }

    public ArrayList<String> generateLines(double[] params)
    {
        ArrayList<String> lines = new ArrayList<>();

        lines.add("G20\n");
        lines.add("G91 G00 Y" + params[4] + "\n");
        // Needs to pause GCode
        lines.add("G91 G01 X" + params[1] + " Y" + params[5] + " F" + params[8] + "\n");
        lines.add("G91 G01 X" + params[2] + " F" + params[7] + "\n");
        lines.add("G91 G01 X" + params[3] + " Y" + params[6] + " F" + params[8] + "\n");
        lines.add("G91 G01 X" + params[2] + " F" + params[7] + "\n");
        lines.add("G91 G01 X" + params[1] + " Y" + params[5] + " F" + params[8] + "\n");
        for (String s: lines)
        {
            System.out.println(s);
        }
        return lines;
    }

}
