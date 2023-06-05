package graph;


import javafx.scene.control.Label;

import java.util.*;
import java.util.function.Function;

public abstract class BipartiteGraph < VertexLabelType, EdgeType extends Edge < VertexLabelType > >
    extends Graph < VertexLabelType, EdgeType > {

    private final Map < Vertex < VertexLabelType, EdgeType >, List < Vertex < VertexLabelType, EdgeType > > > vertexToSubgraphMap = new HashMap<>();
    public enum VertexColoring {
        RED,
        BLUE
    }
    private final Map < Vertex < VertexLabelType, EdgeType >, VertexColoring > vertexColorings = new HashMap<>();

    public BipartiteGraph ( List < Vertex < VertexLabelType, EdgeType > > vertices ) throws NonBipartiteGraphException {
        super(vertices);
        this.checkBipartitionOnGraphCreation();
    }

    public BipartiteGraph(int nodeCount, Function < Integer, VertexLabelType > nodeLabelGenerator) {
        super(nodeCount, nodeLabelGenerator);
        for ( int index = 0; index < this.vertexList.size(); ++ index ) {
            List < Vertex < VertexLabelType, EdgeType > > newSubgraph = new ArrayList <> ();
            newSubgraph.add ( this.vertexList.get(index) );
            vertexToSubgraphMap.put(this.vertexList.get(index), newSubgraph );

            if ( index < this.vertexList.size() / 2 ) {
                vertexColorings.put(this.vertexList.get(index), VertexColoring.RED);
            } else {
                vertexColorings.put(this.vertexList.get(index), VertexColoring.BLUE);
            }
        }
    }

    public Map < Vertex < VertexLabelType, EdgeType >, VertexColoring > getVertexColorings () {
        return vertexColorings;
    }

    @Override
    public String toString () {
        StringBuilder stringBuilder = new StringBuilder("Graph {\n");
        for ( Vertex < VertexLabelType, EdgeType > vertex : this.vertexList ) {
            stringBuilder.append('\t')
                .append(vertex)
                .append(
                    vertexColorings.get ( vertex ) == VertexColoring.RED ?
                    ", Partition:Red" :
                    ", Partition:Blue"
                )
                .append('\n');
        }
        stringBuilder.append('}');
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

        var firstVertexSubgraph = vertexToSubgraphMap.get(firstVertex);
        var secondVertexSubgraph = vertexToSubgraphMap.get(secondVertex);

        if ( firstVertexSubgraph != null ) {
            if ( secondVertexSubgraph != null ) {
                if ( firstVertexSubgraph != secondVertexSubgraph ) {
                    mergeSubgraphs(firstVertex, secondVertex, firstVertexSubgraph, secondVertexSubgraph);
                } else {
                    if ( vertexColorings.get(firstVertex) == vertexColorings.get(secondVertex) ) {
                        throw new BipartiteEdgeAdditionException(firstVertex, secondVertex);
                    }
                }
            } else {
                vertexColorings.put(
                    secondVertex,
                    vertexColorings.get(firstVertex) == VertexColoring.RED ?
                        VertexColoring.BLUE :
                        VertexColoring.RED
                );
                vertexToSubgraphMap.put(secondVertex, firstVertexSubgraph);
            }
        } else {
            if ( secondVertexSubgraph != null ) {
                vertexColorings.put(
                        firstVertex,
                        vertexColorings.get(secondVertex) == VertexColoring.RED ?
                                VertexColoring.BLUE :
                                VertexColoring.RED
                );
                vertexToSubgraphMap.put(firstVertex, secondVertexSubgraph);
            } else {
                vertexColorings.put(firstVertex, VertexColoring.RED);
                vertexColorings.put(secondVertex, VertexColoring.BLUE);
                List < Vertex < VertexLabelType, EdgeType > > newSubgraph = new ArrayList<>();
                newSubgraph.add(firstVertex);
                vertexToSubgraphMap.put(firstVertex, newSubgraph);
                newSubgraph.add(secondVertex);
                vertexToSubgraphMap.put(secondVertex, newSubgraph);
            }
        }
    }


    private void mergeSubgraphs (
            Vertex < VertexLabelType, EdgeType > firstVertex,
            Vertex < VertexLabelType, EdgeType > secondVertex,
            List < Vertex < VertexLabelType, EdgeType > > firstVertexSubgraph,
            List < Vertex < VertexLabelType, EdgeType > > secondVertexSubgraph
    ) {

        if ( vertexColorings.get(firstVertex) == vertexColorings.get(secondVertex) ) {
            for ( var vertex : secondVertexSubgraph ) {
                vertexColorings.put(
                    vertex,
                    vertexColorings.get(vertex) == VertexColoring.RED ?
                        VertexColoring.BLUE :
                        VertexColoring.RED
                );
                firstVertexSubgraph.add(vertex);
                vertexToSubgraphMap.put(vertex, firstVertexSubgraph);
            }
        } else {
            secondVertexSubgraph.forEach (
                vertex -> {
                    firstVertexSubgraph.add ( vertex );
                    vertexToSubgraphMap.put ( vertex, firstVertexSubgraph );
                }
            );
        }
        secondVertexSubgraph.clear();
    }


    private void checkBipartitionOnGraphCreation() throws NonBipartiteGraphException {
        for ( var vertex : this.vertexList ) {
            if ( vertexColorings.get(vertex) == null ) {
                this.BFS( vertex, new ArrayList<>() );
            }
        }
    }


    private void BFS (
        Vertex < VertexLabelType, EdgeType > startVertex,
        List < Vertex < VertexLabelType, EdgeType > > subgraph
    ) throws NonBipartiteGraphException {

        vertexColorings.put( startVertex, VertexColoring.RED );
        vertexToSubgraphMap.put( startVertex, subgraph );

        Queue < Vertex < VertexLabelType, EdgeType > > vertexQueue = new LinkedList<>();
        vertexQueue.add(startVertex);

        while ( ! vertexQueue.isEmpty() ) {
            var currentVertex = vertexQueue.remove();
            for ( var edge : currentVertex.getEdgeList() ) {
                Vertex < VertexLabelType, EdgeType > adjacentVertex = (Vertex<VertexLabelType, EdgeType>) edge.getEdgeEnd();
                if ( vertexColorings.get(adjacentVertex) == null ) {
                    vertexColorings.put(
                        adjacentVertex,
                        vertexColorings.get(currentVertex) == VertexColoring.RED ?
                            VertexColoring.BLUE :
                            VertexColoring.RED
                    );
                    vertexToSubgraphMap.put(adjacentVertex, subgraph);
                    vertexQueue.add(adjacentVertex);
                } else {
                    if ( vertexColorings.get(adjacentVertex) == vertexColorings.get(currentVertex) ) {
                        throw new NonBipartiteGraphException();
                    }
                }
            }
        }
    }
}

class NonBipartiteGraphException extends Exception {
    NonBipartiteGraphException () {
        super ( "The provided graph is not bipartite" );
    }
}

class BipartiteEdgeAdditionException extends Exception {
    BipartiteEdgeAdditionException (
        Vertex firstVertex,
        Vertex secondVertex
    ) {
        super ( "By adding the edge between vertex " + firstVertex.getLabel() +
            " and vertex " + secondVertex.getLabel() +
            " the graph would no longer be bipartite" );
    }
}
