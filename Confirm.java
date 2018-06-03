import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
/**
 * Testing confirm/deny messages to be sent to user-credit to thenewboston
 *
 * @author D.Chen
 * @version b1.0
 */
public class Confirm
{
    private static boolean answer;
    public static boolean display(String title, String message)
    {
        Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Are you sure you want to " + title + "?");
        window.setMinWidth(250);
        
        Label label = new Label();
        label.setText(message);
        
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
        
        yesButton.setOnAction(e -> 
        {
            answer = true;
            window.close();
        });
        
        noButton.setOnAction(e ->
        {
            answer = false;
            window.close();
        });
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        
        return answer;
    }
}
