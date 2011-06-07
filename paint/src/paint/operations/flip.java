package paint.operations;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

/**
 *  Flip operation for turning the image around
 * 
 */
public class Flip extends Operation {

    /**
     * Flip the image horizontally.
     */
    public static final int FLIP_H = 1;
    /**
     * Flip the image vertically.
     */
    public static final int FLIP_V = 2;
    /**
     * Rotate the image 90 degrees clockwise.
     */
    public static final int FLIP_90CW = 3;
    /**
     * Rotate the image 90 degrees counter-clockwise.
     */
    public static final int FLIP_90CCW = 4;
    /**
     * Rotate the image 180 degrees.
     */
    public static final int FLIP_180 = 5;
    private int operation;
    private int width, height;
    private int newWidth, newHeight;

    public Flip() {
        this(FLIP_180);
    }

    public Flip(int operation) {
        this.operation = operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public int getOperation() {
        return operation;
    }

    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        width = src.getWidth();
        height = src.getHeight();

        int[] inPixels = src.getRGB(0, 0, width, height, null, 0, width);

        newWidth = width;
        newHeight = height;

        switch (operation) {
            case FLIP_90CW:
                newWidth = height;
                newHeight = width;
                break;
            case FLIP_90CCW:
                newWidth = height;
                newHeight = width;
                break;
        }

        int[] newPixels = new int[newWidth * newHeight];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int index = row * width + col;
                int newRow = row;
                int newCol = col;
                switch (operation) {
                    case FLIP_H:
                        newCol = width - col - 1;
                        break;
                    case FLIP_V:
                        newRow = height - row - 1;
                        break;
                    case FLIP_90CW:
                        newRow = col;
                        newCol = height - row - 1;
                        break;
                    case FLIP_90CCW:
                        newRow = width - col - 1;
                        newCol = row;
                        break;
                    case FLIP_180:
                        newRow = height - row - 1;
                        newCol = width - col - 1;
                        break;
                }
                int newIndex = newRow * newWidth + newCol;
                newPixels[newIndex] = inPixels[index];
            }
        }

        if (dest == null || operation == FLIP_90CCW || operation == FLIP_90CW) {
            ColorModel destCM = src.getColorModel();
            dest = new BufferedImage(destCM, destCM.createCompatibleWritableRaster(newWidth, newHeight), destCM.isAlphaPremultiplied(), null);
        }
        dest.setRGB(0, 0, newWidth, newHeight, newPixels, 0, newWidth);

        return dest;
    }

    @Override
    public String toString() {
        switch (operation) {
            case FLIP_H:
                return "Flip Horizontal";
            case FLIP_V:
                return "Flip Vertical";
            case FLIP_90CW:
                return "Rotate 90";
            case FLIP_90CCW:
                return "Rotate -90";
            case FLIP_180:
                return "Rotate 180";
        }
        return "Flip";
    }
}
