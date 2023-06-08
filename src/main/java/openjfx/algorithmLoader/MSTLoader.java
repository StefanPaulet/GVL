package openjfx.algorithmLoader;

import graph.Edge;
import graph.Vertex;
import openjfx.Engine;

public class MSTLoader < VertexLabelType extends Comparable < VertexLabelType > , EdgeType extends Edge < VertexLabelType > >
    extends AlgorithmLoader < VertexLabelType, EdgeType > {

    public MSTLoader ( Engine < VertexLabelType, EdgeType > engine ) {
        super( engine );
    }

    @Override
    public String[] getNecessaryFields () {
        return new String[] { "Starting vertex" };
    }

    @Override
    public Object[] convertFieldsFromString ( String[] strings ) {
        Vertex < VertexLabelType, EdgeType > vertex = this.engine.getGraph().getConstVertexList().stream()
            .filter( e -> e.getLabel().toString().equals( strings[0] ) ).findFirst().orElse(null);
        if ( vertex == null ) {
            this.engine.getInfoPanel().setSystemMessage( "There is no vertex with label " + strings[0] );
            return null;
        }

        return new Object[] { vertex };
    }
}
