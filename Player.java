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
  * Fires or uses weapon (can be melee or ranged)
  **/
  public abstract void fire() {}
  
  /**
  * Jumps a certain distance up on the y axis
  **/
  public abstract void jump() {}
  
  /**
  * Moves a certain distance on the x plane over a certain period of time, dependent on speed
  **/
  public abstract void move() {}
  
  /**
  * Adds a win if player won the game
  **/ 
  public void addWin()
  {
    wins++;
  }
  /**
  * Recalculates player health when damage is taken.
  * @param damage: the amount of damage the player takes
  **/
  public void takeDamage(int damage) 
  {
    health -= damage;
  }
  
  /**
  * Sees if the player is dead. If dead, return true. 
  * @return true if player is dead (D:), false if player survived.
  **/
  public boolean isDead()
  {
    return (health <= 0);
  }
  
  /**
  * Returns the health of the player
  * @return health of the player
  **/
  public int getHealth()
  {
    return health;
  }
  
  /**
  * Returns the speed of a player
  * @return the speed of the player
  **/
  public int getSpeed()
  {
    return speed;
  }
  
  /**
  * Returns the x coordinate of a player
  * @return the x coordinate of the player
  **/
  public int getX()
  {
    return x;
  }
  
  /**
  * Returns the y coordinate of a player
  * @return the y coordinate of the player
  **/
  public int getY()
  {
    return y;
  }
  
  /**
  * Returns the wins of a player
  * @return the wins of a player
  **/
  public int getWins()
  {
    return wins;
  }
  /**
  * Returns the image of a player
  * @return the image of the player
  **/
  //public idk getImage()
  //{
  //  return something;
  //}
}
