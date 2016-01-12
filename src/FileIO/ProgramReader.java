package FileIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Adam Fowles on 1/10/2016.
 */
public class ProgramReader
{
    private double[] params, mod;

    public ProgramReader()
    {

    }

    public void readFile(File file)
    {
        double length, x1, x2, x3, y1, y2, y3, f1, f2;
        params = new double[7];
        mod = new double[9];
        BufferedReader reader = null;
        String line, s[];
        // Checks on input
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (Exception e) {
            usage("Cannot find file " + file.getName());
        }
        try {
            //c <length> <x1> <x2> <x3> <y1> <y2> <y3> <f1> <f2>
            while ((line = reader.readLine()) != null) {
                s = line.split("\\s+");
                // c represents a comment line
                if (s[0].equals("c")) {
                    continue;
                }
                else if (s[0].equals("p1"))
                {
                    params[0] = Double.parseDouble(s[1]);
                    params[1] = Double.parseDouble(s[1]);
                    params[2] = Double.parseDouble(s[1]);
                    params[3] = Double.parseDouble(s[1]);
                    params[4] = Double.parseDouble(s[1]);
                    params[5] = Double.parseDouble(s[1]);
                    params[6] = Double.parseDouble(s[1]);
                }
                // p represents the parameters
                else if (s[0].equals("p2")) {
                    length = Double.parseDouble(s[1]);
                    x1 = Double.parseDouble(s[2]);
                    x2 = Double.parseDouble(s[3]);
                    x3 = Double.parseDouble(s[4]);
                    y1 = Double.parseDouble(s[5]);
                    y2 = Double.parseDouble(s[6]);
                    y3 = Double.parseDouble(s[7]);
                    f1 = Double.parseDouble(s[8]);
                    f2 = Double.parseDouble(s[9]);
                    mod[0] = length;
                    mod[1] = x1;
                    mod[2] = x2;
                    mod[3] = x3;
                    mod[4] = y1;
                    mod[5] = y2;
                    mod[6] = y3;
                    mod[7] = f1;
                    mod[8] = f2;
                    break;
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Not a number");
        }
    }
    /**
     * @param error - an error message with command line arguments
     */
    private static void usage(String error)
    {
        System.err.println(error);
        System.exit(1);
    }

    public double[] getMod(){return mod;}
    public double[] getParams(){return params;}

}
