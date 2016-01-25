package Components.Menu;

import Components.ParameterWindowHBox;
import Components.SerialMonitorVBox;
import FileIO.ProgramFileReader;
import Main.LaMachinaGui;
import Serial.SerialCommunication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Adam Fowles on 1/9/2016.
 */
public class LaMachinaMenuBar extends MenuBar
{
    private LaMachinaGui parentGui;
    private Stage parentStage;
    private double[] params;
    private SerialCommunication comm;
    private MenuItem miSerialMonitor;
    private Stage parameterWindowStage, serialMonitorStage;

    public LaMachinaMenuBar(Stage parent,
                            double[] params,
                            LaMachinaGui gui,
                            SerialCommunication comm)
    {
        super();
        parentStage = parent;
        this.params = params;
        this.parentGui = gui;
        this.comm = comm;
        createMenu();
    }

    public void createMenu()
    {
        // List of menus
        Menu menuFile = new FileMenu(parentGui, comm);
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");
        Menu menuSetting = new Menu("Settings");

        MenuItem miParameters = new MenuItem("Parameters");
        miParameters.setOnAction(new ParametersEventHandler());


        menuSetting.getItems().add(miParameters);

        miSerialMonitor = new MenuItem("Serial Monitor");
        miSerialMonitor.setOnAction(new SerialMonitorEventHandler());
        miSerialMonitor.setDisable(true);

        menuView.getItems().add(miSerialMonitor);
        // Add all of the menus to the menubar
        getMenus().addAll(menuFile, menuEdit, menuView, menuSetting);
    }

    private class ParametersEventHandler
            implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            if (parameterWindowStage == null)
            {
                parameterWindowStage = new Stage();
                Scene scene = new Scene(new ParameterWindowHBox(params,
                        parameterWindowStage, parentGui));
                parameterWindowStage.setTitle("Parameters");
                scene.getStylesheets().addAll("Style.css");
                parameterWindowStage.setX(parentStage.getX() + 250);
                parameterWindowStage.setY(parentStage.getY() + 100);
                parameterWindowStage.setScene(scene);
            }
            parameterWindowStage.show();
            parameterWindowStage.toFront();
        }
    }

    private class SerialMonitorEventHandler
            implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            if (serialMonitorStage == null)
            {
                serialMonitorStage = new Stage();
                SerialMonitorVBox smv = new SerialMonitorVBox(
                        params, parentGui, comm);
                VBox.setVgrow(smv, Priority.ALWAYS);
                Scene scene = new Scene(smv, 300, 250);
                serialMonitorStage.setTitle("Serial Monitor");
                scene.getStylesheets().addAll("Style.css");
                serialMonitorStage.setX(parentStage.getX() + 250);
                serialMonitorStage.setY(parentStage.getY() + 100);
                serialMonitorStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent t) {
                        serialMonitorStage.hide();
                    }
                });
                serialMonitorStage.setScene(scene);
            }
            serialMonitorStage.show();
            serialMonitorStage.toFront();
        }
    }
    public void setParams(double[] params)
    {
        this.params = params;
    }

    public void enableSerialMonitor()
    {
        miSerialMonitor.setDisable(false);
    }

    public void closeWindows()
    {
        if (serialMonitorStage != null)
        {
            serialMonitorStage.close();
        }
        if (parameterWindowStage != null)
        {
            parameterWindowStage.close();
        }
    }
}
