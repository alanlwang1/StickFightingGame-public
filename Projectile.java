import javafx.scene.image.Image;
import javafx.scene.shape.Ellipse;
/**
 * Builds a custom weapon from this class (need to make a subclass for it).  
 *
 * @author D.Chen 
 * @version b1.0
 */
public abstract class Projectile
{
    // instance variables - replace the example below with your own
    private double initialSpeed;
    private double finalSpeed;
    private double currentSpeed; 
    private int onHitDmg;
    private int direction; 
    private double x, y;
    private double width, height; 
    private Image gameImage;  
    private Player player;
    private boolean isVisible; 
    private boolean isExisting; 
    private Ellipse hitbox;
    /**
     * Makes someone take damage
     */
    public void dealDamage(Player player)
    {
        player.takeDamage(onHitDmg);
    }
    public void move()
    {
        double newX = x + direction * currentSpeed;
        getHitbox().setCenterX(newX); 
        x = newX;
    }
    
    /**
     * Returns attack speed of the weapon.
     * @return attack speed of the weapon
     */
    public double getCurrentSpeed()
    {
        return currentSpeed;
    }
    public void setCurrentSpeed(double newSpeed)
    {
        currentSpeed = newSpeed; 
    }
    public double getInitialSpeed()
    {
        return initialSpeed;
    }
    /**
     * Sets the initial speed of the weapon.
     * @param newAS the new initial speed of the weapon.
     */
    public void setInitialSpeed(double newInitialSpeed)
    {
        initialSpeed = newInitialSpeed;
    }
    public double getFinalSpeed()
    {
        return finalSpeed;
    }
    public void setFinalSpeed(double newFinalSpeed)
    {
        finalSpeed = newFinalSpeed;
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
    public void setY(double newY)
    {
        y = newY;
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
    public int getDirection()
    {
        return direction;
    }
    public void setDirection(int newDirection)
    {
        direction = newDirection; 
    }
    public boolean isVisible()
    {
        return isVisible; 
    }
    public void setVisible(boolean newVisible)
    {
        isVisible = newVisible; 
    }
    public boolean isExisting()
    {
        return isExisting;
    }
    public void setExisting(boolean newExisting)
    {
        isExisting = newExisting; 
    }
    public Image getGameImage()
    {
        return gameImage; 
    }
    public void setGameImage(Image newGameImage)
    {
        gameImage = newGameImage; 
    }
    public Player getPlayer()
    {
        return player;
    }
    public void setPlayer(Player newPlayer)
    {
        player = newPlayer; 
    }
    public Ellipse getHitbox()
    {
        return hitbox;
    }
    public void setHitbox(Ellipse newHitbox)
    {
        hitbox = newHitbox;
    }
}    
