package graph;

public class Edge < VertexLabelType > {

    private Vertex < VertexLabelType, Edge < VertexLabelType > > edgeEnd;

    public Edge ( ) {
    }

    public Edge ( Vertex < VertexLabelType, Edge < VertexLabelType > > edgeEnd ) {
        this.edgeEnd = edgeEnd;
    }

    public Vertex < VertexLabelType, Edge < VertexLabelType > > getEdgeEnd () {
        return edgeEnd;
    }

    public void setEdgeEnd ( Vertex < VertexLabelType, Edge < VertexLabelType > > edgeEnd ) {
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
