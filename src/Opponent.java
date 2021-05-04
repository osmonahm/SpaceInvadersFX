import javafx.scene.image.Image;

// computer player - opponent
public class Opponent extends Player
{
    private int speed;          // the speed of the rocket
    private int frameWidth;     // the width of the frame
    private int frameHeight;    // the height of the frame
    
    // images of the opponents
    static final Image[] OPPONENTS_IMG =
            {
                    new Image( "file:images/1.png" ),
                    new Image( "file:images/2.png" ),
                    new Image( "file:images/3.png" ),
                    new Image( "file:images/4.png" ),
                    new Image( "file:images/5.png" ),
                    new Image( "file:images/6.png" ),
                    new Image( "file:images/7.png" ),
                    new Image( "file:images/8.png" ),
                    new Image( "file:images/9.png" ),
                    new Image( "file:images/10.png" )
            };
    
    /**
     * @param posX - the x coordinate of the rocket
     * @param posY - the y coordinate of the rocket
     * @param size - the size of the rocket
     * @param img  - the image of the rocket
     */
    public Opponent( int posX, int posY, int size, Image img )
    {
        super( posX, posY, size, img );
    }
    
    /**
     * Sets the speed of the rocket
     * @param score - the current game score
     */
    public void setSpeed( int score )
    {
        speed = ( score / 5 ) + 2;
    }
    
    /**
     * Sets the frame width
     * @param w - the width of the frame
     */
    public void setFrameWidth( int w )
    {
        frameWidth = w;
    }
    
    /**
     * Sets the frame height
     * @param h - the height of the frame
     */
    public void setFrameHeight( int h )
    {
        frameHeight = h;
    }
    
    @Override
    /**
     * Updates the rocket
     */
    public void update()
    {
        super.update();
        if( !exploding && !destroyed ) setPosY( getPosY() + speed );
        if( getPosY() > frameHeight ) destroyed = true;
    }
}