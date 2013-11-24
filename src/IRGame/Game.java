package IRGame;

import IRGame.Graphics.Screen;
import IRGame.Graphics.Sprite;
import IRGame.Input.Keyboard;
import IRGame.Object.Character;
import IRGame.Object.Ground;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.ImageIcon;
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
    
    private Ground ground;
    //private Image ground;
    
    public Game(){
        Dimension winSize = new Dimension(WIDTH/* * scale*/, HEIGHT/* * scale*/);
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
        
        ground = new Ground(0, 0);
        Character chaR = new Character(295, 125);
        
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
    
    public void update() {
        if(key.up){}
        if(key.down){}
        if(key.left){}
        if(key.right){}
    }
    
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        //Ground rendering
        for(int i = 0; i < WIDTH / 80; i++){
            g.drawImage(ground.getImg(), 80 * i, getHeight() - 40, null);
        }
        
        //Character rendering
        
        
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
