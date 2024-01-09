package org.example;

import org.example.Constants;
import org.example.MainMenu;
import org.example.Utility;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionTest {

    public ConnectionTest(JFrame frame){
        frame.setTitle("disconnected menu");

        ////////////// CONNECTION /////////////////
        JLabel disconnectedLabel1 = new JLabel("<html>It seems as if there's an issue<br>with your internet connection.</html>");
        disconnectedLabel1.setBounds(100,100,200,60);
        disconnectedLabel1.setVisible(false);
        frame.add(disconnectedLabel1);
        JLabel disconnectedLabel2=new JLabel("still disconnected...");
        disconnectedLabel2.setBounds(250,100,200,60);
        disconnectedLabel2.setVisible(false);
        frame.add(disconnectedLabel2);
        JButton checkConnectionButton=new JButton("check again");
        checkConnectionButton.setBounds(75,500,120,60);
        checkConnectionButton.setVisible(false);
        frame.add(checkConnectionButton);
        JButton offlineModeButton=new JButton("offline mode");
        offlineModeButton.setBounds(checkConnectionButton.getX()+checkConnectionButton.getWidth(),500,120,60);
        offlineModeButton.setVisible(false);
        frame.add(offlineModeButton);
        List<Component> disconnectedComponents=new ArrayList<>();
        disconnectedComponents.add(disconnectedLabel1);
        disconnectedComponents.add(checkConnectionButton);
        disconnectedComponents.add(offlineModeButton);
        disconnectedComponents.add(disconnectedLabel2);


        checkConnectionButton.addActionListener(e -> {
            boolean isConnected= Utility.isConnected();
            if(isConnected){
                frame.getContentPane().removeAll();
                frame.repaint();
                new MainMenu(frame);
            }else {
                for(Component component:disconnectedComponents){component.setVisible(true);}
                disconnectedLabel1.setVisible(false);
            }
        });
        checkConnectionButton.doClick();
        disconnectedLabel2.setVisible(false);
        disconnectedLabel1.setVisible(true);

        //////////////  OFFLINE MODE /////////////////
        List<Component> offlineModeComponents=new ArrayList<>();
        JLabel offlineModeOptionLabel=new JLabel("<html>you can choose to have offline mode as default" +
                " when you open the program.<br>you can always go back online through the settings.<html>");
        offlineModeOptionLabel.setBounds(100,frame.getHeight()/2,200,90);
        JCheckBox offlineModeCheckBox1 = new JCheckBox("default offline mode");
        offlineModeCheckBox1.setBounds(offlineModeOptionLabel.getX(),offlineModeOptionLabel.getY()+offlineModeOptionLabel.getHeight(),200,30);
        JCheckBox offlineModeCheckBox2=new JCheckBox("undecided");// זה די מיותר
        offlineModeCheckBox2.setBounds(offlineModeCheckBox1.getX(),offlineModeCheckBox1.getY()+offlineModeCheckBox1.getHeight(),200,30);
        JButton offlineModeContinueButton=new JButton("continue");
        offlineModeContinueButton.setBounds(offlineModeCheckBox2.getX(), Constants.frameHeight-(Constants.frameHeight/5),200,60);
        offlineModeComponents.add(offlineModeOptionLabel);offlineModeComponents.add(offlineModeCheckBox1);offlineModeComponents.add(offlineModeCheckBox2);offlineModeComponents.add(offlineModeContinueButton);

        offlineModeButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.repaint();
            for(Component component:offlineModeComponents){component.setVisible(true);frame.add(component);}
        });

        offlineModeCheckBox1.addActionListener(e -> {
            // בשביל זה צריך ליזכור משתמשים ואת הפעולות אותם הם בוחרים
        });
        offlineModeCheckBox2.addActionListener(e -> {
            // שוב לא באמת הכרחי
        });

        offlineModeContinueButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.repaint();
            new OfflineMode(frame);
        });

        frame.setVisible(true);
    }



}