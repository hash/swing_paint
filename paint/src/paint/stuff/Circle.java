package paint.stuff;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;

/**
 *  Circle drawing
 *
 */
public class Circle extends Rectangular {

    private Color _c;
    private Image _i;

    @Override
    public void stop() {
        started = true;
        Graphics g = _i.getGraphics();
        g.setColor(_c);
        g.fillOval(drawX - 1, drawY - 1, OrWidth + 2, OrHeight + 2);
    }

    @Override
    public void start(JComponent jc, MouseEvent e, Image i, Color c) {
        _c = c;
        _i = i;
        Graphics g = jc.getGraphics();
        g.setColor(c);
        if (started) {
            setGraphicalDefaults(e);
            started = false;
        }
        if (mouseHasMoved(e)) {
            g.setXORMode(Color.white);
            g.drawOval(drawX, drawY, OrWidth, OrHeight);
            mousex = e.getX();
            mousey = e.getY();
            setActualBoundry();
            g.drawOval(drawX, drawY, OrWidth, OrHeight);
        }
    }
}
