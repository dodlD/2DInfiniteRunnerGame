/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package irgame.graphics;

/**
 *
 * @author Daniel
 */
public class Screen {

    private int width, height;
    public int[] pixels;
    
    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
    }
    
    public void render() {
        
    }
    
}
