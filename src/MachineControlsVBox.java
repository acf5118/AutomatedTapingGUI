import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by Adam Fowles on 1/6/2016.
 */
public class MachineControlsVBox extends VBox
{
    // Constants
    private final int SPACING = 10;

    /**
     * Constructor for the Machine Control
     * VBox.
     */
    public MachineControlsVBox()
    {
        // Call to VBox constructor
        super();
        // Inside offsets, none for the top, and 5 for
        // the right, bottom and left
        setPadding(new Insets(0,SPACING/2,SPACING/2,SPACING/2));
        // See Style.css
        getStyleClass().add("bordered-titled-border");

        // Border label
        Label lblPlayback = new Label("Playback");
        lblPlayback.getStyleClass().add("bordered-titled-title");

        getChildren().addAll(lblPlayback, createButtons());
    }

    /**
     * Creates the HBox container for
     * the buttons: play, stop pause.
     * @return - the HBox object.
     */
    public HBox createButtons()
    {
        HBox row = new HBox(SPACING);

        // Give each button an Image from Resources folder
        Button btnStart = new Button();
        btnStart.setTooltip(new Tooltip("Start the Machine"));
        btnStart.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("Play Green Button.png"))));
        Button btnStop = new Button();
        btnStop.setTooltip(new Tooltip("Stop the Machine"));
        btnStop.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("Stop Red Button.png"))));
        Button btnPause = new Button();
        btnPause.setTooltip(new Tooltip("Pause the Machine"));
        btnPause.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("Pause Blue Button.png"))));

        // Can change the grey background if desired (as hex value)
        //btnStop.setStyle("-fx-base: #ef1515;");

        // Add each button the the horizontal box
        row.getChildren().addAll(btnStart,btnStop, btnPause);
        return row;
    }
}
