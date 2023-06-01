package openjfx.graphDrawer;

import graph.Edge;
import graph.Graph;
import openjfx.DrawingPanel;

import static openjfx.DrawingPanel.CANVAS_HEIGHT;
import static openjfx.DrawingPanel.CANVAS_WIDTH;

public interface GraphDrawer <
        NodeLabelType,
        EdgeType extends Edge< NodeLabelType >
> {
    double MAIN_CIRCLE_X = CANVAS_WIDTH / 2;
    double MAIN_CIRCLE_Y = CANVAS_HEIGHT / 2 - 25.0;
    double NODE_RADIUS = 15.0;
    double MAIN_CIRCLE_RADIUS = 200.0;

    void draw ( Graph < NodeLabelType, EdgeType > graph, DrawingPanel drawingPanel );
}

class Point {
    double x;
    double y;
    public Point(double coefficient) {
        this.x = GraphDrawer.MAIN_CIRCLE_X + GraphDrawer.MAIN_CIRCLE_RADIUS * Math.cos( 2 * Math.PI * coefficient);
        this.y = GraphDrawer.MAIN_CIRCLE_Y + GraphDrawer.MAIN_CIRCLE_RADIUS * Math.sin( 2 * Math.PI * coefficient);
    }
}
