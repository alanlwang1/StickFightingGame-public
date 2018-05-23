import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.concurrent.TimeUnit;
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

    boolean running, goNorth, goSouth, goEast, goWest;

    @Override
    public void start(Stage stage) throws Exception {
        heroImage = new Image(HERO_IMAGE_LOC);
        hero = new ImageView(heroImage);

        Group dungeon = new Group(hero);

        moveHeroTo(W / 2, H / 2);

        Scene scene = new Scene(dungeon, 1800, 900, Color.FORESTGREEN);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    switch (event.getCode()) {
                        case UP: 
                        for (int i = 0; i < 100; i++)
                        {
                            moveHeroBy(0,-1);
                        }
                        break;
                        case DOWN: 
                        for (int i = 0; i < 100; i++)
                        {
                            moveHeroBy(0,1);
                        }
                        break;
                    }
                }
            });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    switch (event.getCode()) {
                        case UP:
                        break;
                    }
                }
            });

        stage.setScene(scene);
        stage.show();
        
        AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    int dy = 0;
                    int dx = 0;
                    if (goNorth)
                    {
                        dy-=1;
                        goNorth = false;
                    }
                    moveHeroBy(dx, dy);
                }
            };
        timer.start();
    }

    public static void main(String[] args) { launch(args); }

}