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
        Arc arc = new Arc();
        arc.setCenterX(50.0f);
        arc.setCenterY(50.0f);
        arc.setRadiusX(25.0f);
        arc.setRadiusY(25.0f);
        arc.setStartAngle(45.0f);
        arc.setLength(270.0f);
        arc.setType(ArcType.ROUND);
        Group dungeon = new Group(hero);

        hero.relocate(W / 2, H / 2);
        PathTransition transition = new PathTransition();
        transition.setNode(hero);
        transition.setDuration(DUR);
        transition.setPath(arc);
        transition.setCycleCount(1);
        transition.play();
        Scene scene = new Scene(dungeon, 1800, 900, Color.FORESTGREEN);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(args); }

}