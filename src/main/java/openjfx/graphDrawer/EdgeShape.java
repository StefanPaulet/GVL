package openjfx.graphDrawer;

import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Paint;

import java.util.function.UnaryOperator;

public abstract class EdgeShape extends Group {
    public EdgeShape ( Node... nodes ) {
        super( nodes );
        this.setOnMouseClicked( e -> this.select() );
    }


    public void select () {
        this.select("red");
    };

    public abstract void select ( String color );

    public abstract void deselect ();

    public abstract String getColor();

    public void addLabel ( String textString, ChangeListener < String > onLabelChangeEvent ) {

        TextField text = new TextField( textString );
        Point labelPoint = this.getLabelPoint();
        text.relocate( labelPoint.x, labelPoint.y );

        text.setPrefWidth( 40 );
        this.getChildren().add( text );

        UnaryOperator < TextFormatter.Change > integerFilter = change -> {
            String input = change.getControlNewText();
            if ( input.matches( "^[1-9]\\d*$|0" ) ) {
                if ( !input.equals( "" ) && Integer.parseInt( input ) > 99 ) {
                    return null;
                }
                return change;
            }
            return null;
        };
        text.setTextFormatter( new TextFormatter <>( integerFilter ) );
        text.textProperty().addListener( onLabelChangeEvent );
    }

    protected abstract Point getLabelPoint ();
}
