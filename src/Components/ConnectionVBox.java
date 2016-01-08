package Components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * Created by Adam Fowles on 1/7/2016.
 */
public class ConnectionVBox
        extends VBox implements ComponentInterface
{



    /**
     * Constructor
     */
    public ConnectionVBox()
    {
        super();
        // Inside offsets, none for the top, and half spacing for
        // the right, bottom and left
        setPadding(new Insets(0,SPACING/2,SPACING/2,SPACING/2));
        getStyleClass().add("bordered-titled-border");
        createComponents();
    }

    /**
     * Creates the inner components and
     * adds them to this VBox.
     */
    public void createComponents()
    {
        VBox col = new VBox(SPACING);

        Label lblStatus = new Label("Machine Status");
        lblStatus.getStyleClass().add("bordered-titled-title");

        Label lblCurrFile = new Label("No File Currently Loaded");
        Label lblConnection = new Label("Not Connected");

        Button btnConnect = new Button("Connect to Machine");
        //btnConnect.getStyleClass().add("glass-grey");

        ImageView ivConnection = new ImageView(
                new Image(getClass().getResourceAsStream("../Not Connected.png")));

        HBox r1 = new HBox(SPACING);
        r1.getChildren().addAll(lblConnection, ivConnection);

        col.getChildren().addAll(btnConnect, r1, lblCurrFile);
        getChildren().addAll(lblStatus, col);

    }
}

