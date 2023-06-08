package openjfx.graphDrawer;

import graph.Edge;
import graph.Graph;
import javafx.scene.shape.Circle;
import openjfx.Engine;

public class DirectedBipartiteGraphDrawer < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends Edge < VertexLabelType > >
    extends GraphDrawer < VertexLabelType, EdgeType >
    implements BipartiteVerticesDrawer < VertexLabelType, EdgeType >,
    DirectedEdgeDrawer < VertexLabelType, EdgeType > {

    public DirectedBipartiteGraphDrawer ( Graph < VertexLabelType, EdgeType > graph, Engine engine ) {
        super( graph, engine );
    }

    @Override
    public void addEdge ( Circle firstCircle, Circle secondCircle ) {
        super.addEdge( firstCircle, secondCircle );
        this.initializeMaps();
    }
}
