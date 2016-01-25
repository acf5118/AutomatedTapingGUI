package Components;

import GCodeUtil.GCodeGenerator;
import Main.LaMachinaGui;
import Serial.SerialCommunication;
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

import java.util.ArrayList;

/**
 * Created by Adam Fowles on 1/7/2016.
 */
public class MovementControlsVBox
        extends VBox implements ComponentInterface
{
    private SerialCommunication comm;
    private ArrayList<Button> buttons;
    private Button btnGoToZero,btnSetZero, btnLeft,
            btnRight, btnClockwise, btnCounterClock;
    private double[] params;
    private boolean zeroBefore;
    private LaMachinaGui parent;
    private int countRotations;

    /**
     * Constructor
     */
    public MovementControlsVBox(SerialCommunication as,
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
        comm = as;
        zeroBefore = false;
        buttons = new ArrayList<>();
        countRotations = 0;
        createComponents();
    }
    public void createComponents()
    {
        // Title on the border
        Label lblBorderTitle = new Label("Controls");
        lblBorderTitle.getStyleClass().add("bordered-titled-title");

        btnGoToZero = new Button("Home");
        btnGoToZero.setTooltip(new Tooltip("Zero the Machine"));
        btnGoToZero.setOnAction(new EventHandler<ActionEvent>() {
            private int count = 0;
            @Override
            public void handle(ActionEvent event)
            {
                if (count == 0)
                {
                    comm.sendMessage("$H");
                    count++;
                }
                else
                {
                    comm.sendMessage(GCodeGenerator.getGCodeGoToZeroMessage());
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
        });
        buttons.add(btnGoToZero);

        btnLeft = new Button();
        btnLeft.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/Resources/Left Arrow.png"))));
        btnLeft.setOnAction(new GCodeEventHandler(
                GCodeGenerator.getGCodeTranslateMessage(params[2], params[3], -1), 0));
        buttons.add(btnLeft);
        btnRight = new Button();
        btnRight.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/Resources/Right Arrow.png"))));
        btnRight.setOnAction(new GCodeEventHandler(
                GCodeGenerator.getGCodeTranslateMessage(params[2], params[3], 1), 1));
        buttons.add(btnRight);
        btnClockwise = new Button();
        btnClockwise.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/Resources/Clockwise.png"))));
        btnClockwise.setOnAction(new GCodeEventHandler(
                GCodeGenerator.getGCodeRevolveMessage(params[0], params[1], -1), 2));
        buttons.add(btnClockwise);
        btnCounterClock = new Button();
        btnCounterClock.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/Resources/Counterclockwise.png"))));
        btnCounterClock.setOnAction(new GCodeEventHandler(
                GCodeGenerator.getGCodeRevolveMessage(params[0], params[1], 1), 3));
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
        r0.getChildren().addAll(btnGoToZero);//, btnSetZero);

        col.getChildren().addAll(r0, gpMovement);
        getChildren().addAll(lblBorderTitle, col);
    }

    private class GCodeEventHandler
            implements EventHandler<ActionEvent>
    {
        private String code;
        private int type;
        public GCodeEventHandler(String gCode, int t)
        {
            code = gCode;
            type = t;
        }

        @Override
        public void handle(ActionEvent event)
        {
            //TODO create enum
            if (type == 2)
            {
                countRotations++;
            }
            if (type == 3)
            {
                if (countRotations > 0)
                {
                    countRotations--;
                }
                else
                {
                    return;
                }
            }

            comm.sendMessage(code);
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
        /*
        btnGoToZero.setOnAction(new GCodeEventHandler(
                GCodeGenerator.getGCodeGoToZeroMessage()));
        btnSetZero.setOnAction(new GCodeEventHandler(
                GCodeGenerator.getGCodeSetZeroMessage(params[3])));
                */
        btnLeft.setOnAction(new GCodeEventHandler(
                GCodeGenerator.getGCodeTranslateMessage(params[2], params[3], -1), 0));
        btnRight.setOnAction(new GCodeEventHandler(
                GCodeGenerator.getGCodeTranslateMessage(params[2], params[3], 1), 1));
        btnClockwise.setOnAction(new GCodeEventHandler(
                GCodeGenerator.getGCodeRevolveMessage(params[0], params[1], -1), 2));
        btnCounterClock.setOnAction(new GCodeEventHandler(
                GCodeGenerator.getGCodeRevolveMessage(params[0], params[1], 1), 3));
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
        btnGoToZero.setDisable(false);
        btnLeft.setDisable(true);
        btnRight.setDisable(true);
        btnClockwise.setDisable(true);
        btnCounterClock.setDisable(true);
    }
}
