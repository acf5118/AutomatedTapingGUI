package Components;

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
    /**
     *
     */
    public MovementControlsVBox()
    {
        super();
        getStyleClass().add("bordered-titled-border");
        // Inside offsets, none for the top, and 5 for
        // the right, bottom and left
        setPadding(new Insets(0,20,10,20));
        createComponents();
    }
    public void createComponents()
    {
        // Title on the border
        Label lblBorderTitle = new Label("Controls");
        lblBorderTitle.getStyleClass().add("bordered-titled-title");

        Button btnZero = new Button("Zero");
        btnZero.setTooltip(new Tooltip("Zero the Machine"));

        Button btnLeft = new Button();
        btnLeft.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("../Left Arrow.png"))));
        Button btnRight = new Button();
        btnRight.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("../Right Arrow.png"))));
        Button btnClockwise = new Button();
        btnClockwise.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("../Clockwise.png"))));
        Button btnCounterClock = new Button();
        btnCounterClock.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("../Counterclockwise.png"))));

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
}
