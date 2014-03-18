/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package irgame.object;

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
    public final SpriteSheet sheet = new SpriteSheet("/irgame/res/textures/obstacles_sprite_sheet.png");    //Loads the obstacle sprite sheet.
    public final Image sprite = sheet.img.getSubimage(0, 0, WIDTH, HEIGHT); //Sets the sprite for the obstacle.
    public int xPos;
    public int yPos;
    public Rectangle hitBox;
    
    public Obstacle(int startXPos, int startYPos){     //The constructor that sets the starting position for the obstacle.
        xPos = startXPos;
        yPos = startYPos - HEIGHT;
        hitBox = new Rectangle(xPos, yPos, WIDTH, HEIGHT);
    }
    
    public static void render(Graphics g, ArrayList<Obstacle> obst){    //Takes care of the rendering of the obstacle.
       for (int i = 0; i < obst.size(); i++){
            g.drawImage(obst.get(i).sprite, obst.get(i).xPos, obst.get(i).yPos, null);
        } 
    }
    
    public static boolean newObstacle(ArrayList<Obstacle> obst, Ground[] grnd, int level, int r, int i){ //Checks if it is possible to create a new obstacle and returns true if it is.
        boolean nO = false;
        if (r > 10-level && obst.get(obst.size()-1).xPos + grnd[i].WIDTH*4 < grnd[i].xPos){
            nO = true;
        }
        return nO;
    }
    
    public static boolean delObstacle(ArrayList<Obstacle> obst, Ground[] grnd, int x){  //Checks if it is possible to delete the obstacle and returns true if it is.
        boolean dO = false;
        if (obst.get(x).xPos <= -obst.get(x).WIDTH && obst.size() > 1){
            dO = true;
        }
        return dO;
    }
}
