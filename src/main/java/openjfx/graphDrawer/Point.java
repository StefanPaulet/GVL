package openjfx.graphDrawer;

public class Point {
    public double x;
    public double y;

    public Point () {
    }

    public Point ( Point other ) {
        this.x = other.x;
        this.y = other.y;
    }

    public Point (
        double x,
        double y
    ) {
        this.x = x;
        this.y = y;
    }

    public Point (
        boolean ignored,
        double coefficient,
        double circleRadius
    ) {
        this.x = VerticesDrawer.MAIN_CIRCLE_X + circleRadius * Math.cos( 2 * Math.PI * coefficient );
        this.y = VerticesDrawer.MAIN_CIRCLE_Y + circleRadius * Math.sin( 2 * Math.PI * coefficient );
    }
}
