package graph;

import java.util.List;
import java.util.function.Supplier;

public interface EdgeAdder < VertexLabelType, EdgeType extends Edge < VertexLabelType > > {

    default void checkEdgeConstraint (
        List < Vertex < VertexLabelType, EdgeType > > vertexList,
        Vertex < VertexLabelType, EdgeType > firstEnd,
        Vertex < VertexLabelType, EdgeType > secondEnd
    ) throws NonExistingVertexException, LoopEdgeException {

        if ( ! vertexList.contains(firstEnd) ) {
            throw new NonExistingVertexException(firstEnd);
        }

        if ( ! vertexList.contains(secondEnd) ) {
            throw new NonExistingVertexException(secondEnd);
        }

        if ( firstEnd.equals(secondEnd) ) {
            throw new LoopEdgeException(firstEnd);
        }
    }

    default void addEdge (
        List < Vertex < VertexLabelType, EdgeType > > vertexList,
        Vertex < VertexLabelType, EdgeType > firstEnd,
        Vertex < VertexLabelType, EdgeType > secondEnd,
        Supplier < EdgeType > edgeSupplier
    ) throws NonExistingVertexException, LoopEdgeException, AlreadyExistingEdgeException, BipartiteEdgeAdditionException {
        this.checkEdgeConstraint(vertexList, firstEnd, secondEnd);
        this.addEdgeNoexcept(vertexList, firstEnd, secondEnd, edgeSupplier);
    };

    void addEdgeNoexcept(
            List < Vertex < VertexLabelType, EdgeType > > vertexList,
            Vertex < VertexLabelType, EdgeType > firstEnd,
            Vertex < VertexLabelType, EdgeType > secondEnd,
            Supplier < EdgeType > edgeSupplier
    ) throws AlreadyExistingEdgeException;
}
