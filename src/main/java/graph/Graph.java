package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Graph < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends Edge < VertexLabelType > >
    implements EdgeAdder < VertexLabelType, EdgeType > {

    protected final List < Vertex < VertexLabelType, EdgeType > > vertexList;

    public Graph () {
        this.vertexList = new ArrayList <>();
    }

    public Graph ( List < Vertex < VertexLabelType, EdgeType > > vertexList ) {
        this.vertexList = vertexList;
    }

    public Graph (
        int nodeCount,
        Function < Integer, VertexLabelType > nodeLabelGenerator
    ) {
        this.vertexList = new ArrayList <>();
        for ( int index = 0; index < nodeCount; ++index ) {
            VertexLabelType nodeLabel = nodeLabelGenerator.apply( index );
            this.vertexList.add( new Vertex <>( nodeLabel ) );
        }
    }

    public void addNode ( Vertex < VertexLabelType, EdgeType > newVertex ) throws AlreadyExistingVertexException {
        if ( this.vertexList.contains( newVertex ) ) {
            throw new AlreadyExistingVertexException( newVertex );
        }

        newVertex.getEdgeList().clear();
        this.vertexList.add( newVertex );
    }


    public void fillWithRandomEdges ( double edgeProbability, Supplier < EdgeType > edgeTypeSupplier ) {

        Random random = new Random();

        for ( var firstVertex : this.vertexList ) {
            for ( var secondVertex : this.vertexList ) {
                if ( random.nextDouble( 1.0 ) < edgeProbability ) {
                    try {
                        this.addEdge( firstVertex, secondVertex, edgeTypeSupplier );
                    } catch ( Exception ignored ) {
                    }
                }
            }
        }
    }

    public List < Vertex < VertexLabelType, EdgeType > > getConstVertexList () {
        return Collections.unmodifiableList( this.vertexList );
    }


    protected void checkEdgeConstraint (
        Vertex < VertexLabelType, EdgeType > firstEnd,
        Vertex < VertexLabelType, EdgeType > secondEnd
    ) throws NonExistingVertexException, LoopEdgeException, BipartiteEdgeAdditionException {

        if ( !this.vertexList.contains( firstEnd ) ) {
            throw new NonExistingVertexException( firstEnd );
        }

        if ( !this.vertexList.contains( secondEnd ) ) {
            throw new NonExistingVertexException( secondEnd );
        }

        if ( firstEnd.equals( secondEnd ) ) {
            throw new LoopEdgeException( firstEnd );
        }
    }


    public EdgeType findEdge (
        Vertex < VertexLabelType, EdgeType > firstEnd,
        Vertex < VertexLabelType, EdgeType > secondEnd
    ) {
        return firstEnd.getEdgeList().stream()
            .filter( e -> e.getEdgeEnd().equals( secondEnd ) )
            .findAny()
            .orElse( null );
    }


    @Override
    public void addEdge (
        Vertex < VertexLabelType, EdgeType > firstEnd,
        Vertex < VertexLabelType, EdgeType > secondEnd,
        Supplier < EdgeType > edgeSupplier
    ) throws NonExistingVertexException, LoopEdgeException, AlreadyExistingEdgeException, BipartiteEdgeAdditionException {

        this.checkEdgeConstraint( firstEnd, secondEnd );
        EdgeAdder.super.addEdge( firstEnd, secondEnd, edgeSupplier );
    }


    @Override
    public void removeEdge ( Vertex < VertexLabelType, EdgeType > firstEnd, EdgeType edge ) throws NonExistingEdgeException, NonExistingVertexException {

        try {
            this.checkEdgeConstraint( firstEnd, ( Vertex < VertexLabelType, EdgeType > ) edge.getEdgeEnd() );
        } catch ( NonExistingVertexException exception ) {
            throw exception;
        } catch ( Exception ignored ) {
        }

        if ( !firstEnd.getEdgeList().contains( edge ) ) {
            throw new NonExistingEdgeException( firstEnd, edge );
        }

        EdgeAdder.super.removeEdge( firstEnd, edge );
    }

    @Override
    public String toString () {
        StringBuilder stringBuilder = new StringBuilder( "Graph {\n" );
        for ( Vertex < VertexLabelType, EdgeType > vertex : this.vertexList ) {
            stringBuilder.append( '\t' ).append( vertex ).append( '\n' );
        }
        stringBuilder.append( '}' );
        return stringBuilder.toString();
    }
}


class NonExistingVertexException extends Exception {
    NonExistingVertexException ( Vertex vertex ) {
        super( "Node with label=" + vertex.getLabel() + " does not exist in the graph" );
    }
}


class NonExistingEdgeException extends Exception {
    NonExistingEdgeException ( Vertex vertex, Edge edge ) {
        super( "Edge with firstEnd=" + vertex.getLabel() + " secondEnd=" + edge.getEdgeEnd().getLabel() + " does not exist in the graph" );
    }
}

class AlreadyExistingEdgeException extends Exception {
    AlreadyExistingEdgeException (
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
    AlreadyExistingVertexException ( Vertex newVertex ) {
        super( "Vertex " + newVertex.getLabel() + " is already part of the graph" );
    }
}

class LoopEdgeException extends Exception {

    LoopEdgeException ( Vertex loopedVertex ) {
        super( "Cannot add edge from vertex " + loopedVertex.getLabel() + " to itself" );
    }
}