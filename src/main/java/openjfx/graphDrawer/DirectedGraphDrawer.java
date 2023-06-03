package openjfx.graphDrawer;

import graph.Edge;

public class DirectedGraphDrawer < VertexLabelType, EdgeType extends Edge < VertexLabelType > >
	implements GraphDrawer < VertexLabelType, EdgeType >,
	SimpleVerticesDrawer < VertexLabelType, EdgeType >,
	DirectedEdgeDrawer < VertexLabelType, EdgeType > {

}
