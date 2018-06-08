import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Ellipse; 
import javafx.geometry.Rectangle2D;
import javafx.animation.AnimationTimer; 
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
/**
 * This superclass defines the general player class that we can implement custom characters out of 
 * (defines in game playable things' methods and basic variables).
 * @author D. Chen, updated by Alan Wang
 * @version b1.1
 **/
public abstract class Player implements Cloneable
{
    //needed characteristics of each player: health, speed, coordinates, image
    private int health;
    private double x, y;
    private boolean walking;
    private boolean canFire;
    private boolean canMelee; 
    private boolean canTakeDamage;
    private boolean canJump;
    private boolean isCrouching; 
    private int wins;
    private int direction; 
    private int playerID; 
    private int counter;
    private int currentFrame; 
    private Rectangle2D currentClipBounds;
    private Image charSelectImage;
    private Image gameImage; 
    private String name;
    private String imageURL;
    private Ellipse hitbox; 
    private ImageView playerImage;
    private AnimationTimer playerTimer; 
    /**
     * Sets the new health of the player. 
     * @param newHealth the new health of the player
     */
    public void setHealth(int newHealth)
    {
        health = newHealth;
    }

    public void setImagePort(Rectangle2D newClipBounds)
    {
        currentClipBounds = newClipBounds; 
        playerImage.setViewport(newClipBounds); 
    }

    /**
     * Sets the new x coordinate of the player.
     * @param newX the new x coordinate of the player
     */
    public void setX(double newX)
    {
        x = newX;
    }

    /**
     * Sets the new y coordinate of the player.
     * @param newY the new y coordinate of the player
     */
    public void setY(double newY)
    {
        y = newY;
    }

    /**
     * Changes the x position by either a positive or negative amount, dependent on speed.
     **/
    public void move(double newX, double newY)
    {
        double xDiff = newX - getX();
        double yDiff = newY - getY(); 
        setX(newX);
        setY(newY);
        getHitbox().setCenterX(getHitbox().getCenterX() + xDiff);
        getHitbox().setCenterY(getHitbox().getCenterY() + yDiff);
        getPlayerImage().relocate(getX() - 100, getY() - 100);
    }

    /**
     * Uses melee attack
     **/
    public abstract Projectile useMeleeAttack();

    /**
     * Plays melee animation for this player
     * 
     * @param projectile - the projectile being used for this melee animation
     */
    public void playMeleeAnimation(Projectile projectile)
    {
        AttackAnimation animation= new AttackAnimation(getPlayerImage(), Duration.seconds(1), 3, 400, 400, getDirection());
        animation.setCycleCount(1);
        animation.setOnFinished(e -> 
            {
                projectile.setVisible(true);
                projectile.setCurrentSpeed(projectile.getFinalSpeed()); 
                if(getDirection() < 0)
                {
                    setImagePort(new Rectangle2D(800, 200, 200, 200));
                }
                else
                {
                    setImagePort(new Rectangle2D(0, 0, 200, 200));
                }
                setCanMelee(true);
            });
        animation.play(); 
    }

    /**
     * Fires ranged projectile
     */
    public abstract Projectile fireRangedAttack();

    /**
     * Play ranged Animation for this player
     * 
     * @param projectile - the projectile being used for this ranged animation
     */
    public void playRangedAnimation(Projectile projectile)
    {
        AttackAnimation animation= new AttackAnimation(getPlayerImage(), Duration.seconds(1), 3, 0, 400, getDirection());
        animation.setCycleCount(1);
        animation.setOnFinished(e -> 
            {
                projectile.setVisible(true);
                projectile.setCurrentSpeed(projectile.getFinalSpeed()); 
                if(getDirection() < 0)
                {
                    setImagePort(new Rectangle2D(800, 200, 200, 200));
                }
                else
                {
                    setImagePort(new Rectangle2D(0, 0, 200, 200));
                }
                setCanFire(true);
            });
        animation.play(); 
    }

