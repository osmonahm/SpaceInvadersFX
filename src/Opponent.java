import javafx.scene.image.Image;

// computer player - opponent
public class Opponent extends Player
{
    private int speed;
    private int frameWidth;
    private int frameHeight;
    
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
    
    public Opponent( int posX, int posY, int size, Image img )
    {
        super( posX, posY, size, img );
    }
    
    public void setSpeed( int score )
    {
        speed = ( score / 5 ) + 2;
    }
    
    public void setFrameWidth( int w )
    {
        frameWidth = w;
    }
    
    public void setFrameHeight( int h )
    {
        frameHeight = h;
    }
    
    @Override
    public void update()
    {
        super.update();
        if( !exploding && !destroyed ) setPosY( getPosY() + speed );
        if( getPosY() > frameHeight ) destroyed = true;
    }
}