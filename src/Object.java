import javafx.scene.canvas.GraphicsContext;

public interface Object
{
    void setPosX( int posX );
    void setPosY( int posX );
    void setSize( int posX );
    int getPosX();
    int getPosY();
    int getSize();
    void update();
    void draw( GraphicsContext gc );
    boolean collide( Object other );
    int distance( int x1, int y1, int x2, int y2 );
}
