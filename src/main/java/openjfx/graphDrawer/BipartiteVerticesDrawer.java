package openjfx.graphDrawer;

import graph.BipartiteGraph;
import graph.Edge;
import graph.Graph;
import javafx.scene.canvas.GraphicsContext;
import openjfx.DrawingPanel;

public interface BipartiteVerticesDrawer < VertexLabelType, EdgeType extends Edge < VertexLabelType > >
    extends VerticesDrawer < VertexLabelType, EdgeType > {

    double VERTICAL_VERTEX_GAP = 50.0;
    double VERTEX_Y_START = 50.0;
    double RED_PARTITION_X = 50.0;
    double BLUE_PARTITION_X = 450.0;

    @Override
    default void drawVertices ( Graph < VertexLabelType, EdgeType > graph, DrawingPanel drawingPanel ) {
        if ( !( graph instanceof BipartiteGraph < VertexLabelType, EdgeType > bipartiteGraph ) ) {
            return;
        }
        GraphicsContext graphicsContext = drawingPanel.getGraphicsContext2D();

        int redVertexIndex = 0;
        int blueVertexIndex = 0;

        var vertexColorings = bipartiteGraph.getVertexColorings();

        for ( var vertex : graph.getConstVertexList() ) {
            Point point;
            if ( vertexColorings.get( vertex ) == BipartiteGraph.VertexColoring.RED ) {
                point = new Point(
                    true,
                    RED_PARTITION_X,
                    VERTEX_Y_START + (redVertexIndex ++) * VERTICAL_VERTEX_GAP
                );
            } else {
                point = new Point(
                    true,
                    BLUE_PARTITION_X,
                    VERTEX_Y_START + (blueVertexIndex ++) * VERTICAL_VERTEX_GAP
                );
            }
            graphicsContext.fillOval(
                point.x,
                point.y,
                VerticesDrawer.NODE_DIAMETER,
                VerticesDrawer.NODE_DIAMETER
            );
        }
    }
}
