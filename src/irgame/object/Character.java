/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package irgame.object;

import irgame.graphics.Sprite;
import irgame.graphics.SpriteSheet;

/**
 *
 * @author lkpit11dljo
 */
public class Character {
    private static int xPos;
    private static int yPos;
    private static double horizVel = 1.0;
    private static final SpriteSheet chaR = new SpriteSheet("/irgame/res/textures/tempCharSpriteSheet.png");
    private static final Sprite head = new Sprite(32, 0, 0, chaR);
    private static final Sprite body = new Sprite(32, 1, 0, chaR);
    
    
    public Character(int StartXPos, int StartYPos){
        this.xPos = StartXPos;
        this.yPos = StartYPos;
    }
    
    public static Sprite getSprite(){
        return head;
    }
    
    public static int getXPos(){
        return xPos;
    }
    
    public void setXPos(int xPos){
        this.xPos += xPos;
    }
    
    public static int getYPos(){
        return yPos;
    }
    
    public void setYPos(int yPos){
        this.yPos += yPos;
    }
    
    public static double getHorizVel(){
        return horizVel;
    }
    
}
