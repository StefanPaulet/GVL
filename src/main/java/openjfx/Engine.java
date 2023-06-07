package openjfx;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import openjfx.graphDrawer.EdgeShape;
import openjfx.graphDrawer.GraphDrawer;
import openjfx.graphDrawer.Point;

import java.util.function.Function;

public class Engine {

    private Graph graph;
    private GraphDrawer graphDrawer;
    private DrawingPanel drawingPanel;

    private InfoPanel infoPanel;
    private final EngineState state = new EngineState();

    public void setDrawingPanel ( DrawingPanel drawingPanel ) {
        this.drawingPanel = drawingPanel;
    }

    public InfoPanel getInfoPanel () {
        return infoPanel;
    }

    public void setInfoPanel ( InfoPanel infoPanel ) {
        this.infoPanel = infoPanel;
    }
    public void instantiateGraph ( String graphType, int nodeCount, double edgeProbability ) {
        try {
            Class<Graph<?, ?>> graphClass = ( Class < Graph <?, ?> > ) Class.forName( "graph." + graphType );
            var graphConstructor = graphClass.getConstructor( int.class, Function.class );
            this.graph = graphConstructor.newInstance( nodeCount, ( Function < Integer, Integer > ) integer -> integer );
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        if ( edgeProbability != 0.0 ) {
            this.graph.fillWithRandomEdges( edgeProbability, () -> new Edge<>() );
        }

        try {
            Class<GraphDrawer<?,?>> graphDrawerClass = ( Class < GraphDrawer <?, ?> > ) Class.forName( "openjfx.graphDrawer." + graphType + "Drawer" );
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