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
    public static final int SIZE = 32;
    private static final SpriteSheet sheet = new SpriteSheet("/irgame/res/textures/tempCharSpriteSheet.png");
    public static final Image HEAD = sheet.img.getSubimage(0, 0, SIZE, SIZE);
    public static final Image BODY = sheet.img.getSubimage(32, 0, SIZE, SIZE);
    private final int START_X_POS = Game.WIDTH / 2 - Ground.SIZE;
    private final int START_Y_POS = 195;//Game.HEIGHT - Ground.SIZE * 3;
    public final int HEIGHT = SIZE * 2;
    public int xPos = START_X_POS;
    public int yPos = START_Y_POS;
    public int HORIZ_VEL = 2;
    public final int JUMP_FORCE = 4; //The force of a characters jump measured in pixels
    public final int JUMP_HEIGHT = 32;
    public String state = "falling";

    public Character(){
    }
}