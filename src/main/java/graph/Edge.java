package graph;

import java.util.Objects;

public class Edge < VertexLabelType extends Comparable < VertexLabelType > > {

    private Vertex < VertexLabelType, Edge < VertexLabelType > > edgeEnd;
    private Vertex < VertexLabelType, Edge < VertexLabelType > > ownerVertex;


    public Edge () {
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

    public Vertex < VertexLabelType, Edge < VertexLabelType > > getOwnerVertex () {
        return ownerVertex;
    }

    public void setOwnerVertex ( Vertex < VertexLabelType, Edge < VertexLabelType > > ownerVertex ) {
        this.ownerVertex = ownerVertex;
    }

    @Override
    public String toString () {
        return "Edge { " +
            "edgeEnd=" + ( edgeEnd == null ? "" : edgeEnd.getLabel() ) +
            " }";
    }

    @Override
    public boolean equals ( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Edge < ? > edge = ( Edge < ? > ) o;

        if ( !Objects.equals( edgeEnd, edge.edgeEnd ) ) return false;
        return Objects.equals( ownerVertex, edge.ownerVertex );
    }

    @Override
    public int hashCode () {
        int result = edgeEnd != null ? edgeEnd.hashCode() : 0;
        result = 31 * result + ( ownerVertex != null ? ownerVertex.hashCode() : 0 );
        return result;
    }
}
