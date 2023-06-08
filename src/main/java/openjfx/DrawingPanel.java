package openjfx;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static openjfx.App.WINDOW_HEIGHT;
import static openjfx.App.WINDOW_WIDTH;

public class DrawingPanel extends Group {

    private Engine engine;

    public final static double CANVAS_WIDTH = WINDOW_WIDTH * 5 / 6;
    public final static double CANVAS_HEIGHT = WINDOW_HEIGHT * 5 / 6;

    public DrawingPanel () {
        this.setOnMouseClicked(
            e -> this.requestFocus()
        );
    }

    public void setEngine ( Engine engine ) {
        this.engine = engine;
    }
}
