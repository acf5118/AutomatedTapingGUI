package Components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * Created by Adam Fowles on 1/6/2016.
 */
public class PartCreationVBox
        extends VBox implements ComponentInterface
{
    // Constants
    private ArrayList<TextField> fields;

    /**
     * Constructor for this part of
     * the GUI
     */
    public PartCreationVBox()
    {
        super();
        // See Style.css
        getStyleClass().add("bordered-titled-border");
        // Inside offsets, none for the top, and 5 for
        // the right, bottom and left
        setPadding(new Insets(0,5,5,5));
        fields = new ArrayList<TextField>();
        createComponents();
    }

    /**
     * Creates all the inner components
     */
    public void createComponents()
    {
        // Title on the border
        Label lblBorderTitle = new Label("Program New Part");
        lblBorderTitle.getStyleClass().add("bordered-titled-title");

        // First label associated with corresponding text field
        Label lblPartLength = new Label("Length of Part:");
        // Text field to enter data
        TextField tfPartLength =  new TextField();
        tfPartLength.setPromptText("Enter Length");
        // sets the size of the text field
        tfPartLength.setPrefWidth(TF_SIZE);

        // Second items
        Label lblPartDiameter = new Label("Diameter of Part:");
        TextField tfPartDiameter =  new TextField();
        tfPartDiameter.setPromptText("Enter Diameter");
        tfPartDiameter.setPrefWidth(TF_SIZE);

        // Third items
        Label lblPartTapeType = new Label("Tape Type of Part:");
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Ad",
                        "Num"
                );
        ComboBox<String> comboTapeType = new ComboBox(options);
        comboTapeType.setValue(options.get(0));
        comboTapeType.setPrefWidth(TF_SIZE);

        // Fourth items
        Label lblPartTapeWidth = new Label("Tape Width:");
        TextField tfPartTapeWidth =  new TextField();
        tfPartTapeWidth.setPromptText("Enter Width");
        tfPartTapeWidth.setPrefWidth(TF_SIZE);

        // Fifth items
        Label lblTapeSections = new Label("Number of Tape Sections:");
        TextField tfTapeSections =  new TextField();
        tfTapeSections.setPromptText("Enter Sections");
        tfTapeSections.setPrefWidth(TF_SIZE);

        // Sixth items
        Label lblStartEnd = new Label("Start and End of Sections:");
        TextField tfStart = new TextField();
        tfStart.setTooltip(new Tooltip("Comma separate values \nfor multiple sections"));
        tfStart.setPromptText("Start");
        tfStart.setPrefWidth(TF_SIZE/2 - 1);
        TextField tfEnd = new TextField();
        tfEnd.setTooltip(new Tooltip("Comma separate values \nfor multiple sections"));
        tfEnd.setPromptText("End");
        tfEnd.setPrefWidth(TF_SIZE/2 - 1);

        // Spacers separating text field from labels
        Region s1 = new Region(),s2 = new Region(),
                s3 = new Region(),s4 = new Region(),
                s5 = new Region(),s6 = new Region();

        HBox.setHgrow(s1, Priority.ALWAYS);HBox.setHgrow(s2, Priority.ALWAYS);
        HBox.setHgrow(s3, Priority.ALWAYS);HBox.setHgrow(s4, Priority.ALWAYS);
        HBox.setHgrow(s5, Priority.ALWAYS);HBox.setHgrow(s6, Priority.ALWAYS);

        // Rows 1 through 7
        HBox r1 = new HBox(SPACING);
        r1.getChildren().addAll(lblPartLength,s1, tfPartLength);

        HBox r2 = new HBox(SPACING);
        r2.getChildren().addAll(lblPartDiameter, s2, tfPartDiameter);

        HBox r3 = new HBox(SPACING);
        r3.getChildren().addAll(lblPartTapeType, s3, comboTapeType);

        HBox r4 = new HBox(SPACING);
        r4.getChildren().addAll(lblPartTapeWidth,s4, tfPartTapeWidth);

        HBox r5 = new HBox(SPACING);
        r5.getChildren().addAll(lblTapeSections,s5, tfTapeSections);

        HBox r6 = new HBox(SPACING/5);
        r6.getChildren().addAll(lblStartEnd, s6, tfStart, tfEnd);

        fields.add(tfPartLength);
        fields.add(tfPartDiameter);
        fields.add(tfPartTapeWidth);
        fields.add(tfTapeSections);
        fields.add(tfStart);
        fields.add(tfEnd);

        // Add a separator between rows 6 and 7
        Separator separator = new Separator();
        separator.setPadding(new Insets(0,0,5,0));

        // add all the components to this VBox
        getChildren().addAll(lblBorderTitle,r1,r2,r3,r4,r5,r6,
                separator, new FileControlHBox(this));

        // Make sure everything expands properly
        HBox.setHgrow(this, Priority.ALWAYS);
        VBox.setVgrow(this, Priority.ALWAYS);

        VBox.setVgrow(r1, Priority.ALWAYS);
        VBox.setVgrow(r2, Priority.ALWAYS);
        VBox.setVgrow(r3, Priority.ALWAYS);
        VBox.setVgrow(r4, Priority.ALWAYS);
        VBox.setVgrow(r5, Priority.ALWAYS);
        VBox.setVgrow(r6, Priority.ALWAYS);

    }

    public ArrayList<TextField> getFields(){return fields;}
}
