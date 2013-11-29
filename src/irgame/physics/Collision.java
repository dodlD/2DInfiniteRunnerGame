package irgame.physics;

import irgame.Game;
import irgame.object.Character;

/*
 *
 * @author Daniel Johansson
 */
public class Collision {
    public static void update(){
        /*for (int i = 0; i < Game.ground.length; i++){
            //Object sides
            if (Game.chaR.yPos < Game.ground[i].yPos && Game.chaR.xPos > Game.ground[i].xPos){
                Game.chaR.xPos -= Game.chaR.HORIZ_VEL;
            }    
        }*/
        
        
        
        if (Game.chaR.yPos + Game.chaR.HEIGHT > Game.ground[9].yPos){
            Game.chaR.yPos -= Game.gravity;
            Game.chaR.state = "standing";
        }else{
            Game.chaR.state = "falling";
        }
    }
}
