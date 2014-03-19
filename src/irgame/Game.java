package irgame;

import irgame.input.Keyboard;
import irgame.object.Character;
import irgame.object.Ground;
import irgame.object.Obstacle;
import irgame.sound.Sound;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;

/**
 * @author Daniel Johansson
 */
public class Game extends Canvas implements Runnable {   
    private static final long serialVersionUID = 1L;
    
    private static final ImageIcon ICON = new ImageIcon(Game.class.getResource("/irgame/res/textures/icon.png"));
    private static final String GAMETITLE = "Wüüd teh Würm";
    public static final int WIDTH = 640;
    public static final int HEIGHT = WIDTH * 9 / 16;    //16:9 aspect ratio.
    
    private Thread thread;
    private JFrame frame;
    private Keyboard key;
    private boolean running = false;
    
    //Gets the "My Documents" path
    private final FileSystemView FSV = new JFileChooser().getFileSystemView();
    
    //The path of the game directory and the highscore.txt file, where the highscore will be stored.
    private final File GAME_DIR = new File (FSV.getDefaultDirectory() + "\\" + GAMETITLE);
    private final File HIGH_SCORE_DIR = new File(GAME_DIR + "\\highscore.txt");
    
    private FileWriter fw;
    
    private final int GRAVITY = 4;
    private int jumpCount = 0;
    private int level = 1;
    private boolean newHighScore = false;
    private String highScore;
    
    //Objects
    public static final Ground[] GROUND = new Ground[WIDTH / Ground.WIDTH + 1]; //The ground the charater is running on.
    private static final Ground[] GROUND_FILL = new Ground[(WIDTH / Ground.WIDTH + 1) * 2];  //Used to fill the space below the actual ground.
    private ArrayList<Obstacle> obstacle;
    private static Character chaR;
    
    //Sound
    private Sound music;
    private Sound jump;
    private Sound die;
    private Sound dead;
    
    private BufferedImage bG, gameOverImg;
    
    private String elapsedMilliSeconds = "1";
    private String elapsedSeconds = "1";
    private String elapsedMinutes = "1";
    
    private long lastTime = System.nanoTime();
    private long timer = System.currentTimeMillis();
    private final double NS = 1000000000.0 / 60.0;
    private double delta = 0;
    private long startTimeMS = System.currentTimeMillis();
    private long startTimeS = System.currentTimeMillis();
    private long startTimeM = System.currentTimeMillis();

    private int updates = 0;
    private int frames = 0;
    
    public Game(){
        //Sets the dimension of the window.
        Dimension winSize = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(winSize);  
        
        frame = new JFrame();
        
        key = new Keyboard();
        addKeyListener(key);
    }
    
