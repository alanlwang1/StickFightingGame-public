import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
/**
 * Testing error messages to be sent to user-credit to thenewboston
 *
 * @author D.Chen
 * @version b1.0
 */
public class testAlert
{
    public static void display(String title, String message)
    {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Error: " + title);
        window.setMinWidth(250);
        
        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Ok");
        closeButton.setOnAction(e -> window.close());
    }
}
