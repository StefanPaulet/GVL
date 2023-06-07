package openjfx;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import static openjfx.App.WINDOW_HEIGHT;
import static openjfx.App.WINDOW_WIDTH;

public class DrawingPanel extends Group {

    private Engine engine;

    public final static double CANVAS_WIDTH = WINDOW_WIDTH * 5 / 6;
    public final static double CANVAS_HEIGHT = WINDOW_HEIGHT * 5 / 6;

    public void setEngine ( Engine engine ) {
        this.engine = engine;
    }
}
