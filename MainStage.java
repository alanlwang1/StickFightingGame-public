import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.scene.shape.Line;
import javafx.application.HostServices.*;;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
/**
 * class mainStage - class to render all the scenes from each window and 
 * process interactions between scenes
 *
 * @author Alan Wang, Daniel Chen
 * @version 060718
 */
public class MainStage extends Application 
{
    //instance variables
    private static Stage window;
    private StartScreen startScreen;
    private CharacterSelect characterSelect; 
    private MainDrawPhase mainDrawPhase;
    private MainCombatPhase mainCombatPhase;
    private EndGame endGame; 
    private Game game; 
    //main method
    public static void main(String[] args)
    {
        launch(args);
    }
    /**
     * method start - method launches the JavaFX application
     * 
     * @param primaryStage th
     */
    @Override
    public void start(Stage primaryStage)
    {
        //set up primary stage
        window = primaryStage; 
        window.setOnCloseRequest(e -> 
            {
                e.consume();
                closeProgram();
            });
        window.setTitle("Stick Figure Game");
        //set startScreen and begin the game
        StartScreen ss = new StartScreen(this);
        Scene startScene = ss.getScene();
        changeScene(startScene); 
        window.show(); 
    }

    /**
     * method changeScene - method to switch scenes of mainStage and move between game screens
     * 
     * @param scene the new scene to change the stage to
     */
    public void changeScene(Scene scene)
    {
        window.setScene(scene);
    }

    /**
     * method startCharSelect - method to start character select portion of the game
     * and change the scene to the character select screen
     * 
     * @param gameType the type of game being played 1 - best of 3, 2 - best of 5
     */
    public void startCharSelect(int gameType)
    {
        characterSelect = new CharacterSelect(gameType, this);
        Scene csScene = characterSelect.getScene(); 
        changeScene(csScene);
    }
    /**
     * method beginGame - method to start the game and change the scene to the
     * initial combat phase
     * 
     * @param gameType - the type of game being played 1 - best of 3, 2 - best of 5
     * @param player1 Player object for player1
     * @param player2 Player object for player2
     */
    public void beginGame(int gameType, Player player1, Player player2)
    {
        //generate new Game object based on provided gameType; 
        if (gameType == 1)
            game = new Game(player1, player2, 2, 3);
        else
          if(gameType == 2)
                game = new Game(player1, player2, 3, 5);
        
        //create combat and draw phase objects
        mainCombatPhase = new MainCombatPhase(game, this);
        mainDrawPhase = new MainDrawPhase(game, this); 

        //listener for checking when combat ends
        game.getEndGameProperty().addListener(new ChangeListener<Boolean>()
            {
                @Override
                public void changed(ObservableValue<? extends Boolean> o, Boolean oldVal, Boolean newVal)
                {
                    //if game has ended
                    if (newVal.booleanValue() == true)
                    {
                        if (player1.isDead()) //give the win to the player who won 
                            player2.addWin();
                        else
                            player1.addWin();
                        //play transition animation between combatphase and drawphase
                        mainCombatPhase.playEndAnimation();
                    }
                }
            });

        //listener for draw phase end
        game.getDrawProperty().addListener(new ChangeListener<Boolean>()
            {
                @Override 
                public void changed(ObservableValue<? extends Boolean> o, Boolean oldVal, Boolean newVal)
                {
                    //if the drawphase is over
                    if (newVal.booleanValue() == false)
                    {
                        //transition between drawphase and combatphase
                        mainDrawPhase.playCountDownAnimation();
                    }
                }
            }); 
        //change to combat phase scene
        mainCombatPhase.startNewRound(null);
        Scene mainCombatScene = mainCombatPhase.getScene();
        changeScene(mainCombatScene);
    }
    /**
     * method newCombatPhase - method refreshes the combat phase object and 
     * starts a new combat phase, moving the scene
     */
    public void newCombatPhase()
    {
        //change to combat phase scene
        mainCombatPhase.startNewRound(mainDrawPhase.getCreatedLines()); 
        Scene mainCombatScene = mainCombatPhase.getScene();
        changeScene(mainCombatScene); 
    }
    /**
     * method newDrawPhase - method refreshes the draw phase object and starts 
     * a new draw phase, moving the scene
     */
    public void newDrawPhase()
    {
        mainDrawPhase.startNewRound(mainCombatPhase.getCreatedLines()); 
        Scene mainDrawScene = mainDrawPhase.getScene();
        changeScene(mainDrawScene); 
    }
    /**
     * method endGame - method creates a new endGame object and 
     * moves the scene to the endGame scene
     */
    public void endGame()
    {
        endGame = new EndGame(game);
        Scene endGameScene = endGame.getScene();
        changeScene(endGameScene); 
    }
    /**
     * method closeProgram - method to close the window and display prompt for user
     */
    public static void closeProgram()
    {
        //display prompt
        boolean answer = Confirm.display("exit", "Do you really wish to quit the game of honorable summoners?");
        if (answer)
            window.close();
    }
}
