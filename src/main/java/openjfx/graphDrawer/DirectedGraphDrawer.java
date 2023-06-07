package openjfx.graphDrawer;

import graph.Edge;
import graph.Graph;
import openjfx.Engine;

public class DirectedGraphDrawer < VertexLabelType, EdgeType extends Edge < VertexLabelType > >
	extends GraphDrawer < VertexLabelType, EdgeType >
	implements SimpleVerticesDrawer < VertexLabelType, EdgeType >,
	DirectedEdgeDrawer < VertexLabelType, EdgeType > {

	public DirectedGraphDrawer ( Graph < VertexLabelType, EdgeType > graph, Engine engine ) {
		super( graph, engine );
	}
}
