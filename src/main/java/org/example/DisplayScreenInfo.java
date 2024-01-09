package org.example;

import java.awt.*;
import java.awt.GraphicsDevice;

public class DisplayScreenInfo {

    public static final Dimension DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int RESOLUTION = Toolkit.getDefaultToolkit().getScreenResolution();
    public static final GraphicsDevice[] GRAPHICS_DEVICES = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
    private static final GraphicsDevice DEFAULT_SCREEN = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    public static final DisplayMode DISPLAY_MODE = DEFAULT_SCREEN.getDisplayMode();

    public static double getScreenSize(){
        double widthInPixels = DIMENSION.getWidth();
        double heightInPixels = DIMENSION.getHeight();
        return Math.sqrt(Math.pow(widthInPixels / RESOLUTION, 2) + Math.pow(heightInPixels / RESOLUTION, 2));
    }

}