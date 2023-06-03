package openjfx.graphDrawer;

import graph.Edge;
import openjfx.DrawingPanel;

public interface EdgeDrawer < VertexLabelType, EdgeType extends Edge< VertexLabelType > > {
	void drawEdge(
		Point firstEnd,
		Point secondEnd,
		DrawingPanel drawingPanel
	);


}