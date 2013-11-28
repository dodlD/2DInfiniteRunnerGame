package irgame.physics;

import irgame.Game;
import irgame.object.Character;

/*
 *
 * @author Daniel Johansson
 */
public class Collision {
    public static void update(){
        if (Game.chaR.getYPos() + Game.chaR.getSIZE() * 2 > Game.HEIGHT - Game.ground[9].getSIZE()){
            Game.chaR.subYPos(Game.gravity);
            Game.chaR.setState("standing");
        }else{
            Game.chaR.setState("falling");
        }
    }
}
