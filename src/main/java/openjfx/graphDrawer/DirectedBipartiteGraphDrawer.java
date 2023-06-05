package openjfx.graphDrawer;

import graph.Edge;
import graph.Graph;

public class DirectedBipartiteGraphDrawer < VertexLabelType, EdgeType extends Edge < VertexLabelType > >
    extends GraphDrawer < VertexLabelType, EdgeType >
    implements BipartiteVerticesDrawer < VertexLabelType, EdgeType >,
    DirectedEdgeDrawer < VertexLabelType, EdgeType > {

    public DirectedBipartiteGraphDrawer ( Graph < VertexLabelType, EdgeType > graph ) {
        super( graph );
    }
}
