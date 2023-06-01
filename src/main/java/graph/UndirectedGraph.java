package graph;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class UndirectedGraph <
        NodeLabelType,
        EdgeType extends Edge < NodeLabelType >
> extends Graph < NodeLabelType, EdgeType > {

    public UndirectedGraph ( List < Vertex < NodeLabelType, EdgeType > > vertices ) {
        super( vertices );
    }

    public UndirectedGraph ( int nodeCount, Function< Integer, NodeLabelType > nodeLabelGenerator ) {
        super( nodeCount, nodeLabelGenerator );
    }

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
        newFirstEndEdge.setEdgeEnd( ( Vertex< NodeLabelType, Edge< NodeLabelType > > ) secondEnd );
        if ( firstEnd.getEdgeList().contains(newFirstEndEdge) ) {
            throw new AlreadyExistingEdgeException(firstEnd, secondEnd);
        }
        firstEnd.getEdgeList().add(newFirstEndEdge);

        EdgeType newSecondEndEdge = edgeSupplier.get();
        newSecondEndEdge.setEdgeEnd( ( Vertex< NodeLabelType, Edge< NodeLabelType > > ) firstEnd );
        if ( secondEnd.getEdgeList().contains(newSecondEndEdge) ) {
            throw new AlreadyExistingEdgeException(secondEnd, firstEnd);
        }
        secondEnd.getEdgeList().add(newSecondEndEdge);
    }
}
