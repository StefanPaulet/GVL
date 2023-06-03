package openjfx.graphDrawer;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.scene.paint.Color;
import openjfx.DrawingPanel;

import java.util.List;

public interface SimpleVerticesDrawer < VertexLabelType, EdgeType extends Edge < VertexLabelType >>
    extends VerticesDrawer < VertexLabelType, EdgeType > {

    @Override
    default void drawVertices ( Graph < VertexLabelType, EdgeType > graph, DrawingPanel drawingPanel ) {
        var vertexList = graph.getConstVertexList();
        var graphicsContext = drawingPanel.getGraphicsContext2D();
        graphicsContext.setFill( Color.BLACK );
        for ( int index = 0; index < vertexList.size(); ++ index ) {
            var point = new Point ( ( double ) index / vertexList.size(), MAIN_CIRCLE_RADIUS );
            graphicsContext.fillOval(
                point.x,
                point.y,
                VerticesDrawer.NODE_DIAMETER,
                VerticesDrawer.NODE_DIAMETER
            );

            var labelPoint = new Point ( ( double ) index / vertexList.size(), LABEL_CIRCLE_RADIUS );
            labelPoint.x += 5;
            labelPoint.y += 12.5;
            graphicsContext.strokeText(
                    vertexList.get(index).getLabel().toString(),
                labelPoint.x,
                labelPoint.y
            );
        }
    }
}
