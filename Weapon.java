
/**
 * Builds a custom weapon from this class (need to make a subclass for it).  
 *
 * @author D.Chen 
 * @version b1.0
 */
public abstract class Weapon
{
    // instance variables - replace the example below with your own
    protected int attackSpeed;
    protected int onHitDmg;
    
    /**
     * Makes someone take damage
     */
    public void dealDamage(Player player)
    {
        exception();
        player.takeDamage(onHitDmg);
    }
    
    /**
     * Accounts for exceptions (unique attributes) weapons may have when dealing damage. 
     */
    public abstract void exception();
    
    
}
