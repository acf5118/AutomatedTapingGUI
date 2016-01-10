package Components;

import FileIO.ParameterReader;
import javafx.beans.NamedArg;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by Adam Fowles on 1/9/2016.
 */
public class ParameterWindowHBox extends VBox implements ComponentInterface
{
    private double[] params;
    public ParameterWindowHBox(double[] params)
    {
        super(10);
        this.params = params;
        createComponents();
    }

    @Override
    public void createComponents()
    {

        createFirstColumn();
        setPadding(new Insets(SPACING,SPACING,SPACING,SPACING));
    }

    public void createFirstColumn()
    {
        Label lblJog = new Label("Jog");
        lblJog.setAlignment(Pos.CENTER);

        Separator spacer = new Separator();

        VBox middle = new VBox();
        middle.setPadding(new Insets(0,SPACING,SPACING,SPACING));
        middle.getStyleClass().add("bordered-titled-border");
        // Inside offsets, none for the top, and 5 for
        // the right, bottom and left
        setPadding(new Insets(0,5,5,5));
        Label lblTranslate = new Label("Translate");
        lblTranslate.getStyleClass().add("bordered-titled-title");

        Label lblInc = new Label("Increment");
        lblInc.setPrefWidth(TF_SIZE);
        TextField tfInc = new TextField();
        tfInc.setPromptText(Double.toString(params[3]));
        tfInc.setPrefWidth(TF_SIZE);

        Label lblMaxSpeed = new Label("Max Speed");
        lblMaxSpeed.setPrefWidth(TF_SIZE);
        TextField tfMaxSpeed = new TextField();
        tfMaxSpeed.setPrefWidth(TF_SIZE);
        tfMaxSpeed.setPromptText(Double.toString(params[4]));

        HBox r1 = new HBox(SPACING);
        r1.getChildren().addAll(lblInc, tfInc);

        HBox r2 = new HBox(SPACING);
        r2.getChildren().addAll(lblMaxSpeed, tfMaxSpeed);
        middle.getChildren().addAll(lblTranslate, r1, r2);

        // HERE

        VBox rotate = new VBox();
        rotate.setPadding(new Insets(0,SPACING,SPACING,SPACING));
        rotate.getStyleClass().add("bordered-titled-border");
        // Inside offsets, none for the top, and 5 for
        // the right, bottom and left
        setPadding(new Insets(0,5,5,5));
        Label lblRotate = new Label("Rotate");
        lblRotate.getStyleClass().add("bordered-titled-title");

        Label lblIncR = new Label("Increment");
        lblIncR.setPrefWidth(TF_SIZE);
        TextField tfIncR = new TextField();
        tfIncR.setPromptText(Double.toString(params[0]));
        tfIncR.setPrefWidth(TF_SIZE);

        Label lblMaxSpeedR = new Label("Max Speed");
        lblMaxSpeedR.setPrefWidth(TF_SIZE);
        TextField tfMaxSpeedR = new TextField();
        tfMaxSpeedR.setPromptText(Double.toString(params[1]));
        tfMaxSpeedR.setPrefWidth(TF_SIZE);

        HBox r1R = new HBox(SPACING);
        r1R.getChildren().addAll(lblIncR, tfIncR);

        HBox r2R = new HBox(SPACING);
        r2R.getChildren().addAll(lblMaxSpeedR, tfMaxSpeedR);
        rotate.getChildren().addAll(lblRotate, r1R, r2R);

        HBox r3 = new HBox(SPACING);

        Button btnDefault = new Button("Default");
        Button btnApply = new Button("Apply");
        Button btnCancel = new Button("Cancel");

        r3.getChildren().addAll(btnDefault, btnApply, btnCancel);

        getChildren().addAll(lblJog, spacer, middle, rotate, r3);

    }
}
