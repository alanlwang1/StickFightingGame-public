import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.HostServices.*;;
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
    /**
     * method closeProgram - method to close the window and display prompt for user
     */
    public static void closeProgram()
    {
        boolean answer = Confirm.display("exit", "Do you really wish to quit the game of honorable summoners?");
        if (answer)
            window.close();
    }
    /**
     * method showInstructions - method to display instructions for playing the game
     */
    public void showInstructions()
    {
        getHostServices().showDocument("instructions.html");
    }
}
