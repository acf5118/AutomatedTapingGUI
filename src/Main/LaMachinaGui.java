package Main;

import FileIO.ParameterReader;
import GCodeUtil.GCodeGenerator;
import Serial.ArduinoSerial;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import Components.*;

import java.util.ArrayList;

/**
 * Created by Adam Fowles on 1/5/2016.
 */
public class LaMachinaGui extends Application
{
    private final int SPACING = 10;
    private final int WIDTH = 530, HEIGHT = 425;
    private Stage primaryStage;

    private Scene scene;
    private MenuBar menuBar;
    private HBox firstRow;
    private VBox firstColumn, secondColumn;
    private ArduinoSerial arduinoSerial;
    private ParameterReader paramReader;

    // Different Components
    private MovementControlsVBox mvControls;
    private PlaybackVBox playback;
    private QuickViewVBox quickView;
    private MachineStatusVBox machineStatus;

    /**
     * Starting point for the GUI creation
     * @param primaryStage - the "canvas"
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        paramReader = new ParameterReader("src/ParameterSettings");
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("La Machina GUI");
        scene = new Scene(new VBox(), WIDTH, HEIGHT);
        scene.setFill(Color.OLDLACE);
        scene.getStylesheets().addAll("Style.css");
        primaryStage.setScene(scene);
        arduinoSerial = new ArduinoSerial();

        firstRow = new HBox(SPACING*2);
        createMenuBar();
        createFirstColumn();
        createSecondColumn();

        // Make the rows fill horizontally
        HBox.setHgrow(firstColumn, Priority.ALWAYS);
        HBox.setHgrow(secondColumn, Priority.ALWAYS);

        // Make the column fill vertically
        VBox.setVgrow(firstRow, Priority.ALWAYS);
        ((VBox) scene.getRoot()).getChildren().addAll(firstRow);

        this.primaryStage.show();
    }

    /**
     * Creates the menu bar at the top
     */
    public void createMenuBar()
    {
        ((VBox)scene.getRoot()).getChildren().addAll(
                new LaMachinaMenuBar(primaryStage,paramReader.getParams(), this));
    }

    /**
     * Creates the first column with components
     * from the Components Package.
     */
    public void createFirstColumn()
    {
        firstColumn = new VBox();
        firstColumn.setSpacing(10);

        firstColumn.setPadding(new Insets(10, 0, 10, 10));
        mvControls = new MovementControlsVBox(
                arduinoSerial, paramReader.getParams(), this);
        quickView = new QuickViewVBox(primaryStage);
        machineStatus = new MachineStatusVBox(arduinoSerial, this);
        firstColumn.getChildren().addAll(
                machineStatus, mvControls, quickView);
        firstRow.getChildren().addAll(firstColumn);
    }

    /**
     * Creates the second column with
     * components from Components package.
     */
    public void createSecondColumn()
    {
        secondColumn = new VBox();
        secondColumn.setSpacing(SPACING);
        secondColumn.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));
        playback = new PlaybackVBox(arduinoSerial);
        secondColumn.getChildren().addAll(playback, new PartCreationVBox(this));
        firstRow.getChildren().addAll(secondColumn);
    }

    /**
     * Starts application
     * @param args - program arguments (none used)
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    public ArrayList<Button> getControlButtons(){return mvControls.getButtons();}
    public void enablePlayback(){playback.enableButtons();}
    public void updateParameters(double[] params){mvControls.updateParameters(params);}
    public void updateProgramParameters(double[] params, double[] mod, String filename)
    {
        GCodeGenerator gc = new GCodeGenerator();
        playback.setGCodeLines(gc.generateLines(mod));
        quickView.setParams(params, mod);
        quickView.enableFullView();
        machineStatus.setFilename(filename);


    }
    public Stage getPrimaryStage(){return primaryStage;}
}
