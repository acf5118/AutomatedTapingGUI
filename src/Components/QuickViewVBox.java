package Components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


/**
 * Created by Adam Fowles on 1/10/2016.
 */
public class QuickViewVBox extends VBox implements ComponentInterface
{
    public QuickViewVBox()
    {
        super();
        setPadding(new Insets(0,SPACING/2,SPACING/2,SPACING/2));
        getStyleClass().add("bordered-titled-border");
        createComponents();
    }

    @Override
    public void createComponents()
    {
        HBox container = new HBox(SPACING*2);
        Label lblStatus = new Label("Quick View");
        lblStatus.getStyleClass().add("bordered-titled-title");

        VBox c1 = new VBox(SPACING);
        Text lop = new Text("Length of Part: __");
        Text dop = new Text("Diameter of Part: __");
        Text wop = new Text("Tape Width: __");
        c1.getChildren().addAll(lop, dop, wop);

        VBox c2 = new VBox();
        Button btnFullView = new Button();
        btnFullView.setGraphic(new ImageView(
                new javafx.scene.image.Image(getClass().getResourceAsStream("/Resources/Info.png"))));
        btnFullView.setAlignment(Pos.CENTER);
        btnFullView.setTooltip(new Tooltip("View All File Info"));
        btnFullView.setDisable(true);
        c2.getChildren().add(btnFullView);

        container.getChildren().addAll(c1, c2);

        getChildren().addAll(lblStatus, container);
    }
}
