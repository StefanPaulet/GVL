package openjfx.graphDrawer;

import graph.Graph;
import graph.WeighedEdge;
import openjfx.Engine;

public class DirectedWeighedGraphDrawer < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends WeighedEdge < VertexLabelType > >
    extends WeighedGraphDrawer < VertexLabelType, EdgeType >
    implements SimpleVerticesDrawer < VertexLabelType, EdgeType >,
    DirectedEdgeDrawer < VertexLabelType, EdgeType > {

    public DirectedWeighedGraphDrawer ( Graph < VertexLabelType, EdgeType > graph, Engine < VertexLabelType, EdgeType > engine ) {
        super( graph, engine );
    }
}
