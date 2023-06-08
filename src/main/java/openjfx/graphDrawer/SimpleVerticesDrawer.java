package openjfx.graphDrawer;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SimpleVerticesDrawer < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends Edge < VertexLabelType > >
    extends VerticesDrawer < VertexLabelType, EdgeType > {

    double MAIN_CIRCLE_RADIUS = 250.0;
    double LABEL_CIRCLE_RADIUS = MAIN_CIRCLE_RADIUS + 30.0;

    @Override
    default Map < Circle, Vertex < VertexLabelType, EdgeType > > computeGraphPoints ( Graph < VertexLabelType, EdgeType > graph ) {

        Map < Circle, Vertex < VertexLabelType, EdgeType > > vertexPointMap = new HashMap <>();
        var vertexList = graph.getConstVertexList();
        for ( int index = 0; index < vertexList.size(); ++index ) {

            var point = new Circle(
                VerticesDrawer.MAIN_CIRCLE_X + MAIN_CIRCLE_RADIUS * Math.cos( 2 * Math.PI * ( double ) index / vertexList.size() ),
                VerticesDrawer.MAIN_CIRCLE_Y + MAIN_CIRCLE_RADIUS * Math.sin( 2 * Math.PI * ( double ) index / vertexList.size() ),
                NODE_RADIUS
            );
            vertexPointMap.put( point, vertexList.get( index ) );
        }
        return vertexPointMap;
    }

    @Override
    default List < Node > computeVertexLabels ( Graph < VertexLabelType, EdgeType > graph ) {
        List < Node > labelList = new ArrayList <>();
        var vertexList = graph.getConstVertexList();
        for ( int index = 0; index < vertexList.size(); ++index ) {

            var label = new Text(
                VerticesDrawer.MAIN_CIRCLE_X + LABEL_CIRCLE_RADIUS * Math.cos( 2 * Math.PI * ( double ) index / vertexList.size() ),
                VerticesDrawer.MAIN_CIRCLE_Y + LABEL_CIRCLE_RADIUS * Math.sin( 2 * Math.PI * ( double ) index / vertexList.size() ),
                vertexList.get( index ).getLabel().toString()
            );
            labelList.add( label );
        }

        return labelList;
    }
}
