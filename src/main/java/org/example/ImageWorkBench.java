package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageWorkBench {
    private static final int analysisComponentIndexX=20;
    private static final int analysisComponentIndexY=80;
    private static final int analysisComponentWidth=300;
    private static final int analysisComponentHeight=30;
    private static final int analysisComponentAddGap=20;

    public ImageWorkBench(JFrame frame){
        frame.setTitle("Image Work Bench");

        JLabel imageLabel = new JLabel(scaleImage(frame));
        imageLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.add(imageLabel);

        JButton returnToMainMenuButton = new JButton("Return to Main Menu");
        returnToMainMenuButton.setBounds(0, 500, 400, 65);
        returnToMainMenuButton.setFont(new Font(returnToMainMenuButton.getFont().getName(), returnToMainMenuButton.getFont().getStyle(), 20));
        returnToMainMenuButton.setVisible(false);
        frame.add(returnToMainMenuButton);

        JButton imageAnalysisButton = new JButton("image analysis.");
        imageAnalysisButton.setBounds(0, 435, 400, 65);
        imageAnalysisButton.setFont(new Font(imageAnalysisButton.getFont().getName(), imageAnalysisButton.getFont().getStyle(), 20));
        imageAnalysisButton.setVisible(false);
        frame.add(imageAnalysisButton);

        JButton displayScreenAnalysisButton = new JButton("display screen analysis.");
        displayScreenAnalysisButton.setBounds(0, 370, 400, 65);
        displayScreenAnalysisButton.setFont(new Font(displayScreenAnalysisButton.getFont().getName(), displayScreenAnalysisButton.getFont().getStyle(), 20));
        displayScreenAnalysisButton.setVisible(false);
        frame.add(displayScreenAnalysisButton);

        JButton returnButton=new JButton("return");
        returnButton.setBounds(100,480,200,65);
        returnButton.setFont(new Font(returnButton.getFont().getName(), returnButton.getFont().getStyle(), 20));
        returnButton.setVisible(false);
        frame.add(returnButton);

        List<Component> menuComponents=new ArrayList<>();
        menuComponents.add(returnButton);
        menuComponents.add(returnToMainMenuButton);
        menuComponents.add(imageAnalysisButton);
        menuComponents.add(displayScreenAnalysisButton);

        List<Component> imageAnalysisComponents=new ArrayList<>();
        JLabel imageAnalysisComponent1=new JLabel("");
        imageAnalysisComponent1.setBounds(analysisComponentIndexX,analysisComponentIndexY,analysisComponentWidth,analysisComponentHeight);
        JLabel imageAnalysisComponent2=new JLabel("");
        imageAnalysisComponent2.setBounds(analysisComponentIndexX,imageAnalysisComponent1.getY()+analysisComponentAddGap,analysisComponentWidth,analysisComponentHeight);
        JLabel imageAnalysisComponent3=new JLabel("");
        imageAnalysisComponent3.setBounds(analysisComponentIndexX,imageAnalysisComponent2.getY()+analysisComponentAddGap,analysisComponentWidth,analysisComponentHeight);
        JLabel imageAnalysisComponent4=new JLabel("");
        imageAnalysisComponent4.setBounds(analysisComponentIndexX,imageAnalysisComponent3.getY()+analysisComponentAddGap,analysisComponentWidth,analysisComponentHeight);
        imageAnalysisComponents.add(imageAnalysisComponent1);
        imageAnalysisComponents.add(imageAnalysisComponent2);
        imageAnalysisComponents.add(imageAnalysisComponent3);
        imageAnalysisComponents.add(imageAnalysisComponent4);
        for(Component component:imageAnalysisComponents){frame.add(component);}

        List<Component> displayAnalysisComponents=new ArrayList<>();
        JLabel displayAnalysisComponent1=new JLabel("");
        displayAnalysisComponent1.setBounds(analysisComponentIndexX,analysisComponentIndexY,analysisComponentWidth,analysisComponentHeight);
        JLabel displayAnalysisComponent2=new JLabel("");
        displayAnalysisComponent2.setBounds(analysisComponentIndexX,imageAnalysisComponent1.getY()+analysisComponentAddGap,analysisComponentWidth,analysisComponentHeight);
        JLabel displayAnalysisComponent3=new JLabel("");
        displayAnalysisComponent3.setBounds(analysisComponentIndexX,imageAnalysisComponent2.getY()+analysisComponentAddGap,analysisComponentWidth,analysisComponentHeight);
        JLabel displayAnalysisComponent4=new JLabel("");
        displayAnalysisComponent4.setBounds(analysisComponentIndexX,imageAnalysisComponent3.getY()+analysisComponentAddGap,analysisComponentWidth,analysisComponentHeight);
        displayAnalysisComponents.add(displayAnalysisComponent1);
        displayAnalysisComponents.add(displayAnalysisComponent2);
        displayAnalysisComponents.add(displayAnalysisComponent3);
        displayAnalysisComponents.add(displayAnalysisComponent4);
        for(Component component:displayAnalysisComponents){frame.add(component);}


        returnToMainMenuButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.repaint();
            new MainMenu(frame);
        });

        imageAnalysisButton.addActionListener(e -> {
            hideMenuComponents(menuComponents);

            returnButton.setVisible(true);
        });

        displayScreenAnalysisButton.addActionListener(e -> {
            hideMenuComponents(menuComponents);

            returnButton.setVisible(true);
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (!imageLabel.isVisible()) {
                        hideMenuComponents(menuComponents);
                        returnButton.setVisible(false);
                        imageLabel.setVisible(true);
                    } else {
                        imageLabel.setVisible(false);
                        displayMenuComponents(menuComponents);
                    }
                }
            }
        });

        returnButton.addActionListener(e -> {
            displayMenuComponents(menuComponents);
            returnButton.setVisible(false);
        });

        imageAnalysisButton.setFocusable(false);
        returnButton.setFocusable(false);
        displayScreenAnalysisButton.setFocusable(false);


        frame.setVisible(true);
    }
    private ImageIcon scaleImage(JFrame frame){
        Image image;
        try {image = ImageIO.read(new File(Constants.IMAGE_PATH));} catch (IOException e) {throw new RuntimeException(e);}
        Image scaledImage=image.getScaledInstance(frame.getWidth(),frame.getHeight(),Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
    private void displayMenuComponents(List<Component> components){
        for(Component component:components){
            component.setVisible(true);
        }
    }
    private void hideMenuComponents(List<Component> components){
        for(Component component:components){
            component.setVisible(false);
        }
    }
    private void displayOrHideMainComponents(List<Component> components){
        for(Component component:components){
            component.setVisible(!component.isVisible());
        }
    }





}