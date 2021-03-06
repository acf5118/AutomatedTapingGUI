package Main;

import Components.Menu.LaMachinaMenuBar;
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
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import Components.*;
import javafx.stage.WindowEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Adam Fowles on 1/5/2016.
 * The main application, starting point
 * for the program.
 */
public class LaMachinaGui extends Application
{
    private final int SPACING = 10;
    private final int WIDTH = 530, HEIGHT = 460;
    private final long SPLASH_SCREEN_DELAY = 2000;
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
    private PartCreationVBox partCreation;
    private LaMachinaMenuBar menuBar;

    // Debug flag for entire program
    public static boolean DEBUG = false;

    public void init() throws Exception
    {
       Thread.sleep(SPLASH_SCREEN_DELAY);
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
        this.primaryStage.setTitle("La Machina");
        this.primaryStage.getIcons().add(new Image(
                getClass().getResourceAsStream("/Resources/LM Icon.png")));
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
                quit();
            }
        });
        this.primaryStage.show();
    }

    /**
     * Creates the menu bar at the top
     */
    public void createMenuBar()
    {
        menuBar = new LaMachinaMenuBar(primaryStage, params, this, serialComm);
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
        partCreation = new PartCreationVBox(this);
        secondColumn.getChildren().addAll(playback, partCreation);
        firstRow.getChildren().addAll(secondColumn);
    }

    public ArrayList<Button> getControlButtons(){return mvControls.getButtons();}
    public void enablePlayback(){playback.enableButtons();}
    public void toggleControls(boolean toggle){mvControls.toggleAllButtons(toggle);}
    public void updateParameters(double[] params)
    {
        mvControls.updateParameters(params);
        menuBar.setParams(params);
    }

    public void updateProgramParameters(HashMap<String, Double> params, double[] mod, File file)
    {
        playback.setGCodeLines(GCodeGenerator.generateLines(mod));
        playback.setParams(mod);
        quickView.setParams(params, mod);
        quickView.enableFullView();
        machineStatus.setFilename(file.getName());
        // Update the file choosing based on where the last file came from
        partCreation.updateFileControl(file);
        menuBar.updateFileMenu(file);
    }

    public Stage getPrimaryStage(){return primaryStage;}
    public void reset(){mvControls.reset();}

    public void updateState(UpdateMessageEnum type, String[] args)
    {
        switch(type)
        {
            case CONNECTION:
                machineStatus.updateStatus(args);
                menuBar.enableSerialMonitor();
                break;
        }
    }

    public void setAlarmMessage()
    {
        machineStatus.setAlarm();
    }

    /**
     * Quit the Machine, tidy up
     */
    public void quit()
    {
        serialComm.shutdown();
        quickView.close();
        menuBar.closeWindows();
        primaryStage.close();
    }

    /**
     * Starts application
     * @param args - program arguments (none used)
     */
    public static void main(String[] args)
    {
        LauncherImpl.launchApplication(LaMachinaGui.class, GraphicsPreloader.class, args);
    }
}
