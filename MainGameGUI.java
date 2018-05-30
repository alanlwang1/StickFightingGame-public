import java.util.ArrayList;
import java.awt.Point;
import javafx.scene.shape.Line;
import javafx.animation.AnimationTimer; 
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView; 
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.SnapshotParameters;
import javafx.stage.Stage;
import javafx.event.EventHandler; 
import javafx.scene.input.KeyEvent;

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
    private GraphicsContext gc; 
    private Player player1;
    private Player player2;
    private ImageView cursor1, cursor2; 
    private boolean drawing; 
    private boolean lineCreated; 
    private boolean goUp1, goDown1, goLeft1, goRight1; //booleans controlling player1's movement
    private boolean goUp2, goDown2, goLeft2, goRight2; //booleans controlling player2's movement
    private ArrayList<Point> selectedPoints = new ArrayList<Point>(); 
    private Group root;
    private AnimationTimer cursorTimer, moveTimer; 
    public MainGameGUI(Game g, MainStage ms) 
    {
        game = g;
        mainStage = ms;
        player1 = game.getPlayer1();
        player2 = game.getPlayer2();
        
        canvas = new Canvas(1800, 900);
        root = new Group(canvas);
        scene = new Scene(root, 1800, 900); 
        
        cursorTimer = new AnimationTimer() 
        {
            @Override
            public void handle(long now)
            {
                int dx1 = 0; 
                int dy1 = 0;
                int dx2 = 0;
                int dy2 = 0;
                if (goUp1)
                    dy1 -= 1;
                if (goLeft1)
                    dx1 -= 1;
                if (goDown1)
                    dy1 += 1;
                if (goRight1)
                    dx1 += 1;
                if (goUp2)
                    dy2 -= 1;
                if (goLeft2)
                    dx2 -= 1;
                if (goDown2)
                    dy2 += 1;
                if (goRight2)
                    dx2 += 1; 
                moveCursor(cursor1, dx1, dy1); 
                moveCursor(cursor2, dx2, dy2); 
            }
        };
        moveTimer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                //put animation for characters here RYAN
            }
        };
        //instantiate cursors 
        drawPhase();
        runGame(); 
    }
    public Scene getScene()
    {
        return scene;
    }
    public void drawPhase()
    {
        cursorTimer.start(); 
        drawing = true; 
        //add text for drawing
        for(int i = 0; i < 5; i++)
        {
            letPlayerOneDraw();
            letPlayerTwoDraw(); 
        }
        //text saying end of drawing phase;
        cursorTimer.stop();
    }
    public void runGame()
    {
        drawPhase(); 
        //actually run the game 
    }
    //use boolean property
    public void letPlayerOneDraw()
    {
        //display cursor
        root.getChildren().add(cursor1);
        //grant control to first player
        lineCreated = false; 
        

        //change cursor to pencil/eraser depending on button
        //choose 2 points, draw line if confirm
        //erase points if no confirm
        //hide cursor
        lineCreated = true; 
        root.getChildren().remove(cursor1);
    }
    public void letPlayerTwoDraw()
    {
        //display cursor
        root.getChildren().add(cursor2);
        //add keybindings for first player
        lineCreated = false;
        //change cursor to pencil/eraser depending on button
        //choose 2 points, draw line if confirm
        //erase points if no confirm 
        //hide cursor
        lineCreated = true;
        root.getChildren().remove(cursor2); 
    }
    public void moveCursor(ImageView cursor, double deltaX, double deltaY)
    {
        double newX = Math.max(cursor.getLayoutX() + deltaX, 0);
        if(newX > 1800)
        {
            newX = 1800;
        }
        double newY = Math.max(cursor.getLayoutY() + deltaY, 0);
        if(newY > 900)
        {
            newY = 900;
        }
        cursor.relocate(newX, newY); 
    }
    public void setKeyBinds()
    {
        //add keybindings for first player
        scene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                switch(event.getCode()) 
                {
                    case W:
                        goUp1 = true;
                        break;
                    case A:
                        goLeft1 = true;
                        break;
                    case S:
                        goDown1 = true;
                        break;
                    case D:
                        goRight1 = true;
                        break; 
                    case R:
                        //if drawing, draw circle at current position/confirm line for cursor1
                        //else fire particle
                    case F:
                        //if drawing, cancel line, erase last point; 
                        //else do melee attack
                    case UP:
                        goUp1 = true;
                        break;
                    case LEFT:
                        goLeft1 = true;
                        break;
                    case RIGHT:
                        goRight1 = true;
                        break;
                    case DOWN:
                        goDown1 = true;
                        break; 
                    case SHIFT:
                        //if drawing, draw circle at current position/confirm line for cursor2
                        //else fire particle
                    case CONTROL:
                        //if drawing, cancel line, erase last point;
                        //else do melee attack 
                }
            }          
        }); 
        scene.setOnKeyReleased(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                switch(event.getCode()) 
                {
                    case W:
                        goUp1 = false;
                        break;
                    case A:
                        goLeft1 = false;
                        break;
                    case S:
                        goDown1 = false;
                        break;
                    case D:
                        goRight1 = false;
                        break; 
                    case UP:
                        goUp2 = false;
                        break;
                    case LEFT:
                        goLeft2 = false;
                        break;
                    case DOWN:
                        goDown2 = false;
                        break;
                    case RIGHT:
                        goRight2 = false;
                        break; 
                }
            }
        });
    }
}
