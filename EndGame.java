import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.Scene;
import java.io.File;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
/**
 * Write a description of class EndGame here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class EndGame
{
    private MediaPlayer endMusic;
    private Scene endScene;
    private GridPane grid;
    private MainStage mainStage;
    private Game endGame;
    public EndGame(MainStage ms, int winnerID)
    {
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
        //credit: https://stackoverflow.com/questions/24347658/getting-a-mp3-file-to-play-using-javafx?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
        endGame = game;
        String uri = new File("587069_-Endgame-.mp3").toURI().toString(); //credit for music: Waterflame
=======
=======
>>>>>>> d37ffa88dbe1c17724a94c97058f9d3401d3f394
=======
>>>>>>> d37ffa88dbe1c17724a94c97058f9d3401d3f394
=======
>>>>>>> d37ffa88dbe1c17724a94c97058f9d3401d3f394
        //credit: https://stackoverflow.com/questions/24347658/getting-a-mp3-file-to-play-using-javafx?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_
        String uri = new File("587069_-Endgame-.mp3").toURI().toString();
>>>>>>> d37ffa88dbe1c17724a94c97058f9d3401d3f394
        MediaPlayer endMusic = new MediaPlayer(new Media(uri));
        endMusic.play();
        
        mainStage = ms;
        grid = new GridPane();
        grid.setGridLinesVisible(true);
        
        Text showWinner = new Text();
        showWinner.setText("Player " + winnerID + " is the winner!");
        showWinner.setFont(new Font(150));
        grid.setConstraints(showWinner, 1, 1);
        grid.setHalignment(showWinner, HPos.CENTER);
        
        Button quit = new Button();
        quit.setText("Quit");
        quit.setOnAction(e -> MainStage.closeProgram());
        quit.setFont(new Font(90));
        grid.setConstraints(quit,1, 2);
        grid.setHalignment(quit, HPos.CENTER);
        
        grid.getChildren().addAll(showWinner, quit);
        grid.setId("pane");
        
        endScene = new Scene(grid, 1800, 900);
        endScene.getStylesheets().addAll(this.getClass().getResource("background.css").toExternalForm());
        
        
    }
    public Scene getScene()
    {
        return endScene;
    }
}
