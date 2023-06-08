package openjfx.algorithmLoader;

import graph.Edge;
import openjfx.Engine;

public abstract class AlgorithmLoader < VertexLabelType extends Comparable < VertexLabelType > , EdgeType extends Edge < VertexLabelType > > {

    protected Engine < VertexLabelType, EdgeType > engine;

    public AlgorithmLoader ( Engine < VertexLabelType, EdgeType > engine ) {
        this.engine = engine;
    }

    public abstract String[] getNecessaryFields();

    public abstract Object[] convertFieldsFromString(String[] strings);
}
