package irgame.physics;

import irgame.Game;
import irgame.object.Character;

/*
 *
 * @author Daniel Johansson
 */
public class Collision {
    public static void update(){
        if (Game.chaR.yPos + Game.chaR.SIZE * 2 > Game.HEIGHT - Game.ground[9].getSIZE()){
            Game.chaR.yPos -= Game.gravity;
            Game.chaR.state = "standing";
        }else{
            Game.chaR.state = "falling";
        }
    }
}
