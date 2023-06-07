package openjfx.graphDrawer;

import graph.*;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import openjfx.DrawingPanel;
import openjfx.Engine;

import java.util.HashMap;
import java.util.Map;

public abstract class GraphDrawer < VertexLabelType, EdgeType extends Edge < VertexLabelType > >
    implements EdgeDrawer < VertexLabelType, EdgeType > ,
    VerticesDrawer < VertexLabelType, EdgeType > {

    protected final Engine engine;
    protected Map < Circle, Vertex < VertexLabelType, EdgeType > > circlesToVerticesMap;
    protected Map < Vertex < VertexLabelType, EdgeType >, Circle > verticesToCirclesMap;
    protected Map < EdgeShape, EdgeType >  edgeShapesToEdgesMap;
    protected Map < EdgeType, EdgeShape >  edgesToEdgeShapesMap;
    protected final Graph < VertexLabelType, EdgeType > graph;

    public GraphDrawer ( Graph < VertexLabelType, EdgeType > graph, Engine engine ) {
        this.graph = graph;
        this.engine = engine;

        this.initializeMaps();
    }

    protected void initializeMaps() {

        this.verticesToCirclesMap = new HashMap <>();
        this.edgeShapesToEdgesMap = new HashMap <>();
        this.edgesToEdgeShapesMap = new HashMap <>();
        this.circlesToVerticesMap = this.computeGraphPoints( graph );
        for ( var entry : this.circlesToVerticesMap.entrySet() ) {
            this.verticesToCirclesMap.put( entry.getValue(), entry.getKey() );
            entry.getKey().setOnMouseClicked( e -> this.engine.handleVertexClick( entry.getKey() ) );
        }
        this.computeAllEdges();

        this.draw();
    }

    private void draw () {

        DrawingPanel drawingPanel = this.engine.getDrawingPanel();
        drawingPanel.getChildren().clear();
        drawingPanel.getChildren().addAll( circlesToVerticesMap.keySet() );
        drawingPanel.getChildren().addAll( ( edgeShapesToEdgesMap.keySet().stream().map( e -> (Node) e ).toList() ) );
    }

    @Override
    public EdgeShape computeEdge ( Circle firstEnd, Circle secondEnd ) {
        var result = EdgeDrawer.super.computeEdge( firstEnd, secondEnd );
        result.setOnMouseClicked( e -> this.engine.handleEdgeClick( result ) );
        return result;
    }

    private void computeAllEdges () {
        if ( graph instanceof DirectedEdgeAdder ) {
            for ( var vertex : graph.getConstVertexList() ) {
                for ( var edge : vertex.getEdgeList() ) {
                    var edgeShape = this.computeEdge( this.verticesToCirclesMap.get( vertex ), this.verticesToCirclesMap.get( edge.getEdgeEnd() ) );
                    edgeShape.setOnMouseClicked( e -> engine.handleEdgeClick( edgeShape ) );
                    this.edgesToEdgeShapesMap.put( edge, edgeShape );
                    this.edgeShapesToEdgesMap.put( edgeShape, edge );
                }
            }
        } else {
            var vertexList = graph.getConstVertexList();
            for ( int index = 0; index < vertexList.size(); ++index ) {
                var firstVertex = vertexList.get( index );
                for ( int jIndex = index + 1; jIndex < vertexList.size(); ++jIndex ) {
                    var secondVertex = vertexList.get( jIndex );
                    var edge = firstVertex.getEdgeList().stream()
                        .filter( e -> e.getEdgeEnd().equals( secondVertex ) )
                        .findFirst()
                        .orElse( null );
                    if ( edge != null ) {
                        var edgeShape = this.computeEdge( this.verticesToCirclesMap.get( firstVertex ), this.verticesToCirclesMap.get( secondVertex ) );
                        edgeShape.setOnMouseClicked( e -> engine.handleEdgeClick( edgeShape ) );
                        this.edgesToEdgeShapesMap.put( edge, edgeShape );
                        this.edgeShapesToEdgesMap.put( edgeShape, edge );
                    }
                }
            }
        }
    }

    public void removeEdge ( EdgeShape edge ) {
        try {
            this.graph.removeEdge( ( Vertex < VertexLabelType, EdgeType > ) this.edgeShapesToEdgesMap.get( edge ).getOwnerVertex(), this.edgeShapesToEdgesMap.get( edge ) );
        } catch ( Exception e ) {
            this.engine.getInfoPanel().setSystemMessage( e.getMessage() );
        }
        this.edgeShapesToEdgesMap.remove( edge );
        this.edgesToEdgeShapesMap.values().remove( edge );
    }


    public void addEdge ( Circle firstCircle, Circle secondCircle ) {

        var firstVertex = this.circlesToVerticesMap.get( firstCircle );
        var secondVertex = this.circlesToVerticesMap.get( secondCircle );
        try {
            this.graph.addEdge(
                firstVertex,
                secondVertex,
                this.engine.edgeSupplier()
            );

            var edgeShape = this.computeEdge( firstCircle, secondCircle );

            if ( graph instanceof DirectedEdgeAdder ) {
                var edge = firstVertex.getEdgeList().stream()
                    .filter( e -> e.getEdgeEnd() == secondVertex )
                    .findFirst().get();

                this.edgesToEdgeShapesMap.put( edge, edgeShape );
                this.edgeShapesToEdgesMap.put( edgeShape, edge );
            } else {
                var firstEdge = firstVertex.getEdgeList().stream()
                    .filter( e -> e.getEdgeEnd() == secondVertex )
                    .findFirst().get();
                var secondEdge = secondVertex.getEdgeList().stream()
                    .filter( e -> e.getEdgeEnd() == firstVertex )
                    .findFirst().get();

                this.edgesToEdgeShapesMap.put( firstEdge, edgeShape );
                this.edgesToEdgeShapesMap.put( secondEdge, edgeShape );
                this.edgeShapesToEdgesMap.put( edgeShape, firstEdge );
            }

            this.engine.getDrawingPanel().getChildren().add( edgeShape );
        } catch ( Exception e ) {
            this.engine.getInfoPanel().setSystemMessage( e.getMessage() );
        }
    }
}
