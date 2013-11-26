/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package irgame.object;

import irgame.graphics.Sprite;
import irgame.graphics.SpriteSheet;
import java.awt.Image;

/**
 *
 * @author lkpit11dljo
 */
public class Character{
    private final int SIZE = 32;
    private static int xPos;
    private static int yPos;
    private static double horizVel = 1.0;
    private static final SpriteSheet sheet = new SpriteSheet("/irgame/res/textures/tempCharSpriteSheet.png");
    //private static final Sprite head = new Sprite(32, 0, 0, chaR);
    //private static final Sprite body = new Sprite(32, 1, 0, chaR);
    private final Image HEAD = sheet.img.getSubimage(0, 0, SIZE, SIZE);
    private final Image BODY = sheet.img.getSubimage(32, 0, SIZE, SIZE);
    
    
    
    public Character(int StartXPos, int StartYPos){
        this.xPos = StartXPos;
        this.yPos = StartYPos;
    }
    
    public final Image getHeadSprite(){
        return HEAD;
    }
    
    public final Image getBodySprite(){
        return BODY;
    }
    
    public int getXPos(){
        return xPos;
    }
    
    public void addXPos(int xPos){
        this.xPos += xPos;
    }
    
    public void remXPos(int xPos){
        this.xPos -= xPos;
    }
    
    public int getYPos(){
        return yPos;
    }
    
    public void setYPos(int yPos){
        this.yPos += yPos;
    }
    
    public static double getHorizVel(){
        return horizVel;
    }
    
}
