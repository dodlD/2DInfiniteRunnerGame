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
public class Ground{
    //private static double velocity;
    //private static double friction/*Koeffisient*/; //Friction koeffisient? for the current underlag
    private static int xPos;
    private static int yPos;
    private static final int SIZE = 32;
    private static int spriteXPos;
    private static int spriteYPos;
    private static SpriteSheet sheet = new SpriteSheet("/irgame/res/textures/tempGroundSpriteSheet.png");
    private static Image img;
    
    public Ground(int spriteXPos, int spriteYPos, int startXPos){
        this.spriteXPos = spriteXPos * SIZE;
        this.spriteYPos = spriteYPos * SIZE;
        img = sheet.img.getSubimage(spriteXPos, spriteYPos, SIZE, SIZE);
        this.xPos = xPos;
    }
    
    public Image getImg(){
        return img;
    }
    
    public static int getXPos(){
        return xPos;
    }
    
    public void setXPos(int xPos){
        this.xPos += xPos;   
    }
    
    public static final int getSIZE(){
        return SIZE;
    }
}
