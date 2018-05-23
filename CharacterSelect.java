import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.scene.layout.StackPane;
import javafx.collections.ObservableList;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
/**
 * class CharacterSelect - class models character Select screen
 *
 * @author Alan Wang
 * @version 052218
 */
public class CharacterSelect
{
    private final int WIDTH = 1800;
    private final int HEIGHT = 900;
    private Scene scene;
    private Pane pane;
    private Group root;
    private Canvas layer1; 
    private Canvas layer2;
    private Player player1;
    private Player player2;
    private Player[] availablePlayers = {new Normal(), new Ninja(), null, null, null};
    public CharacterSelect(int gameType)
    {
        layer1 = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc1 = layer1.getGraphicsContext2D();
        gc1.setFill(Color.WHITE);
        
        layer2 = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc2 = layer2.getGraphicsContext2D();
        Image notApplicable = new Image("notapplicable.png");
        gc2.setLineWidth(5); 
        for(int i = 0; i < availablePlayers.length; i++)
        {
            if(availablePlayers[i] != null)
                gc2.drawImage(availablePlayers[i].getImage(), 350 * i, 0);
            else
                gc2.drawImage(notApplicable, 350 * i, 0);
            gc2.strokeLine(350 * i, 0, 350 * i, HEIGHT);
        }
        
        
        
        
        pane = new Pane();
        pane.getChildren().add(layer1);
        pane.getChildren().add(layer2);
        layer2.toFront();
        root = new Group(pane);
    }
    public Group getGroup()
    {
        return root; 
    }
}