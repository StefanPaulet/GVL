package openjfx.graphDrawer;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.scene.shape.Circle;

import java.util.Map;

import static openjfx.DrawingPanel.CANVAS_HEIGHT;
import static openjfx.DrawingPanel.CANVAS_WIDTH;

public interface VerticesDrawer < VertexLabelType, EdgeType extends Edge < VertexLabelType > > {
    double MAIN_CIRCLE_X = CANVAS_WIDTH / 2 - 100.0;
    double MAIN_CIRCLE_Y = CANVAS_HEIGHT / 2 - 25.0;
    double NODE_RADIUS = 10;

    Map < Circle,  Vertex < VertexLabelType, EdgeType > > computeGraphPoints ( Graph < VertexLabelType, EdgeType > graph );
}

