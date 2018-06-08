import javafx.scene.image.Image;
import javafx.scene.shape.Ellipse;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;
import javafx.animation.AnimationTimer;
/**
 * Normal class: " Normal " version of player/fighter. No fancy design like ninja
 * @author A.Wang, R.Wei
 */
public class Normal extends Player
{
   /**
    * Normal constructor: creates a character with a starting id and direction
    * @param startingDirection, id
    */
   public Normal(int startingDirection, int playerID)
   {
       //intial values
       super.setHealth(3);
       super.setCharImage(new Image("normalProfile.png"));
       super.setGameImage(new Image("normalSpriteSheet.png")); 
       super.setName("Normal");
       super.setDirection(startingDirection);
       super.setPlayerID(playerID);
       super.setImageURL("normalProfile trans.png");
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
    * Creates and returns the object of the projectile thrown
    * @return ranged projectile
    */
   public Projectile fireRangedAttack()
   {
       Rock rock = new Rock(this, getDirection()); 
       return rock;
   }
   /**
    * Creates and returns object of the melee attack
    * @return melee projectile
    */
   public Projectile useMeleeAttack()
   {
       Punch punch = new Punch(this, getDirection());
       return punch; 
   }

}
