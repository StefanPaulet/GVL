package openjfx.graphDrawer;

import graph.Edge;
import graph.Vertex;
import javafx.scene.shape.Circle;

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
        graphDrawer.edgeShapesToEdgesMap.put( edgeShape, edge );
        graphDrawer.edgesToEdgeShapesMap.put( edge, edgeShape );

        return edge;
    }
}
