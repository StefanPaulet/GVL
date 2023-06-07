package openjfx;
import graph.*;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import openjfx.graphDrawer.*;

public class App extends Application{

    public final static double WINDOW_WIDTH = 1280;
    public final static double WINDOW_HEIGHT = 720;

    @Override
    public void start(Stage primaryStage) {
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
            (key) -> {
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
    public static void main (String[] args)
    {
        launch(args);
    }

    private Point2D eval(CubicCurve c, float t){
        Point2D p=new Point2D(Math.pow(1-t,3)*c.getStartX()+
            3*t*Math.pow(1-t,2)*c.getControlX1()+
            3*(1-t)*t*t*c.getControlX2()+
            Math.pow(t, 3)*c.getEndX(),
            Math.pow(1-t,3)*c.getStartY()+
                3*t*Math.pow(1-t, 2)*c.getControlY1()+
                3*(1-t)*t*t*c.getControlY2()+
                Math.pow(t, 3)*c.getEndY());
        return p;
    }

    /**
     * Evaluate the tangent of the cubic curve at a parameter 0<=t<=1, returns a Point2D
     * @param c the CubicCurve
     * @param t param between 0 and 1
     * @return a Point2D
     */
    private Point2D evalDt(CubicCurve c, float t){
        Point2D p=new Point2D(-3*Math.pow(1-t,2)*c.getStartX()+
            3*(Math.pow(1-t, 2)-2*t*(1-t))*c.getControlX1()+
            3*((1-t)*2*t-t*t)*c.getControlX2()+
            3*Math.pow(t, 2)*c.getEndX(),
            -3*Math.pow(1-t,2)*c.getStartY()+
                3*(Math.pow(1-t, 2)-2*t*(1-t))*c.getControlY1()+
                3*((1-t)*2*t-t*t)*c.getControlY2()+
                3*Math.pow(t, 2)*c.getEndY());
        return p;
    }
}