package openjfx.graphDrawer;

import graph.Edge;
import graph.Graph;
import openjfx.DrawingPanel;

import static openjfx.DrawingPanel.CANVAS_HEIGHT;
import static openjfx.DrawingPanel.CANVAS_WIDTH;

public interface VerticesDrawer < VertexLabelType, EdgeType extends Edge < VertexLabelType > > {
    double MAIN_CIRCLE_X = CANVAS_WIDTH / 2;
    double MAIN_CIRCLE_Y = CANVAS_HEIGHT / 2 - 25.0;
    double NODE_DIAMETER = 15.0;
    double NODE_RADIUS = NODE_DIAMETER / 2;
    double MAIN_CIRCLE_RADIUS = 200.0;
    double LABEL_CIRCLE_RADIUS = 220.0;

    void drawVertices ( Graph < VertexLabelType, EdgeType > graph, DrawingPanel drawingPanel );
}

class Point {
    double x;
    double y;

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
