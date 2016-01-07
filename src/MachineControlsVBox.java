import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by Adam Fowles on 1/6/2016.
 */
public class MachineControlsVBox extends VBox
{
    public MachineControlsVBox()
    {
        super();
        setPadding(new Insets(0,5,5,5));
        getStyleClass().add("bordered-titled-border");
        Label lblPlayback = new Label("Machine Controls");
        lblPlayback.getStyleClass().add("bordered-titled-title");
        getChildren().addAll(lblPlayback, createButtons());
    }

    public HBox createButtons()
    {
        HBox row = new HBox(10);
        //row.setPadding(new Insets(5,5,5,5));

        Button btnStart = new Button();
        btnStart.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("Play Green Button.png"))));
        Button btnStop = new Button();
        btnStop.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("Stop Red Button.png"))));

        Button btnPause = new Button();
        btnPause.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("Pause Blue Button.png"))));

        // Can change the grey background if desired
        //btnStop.setStyle("-fx-base: #ef1515;");
        btnStart.setMaxWidth(Double.MAX_VALUE);
        btnStop.setMaxWidth(Double.MAX_VALUE);

        row.getChildren().addAll(btnStart,btnStop, btnPause);
        return row;
    }
}
