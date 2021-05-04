import javafx.scene.canvas.GraphicsContext;

/**
 * Interface Object is used for all objects that have a coordinate and a size,
 * can be drawn, should dettect collision and measure the distance between
 * two objects.
 */
public interface Object
{
    Rectangle getBounds();
    void setPosX( int posX );   // sets the x coordinate
    void setPosY( int posX );   // sets the y coordinate
    void setSize( int posX );   // sets the size
    int getPosX();  // returns the x coordinate
    int getPosY();  // returns the y coordinate
    int getSize();  // returns the size
    
    void update();  // updates the object
    void draw( GraphicsContext gc );    // draws the object
    boolean collide( Object other );    // detects collision
    int distance( int x1, int y1, int x2, int y2 ); // used to measure distance between two objects
}
