
package pongpack;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;


public class Paddle implements Runnable{
    int x, y, yDir=0, id, ballY, dif = 2;
    Rectangle pad;
    Image pl1,pl2;
    boolean bot;
    
    public Paddle(int x, int y, int id){
        this.x = x;
        this.y = y;
        this.id = id;
        pad = new Rectangle(this.x,this.y,30,130);
        bot = false;
        //Load Images
        ImageIcon i = new ImageIcon("src/pongpack/pl1.png");
        pl1 = i.getImage();
        i = new ImageIcon("src/pongpack/pl2.png");
        pl2 = i.getImage();
    }
    public void setBot(boolean x){
        bot = x;
    }
    double beforeRun, afterRun, diff;
    @Override
    public void run(){
        try{
            while(true){
                beforeRun = System.currentTimeMillis();
                move();
                afterRun = System.currentTimeMillis();
                diff = afterRun - beforeRun;
                if(diff<=6){
                    Thread.sleep(5);
                }
            }
        }
        catch(Exception e){
            System.out.print("error");
        }
    }
    public void setDif(int dif){
        this.dif = dif;
    }
    
    public void move(){
        if(bot)
            if(ballY<pad.y)
                setYdir(-dif);
            else setYdir(dif);
        pad.y += yDir;
        if(pad.y<=15)
            pad.y = 15;
        if(pad.y>=380)
            pad.y = 380;
        
    }
    
        
    
   
    public void setYdir(int ydir){
        yDir = ydir;
    }
    public void draw(Graphics g){
        switch(id){
            default:System.out.println("error");
            case 1:
                g.setColor(Color.red);
                g.drawImage(pl1, pad.x, pad.y-5, null);
                
            break;
                
            case 2:
                g.setColor(Color.blue);
                g.drawImage(pl2, pad.x-25, pad.y-5, null);
                
            break;
            
        }
        
    }
    public void keyPressed(KeyEvent e){
        if(bot==false){
            switch(id){
                default:System.out.println("error");
                break;
                case 1:
                    if(e.getKeyCode() == e.VK_W){
                        setYdir(-3);
                    }
                    if(e.getKeyCode() == e.VK_S){
                        setYdir(3);
                    }
                    break;
                case 2:
                    if(e.getKeyCode() == e.VK_UP){
                        setYdir(-3);
                    }
                    if(e.getKeyCode() == e.VK_DOWN){
                        setYdir(3);
                    }
                    break;
            }
        }
    }
    public void keyReleased(KeyEvent e){
            if(!bot){
            switch(id){
                default:System.out.println("error");
                break;
                case 1:
                    if(e.getKeyCode() == e.VK_W){
                        setYdir(0);
                    }
                    if(e.getKeyCode() == e.VK_S){
                        setYdir(0);
                    }
                    break;
                case 2:
                    if(e.getKeyCode() == e.VK_UP){
                        setYdir(0);
                    }
                    if(e.getKeyCode() == e.VK_DOWN){
                        setYdir(0);
                    }
                    break;
            }
        }
    }
}
