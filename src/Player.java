import javafx.scene.image.Image;

// player
public class Player extends Rocket
{
    // constructor
    public Player( int posX, int posY, int size, Image img )
    {
        super( posX, posY, size, img );
    }
    
    public Bullet shoot()
    {
        return new Bullet( getPosX() + getSize() / 2 - Bullet.size / 2, getPosY() - Bullet.size );
    }
    
    public boolean collide( Player other )
    {
        int d = distance( this.getPosX() + this.getSize() / 2, this.getPosY() + this.getSize() / 2,
                other.getPosX() + other.getSize() / 2, other.getPosY() + other.getSize() / 2 );
        return d < other.getSize() / 2 + this.getSize() / 2;
    }
    
    public void explode()
    {
        exploding = true;
        explosionStep = -1;
    }
}