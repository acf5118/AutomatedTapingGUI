package Components;

import GCodeUtil.GCodeGenerator;
import Main.LaMachinaGui;
import Serial.ArduinoSerialConnection;
import Serial.SerialCommunication;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Adam Fowles on 1/18/2016.
 */
public class SerialMonitorVBox
        extends VBox implements ComponentInterface, Observer
{

    private SerialCommunication comm;
    private StringBuilder inputBuffer;
    private TextArea textArea;

    SerialMonitorVBox(double[] params,
                      Stage window,
                      SerialCommunication comm)
    {
        super();

        this.comm = comm;
        this.comm.addSerialMonitorObserver(this);

        createComponents();
    }

    @Override
    public void createComponents()
    {
        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setFocusTraversable(false);
        TextField tfEnterCmds = new TextField();
        tfEnterCmds.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER))
                {
                    comm.sendMessage(tfEnterCmds.getText());
                    textArea.appendText(">>> " + tfEnterCmds.getText() + "\n");
                    tfEnterCmds.clear();
                }
                if (event.isControlDown() && event.getCode() == KeyCode.X)
                {
                    comm.sendByte(GCodeGenerator.RESET);
                    textArea.appendText(">>> CTRL - X");
                    tfEnterCmds.clear();
                }
            }
        });
        HBox.setHgrow(textArea, Priority.ALWAYS);
        VBox.setVgrow(textArea, Priority.ALWAYS);
        getChildren().addAll(textArea, tfEnterCmds);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        String[] c = (String[])arg;
        if (c == null)
        {
            return;
        }
        Platform.runLater(() -> {
            for (String s: c)
            {
                textArea.appendText(s + "\n");
            }
        });
    }

}
