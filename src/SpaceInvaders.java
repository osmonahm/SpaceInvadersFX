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

public class SpaceInvaders extends Application
{
    // variables
    private static final Random RAND = new Random();
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PLAYER_SIZE = 60;
    
    static final Image PLAYER_IMG = new Image( "file:images/player.png" );
    
    final int MAX_OPPONENTS = 10, MAX_SHOTS = MAX_OPPONENTS * 2;
    boolean gameOver = false;
    private GraphicsContext gc;
    
    Player player;
    List<Bullet> bullets;
    List<Universe> universe;
    List<Opponent> opponents;
    
    private double mouseX;
    private int score;
    
    @Override
    public void start( Stage stage ) throws Exception
    {
        Canvas canvas = new Canvas( WIDTH, HEIGHT );
        gc = canvas.getGraphicsContext2D();
        Timeline timeline = new Timeline( new KeyFrame( Duration.millis( 100 ), e -> run( gc ) ) );
        timeline.setCycleCount( Timeline.INDEFINITE );
        timeline.play();
        canvas.setCursor( Cursor.MOVE );
        canvas.setOnMouseMoved( e -> mouseX = e.getX() );
        canvas.setOnMousePressed(e ->
        {
            if( bullets.size() < MAX_SHOTS ) bullets.add( player.shoot() );
            if( gameOver )
            {
                gameOver = false;
                setup();
                score = 0;
            }
        });
        canvas.setOnMouseDragged(e ->
        {   mouseX = e.getX();
            if( bullets.size() < MAX_SHOTS ) bullets.add( player.shoot() );
            if( gameOver )
            {
                gameOver = false;
                setup();
                score = 0;
            }
        });
        setup();
        stage.setScene( new Scene ( new StackPane( canvas ) ) );
        stage.setTitle( "Space Invaders" );
        stage.show();
    }
    
    // setup the game
    private void setup()
    {
        player = new Player( WIDTH / 2, HEIGHT - PLAYER_SIZE, PLAYER_SIZE, PLAYER_IMG );
        bullets = new ArrayList<>();
        universe = new ArrayList<>();
        opponents = new ArrayList<>();
        IntStream.range( 0, MAX_OPPONENTS ).mapToObj( i -> this.newOpponent() ).forEach( opponents::add );
    }
    
    // run Graphics
    private void run( GraphicsContext gc )
    {
        gc.setFill( Color.grayRgb( 20 ) );
        gc.fillRect( 0, 0, WIDTH, HEIGHT );
        gc.setTextAlign( TextAlignment.CENTER );
        gc.setFont( Font.font( 20 ) );
        gc.setFill( Color.WHITE );
        gc.fillText( "Score: " + score, 60, 20 );
        
        if( gameOver )
        {
            gc.setFont( Font.font( 35 ) );
            gc.setFill( Color.YELLOW );
            gc.fillText( "Game Over \n Your Score is: " + score + " \n Click to play again", WIDTH / 2.0, HEIGHT / 2.5 );
        }
        
        universe.forEach( u -> u.draw( gc ) );
        
        player.update();
        player.draw( gc );
        player.setPosX( ( int ) mouseX );
    
        opponents.stream().peek( Opponent::update ).peek( e -> e.draw( gc ) ).forEach( e ->
        { if( player.collide( e ) && !player.exploding ) player.explode(); } );
        
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
        
        for( int i = opponents.size() - 1; i >= 0; i-- )
        {
            if( opponents.get( i ).destroyed )
                opponents.set( i, newOpponent() );
        }
        
        gameOver = player.destroyed;
        
        if( RAND.nextInt( 10 ) > 2 )
            universe.add( new Universe( RAND, WIDTH, HEIGHT ) );
        
        for( int i = 0; i < universe.size(); i++ )
        {
            if( universe.get( i ).posY > HEIGHT )
                universe.remove( i );
        }
    }
    
    Opponent newOpponent()
    {
        Opponent op = new Opponent( 50 + RAND.nextInt( WIDTH - 100 ), 0, PLAYER_SIZE,
                Opponent.OPPONENTS_IMG[RAND.nextInt( Opponent.OPPONENTS_IMG.length )] );
        op.setSpeed( ( score / 5 ) + 2 );
        op.setFrameHeight( HEIGHT );
        return op;
    }
    
    public static void main( String[] args )
    {
        launch();
    }
}
