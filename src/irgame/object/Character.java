/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package irgame.object;

import irgame.Game;
import irgame.graphics.SpriteSheet;
import java.awt.Image;

/**
 *
 * @author lkpit11dljo
 */
public class Character{
    private final int SIZE = 32;
    private static final SpriteSheet sheet = new SpriteSheet("/irgame/res/textures/tempCharSpriteSheet.png");
    private final Image HEAD = sheet.img.getSubimage(0, 0, SIZE, SIZE);
    private final Image BODY = sheet.img.getSubimage(32, 0, SIZE, SIZE);
    private int xPos;
    private int yPos;
    private int StartXPos = Game.WIDTH / 2 - Ground.getSIZE();
    private static int StartYPos = Game.HEIGHT - Ground.getSIZE()*3;
    private final int HORIZ_VEL = 5;
    private final int JUMP_FORCE = 32; //The force of a characters jump measured in pixels
    private String state;
    
    
    
    public Character(){
        xPos = StartXPos;
        yPos = StartYPos;
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
    
    public final int getHORIZ_VEL(){
        return HORIZ_VEL;
    }
    
    public final int getJumpForce(){
        return JUMP_FORCE;
    }
    
    public String getState(){
        return state;
    }
    
    public void setState(String state){
        this.state = state;
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
