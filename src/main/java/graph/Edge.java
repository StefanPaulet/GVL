package graph;

public class Edge < NodeLabelType > {

    private Vertex < NodeLabelType, Edge < NodeLabelType > > edgeEnd;

    public Edge ( ) {
    }

    public Edge ( Vertex < NodeLabelType, Edge < NodeLabelType > > edgeEnd ) {
        this.edgeEnd = edgeEnd;
    }

    public Vertex < NodeLabelType, Edge < NodeLabelType > > getEdgeEnd () {
        return edgeEnd;
    }

    public void setEdgeEnd ( Vertex < NodeLabelType, Edge < NodeLabelType > > edgeEnd ) {
        this.edgeEnd = edgeEnd;
    }

    @Override
    public String toString() {
        return "Edge { " +
                "edgeEnd=" + (edgeEnd == null ? "" : edgeEnd.getLabel()) +
                " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge<?> edge = (Edge<?>) o;

        return edgeEnd.equals(edge.edgeEnd);
    }

    @Override
    public int hashCode() {
        return edgeEnd.hashCode();
    }
}
