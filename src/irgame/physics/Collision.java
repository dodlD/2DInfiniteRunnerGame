package irgame.physics;

import irgame.Game;
import irgame.object.Character;

/*
 *
 * @author Daniel Johansson
 */
public class Collision {
    private static int groundObj;
    public static void update(){
        /*for (int i = 0; i < Game.ground.length; i++){
            //Object sides
            if (Game.chaR.yPos < Game.ground[i].yPos && Game.chaR.xPos > Game.ground[i].xPos){
                Game.chaR.xPos -= Game.chaR.HORIZ_VEL;
            }    
        }*/
        
        
        if (!Game.chaR.state.equals("jumping")){
            for (int i = 0; i < Game.ground.length; i++){
                if (Game.ground[i].xPos <= 320 && Game.ground[i].xPos >= 288){
                    groundObj = i;
                    System.out.println("groundObj: " + groundObj + ", xPos: " + Game.ground[groundObj].xPos);
                }
            }
            
            if (Game.chaR.yPos + Game.chaR.HEIGHT > Game.ground[/*9*/groundObj].yPos){
                Game.chaR.yPos -= Game.gravity;
                Game.chaR.state = "standing";
            }else{
                Game.chaR.state = "falling";
            }
        }
    }
}
