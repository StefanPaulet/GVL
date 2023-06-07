package openjfx.graphDrawer;

import graph.Edge;
import javafx.event.EventHandler;
import javafx.scene.shape.Circle;
import openjfx.DrawingPanel;

public interface EdgeDrawer < VertexLabelType, EdgeType extends Edge< VertexLabelType > > {
	default EdgeShape computeEdge(
		Circle firstEnd,
		Circle secondEnd
	) { return this.computeEdgeDirectly(firstEnd, secondEnd); }

	EdgeShape computeEdgeDirectly(Circle firstEnd, Circle secondEnd);


}