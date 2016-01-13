package Components;

import FileIO.ProgramFileReader;
import Main.LaMachinaGui;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Adam Fowles on 1/9/2016.
 */
public class LaMachinaMenuBar extends MenuBar
{
    private Stage parentStage;
    private double[] params;
    private LaMachinaGui parentGui;

    public LaMachinaMenuBar(Stage parent,
                            double[] params,
                            LaMachinaGui gui)
    {
        super();
        createMenu();
        parentStage = parent;
        this.params = params;
        this.parentGui = gui;
    }

    public void createMenu()
    {
        // List of menus
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");
        Menu menuSetting = new Menu("Settings");

        MenuItem miParameters = new MenuItem("Parameters");
        miParameters.setOnAction(new ParametersEventHandler());

        MenuItem miOpen = new MenuItem("Open Program File");
        miOpen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                File file = fc.showOpenDialog(parentGui.getPrimaryStage());
                if (file == null){return;}
                ArrayList<double[]> pr = ProgramFileReader.readProgramFile(file);
                parentGui.updateProgramParameters(pr.get(0), pr.get(1), file.getName());
            }
        });
        menuFile.getItems().add(miOpen);
        menuSetting.getItems().add(miParameters);

        // Add all of the menus to the menubar
        getMenus().addAll(menuFile, menuEdit, menuView, menuSetting);
    }

    private class ParametersEventHandler
            implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            Stage stage = new Stage();
            Scene scene = new Scene(new ParameterWindowHBox(params, stage, parentGui)); stage.show();
            stage.setTitle("Parameters");
            scene.getStylesheets().addAll("Style.css");
            stage.setX(parentStage.getX() + 250);
            stage.setY(parentStage.getY() + 100);

            stage.setScene(scene);
            stage.show();
        }
    }
}
