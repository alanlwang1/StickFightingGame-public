//import needed packages
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
/**
 * StartScreen displays the UI the user will immediately run into when they start the application.
 * The first scene displayed in MainStage.
 *
 * @author Alan Wang, Daniel Chen
 */
public class StartScreen
{
    //instantiate variables needed to be used by the class
    private Button playButton, closeButton, bestOfThree, bestOfFive, placebo;
    private MainStage mainStage;
    private Scene scene;
    private GridPane grid;
    private final int WIDTH = 1800;
    private final int HEIGHT = 900;
    /**
     * Constructor - creates a Startscreen object with a reference to MainStage
     * 
     * @param ms - the reference to a MainStage object
     */
    public StartScreen(MainStage ms)
    {
        //Assign reference to mainStage
        mainStage = ms;
        
        grid = new GridPane();
    
        //make the text box for the main screen (welcome to game...)
        Text start = new Text();
        start.setFont(new Font(112));
        start.setText("Welcome to the Game");
        start.setFill(Color.RED);
        start.setStrokeWidth(1.5);
        start.setStroke(Color.BLACK);
        start.setTextOrigin(VPos.TOP);
        start.setId("start");
        //position textbox
        grid.setConstraints(start, 2, 2);
        grid.setHalignment(start, HPos.CENTER);
        
        //make second text box for main screen (on row below above textbox)
        Text startCont = new Text();
        startCont.setFont(new Font(112));
        startCont.setText("of Honorable Summoners");
        startCont.setFill(Color.RED);
        startCont.setStrokeWidth(1.5);
        startCont.setStroke(Color.BLACK);
        //position textbox
        grid.setConstraints(startCont, 2, 3);
        grid.setHalignment(start, HPos.CENTER);
        
        //make invisible text box for formatting (keeps right side as large as left)
        Text alignmentBox = new Text();
        alignmentBox.setFont(new Font(90));
        alignmentBox.setText("Align");
        //position textbox
        grid.setConstraints(alignmentBox,3, 2);
        alignmentBox.setId("transparent");
        
        //makes invisible text box for formatting (keeps middle large enough to make
        //left and right symmetrical)
        Text bigBox = new Text();
        bigBox.setFont(new Font(50));
        bigBox.setText("Bet you will not read this ever Ms.Dafoe cus you ain's in APCSx");
        //position textbox
        bigBox.setId("transparent");
        grid.setConstraints(bigBox, 2, 10);
        
        //display a sprite
        Image image = new Image("stick figure trans.png");
        ImageView stickFigure = new ImageView(image);
        //set dimensions of picture
        stickFigure.setFitHeight(300);
        stickFigure.setFitWidth(300);
        //position picture
        grid.setConstraints(stickFigure, 2,6);
        grid.setHalignment(stickFigure, HPos.CENTER);
        
        //makes a button for playing a best of three series of games
        bestOfThree = new Button();
        bestOfThree.setText("Play Best of Three");
        bestOfThree.setFont(new Font(45));
        //format button
        bestOfThree.setMinSize(400, 100);
        //move to Character Select when button is clicked
        bestOfThree.setOnAction(e ->
        {
            CharacterSelect cs = new CharacterSelect(1, mainStage);
            Scene csScene = cs.getCSScene(); 
            mainStage.changeScene(csScene);
        });
        //position button
        grid.setConstraints(bestOfThree, 2, 8);
        grid.setHalignment(bestOfThree, HPos.LEFT);
        
        //makes a button for playing a best of five series of games
        bestOfFive = new Button();
        bestOfFive.setText("Play Best of Five");
        bestOfFive.setFont(new Font(45));
        //format button
        bestOfFive.setMinSize(400, 100);
        //move to Character Select when button is clicked
        bestOfFive.setOnAction(e ->
        {
            CharacterSelect cs = new CharacterSelect(2, mainStage);
            Scene csScene = cs.getCSScene(); 
            mainStage.changeScene(csScene);
        });
        //position button
        grid.setConstraints(bestOfFive, 2, 8);
        grid.setHalignment(bestOfFive, HPos.RIGHT);
        
        //makes a button for quitting the game
        closeButton = new Button("Quit");
        //request to close the game when clicked
        closeButton.setOnAction(e -> MainStage.closeProgram());
        closeButton.setFont(new Font(30));
        //format the button
        closeButton.setMinSize(210,50);
        //position button
        grid.setConstraints(closeButton, 0, 0);
        
        //make a button displaying instructions in external window
        final Button instructions = new Button("How to Play");
        //really bad solution to calling a method in MainStage, but it works.
        //will call .showInstructions(), which will display an external html page (in user's browser)
        //with the instructions on it. 
        instructions.setOnAction(e -> 
        {
            MainStage z = new MainStage();
            z.showInstructions();
        });
        instructions.setFont(new Font(45));
        //format button
        instructions.setMinSize(210,50);
        //position button
        grid.setConstraints(instructions, 2, 8);
        grid.setHalignment(instructions, HPos.CENTER);
        
        //define gridpane spacing (none to make it easier for me to see)
        grid.setHgap(0);
        grid.setVgap(0);
        //add all nodes to the pane
        grid.getChildren().addAll(start, startCont, bestOfThree, bestOfFive, closeButton, stickFigure, alignmentBox, instructions, bigBox);
        //allow css to display background on pane
        grid.setId("pane");
       
        //instantiate the scene
        scene = new Scene(grid, WIDTH, HEIGHT);
       
        //get css formatting from file 
        scene.getStylesheets().addAll(this.getClass().getResource("background.css").toExternalForm());
        

    }
    /**
     * Returns the scene from this class. 
     * @return the scene displayed in this class
     */
    public Scene getScene()
    {
        return scene;
    }
}
