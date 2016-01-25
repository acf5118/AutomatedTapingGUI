package Components;

import Main.LaMachinaGui;
import Serial.SerialCommunication;
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
public class MachineStatusVBox
        extends VBox implements ComponentInterface
{
    private LaMachinaGui parent;

    private SerialCommunication comm;
    private ImageView ivConnection;
    private Label lblConnection, lblCurrFile, lblMachineStatus;
    private Button btnConnect;

    /**
     * Constructor
     */
    public MachineStatusVBox(SerialCommunication as, LaMachinaGui parent)
    {
        super();
        this.parent = parent;
        // Inside offsets, none for the top, and half spacing for
        // the right, bottom and left
        setPadding(new Insets(0,SPACING/2,SPACING/2,SPACING/2));
        getStyleClass().add("bordered-titled-border");
        createComponents();
        comm = as;
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

        btnConnect = new Button("Connect to Machine");
        btnConnect.setOnAction(new ConnectEventHandler());
        //btnConnect.getStyleClass().add("glass-grey");

        ivConnection = new ImageView(
                new Image(getClass().getResourceAsStream("/Resources/Not Connected.png")));

        lblMachineStatus = new Label("Status: Good");
        HBox r1 = new HBox(SPACING);
        r1.getChildren().addAll(lblConnection, ivConnection);

        col.getChildren().addAll(btnConnect, r1, lblCurrFile, lblMachineStatus);
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
                comm.connect();
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {

            }

        }
    }

    public void updateStatus(String[] status)
    {
        if (status[0].equals("true"))
        {
            ivConnection.setImage(new Image(
                    getClass().getResourceAsStream("/Resources/Warning.png")));
        }
        else
        {
            ivConnection.setImage(new Image(
                    getClass().getResourceAsStream("/Resources/Connected.png")));
            parent.getControlButtons().get(0).setDisable(false);
            btnConnect.setDisable(true);
        }
        lblConnection.setText(status[1]);
    }

    public void setAlarm(){lblMachineStatus.setText("Status: ALARM");}
    public void setFilename(String filename)
    {
        lblCurrFile.setText("Loaded: " + filename);
    }
}

