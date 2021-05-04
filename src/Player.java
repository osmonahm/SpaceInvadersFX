import javafx.scene.image.Image;
import java.awt.*;

/**
 * Class Player is the player's rocket
 */
public class Player extends Rocket
{
    /**
     * @param posX - the x coordinate of the rocket
     * @param posY - the y coordinate of the rocket
     * @param size - the size of the rocket
     * @param img  - the image of the rocket
     */
    public Player( int posX, int posY, int size, Image img )
    {
        super( posX, posY, size, img );
    }
    
    /**
     * Shoots a bullet
     * @return - the bullet object
     */
    public Bullet shoot()
    {
        return new Bullet( getPosX() + getSize() / 2 - Bullet.size / 2, getPosY() - Bullet.size );
    }
    
    @Override
    /**
     * Checks for collision
     * @param other - the object used for comparsion
     */
    public boolean collide( Object other )
    {
        int d = distance( this.getPosX() + this.getSize() / 2, this.getPosY() + this.getSize() / 2,
                other.getPosX() + other.getSize() / 2, other.getPosY() + other.getSize() / 2 );
        return d < other.getSize() / 2 + this.getSize() / 2;
    }
    public Rectangle getBounds(){
        return new Rectangle(getPosX(), getPosY(), getSize(), getSize());
    }
    
    /**
     * Explodes the player's rocket
     */
    public void explode()
    {
        exploding = true;
        explosionStep = -1;
    }
}
