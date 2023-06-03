package openjfx.graphDrawer;

import graph.Edge;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import openjfx.DrawingPanel;

import static openjfx.graphDrawer.VerticesDrawer.NODE_RADIUS;

public interface UndirectedEdgeDrawer < VertexLabelType, EdgeType extends Edge< VertexLabelType > >
	extends EdgeDrawer < VertexLabelType, EdgeType > {

	@Override
	default void drawEdge (
		Point firstEnd,
		Point secondEnd,
		DrawingPanel drawingPanel
	) {
		GraphicsContext graphicsContext = drawingPanel.getGraphicsContext2D ();

		double angle = Math.acos (
			Math.abs ( firstEnd.x - secondEnd.x ) /
				Math.sqrt (
					Math.pow ( firstEnd.x - secondEnd.x, 2 ) +
						Math.pow ( firstEnd.y - secondEnd.y, 2 )
				)
		);

		double xDirection = firstEnd.x < secondEnd.x ? -1.0 : 1.0;
		double yDirection = firstEnd.y < secondEnd.y ? -1.0 : 1.0;

		Point edgeArrowStart = new Point (
			true,
			firstEnd.x - NODE_RADIUS * Math.cos ( angle ) * xDirection,
			firstEnd.y - NODE_RADIUS * Math.sin ( angle ) * yDirection
		);
		Point edgeArrowEnd = new Point (
			true,
			secondEnd.x + NODE_RADIUS * Math.cos ( angle ) * xDirection,
			secondEnd.y + NODE_RADIUS * Math.sin ( angle ) * yDirection
		);

		graphicsContext.strokeLine(
			edgeArrowStart.x,
			edgeArrowStart.y,
			edgeArrowEnd.x,
			edgeArrowEnd.y
		);
	}
}
