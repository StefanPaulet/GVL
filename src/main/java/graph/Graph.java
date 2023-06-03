package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Graph < VertexLabelType, EdgeType extends Edge < VertexLabelType > >
    implements EdgeAdder < VertexLabelType, EdgeType > {

    protected final List < Vertex < VertexLabelType, EdgeType > > vertexList;

    public Graph () {
        this.vertexList = new ArrayList<>();
    }

    public Graph ( List < Vertex < VertexLabelType, EdgeType > > vertexList ) {
        this.vertexList = vertexList;
    }

    public Graph (
            int nodeCount,
            Function < Integer, VertexLabelType > nodeLabelGenerator
    ) {
        this.vertexList = new ArrayList<>();
        for ( int index = 0; index < nodeCount; ++ index ) {
            VertexLabelType nodeLabel = nodeLabelGenerator.apply(index);
            this.vertexList.add( new Vertex<>( nodeLabel ) );
        }
    }

    public void addNode ( Vertex < VertexLabelType, EdgeType > newVertex ) throws AlreadyExistingVertexException {
        if ( this.vertexList.contains(newVertex) ) {
            throw new AlreadyExistingVertexException(newVertex);
        }

        newVertex.getEdgeList().clear();
        this.vertexList.add(newVertex);
    }

    public List < Vertex < VertexLabelType, EdgeType > > getConstVertexList () {
        return Collections.unmodifiableList(this.vertexList);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Graph {\n");
        for ( Vertex < VertexLabelType, EdgeType > vertex : this.vertexList ) {
            stringBuilder.append('\t').append(vertex).append('\n');
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}


class NonExistingVertexException extends Exception {
    NonExistingVertexException(Vertex vertex) {
        super("Node with label=" + vertex.getLabel() + " does not exist in the graph");
    }
}

class AlreadyExistingEdgeException extends Exception {
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

class AlreadyExistingVertexException extends Exception {
    AlreadyExistingVertexException( Vertex newVertex ) {
        super("Vertex " + newVertex.getLabel() + " is already part of the graph");
    }
}

class LoopEdgeException extends Exception {

    LoopEdgeException ( Vertex loopedVertex ) {
        super("Cannot add edge from vertex=" + loopedVertex + " to itself");
    }
}