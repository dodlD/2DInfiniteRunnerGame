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
public class Ground{
    //private static double velocity;
    //private static double friction/*Koeffisient*/; //Friction koeffisient? for the current underlag
    public static final int SIZE = 32;
    private static SpriteSheet sheet = new SpriteSheet("/irgame/res/textures/tempGroundSpriteSheet.png");
    public static Image sprite;
    private int spriteXPos;
    private int spriteYPos;
    public int xPos;
    public int yPos;
    
    public Ground(int spriteXPos, int spriteYPos, int startXPos, int startYPos){
        this.spriteXPos = spriteXPos * SIZE;
        this.spriteYPos = spriteYPos * SIZE;
        sprite = sheet.img.getSubimage(spriteXPos, spriteYPos, SIZE, SIZE);
        xPos = startXPos * SIZE;
        yPos = Game.HEIGHT - startYPos * SIZE;
    }
}
