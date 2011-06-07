package paint.stuff;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 *  unfinished fill area
 *
 */
public class Fill extends ThingForClick {

    @Override
    public void click(MouseEvent e, BufferedImage i, Color c) {
        int x = e.getX();
        int y = e.getY();
        int w = i.getWidth(null);
        int h = i.getHeight(null);
        int[] inPixels = i.getRGB(0, 0, w, h, null, 0, w);
        fill(inPixels, x, y, w, h, i.getRGB(x, y), c.getRGB());
        i.setRGB(0, 0, w, h, inPixels, 0, w);
    }

    private void fill(int[] t, int x, int y, int w, int h,
            int f,
            int fw) {
        int index = y * w + x;
        if (index > h * w) {
            return;
        }
        if (t[index] == f) {
            t[index] = fw;
        }
        /*fill(t, x + 1, y, w, h, f, fw);
        fill(t, x - 1, y, w, h, f, fw);
        fill(t, x, y + 1, w, h, f, fw);
        fill(t, x, y - 1, w, h, f, fw);*/
    }
}
