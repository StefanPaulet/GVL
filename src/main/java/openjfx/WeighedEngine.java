package openjfx;

import graph.WeighedEdge;

import java.util.function.Supplier;

public class WeighedEngine extends Engine < Integer, WeighedEdge < Integer > > {

    static final String graphDrawerClassName = "Weighed";
    public WeighedEngine ( DrawingPanel drawingPanel, InfoPanel infoPanel ) {
        super( drawingPanel, infoPanel );
    }

    @Override
    public Supplier < WeighedEdge < Integer > > edgeSupplier () {
        return () -> new WeighedEdge <>( 0 );
    }

    @Override
    protected String getGraphDrawerType () {
        return "Weighed";
    }
}
