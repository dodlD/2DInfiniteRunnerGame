/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package irgame.object;

import irgame.Game;
import irgame.graphics.SpriteSheet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author lkpit11dljo
 */
public class Obstacle{
    public static final int WIDTH = 15;
    public static final int HEIGHT = 14;
    public final SpriteSheet sheet = new SpriteSheet("/irgame/res/textures/obstacles_sprite_sheet.png");
    public int xPos;
    public int yPos;
    public final Image sprite = sheet.img.getSubimage(0, 0, WIDTH, HEIGHT); //public final Image BODY[] = new Image[5];
    public Rectangle hitBox;
    
    public Obstacle(int startXPos, int startYPos){     
        xPos = startXPos;
        yPos = startYPos - HEIGHT;
        hitBox = new Rectangle(xPos, yPos, WIDTH, HEIGHT);
    }
    
    public static void render(Graphics g, ArrayList<Obstacle> o){
       for (int i = 0; i < o.size(); i++){
            g.drawImage(o.get(i).sprite, o.get(i).xPos, o.get(i).yPos, null);
        } 
    }
    
    public static boolean newObstacle(ArrayList<Obstacle> o, Ground[] grnd, int r, int i){
        boolean nO = false;
        if (r > 9 && o.get(o.size()-1).xPos + grnd[i].WIDTH*4 < grnd[i].xPos){
            nO = true;
        }
        return nO;
    }
    
    public static boolean delObstacle(ArrayList<Obstacle> o, Ground[] grnd, int x){
        boolean dO = false;
        if (o.get(x).xPos <= -o.get(x).WIDTH && o.size() > 1){
            dO = true;
        }
        return dO;
    }
}
