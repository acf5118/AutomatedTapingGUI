package FileIO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Adam Fowles on 1/10/2016.
 */
public class ProgramFileWriter
{
    /**
     * Write a program file with both the input program
     * parameters and the modified equation parameters
     * used in the g code.
     * @param f - the file to write to
     * @param params - the input parameters (from part creation)
     * @param modified - the modified equation parameters
     */
    public static void writeFile(File f, double[] params, double[] modified)
    {
        BufferedWriter writer;
        try
        {
            // If we are writing a new file
            if (!f.exists()) {f.createNewFile();}

            FileWriter fileWriter = new FileWriter(f.getPath());
            writer = new BufferedWriter(fileWriter);

            // Write in some file comments
            writer.write("c Program file\n");
            writer.write("c <length> <diameter> <Tape Width>" +
                    " <Tape Overlap %> <Start> <End> <Part RPM>\n");
            // Write the first section - input parameters
            writer.write("p1 ");
            for (double d : params) {writer.write(d + " ");}
            // Another file comment
            writer.write("\nc <length> <x1> <x2>" +
                    " <x3> <y1> <y2> <y3> <f1> <f2>\n");
            // Write the second section - the g code parameters
            writer.write("p2 ");
            for (double d: modified) {writer.write(d + " ");}
            // Close the writer
            writer.close();
        }
        catch (IOException e)
        {
            //TODO let the user know about this
            System.out.println("Couldn't write file");
        }
    }

    /**
     * Write out the parameters file used in movement control
     * (using buttons)
     * @param params - the parameters to write out
     */
    public static void writeParameterFile(double[] params)
    {
        BufferedWriter writer;
        try
        {
            FileWriter fileWriter = new FileWriter(
                    ProgramFileReader.PARAMETER_FILE);
            writer = new BufferedWriter(fileWriter);
            // Write some comments
            writer.write("c Parameter file\n");
            writer.write("c <x increment> <x speed> " +
                    "<y increment> <y speed> <max part RPM>\n");
            // Write the parameters out
            writer.write("p ");
            for (double d: params) {writer.write(d + " ");}
            writer.close();
        }

        catch(IOException e)
        {
            //TODO let the user know about this
            System.out.println("Couldn't write file");
        }
    }
}
