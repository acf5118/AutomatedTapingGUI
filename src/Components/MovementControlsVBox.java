package Components;

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

/**
 * Created by Adam Fowles on 1/7/2016.
 */
public class MovementControlsVBox
        extends VBox implements ComponentInterface
{
    private ArduinoSerial arduinoSerial;
    /**
     * Constructor
     */
    public MovementControlsVBox(ArduinoSerial as)
    {
        super();
        getStyleClass().add("bordered-titled-border");
        // Inside offsets, none for the top, and half spacing for
        // the right and left, full spacing on bottom
        setPadding(new Insets(0,SPACING/2,SPACING,SPACING/2));
        arduinoSerial = as;
        createComponents();
    }
    public void createComponents()
    {
        // Title on the border
        Label lblBorderTitle = new Label("Controls");
        lblBorderTitle.getStyleClass().add("bordered-titled-title");

        Button btnZero = new Button("Zero");
        btnZero.setTooltip(new Tooltip("Zero the Machine"));
        btnZero.setOnAction(new ZeroEventHandler());

        Button btnLeft = new Button();
        btnLeft.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/Resources/Left Arrow.png"))));
        Button btnRight = new Button();
        btnRight.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/Resources/Right Arrow.png"))));
        Button btnClockwise = new Button();
        btnClockwise.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/Resources/Clockwise.png"))));
        Button btnCounterClock = new Button();
        btnCounterClock.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/Resources/Counterclockwise.png"))));

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

    private class ZeroEventHandler
            implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event)
        {
            //arduinoSerial.writeOut("G28\n");
            arduinoSerial.writeOut("G20 G91 G0 x10\n");
            System.out.println("Hello");
        }
    }
}
