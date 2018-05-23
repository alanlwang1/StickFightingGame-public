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
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;

/**
 * TestGui for the game
 *
 * @author Alan Wang
 * @version 052118
 */
public class danielTest extends Application 
{
    private Button playButton;
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
        start.setFill(Color.RED);
        start.setStrokeWidth(1.5);
        start.setStroke(Color.BLACK);
        start.setTextOrigin(VPos.TOP);
        
        
        Canvas canvas = new Canvas(1800, 900); 
        GraphicsContext gc = canvas.getGraphicsContext2D(); 
        Image image = new Image("stick figure.png"); 
        gc.drawImage(image, 0, 0); 
        //Image img = new Image("stick figure.png");
        //ImageView.setImage(img);
        
        playButton = new Button();
        playButton.setText("Play");
        playButton.setOnAction
        (e -> {
            CharacterSelect cs = new CharacterSelect(1);
            primaryStage.getScene().setRoot(cs.getGroup());     
        });
        Group root = new Group(); 
        root.getChildren().add(canvas); 
        root.getChildren().add(start);
        root.getChildren().add(playButton);
        //ObservableList list = root.getChildren();
        //list.add(start);
        //list.add(canvas);
        
        
        Scene scene = new Scene(root, 1800, 900);
        //credit: https://www.youtube.com/watch?v=o-f-rryAHPw
        start.layoutXProperty().bind(scene.widthProperty().subtract(start.prefWidth(-1)).divide(2));
        start.layoutYProperty().bind(scene.heightProperty().divide(2).subtract(200));
        
        canvas.layoutXProperty().bind(scene.widthProperty().subtract(800));
        canvas.layoutYProperty().bind(scene.heightProperty().divide(2).subtract(200));
        
        playButton.layoutXProperty().bind(scene.widthProperty().subtract(start.prefWidth(-1)).divide(2));
        playButton.layoutYProperty().bind(scene.heightProperty().divide(2).subtract(200));
        
        primaryStage.setTitle("Stick Figure Game");
        primaryStage.setScene(scene);
        primaryStage.show(); 
    }
    
    /**
     * Credit: https://stackoverflow.com/questions/32781362/centering-an-image-in-an-imageview?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
     
    public void centerImage() {
        Image img = imageView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = imageView.getFitWidth() / img.getWidth();
            double ratioY = imageView.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if(ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            imageView.setX((imageView.getFitWidth() - w) / 2);
            imageView.setY((imageView.getFitHeight() - h) / 2);

        }
    }
    */
}
