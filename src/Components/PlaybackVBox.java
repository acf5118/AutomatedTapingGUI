package Components;

import Serial.ArduinoSerial;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jssc.SerialPortException;

import java.util.ArrayList;

/**
 * Created by Adam Fowles on 1/6/2016.
 */
public class PlaybackVBox
        extends VBox implements ComponentInterface
{
    private ArrayList<String> gCodeLines;
    private ArduinoSerial arduinoSerial;
    /**
     * Constructor for the Machine Control
     * VBox.
     */
    public PlaybackVBox(ArduinoSerial ar)
    {
        // Call to VBox constructor
        super();
        // Inside offsets, none for the top, and half spacing for
        // the right, bottom and left
        setPadding(new Insets(0,SPACING/2,SPACING/2,SPACING/2));
        // See Style.css
        getStyleClass().add("bordered-titled-border");
        gCodeLines = new ArrayList<>();
        arduinoSerial = ar;
        createComponents();
    }

    /**
     * Creates the HBox container for
     * the buttons: play, stop pause.
     * @return - the HBox object.
     */
    public void createComponents()
    {
        // Border label
        Label lblPlayback = new Label("Playback");
        lblPlayback.getStyleClass().add("bordered-titled-title");

        HBox row = new HBox(SPACING);

        // Give each button an Image from Resources folder
        Button btnStart = new Button();
        btnStart.setTooltip(new Tooltip("Start the Machine"));
        btnStart.setOnAction(new StartEventHandler());
        btnStart.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/Resources/Play Green Button.png"))));
        Button btnStop = new Button();
        btnStop.setTooltip(new Tooltip("Stop the Machine"));
        btnStop.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/Resources/Stop Red Button.png"))));
        Button btnPause = new Button();
        btnPause.setTooltip(new Tooltip("Pause the Machine"));
        btnPause.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/Resources/Pause Blue Button.png"))));

        // Can change the grey background if desired (as hex value)
        //btnStop.setStyle("-fx-base: #ef1515;");

        // Add each button the the horizontal box
        row.getChildren().addAll(btnStart,btnStop, btnPause);
        getChildren().addAll(lblPlayback, row);
    }

    public void setGCodeLines(ArrayList<String> lines)
    {
        gCodeLines = lines;
    }

    private class StartEventHandler
            implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event)
        {
            for(String s: gCodeLines)
            {
                try {
                    arduinoSerial.writeOut(s);
                } catch (SerialPortException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Start");
        }
    }
}
