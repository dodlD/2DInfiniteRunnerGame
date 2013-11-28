/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package irgame.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Daniel
 */
public class SpriteSheet {

    private String path;
    public BufferedImage img;
    
    public SpriteSheet(String path/*, int size*/){
        this.path = path;
        //SIZE = size;
        load();
    }
    
    private void load(){
        try {
            //img = ImageIO.read(SpriteSheet.class.getResource(path));
            img = ImageIO.read(getClass().getResource(path));
        } catch (IOException ex) {
            System.out.println(getClass().getResource(path));
            Logger.getLogger(SpriteSheet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
