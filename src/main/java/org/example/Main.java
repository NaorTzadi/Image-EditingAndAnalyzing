package org.example;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {

        /*try (Socket socket = new Socket("localhost", 5050)) {
            System.out.println("Connected to server!");
            // At this point, you could read and write data using the socket's input and output streams
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        new Login(new JFrame());

       /* System.out.println("-------- Screen Information --------");
        System.out.println("screen dimension: "+DisplayScreenInfo.DIMENSION);
        System.out.println("screen width: "+DisplayScreenInfo.DIMENSION.getWidth());
        System.out.println("screen height: "+DisplayScreenInfo.DIMENSION.getHeight());
        System.out.println("resolution: "+DisplayScreenInfo.RESOLUTION+" DPI");
        System.out.println("Refresh Rate: " +DisplayScreenInfo.DISPLAY_MODE);
        System.out.println("number of screens: "+DisplayScreenInfo.GRAPHICS_DEVICES.length);
        System.out.println("screen size: "+DisplayScreenInfo.getScreenSize());


        System.out.println("-------- Image Information ---------");
        System.out.println("image dimension: "+ImageInfo.DIMENSION);
        System.out.println("image width: "+ImageInfo.WIDTH);
        System.out.println("image height: "+ImageInfo.HEIGHT);
        System.out.println("image raster: "+ImageInfo.RASTER);
        System.out.println("image type: "+ImageInfo.TYPE);
        System.out.println("image color mode: "+ImageInfo.COLOR_MODEL);
        System.out.println("image file type: "+ImageInfo.FILE_TYPE);*/

    }

}