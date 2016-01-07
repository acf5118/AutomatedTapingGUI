package Components;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


/**
 * Created by Adam Fowles on 1/7/2016.
 */
public class FileControlHBox extends HBox
{
    // Constants
    private final int SPACING = 5;

    /**
     * Constructor
     */
    public FileControlHBox()
    {
        super();
        setSpacing(SPACING);
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
        Button btnErase = new Button();
        btnErase.setTooltip(new Tooltip("Clear Contents"));
        btnErase.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("../Eraser.png"))));

        //Region spacer = new Region();
        //HBox.setHgrow(spacer, Priority.ALWAYS);

        getChildren().addAll(btnSave,btnErase);//,spacer,lblUnits,comboUnits);

    }
}
