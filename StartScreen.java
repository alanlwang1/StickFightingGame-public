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
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.geometry.HPos;
import javafx.geometry.VPos;

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
    private GridPane grid;
    private final static double MIN_TILE_SIZE = 5;
    private final static double MAX_TILE_SIZE = Double.MAX_VALUE;
    public StartScreen(MainStage ms)
    {
        //Assign reference to mainStage
        mainStage = ms;
        
        grid = new GridPane();
        //grid.setPadding(new Insets(10,10,10,10));
        //grid.setGridLinesVisible(true);
        
        //make the text box for the main screen (welcome to game...)
        Text start = new Text();
        start.setFont(new Font(90));
        start.setText("Welcome to the Game");
        start.setFill(Color.RED);
        start.setStrokeWidth(1.5);
        start.setStroke(Color.BLACK);
        start.setTextOrigin(VPos.TOP);
        start.setId("start");
        grid.setConstraints(start, 1, 2);
        grid.setHalignment(start, HPos.CENTER);
        //grid.setValignment(start, VPos.CENTER);
        
        
        Text startCont = new Text();
        startCont.setFont(new Font(90));
        startCont.setText("of Honorable Summoners");
        startCont.setFill(Color.RED);
        startCont.setStrokeWidth(1.5);
        startCont.setStroke(Color.BLACK);
        grid.setConstraints(startCont, 1, 3);
        grid.setHalignment(start, HPos.CENTER);
        
        Text alignmentBox = new Text();
        alignmentBox.setFont(new Font(45));
        alignmentBox.setText("Align");

        
  
        
        //make the picture for the stick figure (might add some other art later)
        /**
        Canvas canvas = new Canvas(1800, 900); 
        GraphicsContext gc = canvas.getGraphicsContext2D(); 
        Image image = new Image("stick figure.png"); 
        gc.drawImage(image, 0, 0);
        **/
        Image image = new Image("stick figure.png");
        ImageView stickFigure = new ImageView(image);
        stickFigure.setFitHeight(300);
        stickFigure.setFitWidth(300);
        grid.setConstraints(stickFigure, 1,6);
        
        grid.setHalignment(stickFigure, HPos.CENTER);
        //Image img = new Image("stick figure.png");
        //ImageView.setImage(img);
        
        /*
        //hello Daniel
        playButton = new Button();
        playButton.setText("Play");
        playButton.setFont(new Font(45));
        playButton.setMinSize(300, 100);
        
        //playButton.setMaxSize(MAX_TILE_SIZE, MAX_TILE_SIZE);
        playButton.setOnAction(e -> 
        {
            
        });
        */
        //grid.setConstraints(playButton, 1, 8);
        
        bestOfThree = new Button();
        bestOfThree.setText("Play Best of Three");
        bestOfThree.setFont(new Font(45));
        bestOfThree.setMinSize(400, 100);
        bestOfThree.setOnAction(e ->
        {
            CharacterSelect cs = new CharacterSelect(1, mainStage);
            Scene csScene = cs.getCSScene(); 
            mainStage.changeScene(csScene);
        });
        grid.setConstraints(bestOfThree, 1, 8);
        grid.setHalignment(bestOfThree, HPos.LEFT);
        
        bestOfFive = new Button();
        bestOfFive.setText("Play Best of Five");
        bestOfFive.setFont(new Font(45));
        bestOfFive.setMinSize(400, 100);
        bestOfFive.setOnAction(e ->
        {
            CharacterSelect cs = new CharacterSelect(2, mainStage);
            Scene csScene = cs.getCSScene(); 
            mainStage.changeScene(csScene);
        });
        grid.setConstraints(bestOfFive, 1, 8);
        grid.setHalignment(bestOfFive, HPos.RIGHT);
        
        closeButton = new Button("Quit");
        closeButton.setOnAction(e -> MainStage.closeProgram());
        closeButton.setFont(new Font(30));
        closeButton.setMinSize(210,50);
        grid.setConstraints(closeButton, 0, 0);
       
        
        /**
        Group root = new Group(); 
        root.getChildren().add(canvas); 
        root.getChildren().add(start);
        root.getChildren().add(playButton);
        root.getChildren().add(closeButton);
        root.getChildren().add(bestOfThree);
        root.getChildren().add(bestOfFive);
        root.setId("pane"); //credit: https://stackoverflow.com/questions/9738146/javafx-how-to-set-scene-background-image?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
        **/
        //ObservableList list = root.getChildren();
        
        //list.add(start);
        //list.add(canvas);
        
        
        grid.setHgap(0);
        grid.setVgap(0);
        grid.getChildren().addAll(start, startCont, bestOfThree, bestOfFive, closeButton, stickFigure);
        
        grid.setId("pane");
       
        
        
        scene = new Scene(grid, 1800, 900);
       
        
        
        //scene.getStylesheets().addAll(this.getClass().getResource("background.css").toExternalForm());
        
         //credit: https://www.youtube.com/watch?v=o-f-rryAHPw
        //format the stick figure to be centered by x, a bit up on y
        /**
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
        **/
        
        scene.getStylesheets().addAll(this.getClass().getResource("background.css").toExternalForm());
        

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
