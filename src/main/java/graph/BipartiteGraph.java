package graph;


import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BipartiteGraph < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends Edge < VertexLabelType > >
    extends Graph < VertexLabelType, EdgeType > {

    private final Map < Vertex < VertexLabelType, EdgeType >, List < Vertex < VertexLabelType, EdgeType > > > vertexToSubgraphMap = new HashMap <>();

    public enum VertexColoring {
        RED,
        BLUE
    }

    private final Map < Vertex < VertexLabelType, EdgeType >, VertexColoring > vertexColorings = new HashMap <>();

    public BipartiteGraph ( List < Vertex < VertexLabelType, EdgeType > > vertices ) throws NonBipartiteGraphException {
        super( vertices );
        this.checkBipartitionOnGraphCreation();
    }

    public BipartiteGraph ( int nodeCount, Function < Integer, VertexLabelType > nodeLabelGenerator ) {
        super( nodeCount, nodeLabelGenerator );
        for ( int index = 0; index < this.vertexList.size(); ++index ) {
            List < Vertex < VertexLabelType, EdgeType > > newSubgraph = new ArrayList <>();
            newSubgraph.add( this.vertexList.get( index ) );
            vertexToSubgraphMap.put( this.vertexList.get( index ), newSubgraph );

            if ( index < this.vertexList.size() / 2 ) {
                vertexColorings.put( this.vertexList.get( index ), VertexColoring.RED );
            } else {
                vertexColorings.put( this.vertexList.get( index ), VertexColoring.BLUE );
            }
        }
    }


    @Override
    public void fillWithRandomEdges ( double edgeProbability, Supplier < EdgeType > edgeTypeSupplier ) {
        Random random = new Random();
        this.vertexColorings.entrySet()
            .stream()
            .filter( el -> el.getValue() == VertexColoring.RED )
            .forEach(
                first -> this.vertexColorings.entrySet()
                    .stream()
                    .filter( el -> el.getValue() == VertexColoring.BLUE )
                    .filter( el -> random.nextDouble( 1.0 ) < edgeProbability )
                    .map( Map.Entry :: getKey ).forEach(
                        second -> {
                            try {
                                this.addEdge( first.getKey(), second, edgeTypeSupplier );
                            } catch ( Exception ignored ) {
                            }
                        }
                    )
            );
        this.vertexColorings.entrySet()
            .stream()
            .filter( el -> el.getValue() == VertexColoring.BLUE )
            .forEach(
                first -> this.vertexColorings.entrySet()
                    .stream()
                    .filter( el -> el.getValue() == VertexColoring.RED )
                    .filter( el -> random.nextDouble( 1.0 ) < edgeProbability )
                    .map( Map.Entry :: getKey ).forEach(
                        second -> {
                            try {
                                this.addEdge( first.getKey(), second, edgeTypeSupplier );
                            } catch ( Exception ignored ) {
                            }
                        }
                    )
            );
    }

    public Map < Vertex < VertexLabelType, EdgeType >, VertexColoring > getVertexColorings () {
        return vertexColorings;
    }

    @Override
    public void removeEdge ( Vertex < VertexLabelType, EdgeType > firstEnd, EdgeType edge ) throws NonExistingVertexException, NonExistingEdgeException {
        super.removeEdge( firstEnd, edge );
        List < Vertex < VertexLabelType, EdgeType > > firstEndSubgraph = this.computeVertexSubgraph( firstEnd );
        if ( firstEndSubgraph.size() != this.vertexToSubgraphMap.get( firstEnd ).size() ) {
            var secondSubgraph = this.vertexToSubgraphMap.get( firstEnd );
            for ( var vertex : firstEndSubgraph ) {
                secondSubgraph.remove( vertex );
                this.vertexToSubgraphMap.put( vertex, firstEndSubgraph );
            }
        }
    }

    @Override
    public String toString () {
        StringBuilder stringBuilder = new StringBuilder( "Graph {\n" );
        for ( Vertex < VertexLabelType, EdgeType > vertex : this.vertexList ) {
            stringBuilder.append( '\t' )
                .append( vertex )
                .append(
                    vertexColorings.get( vertex ) == VertexColoring.RED ?
                        ", Partition:Red" :
                        ", Partition:Blue"
                )
                .append( '\n' );
        }
        stringBuilder.append( '}' );
        return stringBuilder.toString();
    }


    @Override
    protected void checkEdgeConstraint ( Vertex < VertexLabelType, EdgeType > firstEnd, Vertex < VertexLabelType, EdgeType > secondEnd ) throws NonExistingVertexException, LoopEdgeException, BipartiteEdgeAdditionException {
        super.checkEdgeConstraint( firstEnd, secondEnd );
        this.checkBipartitionOnEdgeAddition( firstEnd, secondEnd );
    }

    protected void checkBipartitionOnEdgeAddition (
        Vertex < VertexLabelType, EdgeType > firstVertex,
        Vertex < VertexLabelType, EdgeType > secondVertex
    ) throws BipartiteEdgeAdditionException {

        var firstVertexSubgraph = vertexToSubgraphMap.get( firstVertex );
        var secondVertexSubgraph = vertexToSubgraphMap.get( secondVertex );

        if ( firstVertexSubgraph != null ) {
            if ( secondVertexSubgraph != null ) {
                if ( firstVertexSubgraph != secondVertexSubgraph ) {
                    mergeSubgraphs( firstVertex, secondVertex, firstVertexSubgraph, secondVertexSubgraph );
                } else {
                    if ( vertexColorings.get( firstVertex ) == vertexColorings.get( secondVertex ) ) {
                        throw new BipartiteEdgeAdditionException( firstVertex, secondVertex );
                    }
                }
            } else {
                vertexColorings.put(
                    secondVertex,
                    vertexColorings.get( firstVertex ) == VertexColoring.RED ?
                        VertexColoring.BLUE :
                        VertexColoring.RED
                );
                vertexToSubgraphMap.put( secondVertex, firstVertexSubgraph );
            }
        } else {
            if ( secondVertexSubgraph != null ) {
                vertexColorings.put(
                    firstVertex,
                    vertexColorings.get( secondVertex ) == VertexColoring.RED ?
                        VertexColoring.BLUE :
                        VertexColoring.RED
                );
                vertexToSubgraphMap.put( firstVertex, secondVertexSubgraph );
            } else {
                vertexColorings.put( firstVertex, VertexColoring.RED );
                vertexColorings.put( secondVertex, VertexColoring.BLUE );
                List < Vertex < VertexLabelType, EdgeType > > newSubgraph = new ArrayList <>();
                newSubgraph.add( firstVertex );
                vertexToSubgraphMap.put( firstVertex, newSubgraph );
                newSubgraph.add( secondVertex );
                vertexToSubgraphMap.put( secondVertex, newSubgraph );
            }
        }
    }


    private void mergeSubgraphs (
        Vertex < VertexLabelType, EdgeType > firstVertex,
        Vertex < VertexLabelType, EdgeType > secondVertex,
        List < Vertex < VertexLabelType, EdgeType > > firstVertexSubgraph,
        List < Vertex < VertexLabelType, EdgeType > > secondVertexSubgraph
    ) {

        if ( vertexColorings.get( firstVertex ) == vertexColorings.get( secondVertex ) ) {
            for ( var vertex : secondVertexSubgraph ) {
                vertexColorings.put(
                    vertex,
                    vertexColorings.get( vertex ) == VertexColoring.RED ?
                        VertexColoring.BLUE :
                        VertexColoring.RED
                );
                firstVertexSubgraph.add( vertex );
                vertexToSubgraphMap.put( vertex, firstVertexSubgraph );
            }
        } else {
            secondVertexSubgraph.forEach(
                vertex -> {
                    firstVertexSubgraph.add( vertex );
                    vertexToSubgraphMap.put( vertex, firstVertexSubgraph );
                }
            );
        }
        secondVertexSubgraph.clear();
    }


    private void checkBipartitionOnGraphCreation () throws NonBipartiteGraphException {
        for ( var vertex : this.vertexList ) {
            if ( vertexColorings.get( vertex ) == null ) {
                this.BFS( vertex, new ArrayList <>() );
            }
        }
    }


    private void BFS (
        Vertex < VertexLabelType, EdgeType > startVertex,
        List < Vertex < VertexLabelType, EdgeType > > subgraph
    ) throws NonBipartiteGraphException {

        vertexColorings.put( startVertex, VertexColoring.RED );
        vertexToSubgraphMap.put( startVertex, subgraph );

        Queue < Vertex < VertexLabelType, EdgeType > > vertexQueue = new LinkedList <>();
        vertexQueue.add( startVertex );

        while ( !vertexQueue.isEmpty() ) {
            var currentVertex = vertexQueue.remove();
            for ( var edge : currentVertex.getEdgeList() ) {
                Vertex < VertexLabelType, EdgeType > adjacentVertex = ( Vertex < VertexLabelType, EdgeType > ) edge.getEdgeEnd();
                if ( vertexColorings.get( adjacentVertex ) == null ) {
                    vertexColorings.put(
                        adjacentVertex,
                        vertexColorings.get( currentVertex ) == VertexColoring.RED ?
                            VertexColoring.BLUE :
                            VertexColoring.RED
                    );
                    vertexToSubgraphMap.put( adjacentVertex, subgraph );
                    vertexQueue.add( adjacentVertex );
                } else {
                    if ( vertexColorings.get( adjacentVertex ) == vertexColorings.get( currentVertex ) ) {
                        throw new NonBipartiteGraphException();
                    }
                }
            }
        }
    }

    private List < Vertex < VertexLabelType, EdgeType > > computeVertexSubgraph ( Vertex < VertexLabelType, EdgeType > startVertex ) {

        List < Vertex < VertexLabelType, EdgeType > > resultList = new ArrayList <>();
        resultList.add( startVertex );

        Queue < Vertex < VertexLabelType, EdgeType > > vertexQueue = new LinkedList <>();
        vertexQueue.add( startVertex );

        while ( !vertexQueue.isEmpty() ) {
            var currentVertex = vertexQueue.remove();
            for ( var edge : currentVertex.getEdgeList() ) {
                Vertex < VertexLabelType, EdgeType > adjacentVertex = ( Vertex < VertexLabelType, EdgeType > ) edge.getEdgeEnd();
                if ( !resultList.contains( adjacentVertex ) ) {
                    vertexQueue.add( adjacentVertex );
                    resultList.add( adjacentVertex );
                }
            }
        }
        return resultList;

    }
}

class NonBipartiteGraphException extends Exception {
    NonBipartiteGraphException () {
        super( "The provided graph is not bipartite" );
    }
}

class BipartiteEdgeAdditionException extends Exception {
    BipartiteEdgeAdditionException (
        Vertex firstVertex,
        Vertex secondVertex
    ) {
        super( "By adding the edge between vertex " + firstVertex.getLabel() +
            " and vertex " + secondVertex.getLabel() +
            " the graph would no longer be bipartite" );
    }
}
