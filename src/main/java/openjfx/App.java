package openjfx;
import graph.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
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
        scene.addEventHandler( KeyEvent.KEY_PRESSED, (key) -> {
                if ( key.getCode() == KeyCode.DELETE ) {
                    engine.handleDeleteKey();
                }
            }
        );
        primaryStage.setTitle( "First JavaFX Application" );
        primaryStage.setScene( scene );
        primaryStage.show();
    }
    public static void main (String[] args)
    {
        launch(args);
    }

}