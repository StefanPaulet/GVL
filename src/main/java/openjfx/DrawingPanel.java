package openjfx;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;

import static openjfx.App.WINDOW_HEIGHT;
import static openjfx.App.WINDOW_WIDTH;

public class DrawingPanel extends Group {

    private final Engine engine;

    public final static double CANVAS_WIDTH = WINDOW_WIDTH * 3 / 4;
    public final static double CANVAS_HEIGHT = WINDOW_HEIGHT * 3 / 4;

    public DrawingPanel ( Engine engine ) {
        this.engine = engine;
        this.engine.setDrawingPanel( this );

    }
}
