import javafx.scene.image.Image;
/**
 * Write a description of class Normal here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Ninja extends Player
{
   public Ninja()
   {
       super.setCharImage(new Image("ninjaProfile.png"));
       super.setGameImage(new Image("ninjaRunningSheet.png")); 
       super.setName("Ninja");
       super.setImageURL("ninjaProfile.png");
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
