import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.Scene;
import java.io.File;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
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
    public EndGame(MainStage ms)
    {
        //credit: https://stackoverflow.com/questions/24347658/getting-a-mp3-file-to-play-using-javafx?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
        String uri = new File("C:\\Users\\Daniel\\Desktop\\AP Comp Sci\\APCSFinalProject\\587069_-Endgame-.mp3").toURI().toString();
        MediaPlayer endMusic = new MediaPlayer(new Media(uri));
        
        mainStage = ms;
        grid.setGridLinesVisible(true);
        
        Text showWinner = new Text();
        showWinner.setText(Game.getWinner().getName() + " is the winner!");
        showWinner.setFont(new Font(90));
        
    }
    public Scene getScene()
    {
        return endScene;
    }
}
