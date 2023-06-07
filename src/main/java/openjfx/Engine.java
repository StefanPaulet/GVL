package openjfx;

import graph.Edge;
import graph.Graph;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import openjfx.graphDrawer.EdgeShape;
import openjfx.graphDrawer.GraphDrawer;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Engine < VertexLabelType, EdgeType extends Edge < VertexLabelType > > {

    private Graph < VertexLabelType, EdgeType > graph;
    private GraphDrawer < VertexLabelType, EdgeType > graphDrawer;
    private final DrawingPanel drawingPanel;

    private final InfoPanel infoPanel;
    private final EngineState state = new EngineState();


    public Engine ( DrawingPanel drawingPanel, InfoPanel infoPanel ) {
        this.drawingPanel = drawingPanel;
        this.infoPanel = infoPanel;
        drawingPanel.setEngine( this );
    }

    abstract public Supplier < EdgeType > edgeSupplier();

    public InfoPanel getInfoPanel () {
        return infoPanel;
    }
    public void instantiateGraph ( String graphType, int nodeCount, double edgeProbability ) {
        try {
            Class < Graph < VertexLabelType, EdgeType > > graphClass = ( Class < Graph < VertexLabelType, EdgeType > > ) Class.forName( "graph." + graphType );
            var graphConstructor = graphClass.getConstructor( int.class, Function.class );
            this.graph = graphConstructor.newInstance( nodeCount, ( Function < Integer, Integer > ) integer -> integer );
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        if ( edgeProbability != 0.0 ) {
            this.graph.fillWithRandomEdges( edgeProbability, this.edgeSupplier() );
        }

        try {
            Class < GraphDrawer < VertexLabelType, EdgeType > > graphDrawerClass = ( Class < GraphDrawer < VertexLabelType, EdgeType > > ) Class.forName( "openjfx.graphDrawer." + graphType + "Drawer" );
            var graphDrawerConstructor = graphDrawerClass.getConstructor( Graph.class, Engine.class );
            this.graphDrawer = graphDrawerConstructor.newInstance( this.graph, this );
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }


    public void handleVertexClick ( Circle clickedCircle ) {

        if ( this.state.selectedEdge != null ) {
            return;
        }

        if ( this.state.selectedCircle == null ) {
            this.state.selectedCircle = clickedCircle;
            clickedCircle.setFill( Color.RED );
        } else {
            this.graphDrawer.addEdge( this.state.selectedCircle, clickedCircle );
            this.state.selectedCircle.setFill( Color.BLACK );
            this.state.selectedCircle = null;
        }
    }

    public void handleEdgeClick ( EdgeShape edgeClicked ) {

        if ( this.state.selectedCircle != null ) {
            return;
        }

        if ( this.state.selectedEdge == null ) {
            if ( edgeClicked != null ) {
                edgeClicked.select();
                this.state.selectedEdge = edgeClicked;
            }
        } else {
            if ( edgeClicked.equals( state.selectedEdge ) ) {
                edgeClicked.deselect();
                this.state.selectedEdge = null;
            } else {
                this.state.selectedEdge.deselect();
                edgeClicked.select();
                this.state.selectedEdge = edgeClicked;
            }
        }
    }

    public void handleDeleteKey () {
        if ( this.state.selectedEdge != null ) {
            this.graphDrawer.removeEdge( this.state.selectedEdge );
            this.drawingPanel.getChildren().remove( this.state.selectedEdge );
            this.state.selectedEdge = null;
        }
    }

    public DrawingPanel getDrawingPanel () {
        return drawingPanel;
    }
}


class EngineState {
    Circle selectedCircle;
    EdgeShape selectedEdge;
    boolean algorithmRunning = false;
}