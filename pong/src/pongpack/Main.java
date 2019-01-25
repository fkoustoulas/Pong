
package pongpack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.io.*;
import javax.sound.sampled.*;



public class Main extends JFrame{
    //Double Buffering
    Image dbImage, back;
    Graphics dbg;
    
    //Parameters
    Clip clip;
    File f;
    AudioInputStream audioIn; 
    boolean difCheck = true;
    Dimension myDim = new Dimension(800,500);
    static Ball ball = new Ball(393,243);
    static boolean startGame = false;
    public boolean botChoice = false;
    Rectangle start, botButton, difButton;
    boolean startHover, botHover;
    Color col;
    
    
    
    
    
    
    public Main(){
        //Load Images
        ImageIcon i = new ImageIcon("src/pongpack/blue.png");
        back = i.getImage();
        
        setTitle("Pong");
        setSize(myDim);
        setResizable(false);
        setVisible(true);
        col = new Color(1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(new AL());
        addMouseListener(new MouseListener());
        addMouseMotionListener(new MouseListener());
        start =new Rectangle(270,80, 250, 100);
        botButton = new Rectangle(270,230,250,100);
        difButton = new Rectangle(270,380,250,100);
        startMusic("src/pongpack/begin.wav");
        
        
    }
    
    public void startMusic(String path){
        
         // Open an audio input stream.
        if(path==null)
            path = "src/pongpack/begin.wav";
        f = new File(path);
        try {
        audioIn = AudioSystem.getAudioInputStream(f);
         // Get a sound clip resource.
        if(clip!=null)
            clip.stop();
        clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         
        clip.open(audioIn);
        clip.start();
        
         
        } catch (NullPointerException | UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Audio error = " + e.getMessage());
        }
    }
    @Override
    public void paint(Graphics g){
        dbImage = createImage(getWidth(), getHeight());
        dbg = dbImage.getGraphics();
        try{
        draw(dbg);
        g.drawImage(dbImage, 0, 0, this);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    public void draw(Graphics g){
        if(startGame){
            g.drawImage(back, 0, 0, null);
            ball.draw(g);
            ball.p1.draw(g);
            ball.p2.draw(g);
            
        }
        else{
            g.drawImage(back, 0, 0, null);
            if(!startHover)
                g.setColor(Color.lightGray);
            else
                g.setColor(Color.cyan);
            g.fillRect(start.x, start.y, start.width, start.height);
            g.setColor(Color.RED);
            g.setFont(new Font("TimesNewRoman", Font.BOLD, 30));
            g.drawString("Start Game!", start.x+40, start.y+60);
            if(!botHover)
                g.setColor(Color.lightGray);
            else
                g.setColor(Color.CYAN);
            if(botChoice==true){
                g.setColor(col);
                g.drawString("Bot = ON", botButton.x+300, botButton.y+60);
            }
            else{
                g.setColor(Color.GRAY);
                g.drawString("Bot = OFF", botButton.x+300, botButton.y+60);
            }
            
            g.fillRect(botButton.x, botButton.y, botButton.width, botButton.height);
            g.setColor(Color.RED);
            g.setFont(new Font("TimesNewRoman", Font.BOLD, 30));
            g.drawString("Play with bot!", botButton.x+35, botButton.y+60);
            g.setColor(Color.GRAY);
            if(botChoice)
                g.fillRect(difButton.x, difButton.y, difButton.width, difButton.height);
            g.setColor(Color.RED);
            g.setFont(new Font("TimesNewRoman", Font.BOLD, 30));
            if(botChoice)
                if(difCheck){
                    g.drawString("Difficulty: Normal", difButton.x+2, difButton.y+60);

                }
                else{
                    g.drawString("Difficulty: Hard", difButton.x+2, difButton.y+60);

                }
        }
        repaint();
        if(ball.interSound){
            startMusic("src/pongpack/click.wav");
            ball.interSound = false;
        }
    }
           
            static Thread myball = new Thread(ball);
            static Thread p1 = new Thread(ball.p1);
            static Thread p2 = new Thread(ball.p2);
            
           
    public static void main(String args[]){
        Main m = new Main();
        
        boolean loop = false;
        while(loop==false){
            System.out.println();
            if(m.startGame){
                
                p1.start();
                myball.start();
                p2.start();
                loop = true;
                
            }   
        }
            
    }
    
    public class AL extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            ball.p1.keyPressed(e);
            ball.p2.keyPressed(e);
        }
        @Override
        public void keyReleased(KeyEvent e){
            ball.p1.keyReleased(e);
            ball.p2.keyReleased(e);
        }
        
    }
    int mx,my, mmx, mmy;
    public class MouseListener extends MouseAdapter{
        
        @Override
        public void mouseMoved(MouseEvent e){
            mx = e.getX();
            my = e.getY();
            if(e.getX()>start.x && e.getX()<start.x+start.width && e.getY()>start.y && e.getY()<start.y+start.height){
                
                startHover = true;
            }
            else
                startHover = false;
            if(e.getX()>botButton.x && e.getX()<botButton.x+botButton.width && e.getY()>botButton.y && e.getY()<botButton.y+botButton.height){
                
                botHover = true;
            }
            else
                botHover = false;
        }
    
        
        @Override
        public void mousePressed(MouseEvent e){
            
            mx = e.getX();
            my = e.getY();
            if(mx>start.x && mx<start.x+start.width && my>start.y && my<start.y+start.height){
                startMusic("src/pongpack/start.wav");
                startGame=true;
                
            }
            
            if(!startGame){
                startMusic("src/pongpack/click.wav");
                if(mx>difButton.x && mx<difButton.x+difButton.width && my>difButton.y && my<difButton.y+difButton.height){

                    if(difCheck){
                        ball.p2.dif = 4;
                        difCheck = false;
                    }
                    else{
                        ball.p2.dif = 2;
                        difCheck = true;
                    }
                }

                if(mx>botButton.x && mx<botButton.x+botButton.width && my>botButton.y && my<botButton.y+botButton.height){
                    if(botChoice==true){
                        botChoice=false;
                        ball.p2.bot = false;
                        col = Color.yellow;
                        return;

                    }
                    botChoice=true;
                    ball.p2.bot = true;
                    col = Color.yellow;
                }
            }
        }
    }    
}
