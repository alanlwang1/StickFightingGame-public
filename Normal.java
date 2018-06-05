import javafx.scene.image.Image;
import javafx.scene.shape.Ellipse;
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
       super.setHitbox(new Ellipse(super.getX(), super.getY(), 70, 110));
       super.setCanFire(true);
       super.setCanMelee(true);
   }
   public Projectile fireRangedAttack()
   {
       Rock rock = new Rock(this, getDirection()); 
       return rock;
   }
   public Projectile useMeleeAttack()
   {
       //replace this later
       Punch punch = new Punch(this, getDirection());
       return punch; 
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
