import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;

/**
 * Abstract Class Rocket is used for the player and opponent's rocket
 */
public abstract class Rocket implements Object
{
    private int posX, posY, size;   // the rocket coordinates and the size
    private Image img;              // the rocket image
    boolean exploding, destroyed;   // used to check the rockets condition
    int explosionStep = 0;          // measures the explosion step
    
    // used to get the different explosion images
    static final Image EXPLOSION_IMG = new Image( "file:images/explosion.png" );
    static final int EXPLOSION_WIDTH = 128;
    static final int EXPLOSION_HEIGHT = 128;
    static final int EXPLOSION_ROWS = 3;
    static final int EXPLOSION_COLUMNS = 3;
    static final int EXPLOSION_STEPS = 5;
    
    /**
     * @param posX - the x coordinate of the rocket
     * @param posY - the y coordinate of the rocket
     * @param size - the size of the rocket
     * @param img  - the image of the rocket
     */
    public Rocket( int posX, int posY, int size, Image img )
    {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.img = img;
    }
    
    public void setPosX( int posX ) { this.posX = posX; }   // sets the x coordinate
    public void setPosY( int posY ) { this.posY = posY; }   // sets the y coordinate
    public void setSize( int size ) { this.size = size; }   // sets the size
    
    public int getPosX() { return posX; }   // returns the x coordinate
    public int getPosY() { return posY; }   // returns the y coordinate
    public int getSize() { return size; }   // returns the size
    
    /**
     * Updates the rocket
     */
    public void update()
    {
        if( exploding )
            explosionStep++;
        destroyed = explosionStep > EXPLOSION_STEPS;
    }
    
    /**
     * Draws the rocket
     * @param gc - the graphics pen
     */
    public void draw( GraphicsContext gc )
    {
        if( exploding )
        {
            gc.drawImage( EXPLOSION_IMG, explosionStep % EXPLOSION_COLUMNS * EXPLOSION_WIDTH,
                    ( explosionStep / EXPLOSION_ROWS ) * EXPLOSION_HEIGHT + 1, EXPLOSION_WIDTH, EXPLOSION_HEIGHT,
                    posX, posY, size, size );
        }
        else { gc.drawImage( img, posX, posY, size, size ); }
    }
    
    @Override
    /**
     * Checks for collision
     * @param other - the object used for comparsion
     */
    public boolean collide( Object other )
    {
        Rectangle r1 = this.getBounds();
        Rectangle r2 = other.getBounds();
        return r1.intersects( r2 );
    }
}