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
       charSelectImage = new Image("normal.png");
       gameImage = new Image("stick figure.png"); 
       name = "Normal";
       super.setImageURL("normal.png");
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
