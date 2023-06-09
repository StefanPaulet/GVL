package algorithm;

import graph.Edge;
import graph.Vertex;
import openjfx.Engine;

import java.util.*;

public class DFS < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends Edge < VertexLabelType > >
    extends Algorithm < VertexLabelType, EdgeType > {

    private Vertex < VertexLabelType, EdgeType > startVertex;

    public DFS ( Engine < VertexLabelType, EdgeType > engine ) {
        super( engine );
    }

    @Override
    public void setStartCondition ( Object... startConditions ) {
        if ( startConditions.length < 1 ) {
            this.engine.getInfoPanel().setAlgorithmMessage( "You must give me a starting vertex" );
        }
        this.startVertex = (Vertex<VertexLabelType, EdgeType> ) startConditions[0];
    }

    @Override
    public void run () {
        Map < Vertex < VertexLabelType, EdgeType >, Boolean > visitedVertices = new HashMap <>();
        Map < EdgeType, Boolean > visitedEdges = new HashMap <>();
        Stack < Vertex < VertexLabelType, EdgeType > > vertexStack = new Stack <>();

        visitedVertices.put( startVertex, true );
        vertexStack.push( startVertex );

        this.engine.selectVertex( startVertex );
        this.displayMessage( "We start the exploration from vertex " + startVertex.getLabel() );

        while ( ! vertexStack.isEmpty() && ! stopped ) {
            Vertex < VertexLabelType, EdgeType > currentVertex = vertexStack.peek();

            this.displayMessage( "We leave vertex " + currentVertex.getLabel() + " and explore his unvisited neighbours" );

            int index;
            for ( index = 0; index < currentVertex.getEdgeList().size(); ++ index ) {

                EdgeType edge = currentVertex.getEdgeList().get( index );
                if ( visitedEdges.get( edge ) != null ) {
                    continue;
                }

                this.engine.selectEdge( edge );
                this.displayMessage( "We look at the edge between vertex " + currentVertex.getLabel() + " and vertex " + edge.getEdgeEnd().getLabel() );

                Vertex < VertexLabelType, EdgeType > adjacentVertex = ( Vertex < VertexLabelType, EdgeType > ) edge.getEdgeEnd();

                if ( visitedVertices.get( adjacentVertex ) == null ) {

                    this.engine.markFinalEdge( edge );
                    this.engine.selectVertex( adjacentVertex );
                    this.displayMessage( "The vertex " + adjacentVertex.getLabel() + " is unvisited so we add it to the stack" );

                    visitedVertices.put( adjacentVertex, true );
                    visitedEdges.put( edge, true );

                    vertexStack.push( adjacentVertex );
                    break;
                } else {

                    this.engine.deselectEdge( edge );
                    this.displayMessage( "The vertex " + adjacentVertex.getLabel() + " is already visited" );
                }
            }
            if ( index >= currentVertex.getEdgeList().size() ) {
                this.engine.markFinalVertex( currentVertex );
                vertexStack.pop();
            }
        }

        if ( ! stopped ) {
            this.displayMessage( "We have finished the DFS exploration" );
            visitedEdges.keySet().forEach( this.engine :: markFinalEdge );
        }
    }
}
