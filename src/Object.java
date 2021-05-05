import javafx.scene.canvas.GraphicsContext;
import java.awt.*;

/**
 * Interface Object is used for all objects that have coordinates and a size,
 * can be drawn, and should detect collision.
 */
public interface Object
{
    void setPosX( int posX );   // sets the x coordinate
    void setPosY( int posX );   // sets the y coordinate
    void setSize( int posX );   // sets the size
    int getPosX();  // returns the x coordinate
    int getPosY();  // returns the y coordinate
    int getSize();  // returns the size
    Rectangle getBounds();
    
    void update();  // updates the object
    void draw( GraphicsContext gc );    // draws the object
    boolean collide( Object other );    // detects collision
}
