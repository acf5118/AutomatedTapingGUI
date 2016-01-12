package Components;

import Main.LaMachinaGui;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Adam Fowles on 1/11/2016.
 */
public class FullViewVBox
        extends VBox implements ComponentInterface
{
    private double[] params;

    public FullViewVBox(double[] params)
    {
        super(SPACING);
        this.params = params;
        createComponents();
    }

    @Override
    public void createComponents()
    {
        setPadding(new Insets(SPACING,SPACING,SPACING,SPACING));
        Label lblLength = new Label("Length: " + params[0]);
        Label x1 = new Label("x1: " + params[1]);
        Label x2 = new Label("x2: " + params[2]);
        Label x3 = new Label("x3: " + params[3]);
        Label y1 = new Label("y1: " + params[4]);
        Label y2 = new Label("y2 " + params[5]);
        Label y3 = new Label("y3: " + params[6]);
        Label f1 = new Label("f1: " + params[7]);
        Label f2 = new Label("f2: " + params[8]);

        getChildren().addAll(lblLength, x1,x2,x3,y1,y2,y3,f1,f2);
    }
}
