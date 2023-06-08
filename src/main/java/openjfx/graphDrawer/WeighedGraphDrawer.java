package openjfx.graphDrawer;
import graph.Graph;
import graph.WeighedEdge;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.shape.Circle;
import openjfx.Engine;

public abstract class WeighedGraphDrawer < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends WeighedEdge < VertexLabelType > >
    extends GraphDrawer < VertexLabelType, EdgeType > {

    public WeighedGraphDrawer ( Graph < VertexLabelType, EdgeType > graph, Engine < VertexLabelType, EdgeType > engine ) {
        super( graph, engine );
    }


    @Override
    protected void computeAllEdges () {
        super.computeAllEdges();
        for ( var entry : this.edgeShapesToEdgesMap.entrySet() ) {
            entry.getKey().addLabel( String.valueOf(entry.getValue().getWeight()), ( ChangeListener < String > ) ( observableValue, s, t1 ) -> {
                this.modifyEdgeLabel( entry.getKey(), s );
            } );
        }
    }

    @Override
    public void addEdge ( Circle firstCircle, Circle secondCircle ) {
        var firstVertex = this.circlesToVerticesMap.get( firstCircle );
        var secondVertex = this.circlesToVerticesMap.get( secondCircle );
        try {
            this.graph.addEdge(
                firstVertex,
                secondVertex,
                this.engine.edgeSupplier()
            );
            EdgeType edge = this.mapEdge( this, firstVertex, secondVertex );

            try {
                EdgeShape edgeShape = this.edgesToEdgeShapesMap.get( edge );
                edgeShape.addLabel( String.valueOf( edge.getWeight() ), (observableValue, oldValue, newValue) -> this.modifyEdgeLabel( edgeShape, newValue ) );
                this.engine.getDrawingPanel().getChildren().add( edgeShape );
            } catch ( Exception ignored ) { ignored.printStackTrace();}


        } catch ( Exception e ) {
            this.engine.getInfoPanel().setSystemMessage( e.getMessage() );
        }
    }

    public void modifyEdgeLabel ( EdgeShape edgeShape, String value ) {
        try {
            this.edgeShapesToEdgesMap.get( edgeShape ).setWeight( Integer.parseInt( value ) );
        } catch ( Exception ignored ) {}
    }
}
