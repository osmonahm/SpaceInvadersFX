import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Class Bullet is used for the rocket bullets
 */
public class Bullet implements Object
{
    public boolean toRemove;    // checks if the bullet should be removed
    // the bullet's coordinates, speed, game score, and size
    private int posX, posY, speed = 20, gameScore;
    public static int size = 6;
    
    /**
     * @param posX - the bullets x coordinate
     * @param posY - the bullets x coordinate
     */
    public Bullet( int posX, int posY )
    {
        this.posX = posX;
        this.posY = posY;
    }
    
    public void setPosX( int posX ) { this.posX = posX; }   // sets the x coordinate
    public void setPosY( int posY ) { this.posY = posY; }   // sets the y coordinate
    public void setSize( int size ) { this.size = size; }   // sets the size
    public void setGameScore( int score ) { gameScore = score; }
    
    public int getPosX() { return posX; }   // returns the x coordinate
    public int getPosY() { return posY; }   // returns the y coordinate
    public int getSize() { return size; }   // returns the size
    
    /**
     * Updated the bullet
     */
    public void update()
    {
        posY -= speed;
    }
    
    /**
     * Draws the bullet, with its various forms
     * @param gc - the graphics pen
     */
    public void draw( GraphicsContext gc )
    {
        if( gameScore >= 50 && gameScore <= 70 || gameScore >= 120 && gameScore <= 200 )
        {
            gc.setFill( Color.YELLOWGREEN );
            speed = 30;
            gc.fillRect( posX - 5, posY - 10, size + 10, size + 20 );
        }
        else if( gameScore > 200 && gameScore <= 300 )
        {
            gc.setFill( Color.DARKORANGE );
            speed = 40;
            gc.fillRoundRect( posX - 5, posY - 10, size + 10, size + 20, 120, 120 );
        }
        else if( gameScore > 300 )
        {
            gc.setFill( Color.BLUEVIOLET );
            speed = 40;
            gc.fillOval( posX, posY, size * 3, size * 3 );
        }
        else
        {
            gc.setFill( Color.RED );
            gc.fillOval( posX, posY, size, size );
        }
    }
    
    @Override
    /**
     * Checks for collision
     * @param other - the object used for comparsion
     */
    public boolean collide( Object player )
    {
        int d = distance( this.posX + size / 2, this.posY + size / 2,
                player.getPosX() + size / 2, player.getPosY() + size / 2 );
        
        return d < player.getSize() / 2 + size / 2;
    }
    
    /**
     * @param x1 - the first object x coordinate
     * @param y1 - the first object y coordinate
     * @param x2 - the second object x coordinate
     * @param y2 - the second object y coordinate
     * @return - the distance between the two objects
     */
    public int distance( int x1, int y1, int x2, int y2 )
    {
        return ( int ) Math.sqrt( Math.pow( x1 - x2, 2 ) + Math.pow( y1 - y2, 2 ) );
    }
}