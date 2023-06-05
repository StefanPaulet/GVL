package graph;


public class Main {

    public static void main(String[] args) {
        Graph < String, Edge<String> > graph = new UndirectedBipartiteGraph <> (6, String::valueOf );
        try {
            graph.addEdge( graph.vertexList.get( 0 ), graph.vertexList.get( 1 ), () -> new Edge<>() );
            graph.addEdge( graph.vertexList.get( 0 ), graph.vertexList.get( 0 ), () -> new Edge<>() );
            graph.addEdge( graph.vertexList.get( 2 ), graph.vertexList.get( 5 ), () -> new Edge<>() );
            graph.addEdge( graph.vertexList.get( 4 ), graph.vertexList.get( 5 ), () -> new Edge<>() );
            graph.addEdge( graph.vertexList.get( 0 ), graph.vertexList.get( 5 ), () -> new Edge<>() );
        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        System.out.println(graph);
    }
}

//class A { }
//
//class Generic < T > {
//    public void f ( OtherGeneric < T, Generic < T > > g ) {
//        System.out.println("What");
//    }
//}
//
//class OtherGeneric < T,  U extends Generic < T > > {
//    public Generic < T > obj;
//    public void f ( U ceva ) {
//        ceva.f( ( OtherGeneric < T, Generic < T > > ) this );
//    }
//}
//OtherGeneric<A, Generic<A>> otherGeneric = new OtherGeneric <>();
//    Generic<A> generic = new Generic <>();
//        otherGeneric.f(generic);
// Only God understands why this works