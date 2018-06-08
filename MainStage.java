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
 * class mainStage - class to render all the scenes from each window
 *
 * @author Alan Wang
 * @version 052418
 */
public class MainStage extends Application 
{
    private static Stage window;
    private StartScreen startScreen;
    private CharacterSelect characterSelect; 
    private MainDrawPhase mainDrawPhase;
    private MainCombatPhase mainCombatPhase;
    private EndGame endGame; 
    private Game game; 
    public static void main(String[] args)
    {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage)
    {
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
    public void startCharSelect(int gameType)
    {
            characterSelect = new CharacterSelect(gameType, this);
            Scene csScene = characterSelect.getScene(); 
            changeScene(csScene);
    }
    public void beginGame(int gameType, Player player1, Player player2)
    {
        if (gameType == 1)
            game = new Game(player1, player2, 2, 3);
        else
            if(gameType == 2)
                game = new Game(player1, player2, 3, 5);
        //create combat and draw phases
        mainCombatPhase = new MainCombatPhase(game, this);
        mainDrawPhase = new MainDrawPhase(game, this); 

        //listener for checking when combat ends
        game.getEndGameProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> o, Boolean oldVal, Boolean newVal)
            {
                if (newVal.booleanValue() == true)
                {
                    mainCombatPhase.playEndAnimation();
                    //if the match is over
                    if(game.matchState())
                    {
                        //move to endGame screen
                        endGame();
                        //EndGame eg = new EndGame(mainStage, game.getWinner());
                        //Scene endGameScene = eg.getScene();
                        //mainStage.changeScene(endGameScene);
                    }
                    //if match is not over
                    else
                    {
                        //move to intermediate draw phase
                        newDrawPhase(); 
                    }
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
                        newCombatPhase(); 
                }
            }); 
        //change to combat phase scene
        mainCombatPhase.startNewRound(null);
        Scene mainCombatScene = mainCombatPhase.getScene();
        changeScene(mainCombatScene);
    }
    public void newCombatPhase()
    {
        //change to combat phase scene
        mainCombatPhase.startNewRound(mainDrawPhase.getCreatedLines()); 
        Scene mainCombatScene = mainCombatPhase.getScene();
        changeScene(mainCombatScene); 
    }
    public void newDrawPhase()
    {
        mainDrawPhase.startNewRound(mainCombatPhase.getCreatedLines()); 
        Scene mainDrawScene = mainDrawPhase.getScene();
        changeScene(mainDrawScene); 
    }

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
        boolean answer = Confirm.display("exit", "Do you really wish to quit the game of honorable summoners?");
        if (answer)
            window.close();
    }
}
