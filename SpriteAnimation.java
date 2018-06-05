import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
/**
 * class SpriteAnimation
 * 
 * @author Created by Michael Heinrichs, modified by Alan Wang
 */
public class SpriteAnimation extends Transition {

    private final ImageView imageView;
    private final int count;
    private final int offsetX;
    private final int offsetY;
    private final int direction; 

    private int lastIndex;

    public SpriteAnimation(ImageView imageView,  Duration duration, int count, int offsetX, int offsetY, int direction) 
    {
        this.imageView = imageView;
        this.count     = count;
        this.offsetX   = offsetX;
        this.offsetY   = offsetY;
        this.direction = direction; 
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    protected void interpolate(double k) {
        final int x, y;
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {
            if(direction < 0)
            {
                x = 800 - offsetX - index * 200;
                y = offsetY + 200;
            }
            else
            {
                x = offsetX + index * 200 - 200;
                y = offsetY;
            }
            imageView.setViewport(new Rectangle2D(x, y, 200, 200));
            lastIndex = index;
        }
    }
}