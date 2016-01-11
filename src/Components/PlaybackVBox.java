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
    // private fields
    private ArrayList<String> gCodeLines;
    private ArduinoSerial arduinoSerial;
    private Button btnStart, btnStop, btnPause;

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
        // Each G Code line is a string in this list
        gCodeLines = new ArrayList<>();
        // Serial Connection to the Arduino
        arduinoSerial = ar;
        // Create all the inner components
        createComponents();
    }

    /**
     * Creates the HBox container for
     * the buttons: play, stop, pause.
     * @return - the HBox object.
     */
    public void createComponents()
    {
        // Border label
        Label lblPlayback = new Label("Playback");
        lblPlayback.getStyleClass().add("bordered-titled-title");

        HBox row = new HBox(SPACING);

        // Give each button an Image from Resources folder
        btnStart = new Button();
        btnStart.setTooltip(new Tooltip("Start the Machine"));
        btnStart.setOnAction(new StartEventHandler());
        btnStart.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/Resources/Play Green Button.png"))));
        // Disable the buttons until a connection has been established
        btnStart.setDisable(true);
        btnStop = new Button();
        btnStop.setTooltip(new Tooltip("Stop the Machine"));
        btnStop.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/Resources/Stop Red Button.png"))));
        btnStop.setDisable(true);
        btnPause = new Button();
        btnPause.setTooltip(new Tooltip("Pause the Machine"));
        btnPause.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/Resources/Pause Blue Button.png"))));
        btnPause.setDisable(true);

        // Add each button the the horizontal box
        row.getChildren().addAll(btnStart,btnStop, btnPause);
        // Add the row and label to this VBox
        getChildren().addAll(lblPlayback, row);
    }

    /**
     * Set the lines of g-code to execute on play
     * @param lines
     */
    public void setGCodeLines(ArrayList<String> lines)
    {
        gCodeLines = lines;
    }

    /**
     * Enable the buttons once a connection has been established
     */
    public void enableButtons()
    {
        btnStart.setDisable(false);
        btnStop.setDisable(false);
        btnPause.setDisable(false);
    }

    /**
     * Event Handler for the Start button
     */
    private class StartEventHandler
            implements EventHandler<ActionEvent>
    {
        // Start will most likely be pressed
        // more than once, keep track of it.
        private int numTimes = 0;

        @Override
        public void handle(ActionEvent event)
        {
            if (numTimes == 1)
            {
                try {
                    arduinoSerial.writeOut("Y F100\n");
                } catch (SerialPortException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                // Execute each line of gCode
                for (String s : gCodeLines)
                {
                    try
                    {
                        arduinoSerial.writeOut(s);
                    }
                    catch (SerialPortException e)
                    {
                        e.printStackTrace();
                    }
                }
                System.out.println("Start");
                numTimes++;
            }
        }
    }


}
