package Components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Created by Adam Fowles on 1/10/2016.
 */
public class QuickViewVBox
        extends VBox implements ComponentInterface
{
    private Button btnFullView;
    private double[] params;
    private Stage parentStage;

    public QuickViewVBox(Stage parentStage)
    {
        super();
        setPadding(new Insets(0,SPACING/2,SPACING/2,SPACING/2));
        this.parentStage = parentStage;
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
        btnFullView = new Button();
        btnFullView.setGraphic(new ImageView(
                new javafx.scene.image.Image(getClass().getResourceAsStream("/Resources/Info.png"))));
        btnFullView.setAlignment(Pos.CENTER);
        btnFullView.setTooltip(new Tooltip("View All File Info"));
        btnFullView.setDisable(true);
        btnFullView.setOnAction(new FullViewEventHandler());
        c2.getChildren().add(btnFullView);

        container.getChildren().addAll(c1, c2);

        getChildren().addAll(lblStatus, container);
    }

    private class FullViewEventHandler
            implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event)
        {
            Stage stage = new Stage();
            Scene scene = new Scene(new FullViewVBox(params)); stage.show();
            stage.setTitle("Full View");
            scene.getStylesheets().addAll("Style.css");
            stage.setX(parentStage.getX() + 250);
            stage.setY(parentStage.getY() + 100);

            stage.setScene(scene);
            stage.show();

        }
    }

    public void enableFullView(){btnFullView.setDisable(false);}
    public void setParams(double[] p){params = p;}
}
