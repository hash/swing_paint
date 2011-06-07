
package paint.stuff;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;

/**
 *  Linie thingy
 * 
 */
public class Line extends ThingToPaint {

    private Image _i = null;
    private Color _c = null;

    @Override
    public void stop() {
        if ((Math.abs(Orx - mousex) + Math.abs(Ory - mousey)) != 0) {
            started = true;
            Graphics g = _i.getGraphics();
            g.setColor(_c);
            g.drawLine(Orx, Ory, mousex, mousey);
        }
    }

    @Override
    public void start(JComponent jc, MouseEvent e, Image i, Color c) {
        _i = i;
        _c = c;
        Graphics g = jc.getGraphics();
        g.setColor(c);
        if (started) {
            setGraphicalDefaults(e);
            g.setXORMode(Color.white);
            g.drawLine(Orx, Ory, mousex, mousey);
            started = false;
        }
        if (mouseHasMoved(e)) {
            g.setXORMode(Color.white);
            g.drawLine(Orx, Ory, mousex, mousey);
            mousex = e.getX();
            mousey = e.getY();
            g.drawLine(Orx, Ory, mousex, mousey);
        }
    }
}
