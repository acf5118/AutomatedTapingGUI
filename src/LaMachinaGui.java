


import javafx.application.Application;
import javafx.stage.Stage;

public class LaMachinaGui extends Application
{
    private Stage primary;
    private int q = 24;
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.primary = primaryStage;
        this.primary.setTitle("Adam");
        this.primary.show();
        try {
            System.out.println(test());
            }
        catch (ArithmeticException b)
            {
                System.out.println("Don't divide by zero you twat");
            }
    }

    /**
     * Starts application
     * @param args
     */
    public static void main(String[] args)
    {

        launch(args);

    }

    int test() throws ArithmeticException
    {
        return q/6;
    }
}
