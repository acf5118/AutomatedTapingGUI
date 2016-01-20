package Components;

import Main.LaMachinaGui;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import javax.xml.soap.Text;
import java.util.ArrayList;

/**
 * Created by Adam Fowles on 1/6/2016.
 */
public class PartCreationVBox
        extends VBox implements ComponentInterface
{
    // Constants
    private ArrayList<TextField> fields;
    private LaMachinaGui parent;
    private CheckBox cbApply;

    /**
     * Constructor for this part of
     * the GUI
     */
    public PartCreationVBox(LaMachinaGui parent)
    {
        super();
        // See Style.css
        getStyleClass().add("bordered-titled-border");
        // Inside offsets, none for the top, and 5 for
        // the right, bottom and left
        setPadding(new Insets(0,5,5,5));
        fields = new ArrayList<TextField>();
        this.parent = parent;
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
        tfPartLength.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TextField tf = (TextField)event.getSource();
                tf.clear();
                tf.setStyle("-fx-text-fill: #333333;");
            }
        });
        tfPartLength.setPromptText("Enter Length");
        // sets the size of the text field
        tfPartLength.setPrefWidth(TF_SIZE);

        // Second items
        Label lblPartDiameter = new Label("Diameter of Part:");
        TextField tfPartDiameter =  new TextField();
        tfPartDiameter.setPromptText("Enter Diameter");
        tfPartDiameter.setPrefWidth(TF_SIZE);

        // Third items
        Label lblPartTapeType = new Label("Tape Type:");
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Adhesive",
                        "Non-Adhesive"
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
        Label lblTapePercent = new Label("Tape Overlap Percentage:");
        TextField tfTapePercent=  new TextField();
        tfTapePercent.setPromptText("Enter %");
        tfTapePercent.setTooltip(new Tooltip("A value from 0 to 100"));
        tfTapePercent.setPrefWidth(TF_SIZE);

        // Sixth items
        Label lblNumLayers = new Label("Number of Layers:");
        ObservableList<Integer> optionsLayers =
                FXCollections.observableArrayList(
                        2,
                        4,
                        6
                );
        ComboBox<Integer> comboNumLayers = new ComboBox(optionsLayers);
        comboNumLayers.setValue(optionsLayers.get(0));
        comboNumLayers.setPrefWidth(TF_SIZE);

        // Seventh items
        Label lblStartEnd = new Label("Start and End of Sections:");
        TextField tfStart = new TextField();
        tfStart.setTooltip(new Tooltip("Comma separate values \nfor multiple sections"));
        tfStart.setPromptText("Start");
        tfStart.setPrefWidth(TF_SIZE/2 - 1);
        TextField tfEnd = new TextField();
        tfEnd.setTooltip(new Tooltip("Comma separate values \nfor multiple sections"));
        tfEnd.setPromptText("End");
        tfEnd.setPrefWidth(TF_SIZE/2 - 1);

        // Eighth items
        Label lblPartRPM = new Label("Part RPM:");
        TextField tfPartRPM = new TextField();
        tfPartRPM.setPromptText("Enter RPM");
        tfPartRPM.setPrefWidth(TF_SIZE);

        // Ninth items
        cbApply = new CheckBox("Load Changes on Creation");
        cbApply.setPadding(new Insets(0,0,SPACING/2,0));

        // Spacers separating text field from labels
        Region s1 = new Region(), s2 = new Region(),
                s3 = new Region(), s4 = new Region(),
                s5 = new Region(), s6 = new Region(),
                s7 = new Region(), s8 = new Region();

        HBox.setHgrow(s1, Priority.ALWAYS);HBox.setHgrow(s2, Priority.ALWAYS);
        HBox.setHgrow(s3, Priority.ALWAYS);HBox.setHgrow(s4, Priority.ALWAYS);
        HBox.setHgrow(s5, Priority.ALWAYS);HBox.setHgrow(s6, Priority.ALWAYS);
        HBox.setHgrow(s7, Priority.ALWAYS);HBox.setHgrow(s8, Priority.ALWAYS);

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
        r5.getChildren().addAll(lblTapePercent,s5, tfTapePercent);

        HBox r6 = new HBox(SPACING);
        r6.getChildren().addAll(lblNumLayers, s6, comboNumLayers);

        HBox r7 = new HBox(SPACING/5);
        r7.getChildren().addAll(lblStartEnd, s7, tfStart, tfEnd);

        HBox r8 = new HBox(SPACING);
        r8.getChildren().addAll(lblPartRPM, s8, tfPartRPM);

        fields.add(tfPartLength);
        fields.add(tfPartDiameter);
        fields.add(tfPartTapeWidth);
        fields.add(tfTapePercent);
        fields.add(tfStart);
        fields.add(tfEnd);
        fields.add(tfPartRPM);


        // Add a separator between rows 6 and 7
        Separator separator = new Separator();
        separator.setPadding(new Insets(0,0,5,0));

        // add all the components to this VBox
        getChildren().addAll(lblBorderTitle,r1,r2,r3,r4,r5,r6,r7,r8,
                separator, cbApply, new FileControlHBox(this,parent));

        // Make sure everything expands properly
        HBox.setHgrow(this, Priority.ALWAYS);
        VBox.setVgrow(this, Priority.ALWAYS);

        VBox.setVgrow(r1, Priority.ALWAYS);
        VBox.setVgrow(r2, Priority.ALWAYS);
        VBox.setVgrow(r3, Priority.ALWAYS);
        VBox.setVgrow(r4, Priority.ALWAYS);
        VBox.setVgrow(r5, Priority.ALWAYS);
        VBox.setVgrow(r6, Priority.ALWAYS);
        VBox.setVgrow(r7, Priority.ALWAYS);
        VBox.setVgrow(r8, Priority.ALWAYS);

    }

    public ArrayList<TextField> getFields(){return fields;}
    public boolean loadChanges(){return cbApply.isSelected();}

}
