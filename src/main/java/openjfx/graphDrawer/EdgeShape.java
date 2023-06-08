package openjfx.graphDrawer;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.EventListener;

public abstract class EdgeShape extends Group {
    public EdgeShape ( Node... nodes ) {
        super( nodes );
        this.setOnMouseClicked( e -> this.select() );
    }


    public abstract void select();
    public abstract void deselect();
    public abstract void addLabel( String textString, ChangeListener onLabelChangeEvent );
}
