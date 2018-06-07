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
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javafx.scene.shape.MoveTo; 
import javafx.scene.shape.Path; 
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Rectangle2D; 
import javafx.animation.AnimationTimer;
import java.util.concurrent.TimeUnit;
import javafx.beans.property.BooleanProperty;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
public class cleanAnimations3 extends Application
{
    private static final double W = 1800, H = 900;

    private static final String STICK_PIC =
        "ninjaSpriteSheet.png";
    
    private Image charachterImage;
    private Rectangle2D clipBounds = new Rectangle2D(0, 0, 200, 200);
    private ImageView stickFigure;
    private Line line; 
    private int ticks;
    private int currentFrame = 1; 
    private double dy = 0; 
    private double dx = 0; 
    private double grav, yMotion, xMotion;
    private boolean moveLeft, moveRight, jumping; 
    private boolean walking; 
    private boolean listeningForKeyPress = true;
    private static final Duration DUR = Duration.seconds(0.4);
    @Override
    public void start(Stage stage) throws Exception
    {
        ticks = 0;
        grav = 0.0;
        charachterImage = new Image(STICK_PIC);

        
        stickFigure = new ImageView(charachterImage);
        stickFigure.setViewport(clipBounds); 
        Line line = new Line(0, H - 100, W, H - 100); 
        Group root = new Group(stickFigure, line);
        Scene scene = new Scene(root,W,H);
        stickFigure.setTranslateX(W/2);
        stickFigure.setTranslateY(H/2);
        Ellipse ep = new Ellipse(); 
        ep.setCenterX(stickFigure.getLayoutX() + 1000);
        ep.setCenterY(stickFigure.getLayoutY() + 550);
        ep.setRadiusY(110);
        ep.setRadiusX(70);
        root.getChildren().add(ep);
        
        final BooleanProperty upPressed = new SimpleBooleanProperty(false);
        final BooleanProperty rightPressed = new SimpleBooleanProperty(false);
        final BooleanProperty leftPressed = new SimpleBooleanProperty(false);
        final BooleanBinding upRightPressed = upPressed.and(rightPressed);
        final BooleanBinding upLeftPressed = upPressed.and(leftPressed);
        
        AnimationTimer rightTimer = new AnimationTimer()
        {
          @Override
          public void handle(long now)
          {
                  if(ticks == 1)
                        currentFrame = (currentFrame + 1) % 4;
                  switch(currentFrame)
                  {
                      case 1:
                        clipBounds = new Rectangle2D(200, 0, 200, 200);
                        break;
                      case 2:
                        clipBounds = new Rectangle2D(400, 0, 200, 200);
                        break;
                      case 3:
                        clipBounds = new Rectangle2D(600, 0, 200, 200);
                        break;
                      case 0:
                        clipBounds = new Rectangle2D(800, 0, 200, 200); 
                        break;
                  }
                  stickFigure.setViewport(clipBounds);
                  ticks = (ticks + 1) % 10; 
              }
        };
        AnimationTimer leftTimer = new AnimationTimer()
        {
          @Override
          public void handle(long now)
          {
                  if(ticks == 1)
                    currentFrame = (currentFrame + 1) % 4;
                  switch(currentFrame)
                  {
                      case 1:
                        clipBounds = new Rectangle2D(600, 200, 200, 200);
                        break;
                      case 2:
                        clipBounds = new Rectangle2D(400, 200, 200, 200);
                        break;
                      case 3:
                        clipBounds = new Rectangle2D(200, 200, 200, 200);
                        break;
                      case 0:
                        clipBounds = new Rectangle2D(0, 200, 200, 200); 
                        break;
                  }
                  stickFigure.setViewport(clipBounds);
                  ticks = (ticks + 1) % 10; 
              }
        };
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() 
            {
                @Override
                public void handle(KeyEvent ke) 
                {
                    switch (ke.getCode())
                    {
                        case UP:
                        if(listeningForKeyPress)    
                        {    
                            dy -= 30;
                            listeningForKeyPress = false;  
                        }
                       // genTransition(0).play(); 
                        upPressed.set(true); 
                        break;
                        case DOWN: break;
                        case LEFT: 
                        moveLeft = true; 
                        //genLine(50).play(); 
                        leftPressed.set(true);
                        if(walking)
                            leftTimer.start(); 
                        break;
                        case RIGHT: 
                        moveRight = true; 
                        if(walking)
                            rightTimer.start(); 
                        //genLine(50).play(); 
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
                        case UP: 
                        jumping = false; 
                        upPressed.set(false); 
                        break;
                        case DOWN: break;
                        case LEFT: 
                            moveLeft = false; 
                            leftPressed.set(false); 
                            leftTimer.stop();
                            clipBounds = new Rectangle2D(800, 200, 200, 200);
                            stickFigure.setViewport(clipBounds);
                            
                        break;
                        case RIGHT: 
                            moveRight = false; 
                            rightPressed.set(false); 
                            rightTimer.stop();
                            clipBounds = new Rectangle2D(0, 0, 200, 200);
                            stickFigure.setViewport(clipBounds); 
                            break; 
                    }
                }
            });
        Circle c = new Circle(20);
        c.setCenterX(stickFigure.getLayoutX());
        c.setCenterY(stickFigure.getLayoutY());
        
        root.getChildren().add(c);
        
        AnimationTimer timer = new AnimationTimer() 
            {
                @Override
                public void handle(long now) {
                    c.setCenterX(c.getCenterX() + 50);
                    dx = 0;
                    if(dy < 10)
                        dy += 2;
                    /*    
                    if(jumping && walking)
                    {
                        if(dy > 0)
                            dy = 0;
                        dy = 10;
                    }
                    */
                    if(moveLeft)
                        dx = -5;
                    if(moveRight)
                        dx += 5;
                    if(stickFigure.getBoundsInParent().intersects(line.getBoundsInParent()))
                    {    
                        if(dy > 0)
                        {
                            dy = 0;
                            listeningForKeyPress = true; 
                        }    
                        walking = true; 
                    }
                    stickFigure.relocate(stickFigure.getLayoutX() + dx, stickFigure.getLayoutY() + dy);  
                    /*
                    upLeftPressed.addListener(new ChangeListener<Boolean>() 
                        {
                            @Override
                            public void changed(ObservableValue<? extends Boolean> obs, Boolean werePressed, Boolean arePressed) 
                            {
                                if (arePressed.booleanValue() == true)
                                {
                                    PathTransition pt = genTransition(50);
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
                    */
                   ep.setCenterX(stickFigure.getLayoutX() + 1000);
                   ep.setCenterY(stickFigure.getLayoutY() + 550);
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