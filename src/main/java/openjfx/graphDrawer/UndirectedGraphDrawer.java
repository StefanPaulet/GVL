package openjfx.graphDrawer;

import graph.Edge;

public class UndirectedGraphDrawer < VertexLabelType, EdgeType extends Edge < VertexLabelType > >
    implements GraphDrawer < VertexLabelType, EdgeType >,
    SimpleVerticesDrawer < VertexLabelType, EdgeType >,
    UndirectedEdgeDrawer < VertexLabelType, EdgeType > {

}
