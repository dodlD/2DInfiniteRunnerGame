package irgame;

import irgame.graphics.SpriteSheet;
import irgame.input.Keyboard;
import irgame.object.Character;
import irgame.object.Ground;
import irgame.object.Obstacle;
import irgame.physics.Collision;
import irgame.sound.Sound;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;

/**
 * @author Daniel Johansson
 */
public class Game extends Canvas implements Runnable {   
    private static final long serialVersionUID = 1L;
    
    private static final String GAMETITLE = "Wüüd teh Gaem";
    public static final int WIDTH = 640;
    public static final int HEIGHT = WIDTH * 9 / 16;
    
    private Thread thread;
    private JFrame frame;
    private Keyboard key;
    private boolean running = false;
    private boolean paused = false; 
    private String elapsedMilliSeconds = "1";
    private String elapsedSeconds = "1";
    private String elapsedMinutes = "1";
    
    public static Ground[] ground = new Ground[WIDTH / 32 + 1];
    public static Ground[] groundFill = new Ground[(WIDTH / 32 + 1) * 2];
    public static Obstacle[] obstacle = new Obstacle[5];
    public static int yCoordinates[] = new int[ground.length];
    public static Character chaR;
    public static Sound sound = new Sound("/irgame/res/sounds/kludd.wav");
    private int anim = 0;
    
    public static int gravity = 4;
    private int jump = 0;
    
    //Gets the "My Documents" path
    private final JFileChooser jfc = new JFileChooser();
    private final FileSystemView fsv = jfc.getFileSystemView();
    private final File gameDir = new File (fsv.getDefaultDirectory() + "\\" + GAMETITLE);
    private final File highScoreDir = new File(gameDir + "\\highscore.txt");
    private FileWriter fw;
    
    private boolean newHighScore = false;
    private String highScore;
    
    //Test
    long lastTime = System.nanoTime();
    long timer = System.currentTimeMillis();
    final double ns = 1000000000.0 / 60.0;
    double delta = 0;

    long startTimeMS = System.currentTimeMillis();
    long startTimeS = System.currentTimeMillis();
    long startTimeM = System.currentTimeMillis();


    int updates = 0;
    int frames = 0;
    
    public Game(){
        Dimension winSize = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(winSize);
        
        frame = new JFrame();
        
        key = new Keyboard();
        addKeyListener(key);
    }
    
