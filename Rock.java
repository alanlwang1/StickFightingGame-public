import javafx.scene.image.Image;
import javafx.scene.shape.Ellipse;
/**
 * Rock class: class that sets values for rock throwing projectile (used by Normal Player)
 * @author Alan Wang, Ryan Wei
 */
public class Rock extends Projectile
{
    /**
     * Constructor for objects of class Rock
     */
    public Rock(Player player, int direction)
    {
        //set default values like speed and size
        super.setInitialSpeed(2);
        super.setFinalSpeed(20);
        super.setWidth(48);
        super.setHeight(48);
        super.setX(player.getX() - 50);
        super.setY(player.getY() - 50);
        super.setDirection(direction);
        super.setCurrentSpeed(getInitialSpeed());
        super.setVisible(false);
        super.setExisting(true);
        super.setPlayer(player); 
        super.setGameImage(new Image("rock.png"));
        super.setHitbox(new Ellipse(getX(), getY(), 24, 24)); 
    }
}
