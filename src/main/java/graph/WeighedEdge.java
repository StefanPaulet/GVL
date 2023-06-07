package graph;

public class WeighedEdge < VertexLabelType > extends Edge < VertexLabelType > {

    private int weight;

    public WeighedEdge ( int weight ) {
        this.weight = weight;
    }

    public WeighedEdge ( Vertex < VertexLabelType, Edge < VertexLabelType > > edgeEnd, int weight ) {
        super( edgeEnd );
        this.weight = weight;
    }

    public int getWeight () {
        return weight;
    }

    public void setWeight ( int weight ) {
        this.weight = weight;
    }
}