    /**
     * Adds a win if player won the game.
     **/ 
    public void addWin()
    {
        wins++;
    }

    /**
     * Recalculates player health when damage is taken and plays a flickering animation.
     * @param Damage the amount of damage the player takes
     **/
    public void takeDamage(int damage) 
    {
        health -= damage;
        FlickerAnimation flicker = new FlickerAnimation(getPlayerImage(), Duration.seconds(3), 15);
        flicker.setCycleCount(1);
        flicker.setOnFinished(e -> 
            {
                getPlayerImage().setVisible(true);
                setCanTakeDamage(true); 
            });
        flicker.play();
    }

    /**
     * Sees if the player is dead. If dead, return true. 
     * @return True if player is dead (D:), false otherwise
     **/
    public boolean isDead()
    {
        return (health <= 0);
    }

    /**
     * Returns the health of the player.
     * @return Health of the player
     **/
    public int getHealth()
    {
        return health;
    }  

    /**
     * Returns the x coordinate of a player.
     * @return The x coordinate of the player
     **/
    public double getX()
    {
        return x;
    }

    /**
     * Returns the y coordinate of a player.
     * @return The y coordinate of the player
     **/
    public double getY()
    {
        return y;
    }

    /**
     * Returns the direction this player is facing in
     * positive for right, negative for left
     * 
     * @return direction The direction this player is facing in
     */
    public int getDirection()
    {
        return direction;
    }

    /**
     * Sets the direction this player is facing
     * positive for right, negative for left
     * 
     * @param newDirection The new direction the player is facing in
     */
    public void setDirection(int newDirection)
    {
        direction = newDirection;
    }  

    /**
     * Returns the id of this player object
     * 
     * @return the id of this player object
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * Sets the id of this player object
     * 
     * @param newPlayerID The new id of this player object
     */
    public void setPlayerID(int newPlayerID)
    {
        playerID = newPlayerID; 
    }

    /**
     * Returns the wins of a player.
     * @return The wins of a player
     **/
    public int getWins()
    {
        return wins;
    }

    /**
     * Returns the boolean controlling whether the player is walking
     * 
     * @return the boolean controlling whether the player is walking
     */
    public boolean isWalking()
    {
        return walking;
    }

    /**
     * Sets the boolean controlling whether the player is waparamlking
     * 
     * @param newWalking The new state of the boolean
     */
    public void setWalking(boolean newWalking)
    {
        walking = newWalking;
    }

    /**
     * Returns the boolean controlling whether the player can fire
     * 
     * @return the boolean controlling whether the player can fire
     */
    public boolean canFire()
    {
        return canFire;
    }

    /**
     * Sets the boolean controlling whether the player can fire
     * 
     * @param newCanFire The new state of the boolean
     */
    public void setCanFire(boolean newCanFire)
    {
        canFire = newCanFire;
    }

    /**
     * Returns the boolean controlling whether the player can melee
     * 
     * @return the boolean controlling whether the player can melee
     */
    public boolean canMelee()
    {
        return canMelee; 
    }

    /**
     * Sets the boolean controlling whether the player can Melee
     * 
     * @param newCanMelee The new state of the boolean
     */
    public void setCanMelee(boolean newCanMelee)
    {
        canMelee = newCanMelee;
    }

    /**
     * Returns the boolean controlling whether the player can take damage
     * 
     * @return the boolean controlling whether the player can take damage
     */
    public boolean canTakeDamage()
    {
        return canTakeDamage;
    }

    /**
     * Sets the boolean controlling whether the player can take damage
     * 
     * @param newCanTakeDamage The new state of this boolean
     */
    public void setCanTakeDamage(boolean newCanTakeDamage)
    {
        canTakeDamage = newCanTakeDamage;
    }

    /**
     * Returns the boolean controlling whether the player can jump 
     * 
     * @return the boolean controlling whether the player can jump
     */
    public boolean canJump()
    {
        return canJump;
    }

