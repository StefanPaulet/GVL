package openjfx.graphDrawer;

import graph.Graph;
import graph.WeighedEdge;
import openjfx.Engine;

public class UndirectedWeighedGraphDrawer < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends WeighedEdge < VertexLabelType > >
    extends WeighedGraphDrawer < VertexLabelType, EdgeType >
    implements SimpleVerticesDrawer < VertexLabelType, EdgeType >,
    UndirectedEdgeDrawer < VertexLabelType, EdgeType > {

    public UndirectedWeighedGraphDrawer ( Graph < VertexLabelType, EdgeType > graph, Engine < VertexLabelType, EdgeType > engine ) {
        super( graph, engine );
    }
}
