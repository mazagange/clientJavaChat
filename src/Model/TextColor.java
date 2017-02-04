/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import javafx.scene.paint.Color;

/**
 *
 * @author ahmed mohsen
 */
public class TextColor implements Serializable{
    
   private double red;
   private double green;
   private double blue;

    public double getRed() {
        return red;
    }

    public void setRed(double red) {
        this.red = red;
    }

    public TextColor(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public double getGreen() {
        return green;
    }

    public void setGreen(double green) {
        this.green = green;
    }

    public double getBlue() {
        return blue;
    }

    public void setBlue(double blue) {
        this.blue = blue;
    }
   
   
    
}
