//import needed packages
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * CharacterSelect will be the second screen displayed in MainStage after the users has decided
 * what kind of match they will play. It allows the users to select what characters they want 
 * to play. 
 *
 * @author Alan Wang, Daniel Chen
 */
public class CharacterSelect
{
    //instantiate needed variables
    private final int WIDTH = 1800;
    private final int HEIGHT = 900;
    private MainStage mainStage;
    private int myGameType;
    private Scene csScene;
    private Pane root; 
    private Canvas layer;
    private Button confirmButton; 
    private Image cursor1 = new Image("cursor1.png");
    private Image cursor2 = new Image("cursor2.png");
    private ImageView cursorOne = new ImageView(cursor1);
    private ImageView cursorTwo = new ImageView(cursor2); 
    private Player player1;
    private Player player2;
    private Player[] availablePlayers = {new Normal(1, 0), new Ninja(1, 0), null, null, null};
    private Game game;
    /**
     * Constructor - creates a CharacterSelect object with a reference to MainStage
     * 
     * @param gameType - what kind of match will be played
     * @param ms - the reference to a MainStage object
     */
    public CharacterSelect(int gameType, MainStage ms)
    {        
        //instantiable variables
        mainStage = ms; //so on the same main stage as MainStage
        myGameType = gameType; //so on the same game type as entered in StartScreen
        
        //make a text box
        Text select = new Text();
        select.setText("Choose Your Champion");
        select.setFont(new Font(90));
        //position the textbox
        select.layoutXProperty().bind(csScene.widthProperty().subtract(select.prefWidth(-1)).divide(2));
        select.layoutYProperty().bind(csScene.heightProperty().divide(2).subtract(300));
        
        //make a canvas to display images on
        layer = new Canvas(WIDTH, HEIGHT);
        //put the picture on the canvas
        GraphicsContext gc = layer.getGraphicsContext2D();
        Image notApplicable = new Image("notapplicable.png");
        gc.setLineWidth(5); 
        gc.setFont(new Font(45));
        
        //declare variables, so they can be instantiated in for loop
        ImageView playerImage;
        Image player;
        WritableImage writePlayer;
        
        //traverse the array availablePlayers, checking if there is a valid Player at each 
        //box or not. If there is, print out the player with the name of the player. 
        //If not, prints out N/A. Draws lines between each element found in the array. 
        for(int i = 0; i < availablePlayers.length; i++)
        {
            if(availablePlayers[i] != null) //keep images 330 by 700
            {
                //get the image, scale it to 400 * 690 pixels (keeps image intact)
                player = new Image(availablePlayers[i].getImageURL(), 400, 690, true, false);
                //gets the image again, scale down to 343 * 674 pixels
                writePlayer = new WritableImage(player.getPixelReader(), 0, 0, 343, 674);
                //draw the picture
                gc.drawImage(writePlayer, calculateImagePosition(i), 0);
                gc.strokeLine(350 * i, 0, 350 * i, HEIGHT);
                //displays name of the player above the player
                gc.strokeText(availablePlayers[i].getName(), calculateTextPosition(i), 240);
            }
            else
            {
                gc.drawImage(notApplicable, 350 * i, 0);
                //keeps the third and fourth lines lower because of the "Choose Your Character"
                //text box, the others can be as long as needed. 
                if (i == 2 || i == 3)
                {
                    gc.strokeLine(350 * i, 160, 350 * i, HEIGHT);
                }
                else
                {
                    gc.strokeLine(350 * i, 0, 350 * i, HEIGHT);
                }
            }
        }
        cursorOne.relocate(0 + 100, HEIGHT - 200);
        cursorTwo.relocate(WIDTH - 300, HEIGHT - 100);
        
        //make a button that allows the players to go in game
        confirmButton = new Button("Start Game");
        //check if the players chosen are valid (no N/A selected). Otherwise,
        //display an error box that they have not selected valid characters. 
        //move into game if they have selected valid characters. 
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
                MainDrawPhase mdp = new MainDrawPhase(game, mainStage);
                Scene mainGameScene = mdp.getScene();
                mainStage.changeScene(mainGameScene);  
            }
            else
            {
                Alert.setWidth(500);
                Alert.setHeight(175);
                Alert.display("Player Selection Not Valid", "You have selected one or more invalid players. Please select two valid characters and try again.");
            }
        });
        
        //instantiate root
        root = new Pane(layer, cursorOne, cursorTwo, confirmButton, select);
        root.setId("pane");
        //make the scene
        csScene = new Scene(root, WIDTH, HEIGHT);
        //allow css attributes into the class
        csScene.getStylesheets().addAll(this.getClass().getResource("background.css").toExternalForm());
        
        //moves the cursor when the user moves cursor to left or right (a and d for first player,
        //left and right for second player)
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
    /**
     * Returns the "group" used here (actually is a pane, which isn't a group). 
     * @return the pane used in the scene
     */
    public Pane getGroup()
    {
        return root; 
    }
    /**
     * Returns the scene used in this class
     * @return the scene used in this class
     */
    public Scene getCSScene()
    {
        return csScene;
    }
    /**
     * Returns the game instantiated by this class (sets up next scene)
     * @return game instantiated by this class
     */
    public Game getGame()
    {
        return game;
    }
    private void moveCursor(ImageView cursor, double deltaX)
    //controls how far the cursor moves when displaying the current "hero selector" cursor
    {
        double newX = Math.max(cursor.getLayoutX() + deltaX, 100);;
        if(newX > WIDTH - 300)
        {
            newX = WIDTH - 300;
        }
        cursor.relocate(newX, cursor.getLayoutY()); 
    }
    /**
     * Attempts to create the players who will be used in the game. If selector is not valid,
     * instantiates that particular player for that selector as null. 
     */
    public void setPlayers()
    {
            double cursorOneIndex = cursorOne.getLayoutX() / 350;
            double cursorTwoIndex = cursorTwo.getLayoutX() / 350;
            switch ((int) cursorOneIndex) 
            {
                case 0:
                    player1 = new Normal(1, 1);
                    break;
                case 1:
                    player1 = new Ninja(1, 1); 
                    break;
                default:
                    player1 = null;
                    break;
            }    
            switch ((int) cursorTwoIndex) 
            {
                case 0:
                    player2 = new Normal(-1, 2);
                    break;
                case 1:
                    player2 = new Ninja(-1, 2); 
                    break;
                default:
                    player2 = null;
                    break;
            }               
    }
    private double calculateImagePosition(int x)
    //used for finding out where to position player images in the for loop
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
    private double calculateTextPosition(int x)
    //used for finding out where to position play names in the for loop
    {
        int value = x;
        if (x > 0)
        {
            value *= 400;
            value -= 10;
        }
        return value + 100;
    }
}