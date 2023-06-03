package openjfx.graphDrawer;

import graph.Edge;
import graph.Graph;
import openjfx.DrawingPanel;

public interface GraphDrawer < VertexLabelType, EdgeType extends Edge < VertexLabelType > >
    extends EdgeDrawer < VertexLabelType, EdgeType > ,
    VerticesDrawer < VertexLabelType, EdgeType > {

    default void draw (
        Graph < VertexLabelType, EdgeType > graph,
        DrawingPanel drawingPanel
    ) {
        var nodeList = graph.getConstVertexList();
        this.drawVertices ( graph, drawingPanel );
        for ( var node : nodeList ) {
            Point firstPoint = new Point ( ( double ) nodeList.indexOf( node ) / ( double ) nodeList.size(), MAIN_CIRCLE_RADIUS );

            firstPoint.x += NODE_RADIUS;
            firstPoint.y += NODE_RADIUS;

            for ( var edge : node.getEdgeList() ) {
                var adjacentNode = edge.getEdgeEnd();
                Point secondPoint = new Point ( ( double ) nodeList.indexOf( adjacentNode ) / ( double ) nodeList.size(), MAIN_CIRCLE_RADIUS );

                secondPoint.x += NODE_RADIUS;
                secondPoint.y += NODE_RADIUS;

                this.drawEdge( firstPoint, secondPoint, drawingPanel );

            }
        }
    }
}
