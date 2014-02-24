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
    public static final int WIDTH = 47;
    public static final int HEIGHT = 62;
    public static final SpriteSheet sheet = new SpriteSheet("/irgame/res/textures/char_sprite_sheet.png");
    public Image BODY = sheet.img.getSubimage(0, 0, WIDTH, HEIGHT);
    private final int START_X_POS = Game.WIDTH / 2 - Ground.WIDTH;
    private final int START_Y_POS = Game.HEIGHT / 2 - HEIGHT;
    public int xPos = START_X_POS;
    public int yPos = START_Y_POS;
    public int HORIZ_VEL = 0;
    public final int JUMP_FORCE = 4; //The force of a characters jump measured in pixels
    public final int JUMP_HEIGHT = HEIGHT/(3/2);
    public String state = "falling";
    public Rectangle hitBox = new Rectangle(xPos, yPos, WIDTH, HEIGHT);

    public Character(){
    }
    
    public boolean Intersect(Rectangle r){
        return hitBox.intersects(r);
    }
    
}