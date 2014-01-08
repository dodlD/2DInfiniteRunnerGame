/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package irgame.object;

import irgame.Game;
import irgame.graphics.SpriteSheet;
import java.awt.Image;
import java.awt.Rectangle;

/**
 *
 * @author lkpit11dljo
 */
public class Ground{
    //private static double velocity;
    //private static double friction/*Koeffisient*/; //Friction koeffisient? for the current underlag
    public static final int SPRITE_SIZE = 32;
    public final int WIDTH = SPRITE_SIZE;
    public final int HEIGHT = SPRITE_SIZE;
    private static SpriteSheet sheet = new SpriteSheet("/irgame/res/textures/tempGroundSpriteSheet.png");
    public static Image sprite;
    private int spriteXPos;
    private int spriteYPos;
    public int xPos;
    public int yPos;
    public Rectangle hitBox;
    
    public Ground(int spriteXPos, int spriteYPos, int startXPos, int startYPos){
        this.spriteXPos = spriteXPos * SPRITE_SIZE;
        this.spriteYPos = spriteYPos * SPRITE_SIZE;
        sprite = sheet.img.getSubimage(spriteXPos, spriteYPos, SPRITE_SIZE, SPRITE_SIZE);
        xPos = startXPos * SPRITE_SIZE;
        yPos = Game.HEIGHT - startYPos * SPRITE_SIZE;
        hitBox = new Rectangle(xPos, yPos, WIDTH, HEIGHT);
    }
}
