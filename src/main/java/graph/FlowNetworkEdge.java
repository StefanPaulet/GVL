package graph;

public class FlowNetworkEdge < VertexLabelType extends Comparable < VertexLabelType > >
    extends Edge < VertexLabelType > {

    private int capacity;
    private int flow = 0;

    public FlowNetworkEdge ( int capacity ) {
        if ( capacity < 0 ) {
            throw new IllegalArgumentException( "Edge capacity must be positive" );
        }
        this.capacity = capacity;
    }

    public FlowNetworkEdge ( Vertex < VertexLabelType, Edge < VertexLabelType > > edgeEnd, int capacity ) {
        super( edgeEnd );
        this.capacity = capacity;
    }


    public int getCapacity () {
        return capacity;
    }

    public void setCapacity ( int capacity ) {
        if ( capacity < 0 ) {
            throw new IllegalArgumentException( "Edge capacity must be positive" );
        }
        this.capacity = capacity;
    }

    public int getFlow () {
        return flow;
    }

    public void setFlow ( int flow ) {
        this.flow = flow;
    }
}
