package Components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


/**
 * Created by Adam Fowles on 1/7/2016.
 */
public class FileControlHBox
        extends HBox implements ComponentInterface
{
    private PartCreationVBox parent;
    /**
     * Constructor
     * @param parent - takes in the parent so it
     *               can save and erase data
     */
    public FileControlHBox(PartCreationVBox parent)
    {
        super();
        this.parent = parent;
        setSpacing(SPACING / 2);
        createComponents();
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
                new Image(getClass().getResourceAsStream("../Save.png"))));
        btnSave.setOnAction(new SaveEventHandler());
        Button btnErase = new Button();
        btnErase.setOnAction(new EraseEventHandler());
        btnErase.setTooltip(new Tooltip("Clear Contents"));
        btnErase.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("../Eraser.png"))));

        //Region spacer = new Region();
        //HBox.setHgrow(spacer, Priority.ALWAYS);

        getChildren().addAll(btnSave,btnErase);//,spacer,lblUnits,comboUnits);

    }

    private class SaveEventHandler
            implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            System.out.println("Saved");
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
            for (TextField tf: parent.getFields())
            {
                tf.clear();
            }
            System.out.println("Cleared");
        }
    }
}
