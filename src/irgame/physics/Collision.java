package irgame.physics;

import irgame.Game;

/*
 *
 * @author Daniel Johansson
 */
public class Collision {
    public static void update(){
        for (int i = 0; i < Game.ground.length; i++){
            //System.out.println(Game.chaR.state);
            switch (Game.chaR.state){
                case "walking":
                    if (Game.chaR.Intersect(Game.ground[i].hitBox)){
                        if ((Game.HEIGHT - Game.ground[i].yPos) / 32  > 1){
                            if (Game.chaR.yPos + Game.chaR.HEIGHT > Game.ground[i].yPos + 4){
                                Game.chaR.xPos -= Game.ground[i].HORIZ_VEL + Game.chaR.HORIZ_VEL;
                            }else if (Game.chaR.xPos > Game.ground[i].xPos + Game.ground[i].WIDTH - 4){
                                if (i == 20){
                                    if ((Game.HEIGHT - Game.ground[i].yPos) / 32 > (Game.HEIGHT - Game.ground[0].yPos) / 32){
                                        Game.chaR.state = "falling";
                                    }
                                }else {
                                    if ((Game.HEIGHT - Game.ground[i].yPos) / 32 > (Game.HEIGHT - Game.ground[i+1].yPos) / 32){
                                        Game.chaR.state = "falling";
                                    }
                                } 
                            }else {
                                    Game.chaR.yPos -= Game.gravity;
                                }
                        }else {
                            Game.chaR.yPos -= Game.gravity;
                        }
                        Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos);
                    } 
                    break;
                    
                case "jumping":
                    if (Game.chaR.Intersect(Game.ground[i].hitBox) ){
                        if ((Game.HEIGHT - Game.ground[i].yPos) / 32  > 1){
                            Game.chaR.xPos -= Game.ground[i].HORIZ_VEL + Game.chaR.HORIZ_VEL;
                        }else {
                            Game.chaR.state = "walking";
                        }
                        Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos);
                    }
                    break;
                    
                case "falling":
                    if (Game.chaR.Intersect(Game.ground[i].hitBox) ){
                        if (Game.chaR.yPos + Game.chaR.HEIGHT > Game.ground[i].yPos && Game.chaR.yPos + Game.chaR.HEIGHT < Game.ground[i].yPos + 5){
                                Game.chaR.yPos -= Game.gravity;
                                Game.chaR.state = "walking";
                        }else {
                            if ((Game.HEIGHT - Game.ground[i].yPos) / 32  > 1){
                                Game.chaR.xPos -= Game.ground[i].HORIZ_VEL + Game.chaR.HORIZ_VEL;    
                            }else {
                                Game.chaR.state = "walking";
                            }
                        }
                        Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos);
                    }
                    break;
            }
        }
    }
    public static boolean deadCheck(){
        boolean dead = false;
        for (int i = 0; i < Game.obstacle.length; i++){
            if (Game.chaR.Intersect(Game.obstacle[i].hitBox)){
                dead = true;
            }
        }
        return dead; 
    }
}
