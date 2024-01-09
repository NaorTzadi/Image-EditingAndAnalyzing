package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

public class ImageInfo {
    public static final BufferedImage BUFFERED_IMAGE;
    public static final Dimension DIMENSION = new Dimension();
    public static final int WIDTH;
    public static final int HEIGHT;
    public static final ColorModel COLOR_MODEL;
    public static final int TYPE;
    public static final Raster RASTER;
    public static final String FILE_TYPE=Constants.IMAGE_PATH.substring(Constants.IMAGE_PATH.indexOf(".")+1);

    static {
        BufferedImage bufferedImage;
        ColorModel colorModel;
        int type;
        Raster tempRaster;

        try {
            bufferedImage = ImageIO.read(new File(Constants.IMAGE_PATH));
            WIDTH=bufferedImage.getWidth();
            HEIGHT=bufferedImage.getHeight();
            DIMENSION.setSize(bufferedImage.getWidth(), bufferedImage.getHeight());
            colorModel = bufferedImage.getColorModel();
            type = bufferedImage.getType();
            tempRaster = bufferedImage.getRaster();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not read image file: " + e.getMessage());
        }

        BUFFERED_IMAGE = bufferedImage;
        COLOR_MODEL = colorModel;
        TYPE = type;
        RASTER = tempRaster;
    }
}
