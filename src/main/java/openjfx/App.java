package openjfx;
import graph.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import openjfx.graphDrawer.GraphDrawer;
import openjfx.graphDrawer.UndirectedBipartiteGraphDrawer;
import openjfx.graphDrawer.UndirectedGraphDrawer;

public class App extends Application{

    public final static double WINDOW_WIDTH = 1280;
    public final static double WINDOW_HEIGHT = 720;

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();

        ConfigPanel configPanel = new ConfigPanel();
        root.setTop( configPanel );

        DrawingPanel drawingPanel = new DrawingPanel();
        root.setCenter( drawingPanel );


        Graph < String, Edge<String> > graph = new UndirectedBipartiteGraph <> (6, String::valueOf );
        try {
            graph.addEdge( graph.getConstVertexList(), graph.getConstVertexList().get( 0 ), graph.getConstVertexList().get( 1 ), () -> new Edge<>() );
            graph.addEdge( graph.getConstVertexList(), graph.getConstVertexList().get( 0 ), graph.getConstVertexList().get( 3 ), () -> new Edge<>() );
            graph.addEdge( graph.getConstVertexList(), graph.getConstVertexList().get( 2 ), graph.getConstVertexList().get( 5 ), () -> new Edge<>() );
            graph.addEdge( graph.getConstVertexList(), graph.getConstVertexList().get( 4 ), graph.getConstVertexList().get( 5 ), () -> new Edge<>() );
            graph.addEdge( graph.getConstVertexList(), graph.getConstVertexList().get( 0 ), graph.getConstVertexList().get( 5 ), () -> new Edge<>() );
        } catch ( Exception e ) {
            e.printStackTrace ();
        }

        GraphDrawer < String, Edge < String > > graphDrawer = new UndirectedBipartiteGraphDrawer <>();
        graphDrawer.draw( graph, drawingPanel );

        Scene scene = new Scene( root, WINDOW_WIDTH,WINDOW_HEIGHT );
        primaryStage.setTitle( "First JavaFX Application" );
        primaryStage.setScene( scene );
        primaryStage.show();
    }
    public static void main (String[] args)
    {
        launch(args);
    }

}