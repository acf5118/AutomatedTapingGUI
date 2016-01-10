package Main;

import FileIO.ParameterReader;
import Serial.ArduinoSerial;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
    private final int WIDTH = 530, HEIGHT = 400;
    private Stage primaryStage;

    private Scene scene;
    private MenuBar menuBar;
    private HBox firstRow;
    private VBox firstColumn, secondColumn;
    private ArduinoSerial arduinoSerial;
    private ParameterReader paramReader;

    // Different Components
    private MovementControlsVBox mvControls;
    /**
     * Starting point for the GUI creation
     * @param primaryStage - the "canvas"
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        paramReader = new ParameterReader("src/Settings");
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
                new LaMachinaMenuBar(primaryStage,paramReader.getParams()));
    }

    /**
     * Creates the first column with components
     * from the Components Package.
     */
    public void createFirstColumn()
    {
        firstColumn = new VBox();
        firstColumn.setSpacing(10);

        Text lop = new Text("Length of Part: __");
        Text dop = new Text("Diameter of Part: __");
        Text wop = new Text("Width of Part: __");

        firstColumn.setPadding(new Insets(10, 0, 10, 10));
        mvControls = new MovementControlsVBox(
                arduinoSerial, paramReader.getParams());
        firstColumn.getChildren().addAll(new MachineStatusVBox(arduinoSerial, this),
                mvControls, lop,dop,wop);
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
        secondColumn.getChildren().addAll(new PlaybackVBox(), new PartCreationVBox());
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

}
