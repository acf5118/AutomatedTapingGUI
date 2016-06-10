package Components;

import FileIO.ProgramFileWriter;
import GCodeUtil.GCodeGenerator;
import GCodeUtil.Strings;
import Main.LaMachinaGui;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Adam Fowles on 1/7/2016.
 */
public class FileControlHBox
        extends HBox implements ComponentInterface
{
    private PartCreationVBox parent;
    private LaMachinaGui parentGui;
    private Label lblErrors;
    private boolean errors;
    private FileChooser fileChooser;
    // Todo possibly create a file extension for this application
    private FileChooser.ExtensionFilter extensionFilter;

    /**
     * Constructor
     * @param parent - takes in the parent so it
     *               can save and erase data
     * @param gui - the parent gui
     */
    public FileControlHBox(PartCreationVBox parent, LaMachinaGui gui)
    {
        super();
        this.parent = parent;
        this.parentGui = gui;
        errors = false;
        setSpacing(SPACING / 2);
        createComponents();
    }

    /**
     * Creates the file control components
     */
    public void createComponents()
    {
        // Save button
        Button btnSave = new Button();
        btnSave.setTooltip(new Tooltip("Save Contents"));
        btnSave.setGraphic(new ImageView(new Image(
                getClass().getResourceAsStream("/Resources/Save.png"))));
        btnSave.setOnAction(new SaveEventHandler());
        // Erase button
        Button btnErase = new Button();
        btnErase.setOnAction(new EraseEventHandler());
        btnErase.setTooltip(new Tooltip("Clear Contents"));
        btnErase.setGraphic(new ImageView(new Image(
                getClass().getResourceAsStream("/Resources/Eraser.png"))));
        // Label for displaying error messages
        lblErrors = new Label();
        // Set the label color to red
        lblErrors.setStyle("-fx-text-fill: #e60000;");
        fileChooser = new FileChooser();
        extensionFilter = fileChooser.getSelectedExtensionFilter();
        getChildren().addAll(btnSave,btnErase, lblErrors );
    }

    /**
     * Event handler class for Save button
     */
    private class SaveEventHandler
            implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            // List of values to populate from fields
            ArrayList<Double> values = new ArrayList<>();
            // List of fields from Part Creation
            ArrayList<TextField> fields = parent.getFields();


            // Whether or not there are errors with the
            // input data
            errors = false;
            for (TextField tf: fields)
            {
                // If there is nothing in the text field
                if (tf.getText().equals(""))
                {
                    tf.getStyleClass().add("persistent-prompt-error");
                    errors = true;
                }
                else
                {
                    // try to parse the input data
                    try
                    {
                        Double.parseDouble(tf.getText());
                    }
                    // catch an error
                    catch(NumberFormatException e)
                    {
                        tf.getStyleClass().add("persistent-prompt-error");
                        errors = true;
                    }
                }
            }

            // If there are no errors, a save file can
            // be created
            if (!errors)
            {
                HashMap<String, Double> userParams = parent.getFieldValues();
                // Numerical Error Handling (i.e numbers don't make sense)
                if (userParams.get(Strings.TAPE_WIDTH) + userParams.get(Strings.END) > 25.8)
                {
                    errors = true;
                    lblErrors.setText("Tape Width plus End\n" +
                            "beyond bounds of machine");
                    return;
                }
                if (userParams.get(Strings.TAPE_WIDTH) >= (userParams.get(Strings.END) - userParams.get(Strings.START)))
                {
                    errors = true;
                    lblErrors.setText("Tape too wide for tape section");
                    return;
                }
                // Remove any lingering style sheet
                for (TextField tf: fields)
                {
                    tf.getStyleClass().remove("persistent-prompt-error");
                }
                // Reset error text field
                lblErrors.setText("");

                /*
                //TODO should convert values to double[]...
                double[] d = new double[values.size()];
                for (int i = 0; i < values.size(); i++)
                {
                    d[i] = values.get(i);
                }
                */
                // Pick a file


                File file = fileChooser.showSaveDialog(parentGui.getPrimaryStage());
                // If they canceled the file choosing
                if (file == null)
                {
                    return;
                }
                fileChooser.setInitialDirectory(file.getParentFile());

                // Create the modified parameters
                double [] mod = GCodeGenerator.modifyParameters(userParams);
                // If the load file is supposed to update the current program
                if (parent.loadChanges())
                {
                    // Update the GUI
                    parentGui.updateProgramParameters(userParams, mod, file);
                }
                // Write the file
                ProgramFileWriter.writeFile(file, userParams, mod);
            }
            else
            {
                lblErrors.setText("There are one or more" +
                        "\nerrors that need to be fixed");
            }
        }
    }

    /**
     * Event Handler Class for the Clear button
     * (looks like an eraser).
     */
    private class EraseEventHandler
            implements EventHandler<ActionEvent>
    {
        /**
         * Loops through all the text fields
         * and clears them.
         * @param event - unused
         */
        @Override
        public void handle(ActionEvent event)
        {
            // If there were/are errors clear the red styling
            if (errors)
            {
                for (TextField tf: parent.getFields())
                {
                    tf.getStyleClass().remove("persistent-prompt-error");
                }
                lblErrors.setText("");
            }
            // clear all the text fields
            for (TextField tf: parent.getFields())
            {
                tf.clear();
            }
        }
    }

    /**
     * If the user opens a file, set the directory
     * path to that.
     * @param f - the file to use as a current directory
     */
    public void updateFileChooserPath(File f)
    {
        fileChooser.setInitialDirectory(f.getParentFile());
    }
}
