package openjfx;

import graph.Edge;
import graph.WeighedEdge;

public class EngineFactory {

    public < EdgeType extends Edge < Integer > > Engine < Integer, EdgeType > getEngine (
        Class < EdgeType > edgeClass,
        DrawingPanel drawingPanel,
        InfoPanel infoPanel
    ) {
        if ( (Object)edgeClass == Edge.class ) {
            return ( Engine < Integer, EdgeType > ) new SimpleEngine(drawingPanel, infoPanel);
        }
        if ( (Object)edgeClass == WeighedEdge.class ) {
            return ( Engine < Integer, EdgeType > ) new WeighedEngine(drawingPanel, infoPanel);
        }
        return null;
    }
}
