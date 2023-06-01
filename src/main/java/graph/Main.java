package graph;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List< Vertex < String, Edge < String > > > vertexList = new ArrayList<>();
        vertexList.add(new Vertex<>("1"));
        vertexList.add(new Vertex<>("2"));
        vertexList.add(new Vertex<>("3"));
        vertexList.add(new Vertex<>("4"));
        vertexList.add(new Vertex<>("5"));
        Graph < String, Edge<String> > graph = new UndirectedGraph<>(10, String::valueOf );
        graph.addEdge( graph.vertexList.get( 0 ), graph.vertexList.get( 1 ), () -> new Edge<>() );
        graph.addEdge( graph.vertexList.get( 0 ), graph.vertexList.get( 2 ), () -> new Edge<>() );
        System.out.println(graph);
    }
}