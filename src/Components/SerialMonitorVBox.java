package Components;

import Main.LaMachinaGui;
import Serial.SerialCommunication;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * Created by Adam Fowles on 1/18/2016.
 */
public class SerialMonitorVBox
        extends VBox implements ComponentInterface
{
    private LaMachinaGui parentGui;
    private SerialCommunication comm;
    private StringBuilder inputBuffer;
    private TextFlow textFlow;

    SerialMonitorVBox(double[] params,
                      Stage window,
                      LaMachinaGui parent)
    {
        super();
        this.parentGui = parent;
        //comm = parentGui.getArduinoSerial();
        createComponents();
    }

    @Override
    public void createComponents()
    {
        TextField tfEnterCmds = new TextField();
        tfEnterCmds.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER))
                {
                    comm.sendMessage(tfEnterCmds.getText());
                }
            }
        });
        textFlow = new TextFlow(tfEnterCmds);
        getChildren().addAll(tfEnterCmds, textFlow);
    }
}
