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
import javafx.animation.AnimationTimer;
import java.util.concurrent.TimeUnit;

/**
 * Hold down an arrow key to have your hero move around the screen.
 * Hold down the shift key to have the hero run.
 */
public class Runner2 extends Application {

    private static final double W = 1800, H = 900;

    private static final String HERO_IMAGE_LOC =
        "stick figure.png";

    private Image heroImage;
    private Node  hero;
    final Duration DUR = Duration.seconds(0.01);
    boolean goNorth, goSouth, goEast, goWest;
    long lastPressed;
    double y = 0;
    @Override
    public void start(Stage stage) throws Exception {
        heroImage = new Image(HERO_IMAGE_LOC);
        hero = new ImageView(heroImage);
        Group dungeon = new Group(hero);
        hero.setTranslateX(W/2);
        hero.setTranslateY(H/2);
        Scene scene = new Scene(dungeon, 1800, 900);
        lastPressed = System.nanoTime();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() 
            {
                @Override
                public void handle(KeyEvent event)
                {
                    long difference = System.nanoTime() - lastPressed;
                    double ds = TimeUnit.NANOSECONDS.toSeconds(difference);
                    double x = 0;
                    double y = 0;
                    PathTransition pt;
                    switch (event.getCode()) {
                        case UP: 
                        if ( ds >= 5)goNorth = true;
                        break;
                        case DOWN: 
                        if ( ds >= 5) goSouth = true;
                        break;
                        case LEFT: 
                        if ( ds >= 5) goWest = true;
                        break;
                        case RIGHT:
                        if ( ds >= 5) goEast = true;
                        break;
                    }
                    lastPressed = System.nanoTime();
                }
            });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() 
        {
            @Override
            public void handle(KeyEvent event)
            {
                switch (event.getCode())
                {
                    case UP: goNorth = false; break;
                    case DOWN: goSouth = false; break;
                    case LEFT: goWest = false; break;
                    case RIGHT: goEast = false; break;
                }                
            }
            });
            
          AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                y += 0.5;
                if (goNorth) { y = -10; }
                hero.relocate(hero.getLayoutX(), hero.getLayoutY() + y);
            }
        };
        timer.start();
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}