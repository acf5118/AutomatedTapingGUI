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

    public void writeFile(File f)
    {
        BufferedWriter writer = null;
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            System.out.println(f.getPath());
            FileWriter fileWriter = new FileWriter(f.getPath());
            writer = new BufferedWriter(fileWriter);
            System.out.println("Wrote this file");

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
