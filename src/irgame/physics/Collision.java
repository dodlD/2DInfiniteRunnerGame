package irgame.physics;

import irgame.Game;
import irgame.object.Character;

/*
 *
 * @author Daniel Johansson
 */
public class Collision {
    private static int prevGrndObj;
    private static int currGrndObj;
    public static void update(){
        /*for (int i = 0; i < Game.ground.length; i++){
            //Object sides
            if (Game.chaR.yPos < Game.ground[i].yPos && Game.chaR.xPos > Game.ground[i].xPos){
                Game.chaR.xPos -= Game.chaR.HORIZ_VEL;
            }    
        }*/
        
        
        if (!Game.chaR.state.equals("jumping")){
            for (int i = 0; i < Game.ground.length; i++){
                if (prevGrndObj == 0){
                    prevGrndObj = 1;
                }
                if (Game.chaR.xPos + Game.chaR.WIDTH > Game.ground[i].xPos){
                    
                    prevGrndObj = i-1;
                    currGrndObj = i;
                    
                    System.out.println("groundObj: " + currGrndObj + ", xPos: " + Game.ground[currGrndObj].xPos + ", yPos: " + Game.ground[currGrndObj].yPos);
                }
            }
            if (Game.chaR.xPos < Game.ground[prevGrndObj].xPos + Game.ground[prevGrndObj].WIDTH){
                if (Game.chaR.yPos + Game.chaR.HEIGHT > Game.ground[currGrndObj].yPos -32){
                    Game.chaR.yPos -= Game.gravity;
                    Game.chaR.state = "standing";
                }else{
                    Game.chaR.state = "falling";
                }
            }
            if (Game.chaR.yPos + Game.chaR.HEIGHT > Game.ground[currGrndObj].yPos){
                    Game.chaR.yPos -= Game.gravity;
                    Game.chaR.state = "standing";
                }else{
                    Game.chaR.state = "falling";
                }
        }
    }
}
