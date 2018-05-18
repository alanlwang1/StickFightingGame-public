
/**
 * Builds a custom weapon from this class (need to make a subclass for it).  
 *
 * @author D.Chen 
 * @version b1.0
 */
public abstract class Weapon
{
    // instance variables - replace the example below with your own
    private int attackSpeed;
    private int onHitDmg;
    
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
    
    /**
     * Returns attack speed of the weapon.
     * @return attack speed of the weapon
     */
    public int getAS()
    {
        return attackSpeed;
    }
    
    /**
     * Returns the on hit damage of a weapon.
     * @return the on hit damage of the weapon.
     */
    public int getOHD()
    {
        return onHitDmg;
    }
    
    /**
     * Sets the attack speed of the weapon.
     * @param newAS the new attack speed of the weapon.
     */
    public void setAS(int newAS)
    {
        attackSpeed = newAS;
    }
    
    /**
     * Sets the on hit damage of a weapon.
     * @param newOHD the new on hit damage of a weapon.
     */
    public void setOHD(int newOHD)
    {
        onHitDmg = newOHD;
    }
    
}    
