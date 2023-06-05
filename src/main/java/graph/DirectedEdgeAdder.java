package graph;

import java.util.List;
import java.util.function.Supplier;

public interface DirectedEdgeAdder < VertexLabelType, EdgeType extends Edge < VertexLabelType > >
    extends EdgeAdder < VertexLabelType, EdgeType > {

    @Override
    default void addEdgeNoexcept(
            Vertex < VertexLabelType, EdgeType > firstEnd,
            Vertex < VertexLabelType, EdgeType > secondEnd,
            Supplier < EdgeType > edgeSupplier
    ) throws AlreadyExistingEdgeException {
        EdgeType newFirstEndEdge = edgeSupplier.get();
        newFirstEndEdge.setEdgeEnd( ( Vertex < VertexLabelType, Edge < VertexLabelType > > ) secondEnd );
        if ( firstEnd.getEdgeList().contains(newFirstEndEdge) ) {
            throw new AlreadyExistingEdgeException(firstEnd, secondEnd);
        }
        firstEnd.getEdgeList().add(newFirstEndEdge);
    }
}