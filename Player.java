import javafx.scene.image.Image;
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
  private int speed;
  private int x, y;
  private int wins;
  //these have to be getters and setters
  private Image charSelectImage;
  private Image gameImage; 
  private String name;
  private String imageURL;
  
  /**
   * Sets the new health of the player. 
   * @param newHealth the new health of the player
   */
  public void setHealth(int newHealth)
  {
     health = newHealth;
  }
  
  /**
   * Sets the new speed of the player.
   * @param newSpeed the new speed of the player
   */
  public void setSpeed(int newSpeed)
  {
      speed = newSpeed;
  }
  
  /**
   * Sets the new x coordinate of the player.
   * @param newX the new x coordinate of the player
   */
  public void setX(int newX)
  {
      x = newX;
  }
  
  /**
   * Sets the new y coordinate of the player.
   * @param newY the new y coordinate of the player
   */
  public void setY(int newY)
  {
      y = newY;
  }
 
  
  /**
  * Fires or uses weapon (can be melee or ranged).
  **/
  public abstract void fire();
  
  /**
  * Picks up a weapon.
  **/
  public abstract void pickUp();
  
  /**
  * Changes the y position by either a positive or negative amount.
  **/
  public abstract void jump();
  
  /**
  * Changes the x position by either a positive or negative amount, dependent on speed.
  **/
  public abstract void move();
  
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
  * Recalculates player health when damage is taken.
  * @param Damage the amount of damage the player takes
  **/
  public void takeDamage(int damage) 
  {
    health -= damage;
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
  * Returns the speed of a player.
  * @return The speed of the player
  **/
  public int getSpeed()
  {
    return speed;
  }
  
  /**
  * Returns the x coordinate of a player.
  * @return The x coordinate of the player
  **/
  public int getX()
  {
    return x;
  }
  
  /**
  * Returns the y coordinate of a player.
  * @return The y coordinate of the player
  **/
  public int getY()
  {
    return y;
  }
  
  /**
  * Returns the wins of a player.
  * @return The wins of a player
  **/
  public int getWins()
  {
    return wins;
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
}
