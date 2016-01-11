package Components;

import FileIO.ProgramWriter;
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

    /**
     * Constructor
     * @param parent - takes in the parent so it
     *               can save and erase data
     */
    public FileControlHBox(PartCreationVBox parent, LaMachinaGui gui)
    {
        super();
        this.parent = parent;
        setSpacing(SPACING / 2);
        this.parentGui = gui;
        createComponents();
        errors = false;
    }

    /**
     * Creates the file control components
     */
    public void createComponents()
    {
        // Maybe want to have unit options
        /*
        Label lblUnits = new Label("Units:");
        lblUnits.setAlignment(Pos.BOTTOM_CENTER);
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Metric",
                        "Imperial"
                );
        ComboBox<String> comboUnits = new ComboBox(options);
        comboUnits.setValue(options.get(0));
        */

        Button btnSave = new Button();
        btnSave.setTooltip(new Tooltip("Save Contents"));
        btnSave.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/Resources/Save.png"))));
        btnSave.setOnAction(new SaveEventHandler());
        Button btnErase = new Button();
        btnErase.setOnAction(new EraseEventHandler());
        btnErase.setTooltip(new Tooltip("Clear Contents"));
        btnErase.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/Resources/Eraser.png"))));

        lblErrors = new Label();
        lblErrors.setStyle("-fx-text-fill: #e60000;");
        //Region spacer = new Region();
        //HBox.setHgrow(spacer, Priority.ALWAYS);

        getChildren().addAll(btnSave,btnErase, lblErrors );//,spacer,lblUnits,comboUnits);

    }

    private class SaveEventHandler
            implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            ArrayList<Double> values = new ArrayList<>();
            ArrayList<TextField> fields = parent.getFields();
            errors = false;
            for (TextField tf: fields)
            {
                if (tf.getText().equals(""))
                {
                    tf.getStyleClass().add("persistent-prompt-error");
                    errors = true;
                }
                else
                {
                    try
                    {
                        values.add(Double.parseDouble(tf.getText()));
                    }
                    catch(NumberFormatException e)
                    {
                        tf.getStyleClass().add("persistent-prompt-error");
                        errors = true;
                    }
                }
            }

            if (!errors)
            {
                for (TextField tf: fields)
                {
                    tf.getStyleClass().remove("persistent-prompt-error");
                }
                lblErrors.setText("");
                double[] d = new double[values.size()];
                for (int i = 0; i < values.size(); i++) {
                    d[i] = values.get(i);
                }
                FileChooser fc = new FileChooser();
                File file = fc.showSaveDialog(parentGui.getPrimaryStage());
                if (file == null)
                {
                    return;
                }
                errors = false;
                modifyParams(d, file);
                System.out.println("Saved");
            }
            else
            {
                lblErrors.setText("There are one or more\nerrors that need to be fixed");

            }
        }

        public void modifyParams(double[] params, File writeTo)
        {
            double tHalfLength = (params[5] - params[4])/2;
            double y1 = params[4] + tHalfLength - params[2]/2;
            double y2 = tHalfLength - params[2]/2;
            double y3 = -2*y2;
            double x1 = ((tHalfLength + params[2]/2)*params[1] * Math.PI)
                    /(params[2]*((100-params[3])/100));
            double x2 = Math.PI*params[1]*1.5;
            double x3 = 2*x1;
            double f1 = Math.PI*params[6]*params[1];
            double f2 = (Math.sqrt(x1*x1 + y2*y2)/x1)*f1;
            if (f2 > 200*1.5*Math.PI)
            {
                f2 = 200*1.5*Math.PI;
                f1 = f2*(x1/Math.sqrt(x1*x1 + y2*y2));
            }
            double[] mod = new double[]{tHalfLength, x1, x2, x3, y1, y2, y3, f1, f2};
            ProgramWriter pw = new ProgramWriter(mod);
            parentGui.updateProgramParameters(mod);
            pw.writeFile(writeTo);
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
            if (errors)
            {
                for (TextField tf: parent.getFields())
                {
                    tf.getStyleClass().remove("persistent-prompt-error");
                }
                lblErrors.setText("");
            }
            for (TextField tf: parent.getFields())
            {
                tf.clear();
            }
            System.out.println("Cleared");
        }
    }
}
