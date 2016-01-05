import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;



public class LaMachinaGui extends Application
{
    private final int PADDING = 25;
    private final int WIDTH = 400, HEIGHT = 500;
    private Stage primary;
    private GridPane gridPane;
    private Scene scene;
    private MenuBar menuBar;
    private HBox firstRow;
    private VBox firstColumn, secondColumn;

    @Override
    public void start(Stage primaryStage)
            throws Exception
    {
        this.primary = primaryStage;
        this.primary.setTitle("Adam is less of a Chode");
        scene = new Scene(new VBox(), WIDTH, HEIGHT);
        scene.setFill(Color.OLDLACE);
        primary.setScene(scene);

        firstRow = new HBox();
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
        Button btnConnect = new Button("Connect \n to Machine");
        btnConnect.setMaxWidth(Double.MAX_VALUE);

        Button btnZero = new Button("Zero");
        btnZero.setMaxWidth(Double.MAX_VALUE);

        // Will have images to go with thiss
        Button btnLeft = new Button("Left");
        Button btnRight = new Button("Right");

        Button btnArrowLeft = new Button("Arrow Left");
        Button btnArrowRight = new Button("Arrow Right");

        firstColumn.setSpacing(10);
        firstColumn.setPadding(new Insets(10, 0, 10, 10));

        HBox r1 = new HBox();
        r1.setSpacing(10);
        r1.getChildren().addAll(lblCurrFile, btnConnect);

        HBox r2 = new HBox();
        r2.setSpacing(10);
        r2.getChildren().add(btnZero);
        HBox.setHgrow(btnZero, Priority.ALWAYS);
        VBox.setVgrow(btnZero, Priority.ALWAYS);

        HBox r3 = new HBox();
        r3.setSpacing(10);
        r3.getChildren().addAll(btnLeft, btnRight);

        HBox r4 = new HBox();
        r4.setSpacing(10);
        r4.getChildren().addAll(btnArrowLeft, btnArrowRight);

        HBox r5 = new HBox();


        VBox.setVgrow(r1, Priority.ALWAYS);
        VBox.setVgrow(r2, Priority.ALWAYS);
        VBox.setVgrow(r3, Priority.ALWAYS);
        VBox.setVgrow(r4, Priority.ALWAYS);

        firstColumn.getChildren().addAll(r1, r2, r3, r4);

        firstRow.getChildren().addAll(firstColumn);
    }

    public void createSecondColumn()
    {

        secondColumn = new VBox();
        Button btnStart = new Button("Start");
        btnStart.setStyle("-fx-base: #41e051;");
        Button btnEStop = new Button("Emergency Stop");
        btnEStop.setStyle("-fx-base: #ef1515;");


        btnStart.setMaxWidth(Double.MAX_VALUE);
        btnEStop.setMaxWidth(Double.MAX_VALUE);


        HBox vbButtons = new HBox();
        secondColumn.setSpacing(10);
        secondColumn.setPadding(new Insets(10, 0, 10, 10));
        secondColumn.getChildren().addAll(btnStart, btnEStop);

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
