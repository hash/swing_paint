/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paint.stuff;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 *  Base class for click operations
 * 
 */
public abstract class ThingForClick {

    public abstract void click(MouseEvent e, BufferedImage i, Color c);
}
