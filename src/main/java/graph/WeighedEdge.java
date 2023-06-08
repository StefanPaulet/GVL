package graph;

public class WeighedEdge < VertexLabelType extends Comparable < VertexLabelType > > extends Edge < VertexLabelType >
    implements Comparable < Object > {

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

    @Override
    public int compareTo ( Object o ) {
        if ( ! ( o instanceof WeighedEdge<?> other ) ) {
            throw new IllegalArgumentException();
        }
        return Integer.compare(this.weight, other.weight);
    }
}
