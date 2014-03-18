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
    
    public SpriteSheet(String path){ //The cunstructor wich sets the path of the specified image file and then loads it.
        this.path = path;
        load(); //Explained down below
    }
    
    private void load(){    //Loads the specified image.
        try {
            img = ImageIO.read(getClass().getResource(path));
        } catch (IOException ex) {
            Logger.getLogger(SpriteSheet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
