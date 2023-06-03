package graph;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Graph < String, Edge<String> > graph = new UndirectedBipartiteGraph <> (6, String::valueOf );
        try {
            graph.addEdge( graph.getConstVertexList(), graph.vertexList.get( 0 ), graph.vertexList.get( 1 ), () -> new Edge<>() );
            graph.addEdge( graph.getConstVertexList(), graph.vertexList.get( 0 ), graph.vertexList.get( 3 ), () -> new Edge<>() );
            graph.addEdge( graph.getConstVertexList(), graph.vertexList.get( 2 ), graph.vertexList.get( 5 ), () -> new Edge<>() );
            graph.addEdge( graph.getConstVertexList(), graph.vertexList.get( 4 ), graph.vertexList.get( 5 ), () -> new Edge<>() );
            graph.addEdge( graph.getConstVertexList(), graph.vertexList.get( 0 ), graph.vertexList.get( 5 ), () -> new Edge<>() );
        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        System.out.println(graph);
    }
}