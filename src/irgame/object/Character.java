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
    private static boolean standing;
    private final int HORIZ_VEL = 5;
    private final int JUMP_FORCE = 32; //The force of a characters jump measured in pixels
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
    
    public void subXPos(int xPos){
        this.xPos -= xPos;
    }
    
    public int getYPos(){
        return yPos;
    }
    
    public void addYPos(int yPos){
        this.yPos += yPos;
    }
    
    public void subYPos(int yPos){
        this.yPos -= yPos;
    }
    
    public final int getHorizVel(){
        return HORIZ_VEL;
    }
    
    public final int getJumpForce(){
        return JUMP_FORCE;
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
