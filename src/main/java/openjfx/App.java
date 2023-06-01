package openjfx;
import graph.Edge;
import graph.Graph;
import graph.UndirectedGraph;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import openjfx.graphDrawer.GraphDrawer;
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

        Graph< String, Edge<String> > graph = new UndirectedGraph<>(5, String::valueOf );
        graph.addEdge( graph.getVertexList().get( 0 ), graph.getVertexList().get( 1 ), () -> new Edge<>() );
        graph.addEdge( graph.getVertexList().get( 0 ), graph.getVertexList().get( 2 ), () -> new Edge<>() );
        graph.addEdge( graph.getVertexList().get( 1 ), graph.getVertexList().get( 2 ), () -> new Edge<>() );
        graph.addEdge( graph.getVertexList().get( 2 ), graph.getVertexList().get( 3 ), () -> new Edge<>() );
        graph.addEdge( graph.getVertexList().get( 2 ), graph.getVertexList().get( 4 ), () -> new Edge<>() );

        GraphDrawer<String, Edge<String>> graphDrawer = new UndirectedGraphDrawer<>();
        graphDrawer.draw(graph, drawingPanel);

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