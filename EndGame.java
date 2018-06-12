import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.Scene;
import java.io.File;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
/**
 * EndGame displays the last scene of MainStage, or when one player wins. 
 *
 * @author Daniel Chen
 */
public class EndGame
{
    //instantiated needed variables
    //private MediaPlayer endMusic;
    private Scene endScene;
    private GridPane grid;
    private Game endGame;
    /**
     * Constructor - creates a EndGame object with a reference to MainStage
     * 
     * @param ms - the reference to a MainStage object
     * @param game - the finished game
     */
    public EndGame(Game game)
    {
        //instantiate needed variables
        endGame = game;
        game.getMatchWinner();
        grid = new GridPane();
        //get the media file linked to the program and play it upon loading
        //credit: https://stackoverflow.com/questions/24347658/getting-a-mp3-file-to-play-using-javafx?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
        //String uri = new File("587069_-Endgame-.mp3").toURI().toString(); //credit for music: Waterflame
        //MediaPlayer endMusic = new MediaPlayer(new Media(uri));
       // endMusic.play();
       
        //create a text box with the winner's name in it
        Text showWinner = new Text();
        showWinner.setText("Player " + game.getWinnerId() + " is the winner!");
        showWinner.setFont(new Font(150));
        //position the text box
        grid.setConstraints(showWinner, 1, 1);
        grid.setHalignment(showWinner, HPos.CENTER);
        
        //create a text box with the winner's name in it
        Text align = new Text();
        align.setText("Hi  ");
        align.setFont(new Font(118));
        align.setId("transtext");
        //position the text box
        grid.setConstraints(align, 0, 1);
        grid.setHalignment(align, HPos.CENTER);
        
        //creates an image with the winner's image on it
        Image image = new Image(game.getMatchWinner().getImageURL());
        ImageView winner = new ImageView(image);
        //format the text box
        winner.setFitHeight(300);
        winner.setFitWidth(300);
        //position the text box
        grid.setConstraints(winner, 1,2);
        grid.setHalignment(winner, HPos.CENTER);
        
        //creates a button that allows user to gracefully terminate program
        Button quit = new Button();
        quit.setText("Quit");
        //request to close program when clicked
        quit.setOnAction(e -> MainStage.closeProgram());
        quit.setFont(new Font(90));
        //position the text box
        grid.setConstraints(quit,1, 3);
        grid.setHalignment(quit, HPos.CENTER);
        
        //adds all nodes to the gridpane
        grid.getChildren().addAll(showWinner, quit, winner, align);
        //allow css to show background in the pane
        grid.setId("pane");
        
        //instantiate the scene
        endScene = new Scene(grid, 1800, 900);
        
        //allow css attributes inside class
        endScene.getStylesheets().addAll(this.getClass().getResource("background.css").toExternalForm());
    }
    /**
     * Returns the scene used in this class
     * @return the scene used in this class
     */
    public Scene getScene()
    {
        return endScene;
    }
}
