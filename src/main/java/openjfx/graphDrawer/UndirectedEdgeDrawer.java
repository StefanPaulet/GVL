package openjfx.graphDrawer;

import graph.Edge;
import graph.Vertex;
import javafx.scene.shape.Circle;


public interface UndirectedEdgeDrawer < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends Edge < VertexLabelType > >
    extends EdgeDrawer < VertexLabelType, EdgeType > {

    @Override
    default EdgeShape computeEdgeDirectly ( Circle firstEnd, Circle secondEnd ) {
        return new LineShapedEdge( firstEnd, secondEnd );
    }

    @Override
    default EdgeType mapEdge (
        GraphDrawer < VertexLabelType, EdgeType > graphDrawer,
        Vertex < VertexLabelType, EdgeType > firstVertex,
        Vertex < VertexLabelType, EdgeType > secondVertex
    ) {

        Circle firstCircle = graphDrawer.verticesToCirclesMap.get( firstVertex );
        Circle secondCircle = graphDrawer.verticesToCirclesMap.get( secondVertex );
        EdgeType firstEdge = graphDrawer.graph.findEdge( firstVertex, secondVertex );
        EdgeType secondEdge = graphDrawer.graph.findEdge( secondVertex, firstVertex );
        EdgeShape edgeShape = this.computeEdge( firstCircle, secondCircle );
        graphDrawer.edgeShapesToEdgesMap.put( edgeShape, firstEdge );
        graphDrawer.edgesToEdgeShapesMap.put( firstEdge, edgeShape );
        graphDrawer.edgesToEdgeShapesMap.put( secondEdge, edgeShape );

        return firstEdge;
    }
}
