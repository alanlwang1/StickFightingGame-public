import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
        //set startScreen
        StartScreen ss = new StartScreen(this);
        Scene startScene = ss.getScene();
        changeScene(startScene); 
        window.show(); 
    }
    public void changeScene(Scene scene)
    {
        window.setScene(scene);
    }
    public static void closeProgram()
    {
        boolean answer = testConfirm.display("exit", "Do you really wish to quit the game of honorable summoners?");
        if (answer)
            window.close();
    }
}
