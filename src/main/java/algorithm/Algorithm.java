package algorithm;

import graph.Edge;
import openjfx.Engine;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Algorithm < VertexLabelType extends Comparable < VertexLabelType >, EdgeType extends Edge < VertexLabelType > >
    implements Runnable {

    public Lock pauseLock = new ReentrantLock();

    protected final Engine < VertexLabelType, EdgeType > engine;

    protected int delayMillis = 2000;

    public Algorithm ( Engine < VertexLabelType, EdgeType > engine ) {
        this.engine = engine;
    }

    protected void displayMessage ( String message ) {
        this.engine.getInfoPanel().setAlgorithmMessage( message );
        try {
            Thread.sleep( this.delayMillis );
        } catch ( Exception e ) {
            this.pauseLock.lock();
            this.pauseLock.unlock();
        }
    }

    public int getDelayMillis () {
        return delayMillis;
    }

    public void setDelayMillis ( int delayMillis ) {
        this.delayMillis = delayMillis;
    }

    public abstract void setStartCondition ( Object ... startConditions );

    public abstract void run();
}
