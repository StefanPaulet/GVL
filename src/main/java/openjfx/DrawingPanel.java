package openjfx;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static openjfx.App.WINDOW_HEIGHT;
import static openjfx.App.WINDOW_WIDTH;

public class DrawingPanel extends Canvas {

    public final static double CANVAS_WIDTH = WINDOW_WIDTH * 3 / 4;
    public final static double CANVAS_HEIGHT = WINDOW_HEIGHT * 3 / 4;

    public DrawingPanel () {
        super(CANVAS_WIDTH, CANVAS_HEIGHT);
    }
}
