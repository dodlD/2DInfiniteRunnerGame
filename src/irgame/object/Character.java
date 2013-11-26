/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package irgame.object;

import irgame.graphics.SpriteSheet;
import java.awt.Image;

/**
 *
 * @author lkpit11dljo
 */
public class Character{
    private int xPos;
    private int yPos;
    private static int horizVel = 1;
    private final int SIZE = 32;
    private static final SpriteSheet sheet = new SpriteSheet("/irgame/res/textures/tempCharSpriteSheet.png");
    private final Image HEAD = sheet.img.getSubimage(0, 0, SIZE, SIZE);
    private final Image BODY = sheet.img.getSubimage(32, 0, SIZE, SIZE);
    
    
    
    public Character(int StartXPos, int StartYPos){
        this.xPos = StartXPos;
        this.yPos = StartYPos;
    }
    
    public int getXPos(){
        return xPos;
    }
    
    public void addXPos(int xPos){
        this.xPos += xPos;
    }
    
    public void delXPos(int xPos){
        this.xPos -= xPos;
    }
    
    public int getYPos(){
        return yPos;
    }
    
    public void addYPos(int yPos){
        this.yPos += yPos;
    }
    
    public void delYPos(int yPos){
        this.yPos -= yPos;
    }
    
    public static double getHorizVel(){
        return horizVel;
    }
    
    public final int getSIZE(){
        return SIZE;
    }
    
    public final Image getHeadSprite(){
        return HEAD;
    }
    
    public final Image getBodySprite(){
        return BODY;
    }
    
    
    
}
