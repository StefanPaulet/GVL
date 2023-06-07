package graph;

import java.util.List;
import java.util.function.Supplier;

public interface UndirectedEdgeAdder < VertexLabelType, EdgeType extends Edge < VertexLabelType > >
    extends EdgeAdder < VertexLabelType, EdgeType > {

    @Override
    default void addEdgeNoexcept(
            Vertex < VertexLabelType, EdgeType > firstEnd,
            Vertex < VertexLabelType, EdgeType > secondEnd,
            Supplier < EdgeType > edgeSupplier
    ) throws AlreadyExistingEdgeException {

        EdgeType newFirstEndEdge = edgeSupplier.get();
        newFirstEndEdge.setEdgeEnd( ( Vertex < VertexLabelType, Edge < VertexLabelType > > ) secondEnd );
        newFirstEndEdge.setOwnerVertex( ( Vertex < VertexLabelType, Edge < VertexLabelType > > ) firstEnd );
        if ( firstEnd.getEdgeList().contains(newFirstEndEdge) ) {
            throw new AlreadyExistingEdgeException(firstEnd, secondEnd);
        }
        firstEnd.getEdgeList().add(newFirstEndEdge);

        EdgeType newSecondEndEdge = edgeSupplier.get();
        newSecondEndEdge.setEdgeEnd( ( Vertex < VertexLabelType, Edge < VertexLabelType > > ) firstEnd );
        newSecondEndEdge.setOwnerVertex( ( Vertex < VertexLabelType, Edge < VertexLabelType > > ) secondEnd );
        if ( secondEnd.getEdgeList().contains(newSecondEndEdge) ) {
            throw new AlreadyExistingEdgeException(secondEnd, firstEnd);
        }
        secondEnd.getEdgeList().add(newSecondEndEdge);
    }

    @Override
    default void removeEdgeNoexcept ( Vertex < VertexLabelType, EdgeType > firstEnd, EdgeType edge ) {
        firstEnd.getEdgeList().remove( edge );
        edge.getEdgeEnd().getEdgeList().remove (
            edge.getEdgeEnd().getEdgeList().stream()
            .filter( e -> e.getEdgeEnd().equals( firstEnd ) )
            .findFirst().get()
        );
    }
}
