/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IRGame.Graphics;

import java.awt.Image;

/**
 *
 * @author Daniel
 */
public class Sprite {
    private final int SIZE;
    private int x, y;
    private SpriteSheet sheet;
    private Image sprite;
    
    public Sprite(int size, int x, int y, SpriteSheet sheet){
        SIZE = size;
        this.x = x * size;
        this.y = y * size;
        this.sheet = sheet;
        sprite = sheet.img.getSubimage(x, y, SIZE, SIZE);
    }
    
    public Image getImg(){
        return sprite;
    }
}
