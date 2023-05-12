package graph;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List< Vertex < Integer, Edge < Integer > > > vertexList = new ArrayList<>();
        vertexList.add(new Vertex<>(1));
        vertexList.add(new Vertex<>(2));
        vertexList.add(new Vertex<>(3));
        vertexList.add(new Vertex<>(4));
        vertexList.add(new Vertex<>(5));

//        Graph < Integer > graph = new Graph<>(vertexList);

//        System.out.println(graph);
    }
}
