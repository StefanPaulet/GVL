package graph;

import java.util.List;
import java.util.function.Function;

public abstract class WeighedGraph < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends WeighedEdge < VertexLabelType > >
    extends Graph < VertexLabelType, EdgeType > {
    public WeighedGraph ( List < Vertex < VertexLabelType, EdgeType > > vertices ) {
        super( vertices );
    }

    public WeighedGraph ( int nodeCount, Function < Integer, VertexLabelType > nodeLabelGenerator ) {
        super( nodeCount, nodeLabelGenerator );
    }
}
