package IRGame;

import IRGame.Input.Keyboard;
import IRGame.Physics.Collision;
import java.util.ArrayList;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 *
 * @author lkpit11dljo
 */
public class GamePlay extends BasicGameState{
    
    public static int xPos;
    public static int yPos;
    public static int horizPos = 0;

    public static ArrayList<Image> groundImg = new ArrayList<Image>(/*Game.width / 40 + 1*/);
    
    private static int x = 1; //kollar hur många stort horizPos är
    
    public static Image ground;
    public static Image chaR;
    public static int jumpHeight = 80;
    
    public static int rObj, rPos; 
    
    public GamePlay(int state){
        
    }

    public int getID(){
        return 1;
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
        chaR = new Image("res/tempChar.jpg");
        for(int i = 0; i < Game.width / 40 + x; i++){
            groundImg.add(new Image("res/tempGround.jpg"));
            System.out.println(i+" "+groundImg.size());
        }
        
        xPos = 295;
        yPos = 155;    
    }
    
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
        g.drawImage(chaR, xPos, yPos);
        for (int img = 0; img < Game.width / 40 + x; img++){
            if (horizPos == 40 * x){
                System.out.println(groundImg.size());
                //groundImg.set(img, ground);
                System.out.println(groundImg.size() + " " );
                groundImg.add(new Image("res/tempGround.jpg"));
                System.out.println(groundImg.size());
                x++;
            }
            if (horizPos == 0){
                rObj = 9;//(int)(Math.random() * Game.width / 40);
                rPos = 2;//(int)(Math.random() + 1);
            }
            if (img == rObj){
                g.drawImage(groundImg.get(rObj), groundImg.get(img).getWidth() * rObj - horizPos, Game.height - groundImg.get(img).getHeight() * rPos);
            }else{
                g.drawImage(groundImg.get(img), groundImg.get(img).getWidth() * img - horizPos, Game.height - groundImg.get(img).getHeight());
            }
         
        }   
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{     
        yPos+= 1 * delta;
        horizPos++;
        Collision.check(delta);
        Keyboard.check(gc, delta);
        
        
        
    }
}
