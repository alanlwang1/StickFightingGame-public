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
import javafx.scene.input.KeyCode;
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
    private Canvas layer;
    private Button confirmButton; 
    private Image cursor1 = new Image("cursor1.png");
    private Image cursor2 = new Image("cursor2.png");
    private ImageView cursorOne = new ImageView(cursor1);
    private ImageView cursorTwo = new ImageView(cursor2); 
    private Player player1;
    private Player player;
    private Player[] availablePlayers = {new Normal(), new Ninja(), null, null, null};
    public CharacterSelect(int gameType)
    {        
        layer = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = layer.getGraphicsContext2D();
        Image notApplicable = new Image("notapplicable.png");
        gc.setLineWidth(5); 
        for(int i = 0; i < availablePlayers.length; i++)
        {
            if(availablePlayers[i] != null)
                gc.drawImage(availablePlayers[i].getImage(), 350 * i, 0);
            else
                gc.drawImage(notApplicable, 350 * i, 0);
            gc.strokeLine(350 * i, 0, 350 * i, HEIGHT);
        }
        cursorOne.relocate(0 + 100, HEIGHT - 200);
        cursorTwo.relocate(WIDTH - 300, HEIGHT - 100);
        
        confirmButton = new Button("Confirm Characters");
        
        pane = new Pane();
        pane.getChildren().add(layer);
        root = new Group(pane, cursorOne, cursorTwo);
        scene = new Scene(root, 1800, 900);
        scene.setOnKeyPressed(ke -> {
            KeyCode keyCode = ke.getCode();
            if(keyCode.equals(KeyCode.A))
            {
                moveCursor(cursorOne , -350);
                return;
            }
            if (keyCode.equals(KeyCode.D))
            {
                moveCursor(cursorOne, 350);
                return;
            }
            if(keyCode.equals(KeyCode.LEFT))
            {
                moveCursor(cursorTwo, -350);
                return;
            }
            if(keyCode.equals(KeyCode.RIGHT))
            {
                moveCursor(cursorTwo, 350);
                return;
            }
        });
    }
    public Group getGroup()
    {
        return root; 
    }
    public Scene getScene()
    {
        return scene;
    }
    public void moveCursor(ImageView cursor, double deltaX)
    {
        double newX = Math.max(cursor.getLayoutX() + deltaX, 100);;
        if(newX > WIDTH - 300)
        {
            newX = WIDTH - 300;
        }
        cursor.relocate(newX, cursor.getLayoutY()); 
    }
    
           
}