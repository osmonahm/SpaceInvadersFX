import javafx.application.Application;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame
{
    private static final int WIDTH = 1200;  // the frame width
    private static final int HEIGHT = 700;  // the frame height
    
    public MainMenu()
    {
        Image wp = new ImageIcon( "resources\\wp.jpg" ).getImage().getScaledInstance( WIDTH, HEIGHT, Image.SCALE_SMOOTH );
        setContentPane( new JPanel()
        {
            @Override
            public void paintComponent( Graphics g )
            {
                super.paintComponent( g );
                g.drawImage( wp, 0, 0, this );
            }
        } );
        
        setSize( WIDTH, HEIGHT );
        setTitle( "Space Invaders" );
        setResizable( false );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setVisible( true );
        
        JButton playButton = new JButton();
        playButton.setBounds( WIDTH / 4, ( HEIGHT / 2 ) - 90, 180, 60 );
        playButton.setOpaque( false );
        playButton.setContentAreaFilled( false );
        playButton.setBorderPainted( false );
        Image playImg = new ImageIcon( "resources\\playButton.png" ).getImage().getScaledInstance( 180, 60, Image.SCALE_SMOOTH );
        playButton.setIcon( new ImageIcon( playImg ) );
        playButton.addActionListener( e ->
        {
            this.setVisible( false );
            Application.launch( SpaceInvaders.class );
        } );
        
        JButton exitButton = new JButton();
        exitButton.setBounds( WIDTH / 4, ( HEIGHT / 2 ) - 5, 180, 60 );
        exitButton.setOpaque( false );
        exitButton.setContentAreaFilled( false );
        exitButton.setBorderPainted( false );
        Image exitImg = new ImageIcon( "resources\\exitButton.png" ).getImage().getScaledInstance( 180, 60, Image.SCALE_SMOOTH );
        exitButton.setIcon( new ImageIcon( exitImg ) );
        exitButton.addActionListener( e -> System.exit( 0 ) );
        
        add( playButton );
        add( exitButton );
        repaint();
    }
    
    public static void main( String[] args )
    {
        MainMenu obj = new MainMenu();
    }
}