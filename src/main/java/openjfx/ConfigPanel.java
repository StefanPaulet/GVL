package openjfx;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.text.DecimalFormat;
import java.util.function.UnaryOperator;

public class ConfigPanel extends GridPane {

    private final Engine engine;
    private final TextField vertexCountInput = new TextField();

    private final CheckBox randomisedEdgeCheckBox = new CheckBox();

    private final Label edgeProababilityLabel = new Label("Edge probability:0.5");
    private final Slider edgeProbabilitySlider = new Slider(0.0, 1.0, 0.5);

    private final ComboBox<String> graphTypeDropdown = new ComboBox<>(
            FXCollections.observableArrayList(
                    "DirectedGraph",
                    "UndirectedGraph",
                    "DirectedBipartiteGraph",
                    "UndirectedBipartiteGraph"
            )
    );

    private final Button generateGraphButton = new Button("Generate");

    public ConfigPanel(Engine engine) {
        super();

        this.engine = engine;

        this.setHgap(50);
        this.setAlignment(Pos.CENTER);

        Label vertexCountLabel = new Label( "Number of vertices:" );
        this.addColumn(0, vertexCountLabel, this.vertexCountInput);

        Label generateRandomEdgesLabel = new Label( "Generate random edges?" );
        this.addColumn(1, generateRandomEdgesLabel, this.randomisedEdgeCheckBox);
        GridPane.setHalignment(this.randomisedEdgeCheckBox, HPos.CENTER);

        this.addColumn(2,this.edgeProababilityLabel, this.edgeProbabilitySlider );
        this.edgeProbabilitySlider.setDisable(true);

        Label graphTypeLabel = new Label( "Choose the graph type:" );
        this.addColumn(3, graphTypeLabel, this.graphTypeDropdown);
        this.graphTypeDropdown.setValue( "" );

        this.add(this.generateGraphButton, 4, 1);
        GridPane.setValignment(this.generateGraphButton, VPos.CENTER);

        this.enableInputVerticesFormatter();
        this.randomisedEdgeCheckBox.setOnAction(
            e -> this.edgeProbabilitySlider.setDisable(!this.randomisedEdgeCheckBox.isSelected())
        );
        this.edgeProbabilitySlider.valueProperty().addListener(
                e -> {
                    double value = this.edgeProbabilitySlider.getValue();
                    this.edgeProababilityLabel.setText(
                        "Edge probability:" + new DecimalFormat("#.##").format(value)
                    );
                }
        );

        this.generateGraphButton.setOnAction(
            e -> this.generateNewGraph()
        );
    }

    private void enableInputVerticesFormatter() {
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String input = change.getControlNewText();
            if ( input.matches("^[1-9]\\d*$|")) {
                if ( ! input.equals( "" ) && Integer.parseInt( input ) > 15 ) {
                    return null;
                }
                return change;
            }
            return null;
        };
        this.vertexCountInput.setTextFormatter(new TextFormatter<>(integerFilter));
    }


    private void generateNewGraph() {

        String graphType = this.graphTypeDropdown.getValue();
        if ( graphType.equals( "" ) ) {
            this.engine.getInfoPanel().setSystemMessage( "You must select a graph type" );
            return;
        }

        if ( this.vertexCountInput.textProperty().getValue().equals( "" ) ) {
            this.engine.getInfoPanel().setSystemMessage( "You must provide a number of vertices between 1 and 15" );
            return;
        }
        int nodeCount = Integer.parseInt( this.vertexCountInput.textProperty().getValue() );

        double edgeProbability = this.randomisedEdgeCheckBox.isSelected() ? this.edgeProbabilitySlider.getValue() : 0.0;

        this.engine.instantiateGraph( graphType, nodeCount, edgeProbability );
    }
}
