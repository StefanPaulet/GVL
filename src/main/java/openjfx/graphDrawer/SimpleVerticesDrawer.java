package openjfx.graphDrawer;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import openjfx.DrawingPanel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SimpleVerticesDrawer < VertexLabelType, EdgeType extends Edge < VertexLabelType >>
    extends VerticesDrawer < VertexLabelType, EdgeType > {

    double MAIN_CIRCLE_RADIUS = 250.0;
    double LABEL_CIRCLE_RADIUS = MAIN_CIRCLE_RADIUS + 20.0;

    @Override
    default Map < Circle, Vertex < VertexLabelType, EdgeType > > computeGraphPoints ( Graph < VertexLabelType, EdgeType > graph ) {

        Map < Circle, Vertex < VertexLabelType, EdgeType > > vertexPointMap = new HashMap <>();
        var vertexList = graph.getConstVertexList();
        for ( int index = 0; index < vertexList.size(); ++ index ) {

            var point = new Circle(
                VerticesDrawer.MAIN_CIRCLE_X + MAIN_CIRCLE_RADIUS * Math.cos( 2 * Math.PI * ( double ) index / vertexList.size() ),
                VerticesDrawer.MAIN_CIRCLE_Y + MAIN_CIRCLE_RADIUS * Math.sin( 2 * Math.PI * ( double ) index / vertexList.size() ),
                NODE_RADIUS
            );
            vertexPointMap.put( point, vertexList.get( index ) );
        }
        return vertexPointMap;
    }
}
