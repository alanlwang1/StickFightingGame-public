import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Ellipse;
import javafx.geometry.Rectangle2D;
import javafx.animation.AnimationTimer; 
/**
 * Ninja class: type of fighter with different images
 */
public class Ninja extends Player
{
   public Ninja(int startingDirection, int playerID)
   {
       //intial status of character
       super.setHealth(3);
       super.setCharImage(new Image("ninjaProfile.png"));
       super.setGameImage(new Image("ninjaSpriteSheet.png")); 
       super.setName("Ninja");
       super.setDirection(startingDirection);
       super.setPlayerID(playerID);
       super.setImageURL("ninjaProfile.png");
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
}
