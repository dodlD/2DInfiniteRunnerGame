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
    public static final int WIDTH = SPRITE_SIZE;
    public static final int HEIGHT = SPRITE_SIZE;
    private static final SpriteSheet sheet = new SpriteSheet("/irgame/res/textures/moon_block_sprite_sheet.png");
    //public static Image sprite;
    public static Image sprite[] = new Image[5];
    public int spriteXPos;
    public int spriteYPos;
    public int xPos;
    public int yPos;
    public int HORIZ_VEL = 2;
    public Rectangle hitBox;
    
    public Ground(int spriteXPos, int spriteYPos, int startXPos, int startYPos){
        this.spriteXPos = spriteXPos * WIDTH;
        this.spriteYPos = spriteYPos * HEIGHT;
        for (int i = 0; i < sprite.length; i++){
            sprite[i] = sheet.img.getSubimage(i*WIDTH, this.spriteYPos, WIDTH, HEIGHT);
        }
        
        xPos = startXPos * WIDTH;
        yPos = Game.HEIGHT - startYPos * HEIGHT;
        hitBox = new Rectangle(xPos, yPos, WIDTH, HEIGHT);
    }
}
