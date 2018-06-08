import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
/**
 * class FlickerAnimation - class to model a flickering animation used when a player
 * takes damage
 * 
 * @author Original SpriteAnimation Created by Michael Heinrichs, modified by Alan Wang
 * @version 060718
 */
public class FlickerAnimation extends Transition 
{

    private final ImageView imageView;
    private final int count;

    private int lastIndex;

    public FlickerAnimation(ImageView imageView,  Duration duration, int count) 
    {
        this.imageView = imageView;
        this.count     = count;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    protected void interpolate(double k) 
    {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        //"flicker" the imageview by alternating visible status 
        if (index % 2 == 0) 
            imageView.setVisible(false);
        else
            imageView.setVisible(true);
        lastIndex = index;
    }
}

