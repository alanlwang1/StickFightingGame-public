import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
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
import javafx.scene.layout.GridPane;

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
    private MainStage mainStage;
    private int myGameType;
    private Scene csScene;
    private Scene gameScene;
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
    private Player player2;
    private Player[] availablePlayers = {new Normal(), new Ninja(), null, null, null};
    private Game game;
    private GridPane grid;
    
    public CharacterSelect(int gameType, MainStage ms)
    {        
        Text select = new Text();
        select.setText("Choose Your Champion");
        select.setFont(new Font(90));
        
        
        mainStage = ms;
        myGameType = gameType;
        
        layer = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = layer.getGraphicsContext2D();
        Image notApplicable = new Image("notapplicable.png");
        gc.setLineWidth(5); 
        
 
        ImageView playerImage;
        Image player;
        WritableImage writePlayer;
        
        grid = new GridPane();
        
        for(int i = 0; i < availablePlayers.length; i++)
        {
            if(availablePlayers[i] != null) //keep images 330 by 700
            {
                player = new Image(availablePlayers[i].getImageURL(), 400, 690, true, false);
                //using image scalings of 9/8
                writePlayer = new WritableImage(player.getPixelReader(), 0, 0, 343, 674);
                gc.drawImage(writePlayer, calculatePosition(i), 0);
                gc.strokeLine(350 * i, 0, 350 * i, HEIGHT);
                //gc.drawImage(availablePlayers[i].getCharImage(), 350 * i, 0);
            }
            else
            {
                gc.drawImage(notApplicable, 350 * i, 0);
                gc.strokeLine(350 * i, 0, 350 * i, HEIGHT);
            }
        }
        cursorOne.relocate(0 + 100, HEIGHT - 200);
        cursorTwo.relocate(WIDTH - 300, HEIGHT - 100);
        
        confirmButton = new Button("Start Game");
        confirmButton.setOnAction(e -> 
        {
            setPlayers();
            if(player1 != null && player2 != null)
            {
                if (myGameType == 1)
                    game = new Game(player1, player2, 2, 3);
                else
                    if(myGameType == 2)
                        game = new Game(player1, player2, 3, 5);
                //create new scene with game and send
                MainGameGUI mgg = new MainGameGUI(game, mainStage);
                Scene mainGameScene = mgg.getScene();
                mainStage.changeScene(mainGameScene);  
            }
            else
                System.out.println("Characters not selected"); 
            
        });
        
        //pane.getChildren().add(layer);
        root = new Group(layer, cursorOne, cursorTwo, confirmButton, select);
        csScene = new Scene(root, 1800, 900);
        select.layoutXProperty().bind(csScene.widthProperty().subtract(select.prefWidth(-1)).divide(2));
        select.layoutYProperty().bind(csScene.heightProperty().divide(2).subtract(200));
        csScene.setOnKeyPressed(ke -> {
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
    public Scene getCSScene()
    {
        return csScene;
    }
    public Scene getGameScene()
    {
        return gameScene;
    }
    public Game getGame()
    {
        return game;
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
    public void setPlayers()
    {
            double cursorOneIndex = cursorOne.getLayoutX() / 350;
            double cursorTwoIndex = cursorTwo.getLayoutX() / 350;
            switch ((int) cursorOneIndex) 
            {
                case 0:
                    player1 = new Normal();
                    break;
                case 1:
                    player1 = new Ninja(); 
                    break;
                default:
                    player1 = null;
                    break;
            }    
            switch ((int) cursorTwoIndex) 
            {
                case 0:
                    player2 = new Normal();
                    break;
                case 1:
                    player2 = new Ninja(); 
                    break;
                default:
                    player2 = null;
                    break;
            }               
    }
    private double calculatePosition(int x)
    {
        int value = x;
        if (x > 0)
        {
            value *= 350;
        }
        if (x >= 1)
        {
            value += 10;
        }
        return value;
    }
}