package openjfx.graphDrawer;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.scene.paint.Color;
import openjfx.DrawingPanel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SimpleVerticesDrawer < VertexLabelType, EdgeType extends Edge < VertexLabelType >>
    extends VerticesDrawer < VertexLabelType, EdgeType > {

    double MAIN_CIRCLE_RADIUS = 200.0;
    double LABEL_CIRCLE_RADIUS = 220.0;

    @Override
    default Map < Vertex < VertexLabelType, EdgeType >, Point > computeGraphPoints ( Graph < VertexLabelType, EdgeType > graph ) {

        Map < Vertex < VertexLabelType, EdgeType >, Point > vertexPointMap = new HashMap <>();
        var vertexList = graph.getConstVertexList();
        for ( int index = 0; index < vertexList.size(); ++ index ) {
            var point = new Point( ( double ) index / vertexList.size(), MAIN_CIRCLE_RADIUS );
            vertexPointMap.put( vertexList.get( index ), point );
        }
        return vertexPointMap;
    }
}
