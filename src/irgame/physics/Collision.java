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
        
        
        /*if (!Game.chaR.state.equals("jumping")){
            for (int i = 0; i < Game.ground.length; i++){
                
                
                if (Game.chaR.xPos + Game.chaR.WIDTH > Game.ground[i].xPos){
                    
                    currGrndObj = i;
                    
                    //System.out.println("groundObj: " + currGrndObj + ", xPos: " + Game.ground[currGrndObj].xPos + ", yPos: " + Game.ground[currGrndObj].yPos);
                }
            }
            if (Game.chaR.yPos + Game.chaR.HEIGHT > Game.ground[currGrndObj].yPos){
                Game.chaR.yPos -= Game.gravity;
                Game.chaR.state = "standing";
            }else{
                Game.chaR.state = "falling";
            }
            if (currGrndObj == 20){
                currGrndObj = -1;
            }
            if (Game.chaR.xPos + Game.chaR.WIDTH >= Game.ground[currGrndObj + 1].xPos && Game.ground[currGrndObj + 1].yPos < Game.HEIGHT - Game.ground[currGrndObj + 1].HEIGHT){
                    Game.chaR.xPos -= Game.chaR.HORIZ_VEL;
                }
        }*/
        System.out.println(Game.chaR.state);
        for (int i = 0; i < Game.ground.length; i++){     
            if (Game.chaR.Intersect(Game.ground[i].hitBox)){
                if (Game.chaR.xPos + Game.chaR.WIDTH >= Game.ground[i].xPos && Game.ground[i].yPos < Game.HEIGHT - 32){
                    if (Game.chaR.state.equals("jumping")){
                        Game.chaR.xPos -= Game.chaR.HORIZ_VEL;                      
                    }else if (Game.chaR.state.equals("falling")){ 
                        if (Game.chaR.yPos + 64 > Game.ground[i].yPos && Game.chaR.yPos + 64 < Game.ground[i].yPos + 32){
                            Game.chaR.xPos -= Game.chaR.HORIZ_VEL;
                            Game.chaR.yPos += Game.gravity;
                        }
                        Game.chaR.yPos -= Game.gravity;
                    }else {
                        Game.chaR.xPos -= Game.chaR.HORIZ_VEL; 
                        Game.chaR.yPos -= Game.gravity;
                        System.out.println("yolo");
                    }
                }else {
                    Game.chaR.yPos -= Game.gravity;
                    Game.chaR.state = "standing";
                }
                Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos);  
            }   
        }     
    }
}
