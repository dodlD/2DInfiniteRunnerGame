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
public class Character{
    private static final int WIDTH = 47;
    private static final int HEIGHT = 62;
    private static final SpriteSheet sheet = new SpriteSheet("/irgame/res/textures/char_sprite_sheet.png"); //Loads the character sprite sheet.
    private Image sprite = sheet.img.getSubimage(0, 0, WIDTH, HEIGHT);  //Sets the starting sprite for the charater.
    private final int START_X_POS = Game.WIDTH / 2 - Ground.WIDTH;
    private final int START_Y_POS = Game.HEIGHT / 2 - HEIGHT;
    public int xPos = START_X_POS;
    public int yPos = START_Y_POS;
    public int HORIZ_VEL = 0;   //The horizontal velocity of the charater.
    public final int JUMP_FORCE = 4; //The force of the characters jump.
    public final int JUMP_HEIGHT = HEIGHT + HEIGHT/(HEIGHT/10);
    public String state = "falling";
    public Rectangle headHitBox = new Rectangle(xPos + 11, yPos, WIDTH-8, 45);
    public Rectangle bodyHitBox = new Rectangle(xPos + 12, yPos+45, 35, 21);

    public Character(){ //The constructor that creates the character.
    }
    
    public boolean outOfArea(){ //Checks if the character is out of the windowarea and retruns true if it is.
        boolean oOA = false;
        if (xPos + WIDTH >= Game.WIDTH){    
            oOA = true;  
        }
        return oOA; 
    }
    
    public void  render(Graphics g){    //Takes care of the rendering of the character.
        g.drawImage(sprite, xPos, yPos, null);
    }
    
    public void setSprite(int updates){ //Takes care of the character animations.
        switch (state){ //Changes the characters sprite depending on its state and/or updates, which is used to decide when to switch sprite and what sprite to use for the animation
            case "walking":
                if (updates == 5 || updates == 25 || updates == 45){
                    sprite = sheet.img.getSubimage(0*WIDTH, 0, WIDTH, HEIGHT);
                    bodyHitBox.setBounds(xPos + 11, yPos + 45, 36, 17);
                }else if (updates == 10 || updates == 30 || updates == 50){
                    sprite = sheet.img.getSubimage(1*WIDTH, 0, WIDTH, HEIGHT);
                    bodyHitBox.setBounds(xPos + 8, yPos + 45, 37, 17);
 
                }else if (updates == 15 || updates == 35 || updates == 55){
                    sprite = sheet.img.getSubimage(0*WIDTH, 0, WIDTH, HEIGHT);
                    bodyHitBox.setBounds(xPos + 11, yPos + 45, 36, 17);
                }else if (updates == 20 || updates == 40 || updates == 60){
                    sprite = sheet.img.getSubimage(2*WIDTH, 0, WIDTH, HEIGHT);
                    bodyHitBox.setBounds(xPos + 5, yPos + 45, 37, 17);
                }
                break;
            case "jumping":
                sprite = sheet.img.getSubimage(3*WIDTH, 0, WIDTH, HEIGHT);
                bodyHitBox.setBounds(xPos + 12, yPos + 45, 35, 21);
                break;
            case "falling":
                sprite = sheet.img.getSubimage(3*WIDTH, 0, WIDTH, HEIGHT);
                bodyHitBox.setBounds(xPos + 12, yPos + 45, 35, 21);
                break;
        }
    }
    
    public void collision(Ground[] ground, int gravity){ //Takes care of collisions between the character and the ground.
        for (int i = 0; i < ground.length; i++){
            switch (state){
                case "walking":
                    if (headHitBox.intersects(ground[i].hitBox) || bodyHitBox.intersects(ground[i].hitBox)){
                        if ((Game.HEIGHT - ground[i].yPos) / 32  > 1){
                            if (yPos + HEIGHT > ground[i].yPos + 4){
                                xPos -= ground[i].HORIZ_VEL + HORIZ_VEL;
                            }else {
                                yPos -= gravity;
                            }
                        }else {
                            yPos -= gravity;
                        }
                        if (xPos > ground[i].xPos + ground[i].WIDTH - 13){
                            if (i == 20){
                                if ((Game.HEIGHT - ground[i].yPos) / 32 > (Game.HEIGHT - ground[0].yPos) / 32){
                                    state = "falling";
                                }
                            }else {
                                if ((Game.HEIGHT - ground[i].yPos) / 32 > (Game.HEIGHT - ground[i+1].yPos) / 32){
                                    state = "falling";
                                }
                            } 
                        }
                    }
                    bodyHitBox.setLocation(xPos + 11, yPos + 45);
                    break;
                    
                case "jumping":
                    if (headHitBox.intersects(ground[i].hitBox) || bodyHitBox.intersects(ground[i].hitBox)){
                        if ((Game.HEIGHT - ground[i].yPos) / 32  > 1){
                            xPos -= ground[i].HORIZ_VEL + HORIZ_VEL;
                        }else {
                            state = "walking";
                        }  
                    }
                    bodyHitBox.setLocation(xPos + 12, yPos + 45);
                    break;
                    
                case "falling":
                    if (headHitBox.intersects(ground[i].hitBox) || bodyHitBox.intersects(ground[i].hitBox)){
                        if (yPos + HEIGHT > ground[i].yPos && yPos + HEIGHT < ground[i].yPos + 5){
                                yPos -= gravity;
                                state = "walking";
                        }else if ((Game.HEIGHT - ground[i].yPos) / 32  > 1){
                            xPos -= ground[i].HORIZ_VEL + HORIZ_VEL;    
                        }else {
                            state = "walking";
                        }
                    }
                    bodyHitBox.setLocation(xPos + 12, yPos + 45);
                    break;
            }
            headHitBox.setLocation(xPos + 11, yPos);
        }
    }
    
    public boolean dead(ArrayList<Obstacle> o){ //Checks if the character collides with a obstacle and returns true if it does.
        boolean dead = false;
        for (int i = 0; i < o.size(); i++){
            if(headHitBox.intersects(o.get(i).hitBox) || bodyHitBox.intersects(o.get(i).hitBox)){
                dead = true;
            }
        }
        return dead;    
    }
}