import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Ellipse; 
import javafx.geometry.Rectangle2D;
import javafx.animation.AnimationTimer; 
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
/**
* This superclass defines the general player class that we can implement custom characters out of 
* (defines in game playable things' methods and basic variables).
* @author D. Chen, updated by Alan Wang
* @version b1.1
**/
public abstract class Player
{
  //needed characteristics of each player: health, speed, coordinates, image
  private int health;
  private double x, y;
  private boolean walking;
  private boolean canFire;
  private boolean canMelee; 
  private boolean canTakeDamage;
  private boolean canJump;
  private int wins;
  private int direction; 
  private int playerID; 
  private int counter;
  private int currentFrame; 
  private Rectangle2D currentClipBounds;
  private Image charSelectImage;
  private Image gameImage; 
  private String name;
  private String imageURL;
  private Ellipse hitbox; 
  private ImageView playerImage;
  private AnimationTimer playerTimer; 
  /**
   * Sets the new health of the player. 
   * @param newHealth the new health of the player
   */
  public void setHealth(int newHealth)
  {
     health = newHealth;
  }
  public void setImagePort(Rectangle2D newClipBounds)
  {
      currentClipBounds = newClipBounds; 
      playerImage.setViewport(newClipBounds); 
  }
  /**
   * Sets the new x coordinate of the player.
   * @param newX the new x coordinate of the player
   */
  public void setX(double newX)
  {
      x = newX;
  }
  
  /**
   * Sets the new y coordinate of the player.
   * @param newY the new y coordinate of the player
   */
  public void setY(double newY)
  {
      y = newY;
  }
 
  /**
  * Changes the x position by either a positive or negative amount, dependent on speed.
  **/
  public void move(double newX, double newY)
  {
      setX(newX);
      setY(newY);
      getHitbox().setCenterX(newX);
      getHitbox().setCenterY(newY);
      getPlayerImage().relocate(getX() - 100, getY() - 100);
  }
  /**
  * Uses melee attack
  **/
  public abstract Projectile useMeleeAttack();
  
  public void playMeleeAnimation(Projectile projectile)
  {
       AttackAnimation animation= new AttackAnimation(getPlayerImage(), Duration.seconds(1), 3, 400, 400, getDirection());
       animation.setCycleCount(1);
       animation.setOnFinished(e -> 
       {
           projectile.setVisible(true);
           projectile.setCurrentSpeed(projectile.getFinalSpeed()); 
           if(getDirection() < 0)
            {
                setImagePort(new Rectangle2D(800, 200, 200, 200));
            }
            else
            {
                setImagePort(new Rectangle2D(0, 0, 200, 200));
            }
           setCanMelee(true);
        });
       animation.play(); 
  }
  /**
   * Fires ranged projectile
   */
  public abstract Projectile fireRangedAttack();
  
  public void playRangedAnimation(Projectile projectile)
  {
       AttackAnimation animation= new AttackAnimation(getPlayerImage(), Duration.seconds(1), 3, 0, 400, getDirection());
       animation.setCycleCount(1);
       animation.setOnFinished(e -> 
       {
           projectile.setVisible(true);
           projectile.setCurrentSpeed(projectile.getFinalSpeed()); 
           if(getDirection() < 0)
            {
                setImagePort(new Rectangle2D(800, 200, 200, 200));
            }
            else
            {
                setImagePort(new Rectangle2D(0, 0, 200, 200));
            }
           setCanFire(true);
        });
       animation.play(); 
  }
  /**
  * Gives each player a unique, one game use skill (work on this later).
  **/
  public abstract void superpower();
  
  /**
  * Adds a win if player won the game.
  **/ 
  public void addWin()
  {
    wins++;
  }
  
