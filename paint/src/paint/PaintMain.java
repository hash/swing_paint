package paint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.ArrayList;
import javax.swing.JComponent;
import paint.operations.Operation;
import paint.stuff.Nothing;
import paint.stuff.ThingForClick;
import paint.stuff.ThingToPaint;

/**
 *  The main class for painting
 *
 */
public class PaintMain extends JComponent implements MouseListener, MouseMotionListener {

    private static PaintMain paint = null;
    private static Color background = Color.WHITE;
    private static Color color = Color.BLACK;
    private static BufferedImage image = new BufferedImage(200, 400, BufferedImage.TYPE_INT_RGB);
    private static final int history_size = 3;
    private ArrayList<Raster> history_r = new ArrayList<Raster>();
    private ArrayList<BufferedImage> history = new ArrayList<BufferedImage>();
    private ThingToPaint ttp = new Nothing();
    private ThingForClick tfc = null;
    private int i_w, i_h;
    private boolean clickOp = false;
    private boolean dragOp = false;

    private PaintMain() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public static PaintMain getPaint() {
        if (paint == null) {
            paint = new PaintMain();
            clear();
        }
        return paint;
    }

    public static void clear() {
        Graphics g = image.getGraphics();
        g.setColor(background);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.dispose();
    }

    private void addToHistory() {
        if (history.size() < history_size) {
            history.add(image);
            history_r.add(image.getData());
        } else {
            for (int i = 0; i < history.size() - 1; i++) {
                history.set(i, history.get(i + 1));
                history_r.set(i, history_r.get(i + 1));
            }
            history.set(history.size() - 1, image);
            history_r.set(history_r.size() - 1, image.getData());
        }
    }

    public void back() {
        if (history.size() > 0) {
            image = history.remove(history.size() - 1);
            image.setData(history_r.remove(history.size()));
            updateCanvas();
        }
    }

    public void operation(Operation op) {
        addToHistory();
        image = op.filter(image, image);
        updateCanvas();
    }

    public void setThingToPaint(ThingToPaint ttp) {
        this.ttp = ttp;
        dragOp = true;
        clickOp = false;
    }

    public void setThingForClick(ThingForClick tfc) {
        this.tfc = tfc;
        dragOp = false;
        clickOp = true;
    }

    public void updateCanvas() {
        draw();
        update(image.getGraphics());
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        PaintMain.color = color;
    }

    public void setImage(BufferedImage image) {
        PaintMain.image = image;
        draw();
    }

    public BufferedImage getImage() {
        return image;
    }

    public void draw(Graphics g) {
        i_w = image.getWidth(null);
        i_h = image.getHeight(null);
        g.drawImage(image, 0, 0, null);
        Dimension d = new Dimension(i_w, i_h);
        setSize(d);
        setMinimumSize(d);
        setMaximumSize(d);
        setPreferredSize(d);
        setBounds(0, 0, i_w, i_h);
        updateUI();
        repaint();
        g.dispose();
    }

    public void draw() {
        draw(image.getGraphics());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        draw();
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        addToHistory();
        if (clickOp) {
            tfc.click(e, image, color);
        }
        if (dragOp) {
            ttp.stop();
        }
        updateCanvas();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        if (dragOp) {
            ttp.start(this, e, image, color);
        }
    }

    public void mouseMoved(MouseEvent e) {
    }
}
