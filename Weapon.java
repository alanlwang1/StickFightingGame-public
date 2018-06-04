import javafx.scene.image.Image;
/**
 * Builds a custom weapon from this class (need to make a subclass for it).  
 *
 * @author D.Chen 
 * @version b1.0
 */
public abstract class Weapon
{
    // instance variables - replace the example below with your own
    private double speed;
    private int onHitDmg;
    private double x, y;
    private double width, height; 
    private Image gameImage;  
    
    /**
     * Makes someone take damage
     */
    public void dealDamage(Player player)
    {
        player.takeDamage(onHitDmg);
    }
    
    
    /**
     * Returns attack speed of the weapon.
     * @return attack speed of the weapon
     */
    public double getSpeed()
    {
        return speed;
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
    public void setSpeed(int newSpeed)
    {
        speed = newSpeed;
    }
    
    /**
     * Sets the on hit damage of a weapon.
     * @param newOHD the new on hit damage of a weapon.
     */
    public void setOHD(int newOHD)
    {
        onHitDmg = newOHD;
    }
    public double getX()
    {
        return x;
    }
    public void setX(double newX)
    {
        x = newX;
    }
    public double getY()
    {
        return y; 
    }
    public double setY()
    {
        return y;
    }
    public double getWidth()
    {
        return width;
    }
    public void setWidth(double newWidth)
    {
        width = newWidth;
    }
    public double getHeight()
    {
        return height;
    }
    public void setHeight(double newHeight)
    {
        height = newHeight; 
    }
    public Image getGameImage()
    {
        return gameImage; 
    }
    public void setGameImage(Image newGameImage)
    {
        gameImage = newGameImage; 
    }
}    
