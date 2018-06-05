import javafx.scene.image.Image;
import javafx.scene.shape.Ellipse;
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
       super.setGameImage(new Image("ninjaSpriteSheet.png")); 
       super.setName("Ninja");
       super.setImageURL("ninjaProfile.png");
       super.setHitbox(new Ellipse(super.getX(), super.getY(), 70, 110));
   }
   public Projectile fireRangedAttack()
   {
       Shuriken shuriken = new Shuriken(this, getDirection());
       return shuriken;
   }
   public Projectile useMeleeAttack()
   {
       //replace this later
       DaggerStab daggerStab = new DaggerStab(this, getDirection());
       return daggerStab;
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
