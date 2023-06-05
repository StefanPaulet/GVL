package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class Vertex < LabelType, EdgeType extends Edge < LabelType > > {
    private List < EdgeType > edgeList = new ArrayList<>();

    private LabelType label;

    public Vertex () {
    }

    public Vertex ( LabelType label ) {
        this.label = label;
    }

    public LabelType getLabel ( ) {
        return label;
    }

    public void setLabel ( LabelType label ) {
        this.label = label;
    }

    public List < EdgeType > getEdgeList() {
        return edgeList;
    }

    public void setEdgeList( List < EdgeType > edgeList) {
        this.edgeList = edgeList;
    }

    @Override
    public String toString ( ) {
        StringBuilder stringBuilder = new StringBuilder("Node { label=" + label);

        if ( ! this.edgeList.isEmpty()) {
            stringBuilder.append(", neighbours={ ");
            for (int index = 0; index < this.edgeList.size() - 1; ++index) {
                stringBuilder.append(this.edgeList.get(index)).append(", ");
            }
            stringBuilder.append(this.edgeList.get(this.edgeList.size() - 1)).append(" } ");
        } else {
            stringBuilder.append(" ");
        }

        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex<?, ?> vertex = (Vertex<?, ?>) o;

        return label.equals(vertex.label);
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }
}
