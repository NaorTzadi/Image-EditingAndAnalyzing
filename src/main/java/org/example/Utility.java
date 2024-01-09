package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Utility {
    // דברים שיש לשהם שימוש רק פעם אחת לא אמורים להיות פה אלא במחלקה שבה משתמשים בהם
    private static final String greeting1="Good morning!";
    private static final String greeting2="Good afternoon!";
    private static final String greeting3="Good evening!";
    private static final String greeting4="hello, hope your night is going well.";
    private static final String greeting5="wow, you must be a night owl.\uD83E\uDD89";
    public static List<String> listRootsAndFreeSpaceInGB() {
        List<String> infoList = new ArrayList<>();
        File[] roots = File.listRoots();
        for (File root : roots) {
            infoList.add("File system root: " + root.getAbsolutePath());

            long freeSpaceGB = root.getFreeSpace() / (1024 * 1024 * 1024);
            infoList.add("Free space (GB): " + freeSpaceGB);

            long usableSpaceGB = root.getUsableSpace() / (1024 * 1024 * 1024);
            infoList.add("Usable space (GB): " + usableSpaceGB);

            long totalSpaceGB = root.getTotalSpace() / (1024 * 1024 * 1024);
            infoList.add("Total space (GB): " + totalSpaceGB);
        }
        return infoList;
    }

    public static String getGreeting() {
        LocalDateTime currentTime = LocalDateTime.now();int hour = currentTime.getHour();String greeting;
        if (hour >= 5 && hour < 12) {greeting = greeting1;
        } else if (hour >= 12 && hour < 18) {greeting = greeting2;
        } else if (hour>=18 && hour<22){greeting = greeting3;
        }else if (hour>=22){greeting=greeting4;
        }else {greeting=greeting5;}
        return greeting;
    }

    public static String getFormattedTime() {return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));}
    public static List<String> listRootsAndFreeSpaceInBytes() {
        List<String> infoList = new ArrayList<>();
        File[] roots = File.listRoots();
        for (File root : roots) {
            infoList.add("File system root: " + root.getAbsolutePath());
            infoList.add("Free space (bytes): " + root.getFreeSpace());
            infoList.add("Usable space (bytes): " + root.getUsableSpace());
            infoList.add("Total space (bytes): " + root.getTotalSpace());
        }
        return infoList;
    }

    public static long getMaxHeapSpace() {
        return Runtime.getRuntime().maxMemory();
    }
    public static long getFreeHeapSpace() {
        return Runtime.getRuntime().freeMemory();
    }

    public static boolean isConnected(){
        try {
            InetAddress address = InetAddress.getByName("8.8.8.8");
            boolean isReachable = address.isReachable(5000);
            if (isReachable) {
                System.out.println("Internet connection is available.");
                return true;
            } else {
                System.out.println("No internet connection.");
                return false;
            }
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return false;
        }
    }
    public static void toggleVisibilityOn(JComponent component, int timeInMillis) {
        Timer timer = new Timer(timeInMillis, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                component.setVisible(false);
            }
        });
        component.setVisible(true);
        timer.setRepeats(false);
        timer.start();
    }
    public static void toggleVisibilityOff(JComponent component, int timeInMillis) {
        Timer timer = new Timer(timeInMillis, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                component.setVisible(!component.isVisible());
            }
        });
        component.setVisible(false);
        timer.setRepeats(false);
        timer.start();
    }


}