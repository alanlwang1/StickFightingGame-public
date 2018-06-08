import javafx.scene.image.Image;
import javafx.scene.shape.Ellipse;
/**
 * Shuriken class: class that sets values for star throwing projectile (used by Ninja Player)
 * @author Alan Wang
 */
public class Shuriken extends Projectile
{
    /**
     * Constructor for objects of class Shuriken
     */
    public Shuriken(Player player, int direction)
    {
        //set intial values
        super.setInitialSpeed(1);
        super.setFinalSpeed(25); 
        super.setWidth(32);
        super.setHeight(32); 
        super.setX(player.getX() + 50);
        super.setY(player.getY() - 25);
        super.setDirection(direction);
        super.setCurrentSpeed(getInitialSpeed());
        super.setVisible(false); 
        super.setExisting(true);
        super.setPlayer(player); 
        super.setGameImage(new Image("shuriken.png")); 
        super.setHitbox(new Ellipse(getX(), getY(), 16, 16));
    }
}
