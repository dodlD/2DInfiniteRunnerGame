package irgame.physics;

import irgame.Game;

/*
 *
 * @author Daniel Johansson
 */
public class Collision {
    public static void update(){
        for (int i = 0; i < Game.ground.length; i++){
            if (Game.chaR.Intersect(Game.ground[i].hitBox)){
                if (Game.ground[i].yPos < Game.HEIGHT - Game.ground[i].HEIGHT && Game.chaR.xPos /*+ Game.chaR.WIDTH >*/ </*=*/ Game.ground[i].xPos /*+ Game.ground[i].WIDTH*/){
                    switch (Game.chaR.state) {
                        case "walking":
                            Game.chaR.xPos -= Game.ground[0].HORIZ_VEL + Game.chaR.HORIZ_VEL;
                            //Game.chaR.yPos -= Game.gravity;
                            Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos);
                            break;
                        case "jumping":
                            Game.chaR.xPos -= Game.ground[0].HORIZ_VEL + Game.chaR.HORIZ_VEL;
                            Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos);
                            break;
                        case "falling":
                            if (Game.chaR.xPos + Game.chaR.WIDTH > Game.ground[i].yPos){
                                System.out.println("hej");
                                Game.chaR.xPos -= Game.ground[0].HORIZ_VEL + Game.chaR.HORIZ_VEL;
                            }else{
                                Game.chaR.yPos -= Game.gravity;
                            }
                            
                            Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos);
                            break;
                    }
                }else {
                    Game.chaR.yPos -= Game.gravity;
                    Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos);
                    Game.chaR.state = "walking";
                }
            }
        }     
    }
}