    public synchronized void start() {  //Starts the game.
        running = true;
        thread = new Thread(this, "Display");
        thread.start();
        if (!GAME_DIR.exists()){ //Creates a directory for the game at the users "Documents"-folder if it doesn't exists.
            GAME_DIR.mkdirs();
        }
        
        try {
            if(!HIGH_SCORE_DIR.exists()){ //Creates a highscore.txt-file in the directory of the game if it doesn't exists.
                fw = new FileWriter(HIGH_SCORE_DIR);
            }
            
            bG = ImageIO.read(getClass().getResource("/irgame/res/textures/bg_img.png"));   //Loads the background image for the game.
            gameOverImg = ImageIO.read(getClass().getResource("/irgame/res/textures/game_over_img.png"));   //Loads the image shown when the game is over.
            
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        jump = new Sound("/irgame/res/sounds/jump.wav");    //Loads the jump sound effect.
        die = new Sound("/irgame/res/sounds/die.wav");  //Loads the die sound effect.
        
    }
    
    public synchronized void stop() {   //Stops the game.
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void run() {
        initialize();   //Explained further down.
        
        while(running) {    //The game loop.
            long now = System.nanoTime();
            delta += (now - lastTime) / NS;
            lastTime = now;
            
            
            long currTime = System.currentTimeMillis();
            long deltaTimeMS = currTime - startTimeMS;
            long deltaTimeS = currTime - startTimeS;
            long deltaTimeM = currTime - startTimeM;
            
            //Formats the elapsed milliseconds.
            if (Integer.parseInt(elapsedMilliSeconds) < 100){
                elapsedMilliSeconds = Integer.toString((int) ((deltaTimeMS) / 10));
                if (Integer.parseInt(elapsedMilliSeconds) < 10){
                    elapsedMilliSeconds = "0" + Integer.toString((int) ((deltaTimeMS) / 10)); 
                }
            }else {
                startTimeMS = currTime;
                elapsedMilliSeconds = "1";
            }          
            
            //Formats the elapsed seconds.
            if (Integer.parseInt(elapsedSeconds) < 60){
                elapsedSeconds = Integer.toString((int) ((deltaTimeS) / 1000));
                if (Integer.parseInt(elapsedSeconds) < 10){
                    elapsedSeconds = "0" + Integer.toString((int) ((deltaTimeS) / 1000)); 
                }
            }else {
                startTimeS = currTime;
                elapsedSeconds = "1";
            }
            
            //Formats the elapsed minutes.
            if (Integer.parseInt(elapsedMinutes) < 60){
                elapsedMinutes = Integer.toString((int) ((deltaTimeM) / 60000));
                if (Integer.parseInt(elapsedMinutes) < 10){
                    elapsedMinutes = "0" + Integer.toString((int) ((deltaTimeM) / 60000)); 
                }
            }else {
                startTimeM = currTime;
                elapsedMinutes = "1";
            }
            
            while (delta >= 1){
                update();   //Explained further down.
                updates++;
                render();   //Explained further down.
                frames++;
                delta--;
            }
            
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                updates = 0;
                frames = 0;
            }
            
            while(!running){    //If the game is over this loop will start.
                key.update();   //Explained in the Keyboard class.
                if (key.r){ //If the player presses "r" the game will restart.
                    dead.stop();
                    newHighScore = false;
                    level = 1;
                    running = true;
                    initialize();   //Explained further down.
                }
                
                render();
            }
        }
        
    }
    
    public void update(){   //Handles the locical parts of the game.
        key.update();   //Explained in the Keyboard class
        
        //Takes care of the jumping process.
        if (key.up || chaR.state.equals("jumping")){                            //
            if (chaR.state.equals("walking") || chaR.state.equals("jumping")){  //Starts or continues the jump process depending on if the character is walking or jumping.
                if (jumpCount < chaR.JUMP_HEIGHT / chaR.JUMP_FORCE){            //
                    if(jumpCount == 0){ 
                        jump.play();
                    }
                    chaR.yPos -= chaR.JUMP_FORCE + GRAVITY;
                    chaR.headHitBox.setLocation(chaR.xPos + 11, chaR.yPos);
                    chaR.bodyHitBox.setLocation(chaR.xPos + 12, chaR.yPos + 45);
                    chaR.state = "jumping";
                    jumpCount++;
                }else { //Stops the jump process when the character has reached its jump height.
                    jumpCount = 0;
                    chaR.headHitBox.setLocation(chaR.xPos + 11, chaR.yPos);
                    chaR.bodyHitBox.setLocation(chaR.xPos + 12, chaR.yPos + 45);
                    chaR.state = "falling";
                }
            }
        }
        
        //Takes care of the characters movement back and forth.
        if (key.left){
            chaR.xPos -= GROUND[0].HORIZ_VEL;
            chaR.HORIZ_VEL = -2;
            chaR.headHitBox.setLocation(chaR.xPos + 11, chaR.yPos);
            chaR.bodyHitBox.setLocation(chaR.xPos + 11, chaR.yPos + 45);
        }else if (key.right){
            chaR.xPos += GROUND[0].HORIZ_VEL; 
            chaR.HORIZ_VEL = 2;
            chaR.headHitBox.setLocation(chaR.xPos + 11, chaR.yPos);
            chaR.bodyHitBox.setLocation(chaR.xPos + 11, chaR.yPos + 45);
        }else{
            chaR.HORIZ_VEL = 0;
        }
        
        //Decides what will happen when the character is outside the grapichal area or dead.
        if (chaR.xPos < 0 || chaR.dead(obstacle)){
            die.play();
            music.stop();
            dead.loop();
            
            int newS = Integer.parseInt(elapsedMinutes +""+ elapsedSeconds +""+ elapsedMilliSeconds);  //The new score.
            String content;
            int prevHS = 0;
            
            try {
                content = new String(Files.readAllBytes(Paths.get(HIGH_SCORE_DIR.toString()))); //Reads the higscore.txt file.
                if (!content.isEmpty()){
                    prevHS = Integer.parseInt(content); //Converts the content of the file to an int.
                }
            } catch (IOException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (newS > prevHS){
                newHighScore = true;
                
                //Writes the new high score to the highscore.txt file.
                try {
                    fw = new FileWriter(HIGH_SCORE_DIR);
                    fw.write(Integer.toString(newS));
                    fw.close();
                }catch (IOException iox){
                    iox.printStackTrace();
                }
            }
            
            //Formats the time that will be shown for the user.
            switch(Integer.toString(prevHS).length()){
                case 0:
                    highScore = "00 : 00 : 00";
                    break;
                case 1:
                    highScore = "00 : 00 : 0" + Integer.toString(prevHS);
                    break;
                case 2:
                    highScore = "00 : 00 :" + Integer.toString(prevHS);
                    break;
                case 3:
                    highScore = "00 : 0" + Integer.toString(prevHS).substring(0, 1) + " : " + Integer.toString(prevHS).substring(1, 3);
                    break;
                case 4:
                    highScore = "00 : " + Integer.toString(prevHS).substring(0, 2) + " : " + Integer.toString(prevHS).substring(2, 4);
                    break;
                case 5:
                    highScore = "0" + Integer.toString(prevHS).substring(0, 1) + " : " + Integer.toString(prevHS).substring(1, 3) + " : " + Integer.toString(prevHS).substring(3, 5);
                    break;
                case 6:
                    highScore = Integer.toString(prevHS).substring(0, 2) + " : " + Integer.toString(prevHS).substring(2, 4) + " : " + Integer.toString(prevHS).substring(4, 6);
                    break;
            }
            
            running = false;    //Stops the game from running.
        }else {
            if (chaR.outOfArea()){  //Prevents the player from running out of the windowarea.
                chaR.xPos -= Game.chaR.HORIZ_VEL;;
            }
            
            chaR.yPos += GRAVITY;
            chaR.headHitBox.setLocation(chaR.xPos + 11, chaR.yPos);
            chaR.bodyHitBox.setLocation(chaR.xPos + 11, chaR.yPos + 45);
            
            
            //The movement of the ground and creation of obstacles.
            for (int i = 0; i < GROUND.length; i++){
                if(Ground.newLvl(Integer.parseInt(elapsedSeconds), level)){ //Increases the level of the game.
                    GROUND[i].HORIZ_VEL++;
                    GROUND_FILL[i].HORIZ_VEL++;
                    GROUND_FILL[21+i].HORIZ_VEL++;
                    if (i == 20){
                        level++;
                    }
                }

                if (Ground.outOfArea(GROUND, i)){
                    Ground.rePosY(i);

                    //The ground the character is running on.
                    GROUND[i].yPos = Game.HEIGHT - GROUND[i].HEIGHT * Ground.yCoordinates[i];
                    GROUND[i].xPos += getWidth() + GROUND[i].WIDTH;
                    GROUND[i].hitBox.setLocation(GROUND[i].xPos, GROUND[i].yPos);

                    //The filling ground .
                    GROUND_FILL[i].yPos = Game.HEIGHT - GROUND_FILL[i].HEIGHT * (Ground.yCoordinates[i]-1);
                    GROUND_FILL[i].xPos += getWidth() + GROUND_FILL[i].WIDTH;
                    GROUND_FILL[i].hitBox.setLocation(GROUND_FILL[i].xPos, GROUND_FILL[i].yPos);
                    GROUND_FILL[21+i].yPos = Game.HEIGHT - GROUND_FILL[21+i].HEIGHT * (Ground.yCoordinates[i]-2);
                    GROUND_FILL[21+i].xPos += getWidth() + GROUND_FILL[21+i].WIDTH;
                    GROUND_FILL[21+i].hitBox.setLocation(GROUND_FILL[21+i].xPos, GROUND_FILL[21+i].yPos);
                    
                    //The creation of new obstacles.
                    int r = (int)(Math.random() * 10 + 1);
                    if (Obstacle.newObstacle(obstacle, GROUND, level, r, i)){
                        obstacle.add(new Obstacle(WIDTH + (GROUND[i].WIDTH - obstacle.get(obstacle.size()-1).WIDTH)/2, GROUND[i].yPos - 1));
                    }
                    
                    //Removes invisible obstacles.
                    for (int x = 0; x < obstacle.size(); x++){
                        if (Obstacle.delObstacle(obstacle, GROUND, x)){
                            obstacle.remove(x);
                        }
                    }
                }

                //Moves the ground the character is running on.
                GROUND[i].xPos -= GROUND[i].HORIZ_VEL;
                GROUND[i].hitBox.setLocation(GROUND[i].xPos, GROUND[i].yPos);

                //Moves the filling ground.
                GROUND_FILL[i].xPos -= GROUND_FILL[i].HORIZ_VEL;
                GROUND_FILL[i].hitBox.setLocation(GROUND_FILL[i].xPos, GROUND_FILL[i].yPos);
                GROUND_FILL[21+i].xPos -= GROUND_FILL[21+i].HORIZ_VEL;
                GROUND_FILL[21+i].hitBox.setLocation(GROUND_FILL[21+i].xPos, GROUND_FILL[21+i].yPos);
                
                
            }
            
            //The movement of the obstacles.
            for (int i = 0; i < obstacle.size(); i++){
                obstacle.get(i).xPos -= GROUND[0].HORIZ_VEL;
                obstacle.get(i).hitBox.setLocation(obstacle.get(i).xPos, obstacle.get(i).yPos);    
            }
            
            Ground.setSprite(GROUND);   //Explained in the Ground class.
            
            chaR.collision(GROUND, GRAVITY);    //Explained in the Character class.
            chaR.setSprite(updates);    //Explained in the Character class.
        }
    }
    
    public void render(){   //Handles the rendering of objects and images.
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        
        g.drawImage(bG, 0, 0, null);    //Draws the background image.

        //The rendering of the ground.
        Ground.render(g, GROUND, GROUND_FILL);   //Explained in the Ground class.
        
        
        //The rendering of obstacles.
        Obstacle.render(g, obstacle);   //Explained in the Obstacle class.
        
        
        //The rendering of the character.
        chaR.render(g); //Explained in the Character class.
        
        //The rendering of the elapsed time.
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(elapsedMinutes + " : " + elapsedSeconds + " : " + elapsedMilliSeconds, 550, 30);
        
        //Renders the game over image and the score.
        if(!running){
            g.setColor(Color.BLACK);
            g.drawImage(gameOverImg, 0, 0, null);
            g.drawString("Scüre", 340, 40);
            g.drawString(elapsedMinutes + " : " + elapsedSeconds + " : " + elapsedMilliSeconds, 302, 60);
            
            //Renders the new high score if there is one, otherwise it renders the old high score.
            if (newHighScore){
                g.setColor(Color.RED);
                g.drawString("New High Scüre", 262, 90);
                g.drawString(elapsedMinutes + " : " + elapsedSeconds + " : " + elapsedMilliSeconds, 303, 110);
            }else {
                g.drawString("High Scüre", 300, 90);
                g.drawString(highScore, 303, 110);
            } 
        }
        
        g.dispose();
        bs.show();
    }
    
    public static void main(String[] args) {
        Game game = new Game();
        game.frame.setResizable(false);
        game.frame.setIconImage(ICON.getImage());
        game.frame.setTitle(GAMETITLE);
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);
        
        game.start();
    }
    
    public void initialize(){   //
        lastTime = System.nanoTime();
        timer = System.currentTimeMillis();
        delta = 0;
        
        startTimeMS = System.currentTimeMillis();
        startTimeS = System.currentTimeMillis();
        startTimeM = System.currentTimeMillis();   
        
        updates = 0;
        frames = 0;
        
        //Creates and sets the start coordinates for the ground.
        for (int i = 0; i < GROUND.length; i++){
            Ground.yCoordinates[i] = 1;
            GROUND[i] = new Ground(0, 0, i, Ground.yCoordinates[i]);
            GROUND_FILL[i] = new Ground(4, 0, i, Ground.yCoordinates[i]-1);
            GROUND_FILL[21+i] = new Ground(4, 0, i, Ground.yCoordinates[i]-2);
        }
        
        //Creates the first obstacle.
        int r = (int)((Math.random() * (GROUND.length/3 - 4))  + (2*GROUND.length/3 + 4));
        obstacle = new ArrayList<Obstacle>();
        obstacle.add(new Obstacle(GROUND[r].xPos + (GROUND[r].WIDTH - Obstacle.WIDTH)/2, GROUND[r].yPos));
        
        //Creates the character.
        chaR = new irgame.object.Character();
        
        //Loads the sound files.
        music = new Sound("/irgame/res/sounds/music.wav");
        dead = new Sound("/irgame/res/sounds/dead.wav");
        
        music.loop();
    }
}
