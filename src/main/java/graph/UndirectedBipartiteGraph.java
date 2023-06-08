package graph;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class UndirectedBipartiteGraph < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends Edge < VertexLabelType > >
    extends BipartiteGraph < VertexLabelType, EdgeType >
    implements UndirectedEdgeAdder < VertexLabelType, EdgeType > {

    public UndirectedBipartiteGraph ( List < Vertex < VertexLabelType, EdgeType > > vertices ) throws NonBipartiteGraphException {
        super( vertices );
    }

    public UndirectedBipartiteGraph ( int nodeCount, Function < Integer, VertexLabelType > nodeLabelGenerator ) {
        super( nodeCount, nodeLabelGenerator );
    }

    @Override
    public void addEdge (
        Vertex < VertexLabelType, EdgeType > firstEnd,
        Vertex < VertexLabelType, EdgeType > secondEnd,
        Supplier < EdgeType > edgeSupplier
    ) throws NonExistingVertexException, LoopEdgeException, AlreadyExistingEdgeException, BipartiteEdgeAdditionException {
        super.checkEdgeConstraint( firstEnd, secondEnd );
        this.checkBipartitionOnEdgeAddition( firstEnd, secondEnd );
        UndirectedEdgeAdder.super.addEdgeNoexcept( firstEnd, secondEnd, edgeSupplier );
    }
}
