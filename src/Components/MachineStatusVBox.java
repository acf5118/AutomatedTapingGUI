package Components;

import Main.LaMachinaGui;
import Serial.ArduinoSerial;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
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
public class MachineStatusVBox
        extends VBox implements ComponentInterface
{
    private LaMachinaGui parent;

    private ArduinoSerial arduinoSerial;
    private ImageView ivConnection;
    private Label lblConnection, lblCurrFile;
    /**
     * Constructor
     */
    public MachineStatusVBox(ArduinoSerial as, LaMachinaGui parent)
    {
        super();
        this.parent = parent;
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

        lblCurrFile = new Label("No File Currently Loaded");
        lblConnection = new Label("Not Connected");

        Button btnConnect = new Button("Connect to Machine");
        btnConnect.setOnAction(new ConnectEventHandler());
        //btnConnect.getStyleClass().add("glass-grey");

        ivConnection = new ImageView(
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
            try
            {
                arduinoSerial.connectToArduino();
            }
            catch (SerialPortException e)
            {
                ivConnection.setImage(new Image(
                        getClass().getResourceAsStream("/Resources/Warning.png")));
                lblConnection.setText("Could not connect");
                return;
            }
            ivConnection.setImage(new Image(
                    getClass().getResourceAsStream("/Resources/Connected.png")));
            lblConnection.setText("Connection Established");
            parent.getControlButtons().get(0).setDisable(false);
            try
            {
                arduinoSerial.writeOut("G20\n");
            }
            catch (SerialPortException e)
            {
                System.out.println("Error writing G20");
            }
        }
    }

    public void setFilename(String filename)
    {
        lblCurrFile.setText("Loaded: " + filename);
    }
}

