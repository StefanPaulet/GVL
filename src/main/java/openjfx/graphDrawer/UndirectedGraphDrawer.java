package openjfx.graphDrawer;

import graph.Edge;
import graph.Graph;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import openjfx.DrawingPanel;

public class UndirectedGraphDrawer<
        NodeLabelType,
        EdgeType extends Edge< NodeLabelType >
> implements GraphDrawer<
    NodeLabelType,
    EdgeType
> {

    @Override
    public void draw ( Graph < NodeLabelType, EdgeType > graph, DrawingPanel drawingPanel ) {
        var graphicsContext = drawingPanel.getGraphicsContext2D();
        var nodeList = graph.getVertexList();
        graphicsContext.setFill( Color.BLACK );
        for ( int index = 0; index < nodeList.size(); ++index ) {
            var point = new Point( ( double ) index / nodeList.size() );
            graphicsContext.fillOval(
                point.x,
                point.y,
                GraphDrawer.NODE_RADIUS,
                GraphDrawer.NODE_RADIUS
            );
        }

        for ( var node : nodeList ) {
            Point firstPoint = new Point ( ( double ) nodeList.indexOf( node ) / nodeList.size() );
            for ( var edge : node.getEdgeList() ) {
                var adjacentNode = edge.getEdgeEnd();
                Point secondPoint = new Point ( ( double ) nodeList.indexOf( adjacentNode ) / nodeList.size() );
                graphicsContext.strokeLine(
                        firstPoint.x + GraphDrawer.NODE_RADIUS / 2,
                        firstPoint.y + GraphDrawer.NODE_RADIUS / 2,
                        secondPoint.x + GraphDrawer.NODE_RADIUS / 2,
                        secondPoint.y + GraphDrawer.NODE_RADIUS / 2
                );
            }
        }
    }
}
