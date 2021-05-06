import javafx.scene.image.Image;

// computer player - opponent
public class Opponent extends Player
{
    // images of the opponents
    static final Image[] OPPONENTS_IMG = { new Image( "file:resources/1.png" ), new Image( "file:resources/2.png" ), new Image( "file:resources/3.png" ), new Image( "file:resources/4.png" ), new Image( "file:resources/5.png" ), new Image( "file:resources/6.png" ), new Image( "file:resources/7.png" ), new Image( "file:resources/8.png" ), new Image( "file:resources/9.png" ), new Image( "file:resources/10.png" ), new Image( "file:resources/11.png" ), new Image( "file:resources/12.png" ), new Image( "file:resources/13.png" ), new Image( "file:resources/14.png" ), new Image( "file:resources/15.png" ), new Image( "file:resources/16.png" ) };
    private int speed;          // the speed of the rocket
    private int frameWidth;     // the width of the frame
    private int frameHeight;    // the height of the frame
    private boolean outOfFrame = false;
    
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
     *
     * @param score - the current game score
     */
    public void setSpeed( int score )
    {
        speed = ( score / 5 ) + 2;
    }
    
    /**
     * Sets the frame width
     *
     * @param w - the width of the frame
     */
    public void setFrameWidth( int w )
    {
        frameWidth = w;
    }
    
    /**
     * Sets the frame height
     *
     * @param h - the height of the frame
     */
    public void setFrameHeight( int h )
    {
        frameHeight = h;
    }
    
    @Override
    /**
     * Updates the rocket
     */ public void update()
    {
        super.update();
        if( !exploding && !destroyed ) setPosY( getPosY() + speed );
        if( getPosY() > frameHeight )
        {
            destroyed = true;
            outOfFrame = true;
        }
    }
    
    public boolean isOutOfFrame()
    {
        return outOfFrame;
    }
}