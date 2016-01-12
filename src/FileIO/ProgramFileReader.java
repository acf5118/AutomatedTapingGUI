package FileIO;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Adam Fowles on 1/10/2016.
 */
public class ProgramFileReader
{
    // Other files may also need this, public access
    public static final String PARAMETER_FILE = "Parameter Settings";
    private static final String
            DEFAULT_FILE = "/Main/Default Parameter Settings";
    private static final int NUM_PARAMS = 5;

    /**
     * Reads a program file in and returns a "tuple" (list of double[])
     * object containing the parsed data.
     * @param file - the file to read from
     * @return - the read in data.
     */
    public static ArrayList<double[]> readProgramFile(File file)
    {
        double[] params = new double[7];
        double[] mod = new double[9];
        ArrayList<double[]> returnTuple = new ArrayList<>();
        BufferedReader reader = null;
        String line, s[];
        // Checks on input
        try
        {
            reader = new BufferedReader(new FileReader(file));
        } catch (Exception e)
        {
            usage("Cannot find file " + file.getName());
        }
        try
        {
            while ((line = reader.readLine()) != null)
            {
                s = line.split("\\s+");
                // c represents a comment line
                if (s[0].equals("c")) {
                    continue;
                }
                //c <length> <x1> <x2> <x3> <y1> <y2> <y3> <f1> <f2>
                else if (s[0].equals("p1"))
                {
                    params[0] = Double.parseDouble(s[1]);
                    params[1] = Double.parseDouble(s[2]);
                    params[2] = Double.parseDouble(s[3]);
                    params[3] = Double.parseDouble(s[4]);
                    params[4] = Double.parseDouble(s[5]);
                    params[5] = Double.parseDouble(s[6]);
                    params[6] = Double.parseDouble(s[7]);
                }
                // p represents the parameters
                else if (s[0].equals("p2"))
                {
                    // length
                    mod[0] = Double.parseDouble(s[1]);
                    // x1
                    mod[1] = Double.parseDouble(s[2]);
                    // x2
                    mod[2] = Double.parseDouble(s[3]);
                    // x3
                    mod[3] = Double.parseDouble(s[4]);
                    // y1
                    mod[4] = Double.parseDouble(s[5]);
                    // y2
                    mod[5] = Double.parseDouble(s[6]);
                    // y3
                    mod[6] = Double.parseDouble(s[7]);
                    // f1
                    mod[7] = Double.parseDouble(s[8]);
                    // f2
                    mod[8] = Double.parseDouble(s[9]);
                    break;
                }
            }
        }
        catch (IOException | NumberFormatException e)
        {
            System.out.println("Not a number");
        }
        returnTuple.add(params);
        returnTuple.add(mod);

        return returnTuple;
    }

    /**
     * Reads in the parameter file, either default or user created
     * depending on what exists.
     * @param callingClass - class used to get default
     *                     resources.
     * @return - the parameters as a double array
     */
    public static double[] readParameterFile(Class callingClass)
    {
        BufferedReader reader = null;
        String line, s[];
        double[] params = new double[NUM_PARAMS];

        // Try and get a file called Parameter Settings
        // if it does not exist the user has not created default settings
        // before use the internal default parameter settings file
        try
        {
            reader = new BufferedReader(new
                    FileReader(PARAMETER_FILE));
        }
        catch (FileNotFoundException e)
        {
            // This is where the internal
            // default settings file will be retrieved instead
            try
            {
                reader = new BufferedReader(new InputStreamReader(
                        callingClass.getClass().getResourceAsStream(
                                DEFAULT_FILE)));
            }
            // get resource as stream method
            // does inherently not throw any exceptions however a non-existent
            // file will result in an exception
            catch(Exception e1)
            {
                usage("Internal State has been modified, " +
                        "no default settings file found");
            }
        }

        // Surround the reader loop in try catch in case
        // of incorrect input errors
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
                    // x increment
                    params[0] = Double.parseDouble(s[1]);
                    // x speed
                    params[1] = Double.parseDouble(s[2]);
                    // y increment
                    params[2] = Double.parseDouble(s[3]);
                    // y speed
                    params[3] = Double.parseDouble(s[4]);
                    // max RPM
                    params[4] = Double.parseDouble(s[5]);
                    break;
                }
            }
        }
        catch (IOException |NumberFormatException e)
        {
            usage("Parameter file corrupt, bad input");
        }

        return params;
    }

    /**
     * Quick usage message that quits the program
     * cannot proceed with such errors.
     * @param error - an error message
     */
    private static void usage(String error)
    {
        System.err.println(error);
        System.exit(1);
    }
}
