import javafx.animation.TranslateTransition;
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
import javafx.util.Duration;

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

        hero.relocate(W / 2, H / 2);
        TranslateTransition t = new TranslateTransition(DUR, hero);
        t.setOnFinished(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent t) {
                    hero.relocate(hero.getLayoutX() + hero.getTranslateX(), hero.getTranslateY() + hero.getLayoutY());
                    hero.setTranslateY(0);
                    hero.setTranslateX(0);
                }
            });
        Scene scene = new Scene(dungeon, 1800, 900, Color.FORESTGREEN);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    switch (event.getCode()) {
                        case UP:
                        t.setToY(-20);
                        t.setToX(0);
                        t.playFromStart();
                        break;
                        case LEFT: 
                        t.setToX(-20);
                        t.setToY(0);
                        t.playFromStart();
                        break;
                        case RIGHT: 
                        t.setToX(20);
                        t.setToY(0);
                        t.playFromStart();
                        break;
                        case DOWN:
                        t.setToY(20);
                        t.setToX(0);
                        t.playFromStart();
                        break;
                    }
                }
            });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(args); }

}