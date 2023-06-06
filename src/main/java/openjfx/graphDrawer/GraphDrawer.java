package openjfx.graphDrawer;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.scene.paint.Color;
import openjfx.DrawingPanel;

import java.util.Map;

import static openjfx.App.WINDOW_HEIGHT;
import static openjfx.App.WINDOW_WIDTH;

public abstract class GraphDrawer < VertexLabelType, EdgeType extends Edge < VertexLabelType > >
    implements EdgeDrawer < VertexLabelType, EdgeType > ,
    VerticesDrawer < VertexLabelType, EdgeType > {

    private final Map < Vertex < VertexLabelType, EdgeType >, Point > graphPoints;
    private final Graph < VertexLabelType, EdgeType > graph;

    public GraphDrawer ( Graph < VertexLabelType, EdgeType > graph ) {
        this.graphPoints = this.computeGraphPoints( graph );
        this.graph = graph;
    }

    public void draw ( DrawingPanel drawingPanel ) {

        drawingPanel.getGraphicsContext2D().clearRect( 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT );

        this.drawVertices ( graphPoints.values().toArray( new Point[ 0 ] ), drawingPanel );
        for ( var node : this.graph.getConstVertexList() ) {
            Point firstPoint = new Point(graphPoints.get( node ));

            firstPoint.x += NODE_RADIUS;
            firstPoint.y += NODE_RADIUS;

            for ( var edge : node.getEdgeList() ) {
                Point secondPoint = new Point(graphPoints.get( edge.getEdgeEnd() ));

                secondPoint.x += NODE_RADIUS;
                secondPoint.y += NODE_RADIUS;

                this.drawEdge( firstPoint, secondPoint, drawingPanel );

            }
        }
    }
}
