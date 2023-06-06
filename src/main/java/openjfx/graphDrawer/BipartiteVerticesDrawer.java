package openjfx.graphDrawer;

import graph.BipartiteGraph;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import java.util.HashMap;
import java.util.Map;

public interface BipartiteVerticesDrawer < VertexLabelType, EdgeType extends Edge < VertexLabelType > >
    extends VerticesDrawer < VertexLabelType, EdgeType > {

    double VERTICAL_VERTEX_GAP = 75.0;
    double VERTEX_Y_START = 0;
    double RED_PARTITION_X = MAIN_CIRCLE_X - 150.0;
    double BLUE_PARTITION_X = MAIN_CIRCLE_X + 150.0;


    @Override
    default Map < Vertex < VertexLabelType, EdgeType >, Point > computeGraphPoints ( Graph < VertexLabelType, EdgeType > graph ) {
        if ( ! ( graph instanceof BipartiteGraph < VertexLabelType, EdgeType > bipartiteGraph ) ) {
            return null;
        }

        Map < Vertex < VertexLabelType, EdgeType >, Point > vertexPointMap = new HashMap <>();

        int redVertexIndex = 0;
        int blueVertexIndex = 0;

        var vertexColorings = bipartiteGraph.getVertexColorings();
        for ( var vertex : graph.getConstVertexList() ) {
            Point point;
            if ( vertexColorings.get( vertex ) == BipartiteGraph.VertexColoring.RED ) {
                point = new Point(
                    true,
                    RED_PARTITION_X,
                    VERTEX_Y_START + ( redVertexIndex++ ) * VERTICAL_VERTEX_GAP
                );
            } else {
                point = new Point(
                    true,
                    BLUE_PARTITION_X,
                    VERTEX_Y_START + ( blueVertexIndex++ ) * VERTICAL_VERTEX_GAP
                );
            }
            vertexPointMap.put( vertex, point );
        }

        return vertexPointMap;
    }
}