import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;


public class LaMachinaGui extends Application
{
    private final int PADDING = 25;
    private final int SPACING = 10;
    private final int WIDTH = 650, HEIGHT = 500;
    private Stage primary;

    private GridPane gridPane;
    private Scene scene;
    private MenuBar menuBar;
    private HBox firstRow;
    private VBox firstColumn, secondColumn;


    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.primary = primaryStage;
        this.primary.setTitle("Adam is even less of a Chode");
        scene = new Scene(new VBox(), WIDTH, HEIGHT);
        scene.setFill(Color.OLDLACE);
        scene.getStylesheets().addAll("Style.css");
        primary.setScene(scene);

        firstRow = new HBox();
        createMenuBar();
        createFirstColumn();
        createSecondColumn();

        // Make the rows fill horizontally
        //HBox.setHgrow(firstColumn, Priority.ALWAYS);
        HBox.setHgrow(secondColumn, Priority.ALWAYS);

        // Make the column fill vertically
        VBox.setVgrow(firstRow, Priority.ALWAYS);

        ((VBox) scene.getRoot()).getChildren().addAll(firstRow);

        this.primary.show();

    }

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

    public void createFirstColumn()
    {
        firstColumn = new VBox();
        firstColumn.setSpacing(10);
        //firstColumn.setStyle("-fx-background-color: #41e051;");

        Label lblCurrFile = new Label("Current file name");
        //label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        Button btnConnect = new Button("Connect to Machine");
        btnConnect.setMaxWidth(Double.MAX_VALUE);
        btnConnect.getStyleClass().add("glass-grey");

        Button btnZero = new Button("Zero");
        btnZero.setMaxWidth(90);

        Button btnLeft = new Button();
        btnLeft.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("Left Arrow.png"))));
        Button btnRight = new Button();
        btnRight.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("Right Arrow.png"))));
        Button btnClockwise = new Button();
        btnClockwise.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("Clockwise.png"))));
        Button btnCounterClock = new Button();
        btnCounterClock.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("Counterclockwise.png"))));


        Text lop = new Text("Length of Part: ?");
        Text dop = new Text("Diameter of Part: ?");
        Text wop = new Text("Width of Part: ?");

        firstColumn.setSpacing(10);
        firstColumn.setPadding(new Insets(10, 0, 10, 10));

        HBox r1 = new HBox(SPACING);
        r1.getChildren().addAll(btnConnect,lblCurrFile);

        HBox r2 = new HBox(SPACING);
        r2.getChildren().add(btnZero);
        HBox.setHgrow(btnZero, Priority.ALWAYS);
        VBox.setVgrow(btnZero, Priority.ALWAYS);

        HBox r3 = new HBox(SPACING);
        r3.getChildren().addAll(btnLeft, btnRight);

        HBox r4 = new HBox(SPACING);
        r4.getChildren().addAll(btnClockwise, btnCounterClock);

        HBox r5 = new HBox();
        r5.setSpacing(10);
        r5.getChildren().addAll(lop, dop, wop);

        VBox.setVgrow(r1, Priority.ALWAYS);
        VBox.setVgrow(r2, Priority.ALWAYS);
        VBox.setVgrow(r3, Priority.ALWAYS);
        VBox.setVgrow(r4, Priority.ALWAYS);

        firstColumn.getChildren().addAll(r1, r2,r3, r4, r5);

        firstRow.getChildren().addAll(firstColumn);
    }

    public void createSecondColumn()
    {

        secondColumn = new VBox();
        secondColumn.setSpacing(10);
        secondColumn.setPadding(new Insets(10, 10, 10, 10));

        secondColumn.getChildren().addAll(new MachineControlsVBox(), new NewPartVBox());

        firstRow.getChildren().addAll(secondColumn);
    }

    public void createPane()
    {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(0, 10, 0, 10));


        gridPane = new GridPane();
        // changes default alignment from top left to center
        //gridPane.setAlignment(Pos.CENTER);

        // Spacing around the edges of the window
        gridPane.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));

        gridPane.setHgap(0);
        gridPane.setVgap(0);

        Text scenetitle = new Text("Testing");

        gridPane.add(scenetitle, 0, 0, 2, 1);
        gridPane.setHgrow(scenetitle, Priority.ALWAYS);

        Label userName = new Label("User Name:");
        gridPane.add(userName, 0, 1);

        TextField userTextField = new TextField();
        gridPane.add(userTextField, 1, 1);
        vbox.getChildren().addAll(gridPane);

        //debug
        gridPane.setGridLinesVisible(true);

    }

    /**
     * Starts application
     * @param args
     */
    public static void main(String[] args)
    {
        launch(args);
    }


}
