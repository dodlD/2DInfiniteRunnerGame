package IRGame;

import IRGame.Input.Keyboard;
import IRGame.Physics.Collision;
import IRGame.Object.Character;
import IRGame.Physics.Gravity;
import java.util.ArrayList;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 *
 * @author lkpit11dljo
 */
public class GamePlay extends BasicGameState{
    
    public static int charXPos = 295;
    public static int charYPos = 125;
    public static int vertVel;
    public static int horizVel = 0;
    public static Character chaR;
    
    public static Image[] groundObj = new Image[Game.width / 40 + 1];
    
    public static Image charSprite;
    public static int jumpHeight = 80;
    
    public static int rObj, rPos; 
    
    public GamePlay(int state){
        
    }

    public int getID(){
        return 1;
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
        charSprite = new Image("res/tempChar.jpg");
        chaR = new Character(charSprite, charXPos, charYPos);
        for(int i = 0; i < Game.width / 40 + 1; i++){
            groundObj[i] = new Image("res/tempGround.jpg");
        }
        
    }
    
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
        g.drawImage(Character.getSprite(), chaR.getXPos(), chaR.getYPos());
        for (int img = 0; img < Game.width / 40 + 1; img++){
                g.drawImage(groundObj[img], Game.width + groundObj[img].getWidth() * (img+1) - horizVel, Game.height - groundObj[img].getHeight());
                
          
         
        }   
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{     
        vertVel += Gravity.gravity * delta;
        horizVel += (int)chaR.getHorizVel() * delta;
        Collision.check(delta);
        Keyboard.check(gc, delta);
        
        
        
    }
}
