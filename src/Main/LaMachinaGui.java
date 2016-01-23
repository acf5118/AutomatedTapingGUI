package Main;

import FileIO.ProgramFileReader;
import GCodeUtil.GCodeGenerator;
import Serial.SerialCommunication;
import Serial.UpdateMessageEnum;
import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import Components.*;
import javafx.stage.WindowEvent;

import java.util.ArrayList;

/**
 * Created by Adam Fowles on 1/5/2016.
 */
public class LaMachinaGui extends Application
{
    private final int SPACING = 10;
    private final int WIDTH = 530, HEIGHT = 460;
    private double[] params;
    private Stage primaryStage;

    private Scene scene;
    private HBox firstRow;
    private VBox firstColumn, secondColumn;
    private SerialCommunication serialComm;

    // Different Components
    private MovementControlsVBox mvControls;
    private PlaybackVBox playback;
    private QuickViewVBox quickView;
    private MachineStatusVBox machineStatus;
    private LaMachinaMenuBar menuBar;

    public void init() throws Exception
    {
        Thread.sleep(2000);
    }

    /**
     * Starting point for the GUI creation
     * @param primaryStage - the "canvas"
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.params = ProgramFileReader.readParameterFile(this.getClass());
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("La Machina GUI");
        scene = new Scene(new VBox(), WIDTH, HEIGHT);
        scene.setFill(Color.OLDLACE);
        scene.getStylesheets().addAll("Style.css");
        primaryStage.setScene(scene);

        // Set the application to the center to the screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screenBounds.getWidth() - WIDTH) / 2);
        primaryStage.setY((screenBounds.getHeight() - HEIGHT) / 2);

        serialComm = new SerialCommunication(this);

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

        this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                serialComm.shutdown();
            }
        });
        this.primaryStage.show();
    }

    /**
     * Creates the menu bar at the top
     */
    public void createMenuBar()
    {
        menuBar = new LaMachinaMenuBar(primaryStage,params, this, serialComm);
        ((VBox)scene.getRoot()).getChildren().addAll(menuBar);
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
                serialComm,params, this);
        quickView = new QuickViewVBox(primaryStage);
        machineStatus = new MachineStatusVBox(serialComm, this);
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
        playback = new PlaybackVBox(serialComm, this);
        secondColumn.getChildren().addAll(playback, new PartCreationVBox(this));
        firstRow.getChildren().addAll(secondColumn);
    }

    /**
     * Starts application
     * @param args - program arguments (none used)
     */
    public static void main(String[] args)
    {
        LauncherImpl.launchApplication(LaMachinaGui.class, GraphicsPreloader.class, args);
    }

    public ArrayList<Button> getControlButtons(){return mvControls.getButtons();}
    public void enablePlayback(){playback.enableButtons();}
    public void toggleControls(boolean toggle){mvControls.toggleAllButtons(toggle);}
    public void updateParameters(double[] params)
    {
        mvControls.updateParameters(params);
        menuBar.setParams(params);
    }

    public void updateProgramParameters(double[] params, double[] mod, String filename)
    {
        playback.setGCodeLines(GCodeGenerator.generateLines(mod));
        playback.setParams(mod);
        quickView.setParams(params, mod);
        quickView.enableFullView();
        machineStatus.setFilename(filename);


    }

    public Stage getPrimaryStage(){return primaryStage;}
    //public ArduinoSerialConnection getArduinoSerial(){return arduinoSerial;}
    public void enableSerialMonitor(){}
    public void reset(){mvControls.reset();}

    public void updateState(UpdateMessageEnum type, String[] args)
    {
        switch(type)
        {
            case CONNECTION:
                machineStatus.updateStatus(args);
                break;
        }
    }
}
