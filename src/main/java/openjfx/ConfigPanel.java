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

    private final Label vertexCountLabel = new Label("Number of vertices:");
    private final TextField vertexCountInput = new TextField();

    private final Label generateRandomEdgesLabel = new Label("Generate random edges?");
    private final CheckBox randomisedEdgeCheckBox = new CheckBox();

    private final Label edgeProababilityLabel = new Label("Edge probability:0.5");
    private final Slider edgeProbabilitySlider = new Slider(0.0, 1.0, 0.5);

    private final Label graphTypeLabel = new Label("Choose the graph type:");
    private final ComboBox<String> graphTypeDropdown = new ComboBox<>(
            FXCollections.observableArrayList(
                    "SimpleGraph",
                    "BipartiteGraph",
                    "FlowNetwork"
            )
    );

    private final Button generateGraphButton = new Button("Generate");

    public ConfigPanel() {
        super();

        this.setHgap(50);
        this.setAlignment(Pos.CENTER);

        this.addColumn(0, this.vertexCountLabel, this.vertexCountInput);

        this.addColumn(1, this.generateRandomEdgesLabel, this.randomisedEdgeCheckBox);
        GridPane.setHalignment(this.randomisedEdgeCheckBox, HPos.CENTER);

        this.addColumn(2,this.edgeProababilityLabel, this.edgeProbabilitySlider );
        this.edgeProbabilitySlider.setDisable(true);

        this.addColumn(3, this.graphTypeLabel, this.graphTypeDropdown);

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
            e -> System.out.println("Graph")
        );
    }

    private void enableInputVerticesFormatter() {
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String input = change.getControlNewText();
            if ( input.matches("^[1-9]\\d*$|")) {
                if ( ! input.equals( "" ) && Integer.parseInt( input ) > 25 ) {
                    return null;
                }
                return change;
            }
            return null;
        };
        this.vertexCountInput.setTextFormatter(new TextFormatter<>(integerFilter));
    }
}
