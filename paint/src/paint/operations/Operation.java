/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paint.operations;

import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;

/**
 *  Base operation on full image
 * 
 */
public abstract class Operation implements BufferedImageOp {

    public Rectangle2D getBounds2D(BufferedImage src) {
        return new Rectangle(src.getWidth(), src.getHeight());
    }

    public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel destCM) {
        if (destCM == null) {
            destCM = src.getColorModel();
        }
        return new BufferedImage(destCM, destCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), destCM.isAlphaPremultiplied(), null);
    }

    public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
        if (dstPt == null) {
            dstPt = new Point2D.Double();
        }
        dstPt.setLocation(srcPt);
        return dstPt;
    }

    public RenderingHints getRenderingHints() {
        return null;
    }
}
