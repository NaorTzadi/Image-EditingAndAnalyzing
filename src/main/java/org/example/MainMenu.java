package org.example;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainMenu {
    private static final int componentIndexX=20;
    private static final int componentWidth=300;
    private static final int componentHeight=30;
    private static final int componentIndexY=10;

    public MainMenu(JFrame frame) {
        frame.setTitle("Main Menu");

        JLabel invalidFileTypeLabel=new JLabel("oops...its seems as if you loaded the wrong file type.");
        invalidFileTypeLabel.setBounds(componentIndexX,10,300,componentHeight);
        invalidFileTypeLabel.setVisible(false);
        frame.add(invalidFileTypeLabel);

        JLabel dragFileLabel=new JLabel("*you can drag the desired file onto the frame to load it.");
        dragFileLabel.setBounds(componentIndexX,40,400,componentHeight);
        frame.add(dragFileLabel);

        JButton loadFileButton = new JButton("browse for local file");
        loadFileButton.setBounds(componentIndexX,90,200,componentHeight);
        frame.add(loadFileButton);


        JButton saveFilesButton=new JButton("save files");
        saveFilesButton.setBounds(componentIndexX,loadFileButton.getY()+loadFileButton.getHeight()+10,100,componentHeight);
        frame.add(saveFilesButton);

        JButton saveLinksButton=new JButton("save links");
        saveLinksButton.setBounds(componentIndexX,saveFilesButton.getY()+saveFilesButton.getHeight(),100,componentHeight);
        frame.add(saveLinksButton);

        JButton deleteFilesButton=new JButton("delete files");
        deleteFilesButton.setBounds(saveFilesButton.getX()+saveFilesButton.getWidth(),saveFilesButton.getY(),saveFilesButton.getWidth(),saveFilesButton.getHeight());
        frame.add(deleteFilesButton);
        JButton deleteLinksButton=new JButton("delete links");
        deleteLinksButton.setBounds(saveLinksButton.getX()+saveLinksButton.getWidth(),saveLinksButton.getY(),saveLinksButton.getWidth(),saveLinksButton.getHeight());
        frame.add(deleteLinksButton);

        JButton displayFreeSpaceButton=new JButton("free space in bytes");
        displayFreeSpaceButton.setBounds(componentIndexX,420,250,componentHeight);
        frame.add(displayFreeSpaceButton);
        JLabel freeSpaceInGBLabel=new JLabel("current device free space in GB");
        freeSpaceInGBLabel.setBounds(componentIndexX,450,250,componentHeight);
        frame.add(freeSpaceInGBLabel);
        List<JLabel> freeSpaceInGBLabels=createFreeSpaceLabels(frame,Utility.listRootsAndFreeSpaceInGB());
        for(JLabel label:freeSpaceInGBLabels){label.setVisible(true);}
        JLabel freeSpaceInBytesLabel=new JLabel("current device free space in bytes");
        freeSpaceInBytesLabel.setBounds(componentIndexX,450,250,componentHeight);
        freeSpaceInBytesLabel.setVisible(false);
        frame.add(freeSpaceInBytesLabel);
        List<JLabel> freeSpaceInBytesLabels=createFreeSpaceLabels(frame,Utility.listRootsAndFreeSpaceInBytes());


        /*List<Component> mainMenuComponents=new ArrayList<>();
        mainMenuComponents.add(displayFreeSpaceButton);
        mainMenuComponents.addAll(freeSpaceInGBLabels);
        mainMenuComponents.add(freeSpaceInGBLabel);
        mainMenuComponents.add(saveFilesButton);
        mainMenuComponents.add(saveLinksButton);
        mainMenuComponents.add(deleteFilesButton);
        mainMenuComponents.add(deleteLinksButton);
        mainMenuComponents.add(loadFileButton);
        mainMenuComponents.add(dragFileLabel);*/


        loadFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                    public boolean accept(File f) {
                        return isValidFileType(f);
                    }
                    public String getDescription() {
                        return "Image/Video Files";
                    }
                });
                fileChooser.setFileFilter(fileChooser.getAcceptAllFileFilter());

                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    if(isValidFileType(selectedFile)){
                        loadFile(selectedFile,frame);
                    }else {
                        Utility.toggleVisibilityOn(invalidFileTypeLabel,Constants.defaultToggleVisibilityTimesSet);
                    }
                }
            }
        });
        displayFreeSpaceButton.addActionListener(e -> {
            if (displayFreeSpaceButton.getText().contains("GB")){
                displayFreeSpaceButton.setText("free space in bytes");
                freeSpaceInGBLabel.setVisible(true);
                freeSpaceInBytesLabel.setVisible(false);
                for(JLabel label:freeSpaceInBytesLabels){label.setVisible(false);}
                for(JLabel label:freeSpaceInGBLabels){label.setVisible(true);}
            }else {
                displayFreeSpaceButton.setText("free space in GB");
                freeSpaceInGBLabel.setVisible(false);
                freeSpaceInBytesLabel.setVisible(true);
                for(JLabel label:freeSpaceInBytesLabels){label.setVisible(true);}
                for(JLabel label:freeSpaceInGBLabels){label.setVisible(false);}
            }
        });

        frame.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    Transferable t = evt.getTransferable();
                    Object data = t.getTransferData(DataFlavor.javaFileListFlavor);

                    if (data instanceof List) {
                        List<?> fileList = (List<?>) data;
                        for (Object f : fileList) {
                            if (f instanceof File) {
                                if(isValidFileType((File) f)){
                                    loadFile((File) f,frame);
                                }else {
                                    Utility.toggleVisibilityOn(invalidFileTypeLabel,Constants.defaultToggleVisibilityTimesSet);
                                }
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        frame.setVisible(true);
    }

    private boolean isValidFileType(File file){
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ||
                fileName.endsWith(".bmp") || fileName.endsWith(".gif") || fileName.endsWith(".webp") ||
                fileName.endsWith(".mp4") || fileName.endsWith(".avi") || fileName.endsWith(".mkv");
    }

    private void loadFile(File file,JFrame frame){
        Constants.IMAGE_PATH=file.getAbsolutePath();
        frame.getContentPane().removeAll();
        frame.repaint();
        new ImageWorkBench(frame);

    }


    private List<JLabel> createFreeSpaceLabels(JFrame frame,List<String> strings){
        List<JLabel> labels=new ArrayList<>();
        int y_index=480;
        int x_index=20;
        int counter=0;
        for(String info:strings){
            if (info.contains("root")){
                if (counter>0){
                    x_index+=200;
                    y_index=480;
                }
                counter++;
            }
            JLabel label=new JLabel(info); label.setBounds(x_index,y_index,300,20);
            label.setVisible(false);
            labels.add(label);
            frame.add(label);
            y_index+=20;
        }
        return labels;
    }


}