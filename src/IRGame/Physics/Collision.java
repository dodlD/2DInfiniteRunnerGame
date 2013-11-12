package IRGame.Physics;
import IRGame.Game;
import IRGame.GamePlay;

/**
 *
 * @author Daniel Johansson
 */
public class Collision {
    public static void check(){
        if (GamePlay.yPos + GamePlay.chaR.getHeight() > Game.height - GamePlay.groundObj[9].getHeight() * GamePlay.rPos){
            GamePlay.yPos--;
        }
        //if (GamePlay.yPos + GamePlay.chaR.getHeight() > Game.height - GamePlay.groundImg.get(9).getHeight() * GamePlay.rPos){
        //    GamePlay.yPos--;
        //}
    }
}
