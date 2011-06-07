package paint.stuff;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;

/**
 *  Simple pen
 *
 */
public class Pen extends ThingToPaint {

    @Override
    public void stop() {
        started = true;
    }

    @Override
    public void start(JComponent jc, MouseEvent e, Image i, Color c) {
        Graphics jg = jc.getGraphics();
        Graphics g = i.getGraphics();
        g.setColor(c);
        jg.setColor(c);
        if (started) {
            setGraphicalDefaults(e);
            started = false;
            g.drawLine(prevx, prevy, mousex, mousey);
            jg.drawLine(prevx, prevy, mousex, mousey);
        }
        if (mouseHasMoved(e)) {
            mousex = e.getX();
            mousey = e.getY();
            g.drawLine(prevx, prevy, mousex, mousey);
            jg.drawLine(prevx, prevy, mousex, mousey);
            prevx = mousex;
            prevy = mousey;
        }
    }
}
