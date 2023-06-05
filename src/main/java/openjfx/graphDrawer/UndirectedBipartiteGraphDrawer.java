package openjfx.graphDrawer;

import graph.Edge;
import graph.Graph;

public class UndirectedBipartiteGraphDrawer < VertexLabelType, EdgeType extends Edge < VertexLabelType > >
    extends GraphDrawer < VertexLabelType, EdgeType >
    implements BipartiteVerticesDrawer < VertexLabelType, EdgeType >,
    UndirectedEdgeDrawer < VertexLabelType, EdgeType > {

    public UndirectedBipartiteGraphDrawer ( Graph < VertexLabelType, EdgeType > graph ) {
        super( graph );
    }
}
