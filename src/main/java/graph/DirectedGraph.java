package graph;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class DirectedGraph < VertexLabelType, EdgeType extends Edge < VertexLabelType > >
        extends Graph < VertexLabelType, EdgeType >
        implements DirectedEdgeAdder < VertexLabelType, EdgeType > {

    public DirectedGraph ( List< Vertex < VertexLabelType, EdgeType > > vertices ) {
        super( vertices );
    }


    public DirectedGraph ( int nodeCount, Function< Integer, VertexLabelType > nodeLabelGenerator ) {
        super( nodeCount, nodeLabelGenerator );
    }
}
