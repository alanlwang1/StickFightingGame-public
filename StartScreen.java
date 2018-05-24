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
public class StartScreen
{
    private Button playButton, closeButton, bestOfThree, bestOfFive;
    private MainStage mainStage;
    private Scene scene;
    public StartScreen(MainStage ms)
    {
        //Assign reference to mainStage
        mainStage = ms;
        
        //make the text box for the main screen (welcome to game...)
        Text start = new Text();
        start.setFont(new Font(45));
        start.setText("Welcome to the Game of Honorable Summoners");
        start.setFill(Color.RED);
        start.setStrokeWidth(1.5);
        start.setStroke(Color.BLACK);
        start.setTextOrigin(VPos.TOP);
        
        //make the picture for the stick figure (might add some other art later)
        Canvas canvas = new Canvas(1800, 900); 
        GraphicsContext gc = canvas.getGraphicsContext2D(); 
        Image image = new Image("stick figure.png"); 
        gc.drawImage(image, 0, 0); 
        //Image img = new Image("stick figure.png");
        //ImageView.setImage(img);
        
        //hello Daniel
        playButton = new Button();
        playButton.setText("Play");
        playButton.setFont(new Font(45));
        playButton.setOnAction
        (e -> {
            CharacterSelect cs = new CharacterSelect(1, mainStage);
            Scene characterSelect = cs.getCSScene();
            ms.changeScene(characterSelect);   
        });
        
        bestOfThree = new Button();
        bestOfThree.setText("Best of Three");
        bestOfThree.setFont(new Font(45));
        bestOfThree.setOnAction(e ->
        {
            
        });
        
        bestOfFive = new Button();
        bestOfFive.setText("Best of Five");
        bestOfFive.setFont(new Font(45));
        bestOfFive.setOnAction(e ->
        {
        });
        
        closeButton = new Button("Close Program");
        closeButton.setOnAction(e -> MainStage.closeProgram());
        closeButton.setFont(new Font(30));
        
        Group root = new Group(); 
        root.getChildren().add(canvas); 
        root.getChildren().add(start);
        root.getChildren().add(playButton);
        root.getChildren().add(closeButton);
        root.getChildren().add(bestOfThree);
        root.getChildren().add(bestOfFive);
        //ObservableList list = root.getChildren();
        //list.add(start);
        //list.add(canvas);
        
        
        scene = new Scene(root, 1800, 900);
        //credit: https://www.youtube.com/watch?v=o-f-rryAHPw
        
        scene.getStylesheets().add("mainGUI.css");
        
        //format the stick figure to be centered by x, a bit up on y
        start.layoutXProperty().bind(scene.widthProperty().subtract(start.prefWidth(-1)).divide(2));
        start.layoutYProperty().bind(scene.heightProperty().divide(2).subtract(200));
        
        //format the picture to be centered by x, maybe down on y
        canvas.layoutXProperty().bind(scene.widthProperty().subtract(800));
        canvas.layoutYProperty().bind(scene.heightProperty().divide(2).subtract(10));
        
        //format the first play button to be centered by x, at the bottom of the screen below the screen
        playButton.layoutXProperty().bind(scene.widthProperty().subtract(start.prefWidth(-1)).divide(2).add(100));
        playButton.layoutYProperty().bind(scene.heightProperty().divide(2).add(200));
        
        //format bo3 button to be at the same y position of play but more towards the center
        bestOfThree.layoutXProperty().bind(scene.widthProperty().subtract(start.prefWidth(-1)).divide(2).add(250));
        bestOfThree.layoutYProperty().bind(scene.heightProperty().divide(2).add(200));
        
        //format bo5 button to be at the same y position of play but more towards the right
        bestOfFive.layoutXProperty().bind(scene.widthProperty().subtract(start.prefWidth(-1)).divide(2).add(580));
        bestOfFive.layoutYProperty().bind(scene.heightProperty().divide(2).add(200));
        


    }
    public Scene getScene()
    {
        return scene;
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
