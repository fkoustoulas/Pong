
package pongpack;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.ImageIcon;


public class Ball implements Runnable{
    int x,y, xDirection=1, yDirection=1, p1score=0, p2score=0;
    Rectangle ball;
    Image ballIcon,pl1,pl2;
    Paddle p1 = new Paddle(0, 140,1);
    Paddle p2 = new Paddle(762, 140,2);
    int i = 2, up, down, left, right;
    boolean interSound;
    
    public Ball(int x, int y){
        this.interSound = false;
        this.x = x;
        this.y = y;
        Random r = new Random();
        
        int test = r.nextInt(2);
        if(test == 0)
            test = -1;
        setXdir(test);
        test = r.nextInt(2);
        if(test == 0)
            test = -1;
        setYdir(test);
           
        
        ball = new Rectangle(this.x, this.y, 30,30);
        //Load Images
        ImageIcon i = new ImageIcon("src/pongpack/ball.png");
        ballIcon = i.getImage();
        
    }
    public void newCollusion(){
        if(ball.intersects(p1.pad)){
            interSound = true;
            if(ball.y>=p1.pad.y && ball.y < p1.pad.y+32){
                setYdir(-2);
                
            }
            else if(ball.y>=p1.pad.y+32 && ball.y < p1.pad.y+64){
                setYdir(-1);
            }
            else if(ball.y>=p1.pad.y+64 && ball.y < p1.pad.y+96){
                setYdir(1);
            }
            else if(ball.y>=p1.pad.y+96 && ball.y < p1.pad.y+130){
                setYdir(2);
            }
            setXdir(2);
        }
        
        if(ball.intersects(p2.pad)){
            interSound = true;
            if(ball.y>=p2.pad.y && ball.y < p2.pad.y+32){
                setYdir(-2);
            }
            else if(ball.y>=p2.pad.y+32 && ball.y < p2.pad.y+64){
                setYdir(-1);
            }
            else if(ball.y>=p2.pad.y+64 && ball.y < p2.pad.y+96){
                setYdir(1);
            }
            else if(ball.y>=p2.pad.y+96 && ball.y < p2.pad.y+130){
                setYdir(2);
            }
            setXdir(-2);
        }
    }
    public void collusion(){
        if(ball.intersects(p1.pad)){
            
            setXdir(+1);
        }
        if(ball.intersects(p2.pad)){
            
            
            setXdir(-1);
        }
    }
    
    
    
    public void move(){
        
        
        ball.x += xDirection;
        ball.y += yDirection;
        if(ball.x<=0){
            setXdir(2);
            p2score++;
        }
        if(ball.x>790){
            setXdir(-2);
            p1score++;
        }
        if(ball.y<=20){
            if(yDirection==-1)
                setYdir(1);
            else setYdir(2);
        }
        if(ball.y>490){
            if(yDirection==1)
                setYdir(-1);
            else setYdir(-2);
        }
        newCollusion();
    }
    
    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.drawImage(ballIcon, ball.x-10, ball.y-7, null);
        
        
        g.setFont(new Font("TimesNewRoman", Font.BOLD+ Font.ITALIC+Font.PLAIN, 30));
        g.drawString(""+p1score, 10, 50);
        g.drawString(""+p2score, 730, 50);
        
        
    }
    public void setXdir(int x){
        xDirection = x;
    }
    public void setYdir(int x){
        yDirection = x;
    }
    public void setBallY(){
        p2.ballY = ball.y;
    }
    double beforeRun, afterRun, diff;
    public void run(){
        try{
            while(true){
                beforeRun = System.currentTimeMillis();
                setBallY();
                move();
                afterRun = System.currentTimeMillis();
                diff = afterRun - beforeRun;
                if(diff<=6){
                    Thread.sleep(i);
                }
            }
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    
}
