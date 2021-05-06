import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Class SpaceInvaders is the game controller
 */
public class SpaceInvaders extends Application
{
    static final Image PLAYER_IMG = new Image( "file:resources/player.png" );// the player's rocket image
    private static final Random RAND = new Random();                            // used to get random numbers
    private static final int WIDTH = 1200;                                      // the frame width
    private static final int HEIGHT = 650;                                      // the frame height
    private static final int PLAYER_SIZE = 60;                                  // the player size
    final int MAX_OPPONENTS = 10, MAX_SHOTS = MAX_OPPONENTS * 2;                // the maximum number of opponents
    boolean gameOver = false;                                                   // checks if game is finished
    Player player;                                                              // the player object
    List<Bullet> bullets;                                                       // the bullets list
    List<Universe> universe;                                                    // the universe components list
    List<Opponent> opponents;                                                   // the opponents list
    private GraphicsContext gc;                                                 // the graphics pen
    private double mouseX;                                                      // position of the mouse
    private int score;                                                          // used to keep track of the game score
    private boolean toggleShootBullets = false;                                 // shoots bullets while true
    
    
    public SpaceInvaders()
    {
        //launch();
    }
    
    public static void main( String[] args )
    {
        launch();
    }
    
    @Override
    /**
     * Starts the game, creates the canvas and adds the mouse listeners
     */ public void start( Stage stage ) throws Exception
    {
        Canvas canvas = new Canvas( WIDTH, HEIGHT );
        gc = canvas.getGraphicsContext2D();
        Timeline timeline = new Timeline( new KeyFrame( Duration.millis( 100 ), e -> run( gc ) ) );
        timeline.setCycleCount( Timeline.INDEFINITE );
        timeline.play();
        canvas.setCursor( Cursor.MOVE );
    
        canvas.setOnMouseMoved( e ->
        {
            if( e.getX() > 0 && e.getX() < WIDTH - PLAYER_SIZE ) mouseX = e.getX();
        } );
    
        canvas.setOnMouseClicked( e ->
        {
            if( gameOver )
            {
                gameOver = false;
                setup();
                score = 0;
            }
        } );
    
        canvas.setOnMousePressed( e ->
        { toggleShootBullets = true; } );
    
        canvas.setOnMouseReleased( e ->
        { toggleShootBullets = false; } );
    
        canvas.setOnMouseDragged( e ->
        {
            if( e.getX() > 0 && e.getX() < WIDTH - PLAYER_SIZE ) mouseX = e.getX();
            toggleShootBullets = !gameOver;
        } );
    
        setup();
        stage.setScene( new Scene( new StackPane( canvas ) ) );
        stage.setTitle( "Space Invaders" );
        stage.show();
    }
    
    /**
     * Sets the game up
     */
    private void setup()
    {
        player = new Player( WIDTH / 2, HEIGHT - PLAYER_SIZE, PLAYER_SIZE, PLAYER_IMG );
        bullets = new ArrayList<>();
        universe = new ArrayList<>();
        opponents = new ArrayList<>();
        IntStream.range( 0, MAX_OPPONENTS ).mapToObj( i -> this.newOpponent() ).forEach( opponents::add );
    }
    
    /**
     * Runs the game
     *
     * @param gc - the graphics pen
     */
    private void run( GraphicsContext gc )
    {
        // draws the game score on the top left corner
        gc.setFill( Color.grayRgb( 20 ) );
        gc.fillRect( 0, 0, WIDTH, HEIGHT );
        gc.setTextAlign( TextAlignment.CENTER );
        gc.setFont( Font.font( 20 ) );
        gc.setFill( Color.WHITE );
        gc.fillText( "Score: " + score, 60, 20 );
    
        // shoots bullets when mouse is clicked
        if( toggleShootBullets )
            if( bullets.size() < MAX_SHOTS ) bullets.add( player.shoot() );
    
        // checks if the game is finished, and prompts for a replay
        if( gameOver )
        {
            toggleShootBullets = false;
            gc.setFont( Font.font( 35 ) );
            gc.setFill( Color.YELLOW );
            gc.fillText( "Game Over \n Your Score is: " + score + " \n Click to play again", WIDTH / 2.0, HEIGHT / 2.5 );
        }
    
        universe.forEach( u -> u.draw( gc ) );  // redraws the universe components
        
        // updates, redraws, and changes the position of the player
        player.update();
        player.draw( gc );
        player.setPosX( ( int ) mouseX );
        
        // updates and redraws the opponents
        opponents.stream().peek( Opponent::update ).peek( e -> e.draw( gc ) ).forEach( e ->
        {
            if( player.collide( e ) && !player.exploding ) player.explode();
        } );
        
        for( Opponent opponent : opponents )
            if( opponent.isOutOfFrame() && !gameOver ) score -= 5;
        
        // maintains the active bullets, updates and redraws them
        for( int i = bullets.size() - 1; i >= 0; i-- )
        {
            Bullet bullet = bullets.get( i );
            if( bullet.getPosY() < 0 || bullet.toRemove )
            {
                bullets.remove( i );
                continue;
            }
            
            bullet.update();
            bullet.setGameScore( score );
            bullet.draw( gc );
            
            for( Opponent opponent : opponents )
            {
                if( bullet.collide( opponent ) && !opponent.exploding )
                {
                    score++;
                    opponent.explode();
                    bullet.toRemove = true;
                }
            }
        }
        
        // maintains the active opponents
        for( int i = opponents.size() - 1; i >= 0; i-- )
        {
            if( opponents.get( i ).destroyed ) opponents.set( i, newOpponent() );
        }
        
        gameOver = player.destroyed;    // checks if the game is over
        
        if( RAND.nextInt( 10 ) > 2 )
            universe.add( new Universe( RAND, WIDTH, HEIGHT ) );   // adds new universe components
        
        // maintains the active universe components
        for( int i = 0; i < universe.size(); i++ )
        {
            if( universe.get( i ).posY > HEIGHT ) universe.remove( i );
        }
    }
    
    /**
     * Used to create a new opponent
     *
     * @return - the opponent object
     */
    Opponent newOpponent()
    {
        int xPos = genXPos( 100 );
        int yPos = 0;
        
        Opponent op = new Opponent( xPos, yPos, PLAYER_SIZE, Opponent.OPPONENTS_IMG[RAND.nextInt( Opponent.OPPONENTS_IMG.length )] );
        op.setSpeed( ( score / 5 ) + 2 );
        op.setFrameHeight( HEIGHT );
        return op;
    }
    
    private int genXPos( int termination )
    {
        int xPos = 50 + RAND.nextInt( WIDTH - 100 );
        
        if( termination == 0 ) return 50;
        
        for( Opponent opponent : opponents )
            if( ( xPos > opponent.getPosX() && xPos < opponent.getPosX() + PLAYER_SIZE ) || ( xPos < opponent.getPosX() && xPos + PLAYER_SIZE >= opponent.getPosX() ) )
                xPos = genXPos( --termination );
        
        return xPos;
    }
}