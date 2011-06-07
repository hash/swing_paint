package paint.stuff;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;

/**
 *  Base class for drag operations
 * 
 */
public abstract class ThingToPaint {

    protected Dimension size;
    protected Point origin;
    protected int mousex = 0;
    protected int OrHeight = 0;
    protected int OrWidth = 0;
    protected int drawY = 0;
    protected int drawX = 0;
    protected int Ory = 0;
    protected int Orx = 0;
    protected int prevy = 0;
    protected int prevx = 0;
    protected int mousey = 0;
    protected boolean started = true;

    protected void setGraphicalDefaults(MouseEvent e) {
        mousex = e.getX();
        mousey = e.getY();
        prevx = e.getX();
        prevy = e.getY();
        Orx = e.getX();
        Ory = e.getY();
        drawX = e.getX();
        drawY = e.getY();
        OrWidth = 0;
        OrHeight = 0;
    }

    public boolean mouseHasMoved(MouseEvent e) {
        return (mousex != e.getX() || mousey != e.getY());
    }

    public abstract void start(JComponent jc, MouseEvent e, Image i, Color c);

    public abstract void stop();
}
