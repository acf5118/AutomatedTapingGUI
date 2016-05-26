package Components;

import GCodeUtil.Strings;
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

import java.util.HashMap;


/**
 * Created by Adam Fowles on 1/10/2016.
 */
public class QuickViewVBox
        extends VBox implements ComponentInterface
{
    private Button btnFullView;
    private double[]  mod;
    private Stage parentStage;
    private Label dop, wop;
    private Stage fullViewStage;

    public QuickViewVBox(Stage parentStage)
    {
        super();
        setPadding(new Insets(0, SPACING / 2, SPACING / 2, SPACING / 2));
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
        dop = new Label("Diameter of Part: __");
        wop = new Label("Tape Width: __");
        c1.getChildren().addAll(dop, wop);

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
            if (fullViewStage == null)
            {
                fullViewStage = new Stage();
                Scene scene = new Scene(new FullViewVBox(mod));
                fullViewStage.setTitle("Full View");
                scene.getStylesheets().addAll("Style.css");
                fullViewStage.setX(parentStage.getX() + 250);
                fullViewStage.setY(parentStage.getY() + 100);

                fullViewStage.setScene(scene);
            }
            fullViewStage.show();
            fullViewStage.toFront();

        }
    }

    public void enableFullView(){btnFullView.setDisable(false);}
    public void setParams(HashMap<String, Double> p, double[] m)
    {
        //params = p;
        mod = m;
        dop.setText("Diameter of Part: " + p.get(Strings.DIAMETER));
        wop.setText("Tape Width: " + p.get(Strings.TAPE_WIDTH));

    }

    public void close()
    {
        if (fullViewStage != null)
        {
            fullViewStage.close();
        }
    }
}
