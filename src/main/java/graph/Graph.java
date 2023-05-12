package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Graph <
        NodeLabelType,
        EdgeType extends Edge < NodeLabelType >
> {

    protected final List < Vertex < NodeLabelType, EdgeType > > vertexList;

    public Graph () {
        this.vertexList = new ArrayList<>();
    }

    public Graph ( List < Vertex < NodeLabelType, EdgeType > > vertexList ) {
        this.vertexList = vertexList;
    }

    public Graph (
            int nodeCount,
            Function < Integer, NodeLabelType > nodeLabelGenerator
    ) {
        this.vertexList = new ArrayList<>();
        for ( int index = 0; index < nodeCount; ++ index ) {
            NodeLabelType nodeLabel = nodeLabelGenerator.apply(index);
            this.vertexList.add( new Vertex < NodeLabelType, EdgeType > ( nodeLabel ) );
        }
    }

    public List < Vertex < NodeLabelType, EdgeType > > getVertexList () {
        return vertexList;
    }

    public abstract void addEdge (
            Vertex < NodeLabelType, EdgeType > firstEnd,
            Vertex < NodeLabelType, EdgeType > secondEnd,
            Supplier < EdgeType > edgeSupplier
    );

    public abstract void addEdge ( EdgeType edge );

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Graph {\n");
        for ( Vertex < NodeLabelType, EdgeType > vertex : this.vertexList ) {
            stringBuilder.append(vertex).append('\n');
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}


class NonExistingVertexException extends RuntimeException {
    NonExistingVertexException(Vertex vertex) {
        super("Node with label=" + vertex.getLabel() + " does not exist in the graph");
    }
}

class AlreadyExistingEdgeException extends RuntimeException {
    AlreadyExistingEdgeException(
            Vertex firstEnd,
            Vertex secondEnd
    ) {
        super(
            "Edge with firstEnd=" + firstEnd.getLabel() +
            " and secondEnd=" + secondEnd.getLabel() +
            " already exists in the graph"
        );
    }
}