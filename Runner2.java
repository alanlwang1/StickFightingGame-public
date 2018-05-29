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
public class Runner2 extends Application {

    private static final double W = 1800, H = 900;

    private static final String HERO_IMAGE_LOC =
        "http://icons.iconarchive.com/icons/raindropmemory/legendora/64/Hero-icon.png";

    private Image heroImage;
    private Node  hero;
    final Duration DUR = Duration.seconds(0.01);

    @Override
    public void start(Stage stage) throws Exception {
        heroImage = new Image(HERO_IMAGE_LOC);
        hero = new ImageView(heroImage);
        Group dungeon = new Group(hero);
        hero.setTranslateX(W/2);
        hero.setTranslateY(H/2);
        Scene scene = new Scene(dungeon, 1800, 900);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() 
            {
                @Override
                public void handle(KeyEvent event)
                {
                    double x = 0;
                    double y = 0;
                    switch (event.getCode()) {
                        case UP: 
                        y = -25;
                        break;
                        case DOWN: 
                        y = 25; 
                        break;
                        case LEFT: 
                        x = -50; 
                        break;
                        case RIGHT: 
                        x = 50; 
                        break;
                    }
                    PathTransition pt = genTransition(x,y);
                    pt.play();
                    
                }
            });
        stage.setScene(scene);
        stage.show();
    }

    public PathTransition genTransition(double dx, double dy)
    {
        Path path = new Path();
        MoveTo moveTo = new MoveTo();
        moveTo.setX(hero.getTranslateX() + hero.getBoundsInLocal().getWidth()  / 2);
        moveTo.setY(hero.getTranslateY() + hero.getBoundsInLocal().getHeight()  / 2);

        QuadCurveTo quadTo = new QuadCurveTo();
        quadTo.setControlX(hero.getTranslateX() + hero.getBoundsInLocal().getWidth()  / 2 + dx/2);
        quadTo.setControlY(hero.getTranslateY() + dy);
        quadTo.setX(hero.getTranslateX() + hero.getBoundsInLocal().getWidth()  / 2 + dx);
        quadTo.setY(hero.getTranslateY() + hero.getBoundsInLocal().getHeight()  / 2);

        path.getElements().add(moveTo);
        path.getElements().add(quadTo);
        PathTransition transition = new PathTransition();
        transition.setNode(hero);
        transition.setDuration(Duration.seconds(0.1));
        transition.setPath(path);
        transition.setCycleCount(1);
        
        return transition;
    }

    public static void main(String[] args) { launch(args); }
}