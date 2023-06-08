package graph;

import java.util.List;
import java.util.function.Function;

public class UndirectedGraph < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends Edge < VertexLabelType > >
    extends Graph < VertexLabelType, EdgeType >
    implements UndirectedEdgeAdder < VertexLabelType, EdgeType > {

    public UndirectedGraph ( List < Vertex < VertexLabelType, EdgeType > > vertices ) {
        super( vertices );
    }


    public UndirectedGraph ( int nodeCount, Function < Integer, VertexLabelType > nodeLabelGenerator ) {
        super( nodeCount, nodeLabelGenerator );
    }
}
