package openjfx;

import graph.WeighedEdge;

import java.util.function.Supplier;

public class WeighedEngine extends Engine < Integer, WeighedEdge < Integer > > {

    public WeighedEngine ( DrawingPanel drawingPanel, InfoPanel infoPanel ) {
        super( drawingPanel, infoPanel );
    }

    @Override
    public Supplier < WeighedEdge < Integer > > edgeSupplier () {
        return () -> new WeighedEdge <>( 0);
    }
}
