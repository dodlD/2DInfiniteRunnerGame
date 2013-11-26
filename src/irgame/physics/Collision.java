package irgame.physics;

import irgame.Game;
import irgame.object.Character;

/*
 *
 * @author Daniel Johansson
 */
public class Collision {
    public static void update(){
        if (Game.chaR.getYPos() + Game.chaR.getSIZE() * 2 > Game.HEIGHT - Game.ground.SIZE){
            Game.chaR.delYPos(Game.gravity);
        }
    }
}
