package graph;

import java.util.List;
import java.util.function.Function;

public class DirectedBipartiteGraph < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends Edge < VertexLabelType > >
    extends BipartiteGraph < VertexLabelType, EdgeType >
    implements DirectedEdgeAdder < VertexLabelType, EdgeType > {

    public DirectedBipartiteGraph ( List < Vertex < VertexLabelType, EdgeType > > vertices ) throws NonBipartiteGraphException {
        super( vertices );
    }

    public DirectedBipartiteGraph ( int nodeCount, Function < Integer, VertexLabelType > nodeLabelGenerator ) {
        super( nodeCount, nodeLabelGenerator );
    }
}
