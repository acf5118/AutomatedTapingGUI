package Components;

import Serial.ArduinoSerial;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import jssc.*;

/**
 * Created by Adam Fowles on 1/7/2016.
 */
public class ConnectionVBox
        extends VBox implements ComponentInterface
{

    public ArduinoSerial arduinoSerial;
    /**
     * Constructor
     */
    public ConnectionVBox(ArduinoSerial as)
    {
        super();
        // Inside offsets, none for the top, and half spacing for
        // the right, bottom and left
        setPadding(new Insets(0,SPACING/2,SPACING/2,SPACING/2));
        getStyleClass().add("bordered-titled-border");
        createComponents();
        arduinoSerial = as;
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
        btnConnect.setOnAction(new ConnectEventHandler());
        //btnConnect.getStyleClass().add("glass-grey");

        ImageView ivConnection = new ImageView(
                new Image(getClass().getResourceAsStream("/Resources/Not Connected.png")));

        HBox r1 = new HBox(SPACING);
        r1.getChildren().addAll(lblConnection, ivConnection);

        col.getChildren().addAll(btnConnect, r1, lblCurrFile);
        getChildren().addAll(lblStatus, col);

    }

    private class ConnectEventHandler
            implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            arduinoSerial.connectToArduino();
            System.out.println("Connected");
        }
    }


}

