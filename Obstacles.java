/**
* Makes the obstacles that the players can put down. 
* @author D. Chen
* @version b1.0
**/
public abstract class Obstacles
{
  //instantiate needed variables
  protected int health;
  protected int length;
  protected int width;
  
  /**
  * Places an object down.
  **/
  public abstract void place();
  
  /**
  * Recalculates player health when damage is taken.
  * @param Damage the amount of damage the player takes
  **/
  public void takeDamage(int damage) 
  {
    health -= damage;
  }
  
}