    /**
     * Sets the boolean controlling whether the player can jump
     * 
     * @param newCanJump The new state of this boolean
     */
    public void setCanJump(boolean newCanJump)
    {
        canJump = newCanJump;
    }

    /**
     * Returns the boolean controlling whether the player can crouch
     * 
     * @return the boolean controlling whether the player can crouch
     */
    public boolean isCrouching()
    {
        return isCrouching;
    }

    /**
     * Sets the boolean controlling whether the player can crouch
     * 
     * @param newIsCrouching The new state of this boolean
     */
    public void setIsCrouching(boolean newIsCrouching)
    {
        isCrouching = newIsCrouching; 
    }

    /**
     * Returns the character select image of this player
     * 
     * @return the character select image of this player
     */
    public Image getCharImage()
    {
        return charSelectImage;
    }

    /**
     * Returns the game sprite sheet of this player
     * 
     * @return the game sprite sheet of this player
     */
    public Image getGameImage()
    {
        return gameImage;
    }

    /**
     * Sets the character select image of this player
     * 
     * @param iamge The new character select image of this player
     */
    public void setCharImage(Image image)
    {
        charSelectImage = image;
    }

    /**
     * Sets the game sprite sheet of this player
     * 
     * @param The new game sprite sheet of this player
     */
    public void setGameImage(Image image)
    {
        gameImage = image; 
    }

    /**
     * Returns the name of the class of this player
     * 
     * @return the name of the class of this player
     */
    public String getName()
    {
        return name; 
    }

    /**
     * Sets the name of the class of this player
     * 
     * @param newName the new Name of the class of this player
     */
    public void setName(String newName)
    {
        name = newName;
    }

    /**
     * Returns the image of a player.
     * @return The image of the player
     **/
    public String getImageURL()
    {
        return imageURL;
    }

    /**
     * Sets the image of a player
     * @String url the string url of the image
     */
    public void setImageURL(String url)
    {
        imageURL = url;
    }

    /**
     * Returns the ellipse for the hitbox of this player
     * 
     * @return the ellipse for the hitbox of this player
     */
    public Ellipse getHitbox()
    {
        return hitbox;
    }

    /**
     * Sets the ellipse for the hitbox of this player
     * 
     * @param newHitBox The new ellipse for the hitbox of this player
     */
    public void setHitbox(Ellipse newHitbox)
    {
        hitbox = newHitbox; 
    }

    /**
     * Returns the ImageView containing the sprite sheet gameImage
     * 
     * @return the ImageView containing the sprite sheet gameImage
     */
    public ImageView getPlayerImage()
    {
        return playerImage;
    }

    /**
     * Sets the ImageView containing the sprite sheet gameImage
     * 
     * @param newPlayerImage The new ImageView containign the sprite sheet gameImage
     */
    public void setPlayerImage(ImageView newPlayerImage)
    {
        playerImage = newPlayerImage;
    }

    /**
     * Returns the AnimationTimer controlling player move animation
     * 
     * @return the AnimationTimer controlling player move animation
     */
    public AnimationTimer getPlayerTimer()
    {
        return playerTimer; 
    }

    /**
     * Sets the AnimationTimer controlling player move animation
     * 
     * @param the new AnimationTimer controlling player move animation
     */
    public void setPlayerTimer(AnimationTimer newTimer)
    {
        playerTimer = newTimer;
    }

    /**
     * Returns the counter for when to change frame of animation
     * 
     * @return the counter for when to change frame of animation
     */
    public int getCounter()
    {
        return counter;
    }

    /**
     * Sets the counter for when to change frame of animation
     * 
     * @param newValue the new value of the counter
     */
    public void setCounter(int newValue)
    {
        counter = newValue;
    }

    /**
     * Returns the current frame of the animation
     * 
     * @return the current frame of the animation
     */
    public int getCurrentFrame()
    {
        return currentFrame;
    }

    /**
     * Sets the current frame of the animation
     * 
     * @param newValue the new value of the current frame
     */
    public void setCurrentFrame(int newValue)
    {
        currentFrame = newValue;
    }

}
