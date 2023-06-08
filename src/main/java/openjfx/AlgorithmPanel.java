package openjfx;

import graph.DirectedGraph;
import graph.UndirectedGraph;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;
import java.util.HashMap;

public class AlgorithmPanel extends GridPane {

    private static < T > ComboBox < String > getAlgorithms ( Class < T > graphClass ) {
        String[] algorithmArray = new String[]{};
        if ( graphClass.equals( UndirectedGraph.class ) ) {
            algorithmArray = new String[] { "BFS", "DFS" };
        }
        if ( graphClass.equals( DirectedGraph.class ) ) {
            algorithmArray = new String[] { "BFS", "DFS" };
        }


        return new ComboBox <> (
            FXCollections.observableArrayList(
                algorithmArray
            )
        );
    }

    private final Engine engine;
    private final ComboBox < String > algorithmBox;

    public AlgorithmPanel ( Engine engine ) {
        super();
        this.setPadding( new Insets( 0, 75, 0, 0 ) );
        this.setVgap( 20 );
        this.setHgap( -80 );

        this.engine = engine;
        this.algorithmBox = getAlgorithms( engine.getGraph().getClass() );
        this.algorithmBox.setPrefWidth( 100 );

        Button loadAlgorithmButton = new Button("Load algorithm");
        loadAlgorithmButton.setOnMouseClicked( e -> this.engine.loadNewAlgorithm( this.algorithmBox.getValue() ) );
        this.addColumn( 0, new Label ( "Select algorithm" ), this.algorithmBox );
        this.addRow( 3, loadAlgorithmButton );

        ( ( BorderPane )engine.getDrawingPanel().getParent() ).setRight( this );
    }

    public void addInputBoxes ( String[] labels ) {

        TextField[] textFields = new TextField[labels.length];
        for ( int index = 0; index < labels.length; ++ index ) {
            textFields[index] = new TextField();
            textFields[index].setPrefWidth( 100 );
            this.addRow( 5 + 3 * index, new Label ( labels[index] ) );
            this.addRow ( 5 + 3 * index + 1, textFields[index] );
        }

        Button button = new Button ( "Run" );
        button.setOnMouseClicked( e -> {
            String[] parameters = new String[labels.length];
            for ( int index = 0; index < labels.length; ++ index ) {
                parameters[index] = textFields[index].textProperty().getValue();
            }
            this.engine.runAlgorithm( parameters );
        } );
        this.addRow( 5 + 3 * labels.length, button  );


        Button pause = new Button ( "Pause" );
        pause.setOnMouseClicked( e -> this.engine.pauseAlgorithm() );

        Button resume = new Button ( "Resume" );
        resume.setOnMouseClicked( e -> this.engine.resumeAlgorithm() );
        this.addRow( 8 + 3 * labels.length, pause, resume );

        Slider delaySlider = new Slider(0, 5000, 100);
        delaySlider.setValue( 2000 );
        Label delayLabel = new Label( "Delay:2 sec");
        delaySlider.valueProperty().addListener(
            e -> {
                double value = delaySlider.getValue();
                delayLabel.setText(
                    "Delay:" + new DecimalFormat( "#.##" ).format(value / 1000) + " sec"
                );
                engine.updateAlgorithmDelay( value );
            }
        );
        this.addRow(11 + 3 * labels.length, delayLabel);
        this.addRow(12 + 3 * labels.length, delaySlider);
    }
}
