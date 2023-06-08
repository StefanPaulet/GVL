package openjfx.graphDrawer;

import graph.Edge;
import graph.Vertex;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public interface DirectedEdgeDrawer < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends Edge < VertexLabelType > >
    extends EdgeDrawer < VertexLabelType, EdgeType > {

    @Override
    default EdgeShape computeEdgeDirectly ( Circle firstEnd, Circle secondEnd ) {
        return new ArrowShapedEdge( firstEnd, secondEnd );
    }

    @Override
    default EdgeType mapEdge (
        GraphDrawer < VertexLabelType, EdgeType > graphDrawer,
        Vertex < VertexLabelType, EdgeType > firstVertex,
        Vertex < VertexLabelType, EdgeType > secondVertex
    ) {

        Circle firstCircle = graphDrawer.verticesToCirclesMap.get( firstVertex );
        Circle secondCircle = graphDrawer.verticesToCirclesMap.get( secondVertex );
        EdgeType edge = graphDrawer.graph.findEdge( firstVertex, secondVertex );
        EdgeShape edgeShape = this.computeEdge( firstCircle, secondCircle );

        List < EdgeType > edgeTypeList = new ArrayList <>();
        edgeTypeList.add( edge );

        graphDrawer.edgeShapesToEdgesMap.put( edgeShape, edgeTypeList );
        graphDrawer.edgesToEdgeShapesMap.put( edge, edgeShape );

        return edge;
    }
}
