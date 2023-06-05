package graph;

import java.util.List;
import java.util.function.Supplier;

public interface EdgeAdder < VertexLabelType, EdgeType extends Edge < VertexLabelType > > {

    default void addEdge (
        Vertex < VertexLabelType, EdgeType > firstEnd,
        Vertex < VertexLabelType, EdgeType > secondEnd,
        Supplier < EdgeType > edgeSupplier
    ) throws NonExistingVertexException, LoopEdgeException, AlreadyExistingEdgeException, BipartiteEdgeAdditionException {
        this.addEdgeNoexcept(firstEnd, secondEnd, edgeSupplier);
    };

    void addEdgeNoexcept(
        Vertex < VertexLabelType, EdgeType > firstEnd,
        Vertex < VertexLabelType, EdgeType > secondEnd,
        Supplier < EdgeType > edgeSupplier
    ) throws AlreadyExistingEdgeException;
}
