package openjfx.graphDrawer;

import graph.Edge;

public class DirectedBipartiteGraphDrawer < VertexLabelType, EdgeType extends Edge < VertexLabelType > >
    implements GraphDrawer < VertexLabelType, EdgeType >,
    BipartiteVerticesDrawer < VertexLabelType, EdgeType >,
    DirectedEdgeDrawer < VertexLabelType, EdgeType > {
}
