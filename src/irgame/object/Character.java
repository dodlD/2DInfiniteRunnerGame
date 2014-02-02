/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package irgame.object;

import irgame.Game;
import irgame.graphics.SpriteSheet;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Rectangle;

/**
 *
 * @author lkpit11dljo
 */
public class Character{
    public static final int SPRITE_SIZE = 32;
    public final int WIDTH = SPRITE_SIZE;
    public final int HEIGHT = SPRITE_SIZE * 2;
    private static final SpriteSheet sheet = new SpriteSheet("/irgame/res/textures/tempCharSpriteSheet.png");
    public static final Image HEAD = sheet.img.getSubimage(0, 0, SPRITE_SIZE, SPRITE_SIZE);
    public static final Image BODY = sheet.img.getSubimage(32, 0, SPRITE_SIZE, SPRITE_SIZE);
    private final int START_X_POS = Game.WIDTH / 2 - Ground.SPRITE_SIZE;
    private final int START_Y_POS = Game.HEIGHT / 2;
    //public final int HEIGHT = SIZE * 2;
    public int xPos = START_X_POS;
    public int yPos = START_Y_POS;
    public int HORIZ_VEL = 2;
    public final int JUMP_FORCE = 4; //The force of a characters jump measured in pixels
    public final int JUMP_HEIGHT = 64;
    public String state = "standing";
    public Rectangle hitBox = new Rectangle(xPos, yPos, WIDTH, HEIGHT);

    public Character(){
    }
    
    public boolean Intersect(Rectangle r){
        //System.out.println("(" + hitBox.x + ", " + hitBox.y + "), (" + (hitBox.x+hitBox.width) + ", " + (hitBox.y+hitBox.height) + ")");
        //System.out.println("(" + r.x + ", " + r.y + "), (" + (r.x+r.width) + ", " + (r.y+r.height) + ")");
        return hitBox.intersects(r);
    }
    
}