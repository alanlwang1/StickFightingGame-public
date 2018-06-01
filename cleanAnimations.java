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
import javafx.beans.property.BooleanProperty;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.shape.LineTo;
public class cleanAnimations extends Application
{
    private static final double W = 1800, H = 900;

    private static final String STICK_PIC =
        "http://icons.iconarchive.com/icons/raindropmemory/legendora/64/Hero-icon.png";

    private Image charachterImage;
    private Node  stickFigure;
    private double grav, yMotion, xMotion;
    private static final Duration DUR = Duration.seconds(0.4);
    @Override
    public void start(Stage stage) throws Exception
    {
        grav = 0.0;
        charachterImage = new Image(STICK_PIC);
        stickFigure = new ImageView(charachterImage);
        Group root = new Group(stickFigure);
        Scene scene = new Scene(root,W,H);
        stickFigure.setTranslateX(W/2);
        stickFigure.setTranslateY(H/2);

        final BooleanProperty upPressed = new SimpleBooleanProperty(false);
        final BooleanProperty rightPressed = new SimpleBooleanProperty(false);
        final BooleanProperty leftPressed = new SimpleBooleanProperty(false);
        final BooleanBinding upRightPressed = upPressed.and(rightPressed);
        final BooleanBinding upLeftPressed = upPressed.and(leftPressed);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() 
            {
                @Override
                public void handle(KeyEvent ke) 
                {
                    switch (ke.getCode())
                    {
                        case UP: 
                        genTransition(0).play(); 
                        upPressed.set(true); 
                        break;
                        case DOWN: break;
                        case LEFT: 
                        genLine(-50).play(); 
                        leftPressed.set(true); 
                        break;
                        case RIGHT: 
                        genLine(50).play(); 
                        rightPressed.set(true); 
                        break;
                    }
                }
            });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>()
            {
                @Override
                public void handle(KeyEvent ke)
                {
                    switch(ke.getCode())
                    {
                        case UP: upPressed.set(false); break;
                        case DOWN: break;
                        case LEFT: leftPressed.set(false); break;
                        case RIGHT: rightPressed.set(false); break; 
                    }
                }
            });

        AnimationTimer timer = new AnimationTimer() 
            {
                @Override
                public void handle(long now) {
                    /*
                    grav += 0.5;
                    stickFigure.relocate(stickFigure.getTranslateX(), stickFigure.getTransitionY() + grav);
                     */
                    upLeftPressed.addListener(new ChangeListener<Boolean>() 
                        {
                            @Override
                            public void changed(ObservableValue<? extends Boolean> obs, Boolean werePressed, Boolean arePressed) 
                            {
                                if (arePressed.booleanValue() == true)
                                {
                                    PathTransition pt = genTransition(-50);
                                    pt.play();
                                }
                            }
                        });

                    upRightPressed.addListener(new ChangeListener<Boolean>() 
                        {
                            @Override
                            public void changed(ObservableValue<? extends Boolean> obs, Boolean werePressed, Boolean arePressed) 
                            {
                                if(arePressed.booleanValue() == true)
                                {
                                    PathTransition pt = genTransition(50);
                                    pt.play();
                                }
                            }
                        });
                }
            };
        timer.start();

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    public PathTransition genTransition(double dx)
    {
        Path path = new Path();
        MoveTo moveTo = new MoveTo();
        moveTo.setX(stickFigure.getTranslateX() + stickFigure.getBoundsInLocal().getWidth()  / 2);
        moveTo.setY(stickFigure.getTranslateY() + stickFigure.getBoundsInLocal().getHeight()  / 2);

        QuadCurveTo quadTo = new QuadCurveTo();
        quadTo.setControlX(stickFigure.getTranslateX() + stickFigure.getBoundsInLocal().getWidth()  / 2 + dx/2);
        quadTo.setControlY(stickFigure.getTranslateY());
        quadTo.setX(stickFigure.getTranslateX() + stickFigure.getBoundsInLocal().getWidth()  / 2 + dx);
        quadTo.setY(stickFigure.getTranslateY() + stickFigure.getBoundsInLocal().getHeight()  / 2);

        path.getElements().add(moveTo);
        path.getElements().add(quadTo);
        PathTransition transition = new PathTransition();
        transition.setNode(stickFigure);
        transition.setDuration(DUR);
        transition.setPath(path);
        transition.setCycleCount(1);

        return transition;
    }

    public PathTransition genLine(double dx)
    {
        Path path = new Path();
        MoveTo moveTo = new MoveTo();
        moveTo.setX(stickFigure.getTranslateX() + stickFigure.getBoundsInLocal().getWidth()  / 2);
        moveTo.setY(stickFigure.getTranslateY() + stickFigure.getBoundsInLocal().getHeight()  / 2);

        LineTo lineTo = new LineTo(moveTo.getX() + dx, moveTo.getY());

        path.getElements().add(moveTo);
        path.getElements().add(lineTo);
        PathTransition transition = new PathTransition();
        transition.setNode(stickFigure);
        transition.setDuration(DUR);
        transition.setPath(path);
        transition.setCycleCount(1);

        return transition;
    }
}