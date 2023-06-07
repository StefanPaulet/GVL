package openjfx.graphDrawer;


import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import static openjfx.graphDrawer.VerticesDrawer.NODE_RADIUS;

public class ArrowShapedEdge extends EdgeShape {

    private final Line mainLine;
    private final Line firstArrow;
    private final Line secondArrow;

    double ARROW_LENGTH = 15.0f;
    double ARROW_ANGLE = Math.PI / 9;

    public ArrowShapedEdge ( Circle firstEnd, Circle secondEnd ) {
        this ( firstEnd, secondEnd, new Line(), new Line(), new Line(), new Line() );
    }

    private ArrowShapedEdge ( Circle firstEnd, Circle secondEnd, Line line, Line arrow1, Line arrow2, Line clickableLine ) {
        super ( line, arrow1, arrow2, clickableLine );
        this.mainLine = line;
        this.firstArrow = arrow1;
        this.secondArrow = arrow2;

        double angle = Math.acos (
            Math.abs ( firstEnd.getCenterX() - secondEnd.getCenterX() ) /
                Math.sqrt (
                    Math.pow ( firstEnd.getCenterX() - secondEnd.getCenterX(), 2 ) +
                        Math.pow ( firstEnd.getCenterY() - secondEnd.getCenterY(), 2 )
                )
        );

        double xDirection = firstEnd.getCenterX() < secondEnd.getCenterX() ? -1.0 : 1.0;
        double yDirection = firstEnd.getCenterY() < secondEnd.getCenterY() ? -1.0 : 1.0;

        Point edgeArrowStart = new Point (
            firstEnd.getCenterX() - NODE_RADIUS * Math.cos ( angle ) * xDirection,
            firstEnd.getCenterY() - NODE_RADIUS * Math.sin ( angle ) * yDirection
        );
        Point edgeArrowEnd = new Point (
            secondEnd.getCenterX() + NODE_RADIUS * Math.cos ( angle ) * xDirection,
            secondEnd.getCenterY() + NODE_RADIUS * Math.sin ( angle ) * yDirection
        );
        
        line.setStartX( edgeArrowStart.x );
        line.setStartY( edgeArrowStart.y );
        line.setEndX( edgeArrowEnd.x );
        line.setEndY( edgeArrowEnd.y );

        arrow1.setStartX( edgeArrowEnd.x );
        arrow1.setStartY( edgeArrowEnd.y );
        arrow1.setEndX( edgeArrowEnd.x + ARROW_LENGTH * Math.cos ( angle - ARROW_ANGLE ) * xDirection );
        arrow1.setEndY( edgeArrowEnd.y + ARROW_LENGTH * Math.sin ( angle - ARROW_ANGLE ) * yDirection );

        arrow2.setStartX(edgeArrowEnd.x );
        arrow2.setStartY(edgeArrowEnd.y );
        arrow2.setEndX(edgeArrowEnd.x + ARROW_LENGTH * Math.cos ( angle + ARROW_ANGLE ) * xDirection );
        arrow2.setEndY(edgeArrowEnd.y + ARROW_LENGTH * Math.sin ( angle + ARROW_ANGLE ) * yDirection );

        clickableLine.setStartX( line.getStartX() );
        clickableLine.setStartY( line.getStartY() );
        clickableLine.setEndX( line.getEndX() );
        clickableLine.setEndY( line.getEndY() );
        clickableLine.setStrokeWidth( 10 );
        clickableLine.setOpacity( 0 );
    }

    @Override
    public void select () {
        this.mainLine.setStyle( "-fx-stroke: red;" );
        this.firstArrow.setStyle( "-fx-stroke: red;" );
        this.secondArrow.setStyle( "-fx-stroke: red;" );
    }

    @Override
    public void deselect () {
        this.mainLine.setStyle( "-fx-stroke: black;" );
        this.firstArrow.setStyle( "-fx-stroke: black;" );
        this.secondArrow.setStyle( "-fx-stroke: black;" );
    }
}
