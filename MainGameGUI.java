

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
    private ImageView cursor; 
    private boolean player1Control, player2Control;
    private boolean goUp, goDown, goLeft, goRight;
    private Group root; 
    public MainGameGUI(Game g, MainStage ms) 
    {
        game = g;
        mainStage = ms;
        player1 = game.getPlayer1();
        player2 = game.getPlayer2();
        
        canvas = new Canvas(1800, 900);
        root = new Group(canvas);
        scene = new Scene(root, 1800, 900); 
        
        AnimationTimer cursorTimer = new AnimationTimer() 
        {
            @Override
            public void handle(long now)
            {
                int dx = 0; 
                int dy = 0;
                if (goUp)
                    dy -= 1;
                if (goLeft)
                    dx -= 1;
                if (goDown)
                    dy += 1;
                if (goRight)
                    dx += 1;
                moveCursor(cursor, dx, dy);   
            }
        };
        
        
        drawPhase();
        runGame(); 
    }
    public Scene getScene()
    {
        return scene;
    }
    public void drawPhase()
    {
        //add text for drawing
        for(int i = 0; i < 5; i++)
        {
            letPlayerOneDraw();
            letPlayerTwoDraw(); 
        }
        //text saying end of drawing phase; 
    }
    public void runGame()
    {
        drawPhase(); 
        //actually run the game 
    }
    public void letPlayerOneDraw()
    {
        //display cursor
        root.getChildren().add(cursor);
        //grant control to first player
        player1Control = true;
        boolean lineCreated = false; 
        //add keybindings for first player
        scene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                switch(event.getCode()) 
                {
                    case W:
                        goUp = true;
                        break;
                    case A:
                        goLeft = true;
                        break;
                    case S:
                        goDown = true;
                        break;
                    case D:
                        goRight = true;
                        break; 
                    case F:
                        //draw circle at current position/confirm line
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
                        goUp = true;
                        break;
                    case A:
                        goLeft = true;
                        break;
                    case S:
                        goDown = true;
                        break;
                    case D:
                        goRight = true;
                        break; 
                }
            }
        });

        //change cursor to pencil/eraser depending on button
        //choose 2 points, draw line if confirm
        //erase points if no confirm
        //hide cursor 
        root.getChildren().remove(cursor);
    }
    public void letPlayerTwoDraw()
    {
        ImageView cursor;
        //add keybindings for first player
        //change cursor to pencil/eraser depending on button
        //choose 2 points, draw line if confirm
        //erase points if no confirm 
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
}
