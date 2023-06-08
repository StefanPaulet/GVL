package openjfx;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.CubicCurve;
import javafx.stage.Stage;

public class App extends Application {

    public final static double WINDOW_WIDTH = 1280;
    public final static double WINDOW_HEIGHT = 720;

    @Override
    public void start ( Stage primaryStage ) {
        BorderPane root = new BorderPane();

        ConfigPanel configPanel = new ConfigPanel();
        root.setTop( configPanel );

        DrawingPanel drawingPanel = new DrawingPanel();
        root.setCenter( drawingPanel );

        InfoPanel infoPanel = new InfoPanel();
        root.setLeft( infoPanel );

        Scene scene = new Scene( root, WINDOW_WIDTH, WINDOW_HEIGHT );
        scene.addEventHandler(
            KeyEvent.KEY_PRESSED,
            ( key ) -> {
                if ( key.getCode() == KeyCode.DELETE ) {
                    if ( configPanel.getEngine() != null ) {
                        configPanel.getEngine().handleDeleteKey();
                    }
                }
            }
        );
        primaryStage.setTitle( "First JavaFX Application" );
        primaryStage.setScene( scene );
        primaryStage.show();
    }

    public static void main ( String[] args ) {
        launch( args );
    }
}