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
                if (Game.ground[i].yPos < Game.HEIGHT - 32 && Game.chaR.xPos /*+ Game.chaR.WIDTH*/ </*=*/ Game.ground[i].xPos /*+ Game.ground[i].WIDTH*/){
                    switch (Game.chaR.state) {
                        case "standing":
                            Game.chaR.xPos -= Game.chaR.HORIZ_VEL;
                            //Game.chaR.yPos -= Game.gravity;
                            Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos);
                            break;
                        case "jumping":
                            Game.chaR.xPos -= Game.chaR.HORIZ_VEL;
                            Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos);
                            break;
                        case "falling":
                            //Game.chaR.xPos -= Game.chaR.HORIZ_VEL;
                            Game.chaR.yPos -= Game.gravity;
                            Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos);
                            break;
                    }
                }else {
                    Game.chaR.yPos -= Game.gravity;
                    Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos);
                    Game.chaR.state = "standing";
                }
            }
        }     
    }
}
