package Components;

import GCodeUtil.GCodeGenerator;
import Main.LaMachinaGui;
import Serial.ArduinoSerial;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import jssc.SerialPortException;

import java.util.ArrayList;

/**
 * Created by Adam Fowles on 1/7/2016.
 */
public class MovementControlsVBox
        extends VBox implements ComponentInterface
{
    private ArduinoSerial arduinoSerial;
    private ArrayList<Button> buttons;
    private Button btnZero, btnLeft, btnRight, btnClockwise, btnCounterClock;
    private double[] params;
    private boolean zeroBefore;
    private LaMachinaGui parent;

    /**
     * Constructor
     */
    public MovementControlsVBox(ArduinoSerial as,
                                double[] params,
                                LaMachinaGui parent)
    {
        super();
        this.params = params;
        this.parent = parent;
        getStyleClass().add("bordered-titled-border");
        // Inside offsets, none for the top, and half spacing for
        // the right and left, full spacing on bottom
        setPadding(new Insets(0,SPACING/2,SPACING,SPACING/2));
        arduinoSerial = as;
        zeroBefore = false;
        buttons = new ArrayList<>();
        createComponents();
    }
    public void createComponents()
    {
        // Title on the border
        Label lblBorderTitle = new Label("Controls");
        lblBorderTitle.getStyleClass().add("bordered-titled-title");

        btnZero = new Button("Zero");
        btnZero.setTooltip(new Tooltip("Zero the Machine"));
        btnZero.setOnAction(new GCodeEventHandler(GCodeGenerator.getGCodeZeroMessage(params[3])));
        buttons.add(btnZero);
        btnLeft = new Button();
        btnLeft.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/Resources/Left Arrow.png"))));
        btnLeft.setOnAction(new GCodeEventHandler(
                GCodeGenerator.getGCodeTranslateMessage(params[2], params[3], -1)));
        buttons.add(btnLeft);
        btnRight = new Button();
        btnRight.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/Resources/Right Arrow.png"))));
        btnRight.setOnAction(new GCodeEventHandler(
                GCodeGenerator.getGCodeTranslateMessage(params[2], params[3], 1)));
        buttons.add(btnRight);
        btnClockwise = new Button();
        btnClockwise.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/Resources/Clockwise.png"))));
        btnClockwise.setOnAction(new GCodeEventHandler(
                GCodeGenerator.getGCodeRevolveMessage(params[0], params[1], -1)));
        buttons.add(btnClockwise);
        btnCounterClock = new Button();
        btnCounterClock.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/Resources/Counterclockwise.png"))));
        btnCounterClock.setOnAction(new GCodeEventHandler(GCodeGenerator.getGCodeRevolveMessage(params[0], params[1], 1)));
        buttons.add(btnCounterClock);

        toggleAllButtons(true);

        VBox col = new VBox(SPACING);
        GridPane gpMovement = new GridPane();
        gpMovement.add(btnLeft,0,0);
        gpMovement.add(btnRight,1,0);
        gpMovement.add(btnClockwise,0,1);
        gpMovement.add(btnCounterClock,1,1);
        gpMovement.setHgap(SPACING);
        gpMovement.setVgap(SPACING);

        gpMovement.setAlignment(Pos.CENTER);
        HBox r0 = new HBox(SPACING);
        r0.setAlignment(Pos.CENTER);
        r0.getChildren().add(btnZero);

        col.getChildren().addAll(r0, gpMovement);
        getChildren().addAll(lblBorderTitle, col);
    }

    private class GCodeEventHandler
            implements EventHandler<ActionEvent>
    {
        private String code;
        public GCodeEventHandler(String gCode)
        {
            code = gCode;
        }

        @Override
        public void handle(ActionEvent event)
        {
            try
            {
                arduinoSerial.writeOut(code+"\n");
            } catch (SerialPortException e)
            {
                System.out.println("Couldnt write code");
            }
            if (!zeroBefore)
            {
                zeroBefore = true;
                for (Button b: buttons)
                {
                    b.setDisable(false);
                }
                parent.enablePlayback();

            }
        }
    }

    public ArrayList<Button> getButtons(){return buttons;}

    public void updateParameters(double[] params)
    {
        this.params = params;
        updateButtonGCode();
    }

    public void updateButtonGCode()
    {
        btnZero.setOnAction(new GCodeEventHandler(
                GCodeGenerator.getGCodeZeroMessage(params[3])));
        btnLeft.setOnAction(new GCodeEventHandler(
                GCodeGenerator.getGCodeTranslateMessage(params[2], params[3], -1)));
        btnRight.setOnAction(new GCodeEventHandler(
                GCodeGenerator.getGCodeTranslateMessage(params[2], params[3], 1)));
        btnClockwise.setOnAction(new GCodeEventHandler(
                GCodeGenerator.getGCodeRevolveMessage(params[0], params[1], -1)));
        btnCounterClock.setOnAction(new GCodeEventHandler(
                GCodeGenerator.getGCodeRevolveMessage(params[0], params[1], 1)));
    }

    public void toggleAllButtons(boolean onOff)
    {
        for (Button b: buttons)
        {
            b.setDisable(onOff);
        }
    }

    public void reset()
    {
        zeroBefore = false;
        btnZero.setDisable(false);
        btnLeft.setDisable(true);
        btnRight.setDisable(true);
        btnClockwise.setDisable(true);
        btnCounterClock.setDisable(true);
    }
}
