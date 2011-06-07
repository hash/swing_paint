package paint.stuff;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;

/**
 *  Filling rectangle
 *
 */
public class Rectangle extends Rectangular {

    private Image _i;
    private Color _c;

    @Override
    public void stop() {
        started = true;
        Graphics g = _i.getGraphics();
        g.setColor(_c);
        g.fillRect(drawX, drawY, OrWidth, OrHeight);
    }

    @Override
    public void start(JComponent jc, MouseEvent e, Image i, Color c) {
        _i = i;
        _c = c;
        Graphics g = jc.getGraphics();
        g.setColor(c);
        if (started) {
            setGraphicalDefaults(e);
            started = false;
        }
        if (mouseHasMoved(e)) {
            g.setXORMode(Color.white);
            g.drawRect(drawX, drawY, OrWidth, OrHeight);
            mousex = e.getX();
            mousey = e.getY();
            setActualBoundry();
            g.drawRect(drawX, drawY, OrWidth, OrHeight);
        }
    }
}
