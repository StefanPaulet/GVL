package openjfx.graphDrawer;

import graph.BipartiteGraph;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.Map;

public interface BipartiteVerticesDrawer < VertexLabelType, EdgeType extends Edge < VertexLabelType > >
    extends VerticesDrawer < VertexLabelType, EdgeType > {

    double VERTICAL_VERTEX_GAP = 75.0;
    double VERTEX_Y_START = 0;
    double RED_PARTITION_X = MAIN_CIRCLE_X - 150.0;
    double BLUE_PARTITION_X = MAIN_CIRCLE_X + 150.0;


    @Override
    default Map < Circle, Vertex < VertexLabelType, EdgeType > > computeGraphPoints ( Graph < VertexLabelType, EdgeType > graph ) {
        if ( ! ( graph instanceof BipartiteGraph < VertexLabelType, EdgeType > bipartiteGraph ) ) {
            return null;
        }

        Map < Circle, Vertex < VertexLabelType, EdgeType > > vertexPointMap = new HashMap <>();

        int redVertexIndex = 0;
        int blueVertexIndex = 0;

        var vertexColorings = bipartiteGraph.getVertexColorings();
        for ( var vertex : graph.getConstVertexList() ) {
            Circle circle;
            if ( vertexColorings.get( vertex ) == BipartiteGraph.VertexColoring.RED ) {
                circle = new Circle(
                    RED_PARTITION_X,
                    VERTEX_Y_START + ( redVertexIndex++ ) * VERTICAL_VERTEX_GAP,
                    NODE_RADIUS
                );
            } else {
                circle = new Circle(
                    BLUE_PARTITION_X,
                    VERTEX_Y_START + ( blueVertexIndex++ ) * VERTICAL_VERTEX_GAP,
                    NODE_RADIUS
                );
            }
            vertexPointMap.put( circle, vertex );
        }

        return vertexPointMap;
    }
}