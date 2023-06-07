package openjfx.graphDrawer;

import graph.Edge;
import javafx.scene.shape.Circle;


public interface UndirectedEdgeDrawer < VertexLabelType, EdgeType extends Edge< VertexLabelType > >
	extends EdgeDrawer < VertexLabelType, EdgeType > {

	@Override
	default EdgeShape computeEdgeDirectly ( Circle firstEnd, Circle secondEnd ) {
		return new LineShapedEdge( firstEnd, secondEnd );
	}
}
