package irgame.collision;

import irgame.Game;

/*
 *
 * @author Daniel Johansson
 */
public class Collision {
    private static int x;
    public static void update(){
        for (int i = 0; i < Game.ground.length; i++){
            //System.out.println(Game.chaR.state);
            switch (Game.chaR.state){
                case "walking":
                    if (Game.chaR.headIntersect(Game.ground[i].hitBox) || Game.chaR.bodyIntersect(Game.ground[i].hitBox)){
                        if ((Game.HEIGHT - Game.ground[i].yPos) / 32  > 1){
                            if (Game.chaR.yPos + Game.chaR.HEIGHT > Game.ground[i].yPos + 4){
                                Game.chaR.xPos -= Game.ground[i].HORIZ_VEL + Game.chaR.HORIZ_VEL;
                            }else {
                                Game.chaR.yPos -= Game.gravity;
                            }
                        }else {
                            Game.chaR.yPos -= Game.gravity;
                        }
                        if (Game.chaR.xPos > Game.ground[i].xPos + Game.ground[i].WIDTH - 13){
                            if (i == 20){
                                if ((Game.HEIGHT - Game.ground[i].yPos) / 32 > (Game.HEIGHT - Game.ground[0].yPos) / 32){
                                    Game.chaR.state = "falling";
                                }
                            }else {
                                if ((Game.HEIGHT - Game.ground[i].yPos) / 32 > (Game.HEIGHT - Game.ground[i+1].yPos) / 32){
                                    Game.chaR.state = "falling";
                                }
                            } 
                        }
                    }
                    /*else if (Game.chaR.xPos > Game.ground[i].xPos + Game.ground[i].WIDTH - 4){
                        System.out.println("yolo");
                        if (i == 20){
                            if ((Game.HEIGHT - Game.ground[i].yPos) / 32 > (Game.HEIGHT - Game.ground[0].yPos) / 32){
                                Game.chaR.state = "falling";
                            }
                        }else {
                            if ((Game.HEIGHT - Game.ground[i].yPos) / 32 > (Game.HEIGHT - Game.ground[i+1].yPos) / 32){
                                Game.chaR.state = "falling";
                            }
                        } 
                    }*/
                    Game.chaR.bodyHitBox.setLocation(Game.chaR.xPos + 11, Game.chaR.yPos + 45);
                    break;
                    
                case "jumping":
                    if (Game.chaR.headIntersect(Game.ground[i].hitBox) || Game.chaR.bodyIntersect(Game.ground[i].hitBox)){
                        if ((Game.HEIGHT - Game.ground[i].yPos) / 32  > 1){
                            Game.chaR.xPos -= Game.ground[i].HORIZ_VEL + Game.chaR.HORIZ_VEL;
                        }else {
                            Game.chaR.state = "walking";
                        }  
                    }
                    Game.chaR.bodyHitBox.setLocation(Game.chaR.xPos + 12, Game.chaR.yPos + 45);
                    break;
                    
                case "falling":
                    if (Game.chaR.headIntersect(Game.ground[i].hitBox) || Game.chaR.bodyIntersect(Game.ground[i].hitBox)){
                        if (Game.chaR.yPos + Game.chaR.HEIGHT > Game.ground[i].yPos && Game.chaR.yPos + Game.chaR.HEIGHT < Game.ground[i].yPos + 5){
                                Game.chaR.yPos -= Game.gravity;
                                Game.chaR.state = "walking";
                        }else if ((Game.HEIGHT - Game.ground[i].yPos) / 32  > 1){
                            Game.chaR.xPos -= Game.ground[i].HORIZ_VEL + Game.chaR.HORIZ_VEL;    
                        }else {
                            Game.chaR.state = "walking";
                        }
                    }
                    Game.chaR.bodyHitBox.setLocation(Game.chaR.xPos + 12, Game.chaR.yPos + 45);
                    break;
            }
            Game.chaR.headHitBox.setLocation(Game.chaR.xPos + 8, Game.chaR.yPos);
        }
    }
    public static boolean deadCheck(){
        boolean dead = false;
        for (int i = 0; i < Game.obstacle.size(); i++){
            if (Game.chaR.headIntersect(Game.obstacle.get(i).hitBox) || Game.chaR.bodyIntersect(Game.obstacle.get(i).hitBox)){
                dead = true;
            }
        }
        return dead; 
    }
}
