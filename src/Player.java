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
     *
     * @return - the bullet object
     */
    public Bullet shoot()
    {
        return new Bullet( getPosX() + getSize() / 2 - Bullet.size / 2, getPosY() - Bullet.size );
    }
}