package openjfx;

import graph.Edge;

import java.util.function.Supplier;

public class SimpleEngine extends Engine < Integer, Edge < Integer > > {
    public SimpleEngine ( DrawingPanel drawingPanel, InfoPanel infoPanel ) {
        super( drawingPanel, infoPanel );
    }

    @Override
    public Supplier < Edge < Integer > > edgeSupplier () {
        return Edge :: new;
    }

    @Override
    protected String getGraphDrawerType () {
        return "";
    }
}
