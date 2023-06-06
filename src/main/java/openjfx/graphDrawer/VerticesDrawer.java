package openjfx.graphDrawer;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.scene.canvas.GraphicsContext;
import openjfx.DrawingPanel;

import java.util.Map;

import static openjfx.DrawingPanel.CANVAS_HEIGHT;
import static openjfx.DrawingPanel.CANVAS_WIDTH;

public interface VerticesDrawer < VertexLabelType, EdgeType extends Edge < VertexLabelType > > {
    double MAIN_CIRCLE_X = CANVAS_WIDTH / 2 - 100.0;
    double MAIN_CIRCLE_Y = CANVAS_HEIGHT / 2 - 25.0;
    double NODE_DIAMETER = 15.0;
    double NODE_RADIUS = NODE_DIAMETER / 2;

    default void drawVertices ( Point[] points, DrawingPanel drawingPanel ) {
        GraphicsContext graphicsContext = drawingPanel.getGraphicsContext2D();
        for ( Point point : points ) {
            graphicsContext.fillOval(
                point.x,
                point.y,
                NODE_DIAMETER,
                NODE_DIAMETER
            );
        }
    };

    Map < Vertex < VertexLabelType, EdgeType >, Point > computeGraphPoints ( Graph < VertexLabelType, EdgeType > graph );
}

class Point {
    double x;
    double y;

    public Point ( Point other ) {
        this.x = other.x;
        this.y = other.y;
    }

    public Point (
        boolean ignored,
        double x,
        double y
    ) {
        this.x = x;
        this.y = y;
    }

    public Point (
        double coefficient,
        double circleRadius
    ) {
        this.x = VerticesDrawer.MAIN_CIRCLE_X + circleRadius * Math.cos( 2 * Math.PI * coefficient);
        this.y = VerticesDrawer.MAIN_CIRCLE_Y + circleRadius * Math.sin( 2 * Math.PI * coefficient);
    }
}
