package Components.Menu;

import FileIO.ProgramFileReader;
import GCodeUtil.ArrayMapTuple;
import GCodeUtil.GCodeGenerator;
import Main.LaMachinaGui;
import Serial.SerialCommunication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Adam Fowles on 1/24/2016.
 * The File Menu
 */
public class FileMenu extends Menu
{
    private LaMachinaGui parentGui;
    private SerialCommunication serialCommunication;
    private FileChooser fc;
    /**
     * Constructor for the file menu
     * @param parentGui
     */
    public FileMenu(LaMachinaGui parentGui,
                    SerialCommunication comm)
    {
        super("File");
        this.parentGui = parentGui;
        this.serialCommunication = comm;
        createComponents();
    }

    /**
     * Creates all of the menu items
     * for the file menu
     */
    public void createComponents()
    {
        // Opens a menu item to load a new program
        MenuItem miOpen = new MenuItem("Open Program File");
        fc = new FileChooser();
        miOpen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                File file = fc.showOpenDialog(parentGui.getPrimaryStage());
                if (file == null){return;}
                ArrayMapTuple pr = ProgramFileReader.readProgramFile(file);
                parentGui.updateProgramParameters(pr.params, pr.mod, file);
            }
        });
        // Quits the program
        MenuItem miQuit = new MenuItem("Quit");
        miQuit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parentGui.quit();
            }
        });
        // Resets the serial connections
        MenuItem miReset = new MenuItem("Reset");
        miReset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                serialCommunication.sendByte(GCodeGenerator.RESET);
            }
        });
        // Add all the menu items
        getItems().addAll(miOpen, miReset, miQuit);
    }

    public void updateFileChooser(File f)
    {
        fc.setInitialDirectory(f.getParentFile());
    }
}
