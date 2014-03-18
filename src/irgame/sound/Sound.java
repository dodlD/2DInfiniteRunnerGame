/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package irgame.sound;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Daniel
 */
public class Sound {
    private String path;
    private Clip clip;
    
    public Sound(String path) { //The constructor that loads the specified sound file.
        this.path = path;
        load();
        
    }
    
    private void load(){
        try {
            File file = new File(getClass().getResource(path).toURI());
            if (file.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(file);
             // load the sound into memory (a Clip)
                clip = AudioSystem.getClip();
                clip.open(sound);
            }
        }catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }catch (LineUnavailableException e) {
            e.printStackTrace();
        }catch (URISyntaxException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void play(){ //Plays the sound clip.
        clip.setFramePosition(0);  // Must always rewind!
        clip.start();
    }
    
    public void loop(){ //Loops the sound clip.
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void stop(){ //Stops the sound clip.
        clip.stop();
    }
}
