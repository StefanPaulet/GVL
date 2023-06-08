package graph;

import java.util.List;
import java.util.function.Function;

public class DirectedWeighedGraph < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends WeighedEdge < VertexLabelType > >
    extends WeighedGraph < VertexLabelType, EdgeType >
    implements DirectedEdgeAdder < VertexLabelType, EdgeType > {

    public DirectedWeighedGraph ( List < Vertex < VertexLabelType, EdgeType > > vertices ) {
        super( vertices );
    }


    public DirectedWeighedGraph ( int nodeCount, Function < Integer, VertexLabelType > nodeLabelGenerator ) {
        super( nodeCount, nodeLabelGenerator );
    }
}
