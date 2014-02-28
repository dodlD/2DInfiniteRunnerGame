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
public class Obstacle{
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;
    public static final SpriteSheet sheet = new SpriteSheet("/irgame/res/textures/obstacle_sprite_sheet.png");
    public int spriteXPos;
    public int spriteYPos;
    public int xPos;
    public int yPos;
    public final Image BODY = sheet.img.getSubimage(0, 0, WIDTH, HEIGHT); //public final Image BODY[] = new Image[5];
    public Rectangle hitBox;
    
    public Obstacle(int spriteXPos, int spriteYPos, int startXPos, int startYPos){
        //this.spriteXPos = spriteXPos * WIDTH;
        //this.spriteYPos = spriteYPos * HEIGHT;
        /*for (int i = 0; i < BODY.length; i++){
            BODY[i] = sheet.img.getSubimage(i*WIDTH, this.spriteYPos, WIDTH, HEIGHT);
        }*/
        
        xPos = startXPos * WIDTH;
        yPos = Game.HEIGHT - startYPos * HEIGHT;
        hitBox = new Rectangle(xPos, yPos, WIDTH, HEIGHT);
    }
}
