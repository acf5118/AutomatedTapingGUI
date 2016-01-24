package Components;

import GCodeUtil.GCodeGenerator;
import Serial.SerialCommunication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
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
    private TextField tfEnterCmds;

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
        HBox r = new HBox(SPACING);
        r.setPadding(new Insets(5,5,5,5));
        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setFocusTraversable(false);
        textArea.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tfEnterCmds.requestFocus();
            }
        });
        tfEnterCmds = new TextField();
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
        Button btnClear = new Button("Clear");
        btnClear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textArea.clear();
            }
        });

        r.getChildren().addAll(tfEnterCmds, btnClear);
        HBox.setHgrow(textArea, Priority.ALWAYS);
        VBox.setVgrow(textArea, Priority.ALWAYS);
        HBox.setHgrow(tfEnterCmds, Priority.ALWAYS);
        getChildren().addAll(textArea, r);
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
