package openjfx.graphDrawer;

import graph.FlowNetwork;
import graph.FlowNetworkEdge;
import graph.Graph;
import graph.Vertex;
import javafx.scene.shape.Circle;
import openjfx.Engine;

import java.util.Map;

public class FlowNetworkDrawer < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends FlowNetworkEdge < VertexLabelType > >
    extends GraphDrawer < VertexLabelType, EdgeType >
    implements DirectedEdgeDrawer < VertexLabelType, EdgeType >,
    SimpleVerticesDrawer < VertexLabelType, EdgeType > {

    private static final double SOURCE_X = 125.0;
    private static final double SOURCE_Y = MAIN_CIRCLE_Y;
    private static final double SINK_X = 825.0;
    private static final double SINK_Y = MAIN_CIRCLE_Y;

    public FlowNetworkDrawer ( Graph < VertexLabelType, EdgeType > graph, Engine engine ) {
        super( graph, engine );
    }

    @Override
    public Map < Circle, Vertex < VertexLabelType, EdgeType > > computeGraphPoints ( Graph < VertexLabelType, EdgeType > graph ) {
        if ( !( graph instanceof FlowNetwork < VertexLabelType, EdgeType > flowNetwork ) ) {
            return null;
        }
        var resultMap = SimpleVerticesDrawer.super.computeGraphPoints( graph );
        resultMap.put( new Circle( SOURCE_X, SOURCE_Y, NODE_RADIUS ), flowNetwork.getSource() );
        resultMap.put( new Circle( SINK_X, SINK_Y, NODE_RADIUS ), flowNetwork.getSink() );

        return resultMap;
    }
}
