package irgame;

import irgame.input.Keyboard;
import irgame.object.Character;
import irgame.object.Ground;
import irgame.physics.Collision;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

/**
 * @author Daniel Johansson
 */
public class Game extends Canvas implements Runnable {   
    private static final long serialVersionUID = 1L;
    
    private static final String GAMETITLE = "Plud";
    public static final int WIDTH = 640;
    public static final int HEIGHT = WIDTH * 9 / 16;
    // public static int scale = 3;
    
    private Thread thread;
    private JFrame frame;
    private Keyboard key;
    private boolean running = false;
    
    //private Screen screen;
    
    //private BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    //private int[] pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
    
    public static int gravity = 4;
    
    //public static Ground ground;
    public static Ground[] ground = new Ground[WIDTH / 32 + 1];
    int[] yCoordinates = new int[ground.length];
    public static Character chaR;
    
    private int jump = 0;
    //private Image ground;
    
    public Game(){
        Dimension winSize = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(winSize);
        
        //screen = new Screen(width, height);
        frame = new JFrame();
        
        key = new Keyboard();
        addKeyListener(key);
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
        
        int frames = 0;
        int updates = 0;
        
        //ground = new Ground(0, 0);
        for (int i = 0; i < ground.length; i++){
            
            /*if (i != 0){
                switch(yCoordinates[i-1]){
                    case 1:
                        yCoordinates[i] = (int)(Math.random() * 2 + 1);
                        ground[i].yPos = Game.HEIGHT - ground[i].HEIGHT * yCoordinates[i];
                        break;
                    case 2:
                        yCoordinates[i] = (int)(Math.random() * 3 + 1);
                        ground[i].yPos = Game.HEIGHT - ground[i].HEIGHT * yCoordinates[i];
                        break;
                    case 3:
                        yCoordinates[i] = (int)(Math.random() * 3 + 2);
                        ground[i].yPos = Game.HEIGHT - ground[i].HEIGHT * yCoordinates[i];
                        break;
                    }
                
            }else{
                yCoordinates[i] = (int)(Math.random() * 3 + 1);
                ground[i].yPos = Game.HEIGHT - ground[i].HEIGHT * yCoordinates[i];
            }*/
            
            /*if (i == 20){
                yCoordinates[i] = 2;
            }else if(i != 0){
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
            }else{
                yCoordinates[i] = (int)(Math.random() * 3 + 1);
            }
            ground[i] = new Ground(0, 0, i, yCoordinates[i]);
            System.out.println(yCoordinates[i]);*/
            if (i == 5){
                ground[i] = new Ground(0, 0, i, 2);
            }else{
                ground[i] = new Ground(0, 0, i, 1);
            }
        }
        
        chaR = new irgame.object.Character();
        
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            
            while(delta >= 1){
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
    }
    
    public void update(){
        chaR.yPos += gravity;
        chaR.hitBox.setLocation(chaR.xPos, chaR.yPos);
        
        for (int i = 0; i < ground.length; i++){
            if (ground[i].xPos <= -ground[i].WIDTH){
                ground[i].xPos += getWidth() + ground[i].WIDTH;
                ground[i].hitBox.setLocation(ground[i].xPos, ground[i].yPos);
            }
            ground[i].xPos -= ground[0].HORIZ_VEL;
            ground[i].hitBox.setLocation(ground[i].xPos, ground[i].yPos);
        }
        
        if (chaR.xPos <= -chaR.WIDTH){
            chaR.xPos += getWidth() + chaR.WIDTH;
            chaR.hitBox.setLocation(chaR.xPos, chaR.yPos);
        }else if (chaR.xPos + chaR.WIDTH >= getWidth() + chaR.WIDTH){
            chaR.xPos -= getWidth() + chaR.WIDTH;
            chaR.hitBox.setLocation(chaR.xPos, chaR.yPos);
        }
        
        key.update();
        if (key.up || chaR.state.equals("jumping")){ //If the up-key is pressed or the character is already moving upwards (jumping),
            if (chaR.state.equals("walking") || chaR.state.equals("jumping")){ //If the character is standing or jumping
                //gravity = 0;
                if (jump < chaR.JUMP_HEIGHT / chaR.JUMP_FORCE){ //and if the current height is less than above the ground is less than the height you can jump, the current height will increase.
                    chaR.yPos -= chaR.JUMP_FORCE + gravity;
                    chaR.hitBox.setLocation(chaR.xPos, chaR.yPos);
                    chaR.state = "jumping";
                    jump++;
                }else {
                    //gravity = 4;
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
        }
        if (key.right){
            chaR.xPos += ground[0].HORIZ_VEL; 
            chaR.HORIZ_VEL = 2;
            chaR.hitBox.setLocation(chaR.xPos, chaR.yPos);
        }else{
            chaR.HORIZ_VEL = 0;
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
            g.drawImage(ground[i].sprite, ground[i].xPos, ground[i].yPos, null);
            g.setColor(Color.red);                                       //
            g.drawRect(ground[i].hitBox.x, ground[i].hitBox.y,           //Hitbox
                    ground[i].hitBox.width, ground[i].hitBox.height);    //
            g.drawString(""+(i+1), ground[i].xPos+10, ground[i].yPos+20);//Nummer
        }
        
        //Character rendering
        g.drawImage(chaR.BODY, chaR.xPos, chaR.yPos + chaR.SPRITE_SIZE, null);
        g.drawImage(chaR.HEAD, chaR.xPos, chaR.yPos, null);
        g.setColor(Color.red);                                      //Hitbox
        g.drawRect(chaR.xPos, chaR.yPos, chaR.WIDTH, chaR.HEIGHT);  //
        
        
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
