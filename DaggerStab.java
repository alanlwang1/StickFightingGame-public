import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty; 
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.shape.Ellipse;
/**
 * Method for Melee Attacks by Ninja
 * @author Alan Wang, Ryan Wei
 */
public class DaggerStab extends Projectile
{
    private DoubleProperty distanceTraveled;
    /**
     * 
     */
    public DaggerStab(Player player, int direction)
    {
        super.setInitialSpeed(1.5);
        super.setFinalSpeed(0);
        super.setWidth(20);
        super.setHeight(20);
        super.setX(player.getX());
        super.setY(player.getY() + 20);
        super.setDirection(direction);
        super.setCurrentSpeed(getInitialSpeed());
        super.setVisible(false);
        super.setExisting(true);
        super.setPlayer(player); 

        super.setHitbox(new Ellipse(getX(), getY(), 10, 10)); 
        distanceTraveled = new SimpleDoubleProperty(); 
        //add listener to mark punch as gone when traveled certain distance
        
        distanceTraveled.addListener(new ChangeListener<Number>()
        {
            @Override
                public void changed(ObservableValue<? extends Number> o, Number oldVal, Number newVal)
                {
                    if(newVal.doubleValue() >= 1000)
                        setExisting(false);
                }
        });
        
    }
    @Override
    /**
     * Move Method: Moves the hitbox for the projectile and measures the distance
     */
    public void move()
    {
        double newX = getX() + getDirection() * getCurrentSpeed();
        getHitbox().setCenterX(newX); 
        setX(newX);
        distanceTraveled.set(distanceTraveled.get() + getCurrentSpeed());        
        setX(getX() + getDirection() * getCurrentSpeed());
        distanceTraveled.set(distanceTraveled.get() + getCurrentSpeed());
    }
}
