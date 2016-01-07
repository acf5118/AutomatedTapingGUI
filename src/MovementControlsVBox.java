import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Created by Adam Fowles on 1/7/2016.
 */
public class MovementControlsVBox extends VBox
{
    private final int SPACING = 10;

    public MovementControlsVBox()
    {
        super();
        getStyleClass().add("bordered-titled-border");
        // Inside offsets, none for the top, and 5 for
        // the right, bottom and left
        setPadding(new Insets(0,20,0,20));
        VBox.setVgrow(this, Priority.ALWAYS);
        //HBox.setHgrow(this, Priority.ALWAYS);
        createComponents();
    }
    public void createComponents()
    {
        // Title on the border
        Label lblBorderTitle = new Label("Controls");
        lblBorderTitle.getStyleClass().add("bordered-titled-title");

        Button btnLeft = new Button();
        btnLeft.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("Left Arrow.png"))));
        Button btnRight = new Button();
        btnRight.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("Right Arrow.png"))));
        Button btnClockwise = new Button();
        btnClockwise.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("Clockwise.png"))));
        Button btnCounterClock = new Button();
        btnCounterClock.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("Counterclockwise.png"))));

        Region space = new Region();
        HBox.setHgrow(space, Priority.ALWAYS);
        Region space2 = new Region();
        HBox.setHgrow(space2, Priority.ALWAYS);

        HBox r1 = new HBox(SPACING);
        r1.setAlignment(Pos.CENTER);
        r1.getChildren().addAll(btnLeft,space, btnRight);

        HBox r2 = new HBox(SPACING);
        r2.setAlignment(Pos.CENTER);
        r2.getChildren().addAll(btnClockwise,space2, btnCounterClock);

        VBox.setVgrow(r1, Priority.ALWAYS);
        VBox.setVgrow(r2, Priority.ALWAYS);

        getChildren().addAll(lblBorderTitle, r1,r2);
    }
}
