package openjfx.graphDrawer;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.function.UnaryOperator;

import static openjfx.graphDrawer.VerticesDrawer.NODE_RADIUS;

public class LineShapedEdge extends EdgeShape {

    private final Line line;

    public LineShapedEdge( Circle firstEnd, Circle secondEnd ) {
        this(firstEnd, secondEnd, new Line(), new Line());
    }

    private LineShapedEdge( Circle firstEnd, Circle secondEnd, Line line, Line clickableLine ) {
        super(line, clickableLine);
        this.line = line;

        double angle = Math.acos (
            Math.abs ( firstEnd.getCenterX() - secondEnd.getCenterX() ) /
                Math.sqrt (
                    Math.pow ( firstEnd.getCenterX() - secondEnd.getCenterX(), 2 ) +
                        Math.pow ( firstEnd.getCenterY() - secondEnd.getCenterY(), 2 )
                )
        );

        double xDirection = firstEnd.getCenterX() < secondEnd.getCenterX() ? -1.0 : 1.0;
        double yDirection = firstEnd.getCenterY() < secondEnd.getCenterY() ? -1.0 : 1.0;

        this.line.setStartX( firstEnd.getCenterX() - NODE_RADIUS * Math.cos ( angle ) * xDirection );
        this.line.setStartY( firstEnd.getCenterY() - NODE_RADIUS * Math.sin ( angle ) * yDirection );
        this.line.setEndX( secondEnd.getCenterX() + NODE_RADIUS * Math.cos ( angle ) * xDirection );
        this.line.setEndY( secondEnd.getCenterY() + NODE_RADIUS * Math.sin ( angle ) * yDirection );

        clickableLine.setStartX( line.getStartX() );
        clickableLine.setStartY( line.getStartY() );
        clickableLine.setEndX( line.getEndX() );
        clickableLine.setEndY( line.getEndY() );
        clickableLine.setStrokeWidth( 10 );
        clickableLine.setOpacity( 0 );
    }

    @Override
    public void select () {
        this.line.setStyle( "-fx-stroke: red;" );
    }

    @Override
    public void deselect () {
        this.line.setStyle( "-fx-stroke: black;" );
    }

    @Override
    public void addLabel ( String textString, ChangeListener onLabelChangeEvent ) {
        TextField text = new TextField(textString);
        text.relocate(
            ( this.line.getStartX() + this.line.getEndX() ) / 2,
            ( this.line.getStartY() + this.line.getEndY() ) / 2
        );
        text.setPrefWidth( 40 );
        this.getChildren().add( text );

        UnaryOperator < TextFormatter.Change> integerFilter = change -> {
            String input = change.getControlNewText();
            if ( input.matches("^[1-9]\\d*$|")) {
                if ( ! input.equals( "" ) && Integer.parseInt( input ) > 99 ) {
                    return null;
                }
                return change;
            }
            return null;
        };
        text.setTextFormatter(new TextFormatter<>(integerFilter));
        text.textProperty().addListener( onLabelChangeEvent );
    }
}