    public synchronized void start() {
        running = true;
        thread = new Thread(this, "Display");
        thread.start();
        if (!gameDir.exists()){
            gameDir.mkdirs();
        }
        if(!highScoreDir.exists()){
            try {
            fw = new FileWriter(highScoreDir);
            } catch (IOException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void run() {
        initialize();
        
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            
            long currTime = System.currentTimeMillis();
            long deltaTimeMS = currTime - startTimeMS;
            long deltaTimeS = currTime - startTimeS;
            long deltaTimeM = currTime - startTimeM;
            
            if (Integer.parseInt(elapsedMilliSeconds) < 100){
                elapsedMilliSeconds = Integer.toString((int) ((deltaTimeMS) / 10));
                if (Integer.parseInt(elapsedMilliSeconds) < 10){
                    elapsedMilliSeconds = "0" + Integer.toString((int) ((deltaTimeMS) / 10)); 
                }
            }else {
                startTimeMS = currTime;
                elapsedMilliSeconds = "1";
            }          
            
            if (Integer.parseInt(elapsedSeconds) < 60){
                elapsedSeconds = Integer.toString((int) ((deltaTimeS) / 1000));
                if (Integer.parseInt(elapsedSeconds) < 10){
                    elapsedSeconds = "0" + Integer.toString((int) ((deltaTimeS) / 1000)); 
                }
            }else {
                startTimeS = currTime;
                elapsedSeconds = "1";
            }
            
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
                update();
                updates++;
                delta--;
            }
            
            render();
            frames++;
            
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frame.setTitle(GAMETITLE + " | " + updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
            }
            
            while(!running){
                key.update();
                /*if (paused && key.u){
                    running = true;
                    paused = false;
                }*/
                if (/*!paused && */key.r){
                    newHighScore = false;
                    running = true;
                    initialize();
                }
            }
        }
        
    }
    
    public void update(){   
        key.update();
        if (key.up || chaR.state.equals("jumping")){ //If the up-key is pressed or the character is already moving upwards (jumping),
            if (chaR.state.equals("walking") || chaR.state.equals("jumping")){ //If the character is standing or jumping
                if (jump < chaR.JUMP_HEIGHT / chaR.JUMP_FORCE){ //and if the current height is less than above the ground is less than the height you can jump, the current height will increase.
                    chaR.yPos -= chaR.JUMP_FORCE + gravity;
                    chaR.hitBox.setLocation(chaR.xPos, chaR.yPos);
                    chaR.state = "jumping";
                    jump++;
                }else {
                    jump = 0;
                    chaR.hitBox.setLocation(chaR.xPos, chaR.yPos);
                    chaR.state = "falling";
                }
            }
        }
        
        //if (key.down){}
        if (key.left){
            chaR.xPos -= ground[0].HORIZ_VEL;
            chaR.HORIZ_VEL = -2;
            chaR.hitBox.setLocation(chaR.xPos, chaR.yPos);
        }else if (key.right){
            chaR.xPos += ground[0].HORIZ_VEL; 
            chaR.HORIZ_VEL = 2;
            chaR.hitBox.setLocation(chaR.xPos, chaR.yPos);
        }else{
            chaR.HORIZ_VEL = 0;
        }
        
        if (key.p){
            paused = true;
            //running = false;
        }
        
        //What happens when the character is outside the grapichal area
        if (chaR.xPos < 0 || Game.chaR.Intersect(Game.obstacle[i].hitBox)){
            sound.stop();
            int newHS = Integer.parseInt(elapsedMinutes +""+ elapsedSeconds +""+ elapsedMilliSeconds);
            String content;
            int prevHS = 0;
            try {
                content = new String(Files.readAllBytes(Paths.get(highScoreDir.toString()))); //Reads the higscore.txt-file
                if (!content.isEmpty()){
                    prevHS = Integer.parseInt(content); //converts the content of the file to an int
                }
            } catch (IOException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (newHS > prevHS){ //Compares the new high score (newHS) with the previous one (prevHS) and if the new high score is a higher value than the previous one
                newHighScore = true;
                try {
                    String score = Integer.toString(newHS); //the new high score (newHS) gets converted to a String

                    fw = new FileWriter(highScoreDir);
                    fw.write(score);
                    fw.close();
                }catch (IOException iox){
                    //do stuff with exception
                    iox.printStackTrace();
                }
            }
            
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
            running = false;
        }else if (paused){
            if (key.u){
                paused = false;
            }
        }else {
            if (chaR.xPos + chaR.WIDTH >= getWidth()){
                chaR.xPos -= Game.chaR.HORIZ_VEL;;
            }
            
            chaR.yPos += gravity;
            chaR.hitBox.setLocation(chaR.xPos, chaR.yPos);
            
            //Ground moving
            for (int i = 0; i < ground.length; i++){
                switch (Integer.parseInt(elapsedMinutes)){
                    case 1:
                        ground[i].HORIZ_VEL = 3;
                        groundFill[i].HORIZ_VEL = 3;
                        groundFill[21+i].HORIZ_VEL = 3;
                        break;
                    case 2:
                        ground[i].HORIZ_VEL = 4;
                        groundFill[i].HORIZ_VEL = 4;
                        groundFill[21+i].HORIZ_VEL = 4;
                        break;
                    case 3:
                        ground[i].HORIZ_VEL = 5;
                        groundFill[i].HORIZ_VEL = 5;
                        groundFill[21+i].HORIZ_VEL = 5;
                        break;
                    case 4:
                        ground[i].HORIZ_VEL = 6;
                        groundFill[i].HORIZ_VEL = 6;
                        groundFill[21+i].HORIZ_VEL = 6;
                        break;
                }

                if (ground[i].xPos <= -ground[i].WIDTH){
                    if (i == 0){
                        groundSetYPos(19, 20, i);
                    }else if (i == 1){
                        groundSetYPos(20, i-1, i);
                    }else{
                        groundSetYPos(i-2, i-1, i);
                    }

                    //The ground the character is running on
                    ground[i].yPos = Game.HEIGHT - ground[i].HEIGHT * yCoordinates[i];
                    ground[i].xPos += getWidth() + ground[i].WIDTH;
                    ground[i].hitBox.setLocation(ground[i].xPos, ground[i].yPos);

                    //The filling ground 
                    groundFill[i].yPos = Game.HEIGHT - groundFill[i].HEIGHT * (yCoordinates[i]-1);
                    groundFill[i].xPos += getWidth() + groundFill[i].WIDTH;
                    groundFill[i].hitBox.setLocation(groundFill[i].xPos, groundFill[i].yPos);
                    groundFill[21+i].yPos = Game.HEIGHT - groundFill[21+i].HEIGHT * (yCoordinates[i]-2);
                    groundFill[21+i].xPos += getWidth() + groundFill[21+i].WIDTH;
                    groundFill[21+i].hitBox.setLocation(groundFill[21+i].xPos, groundFill[21+i].yPos);
                }

                //The ground the character is running on
                ground[i].xPos -= ground[0].HORIZ_VEL;
                ground[i].hitBox.setLocation(ground[i].xPos, ground[i].yPos);

                //The filling ground 
                groundFill[i].xPos -= groundFill[i].HORIZ_VEL;
                groundFill[i].hitBox.setLocation(groundFill[i].xPos, groundFill[i].yPos);
                groundFill[21+i].xPos -= groundFill[21+i].HORIZ_VEL;
                groundFill[21+i].hitBox.setLocation(groundFill[21+i].xPos, groundFill[21+i].yPos);
            }

            for (int i = 0; i < ground.length; i++){
                if (i == 0){
                    if (yCoordinates[i] <= yCoordinates[20]){
                         if (yCoordinates[i] <= yCoordinates[i+1]){
                            ground[i].spriteXPos = 0 * ground[i].WIDTH;
                        }else {
                            ground[i].spriteXPos = 2 * ground[i].WIDTH;
                        }  
                    }else {
                        if (yCoordinates[i] > yCoordinates[i+1]){
                            ground[i].spriteXPos = 3 * ground[i].WIDTH;
                        }else {
                            ground[i].spriteXPos = 1 * ground[i].WIDTH;
                        }
                    } 
                }else if(i == 20){
                    if (yCoordinates[i] <= yCoordinates[i-1]){
                        if (yCoordinates[i] <= yCoordinates[0]){
                            ground[i].spriteXPos = 0 * ground[i].WIDTH;
                        }else {
                            ground[i].spriteXPos = 2 * ground[i].WIDTH;
                        } 
                    }else {
                        if (yCoordinates[i] > yCoordinates[0]){
                            ground[i].spriteXPos = 3 * ground[i].WIDTH;
                        }else {
                            ground[i].spriteXPos = 1 * ground[i].WIDTH;
                        }
                    }
                }else{
                    if (yCoordinates[i] <= yCoordinates[i-1]){
                        if (yCoordinates[i] <= yCoordinates[i+1]){
                            ground[i].spriteXPos = 0 * ground[i].WIDTH;
                        }else {
                            ground[i].spriteXPos = 2 * ground[i].WIDTH;
                        }  
                    }else {
                        if (yCoordinates[i] > yCoordinates[i+1]){
                            ground[i].spriteXPos = 3 * ground[i].WIDTH;
                        }else {
                            ground[i].spriteXPos = 1 * ground[i].WIDTH;
                        }
                    }
                }
            }
            switch (chaR.state){
                case "walking":
                    if (updates == 5 || updates == 25 || updates == 45){
                        chaR.BODY = chaR.sheet.img.getSubimage(0*chaR.WIDTH, 0, chaR.WIDTH, chaR.HEIGHT);
                    }else if (updates == 10 || updates == 30 || updates == 50){
                        chaR.BODY = chaR.sheet.img.getSubimage(1*chaR.WIDTH, 0, chaR.WIDTH, chaR.HEIGHT);
                    }else if (updates == 15 || updates == 35 || updates == 55){
                        chaR.BODY = chaR.sheet.img.getSubimage(0*chaR.WIDTH, 0, chaR.WIDTH, chaR.HEIGHT);
                    }else if (updates == 20 || updates == 40 || updates == 60){
                        chaR.BODY = chaR.sheet.img.getSubimage(2*chaR.WIDTH, 0, chaR.WIDTH, chaR.HEIGHT);
                    }
                    break;
                case "jumping":
                    chaR.BODY = chaR.sheet.img.getSubimage(3*chaR.WIDTH, 0, chaR.WIDTH, chaR.HEIGHT);
                    break;
                case "falling":
                    chaR.BODY = chaR.sheet.img.getSubimage(3*chaR.WIDTH, 0, chaR.WIDTH, chaR.HEIGHT);
                    break;
            }
            
            Collision.update();
        }
    }
    
    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        //Ground rendering
        for (int i = 0; i < ground.length; i++){
            g.drawImage(ground[i].sprite[ground[i].spriteXPos/32], ground[i].xPos, ground[i].yPos, null);
            g.drawImage(groundFill[i].sprite[4], groundFill[i].xPos, groundFill[i].yPos, null);
            g.drawImage(groundFill[21+i].sprite[4], groundFill[21+i].xPos, groundFill[21+i].yPos, null);
            //g.setColor(Color.red);                                       //
            //g.drawRect(ground[i].hitBox.x, ground[i].hitBox.y,           //Hitbox
            //        ground[i].hitBox.width, ground[i].hitBox.height);    //
            //g.drawString(""+(i+1), ground[i].xPos+10, ground[i].yPos+20);//Number
        }
        
        //Character rendering
        g.drawImage(chaR.BODY, chaR.xPos, chaR.yPos, null);
        //g.setColor(Color.red);                                      //Hitbox
        //g.drawRect(chaR.hitBox.x, chaR.hitBox.y, chaR.hitBox.width, chaR.hitBox.height);  //
        
        //Time rendering
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(elapsedMinutes + " : " + elapsedSeconds + " : " + elapsedMilliSeconds, 550, 30);
        
        //Text rendering
        g.drawString("p - pause", 286, 30);
        
        if (paused){
            g.drawImage(new SpriteSheet("/irgame/res/textures/paused_img.png").img.getSubimage(0, 0, getWidth(), getHeight()), 0, 0, null);
        }else if(!running){
            g.setColor(Color.BLACK);
            g.drawImage(new SpriteSheet("/irgame/res/textures/gameover_img.png").img.getSubimage(0, 0, getWidth(), getHeight()), 0, 0, null);
            g.drawString("Scüre", 340, 40);
            g.drawString(elapsedMinutes + " : " + elapsedSeconds + " : " + elapsedMilliSeconds, 302, 60);
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
        game.frame.setTitle(GAMETITLE);
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);
        
        game.start();
    }
    
    public void initialize(){
        lastTime = System.nanoTime();
        timer = System.currentTimeMillis();
        delta = 0;
        
        startTimeMS = System.currentTimeMillis();
        startTimeS = System.currentTimeMillis();
        startTimeM = System.currentTimeMillis();   
        
        updates = 0;
        frames = 0;
        
        for (int i = 0; i < ground.length; i++){
            yCoordinates[i] = 1;
            ground[i] = new Ground(0, 0, i, yCoordinates[i]);
            groundFill[i] = new Ground(4, 0, i, yCoordinates[i]-1);
            groundFill[21+i] = new Ground(4, 0, i, yCoordinates[i]-2);
        }
        
        chaR = new irgame.object.Character();
        sound.loop();
    }
    
    public void groundSetYPos(int prevG2, int prevG, int curG){
        switch(yCoordinates[prevG]){
            case 1:
                if(yCoordinates[prevG2] == 2){
                    yCoordinates[curG] = 1; 
                }else {
                    yCoordinates[curG] = (int)(Math.random() * 2 + 1);
                }
                
                break;
                
            case 2:
                if(yCoordinates[prevG2] != 2){
                    yCoordinates[curG] = 2; 
                }else {
                    yCoordinates[curG] = (int)(Math.random() * 3 + 1);
                }
                
                break;
                
            case 3:
                if(yCoordinates[prevG2] != 3){
                    yCoordinates[curG] = 3;   
                }else {
                    yCoordinates[curG] = (int)(Math.random() * 2 + 2);
                }
                
                break;
        }
    }
}
