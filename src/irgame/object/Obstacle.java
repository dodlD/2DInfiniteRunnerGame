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
    public static final int WIDTH = 15;
    public static final int HEIGHT = 14;
    public static final SpriteSheet sheet = new SpriteSheet("/irgame/res/textures/obstacles_sprite_sheet.png");
    public int xPos;
    public int yPos;
    public final Image sprite = sheet.img.getSubimage(0, 0, WIDTH, HEIGHT); //public final Image BODY[] = new Image[5];
    public Rectangle hitBox;
    
    public Obstacle(int startXPos, int startYPos){     
        xPos = startXPos;
        yPos = startYPos - HEIGHT;
        hitBox = new Rectangle(xPos, yPos, WIDTH, HEIGHT);
    }
}
