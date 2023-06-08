package graph;

import java.util.List;
import java.util.function.Function;

public class UndirectedWeighedGraph < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends WeighedEdge < VertexLabelType > >
    extends WeighedGraph < VertexLabelType, EdgeType >
    implements UndirectedEdgeAdder < VertexLabelType, EdgeType > {

    public UndirectedWeighedGraph ( List < Vertex < VertexLabelType, EdgeType > > vertices ) {
        super( vertices );
    }


    public UndirectedWeighedGraph ( int nodeCount, Function < Integer, VertexLabelType > nodeLabelGenerator ) {
        super( nodeCount, nodeLabelGenerator );
    }
}
