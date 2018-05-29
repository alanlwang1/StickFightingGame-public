import javafx.animation.PathTransition; 
import javafx.application.Application; 
import static javafx.application.Application.launch; 
import javafx.event.EventHandler; 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.scene.paint.Color; 
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle; 
import javafx.scene.shape.CubicCurveTo; 
import javafx.scene.shape.MoveTo; 
import javafx.scene.shape.Path; 

import javafx.stage.Stage; 
import javafx.util.Duration;  
    
public class PathTransitionExample1 extends Application { 
   @Override 
   public void start(Stage stage) {      
      //Drawing a Circle 
      Circle circle = new Circle(); 
      
      //Setting the position of the circle 
      circle.setCenterX(300.0f); 
      circle.setCenterY(135.0f); 
      
      //Setting the radius of the circle 
      circle.setRadius(25.0f); 
      
      //Setting the color of the circle 
      circle.setFill(Color.BROWN); 
      
      //Setting the stroke width of the circle 
      circle.setStrokeWidth(20);     
       
      //Instantiating the path class  
      Path path = new Path(); 
      
      //Creating the MoveTo path element 
      MoveTo moveTo = new MoveTo(100, 150); 
      
      //Creating the Cubic curve path element 
      CubicCurveTo cubicCurveTo = new CubicCurveTo(400, 40, 175, 250, 500, 150); 
      
      //Adding the path elements to Observable list of the Path class 
      path.getElements().add(moveTo); 
      path.getElements().add(cubicCurveTo);        
      
      //Creating a path transition 
      PathTransition pathTransition = new PathTransition(); 
      
      //Setting the duration of the path transition 
      pathTransition.setDuration(Duration.millis(1000)); 
      
      //Setting the node for the transition 
      pathTransition.setNode(circle); 
      Circle circ2 = new Circle(50);
      circ2.setCenterX(300.0f);
      circ2.setCenterY(135.0f); 
      pathTransition.setPath(circ2);  
      
      //Setting the orientation of the path 
      pathTransition.setOrientation(PathTransition.OrientationType.
      ORTHOGONAL_TO_TANGENT); 
      
      //Setting the cycle count for the transition 
      pathTransition.setCycleCount(1); 
      
      //Setting auto reverse value to false 
      pathTransition.setAutoReverse(false); 
    
      //Playing the animation 
      
      //pathTransition.play(); 
         
      //Creating a Group object  
      Group root = new Group(circle); 
         
      //Creating a scene object 
      Scene scene = new Scene(root, 600, 300);   
      
      scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
          @Override
          public void handle(KeyEvent event) {
              pathTransition.play();
          }
        });
      //Setting title to the Stage 
      stage.setTitle("Path transition example"); 
         
      //Adding scene to the stage 
      stage.setScene(scene); 
         
      //Displaying the contents of the stage 
      stage.show(); 
   } 
   public static void main(String args[]){ 
      launch(args); 
   } 
}