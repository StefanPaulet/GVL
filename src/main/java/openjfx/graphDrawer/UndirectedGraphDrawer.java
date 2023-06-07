package openjfx.graphDrawer;

import graph.Edge;
import graph.Graph;
import openjfx.Engine;

public class UndirectedGraphDrawer < VertexLabelType, EdgeType extends Edge < VertexLabelType > >
    extends GraphDrawer < VertexLabelType, EdgeType >
    implements SimpleVerticesDrawer < VertexLabelType, EdgeType >,
    UndirectedEdgeDrawer < VertexLabelType, EdgeType > {

    public UndirectedGraphDrawer ( Graph < VertexLabelType, EdgeType > graph, Engine engine ) {
        super( graph, engine );
    }
}
