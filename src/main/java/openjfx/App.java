package openjfx;
import graph.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import openjfx.graphDrawer.*;

public class App extends Application{

    public final static double WINDOW_WIDTH = 1280;
    public final static double WINDOW_HEIGHT = 720;

    @Override
    public void start(Stage primaryStage) {

        Engine engine = new Engine();

        BorderPane root = new BorderPane();

        ConfigPanel configPanel = new ConfigPanel(engine);
        root.setTop( configPanel );

        DrawingPanel drawingPanel = new DrawingPanel(engine);
        root.setCenter( drawingPanel );

        InfoPanel infoPanel = new InfoPanel();
        engine.setInfoPanel( infoPanel );
        root.setLeft( infoPanel );


        Scene scene = new Scene( root, WINDOW_WIDTH, WINDOW_HEIGHT );
        primaryStage.setTitle( "First JavaFX Application" );
        primaryStage.setScene( scene );
        primaryStage.show();
    }
    public static void main (String[] args)
    {
        launch(args);
    }

}