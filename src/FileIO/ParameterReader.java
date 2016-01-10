package FileIO;

import java.io.*;

/**
 * Created by Adam Fowles on 1/10/2016.
 */
public class ParameterReader
{
    private double[] params = new double[5];

    public ParameterReader(String file)
    {
        BufferedReader reader = null;
        String line, s[];
        double xInc, yInc, xSpeed, ySpeed, maxRPM;

        // Checks on input
        try
        {
            reader = new BufferedReader(new FileReader(file));
        }
        catch (Exception e)
        {
            usage("Cannot find file " + file);
        }
        try
        {

            while ((line = reader.readLine()) != null)
            {
                s = line.split("\\s+");
                // c represents a comment line
                if (s[0].equals("c")){continue;}
                // p represents the parameters
                else if (s[0].equals("p"))
                {
                    xInc = Double.parseDouble(s[1]);
                    xSpeed = Double.parseDouble(s[2]);
                    yInc = Double.parseDouble(s[3]);
                    ySpeed = Double.parseDouble(s[4]);
                    maxRPM = Double.parseDouble(s[5]);
                    params[0] = xInc;
                    params[1] = xSpeed;
                    params[2] = yInc;
                    params[3] = ySpeed;
                    params[4] = maxRPM;
                    break;
                }
            }
        }
        catch (IOException |NumberFormatException e)
        {
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

    public double[] getParams(){return params;}
}
