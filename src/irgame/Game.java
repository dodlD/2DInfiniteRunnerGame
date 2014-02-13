package irgame;

import irgame.graphics.SpriteSheet;
import irgame.input.Keyboard;
import irgame.object.Character;
import irgame.object.Ground;
import irgame.physics.Collision;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

/**
 * @author Daniel Johansson
 */
public class Game extends Canvas implements Runnable {   
    private static final long serialVersionUID = 1L;
    
    private static final String GAMETITLE = "Wud";
    public static final int WIDTH = 640;
    public static final int HEIGHT = WIDTH * 9 / 16;
    
    private Thread thread;
    private JFrame frame;
    private Keyboard key;
    private boolean running = false;
    private int elapsedMilliSeconds;
    private int elapsedSeconds;
    private int elapsedMinutes;
    
    public static Ground[] ground = new Ground[WIDTH / 32 + 1];
    public static Ground[] groundFill = new Ground[(WIDTH / 32 + 1) * 2];
    int[] yCoordinates = new int[ground.length];
    public static Character chaR;
    
    public static int gravity = 4;
    private int jump = 0;
    
    private Font timeFont;
    
    public Game(){
        Dimension winSize = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(winSize);
        
        frame = new JFrame();
        
        key = new Keyboard();
        addKeyListener(key);
        
        timeFont = new Font("TimesRoman", Font.BOLD, 16);
    }
    
    public synchronized void start() {
        running = true;
        thread = new Thread(this, "Display");
        thread.start();
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
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        
        long startTimeMS = System.currentTimeMillis();
        long startTimeS = System.currentTimeMillis();
        long startTimeM = System.currentTimeMillis();
        
        
        int updates = 0;
        int frames = 0;
        
        
        for (int i = 0; i < ground.length; i++){
            if (i == 0){
                yCoordinates[i] = (int)(Math.random() * 3 + 1);
            }else if(i == 20){
                yCoordinates[i] = 2;
            }else{
                switch(yCoordinates[i-1]){
                    case 1:
                        yCoordinates[i] = (int)(Math.random() * 2 + 1);
                        break;
                    case 2:
                        yCoordinates[i] = (int)(Math.random() * 3 + 1);
                        break;
                    case 3:
                        yCoordinates[i] = (int)(Math.random() * 2 + 2);
                        break;
                }
            }
                    
            //ground[i] = new Ground(0, 0, i, yCoordinates[i]);
            groundFill[i] = new Ground(4, 0, i, yCoordinates[i]-1);
            groundFill[21+i] = new Ground(4, 0, i, yCoordinates[i]-2);
        }
        
        for (int i = 0; i < ground.length; i++){
            //Checks and decides what sprite should be used for each block
            if (i == 0){
                if (yCoordinates[i] <= yCoordinates[20]){
                     if (yCoordinates[i] <= yCoordinates[i+1]){
                        ground[i] = new Ground(0, 0, i, yCoordinates[i]);
                    }else {
                        ground[i] = new Ground(2, 0, i, yCoordinates[i]);
                    }  
                }else {
                    if (yCoordinates[i] > yCoordinates[i+1]){
                        ground[i] = new Ground(3, 0, i, yCoordinates[i]);
                    }else {
                        ground[i] = new Ground(1, 0, i, yCoordinates[i]);
                    }
                } 
            }else if (i == 20){
                if (yCoordinates[i] <= yCoordinates[i-1]){
                    if (yCoordinates[i] <= yCoordinates[0]){
                        ground[i] = new Ground(0, 0, i, yCoordinates[i]);
                    }else {
                        ground[i] = new Ground(2, 0, i, yCoordinates[i]);
                    } 
                }else {
                    if (yCoordinates[i] > yCoordinates[0]){
                        ground[i] = new Ground(3, 0, i, yCoordinates[i]);
                    }else {
                        ground[i] = new Ground(1, 0, i, yCoordinates[i]);
                    }
                }
            }else {
                if (yCoordinates[i] <= yCoordinates[i-1]){
                    if (yCoordinates[i] <= yCoordinates[i+1]){
                        ground[i] = new Ground(0, 0, i, yCoordinates[i]);
                    }else {
                        ground[i] = new Ground(2, 0, i, yCoordinates[i]);
                    }  
                }else {
                    if (yCoordinates[i] > yCoordinates[i+1]){
                        ground[i] = new Ground(3, 0, i, yCoordinates[i]);
                    }else {
                        ground[i] = new Ground(1, 0, i, yCoordinates[i]);
                    }
                }
            }
        }

        chaR = new irgame.object.Character();
        
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            
            long currTime = System.currentTimeMillis();
            long deltaTimeMS = currTime - startTimeMS;
            long deltaTimeS = currTime - startTimeS;
            long deltaTimeM = currTime - startTimeM;
            
            
            if (elapsedMilliSeconds < 100){
                elapsedMilliSeconds = (int) ((deltaTimeMS) / 10);
            }else {
                startTimeMS = currTime;
                elapsedMilliSeconds = 0;
            }
            
            if (elapsedSeconds < 60){
                elapsedSeconds = (int) ((deltaTimeS) / 1000);
            }else {
                startTimeS = currTime;
                elapsedSeconds = 0;
            }
            
            if (elapsedMinutes < 60){
                elapsedMinutes = (int) ((deltaTimeM) / 60000);
            }else {
                startTimeM = currTime;
                elapsedMinutes = 0;
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
        }
        while(!running){   
            if (key.p){
                running = true;
            }
            if (key.r){
                running = true;
            }
        }
    }
    
