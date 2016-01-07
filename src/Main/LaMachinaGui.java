package Main;

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

/**
 * Created by Adam Fowles on 1/5/2016.
 */
public class LaMachinaGui extends Application
{
    private final int SPACING = 10;
    private final int WIDTH = 530, HEIGHT = 400;
    private Stage primary;

    private Scene scene;
    private MenuBar menuBar;
    private HBox firstRow;
    private VBox firstColumn, secondColumn;


    /**
     * Starting point for the GUI creation
     * @param primaryStage - the "canvas"
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.primary = primaryStage;
        this.primary.setTitle("La Machina GUI");
        scene = new Scene(new VBox(), WIDTH, HEIGHT);
        scene.setFill(Color.OLDLACE);
        scene.getStylesheets().addAll("Style.css");
        primary.setScene(scene);

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

        this.primary.show();
    }

    /**
     * Creates the menu bar at the top
     */
    public void createMenuBar()
    {
        menuBar = new MenuBar();
        // List of menus
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");
        Menu menuSetting = new Menu("Settings");

        // Add all of the menus to the menubar
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView, menuSetting);

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

        Text lop = new Text("Length of Part: __");
        Text dop = new Text("Diameter of Part: __");
        Text wop = new Text("Width of Part: __");

        firstColumn.setPadding(new Insets(10, 0, 10, 10));
        firstColumn.getChildren().addAll(new ConnectionVBox(), new MovementControlsVBox(), lop,dop,wop);
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
        secondColumn.getChildren().addAll(new MachineControlsVBox(), new PartCreationVBox());
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


}
