import javafx.scene.image.Image;
import javafx.scene.image.ImageView; 
import javafx.scene.shape.Ellipse;
/**
 * Builds a custom weapon from this class (need to make a subclass for it).  
 *
 * @author D.Chen, R. Wei
 * @version b1.0
 */
public abstract class Projectile
{
    private double initialSpeed;
    private double finalSpeed;
    private double currentSpeed; 
    private int onHitDmg;
    private int direction; 
    private double x, y;
    private double width, height; 
    private Image gameImage;  
    private ImageView projectileImage; 
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
    
    /**
     * moves the hitbox for the projectile and updates its location
     */
    public void move()
    {
        double newX = x + direction * currentSpeed;
        getHitbox().setCenterX(newX); 
        projectileImage.setX(newX - (width / 2));
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
    
    /**
     * sets the current attack speed of the projectile
     * @param new Speed 
     */
    public void setCurrentSpeed(double newSpeed)
    {
        currentSpeed = newSpeed; 
    }
    /**
     * Returns the intial speed of the projectile
     */
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
    /**
     * returns the max speed of the projectile
     * @return max speed of projectile
     */
    public double getFinalSpeed()
    {
        return finalSpeed;
    }
    /**
     * Set the final speed the projectile reaches
     * @param final Speed
     */
    public void setFinalSpeed(double newFinalSpeed)
    {
        finalSpeed = newFinalSpeed;
    }
    /**
     * returns the x
     * @returns the x location of the projectile
     */
    public double getX()
    {
        return x;
    }
    /**
     * sets the X location of the projectile
     * @param the new x location of the projectile
     */
    public void setX(double newX)
    {
        x = newX;
    }
    /**
     * returns the y of projectile
     * @returns y location of the projectile
     */
    public double getY()
    {
        return y; 
    }
    /**
     * sets the Y location of the particle
     * @param y loc
     */
    public void setY(double newY)
    {
        y = newY;
    }
    /**
     * returns width of proj
     * @return width of proj
     */
    public double getWidth()
    {
        return width;
    }
    /**
     * sets width of proj
     * @param new width of proj
     */
    public void setWidth(double newWidth)
    {
        width = newWidth;
    }
    /**
     * returns height of proj
     * @return height of proj
     */
    public double getHeight()
    {
        return height;
    }
    /**
     * sets height of proj
     * @param new height of proj hitbox
     */
    public void setHeight(double newHeight)
    {
        height = newHeight; 
    }
    /**
     * returns the direction the projectile will fire
     * @return direction in degrees
     */
    public int getDirection()
    {
        return direction;
    }
    /**
     * changes direction projectile will be fired
     * @param new fire direction
     */
    public void setDirection(int newDirection)
    {
        direction = newDirection; 
    }
    /**
     * returns boolean representing whether or not the boolean should be visible
     * @return bool isVisible
     */
    public boolean isVisible()
    {
        return isVisible; 
    }
    /**
     * sets boolean value for visible
     * @param is Visible or no (boolean)
     */
    public void setVisible(boolean newVisible)
    {
        isVisible = newVisible; 
    }
    /**
     * is in existence or no?
     * @return existence
     */
    public boolean isExisting()
    {
        return isExisting;
    }
    /**
     * sets in existence or no
     * @param new existence
     */
    public void setExisting(boolean newExisting)
    {
        isExisting = newExisting; 
    }
    /**
     * returns image of projectile
     * @return Image of proj
     */
    public Image getGameImage()
    {
        return gameImage; 
    }
    /**
     * sets Image for game
     * @param Image class that'll become the projectile
     */
    public void setGameImage(Image newGameImage)
    {
        
        gameImage = newGameImage; 
    }
    public ImageView getProjectileImage()
    {
        return projectileImage;
    }
    public void setProjectileImage(ImageView newProjectileImage)
    {
        projectileImage = newProjectileImage;
    }
    /**
     * returns player the projectile is associated with
     * @return Player
     */
    public Player getPlayer()
    {
        return player;
    }
    /**
     * sets the player the projectile is associated with
     * @param Player
     */
    public void setPlayer(Player newPlayer)
    {
        player = newPlayer; 
    }
    /**
     * return the hitbox (invisible shape under image) of the file
     * @return Ellipse hitbox shape
     */
    public Ellipse getHitbox()
    {
        return hitbox;
    }
    /**
     * set the hitbox for the Ellipse
     * @param the ellipse the hitbox will be
     */
    public void setHitbox(Ellipse newHitbox)
    {
        hitbox = newHitbox;
    }
}    
