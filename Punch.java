import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty; 
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.shape.Ellipse;
/**
 * Write a description of class Punch here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Punch extends Projectile
{
    private DoubleProperty distanceTraveled;
    public Punch(Player player, int direction)
    {
        super.setInitialSpeed(1);
        super.setFinalSpeed(0);
        super.setWidth(20);
        super.setHeight(20);
        super.setX(player.getX());
        super.setY(player.getY() - 20);
        super.setDirection(direction);
        super.setCurrentSpeed(getInitialSpeed());
        super.setVisible(false);
        super.setExisting(true);
        super.setPlayer(player); 
        //remove later
        super.setGameImage(new Image("shuriken.png"));
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
    public void move()
    {
        double newX = getX() + getDirection() * getCurrentSpeed();
        getHitbox().setCenterX(newX); 
        setX(newX);
        distanceTraveled.set(distanceTraveled.get() + getCurrentSpeed());
    }
}
