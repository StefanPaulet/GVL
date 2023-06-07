package openjfx.graphDrawer;

import javafx.scene.Group;
import javafx.scene.Node;

public abstract class EdgeShape extends Group {
    public EdgeShape ( Node... nodes ) {
        super( nodes );
        this.setOnMouseClicked( e -> this.select() );
    }


    public abstract void select();
    public abstract void deselect();
}
