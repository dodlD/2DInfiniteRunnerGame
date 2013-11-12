package IRGame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 * @author Daniel Johansson
 */
public class Game extends StateBasedGame{   
    
    public static final String gameName = "Plud";
    public static int width = 640;
    public static int height = width * 9 / 16;
    //public static final int gameMenu = 0;
    public static final int gamePlay = 1;
    //public static final int gameOver = 2;
    
    public Game(String gameName){
        super(gameName);
        //this.addState(new GameMenu(gameMenu));
        this.addState(new GamePlay(gamePlay));
        this.enterState(gamePlay);
        //this.addState(new GameOver(gameOver));
    }
    
    public void initStatesList(GameContainer gc) throws SlickException{
        //this.getState(gameMenu).init(gc, this);
        //this.getState(gamePlay).init(gc, this);
        //this.getState(gameOver).init(gc, this);
        //this.enterState(gamePlay);
    }
    
    public static void main(String[] args){
        AppGameContainer appgc;
        try{
            appgc = new AppGameContainer(new Game(gameName));
            appgc.setDisplayMode(width, height, false);
            appgc.start();
        }catch(SlickException e){
           
        }
    } 
}
