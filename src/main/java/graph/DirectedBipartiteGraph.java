package graph;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class DirectedBipartiteGraph < VertexLabelType, EdgeType extends Edge < VertexLabelType > >
	extends BipartiteGraph < VertexLabelType, EdgeType >
	implements DirectedEdgeAdder < VertexLabelType, EdgeType > {

	public DirectedBipartiteGraph ( List < Vertex < VertexLabelType, EdgeType > > vertices ) throws NonBipartiteGraphException {
		super ( vertices );
	}

	public DirectedBipartiteGraph ( int nodeCount, Function < Integer, VertexLabelType > nodeLabelGenerator ) {
		super ( nodeCount, nodeLabelGenerator );
	}

	@Override
	public void addEdge ( List < Vertex < VertexLabelType, EdgeType > > vertices, Vertex < VertexLabelType, EdgeType > firstEnd, Vertex < VertexLabelType, EdgeType > secondEnd, Supplier < EdgeType > edgeSupplier ) throws NonExistingVertexException, LoopEdgeException, AlreadyExistingEdgeException, BipartiteEdgeAdditionException {
		super.checkEdgeConstraint ( this.vertexList, firstEnd, secondEnd );
		this.checkBipartitionOnEdgeAddition ( firstEnd, secondEnd );
		DirectedEdgeAdder.super.addEdgeNoexcept ( vertices, firstEnd, secondEnd, edgeSupplier );
	}
}
