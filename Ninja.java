import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Ellipse;
import javafx.geometry.Rectangle2D;
import javafx.animation.AnimationTimer; 
/**
 * Ninja class: type of fighter with different images
 * @author A.Wang, R.Wei
 */
public class Ninja extends Player
{
   /**
    * Ninja constructor: creates a ninja with a playerID and starting Direction
    * @param starting Direction, and ID (1 or 2)
    */
   public Ninja(int startingDirection, int playerID)
   {
       //intial status of character
       super.setHealth(3);
       super.setCharImage(new Image("ninjaProfile.png"));
       super.setGameImage(new Image("ninjaSpriteSheet.png")); 
       super.setName("Ninja");
       super.setDirection(startingDirection);
       super.setPlayerID(playerID);
       super.setImageURL("ninjaProfile trans.png");
       super.setHitbox(new Ellipse(super.getX(), super.getY(), 70, 110));
       super.setCanFire(true);
       super.setCanMelee(true);
       super.setCanTakeDamage(true);
       
       //animations
       super.setPlayerImage(new ImageView(getGameImage()));
       if(super.getDirection() < 0)
            super.setImagePort(new Rectangle2D(800, 200, 200, 200));
       else
         if(super.getDirection() > 0)
            super.setImagePort(new Rectangle2D(800, 200, 200, 200));
       super.setPlayerTimer(new AnimationTimer()
       {
            @Override
            public void handle(long now)
            {
                Rectangle2D rect = new Rectangle2D(0, 0, 200, 200);
                //if it is time to change frame
                if(getCounter() == 1)
                    setCurrentFrame((getCurrentFrame() + 1) % 4);
                //change frame of imageView      
                if(getDirection() < 0)
                    rect = new Rectangle2D(600 - getCurrentFrame() * 200, 200, 200, 200);
                else
                if(getDirection() > 0)
                    rect = new Rectangle2D(200 + getCurrentFrame() * 200, 0, 200, 200);
                setImagePort(rect);
                //increment counter
                setCounter((getCounter() + 1) % 10); 
            }
       });
   }
   /**
    * Creates and returns the object of the ranged attack
    * @return Shuriken class
    */
   public Projectile fireRangedAttack()
   {
       Shuriken shuriken = new Shuriken(this, getDirection());
       return shuriken;
   }
   /**
    * Creates and returns the object of the melee attack
    * @return daggerStab (subclass of Projectile)
    */
   public Projectile useMeleeAttack()
   {
       //replace this later
       DaggerStab daggerStab = new DaggerStab(this, getDirection());
       return daggerStab;
   }
}
