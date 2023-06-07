package openjfx.graphDrawer;


import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;

import static openjfx.graphDrawer.VerticesDrawer.NODE_RADIUS;

public class ArrowShapedEdge extends EdgeShape {

    private final QuadCurve mainLine;
    private final Line firstArrow;
    private final Line secondArrow;

    double ARROW_LENGTH = 15.0f;
    double ARROW_ANGLE = Math.PI / 9;

    public ArrowShapedEdge ( Circle firstEnd, Circle secondEnd ) {
        this ( firstEnd, secondEnd, new QuadCurve(), new Line(), new Line(), new QuadCurve() );
    }

    private ArrowShapedEdge ( Circle firstEnd, Circle secondEnd, QuadCurve line, Line arrow1, Line arrow2, QuadCurve clickableLine ) {
        super ( line, arrow1, arrow2, clickableLine );
        this.mainLine = line;
        this.firstArrow = arrow1;
        this.secondArrow = arrow2;


        double arrowAngle = 0;
        Point controlPoint = new Point((firstEnd.getCenterX() + secondEnd.getCenterX()) / 2, (firstEnd.getCenterY() + secondEnd.getCenterY()) / 2);
//        if ( Math.abs(firstEnd.getCenterX() - secondEnd.getCenterX()) < 1 ) {
//            if ( firstEnd.getCenterY() < secondEnd.getCenterY() ) {
//                controlPoint.x = firstEnd.getCenterX() - 50;
//            } else {
//                controlPoint.x = firstEnd.getCenterX() + 50;
//            }
//            controlPoint.y = ( firstEnd.getCenterY() + secondEnd.getCenterY() ) / 2;
//        } else {
//            if ( Math.abs(firstEnd.getCenterY() - secondEnd.getCenterY()) < 1 ) {
//                controlPoint.x = ( firstEnd.getCenterX() + secondEnd.getCenterX() ) / 2;
//                if ( firstEnd.getCenterY() < secondEnd.getCenterY() ) {
//                    controlPoint.y = firstEnd.getCenterY() - 50;
//                } else {
//                    controlPoint.y = firstEnd.getCenterY() + 50;
//                }
//            } else {
//                if ( firstEnd.getCenterY() < secondEnd.getCenterY() ) {
//                    controlPoint.
//                }
//            }
//        }

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
        line.setControlX( controlPoint.x );
        line.setControlY( controlPoint.y );

        line.setFill( null );
        line.setStroke( Color.BLACK );
        line.setStrokeWidth( 1 );

        arrow1.setStartX( edgeArrowEnd.x );
        arrow1.setStartY( edgeArrowEnd.y );
        arrow1.setEndX( edgeArrowEnd.x + ARROW_LENGTH * Math.cos ( angle - ARROW_ANGLE - arrowAngle ) * xDirection );
        arrow1.setEndY( edgeArrowEnd.y + ARROW_LENGTH * Math.sin ( angle - ARROW_ANGLE - arrowAngle ) * yDirection );

        arrow2.setStartX(edgeArrowEnd.x );
        arrow2.setStartY(edgeArrowEnd.y );
        arrow2.setEndX(edgeArrowEnd.x + ARROW_LENGTH * Math.cos ( angle + ARROW_ANGLE - arrowAngle ) * xDirection );
        arrow2.setEndY(edgeArrowEnd.y + ARROW_LENGTH * Math.sin ( angle + ARROW_ANGLE - arrowAngle ) * yDirection );

        clickableLine.setStartX( line.getStartX() );
        clickableLine.setStartY( line.getStartY() );
        clickableLine.setEndX( line.getEndX() );
        clickableLine.setEndY( line.getEndY() );
        clickableLine.setControlX( line.getControlX() );
        clickableLine.setControlY( line.getControlY() );
        clickableLine.setStroke( Color.BLACK );
        clickableLine.setStrokeWidth( 10 );
        clickableLine.setFill( null );
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
