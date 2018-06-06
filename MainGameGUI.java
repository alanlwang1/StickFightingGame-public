//DEPRECATED DO NOT WORK ON THIS

import java.util.ArrayList;
import java.awt.geom.Point2D; 
import java.awt.geom.Point2D.Double;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle; 
import javafx.animation.AnimationTimer; 
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView; 
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.*;
import javafx.geometry.VPos; 
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.stage.Stage;
import javafx.event.EventHandler; 
import javafx.scene.input.KeyEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Ellipse;
import javafx.util.Duration;
import javafx.scene.shape.Rectangle;
/**
 * Main Gui for the actual game
 *
 * @author Alan Wang
 * @version 052118
 */
public class MainGameGUI 
{
    private Game game;
    private MainStage mainStage;
    private Scene scene;
    private Canvas canvas;
    private GraphicsContext gc; 
    private Player player1;
    private Player player2;
    private ImageView playerOne, playerTwo; 
    private ImageView cursor1, cursor2;
    private Text topBanner; 
    private Text turnBanner; 
    private boolean lineCreated; 
    private boolean goUp1, goDown1, goLeft1, goRight1; //booleans controlling player1's movement
    private boolean goUp2, goDown2, goLeft2, goRight2; //booleans controlling player2's movement
    private double dx1, dy1, dx2, dy2; //doubles controlling movement distance for both players
    private int currentFrame1, currentFrame2; //ints controlling player animation
    private int counter1, counter2;
    private ArrayList<Point2D.Double> selectedPoints; 
    private ArrayList<Line> createdLines; 
    private ArrayList<Projectile> createdProjectiles; 
    private Rectangle2D clipBounds1;
    private Rectangle2D clipBounds2; 
    private Group root;
    private AnimationTimer cursorTimer, moveTimer, player1Animation, player2Animation; 
    public MainGameGUI(Game g, MainStage ms) 
    {
        game = g;
        mainStage = ms;
        player1 = game.getPlayer1();
        player2 = game.getPlayer2();

        canvas = new Canvas(1800, 900);
        gc = canvas.getGraphicsContext2D(); 

        topBanner = new Text(); 
        topBanner.setText("Draw Phase: Player 1's Turn");
        topBanner.setFont(new Font(45));
        topBanner.setFill(Color.BLACK);
        topBanner.setStrokeWidth(1.5);
        topBanner.setStroke(Color.BLACK);
        topBanner.setTextOrigin(VPos.TOP);

        //display cursors, move to positions 
        cursor1 = new ImageView("pointer1.png");
        cursor2 = new ImageView("pointer2.png"); 

        //Timer for drawing cursor movement and repainting
        cursorTimer = new AnimationTimer() 
        {
            @Override
            public void handle(long now)
            {
                dx1 = 0; 
                dy1 = 0;
                dx2 = 0;
                dy2 = 0;
                if (goUp1)
                    dy1 -= 5;
                if (goLeft1)
                    dx1 -= 5;
                if (goDown1)
                    dy1 += 5;
                if (goRight1)
                    dx1 += 5;
                if (goUp2)
                    dy2 -= 5;
                if (goLeft2)
                    dx2 -= 5;
                if (goDown2)
                    dy2 += 5;
                if (goRight2)
                    dx2 += 5; 
                moveCursor(cursor1, dx1, dy1); 
                moveCursor(cursor2, dx2, dy2); 

                //repaint selected points, if any 
                gc.setFill(Color.WHITE); 
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gc.setFill(Color.BLUE); 
                for(Point2D.Double point: selectedPoints)
                {
                    gc.fillOval(point.getX(), point.getY(), 5, 5); 
                }
            }  
        };
        //add listener for draw phase start/end
        game.getDrawProperty().addListener(new ChangeListener<Boolean>()
            {
                @Override 
                public void changed(ObservableValue<? extends Boolean> o, Boolean oldVal, Boolean newVal)
                {
                    if (newVal.booleanValue() == false)
                    {
                        cursorTimer.stop();
                        startCombat(); 
                    }
                }
            });    
        //add listener for turn change
        game.getTurnProperty().addListener(new ChangeListener<Number>()
            {
                @Override
                public void changed(ObservableValue<? extends Number> o, Number oldVal, Number newVal)
                {
                    if(newVal.intValue() == 1)
                    {
                        topBanner.setText("Draw Phase: Player 1's Turn");
                    }
                    else
                        topBanner.setText("Draw Phase: Player 2's Turn");
                }
            });
        //timer for character/projectile movement in combat phase 
        moveTimer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                //reset dx of both players    
                dx1 = 0;
                dx2 = 0;
                //increase gravity for both players constantly
                if(dy1 < 10)
                    dy1 += 2;
                if(dy2 < 10)
                    dy2 += 2;
                //change dx of players based on key presses    
                if(goLeft1)
                    dx1 -= 5;
                if(goRight1)
                    dx1 += 5;
                if(goLeft2)
                    dx2 -= 5;
                if(goRight2)
                    dx2 += 5;
                //check if players have collided with any of the lines created
                for(Line line : createdLines)
                {
                    if(checkCollisions(player1.getHitbox(), line))
                    {    
                        if(dy1 > 0)
                        {
                            dy1 = 0;
                            goUp1 = true; 
                        }    
                        player1.setWalking(true); 
                    }
                    if(checkCollisions(player2.getHitbox(), line))
                    {    
                        if(dy2 > 0)
                        {
                            dy2 = 0;
                            goUp2 = true; 
                        }    
                        player2.setWalking(true); 
                    }
                }
                //update variables in player classes
                movePlayer(player1, dx1, dy1);
                movePlayer(player2, dx2, dy2);
                //move players and hitboxesto new locations
                playerOne.relocate(player1.getX() - 100, player1.getY() - 100);
                playerTwo.relocate(player2.getX() - 100, player2.getY() - 100);

                //move and repaint projectiles, if any 
                gc.setFill(Color.WHITE); 
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                for(Projectile projectile: createdProjectiles)
                {
                    projectile.move(); 
                    if(projectile.isVisible())
                        gc.drawImage(projectile.getGameImage(), projectile.getX() - projectile.getWidth() / 2, projectile.getY() - projectile.getHeight() / 2);
                    if(!projectile.isExisting())
                        createdProjectiles.remove(projectile);
                }
            }
        };

        //create scene and send 
        root = new Group(canvas, topBanner, cursor1, cursor2);
        scene = new Scene(root, 1800, 900); 

        //format topBanner and cursors 
        topBanner.layoutXProperty().bind(scene.widthProperty().subtract(topBanner.prefWidth(-1)).divide(2));
        topBanner.layoutYProperty().bind(scene.heightProperty().subtract(850));

        //format cursor1 and cursor2
        cursor1.relocate(0 + cursor1.getImage().getWidth(), scene.getHeight() - cursor1.getImage().getHeight());
        cursor2.relocate(scene.getWidth() - cursor2.getImage().getWidth(), scene.getHeight() - cursor2.getImage().getHeight());

        //instantiate arraylists to hold points and lines
        selectedPoints = new ArrayList<Point2D.Double>();
        createdLines = new ArrayList<Line>(); 
        //start the game
        setKeyBinds();
        runGame(); 
    }

    public Scene getScene()
    {
        return scene;
    }

    public void runGame()
    {
        //start the drawPhase
        cursorTimer.start();
        game.setDrawPhase(true); 
        //actually run the game 
    }

    public void startCombat()
    {
        //change top banner
        topBanner.setText("Combat Phase");
        //add bottom line
        Line bottomLine = new Line(0, canvas.getHeight() - 100, canvas.getWidth(), canvas.getHeight() - 100); 
        root.getChildren().add(bottomLine); 
        createdLines.add(bottomLine);

        Rectangle r1=new Rectangle();
        r1.setX(0);
        r1.setY(0);
        r1.setHeight(50);
        r1.setWidth(400);
        r1.setFill(javafx.scene.paint.Color.GREEN);
        root.getChildren().add(r1);

        Rectangle r2=new Rectangle();
        r2.setX(1400);
        r2.setY(0);
        r2.setHeight(50);
        r2.setWidth(400);
        r2.setFill(javafx.scene.paint.Color.GREEN);
        root.getChildren().add(r2);

        Text t2 = new Text(0, 25, "Player1 Health");
        t2.setFont(new Font(20));

        Text t1 = new Text(1674, 25, "Player2 Health");
        t1.setFont(new Font(20));
        root.getChildren().add(t1);
        root.getChildren().add(t2);
        //set up imageview and coordinates for player1
        playerOne = new ImageView(player1.getGameImage());
        clipBounds1 = new Rectangle2D(0, 0, 200, 200);
        playerOne.setViewport(clipBounds1);
        player1.setX(0 + 100);
        player1.setY(canvas.getHeight() - playerOne.getImage().getHeight() - 100); 
        player1.setDirection(1);
        //playerOne.relocate(0, canvas.getHeight() - 100 - playerOne.getImage().getHeight());
        //set up hitbox for player1
        //set up imageview and coordinates for player2
        playerTwo = new ImageView(player2.getGameImage());
        clipBounds2 = new Rectangle2D(0, 0, 200, 200);
        playerTwo.setViewport(clipBounds2);
        player2.setX(canvas.getWidth() - 100);
        player2.setY(canvas.getHeight() - playerTwo.getImage().getHeight() - 100); 
        player2.setDirection(-1);
        //playerTwo.relocate(canvas.getWidth() - playerTwo.getImage().getWidth(), canvas.getHeight() - 100 - playerTwo.getImage().getHeight()); 

        //instantiate arrayList to hold created Projectiles
        createdProjectiles = new ArrayList<Projectile>(); 
        //set up countdown

        //create animation timers for both players
        player1Animation = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                //if it is time to change frame
                if(counter1 == 1)
                    currentFrame1 = (currentFrame1 + 1) % 4;
                //change frame of imageView      
                if(goLeft1)
                    clipBounds1 = new Rectangle2D(600 - currentFrame1 * 200, 200, 200, 200);
                else
                if(goRight1)
                    clipBounds1 = new Rectangle2D(200 + currentFrame1 * 200, 0, 200, 200);
                playerOne.setViewport(clipBounds1);
                //increment counter
                counter1 = (counter1 + 1) % 10; 
            }
        };
        player2Animation = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                //if it is time to change frame
                if(counter2 == 1)
                    currentFrame2 = (currentFrame2 + 1) % 4;
                //change frame of imageView
                if(goLeft2)
                    clipBounds2 = new Rectangle2D(600 - currentFrame2 * 200, 200, 200, 200);
                else
                if(goRight2)
                    clipBounds2 = new Rectangle2D(200 + currentFrame2 * 200, 0, 200, 200);
                playerTwo.setViewport(clipBounds2);
                //increment counter
                counter2 = (counter2 + 1) % 10; 
            }
        };
        root.getChildren().add(playerOne);
        root.getChildren().add(playerTwo);
        //hide cursors
        root.getChildren().remove(cursor1);
        root.getChildren().remove(cursor2);

        //add countdown

        //start moveTimer 
        moveTimer.start(); 
    }

    public void moveCursor(ImageView cursor, double deltaX, double deltaY)
    {
        double newX = Math.max(cursor.getLayoutX() + deltaX, 0);
        if(newX > scene.getWidth())
        {
            newX = scene.getWidth();
        }
        double newY = Math.max(cursor.getLayoutY() + deltaY, 0);
        if(newY > scene.getHeight())
        {
            newY = scene.getHeight();
        }
        cursor.relocate(newX, newY); 
    }

    public void movePlayer(Player player, double deltaX, double deltaY)
    {
        double newX = Math.max(player.getX() + deltaX, 100);
        if(newX > scene.getWidth() - 100)
        {
            newX = scene.getWidth() - 100;
        }
        double newY = Math.max(player.getY() + deltaY, 100); 
        if(newY > scene.getHeight() - 100)
        {
            newY = scene.getHeight() - 100; 
        }
        player.move(newX, newY);
    }

    public void setKeyBinds()
    {
        //add keybindings for first player
        scene.setOnKeyPressed(new EventHandler<KeyEvent>()
            {
                @Override
                public void handle(KeyEvent event)
                {
                    switch(event.getCode()) 
                    {
                        case W:
                        if(game.getDrawPhase())
                            goUp1 = true;
                        else 
                        if(goUp1) //if character can jump
                        {
                            //add upward velocity
                            dy1 -= 25; 
                            //disable double jumping
                            goUp1 = false;
                        }
                        break;
                        case A:
                        goLeft1 = true;
                        player1.setDirection(-1);
                        if(!game.getDrawPhase() && player1.isWalking()) //if during combat and player on ground
                            player1Animation.start(); //play walking animation
                        break;
                        case S:
                        goDown1 = true;
                        break;
                        case D:
                        goRight1 = true;
                        player1.setDirection(1); 
                        if(!game.getDrawPhase() && player1.isWalking()) //if during combat and player one ground
                            player1Animation.start(); //play walking animation
                        break; 
                        case R:
                        //if drawing, draw circle at current position/confirm line for cursor1
                        if(game.getDrawPhase() && game.getCurrentTurn() == 1)
                        {
                            //if two points are not selected already
                            if(selectedPoints.size() < 2)
                            {
                                Point2D.Double newPoint = new Point2D.Double(cursor1.getLayoutX(), cursor1.getLayoutY());
                                selectedPoints.add(newPoint);
                            }
                            //add line with two selected points as endpoints
                            else
                            {
                                Point2D.Double point1 = selectedPoints.get(0);
                                Point2D.Double point2 = selectedPoints.get(1); 
                                Line line = new Line(point1.getX(), point1.getY(), point2.getX(), point2.getY());
                                selectedPoints.clear(); 
                                root.getChildren().add(line);
                                createdLines.add(line); 
                                game.incrementLine();
                            }
                        }
                        //else do ranged attack
                        else 
                        if(!game.getDrawPhase() && player1.canFire())
                        {
                            player1.setCanFire(false);
                            //generate projectile
                            Projectile projectile = player1.fireRangedAttack();
                            createdProjectiles.add(projectile);
                            //show hitbox
                            //root.getChildren().add(projectile.getHitbox());
                            //create and play animation
                            SpriteAnimation animation= new SpriteAnimation(playerOne, Duration.seconds(1), 3, 0, 400, player1.getDirection());
                            animation.setCycleCount(1);
                            animation.setOnFinished(e -> 
                                {
                                    projectile.setVisible(true);
                                    projectile.setCurrentSpeed(projectile.getFinalSpeed()); 
                                    if(player1.getDirection() < 0)
                                    {
                                        clipBounds1 = new Rectangle2D(800, 200, 200, 200);
                                        playerOne.setViewport(clipBounds1);
                                    }
                                    else
                                    {
                                        clipBounds1 = new Rectangle2D(0, 0, 200, 200);
                                        playerOne.setViewport(clipBounds1);
                                    }
                                    player1.setCanFire(true);

                                });
                            animation.play(); 
                        }
                        break;
                        case F:
                        //if drawing, cancel line, erase last point; 
                        if(game.getDrawPhase() && game.getCurrentTurn() == 1)
                        {
                            //if there is a point(s) selected
                            if(selectedPoints.size() > 0)
                            {
                                Point2D.Double point = selectedPoints.get(selectedPoints.size() - 1); 
                                selectedPoints.remove(point); 
                            }
                        }
                        //else do melee attack
                        else
                        if(!game.getDrawPhase() && player1.canMelee())
                        {
                            player1.setCanMelee(false);
                            Projectile projectile = player1.useMeleeAttack();
                            createdProjectiles.add(projectile);
                            //show hitbox
                            //root.getChildren().add(projectile.getHitbox());
                            SpriteAnimation animation= new SpriteAnimation(playerOne, Duration.seconds(1), 3, 400, 400, player1.getDirection());
                            animation.setCycleCount(1);
                            animation.setOnFinished(e -> 
                                {
                                    //remove later
                                    projectile.setVisible(true);
                                    projectile.setCurrentSpeed(projectile.getFinalSpeed());
                                    if(player1.getDirection() < 0)
                                    {
                                        clipBounds1 = new Rectangle2D(800, 200, 200, 200);
                                        playerOne.setViewport(clipBounds1);
                                    }
                                    else
                                    {
                                        clipBounds1 = new Rectangle2D(0, 0, 200, 200);
                                        playerOne.setViewport(clipBounds1);
                                    }
                                    player1.setCanMelee(true);
                                });
                            animation.play(); 
                        }
                        break;
                        case K:
                        //remove this later -- for skipping draw phase
                        game.setDrawPhase(false); 
                        break;
                        case UP:
                        if(game.getDrawPhase())
                            goUp2 = true;
                        else
                        if(goUp2) //if player can jump
                        {
                            //add upward velocity
                            dy2 -= 25;
                            //disable double jumping
                            goUp2 = false;
                        }
                        break;
                        case LEFT:
                        goLeft2 = true;
                        player2.setDirection(-1);
                        if(!game.getDrawPhase() && player2.isWalking()) //if during combat and player on ground
                            player2Animation.start(); //start walking animation
                        break;
                        case RIGHT:
                        goRight2 = true;
                        player2.setDirection(1); 
                        if(!game.getDrawPhase() && player2.isWalking()) //if during combat and player on ground
                            player2Animation.start(); //start walking animation 
                        break;
                        case DOWN:
                        goDown2 = true;
                        break; 
                        case SHIFT:
                        if(game.getDrawPhase() && game.getCurrentTurn() == 2)
                        {
                            //if two points are not selected already
                            if(selectedPoints.size() < 2)
                            {
                                Point2D.Double newPoint = new Point2D.Double(cursor2.getLayoutX(), cursor2.getLayoutY());
                                selectedPoints.add(newPoint); 
                            }
                            //add line with two selected points as endpoints
                            else
                            {
                                Point2D.Double point1 = selectedPoints.get(0);
                                Point2D.Double point2 = selectedPoints.get(1); 
                                Line line = new Line(point1.getX(), point1.getY(), point2.getX(), point2.getY());
                                selectedPoints.clear(); 
                                root.getChildren().add(line); 
                                createdLines.add(line); 
                                game.incrementLine();
                            }
                        }
                        //else fire particle 
                        else
                        if(!game.getDrawPhase() && player2.canFire())
                        {
                            player2.setCanFire(false);
                            Projectile projectile = player2.fireRangedAttack();
                            createdProjectiles.add(projectile);
                            SpriteAnimation animation= new SpriteAnimation(playerTwo, Duration.seconds(1), 3, 0, 400, player2.getDirection());
                            animation.setCycleCount(1);
                            animation.setOnFinished(e -> 
                                {
                                    if(player2.getDirection() < 0)
                                    {
                                        projectile.setVisible(true);
                                        projectile.setCurrentSpeed(projectile.getFinalSpeed());
                                        clipBounds2 = new Rectangle2D(800, 200, 200, 200);
                                        playerTwo.setViewport(clipBounds2);
                                    }
                                    else
                                    {
                                        clipBounds2 = new Rectangle2D(0, 0, 200, 200);
                                        playerTwo.setViewport(clipBounds2);
                                    }
                                    player2.setCanFire(true);
                                });
                            animation.play(); 
                        }
                        break; 
                        case CONTROL:
                        //if drawing, cancel line, erase last point; 
                        if(game.getDrawPhase() && game.getCurrentTurn() == 2)
                        {
                            //if there is a point(s) selected
                            if(selectedPoints.size() > 0)
                            {
                                Point2D.Double point = selectedPoints.get(selectedPoints.size() - 1); 
                                selectedPoints.remove(point); 
                            }
                        }
                        //else do melee attack 
                        else
                        if(!game.getDrawPhase() && player2.canMelee())
                        {
                            player2.setCanMelee(false);
                            Projectile projectile = player2.useMeleeAttack();
                            createdProjectiles.add(projectile);
                            SpriteAnimation animation= new SpriteAnimation(playerTwo, Duration.seconds(1), 3, 400, 400, player2.getDirection());
                            animation.setCycleCount(1);
                            animation.setOnFinished(e -> 
                                {
                                    projectile.setVisible(true);
                                    projectile.setCurrentSpeed(projectile.getFinalSpeed());
                                    if(player2.getDirection() < 0)
                                    {
                                        clipBounds2 = new Rectangle2D(800, 200, 200, 200);
                                        playerTwo.setViewport(clipBounds2);
                                    }
                                    else
                                    {
                                        clipBounds2 = new Rectangle2D(0, 0, 200, 200);
                                        playerTwo.setViewport(clipBounds2);
                                    }
                                    player2.setCanMelee(true);
                                });
                            animation.play(); 
                        }
                        break;
                    }
                }          
            }); 
        scene.setOnKeyReleased(new EventHandler<KeyEvent>()
            {
                @Override
                public void handle(KeyEvent event)
                {
                    switch(event.getCode()) 
                    {
                        case W:
                        if(game.getDrawPhase())    
                            goUp1 = false;
                        break;
                        case A:
                        goLeft1 = false;
                        if(!game.getDrawPhase())
                        {
                            player1Animation.stop();
                            currentFrame1 = 0;
                            clipBounds1 = new Rectangle2D(800, 200, 200, 200);
                            playerOne.setViewport(clipBounds1);
                        }
                        break;
                        case S:
                        goDown1 = false;
                        break;
                        case D:
                        goRight1 = false;
                        if(!game.getDrawPhase())
                        {
                            player1Animation.stop();
                            currentFrame1 = 0;
                            clipBounds1 = new Rectangle2D(0, 0, 200, 200);
                            playerOne.setViewport(clipBounds1);
                        }
                        break; 
                        case UP:
                        if(game.getDrawPhase())
                            goUp2 = false;
                        break;
                        case LEFT:
                        goLeft2 = false;
                        if(!game.getDrawPhase())
                        {
                            player2Animation.stop();
                            currentFrame2 = 0;
                            clipBounds2 = new Rectangle2D(800, 200, 200, 200);
                            playerTwo.setViewport(clipBounds2);
                        }
                        break;
                        case DOWN:
                        goDown2 = false;
                        break;
                        case RIGHT:
                        goRight2 = false;
                        if(!game.getDrawPhase())
                        {
                            player2Animation.stop();
                            currentFrame2 = 0;
                            clipBounds2 = new Rectangle2D(0, 0, 200, 200);
                            playerTwo.setViewport(clipBounds2);
                        }
                        break;
                    }
                }
            });
    }

    public boolean checkCollisions( Shape body, Shape coll)
    {
        Shape sp = Shape.intersect(body, coll);
        if(sp.getBoundsInLocal().getWidth() != -1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void damage1(Rectangle r)
    {
        r.setWidth(r.getWidth() -133.3333333333333);
    }

    public void damage2(Rectangle r)
    {
        r.setWidth(r.getWidth() -133.33333333333333);
        r.setX(r.getX() + 133.33333333333333333);
    }
}
