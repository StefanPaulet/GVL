package openjfx;

import graph.Edge;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.text.DecimalFormat;
import java.util.function.UnaryOperator;

public class ConfigPanel extends GridPane {

    private static final EngineFactory engineFactory = new EngineFactory();

    private Engine engine;
    private final TextField vertexCountInput = new TextField();

    private final CheckBox randomisedEdgeCheckBox = new CheckBox();

    private final Label edgeProababilityLabel = new Label( "Edge probability:0.5" );
    private final Slider edgeProbabilitySlider = new Slider( 0.0, 1.0, 0.5 );
    private final ComboBox < String > graphTypeDropdown = new ComboBox <>(
        FXCollections.observableArrayList(
            "DirectedGraph",
            "UndirectedGraph",
//            "DirectedBipartiteGraph",
            "UndirectedBipartiteGraph",
            "UndirectedWeighedGraph"
//            "DirectedWeighedGraph"
        )
    );

    public ConfigPanel () {
        super();

        this.setHgap( 50 );
        this.setAlignment( Pos.CENTER );

        Label vertexCountLabel = new Label( "Number of vertices:" );
        this.addColumn( 0, vertexCountLabel, this.vertexCountInput );

        Label generateRandomEdgesLabel = new Label( "Generate random edges?" );
        this.addColumn( 1, generateRandomEdgesLabel, this.randomisedEdgeCheckBox );
        GridPane.setHalignment( this.randomisedEdgeCheckBox, HPos.CENTER );

        this.addColumn( 2, this.edgeProababilityLabel, this.edgeProbabilitySlider );
        this.edgeProbabilitySlider.setDisable( true );

        Label graphTypeLabel = new Label( "Choose the graph type:" );
        this.addColumn( 3, graphTypeLabel, this.graphTypeDropdown );
        this.graphTypeDropdown.setValue( "" );

        Button generateGraphButton = new Button( "Generate" );
        this.add( generateGraphButton, 4, 1 );
        GridPane.setValignment( generateGraphButton, VPos.CENTER );

        this.enableInputVerticesFormatter();
        this.randomisedEdgeCheckBox.setOnAction(
            e -> this.edgeProbabilitySlider.setDisable( !this.randomisedEdgeCheckBox.isSelected() )
        );
        this.edgeProbabilitySlider.valueProperty().addListener(
            e -> {
                double value = this.edgeProbabilitySlider.getValue();
                this.edgeProababilityLabel.setText(
                    "Edge probability:" + new DecimalFormat( "#.##" ).format( value )
                );
            }
        );

        generateGraphButton.setOnAction(
            e -> this.generateNewGraph()
        );
    }

    private void enableInputVerticesFormatter () {
        UnaryOperator < TextFormatter.Change > integerFilter = change -> {
            String input = change.getControlNewText();
            if ( input.matches( "^[1-9]\\d*$|" ) ) {
                if ( !input.equals( "" ) && Integer.parseInt( input ) > 15 ) {
                    return null;
                }
                return change;
            }
            return null;
        };
        this.vertexCountInput.setTextFormatter( new TextFormatter <>( integerFilter ) );
    }


    private void generateNewGraph () {

        String engineTypeString = this.graphTypeDropdown.getValue().contains( "Weighed" ) ? "WeighedEdge" : "Edge";
        instantiateEngine( engineTypeString );

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
        this.engine.setAlgorithmPanel( new AlgorithmPanel( this.engine ) );
    }

    private void instantiateEngine ( String edgeClassType ) {
        try {
            Class < Edge < Integer > > edgeClass = ( Class < Edge < Integer > > ) Class.forName( "graph." + edgeClassType );
            this.engine = engineFactory.getEngine(
                edgeClass,
                ( DrawingPanel ) this.getParent().getChildrenUnmodifiable().stream()
                    .filter( e -> e.getClass() == DrawingPanel.class )
                    .findAny().get(),
                ( InfoPanel ) this.getParent().getChildrenUnmodifiable().stream()
                    .filter( e -> e.getClass() == InfoPanel.class )
                    .findAny().get()
            );

        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public Engine getEngine () {
        return engine;
    }
}
