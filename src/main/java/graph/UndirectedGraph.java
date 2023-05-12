package graph;

import java.util.function.Supplier;

public class UndirectedGraph < NodeLabelType, EdgeType extends Edge < NodeLabelType > > extends Graph < NodeLabelType, EdgeType > {
    @Override
    public void addEdge(
            Vertex < NodeLabelType, EdgeType > firstEnd,
            Vertex < NodeLabelType, EdgeType > secondEnd,
            Supplier < EdgeType > edgeSupplier
    ) {

        if ( ! this.vertexList.contains(firstEnd) ) {
            throw new NonExistingVertexException(firstEnd);
        }

        if ( ! this.vertexList.contains(secondEnd) ) {
            throw new NonExistingVertexException(secondEnd);
        }

        EdgeType newFirstEndEdge = edgeSupplier.get();
        if ( firstEnd.getEdgeList().contains(newFirstEndEdge) ) {
            throw new AlreadyExistingEdgeException(firstEnd, secondEnd);
        }

        firstEnd.getEdgeList().add(newFirstEndEdge);
    }

    @Override
    public void addEdge(EdgeType edge) {

    }
}
