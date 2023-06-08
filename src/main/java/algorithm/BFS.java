package algorithm;

import graph.Edge;
import graph.Vertex;
import openjfx.Engine;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BFS < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends Edge < VertexLabelType > >
    extends Algorithm < VertexLabelType, EdgeType > {

    private Vertex < VertexLabelType, EdgeType > startVertex;

    public BFS ( Engine < VertexLabelType, EdgeType > engine ) {
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
        Queue < Vertex < VertexLabelType, EdgeType > > vertexQueue = new LinkedList <>();

        visitedVertices.put( startVertex, true );
        vertexQueue.add( startVertex );

        this.engine.selectVertex( startVertex );
        this.displayMessage( "We start the exploration from vertex " + startVertex.getLabel() );

        while ( ! vertexQueue.isEmpty() ) {
            Vertex < VertexLabelType, EdgeType > currentVertex = vertexQueue.remove();

            this.engine.markFinalVertex( currentVertex );
            this.displayMessage( "We leave vertex " + currentVertex.getLabel() + " and explore his unvisited neighbours" );

            for ( EdgeType edge : currentVertex.getEdgeList() ) {

                this.engine.selectEdge( edge );
                this.displayMessage( "We look at the edge between vertex " + currentVertex.getLabel() + " and vertex " + edge.getEdgeEnd().getLabel() );

                Vertex < VertexLabelType, EdgeType > adjacentVertex = ( Vertex < VertexLabelType, EdgeType > ) edge.getEdgeEnd();

                if ( visitedVertices.get( adjacentVertex ) == null ) {

                    this.engine.markFinalEdge( edge );
                    this.engine.selectVertex( adjacentVertex );
                    this.displayMessage( "The vertex " + adjacentVertex.getLabel() + " is unvisited so we add it to the queue" );
                    visitedEdges.put( edge, true );

                    visitedVertices.put( adjacentVertex, true );

                    vertexQueue.add( adjacentVertex );
                } else {

                    this.engine.deselectEdge( edge );
                    this.displayMessage( "The vertex " + adjacentVertex.getLabel() + " is already visited" );
                }
            }
        }

        this.displayMessage( "We have finished the BFS exploration" );
        visitedEdges.keySet().forEach( this.engine::markFinalEdge );
    }
}
