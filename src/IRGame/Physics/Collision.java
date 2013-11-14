package IRGame.Physics;
import IRGame.Game;
import IRGame.GamePlay;

/**
 *
 * @author Daniel Johansson
 */
public class Collision {
    public static void check(int delta){
        if (GamePlay.charYPos + GamePlay.chaR.getSprite().getHeight() > Game.height - GamePlay.groundObj[9].getHeight()){
            GamePlay.vertVel-= Gravity.gravity * delta;
        }
    }
}
