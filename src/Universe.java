import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

/**
 * Class Universe is used to create the game background
 */
public class Universe
{
    int posX, posY;                         // the x and y coordinates
    private int frameWidth, frameHeight;    // the frame width and height
    private int w, h, r, g, b;              // the width and height of the background rocks, and the rgb components
    private double opacity;                 // the oppacity of the background rocks
    
    /**
     * @param RAND - used for randomness
     * @param fW   - the frame width
     * @param fH   - the frame height
     */
    public Universe( Random RAND, int fW, int fH )
    {
        frameWidth = fW;
        frameHeight = fH;
        posX = RAND.nextInt( frameWidth );
        posY = 0;
        w = RAND.nextInt( 5 ) + 1;
        h = RAND.nextInt( 5 ) + 1;
        r = RAND.nextInt( 100 ) + 150;
        g = RAND.nextInt( 100 ) + 150;
        b = RAND.nextInt( 100 ) + 150;
        opacity = RAND.nextFloat();
        if( opacity < 0 ) opacity *= -1;
        if( opacity > 0.5 ) opacity = 0.5;
    }
    
    /**
     * Draws the universe
     * @param gc - the graphics pen
     */
    public void draw( GraphicsContext gc )
    {
        if( opacity > 0.8 ) opacity -= 0.01;
        if( opacity < 0.1 ) opacity += 0.01;
        gc.setFill( Color.rgb( r, g, b, opacity ) );
        gc.fillOval( posX, posY, w, h );
        posY += 20;
    }
}