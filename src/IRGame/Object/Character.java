/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IRGame.Object;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author lkpit11dljo
 */
public class Character {
    private static Image sprite;
    private static int xPos;
    private static int yPos;
    private static double horizVel = 1.0;
    
    
    public Character(Image sprite, int xPos, int yPos){
        this.sprite = sprite;
        this.xPos = xPos;
        this.yPos = yPos;
    }
    
    public static Image getSprite(){
        return sprite;
    }
    
    public static int getXPos(){
        return xPos;
    }
    
    public void setXPos(int xPos){
        this.xPos = xPos;
    }
    
    public static int getYPos(){
        return yPos;
    }
    
    public void setYPos(int yPos){
        this.yPos = yPos;
    }
    
    public static double getHorizVel(){
        return horizVel;
    }
    
}
