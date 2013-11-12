package IRGame.Input;

import IRGame.Game;
import IRGame.GamePlay;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
//import org.newdawn.slick.KeyListener;


/**
 *
 * @author Daniel
 */
public class Keyboard /*implements KeyListener*/{
    
    public static void check(GameContainer gc, int delta){
        Input input = gc.getInput();
        
        if(input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)){    
            GamePlay.yPos -= 2 * delta;
        }
        if(input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)){
            //GamePlay.yPos += 1 * delta;
        }
        if(input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)){
            GamePlay.xPos -= 1 * delta;
        }
        if(input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)){
            GamePlay.xPos += 1 * delta;
        }
    }

    /*public void keyPressed(int keyCode, char c) {
        keyCode.getKeyCode();
        switch( keyCode ) { 
            case KeyEvent.VK_UP | KeyEvent.VK_W:
                keyUp = true; 
                break;
            case KeyEvent.VK_DOWN | KeyEvent.VK_S:
                keyDown = true; 
                break;
            case KeyEvent.VK_LEFT | KeyEvent.VK_A:
                keyLeft = true;
                break;
            case KeyEvent.VK_RIGHT | KeyEvent.VK_D:
                keyRight = true;
                break;
         }
    }

    public void keyReleased(int key, char c) {
        
    }

    public void setInput(Input input) {

    }

    public boolean isAcceptingInput() {
        return false;
    }

    public void inputEnded() {

    }

    public void inputStarted() {

    }*/

    
    
}
