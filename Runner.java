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

    boolean running, goNorth, goSouth, goEast, goWest, yesG;

    @Override
    public void start(Stage stage) throws Exception {
        heroImage = new Image(HERO_IMAGE_LOC);
        hero = new ImageView(heroImage);

        Group dungeon = new Group(hero);

        hero.relocate(W / 2, H / 2);

        Scene scene = new Scene(dungeon, 1800, 900, Color.FORESTGREEN);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    switch (event.getCode()) {
                        case UP:
                            goNorth = true;
                            yesG = false;
                        break;
                    }
                }
            });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    switch (event.getCode()) {
                        case UP:
                            goNorth = false;
                            yesG = true;
                        break;
                    }
                }
            });

        stage.setScene(scene);
        stage.show();
        
        AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (yesG)
                    {
                        hero.setTranslateY(1);
                    }
                    if (goNorth)
                    {
                        hero.setTranslateY(-15);
                    }
                }
            };
        timer.start();
    }

    public static void main(String[] args) { launch(args); }

}