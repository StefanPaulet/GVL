package openjfx.graphDrawer;

import graph.Edge;
import graph.Vertex;
import javafx.scene.shape.Circle;

public interface EdgeDrawer < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends Edge < VertexLabelType > > {
    default EdgeShape computeEdge (
        Circle firstEnd,
        Circle secondEnd
    ) {
        return this.computeEdgeDirectly( firstEnd, secondEnd );
    }

    EdgeShape computeEdgeDirectly ( Circle firstEnd, Circle secondEnd );

    EdgeType mapEdge (
        GraphDrawer < VertexLabelType, EdgeType > graphDrawer,
        Vertex < VertexLabelType, EdgeType > firstCircle,
        Vertex < VertexLabelType, EdgeType > secondCircle
    );
}