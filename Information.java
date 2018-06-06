import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
/**
 * Testing confirm/deny messages to be sent to user-credit to thenewboston (modified Error class)
 *
 * @author D.Chen
 * @version b1.0
 */
public class Information
{
    private static boolean answer;
    private static int width;
    private static int height;
    public static void display(String title, String message)
    {
        Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(width);
        window.setMinHeight(height);
        
        Label label = new Label();
        label.setText(message);
        
        Button okayButton = new Button("Ok");
        
        okayButton.setOnAction(e -> 
        {
            answer = true;
            window.close();
        });
        
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, okayButton);
        layout.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        
    }
    public static void setWidth(int x)
    {
        width = x;
    }
    public static void setHeight(int x)
    {
        height = x;
    }
}
