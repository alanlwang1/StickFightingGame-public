import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
/**
 * class CountDownAnimation - class used to model the countdown animation for 
 * transitioning between drawphase and combat phase
 * 
 * @author Original Sprite Animation Created by Michael Heinrichs, modified by Alan Wang
 */
public class CountDownAnimation extends Transition 
{

    private final ImageView imageView;
    private final int count;

    private int lastIndex;

    public CountDownAnimation(ImageView imageView, Duration duration, int count) 
    {
        this.imageView = imageView;
        this.count     = count;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    protected void interpolate(double k) 
    {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        imageView.setViewport(new Rectangle2D(200 * index, 0, 200, 200)); 
    }
}