    public void update(){
        chaR.yPos += gravity;
        chaR.hitBox.setLocation(chaR.xPos, chaR.yPos);
        
        //Ground moving
        for (int i = 0; i < ground.length; i++){
            if (ground[i].xPos <= -ground[i].WIDTH){
                if (i == 0){
                    yCoordinates[i] = (int)(Math.random() * 3 + 1);
                }else if(i == 20){
                    yCoordinates[i] = 2;
                }else{
                    switch(yCoordinates[i-1]){
                        case 1:
                            yCoordinates[i] = (int)(Math.random() * 2 + 1);
                            break;
                        case 2:
                            yCoordinates[i] = (int)(Math.random() * 3 + 1);
                            break;
                        case 3:
                            yCoordinates[i] = (int)(Math.random() * 2 + 2);
                            break;
                    }
                }
                
                ground[i].yPos = Game.HEIGHT - ground[i].HEIGHT * yCoordinates[i];
                groundFill[i].yPos = Game.HEIGHT - groundFill[i].HEIGHT * (yCoordinates[i]-1);
                groundFill[21+i].yPos = Game.HEIGHT - groundFill[21+i].HEIGHT * (yCoordinates[i]-2);
                
                ground[i].xPos += getWidth() + ground[i].WIDTH;
                ground[i].hitBox.setLocation(ground[i].xPos, ground[i].yPos);

                groundFill[i].xPos += getWidth() + groundFill[i].WIDTH;
                groundFill[i].hitBox.setLocation(groundFill[i].xPos, groundFill[i].yPos);
                groundFill[21+i].xPos += getWidth() + groundFill[21+i].WIDTH;
                groundFill[21+i].hitBox.setLocation(groundFill[21+i].xPos, groundFill[21+i].yPos);
            }
            ground[i].xPos -= ground[0].HORIZ_VEL;
            ground[i].hitBox.setLocation(ground[i].xPos, ground[i].yPos);
            
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
        
        //What happens when the character is outside the grapichal area
        /*if (chaR.xPos <= -chaR.WIDTH){
            chaR.xPos += getWidth() + chaR.WIDTH;
            chaR.hitBox.setLocation(chaR.xPos, chaR.yPos);
        }else if (chaR.xPos + chaR.WIDTH >= getWidth() + chaR.WIDTH){
            chaR.xPos -= getWidth() + chaR.WIDTH;
            chaR.hitBox.setLocation(chaR.xPos, chaR.yPos);
        }*/
        if (chaR.xPos < 0){
            System.out.println("Game Over");
            //Spara tiden
            running = false;
        }else if (chaR.xPos + chaR.WIDTH >= getWidth()){
            chaR.xPos -= Game.chaR.HORIZ_VEL;;
            chaR.hitBox.setLocation(chaR.xPos, chaR.yPos);
        }
        
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
            running = false;
        }
        
        switch (chaR.state){
            case "walking":
                chaR.HEAD = chaR.sheet.img.getSubimage(0, 0, chaR.WIDTH, chaR.SPRITE_SIZE);
                /*if (i == 2){
                    chaR.BODY = chaR.sheet.img.getSubimage(0, 32, chaR.WIDTH, chaR.SPRITE_SIZE);
                }else {
                    chaR.BODY = chaR.sheet.img.getSubimage(i*32, 32, chaR.WIDTH, chaR.SPRITE_SIZE);    
                }*/
                chaR.BODY = chaR.sheet.img.getSubimage(0, 32, chaR.WIDTH, chaR.SPRITE_SIZE);
                break;
            case "jumping":
                chaR.HEAD = chaR.sheet.img.getSubimage(96, 0, chaR.WIDTH, chaR.SPRITE_SIZE);
                chaR.BODY = chaR.sheet.img.getSubimage(96, 32, chaR.WIDTH, chaR.SPRITE_SIZE);
                break;
            case "falling":
                chaR.HEAD = chaR.sheet.img.getSubimage(96, 0, chaR.WIDTH, chaR.SPRITE_SIZE);
                chaR.BODY = chaR.sheet.img.getSubimage(96, 32, chaR.WIDTH, chaR.SPRITE_SIZE);
                break;
        }
        
        Collision.update();
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
        g.drawImage(chaR.BODY, chaR.xPos, chaR.yPos + chaR.SPRITE_SIZE, null);
        g.drawImage(chaR.HEAD, chaR.xPos, chaR.yPos, null);
        //g.setColor(Color.red);                                      //Hitbox
        //g.drawRect(chaR.xPos, chaR.yPos, chaR.WIDTH, chaR.HEIGHT);  //
        
        //Time rendering
        g.setColor(Color.WHITE);
        g.setFont(timeFont);
        g.drawString(Integer.toString(elapsedMinutes) + " : " + Integer.toString(elapsedSeconds) + " : " + Integer.toString(elapsedMilliSeconds), 550, 30);
        
        //Text rendering
        g.drawString("p - pause", 290, 30);
        
        if(!running){
            g.drawImage(new SpriteSheet("/irgame/res/textures/GameOver_img.png").img.getSubimage(0, 0, getWidth(), getHeight()), 0, 0, null);
            System.out.println("asd");
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
}
