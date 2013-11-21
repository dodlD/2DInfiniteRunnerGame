package IRGame;

import IRGame.Input.Keyboard;
import IRGame.Physics.Collision;
import IRGame.Object.Character;
import java.util.ArrayList;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 *
 * @author lkpit11dljo
 */
public class GamePlay extends BasicGameState{
    
    public static int charStartXPos = 295;
    public static int charStartYPos = 125;
    public static double vertVel = 1.0;
    public static int horizVel = 0;
    
    public static Character chaR;
    public static Image charSprite;
    public static Image[] groundObj = new Image[Game.width / 80 + 2];
    
    public GamePlay(int state){
        
    }

    public int getID(){
        return 1;
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
        charSprite = new Image("res/tempChar.jpg");
        chaR = new Character(charSprite, charStartXPos, charStartYPos);
        for (int i = 0; i < Game.width / 80 + 2; i++){
            groundObj[i] = new Image("res/tempGround.jpg");
        }
        
    }
    
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
        g.drawImage(chaR.getSprite(), chaR.getXPos(), chaR.getYPos());
        for (int img = 0; img < Game.width / 80 + 2; img++){
            if (horizVel > Game.width + groundObj[img].getWidth() * (img+1)){
                g.drawImage(groundObj[img], Game.width + groundObj[img].getWidth() * (img+1) - horizVel, Game.height - groundObj[img].getHeight());
            }
            g.drawImage(groundObj[img], Game.width + groundObj[img].getWidth() * (img+1) - horizVel, Game.height - groundObj[img].getHeight());
            
        }
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{     
        chaR.setYPos((int)vertVel * delta);
        horizVel += 1 * delta;
        Collision.check(delta);
        Keyboard.check(gc, delta);
        
    }
}
