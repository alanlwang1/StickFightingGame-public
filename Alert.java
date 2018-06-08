//import needed packages
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * This class makes a pop-up window with an error message when the user tries to break the
 * program. 
 * -credit to thenewboston
 *
 * @author D.Chen
 * @version b1.0
 */
public class Alert extends PopUp
{
    /**
     * Displays the pop up window.
     * @param title the title displayed on the window
     * @param message the message displayed on the window
     */
    public static void display(String title, String message)
    {
        //instantiate stage
        Stage window = new Stage();
        //format stage
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("About " + title);
        window.setMinWidth(getWidth());
        window.setMinHeight(getHeight());
        
        //instantiate label with the message
        Label label = new Label();
        label.setText(message);
        
        //make a close button that returns true when clicked
        Button closeButton = new Button("Ok");
        closeButton.setOnAction(e -> window.close());
        
        //instantiate a VBox and add all nodes in there
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        //position all elements in VBox to be centered
        layout.setAlignment(Pos.CENTER);
        
        //instantiate the scene
        Scene scene = new Scene(layout);
        //change the scene (or overlays a scene)
        window.setScene(scene);
        //wait for them to click one button
        window.showAndWait();
    }
}
