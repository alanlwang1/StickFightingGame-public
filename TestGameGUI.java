import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

/**
 * TestGui for the game
 *
 * @author Alan Wang
 * @version 052118
 */
public class TestGameGUI extends Application
{
    public TestGameGUI()
    {
    }
    
    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Test");
        Group root = new Group(); 
        Canvas canvas = new Canvas(300, 300); 
        GraphicsContext gc = canvas.getGraphicsContext2D(); 
        Image image = new Image("stick figure.png"); 
        gc.drawImage(image, 150, 150); 
        root.getChildren().add(canvas); 
        primaryStage.setScene(new Scene(root));
        primaryStage.show(); 
    }
    
}
