import javafx.scene.image.Image;
/**
 * Write a description of class Normal here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Normal extends Player
{
   public Normal()
   {
       super.setCharImage(new Image("normalProfile.png"));
       super.setGameImage(new Image("normalSpriteSheet.png")); 
       super.setName("Normal");
       super.setImageURL("normalProfile.png");
   }
   public void fire()
   {
   }
   public void pickUp()
   {
   }
   public void jump()
   {
   }
   public void move()
   {
   }
   public void superpower()
   {
   }
}
