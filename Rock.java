import javafx.scene.image.Image;
import javafx.scene.shape.Ellipse;
/**
 * Write a description of class Rock here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Rock extends Projectile
{
    /**
     * Constructor for objects of class Rock
     */
    public Rock(Player player, int direction)
    {
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
