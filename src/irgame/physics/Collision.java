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
            if (Game.chaR.Intersect(Game.ground[i].hitBox) ){
                if ((Game.HEIGHT - Game.ground[i].yPos) / 32  > 1 && Game.chaR.xPos + Game.chaR.WIDTH >= Game.ground[i].xPos){
                    System.out.println("1");
                    if (Game.chaR.yPos + Game.chaR.HEIGHT > Game.ground[i].yPos){ //LADE TILL DETTA
                        Game.chaR.state = "walking";
                    }
                    switch (Game.chaR.state){
                        case "walking":
                            Game.chaR.xPos -= Game.ground[i].HORIZ_VEL + Game.chaR.HORIZ_VEL;
                            Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos);
                            break;
                        case "jumping":
                            Game.chaR.xPos -= Game.ground[i].HORIZ_VEL + Game.chaR.HORIZ_VEL;
                            Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos);
                            break;
                        case "falling":
                            Game.chaR.xPos -= Game.ground[i].HORIZ_VEL + Game.chaR.HORIZ_VEL;
                            Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos);
                            break;
                    }
                }else if ("hej" == "korv"){
                    System.out.println("2");
                    
                }else {
                    System.out.println("3");
                    if (Game.chaR.yPos + Game.chaR.HEIGHT > Game.ground[i].yPos){
                        Game.chaR.state = "walking";
                    }
                    switch (Game.chaR.state){
                        case "walking":
                            Game.chaR.yPos -= Game.gravity;
                            Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos);
                            break;
                        case "jumping":
                            //Game.chaR.xPos -= Game.ground[i].HORIZ_VEL + Game.chaR.HORIZ_VEL;
                            Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos);
                            break;
                        case "falling":
                            //Game.chaR.xPos -= Game.ground[i].HORIZ_VEL + Game.chaR.HORIZ_VEL;
                            Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos);
                            break;
                    }
                }
            }    
        }
    }
            
            /*if (Game.chaR.Intersect(Game.ground[i].hitBox)){     
                if (Game.ground[i].yPos < Game.HEIGHT - Game.ground[i].HEIGHT && Game.chaR.xPos + Game.chaR.WIDTH >= Game.ground[i].xPos ){
                    switch (Game.chaR.state) {
                        case "walking":
                            Game.chaR.xPos -= Game.ground[i].HORIZ_VEL + Game.chaR.HORIZ_VEL;
                            //Game.chaR.yPos -= Game.gravity;
                            Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos);
                            break;
                        case "jumping":
                            Game.chaR.xPos -= Game.ground[i].HORIZ_VEL + Game.chaR.HORIZ_VEL;
                            Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos);
                            break;
                        case "falling":
                            //Game.chaR.xPos -= Game.ground[0].HORIZ_VEL + Game.chaR.HORIZ_VEL;
                            Game.chaR.yPos -= Game.gravity;
                            Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos);
                            break;
                    }
                }
                    
                }else {
                    Game.chaR.yPos -= Game.gravity;
                    Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos);
                    Game.chaR.state = "walking";
                }
                }
            }*/     
}