  /**
  * Recalculates player health when damage is taken and plays a flickering animation.
  * Timeline credit: 
  * https://stackoverflow.com/questions/36577687/use-timeline-to-trigger-a-void-method-every-certain-seconds-javafx
  * @param Damage the amount of damage the player takes
  **/
  public void takeDamage(int damage) 
  {
      health -= damage;
      FlickerAnimation flicker = new FlickerAnimation(getPlayerImage(), Duration.seconds(3), 15);
      flicker.setCycleCount(1);
      flicker.setOnFinished(e -> 
      {
          getPlayerImage().setVisible(true);
          setCanTakeDamage(true); 
      });
      flicker.play();
  }
  
  /**
  * Sees if the player is dead. If dead, return true. 
  * @return True if player is dead (D:), false otherwise
  **/
  public boolean isDead()
  {
    return (health <= 0);
  }
  
  /**
  * Returns the health of the player.
  * @return Health of the player
  **/
  public int getHealth()
  {
    return health;
  }  
  
  /**
  * Returns the x coordinate of a player.
  * @return The x coordinate of the player
  **/
  public double getX()
  {
    return x;
  }
  
  /**
  * Returns the y coordinate of a player.
  * @return The y coordinate of the player
  **/
  public double getY()
  {
    return y;
  }
  public int getDirection()
  {
      return direction;
  }
  public void setDirection(int newDirection)
  {
      direction = newDirection;
  }  
  public int getPlayerID()
  {
      return playerID;
  }
  public void setPlayerID(int newPlayerID)
  {
      playerID = newPlayerID; 
  }
  /**
  * Returns the wins of a player.
  * @return The wins of a player
  **/
  public int getWins()
  {
    return wins;
  }
  public boolean isWalking()
  {
      return walking;
  }
  public void setWalking(boolean newWalking)
  {
      walking = newWalking;
  }
  public boolean canFire()
  {
      return canFire;
  }
  public void setCanFire(boolean newCanFire)
  {
      canFire = newCanFire;
  }
  public boolean canMelee()
  {
      return canMelee; 
  }
  public void setCanMelee(boolean newCanMelee)
  {
      canMelee = newCanMelee;
  }
  public boolean canTakeDamage()
  {
      return canTakeDamage;
  }
  public void setCanTakeDamage(boolean newCanTakeDamage)
  {
      canTakeDamage = newCanTakeDamage;
  }
  public boolean canJump()
  {
      return canJump;
  }
  public void setCanJump(boolean newCanJump)
  {
      canJump = newCanJump;
  }
  public Image getCharImage()
  {
      return charSelectImage;
  }
  public Image getGameImage()
  {
      return gameImage;
  }
  public void setCharImage(Image image)
  {
      charSelectImage = image;
  }
  public void setGameImage(Image image)
  {
      gameImage = image; 
  }
  public String getName()
  {
      return name; 
  }
  public void setName(String newName)
  {
      name = newName;
  }
  /**
  * Returns the image of a player.
  * @return The image of the player
  **/
  public String getImageURL()
  {
      return imageURL;
  }
  /**
   * Sets the image of a player
   * @String url the string url of the image
   */
  public void setImageURL(String url)
  {
      imageURL = url;
  }
  public Ellipse getHitbox()
  {
      return hitbox;
  }
  public void setHitbox(Ellipse newHitbox)
  {
      hitbox = newHitbox; 
  }
  public ImageView getPlayerImage()
  {
      return playerImage;
  }
  public void setPlayerImage(ImageView newPlayerImage)
  {
      playerImage = newPlayerImage;
  }
  public AnimationTimer getPlayerTimer()
  {
      return playerTimer; 
  }
  public void setPlayerTimer(AnimationTimer newTimer)
  {
      playerTimer = newTimer;
  }
  public int getCounter()
  {
      return counter;
  }
  public void setCounter(int newValue)
  {
      counter = newValue;
  }
  public int getCurrentFrame()
  {
      return currentFrame;
  }
   public void setCurrentFrame(int newValue)
  {
      currentFrame = newValue;
  }
}
