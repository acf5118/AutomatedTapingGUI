package FileIO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Adam Fowles on 1/10/2016.
 */
public class ProgramWriter
{
    private double[] params;

    public ProgramWriter(double[] params)
    {
        this.params = params;
    }

    public void writeFile(String filename)
    {
        BufferedWriter writer = null;
        try {
            File f = new File(filename);
            if (!f.exists()) {
                f.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(filename);
            writer = new BufferedWriter(fileWriter);


            writer.write("c Program file\n");
            writer.write("c <length> <x1> <x2> <x3> <y1> <y2> <y3> <f1> <f2>\n");
            writer.write("p ");
            for (double d : params) {
                writer.write(d + " ");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Couldnt write file");
        }
    }
}
