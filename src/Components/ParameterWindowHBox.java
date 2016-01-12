package Components;

import FileIO.ProgramFileReader;
import FileIO.ProgramFileWriter;
import Main.LaMachinaGui;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Adam Fowles on 1/9/2016.
 */
public class ParameterWindowHBox
        extends VBox implements ComponentInterface
{
    // private fields
    private double[] params;
    private TextField tfIncRotate, tfFeedRateRotate,
            tfIncTranslate, tfFeedRateTranslate;
    private Stage window;
    private LaMachinaGui parent;

    /**
     * Constructor, calls the super for a VBox
     * and create the inner components
     * @param params - the parameter settings as a double[]
     * @param parent - the parent window
     */
    public ParameterWindowHBox(double[] params,
                               Stage window,
                               LaMachinaGui parent)
    {
        super(SPACING);
        this.params = params;
        this.window = window;
        this.parent = parent;
        createComponents();
    }

    /**
     * Creates all of the components
     * may be adding more than just the one column
     * in the future.
     */
    @Override
    public void createComponents()
    {
        createColumn();
        // Set the padding for the window
        setPadding(new Insets(SPACING,SPACING,SPACING,SPACING));
    }

    /**
     * Creates all of the components in the first
     * column.
     */
    public void createColumn()
    {
        // Top Label
        Label lblJog = new Label("Jog");
        lblJog.setAlignment(Pos.CENTER);

        // The separator line
        Separator spacer = new Separator();

        // All of the text fields to be used
        tfIncRotate = new TextField();
        tfFeedRateRotate = new TextField();
        tfIncTranslate = new TextField();
        tfFeedRateTranslate = new TextField();

        // Create the rotate (x) box
        VBox v1 = createBox(tfIncRotate,
                tfFeedRateRotate, "Rotate", params[0], params[1]);

        // Create the translate (y) box
        VBox v2 = createBox(tfIncTranslate,
                tfFeedRateTranslate, "Translate", params[2], params[3]);

        // Create the row for the buttons
        HBox r3 = new HBox(SPACING);

        // Create all of the buttons
        Button btnDefault = new Button("Default");
        // What to do on button click
        btnDefault.setOnAction(new DefaultEventHandler());
        Button btnApply = new Button("Apply");
        btnApply.setOnAction(new ApplyEventHandler());
        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.close();
            }
        });

        // Add them all to the third row
        r3.getChildren().addAll(btnDefault, btnApply, btnCancel);

        // Add them all to this VBox
        getChildren().addAll(lblJog, spacer, v1, v2, r3);

    }

    /**
     * Creates the two boxes shown
     * @param inc - the increment text field either rotate
     *            or translate
     * @param feedRate - same as above but for feed rate
     * @param message - the title border, either rotate
     *                or translate
     * @param incDefault - parameter for either rotate
     *                   or translate
     * @param feedDefault - same as above
     * @return - the newly created VBox
     */
    private VBox createBox(
            TextField inc, TextField feedRate, String message,
            double incDefault, double feedDefault)
    {
        VBox box = new VBox();
        box.setPadding(new Insets(0, SPACING, SPACING, SPACING));
        box.getStyleClass().add("bordered-titled-border");
        // Inside offsets, none for the top, and 5 for
        // the right, bottom and left
        setPadding(new Insets(0,5,5,5));
        Label lblMessage = new Label(message);
        lblMessage.getStyleClass().add("bordered-titled-title");

        Label lblInc = new Label("Increment");
        lblInc.setPrefWidth(TF_SIZE);

        inc.setFocusTraversable(false);
        inc.setPromptText(Double.toString(incDefault));
        inc.setPrefWidth(TF_SIZE);

        Label lblFeedRate = new Label("Feed rate");
        lblFeedRate.setPrefWidth(TF_SIZE);

        feedRate.setFocusTraversable(false);
        feedRate.setPrefWidth(TF_SIZE);
        feedRate.setPromptText(Double.toString(feedDefault));

        HBox r1 = new HBox(SPACING);
        r1.getChildren().addAll(lblInc, inc);

        HBox r2 = new HBox(SPACING);
        r2.getChildren().addAll(lblFeedRate, feedRate);

        box.getChildren().addAll(lblMessage, r1, r2);
        return box;
    }

    /**
     * Class for the button handler
     * for the default button
     */
    private class DefaultEventHandler
            implements EventHandler<ActionEvent>
    {
        /**
         * Clears all of the data
         * returning to the messages showing
         * default values.
         * @param event - unused
         */
        @Override
        public void handle(ActionEvent event)
        {
            tfIncRotate.clear();
            tfIncTranslate.clear();
            tfFeedRateRotate.clear();
            tfFeedRateTranslate.clear();
        }
    }

    /**
     * Class for the button handler
     * for the Apply button
     */
    private class ApplyEventHandler
            implements EventHandler<ActionEvent>
    {

        /**
         * Grabs all the values from
         * the text fields and then writes them
         * out to the settings file.
         * @param event - unused
         */
        @Override
        public void handle(ActionEvent event)
        {
            double[] parameters = new double[]{
                    Double.parseDouble(tfIncRotate.getText()),
                    Double.parseDouble(tfFeedRateRotate.getText()),
                    Double.parseDouble(tfIncTranslate.getText()),
                    Double.parseDouble(tfFeedRateTranslate.getText()),
                    120};

            ProgramFileWriter.writeParameterFile(parameters);
            // Close the window after you apply the new values
            parent.updateParameters(parameters);
            window.close();
        }
    }


}
