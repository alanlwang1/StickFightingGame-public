/**
* this superclass defines the general player class that we can implement custom characters out of. 
* (defines in game playable things' methods and basic variables)
* @author D. Chen
* @created 05-17-18
* @modified 05-17-18
**/
public abstract class Player
{
  //needed characteristics of each player: health, speed, coordinates, image
  protected int health;
  protected int speed;
  protected int x, y;
  protected int wins;
  //image of the player? 
  
  /**
  * @summary Fires or uses weapon (can be melee or ranged)
  **/
  public abstract void fire() {}
  
  /**
  * @summary changes the y position by either a positive or negative amount
  **/
  public abstract void jump() {}
  
  /**
  * @summary changes the x position by either a positive or negative amount, dependent on speed
  **/
  public abstract void move() {}
  
  /**
  * @summary Adds a win if player won the game
  **/ 
  public void addWin()
  {
    wins++;
  }
  /**
  * @summary Recalculates player health when damage is taken.
  * @param damage: the amount of damage the player takes
  **/
  public void takeDamage(int damage) 
  {
    health -= damage;
  }
  
  /**
  * @summary Sees if the player is dead. If dead, return true. 
  * @return true if player is dead (D:), false if player survived.
  **/
  public boolean isDead()
  {
    return (health <= 0);
  }
  
  /**
  * @summary Returns the health of the player
  * @return health of the player
  **/
  public int getHealth()
  {
    return health;
  }
  
  /**
  * @summary Returns the speed of a player
  * @return the speed of the player
  **/
  public int getSpeed()
  {
    return speed;
  }
  
  /**
  * @summary Returns the x coordinate of a player
  * @return the x coordinate of the player
  **/
  public int getX()
  {
    return x;
  }
  
  /**
  * @summary Returns the y coordinate of a player
  * @return the y coordinate of the player
  **/
  public int getY()
  {
    return y;
  }
  
  /**
  * @summary Returns the wins of a player
  * @return the wins of a player
  **/
  public int getWins()
  {
    return wins;
  }
  /**
  * @summary Returns the image of a player
  * @return the image of the player
  **/
  //public idk getImage()
  //{
  //  return something;
  //}
}
