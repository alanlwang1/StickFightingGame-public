import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Arc;
import javafx.util.Duration;
import javafx.scene.shape.MoveTo; 
import javafx.scene.shape.Path; 
import javafx.scene.shape.QuadCurveTo;
/**
 * Hold down an arrow key to have your hero move around the screen.
 * Hold down the shift key to have the hero run.
 */
public class Runner extends Application {

    private static final double W = 1800, H = 900;

    private static final String HERO_IMAGE_LOC =
        "http://icons.iconarchive.com/icons/raindropmemory/legendora/64/Hero-icon.png";

    private Image heroImage;
    private Node  hero;
    final Duration DUR = Duration.seconds(0.25);
    boolean running, goNorth, goSouth, goEast, goWest, yesG;

    @Override
    public void start(Stage stage) throws Exception {
        heroImage = new Image(HERO_IMAGE_LOC);
        hero = new ImageView(heroImage);
        Group dungeon = new Group(hero);
        Path path = new Path();

        MoveTo moveTo = new MoveTo();
        moveTo.setX(hero.getLayoutX() + hero.getBoundsInLocal().getWidth()  / 2);
        moveTo.setY(hero.getLayoutY() + hero.getBoundsInLocal().getHeight()  / 2);

        QuadCurveTo quadTo = new QuadCurveTo();
        quadTo.setControlX(hero.getLayoutX() + hero.getBoundsInLocal().getWidth()  / 2 + 25.0f);
        quadTo.setControlY(hero.getLayoutY());
        quadTo.setX(hero.getLayoutX() + hero.getBoundsInLocal().getWidth()  / 2 + 50.0f);
        quadTo.setY(hero.getLayoutY() + hero.getBoundsInLocal().getHeight()  / 2);

        path.getElements().add(moveTo);
        path.getElements().add(quadTo);
        hero.relocate(W / 2, H / 2);
        PathTransition transition = new PathTransition();
        transition.setNode(hero);
        transition.setDuration(Duration.seconds(1));
        transition.setPath(path);
        //transition.setOrientation(PathTransition.OrientationType.
        //    ORTHOGONAL_TO_TANGENT); 
        transition.setCycleCount(1);
        
        //transition.play();
        Scene scene = new Scene(dungeon, 1800, 900);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event)
            {
                transition.play();
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(args); }

}