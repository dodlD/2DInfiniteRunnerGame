/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IRGame.Object;

import IRGame.Graphics.Sprite;
import IRGame.Graphics.SpriteSheet;
import java.awt.Image;

/**
 *
 * @author lkpit11dljo
 */
public class Ground {
    //private static double velocity;
    //private static double friction/*Koeffisient*/; //Friction koeffisient? for the current underlag
    private static int spriteXPos;
    private static int spriteYPos;
    private static SpriteSheet sheet = new SpriteSheet("res/textures/tempGroundSpriteSheet.png");
    private Sprite sprite;
    private static Image img;
    
    public Ground(int spriteXPos, int spriteYPos){
        this.spriteXPos = spriteXPos;
        this.spriteYPos = spriteYPos;
        sprite = new Sprite(32, spriteXPos, spriteYPos, sheet);
        img = sprite.getImg();
    }
    
    public Image getImg(){
        return img;
    }
}
