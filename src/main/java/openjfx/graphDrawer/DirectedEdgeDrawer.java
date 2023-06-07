package openjfx.graphDrawer;

import graph.Edge;
import javafx.event.EventHandler;
import javafx.scene.shape.Circle;

public interface DirectedEdgeDrawer < VertexLabelType, EdgeType extends Edge< VertexLabelType > >
	extends EdgeDrawer < VertexLabelType, EdgeType > {

	@Override
	default EdgeShape computeEdgeDirectly ( Circle firstEnd, Circle secondEnd ) {
		return new ArrowShapedEdge( firstEnd, secondEnd );
	}
}
