package openjfx;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import openjfx.graphDrawer.GraphDrawer;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class Engine {

    private Graph graph;
    private GraphDrawer graphDrawer;
    private DrawingPanel drawingPanel;

    private InfoPanel infoPanel;

    public void setDrawingPanel ( DrawingPanel drawingPanel ) {
        this.drawingPanel = drawingPanel;
    }

    public InfoPanel getInfoPanel () {
        return infoPanel;
    }

    public void setInfoPanel ( InfoPanel infoPanel ) {
        this.infoPanel = infoPanel;
    }

    public void drawGraph () {
        this.graphDrawer.draw( this.drawingPanel );
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
            var graphDrawerConstructor = graphDrawerClass.getConstructor( Graph.class );
            this.graphDrawer = graphDrawerConstructor.newInstance( this.graph );
        } catch ( Exception e) {
            e.printStackTrace();
        }

        this.drawGraph();
    }
}
