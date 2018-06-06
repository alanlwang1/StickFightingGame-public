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
 * Write a description of class MainCombatPhase here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MainCombatPhase
{
    private Game game;
    private MainStage mainStage;
    private Scene scene;
    private Canvas canvas;
    private GraphicsContext gc; 
    private Player player1;
    private Player player2; 
    private boolean goUp1, goDown1, goLeft1, goRight1; //booleans controlling player1's movement
    private boolean goUp2, goDown2, goLeft2, goRight2; //booleans controlling player2's movement
    private double dx1, dy1, dx2, dy2; //doubles controlling movement distance for both players
    private int currentFrame1, currentFrame2; //ints controlling player animation
    private int counter1, counter2;
    private ArrayList<Line> createdLines; 
    private ArrayList<Projectile> createdProjectiles;  
    private Group root;
    private AnimationTimer moveTimer;
    /**
     * Constructor for objects of class MainCombatPhase
     */
    public MainCombatPhase(Game g, MainStage ms, ArrayList<Line> lines)
    {
        game = g;
        mainStage = ms; 
        player1 = game.getPlayer1();
        player2 = game.getPlayer2(); 
        createdLines = lines;
        createdProjectiles = new ArrayList<Projectile>();
        
        canvas = new Canvas(1800, 900);
        gc = canvas.getGraphicsContext2D(); 
        
        root = new Group(canvas, player1.getPlayerImage(), player2.getPlayerImage());
        
        //move players to starting positions
        player1.move(0 + 100, canvas.getHeight() - player1.getPlayerImage().getImage().getHeight() - 100);
        player2.move(canvas.getWidth() - 100, canvas.getHeight() - player2.getPlayerImage().getImage().getHeight() - 100);
        //create health bars
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

        for(Line line : createdLines)
            root.getChildren().add(line);
        
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
        

        scene = new Scene(root, 1800, 900);
        setKeyBinds();
        moveTimer.start();
    }
    public Scene getScene()
    {
        return scene;
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
                            if(player1.isWalking()) //if during combat and player on ground
                                player1.getPlayerTimer().start(); //play walking animation
                            break;
                        case S:
                            goDown1 = true;
                            //add crouch later if there is time
                            break;
                        case D:
                            goRight1 = true;
                            player1.setDirection(1); 
                            if(player1.isWalking()) //if during combat and player one ground
                                player1.getPlayerTimer().start(); //play walking animation
                            break; 
                        case R:
                            if(player1.canFire())
                            {
                                player1.setCanFire(false);
                                //generate projectile
                                Projectile projectile = player1.fireRangedAttack();
                                createdProjectiles.add(projectile);
                                //show hitbox
                                //root.getChildren().add(projectile.getHitbox());
                                //create and play animation
                                SpriteAnimation animation= new SpriteAnimation(player1.getPlayerImage(), Duration.seconds(1), 3, 0, 400, player1.getDirection());
                                animation.setCycleCount(1);
                                animation.setOnFinished(e -> 
                                {
                                    projectile.setVisible(true);
                                    projectile.setCurrentSpeed(projectile.getFinalSpeed()); 
                                    if(player1.getDirection() < 0)
                                    {
                                        player1.setImagePort(new Rectangle2D(800, 200, 200, 200));
                                    }
                                    else
                                    {
                                        player1.setImagePort(new Rectangle2D(0, 0, 200, 200));
                                    }
                                    player1.setCanFire(true);
                                });
                                animation.play(); 
                            }
                            break;
                        case F:
                            if(player1.canMelee())
                            {
                                player1.setCanMelee(false);
                                Projectile projectile = player1.useMeleeAttack();
                                createdProjectiles.add(projectile);
                                //show hitbox
                                //root.getChildren().add(projectile.getHitbox());
                                SpriteAnimation animation= new SpriteAnimation(player1.getPlayerImage(), Duration.seconds(1), 3, 400, 400, player1.getDirection());
                                animation.setCycleCount(1);
                                animation.setOnFinished(e -> 
                                {
                                    //remove later
                                    projectile.setVisible(true);
                                    projectile.setCurrentSpeed(projectile.getFinalSpeed());
                                    if(player1.getDirection() < 0)
                                    {
                                        player1.setImagePort(new Rectangle2D(800, 200, 200, 200));
                                    }
                                    else
                                    {
                                        player1.setImagePort(new Rectangle2D(0, 0, 200, 200));
                                    }
                                    player1.setCanMelee(true);
                                });
                                animation.play(); 
                            }
                            break;
                        case UP:
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
                            if(player2.isWalking()) //if during combat and player on ground
                                player2.getPlayerTimer().start(); //start walking animation
                            break;
                        case RIGHT:
                            goRight2 = true;
                            player2.setDirection(1); 
                            if(player2.isWalking()) //if during combat and player on ground
                                player2.getPlayerTimer().start(); //start walking animation 
                            break;
                        case DOWN:
                            goDown2 = true;
                            //add crouch later if time
                            break; 
                        case SHIFT:
                            if(player2.canFire())
                            {
                                player2.setCanFire(false);
                                Projectile projectile = player2.fireRangedAttack();
                                createdProjectiles.add(projectile);
                                SpriteAnimation animation= new SpriteAnimation(player2.getPlayerImage(), Duration.seconds(1), 3, 0, 400, player2.getDirection());
                                animation.setCycleCount(1);
                                animation.setOnFinished(e -> 
                                {
                                    projectile.setVisible(true);
                                    projectile.setCurrentSpeed(projectile.getFinalSpeed());
                                    if(player2.getDirection() < 0)
                                    {
                                        player2.setImagePort(new Rectangle2D(800, 200, 200, 200));
                                    }
                                    else
                                    {
                                        player2.setImagePort(new Rectangle2D(0, 0, 200, 200));
                                    }
                                    player2.setCanFire(true);
                                });
                                animation.play(); 
                            }
                            break; 
                        case CONTROL:
                            if(player2.canMelee())
                            {
                                player2.setCanMelee(false);
                                Projectile projectile = player2.useMeleeAttack();
                                createdProjectiles.add(projectile);
                                SpriteAnimation animation= new SpriteAnimation(player2.getPlayerImage(), Duration.seconds(1), 3, 400, 400, player2.getDirection());
                                animation.setCycleCount(1);
                                animation.setOnFinished(e -> 
                                {
                                    projectile.setVisible(true);
                                    projectile.setCurrentSpeed(projectile.getFinalSpeed());
                                    if(player2.getDirection() < 0)
                                    {
                                        player2.setImagePort(new Rectangle2D(800, 200, 200, 200));
                                    }
                                    else
                                    {
                                        player2.setImagePort(new Rectangle2D(0, 0, 200, 200));
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
                            break;
                        case A:
                            goLeft1 = false;
                            player1.getPlayerTimer().stop();
                            player1.setCurrentFrame(0);
                            player1.setImagePort(new Rectangle2D(800, 200, 200, 200));
                            break;
                        case S:
                            goDown1 = false;
                            break;
                        case D:
                            goRight1 = false;
                            player1.getPlayerTimer().stop();
                            player1.setCurrentFrame(0);
                            player1.setImagePort(new Rectangle2D(0, 0, 200, 200));
                            break; 
                        case UP:
                            break;
                        case LEFT:
                            goLeft2 = false;
                            player2.getPlayerTimer().stop();
                            player2.setCurrentFrame(0);
                            player2.setImagePort(new Rectangle2D(800, 200, 200, 200));
                            break;
                        case DOWN:
                            goDown2 = false;
                            break;
                        case RIGHT:
                            goRight2 = false;
                            player2.getPlayerTimer().stop();
                            player2.setCurrentFrame(0);
                            player2.setImagePort(new Rectangle2D(0, 0, 200, 200));
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

