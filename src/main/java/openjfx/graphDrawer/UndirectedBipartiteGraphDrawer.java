package openjfx.graphDrawer;

import graph.Edge;
import graph.Graph;
import javafx.scene.shape.Circle;
import openjfx.Engine;

public class UndirectedBipartiteGraphDrawer < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends Edge < VertexLabelType > >
    extends GraphDrawer < VertexLabelType, EdgeType >
    implements BipartiteVerticesDrawer < VertexLabelType, EdgeType >,
    UndirectedEdgeDrawer < VertexLabelType, EdgeType > {

    public UndirectedBipartiteGraphDrawer ( Graph < VertexLabelType, EdgeType > graph, Engine engine ) {
        super( graph, engine );
    }


    @Override
    public void addEdge ( Circle firstCircle, Circle secondCircle ) {
        super.addEdge( firstCircle, secondCircle );
        this.initializeMaps();
    }
}
