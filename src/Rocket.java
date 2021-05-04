import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Rocket implements Object
{
    private int posX, posY, size;
    private Image img;
    boolean exploding, destroyed;
    int explosionStep = 0;
    
    static final Image EXPLOSION_IMG = new Image( "file:images/explosion.png" );
    static final int EXPLOSION_WIDTH = 128;
    static final int EXPLOSION_HEIGHT = 128;
    static final int EXPLOSION_ROWS = 3;
    static final int EXPLOSION_COLUMNS = 3;
    static final int EXPLOSION_STEPS = 5;
    
    public Rocket( int posX, int posY, int size, Image img )
    {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.img = img;
    }
    
    public void setPosX( int posX ) { this.posX = posX; }
    public void setPosY( int posY ) { this.posY = posY; }
    public void setSize( int size ) { this.size = size; }
    
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public int getSize() { return size; }
    
    public void update()
    {
        if( exploding )
            explosionStep++;
        destroyed = explosionStep > EXPLOSION_STEPS;
    }
    
    public void draw( GraphicsContext gc )
    {
        if( exploding )
        {
            gc.drawImage( EXPLOSION_IMG, explosionStep % EXPLOSION_COLUMNS * EXPLOSION_WIDTH,
                    ( explosionStep / EXPLOSION_ROWS ) * EXPLOSION_HEIGHT + 1, EXPLOSION_WIDTH, EXPLOSION_HEIGHT,
                    posX, posY, size, size );
        }
        else
        {
            gc.drawImage( img, posX, posY, size, size );
        }
    }
    
    @Override
    public boolean collide( Object other )
    {
        return false;
    }
    
    public int distance( int x1, int y1, int x2, int y2 )
    {
        return ( int ) Math.sqrt( Math.pow( x1 - x2, 2 ) + Math.pow( y1 - y2, 2 ) );
    }
}
