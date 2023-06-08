package algorithm;

import graph.Edge;
import graph.Vertex;
import graph.WeighedEdge;
import openjfx.Engine;

import java.util.*;

public class MST < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends WeighedEdge < VertexLabelType > >
    extends Algorithm < VertexLabelType, EdgeType > {

    private final static int MAX_COST = Integer.MAX_VALUE;
    private Vertex < VertexLabelType, EdgeType > startVertex;

    public MST ( Engine < VertexLabelType, EdgeType > engine ) {
        super( engine );
    }

    @Override
    public void setStartCondition ( Object... startConditions ) {
        if ( startConditions.length < 1 ) {
            this.engine.getInfoPanel().setAlgorithmMessage( "You must give me a starting vertex" );
        }
        this.startVertex = ( Vertex <VertexLabelType, EdgeType> ) startConditions[0];
    }

    @Override
    public void run () {
        Map < Vertex < VertexLabelType, EdgeType >, EdgeType > costMap = new HashMap <>();
        List < Vertex < VertexLabelType, EdgeType > > vertexList = new ArrayList <>();
        vertexList.add( this.startVertex );

        this.engine.selectVertex( this.startVertex );
        this.displayMessage( "We start the algorithm from vertex " + this.startVertex.getLabel() );

        for ( var vertex : this.engine.getGraph().getConstVertexList() ) {
            if ( ! vertex.equals( startVertex ) ) {
                EdgeType edge = this.startVertex.getEdgeList().stream()
                    .filter( edgeType -> edgeType.getEdgeEnd().equals( vertex ) )
                    .findFirst()
                    .orElse( null );
                if ( edge != null ) {
                    costMap.put( vertex, edge );
                }
            }
        }

        while ( vertexList.size() != this.engine.getGraph().getConstVertexList().size() ) {

            if ( costMap.isEmpty() ) {
                break;
            }

            Map.Entry < Vertex < VertexLabelType, EdgeType >, EdgeType > entry = costMap.entrySet().stream().min( Map.Entry.comparingByValue() ).get();
            costMap.remove( entry.getKey() );
            Vertex < VertexLabelType, EdgeType > newVertex = entry.getKey();

            this.engine.markFinalEdge( entry.getValue() );
            this.engine.markFinalVertex( entry.getKey() );
            this.displayMessage( "We choose the edge towards vertex " + entry.getValue().getEdgeEnd().getLabel() );

            vertexList.add( entry.getKey() );
            for ( var vertex : this.engine.getGraph().getConstVertexList() ) {
                if ( ! vertexList.contains( vertex ) ) {
                    EdgeType edgeType = this.engine.getGraph().findEdge( newVertex, vertex );
                    if ( edgeType != null ) {
                        if ( costMap.get( vertex ) == null || costMap.get( vertex ).compareTo( edgeType ) > 0 ) {
                            costMap.put( vertex, edgeType );
                        }
                    }
                }
            }
        }
    }
}
