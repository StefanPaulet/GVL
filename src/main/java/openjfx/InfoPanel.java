package openjfx;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class InfoPanel extends VBox {

    private Text systemMessage = new Text();
    private Text algorithmMessage = new Text();

    public InfoPanel() {
        super();

        this.setSpacing( 200.0 );
        this.setPadding( new Insets( 50, 0, 0, 25 ) );

        this.systemMessage.setWrappingWidth( 150.0 );
        this.algorithmMessage.setWrappingWidth( 150.0 );

        this.getChildren().addAll( systemMessage, algorithmMessage );
    }

    void setSystemMessage( String message ) {
        this.systemMessage.textProperty().set( message );
    }

    void setAlgorithmMessage( String message ) {
        this.algorithmMessage.textProperty().set( message );
    }
}