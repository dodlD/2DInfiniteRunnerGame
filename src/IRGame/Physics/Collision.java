package IRGame.Physics;
import IRGame.Game;
import IRGame.GamePlay;

/**
 *
 * @author Daniel Johansson
 */
public class Collision {
    public static void check(int delta){
        if (GamePlay.chaR.getYPos() + GamePlay.chaR.getSprite().getHeight() > Game.height - GamePlay.groundObj[5].getHeight()){
            GamePlay.chaR.setYPos(-1 * delta);
        }
    }
}
