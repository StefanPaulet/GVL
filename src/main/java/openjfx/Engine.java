package openjfx;

import algorithm.Algorithm;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import openjfx.algorithmLoader.AlgorithmLoader;
import openjfx.graphDrawer.EdgeShape;
import openjfx.graphDrawer.GraphDrawer;

import java.util.Arrays;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Engine < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends Edge < VertexLabelType > > {



    class EngineState {
        Circle selectedCircle;
        EdgeShape selectedEdge;
        Algorithm < VertexLabelType, EdgeType > algorithm;

        AlgorithmLoader < VertexLabelType, EdgeType > algorithmLoader;
        boolean algorithmRunning = false;

        Thread algorithmThread;
    }


    private Graph < VertexLabelType, EdgeType > graph;
    private GraphDrawer < VertexLabelType, EdgeType > graphDrawer;
    private final DrawingPanel drawingPanel;

    private final InfoPanel infoPanel;

    private AlgorithmPanel algorithmPanel;
    private final EngineState state = new EngineState();


    public Engine ( DrawingPanel drawingPanel, InfoPanel infoPanel ) {
        this.drawingPanel = drawingPanel;
        this.infoPanel = infoPanel;
        drawingPanel.setEngine( this );
    }

    public void setAlgorithmPanel ( AlgorithmPanel algorithmPanel ) {
        this.algorithmPanel = algorithmPanel;
    }

    abstract public Supplier < EdgeType > edgeSupplier ();

    abstract protected String getGraphDrawerType();

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
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }


    public void handleVertexClick ( Circle clickedCircle ) {

        if ( this.state.algorithmThread != null ) {
            this.infoPanel.setSystemMessage( "You cannot edit the graph after starting an algorithm" );
            return;
        }

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

        if ( this.state.algorithmThread != null ) {
            this.infoPanel.setSystemMessage( "You cannot edit the graph after starting an algorithm" );
            return;
        }

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

    public void selectVertex ( Vertex < VertexLabelType, EdgeType > vertex ) {
        this.graphDrawer.selectVertex( vertex, Color.BLUE );
    }

    public void markFinalVertex ( Vertex < VertexLabelType, EdgeType > vertex ) {
        this.graphDrawer.selectVertex( vertex, Color.RED );
    }

    public void deselectVertx ( Vertex < VertexLabelType, EdgeType > vertex ) {
        this.graphDrawer.selectVertex( vertex, Color.BLACK );
    }


    public void selectEdge ( EdgeType edge ) {
        this.graphDrawer.selectEdge( edge, "blue" );
    }

    public void markFinalEdge ( EdgeType edge ) {
        this.graphDrawer.selectEdge( edge, "red" );
    }

    public void deselectEdge ( EdgeType edge ) {
        this.graphDrawer.deselectEdge ( edge );
    }

    public Graph < VertexLabelType, EdgeType > getGraph () {
        return graph;
    }

    public GraphDrawer < VertexLabelType, EdgeType > getGraphDrawer () {
        return graphDrawer;
    }

    public void loadNewAlgorithm ( String algorithmName ) {
        try {
            this.graphDrawer.draw();
            this.stopRunningAlgorithm();

            Class < Algorithm < VertexLabelType, EdgeType > > algorithmClass = ( Class < Algorithm < VertexLabelType, EdgeType > > ) Class.forName( "algorithm." + algorithmName );
            var algorithmConstructor = algorithmClass.getConstructor(Engine.class);
            this.state.algorithm = algorithmConstructor.newInstance( this );

            Class < AlgorithmLoader < VertexLabelType, EdgeType > > algorithmLoaderClass = ( Class < AlgorithmLoader < VertexLabelType, EdgeType > > ) Class.forName( "openjfx.algorithmLoader." + algorithmName + "Loader" );
            var algorithmLoaderConstructor = algorithmLoaderClass.getConstructor( Engine.class );
            this.state.algorithmLoader = algorithmLoaderConstructor.newInstance( this );

            this.algorithmPanel = new AlgorithmPanel( this );
            this.algorithmPanel.addInputBoxes( this.state.algorithmLoader.getNecessaryFields() );
        } catch ( Exception e ) {
            this.infoPanel.setSystemMessage( "You must select a valid algorithm" );
        }
    }

    public void stopRunningAlgorithm () {
        if ( this.state.algorithmThread != null ) {
            try { this.state.algorithm.pauseLock.unlock(); } catch ( Exception ignored ) {}
            this.state.algorithm.stopped = true;
            this.state.algorithmThread = null;
            this.state.algorithmRunning = false;
        }
    }


    public void runAlgorithm ( String[] parameters ) {
        if ( this.state.algorithmThread != null ) {
            this.infoPanel.setSystemMessage( "An algorithm has already been ran; load a new one" );
        }

        Object[] objParameters = this.state.algorithmLoader.convertFieldsFromString( parameters );
        if ( objParameters == null ) {
            return;
        }
        this.state.algorithm.setStartCondition( objParameters );

        this.state.algorithmThread = new Thread ( this.state.algorithm );
        this.state.algorithmThread.start();
        this.state.algorithmRunning = true;
    }


    public void pauseAlgorithm () {
        if ( this.state.algorithmThread == null ) {
            this.infoPanel.setSystemMessage( "The algorithm is not yet running" );
            return;
        }

        if ( this.state.algorithmThread.getState().equals( Thread.State.TERMINATED ) ) {
            this.infoPanel.setSystemMessage( "The algorithm is finished" );
            return;
        }

        if ( ! this.state.algorithmRunning ) {
            this.infoPanel.setSystemMessage( "The algorithm is already paused" );
            return;
        }

        this.infoPanel.setSystemMessage( "The algorithm is paused" );
        this.state.algorithm.pauseLock.lock();
        this.state.algorithmThread.interrupt();
        this.state.algorithmRunning = false;
    }


    public void resumeAlgorithm () {
        if ( this.state.algorithmThread == null ) {
            this.infoPanel.setSystemMessage( "The algorithm is not yet running" );
            return;
        }

        if ( this.state.algorithmThread.getState().equals( Thread.State.TERMINATED ) ) {
            this.infoPanel.setSystemMessage( "The algorithm is finished" );
            return;
        }

        if ( this.state.algorithmRunning ) {
            this.infoPanel.setSystemMessage( "The algorithm is already running" );
            return;
        }

        this.infoPanel.setSystemMessage( "The algorithm is now running" );
        this.state.algorithm.pauseLock.unlock();
        this.state.algorithmRunning = true;
    }

    public void updateAlgorithmDelay ( double value ) {
        this.state.algorithm.setDelayMillis( (int) (value) );
    }


    public EngineState getState () {
        return state;
    }
}