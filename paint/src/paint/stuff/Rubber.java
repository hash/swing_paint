package paint.stuff;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;

/**
 *  For earasing things
 *
 */
public class Rubber extends ThingToPaint {

    private Image _i;
    private Color delete;
    private int eraserLength = 1;

    public void setDelete(Color delete) {
        this.delete = delete;
    }

    @Override
    public void stop() {
        started = true;
        Graphics g = _i.getGraphics();
        g.setColor(delete);
        g.drawRect(mousex - eraserLength, mousey - eraserLength, eraserLength * 2, eraserLength * 2);
    }

    @Override
    public void start(JComponent jc, MouseEvent e, Image i, Color c) {
        _i = i;
        Graphics g = i.getGraphics();
        Graphics jg = jc.getGraphics();
        if (started) {
            setGraphicalDefaults(e);
            started = false;
            g.setColor(delete);
            g.fillRect(mousex - eraserLength, mousey - eraserLength, eraserLength * 2, eraserLength * 2);
            g.setColor(Color.black);
            g.drawRect(mousex - eraserLength, mousey - eraserLength, eraserLength * 2, eraserLength * 2);
            jg.setColor(delete);
            jg.fillRect(mousex - eraserLength, mousey - eraserLength, eraserLength * 2, eraserLength * 2);
            jg.setColor(Color.black);
            jg.drawRect(mousex - eraserLength, mousey - eraserLength, eraserLength * 2, eraserLength * 2);
            prevx = mousex;
            prevy = mousey;
        }
        if (mouseHasMoved(e)) {
            g.setColor(delete);
            g.drawRect(prevx - eraserLength, prevy - eraserLength, eraserLength * 2, eraserLength * 2);
            jg.setColor(delete);
            jg.drawRect(prevx - eraserLength, prevy - eraserLength, eraserLength * 2, eraserLength * 2);
            mousex = e.getX();
            mousey = e.getY();
            g.setColor(delete);
            g.fillRect(mousex - eraserLength, mousey - eraserLength, eraserLength * 2, eraserLength * 2);
            g.setColor(Color.black);
            g.drawRect(mousex - eraserLength, mousey - eraserLength, eraserLength * 2, eraserLength * 2);
            jg.setColor(delete);
            jg.fillRect(mousex - eraserLength, mousey - eraserLength, eraserLength * 2, eraserLength * 2);
            jg.setColor(Color.black);
            jg.drawRect(mousex - eraserLength, mousey - eraserLength, eraserLength * 2, eraserLength * 2);
            prevx = mousex;
            prevy = mousey;
        }
    }
}
