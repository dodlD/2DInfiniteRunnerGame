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
    public final int SIZE = 32;
    private static final SpriteSheet sheet = new SpriteSheet("/irgame/res/textures/tempCharSpriteSheet.png");
    public final Image HEAD = sheet.img.getSubimage(0, 0, SIZE, SIZE);
    public final Image BODY = sheet.img.getSubimage(32, 0, SIZE, SIZE);
    private final int START_X_POS = Game.WIDTH / 2 - Ground.getSIZE();
    private final int START_Y_POS = Game.HEIGHT - Ground.getSIZE() * 3;
    public int xPos = START_X_POS;
    public int yPos = START_Y_POS;
    public final int HORIZ_VEL = 5;
    public final int JUMP_FORCE = 32; //The force of a characters jump measured in pixels
    public String state;

    public Character(){
    }
}