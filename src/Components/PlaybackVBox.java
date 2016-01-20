package Components;

import GCodeUtil.GCodeGenerator;
import Main.LaMachinaGui;
import Serial.SerialCommunication;
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

import java.util.ArrayList;

/**
 * Created by Adam Fowles on 1/6/2016.
 */
public class PlaybackVBox
        extends VBox implements ComponentInterface
{
    // private fields
    private ArrayList<String> gCodeLines;
    private SerialCommunication comm;
    private Button btnStart, btnStop, btnPause, btnCheck;
    private double[] params;
    private boolean pausePressed, stopPressed;
    private LaMachinaGui parentGui;

    /**
     * Constructor for the Machine Control
     * VBox.
     */
    public PlaybackVBox(SerialCommunication c, LaMachinaGui parent)
    {
        // Call to VBox constructor
        super();
        // Inside offsets, none for the top, and half spacing for
        // the right, bottom and left
        setPadding(new Insets(0, SPACING / 2, SPACING / 2, SPACING / 2));
        // See Style.css
        getStyleClass().add("bordered-titled-border");
        // Each G Code line is a string in this list
        gCodeLines = new ArrayList<>();
        // Serial Connection to the Arduino
        comm = c;
        // Create all the inner components
        pausePressed = false; stopPressed = false;
        parentGui = parent;
        createComponents();
    }

    /**
     * Creates the HBox container for
     * the buttons: play, stop, pause.
     *
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
        btnStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String[] s = GCodeGenerator.getGCodeStopMessage();

                comm.reconnect();
                stopPressed = true;
                btnStart.setDisable(true);
                btnStop.setDisable(true);
                btnPause.setDisable(true);
                btnCheck.setDisable(true);
                btnStart.setOnAction(new StartEventHandler());
                pausePressed = false;
                parentGui.reset();
            }
        });
        btnPause = new Button();
        btnPause.setTooltip(new Tooltip("Pause the Machine"));
        btnPause.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/Resources/Pause Blue Button.png"))));
        btnPause.setDisable(true);
        btnPause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    comm.sendMessage("!");
                    pausePressed = true;
                    btnStart.setDisable(false);
            }
        });

        btnCheck = new Button();
        btnCheck.setTooltip(new Tooltip("Press when Tape has been applied or cut"));
        btnCheck.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/Resources/Check.png"))));
        btnCheck.setDisable(true);
        btnCheck.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btnStart.setDisable(false);

            }
        });

        // Add each button the the horizontal box
        row.getChildren().addAll(btnStart, btnStop, btnPause, btnCheck);
        // Add the row and label to this VBox
        getChildren().addAll(lblPlayback, row);
    }

    /**
     * Set the lines of g-code to execute on play
     *
     * @param lines
     */
    public void setGCodeLines(ArrayList<String> lines) {
        gCodeLines = lines;
    }

    public void setParams(double[] params) {
        this.params = params;
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
        private int restart = 0;

        @Override
        public void handle(ActionEvent event)
        {

            if (stopPressed)
            {
                stopPressed = false;
                comm.sendMessage("~");

            }
            if (pausePressed)
            {
                pausePressed = false;
                comm.sendMessage("~");

                btnStart.setDisable(true);
                return;
            }
            if (numTimes == 1)
            {

                for (int i = 4; i < gCodeLines.size(); i++)
                {
                    comm.sendMessage(gCodeLines.get(i));
                    System.out.println(gCodeLines.get(i));
                }
                numTimes = 0;
            }
            else
            {
                parentGui.toggleControls(true);
                btnStart.setDisable(true);
                btnCheck.setDisable(false);
                for (String s : gCodeLines)
                {

                    if (s.equals("!"))
                    {
                        break;
                    }
                    comm.sendMessage(s);
                }
                numTimes++;

            }

        }
    }
}
