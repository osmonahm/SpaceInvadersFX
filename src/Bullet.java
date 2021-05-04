import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// bullets
public class Bullet implements Object
{
    public boolean toRemove;
    private int posX, posY, speed = 20, gameScore;
    public static int size = 6;
    
    public Bullet( int posX, int posY )
    {
        this.posX = posX;
        this.posY = posY;
    }
    
    public void setPosX( int posX ) { this.posX = posX; }
    public void setPosY( int posY ) { this.posY = posY; }
    public void setSize( int size ) { System.out.println( "Bullet size is static" ); }
    public void setGameScore( int score ) { gameScore = score; }
    
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public int getSize() { return size; }
    
    public void update()
    {
        posY -= speed;
    }
    
    public void draw( GraphicsContext gc )
    {
        if( gameScore >= 50 && gameScore <= 70 || gameScore >= 120 )
        {
            gc.setFill( Color.YELLOWGREEN );
            speed = 50;
            gc.fillRect( posX - 5, posY - 10, size + 10, size + 30 );
        }
        else
        {
            gc.setFill( Color.RED );
            gc.fillOval( posX, posY, size, size );
        }
    }
    
    @Override
    public boolean collide( Object player )
    {
        int d = distance( this.posX + size / 2, this.posY + size / 2,
                player.getPosX() + size / 2, player.getPosY() + size / 2 );
        
        return d < player.getSize() / 2 + size / 2;
    }
    
    public int distance( int x1, int y1, int x2, int y2 )
    {
        return ( int ) Math.sqrt( Math.pow( x1 - x2, 2 ) + Math.pow( y1 - y2, 2 ) );
    }
}