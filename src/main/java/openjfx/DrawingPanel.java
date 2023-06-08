package openjfx;

import javafx.scene.Group;

import static openjfx.App.WINDOW_HEIGHT;
import static openjfx.App.WINDOW_WIDTH;

public class DrawingPanel extends Group {

    private Engine engine;

    public final static double CANVAS_WIDTH = WINDOW_WIDTH * 3 / 4;
    public final static double CANVAS_HEIGHT = WINDOW_HEIGHT * 3 / 4;

    public DrawingPanel () {
        this.setOnMouseClicked(
            e -> this.requestFocus()
        );
    }

    public void setEngine ( Engine engine ) {
        this.engine = engine;
    }
}
