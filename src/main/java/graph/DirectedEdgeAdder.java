package graph;

import java.util.function.Supplier;

public interface DirectedEdgeAdder < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends Edge < VertexLabelType > >
    extends EdgeAdder < VertexLabelType, EdgeType > {

    @Override
    default void addEdgeNoexcept (
        Vertex < VertexLabelType, EdgeType > firstEnd,
        Vertex < VertexLabelType, EdgeType > secondEnd,
        Supplier < EdgeType > edgeSupplier
    ) throws AlreadyExistingEdgeException {
        EdgeType newFirstEndEdge = edgeSupplier.get();
        newFirstEndEdge.setEdgeEnd( ( Vertex < VertexLabelType, Edge < VertexLabelType > > ) secondEnd );
        newFirstEndEdge.setOwnerVertex( ( Vertex < VertexLabelType, Edge < VertexLabelType > > ) firstEnd );
        if ( firstEnd.getEdgeList().contains( newFirstEndEdge ) ) {
            throw new AlreadyExistingEdgeException( firstEnd, secondEnd );
        }
        firstEnd.getEdgeList().add( newFirstEndEdge );
    }

    @Override
    default void removeEdgeNoexcept ( Vertex < VertexLabelType, EdgeType > firstEnd, EdgeType edge ) {
        firstEnd.getEdgeList().remove( edge );
    }


}