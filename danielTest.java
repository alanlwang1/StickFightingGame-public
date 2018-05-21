import javafx.application.Application;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.collections.ObservableList;

/**
 * TestGui for the game
 *
 * @author Alan Wang
 * @version 052118
 */
public class danielTest extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage)
    {
        Text start = new Text();
        start.setFont(new Font(45));
        start.setText("Welcome to the Game of Honorable Summoners");
        
        StackPane root = new StackPane(); 
        Canvas canvas = new Canvas(300, 300); 
        GraphicsContext gc = canvas.getGraphicsContext2D(); 
        Image image = new Image("stick figure.png"); 
        gc.drawImage(image, 0, 0); 
        root.getChildren().add(canvas); 
        ObservableList list = root.getChildren();
        list.add(start);
        
        Scene scene = new Scene(root, 600, 300);
        StackPane.setAlignment(start, Pos.CENTER);
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Stick Figure Game");
        primaryStage.show(); 
    }
    
}
