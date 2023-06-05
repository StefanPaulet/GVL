package graph;

import java.util.List;

public class FlowNetwork < VertexLabelType, EdgeType extends FlowNetworkEdge < VertexLabelType > >
    extends Graph < VertexLabelType, EdgeType >
    implements DirectedEdgeAdder < VertexLabelType, EdgeType > {

    private final Vertex < VertexLabelType, EdgeType > source = new Vertex<>();
    private final Vertex < VertexLabelType, EdgeType > sink = new Vertex<>();

    @Override
    protected void checkEdgeConstraint (
        Vertex < VertexLabelType, EdgeType > firstEnd,
        Vertex < VertexLabelType, EdgeType > secondEnd
    ) throws NonExistingVertexException, LoopEdgeException, BipartiteEdgeAdditionException {

        if ( firstEnd != this.source && firstEnd != this.sink && ! this.vertexList.contains(firstEnd) ) {
            throw new NonExistingVertexException(firstEnd);
        }

        if ( secondEnd != this.source && secondEnd != this.sink && ! this.vertexList.contains(secondEnd) ) {
            throw new NonExistingVertexException(secondEnd);
        }

        if ( firstEnd.equals(secondEnd) ) {
            throw new LoopEdgeException(firstEnd);
        }
    }
}
