import java.util.ArrayList;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle; 
import javafx.animation.AnimationTimer; 
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView; 
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.geometry.Rectangle2D;
import javafx.stage.Stage;
import javafx.event.EventHandler; 
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Ellipse;
import javafx.util.Duration;
import javafx.scene.shape.Rectangle;
/**
 * class MainCombatPhase - class to model the combat phase of the game
 *
 * @author Alan Wang, Ryan Wei
 * @version 060718
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
    private boolean goLeft1, goRight1; //booleans controlling player1's movement
    private boolean goLeft2, goRight2; //booleans controlling player2's movement
    private double dx1, dy1, dx2, dy2; //doubles controlling movement distance for both players
    private ArrayList<Line> createdLines; 
    private ArrayList<Projectile> createdProjectiles;
    private ArrayList<ImageView> projectileImages; 
    private Group root;
    private AnimationTimer moveTimer;
    /**
     * Constructor for objects of class MainCombatPhase
     * 
     * @param g a reference to the Game object
     * @param ms a reference to the MainStage object
     * @param lines ArrayList containing the created lines
     */
    public MainCombatPhase(Game g, MainStage ms)
    {
        //assign reference to Game object and players
        game = g;
        player1 = game.getPlayer1();
        player2 = game.getPlayer2();
        //reset values for movement 
        dx1 = 0;
        dy1 = 0;
        dx2 = 0;
        dy2 = 0;
        //assign reference to MainStage object, reset players
        mainStage = ms;
        createdProjectiles = new ArrayList<Projectile>();
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
                //update variables in player classes
                movePlayer(player1, dx1, dy1);
                movePlayer(player2, dx2, dy2);
                //move players and hitboxesto new locations

                for(int i = 0; i < createdProjectiles.size(); i++)
                {
                    Projectile projectile = createdProjectiles.get(i);
                    projectile.move(); 
                    if(projectile.getX() < 0 || projectile.getX() > 1800)  
                    {
                        createdProjectiles.remove(projectile);
                        root.getChildren().remove(projectile.getProjectileImage());
                        i--;
                    }
                    //if projectile is no longer existing, remove it   
                    if(!projectile.isExisting())
                    {
                        createdProjectiles.remove(projectile);
                        root.getChildren().remove(projectile.getProjectileImage());
                        i--;
                    }    
                    //if projectile collides with line, remove it
                    for(Line line : createdLines)
                    {
                        if(checkCollisions(projectile.getHitbox(), line))
                        {
                            createdProjectiles.remove(projectile);
                            root.getChildren().remove(projectile.getProjectileImage());
                            i--;
                        }
                    }
                    //if projectile collides with a player, damage player
                    if(checkCollisions(projectile.getHitbox(), player1.getHitbox()) && projectile.getPlayer().getPlayerID() == 2 && player1.canTakeDamage())
                    {
                        player1.takeDamage(1);
                        player1.setCanTakeDamage(false);
                        createdProjectiles.remove(projectile);
                        root.getChildren().remove(projectile.getProjectileImage());
                        i--;
                    }
                    if(checkCollisions(projectile.getHitbox(), player2.getHitbox()) && projectile.getPlayer().getPlayerID() == 1 && player2.canTakeDamage())
                    {
                        player2.takeDamage(1);
                        player2.setCanTakeDamage(false);
                        createdProjectiles.remove(projectile);
                        root.getChildren().remove(projectile.getProjectileImage());
                        i--;
                    }
                }
                //redraw health bars
                gc.setFill(Color.GREEN);
                gc.fillRect(0, 0, player1.getHealth() * 100, 50);
                gc.fillRect(1800 - player2.getHealth() * 100, 0, 1800, 50); 
                //check if a player has won/lost
                game.checkWinCondition(); 
            }
        };
        //create new scene, set keybinds
        root = new Group();
        scene = new Scene(root, 1800, 900);
        setKeyBinds(); 
    }
    /**
     * method getScene - method returns the scene for this MainCombatPhase object
     * 
     * @return scene the scene for this MainCombatPhase object
     */
    public Scene getScene()
    {
        return scene;
    }
    /**
     * method getCreatedLines - method returns the ArrayList containing the lines created so far
     * 
     * @return the ArrayList containing the lines created so far
     */
    public ArrayList<Line> getCreatedLines()
    {
        return createdLines; 
    }
    /**
     * method movePlayer - method moves the player object by deltaX and deltaY, 
     * accounting for boundaries
     * 
     * @param player - the Player object to be moved
     * @param deltaX - amount to move the Player in X direction
     * @param deltaY - amount to move the Player in Y direction
     */
    public void movePlayer(Player player, double deltaX, double deltaY)
    {
        //restrict movement if player hits boundary, line
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
        //check if player collided with any lines
        for(Line line : createdLines)
        {
            if(checkCollisions(player.getHitbox(), line))
            {
                //if player is approaching left side of line from the left
                if(player.getX() < line.getStartX() && deltaX > 0)
                {
                    newX = player.getX(); 
                }
                //if player is approaching right side of line from the right
                if(player.getX() > line.getEndX() && deltaX < 0)
                {
                    newX = player.getX();
                }
                //if player falls onto a line
                if(player.getY() < line.getStartY() && deltaY > 0)
                {
                    
                    newY = player.getY();
                    player.setWalking(true);
                    //allow player to jump again
                    player.setCanJump(true);
                }
                //if player jumps into a line
                if(player.getY() > line.getEndY() && deltaY < 0)
                {
                    newY = player.getY();
                }
            }
        }
        //move player to new location
        player.move(newX, newY);
    }
    /**
     * method setKeyBinds - method sets the keybinds to be used during mainDrawPhase
     */
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
                            if(player1.canJump()) //if character can jump
                            {
                                //add upward velocity
                                dy1 -= 40; 
                                //disable double jumping
                                player1.setCanJump(false);
                            }
                            break;
                        case A:
                            goLeft1 = true;
                            player1.setDirection(-1);
                            if(player1.isWalking()) //if during combat and player on ground
                                if(player1.isCrouching())
                                    //keep player image to be right crouching image
                                    player1.setImagePort(new Rectangle2D(800, 500, 200, 100));
                                else
                                    player1.getPlayerTimer().start(); //play walking animation
                            break;
                        case S:
                            //stop walking animation timer
                            player1.getPlayerTimer().stop();
                            //change crouching image depending on direction
                            if(player1.getDirection() < 0)
                                player1.setImagePort(new Rectangle2D(800, 500, 200, 100));
                            else
                                player1.setImagePort(new Rectangle2D(800, 400, 200, 100));
                            //move hitbox downward
                            player1.setHitbox(new Ellipse(player1.getX(), player1.getY() - 40, 90, 30));
                            player1.setIsCrouching(true); 
                            break;
                        case D:
                            goRight1 = true;
                            player1.setDirection(1); 
                            if(player1.isWalking()) //if during combat and player one ground
                                if(player1.isCrouching())
                                    player1.setImagePort(new Rectangle2D(800, 400, 200, 100));
                                else
                                    player1.getPlayerTimer().start(); //play walking animation
                            break; 
                        case R:
                            if(player1.canFire() && !player1.isCrouching())
                            {
                                player1.setCanFire(false);
                                //generate projectile
                                Projectile projectile = player1.fireRangedAttack();
                                projectile.getProjectileImage().setVisible(false); 
                                root.getChildren().add(projectile.getProjectileImage()); 
                                createdProjectiles.add(projectile);
                                //create and play animation
                                player1.playRangedAnimation(projectile);
                            }
                            break;
                        case F:
                            if(player1.canMelee() && !player1.isCrouching())
                            {
                                player1.setCanMelee(false);
                                //generate melee projectile
                                Projectile projectile = player1.useMeleeAttack();
                                createdProjectiles.add(projectile);
                                //create and play animation
                                player1.playMeleeAnimation(projectile); 
                            }
                            break;
                        //same as above but for player2
                        case UP:
                            if(player2.canJump()) //if player can jump
                            {
                                //add upward velocity
                                dy2 -= 40;
                                //disable double jumping
                                player2.setCanJump(false);
                            }
                            break;
                        case LEFT:
                            goLeft2 = true;
                            player2.setDirection(-1);
                            if(player2.isWalking())//if during combat and player on ground
                                if(player2.isCrouching())
                                    player2.setImagePort(new Rectangle2D(800, 500, 200, 100));
                                else
                                    player2.getPlayerTimer().start(); //start walking animation
                            break;
                        case RIGHT:
                            goRight2 = true;
                            player2.setDirection(1); 
                            if(player2.isWalking()) //if during combat and player on ground
                                if(player2.isCrouching())
                                    player2.setImagePort(new Rectangle2D(800, 400, 200, 100));
                                else
                                    player2.getPlayerTimer().start(); //start walking animation 
                            break;
                        case DOWN:
                            player2.getPlayerTimer().stop(); 
                            if(player2.getDirection() < 0)
                                player2.setImagePort(new Rectangle2D(800, 500, 200, 100));
                            else
                                player2.setImagePort(new Rectangle2D(800, 400, 200, 100));
                            player2.setHitbox(new Ellipse(player2.getX(), player2.getY() - 40, 90, 30));
                            player2.setIsCrouching(true); 
                            break; 
                        case SHIFT:
                            if(player2.canFire() && !player2.isCrouching())
                            {
                                player2.setCanFire(false);
                                Projectile projectile = player2.fireRangedAttack();
                                projectile.getProjectileImage().setVisible(false); 
                                root.getChildren().add(projectile.getProjectileImage()); 
                                createdProjectiles.add(projectile);
                                player2.playRangedAnimation(projectile);
                            }
                            break; 
                        case CONTROL:
                            if(player2.canMelee() && !player2.isCrouching())
                            {
                                player2.setCanMelee(false);
                                Projectile projectile = player2.useMeleeAttack();
                                createdProjectiles.add(projectile);
                                player2.playMeleeAnimation(projectile);
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
                            if(!player1.isCrouching())
                            {
                                //stop animation timer, set player to normal
                                player1.getPlayerTimer().stop();
                                player1.setCurrentFrame(0);
                                player1.setImagePort(new Rectangle2D(800, 200, 200, 200));
                            }
                            break;
                        case S:
                            //shift player up
                            player1.move(player1.getX(), player1.getY() - 110);
                            //move hitbox up
                            player1.setHitbox(new Ellipse(player1.getX(), player1.getY(), 70, 110));
                            //set player sprite back to normal
                            if(player1.getDirection() < 0)
                                player1.setImagePort(new Rectangle2D(800, 200, 200, 200));
                            else
                                player1.setImagePort(new Rectangle2D(0, 0, 200, 200));
                            player1.setIsCrouching(false);
                            break;
                        case D:
                            goRight1 = false;
                            if(!player1.isCrouching())
                            {   
                                //stop animation timer, set player to normal
                                player1.getPlayerTimer().stop();
                                player1.setCurrentFrame(0);
                                player1.setImagePort(new Rectangle2D(0, 0, 200, 200));
                            }
                            break; 
                        //same as above but for player 2
                        case UP:
                            break;
                        case LEFT:
                            goLeft2 = false;
                            if(!player2.isCrouching())
                            {
                                player2.getPlayerTimer().stop();
                                player2.setCurrentFrame(0);
                                player2.setImagePort(new Rectangle2D(800, 200, 200, 200));
                            }
                            break;
                        case DOWN:
                            player2.move(player2.getX(), player2.getY() - 110);
                            player2.setHitbox(new Ellipse(player2.getX(), player2.getY(), 70, 110));
                            if(player2.getDirection() < 0)
                                player2.setImagePort(new Rectangle2D(800, 200, 200, 200));
                            else
                                player2.setImagePort(new Rectangle2D(0, 0, 200, 200));
                            player2.setIsCrouching(false);
                            break;
                        case RIGHT:
                            goRight2 = false;
                            if(!player2.isCrouching())
                            {
                                player2.getPlayerTimer().stop();
                                player2.setCurrentFrame(0);
                                player2.setImagePort(new Rectangle2D(0, 0, 200, 200));
                            }
                            break;
                    }
                }
            });
    }
    /**
     * method checkCollisions - method checks collisions between hitboxes of players and projectiles with lines
     * 
     * @param body - shape of first object
     * @param coll - shape of second object
     */
    public boolean checkCollisions( Shape body, Shape coll)
    {
        Shape sp = Shape.intersect(body, coll);
        //if there is a collision
        if(sp.getBoundsInLocal().getWidth() != -1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    /**
     * method startNewRound - starts a new combat phase with the 
     * provided ArrayList of lines that were created already
     * 
     * @param createdLines the ArrayList of lines that were created already
     */
    public void startNewRound(ArrayList<Line> createdLines)
    {
        //stop movetimer while game is being refreshed
        moveTimer.stop(); 
        //refresh game and players
        game.refreshGameState(); 


        //create canvas for displaying projectiles
        canvas = new Canvas(1800, 900);
        gc = canvas.getGraphicsContext2D(); 
        
        //create new root group and assign to scene
        root = new Group(canvas, player1.getPlayerImage(), player2.getPlayerImage());
        scene.setRoot(root); 
        
        Image image = new Image("notebook3.png", 2020, 2500, true, true);
        ImageView background = new ImageView(image);
        root.getChildren().add(background); 
        background.toBack(); 
        
        //move players to starting positions
        player1.move(0 + 100, canvas.getHeight() - player1.getPlayerImage().getImage().getHeight() - 100);
        player2.move(canvas.getWidth() - 100, canvas.getHeight() - player2.getPlayerImage().getImage().getHeight() - 100);
        
        //if arrayList null - no lines created so far
        if(createdLines == null)
        {
            //create initial lines array 
            this.createdLines = new ArrayList<Line>();

            //add initial lines
            Line bottomLine = new Line(0, scene.getHeight() - 100, scene.getWidth(), scene.getHeight() - 100);
            bottomLine.setStrokeWidth(10);
            this.createdLines.add(bottomLine);
            
            Line Left1 = new Line(200, 550, 600, 550);
            Left1.setStrokeWidth(10);
            this.createdLines.add(Left1);
            
            Line Right1 = new Line(1200, 550, 1600, 550);
            Right1.setStrokeWidth(10);
            this.createdLines.add(Right1);
            
            
            root.getChildren().addAll(this.createdLines); 
        }
        else
        {
            //add lines from array
            root.getChildren().addAll(createdLines);
        }

        //create text displaying player information
        Text t2 = new Text(50, 25, "Player1 - Wins: " + player1.getWins());
        t2.setFont(new Font(20));
        Text t1 = new Text(1600, 25, "Player2 - Wins: " + player2.getWins());
        t1.setFont(new Font(20));
        root.getChildren().add(t1);
        root.getChildren().add(t2);
        
        //start timer and the phase
        moveTimer.start(); 
    }
    /**
     * method playEndAnimation - method plays animation displaying 
     * who won the current game
     */
    public void playEndAnimation()
    {
        //set the text to be displayed
        Text gameEndText = new Text(600, 450, "GAME!\nWinner: Player " + game.getGameWinner());
        gameEndText.setFont(new Font(75));
        gameEndText.setFill(Color.BLACK);
        gameEndText.setStrokeWidth(1.5);
        gameEndText.setStroke(Color.BLACK);
        root.getChildren().add(gameEndText);
        //play fade transition
        FadeTransition ft = new FadeTransition(Duration.seconds(1), gameEndText);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.setCycleCount(1);
        ft.setOnFinished(e ->
        {
            //if the match is over
            if(game.matchState())
            {
                //move to endGame screen
                mainStage.endGame();
            }
            //if match is not over
            else
            {
                //give winner of game draw powers
                game.setCurrentTurn(game.getGameWinner()); 
                //move to intermediate draw phase
                mainStage.newDrawPhase(); 
            }       
        });
        ft.play();
    }
}

