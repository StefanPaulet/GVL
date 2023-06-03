package openjfx.graphDrawer;

import graph.Edge;

public class UndirectedBipartiteGraphDrawer < VertexLabelType, EdgeType extends Edge < VertexLabelType > >
    implements GraphDrawer < VertexLabelType, EdgeType >,
    BipartiteVerticesDrawer < VertexLabelType, EdgeType >,
    UndirectedEdgeDrawer < VertexLabelType, EdgeType > {
}
