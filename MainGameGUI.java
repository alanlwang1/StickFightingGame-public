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
 * Main Gui for the actual game
 *
 * @author Alan Wang
 * @version 052118
 */
public class MainGameGUI 
{
    private Game game;
    private MainStage mainStage;
    private Scene scene;
    private Canvas canvas;
    private Player player1;
    private Player player2;
    public MainGameGUI(Game g, MainStage ms) 
    {
        game = g;
        mainStage = ms;
        player1 = game.getPlayer1();
        player2 = game.getPlayer2();
        
        Canvas canvas = new Canvas(1800, 900); 
        Group root = new Group(canvas);
        scene = new Scene(root, 1800, 900); 
        
        
        
        drawPhase();
        runGame(); 
    }
    public Scene getScene()
    {
        return scene;
    }
    public void drawPhase()
    {
        
    }
    public void runGame()
    {
        
    }
    
}
