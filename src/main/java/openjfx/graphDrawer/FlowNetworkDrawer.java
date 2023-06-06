package openjfx.graphDrawer;

import graph.FlowNetwork;
import graph.FlowNetworkEdge;
import graph.Graph;
import graph.Vertex;

import java.util.Map;

public class FlowNetworkDrawer < VertexLabelType, EdgeType extends FlowNetworkEdge < VertexLabelType > >
    extends GraphDrawer < VertexLabelType, EdgeType >
    implements DirectedEdgeDrawer < VertexLabelType, EdgeType >,
    SimpleVerticesDrawer < VertexLabelType, EdgeType > {

    private static final double SOURCE_X = 125.0;
    private static final double SOURCE_Y = MAIN_CIRCLE_Y;
    private static final double SINK_X = 825.0;
    private static final double SINK_Y = MAIN_CIRCLE_Y;

    public FlowNetworkDrawer ( Graph < VertexLabelType, EdgeType > graph ) {
        super( graph );
    }

    @Override
    public Map < Vertex < VertexLabelType, EdgeType >, Point > computeGraphPoints ( Graph < VertexLabelType, EdgeType > graph ) {
        if ( ! ( graph instanceof FlowNetwork < VertexLabelType, EdgeType > flowNetwork ) ) {
            return null;
        }
        var resultMap = SimpleVerticesDrawer.super.computeGraphPoints( graph );
        resultMap.put( flowNetwork.getSource(), new Point( true, SOURCE_X, SOURCE_Y ));
        resultMap.put( flowNetwork.getSink(), new Point( true, SINK_X, SINK_Y ));

        return resultMap;
    }
}
