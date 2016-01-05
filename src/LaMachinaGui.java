import javafx.application.Application;
import javafx.stage.Stage;


public class LaMachinaGui extends Application
{
    private Stage primary;
    @Override
    public void start(Stage primaryStage)
            throws Exception
    {
        this.primary = primaryStage;
        this.primary.setTitle("Adam is a Chode");
        this.primary.show();
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
