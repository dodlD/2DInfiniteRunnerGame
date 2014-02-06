package irgame.physics;

import irgame.Game;

/*
 *
 * @author Daniel Johansson
 */
public class Collision {
    public static void update(){
        for (int i = 0; i < Game.ground.length; i++){
            switch (Game.chaR.state){
                case "walking":
                    if (Game.chaR.Intersect(Game.ground[i].hitBox) ){
                        if ((Game.HEIGHT - Game.ground[i].yPos) / 32  > 1){
                            if(Game.chaR.yPos + Game.chaR.HEIGHT < Game.ground[i].yPos + 5){ //Tillfällig lösning
                                Game.chaR.yPos -= Game.gravity;
                            }else {
                                Game.chaR.xPos -= Game.ground[i].HORIZ_VEL + Game.chaR.HORIZ_VEL;
                            }
                        }else {
                            Game.chaR.yPos -= Game.gravity; 
                        }
                    }
                    Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos); 
                    break;
                case "jumping":
                    if (Game.chaR.Intersect(Game.ground[i].hitBox) ){
                        if ((Game.HEIGHT - Game.ground[i].yPos) / 32  > 1){
                            Game.chaR.xPos -= Game.ground[i].HORIZ_VEL + Game.chaR.HORIZ_VEL;
                        }else {
                            Game.chaR.state = "walking";
                        }
                    }
                    Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos);
                    break;
                case "falling":
                    if (Game.chaR.Intersect(Game.ground[i].hitBox) ){
                        if ((Game.HEIGHT - Game.ground[i].yPos) / 32  > 1){
                            Game.chaR.xPos -= Game.ground[i].HORIZ_VEL + Game.chaR.HORIZ_VEL;
                            if(Game.chaR.yPos + Game.chaR.HEIGHT >= Game.ground[i].yPos){
                                Game.chaR.yPos -= Game.gravity;
                                Game.chaR.state = "walking";
                            }
                        }else {
                            Game.chaR.state = "walking";
                        }
                    Game.chaR.hitBox.setLocation(Game.chaR.xPos, Game.chaR.yPos);
                    break;
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
