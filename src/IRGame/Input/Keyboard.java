package IRGame.Input;

import IRGame.Game;
import IRGame.Object.Character;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import org.newdawn.slick.KeyListener;


/**
 *
 * @author Daniel
 */
public class Keyboard implements KeyListener{
    
    /*public static void check(GameContainer gc, int delta){
        Input input = gc.getInput();
        
        if(input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)){    
            GamePlay.chaR.setYPos(-2 * delta);
        }
        if(input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)){
            //GamePlay.chaR.setYPos(1 * delta);
        }
        if(input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)){
            
            GamePlay.chaR.setXPos(-1 * delta);
        }
        if(input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)){   
            GamePlay.chaR.setXPos(1 * delta);
        }
    }*/
    
    private boolean[] keys = new boolean[120];
    public boolean up, down, left, right;
    
    public void update(){
        up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
    }
    
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
    
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}