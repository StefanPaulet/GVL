package openjfx.graphDrawer;

import graph.DirectedEdgeAdder;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import openjfx.DrawingPanel;
import openjfx.Engine;

import java.util.HashMap;
import java.util.Map;

import static openjfx.DrawingPanel.CANVAS_HEIGHT;
import static openjfx.DrawingPanel.CANVAS_WIDTH;

public abstract class GraphDrawer < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends Edge < VertexLabelType > >
    implements EdgeDrawer < VertexLabelType, EdgeType >,
    VerticesDrawer < VertexLabelType, EdgeType > {

    protected final Engine < VertexLabelType, EdgeType > engine;
    protected Map < Circle, Vertex < VertexLabelType, EdgeType > > circlesToVerticesMap;
    protected Map < Vertex < VertexLabelType, EdgeType >, Circle > verticesToCirclesMap;
    protected Map < EdgeShape, EdgeType > edgeShapesToEdgesMap;
    protected Map < EdgeType, EdgeShape > edgesToEdgeShapesMap;
    protected final Graph < VertexLabelType, EdgeType > graph;

    public GraphDrawer ( Graph < VertexLabelType, EdgeType > graph, Engine < VertexLabelType, EdgeType > engine ) {
        this.graph = graph;
        this.engine = engine;

        this.initializeMaps();
    }

    public void initializeMaps () {

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

    public void draw () {

        DrawingPanel drawingPanel = this.engine.getDrawingPanel();
        drawingPanel.getChildren().clear();

        var selectionRect = new Rectangle( 0, 0, CANVAS_WIDTH, CANVAS_HEIGHT );
        selectionRect.setFill( Color.WHEAT );
        selectionRect.setOpacity( 0 );
        drawingPanel.getChildren().add( selectionRect );

        this.circlesToVerticesMap.keySet().forEach( e -> e.setFill( Color.BLACK ) );
        this.edgeShapesToEdgesMap.keySet().forEach( EdgeShape :: deselect );

        drawingPanel.getChildren().addAll( circlesToVerticesMap.keySet() );
        drawingPanel.getChildren().addAll( ( edgeShapesToEdgesMap.keySet().stream().map( e -> ( Node ) e ).toList() ) );
        drawingPanel.getChildren().addAll( this.computeVertexLabels( this.graph ) );
    }

    @Override
    public EdgeShape computeEdge ( Circle firstEnd, Circle secondEnd ) {
        var result = EdgeDrawer.super.computeEdge( firstEnd, secondEnd );
        result.setOnMouseClicked( e -> this.engine.handleEdgeClick( result ) );
        return result;
    }

    protected void computeAllEdges () {
        if ( graph instanceof DirectedEdgeAdder ) {
            for ( var vertex : graph.getConstVertexList() ) {
                for ( var edge : vertex.getEdgeList() ) {
                    this.mapEdge( this, vertex, ( Vertex < VertexLabelType, EdgeType > ) edge.getEdgeEnd() );
                }
            }
        } else {
            for ( var vertex : graph.getConstVertexList() ) {
                for ( var edge : vertex.getEdgeList() ) {
                    if ( vertex.compareTo( ( Vertex < VertexLabelType, EdgeType > ) edge.getEdgeEnd() ) < 0 ) {
                        this.mapEdge( this, vertex, ( Vertex < VertexLabelType, EdgeType > ) edge.getEdgeEnd() );
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
            EdgeType newEdge = this.mapEdge( this, firstVertex, secondVertex );
            this.engine.getDrawingPanel().getChildren().add( this.edgesToEdgeShapesMap.get( newEdge ) );
        } catch ( Exception e ) {
            this.engine.getInfoPanel().setSystemMessage( e.getMessage() );
        }
    }


    public void selectVertex ( Vertex < VertexLabelType, EdgeType > vertex, Paint color ) {
        this.verticesToCirclesMap.get( vertex ).setFill( color );
    }

    public void selectEdge ( EdgeType edge, String color ) {
        this.edgesToEdgeShapesMap.get( edge ).select(color);
    }

    public void deselectEdge ( EdgeType edge ) {

        EdgeShape edgeShape = this.edgesToEdgeShapesMap.get( edge );
        edgeShape.deselect();
    }
}
