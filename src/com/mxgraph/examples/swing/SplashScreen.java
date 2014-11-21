package com.mxgraph.examples.swing;

import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class SplashScreen extends JWindow {

    static boolean isRegistered;
    private static JProgressBar progressBar = new JProgressBar();
    private static SplashScreen execute;
    private static int count;
    private static Timer timer1;

    public SplashScreen() {

        Container container = getContentPane();
        container.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(new javax.swing.border.EtchedBorder());
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(10, 10, 350, 140);
        //panel.setLayout(null);
        
        JLabel label = new JLabel();
        label.setFont(new Font("Verdana", Font.BOLD, 14));
        label.setBounds(85, 25, 300, 30);
        //panel.add(label);
               
	  ImageIcon myImage = new ImageIcon("smartPM-LOGO.jpg");
	  
	  //screen = new SplashScreen(myImage);
	  //screen.setLocationRelativeTo(null);
	  //screen.setProgressMax(100);
	  //screen.setScreenVisible(true);
	  label.setIcon(myImage);
	  label.setVisible(true);
	  panel.add(label);
	  panel.setVisible(true);
        
      container.add(panel);

      progressBar.setMaximum(50);
      progressBar.setBounds(55, 110, 260, 15);
      container.add(progressBar);
      loadProgressBar();
      setSize(370, 160);
      setLocationRelativeTo(null);
      setVisible(true);
    }

    private void loadProgressBar() {
        ActionListener al = new ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                count++;

                progressBar.setValue(count);
                
                if (count >= 100) {

                    //createFrame();

                    //execute.setVisible(false);//swapped this around with timer1.stop()
                	timer1.stop();
                	dispose();
                    
                }

            }

            private void createFrame() throws HeadlessException {
                JFrame frame = new JFrame();
                frame.setSize(500, 500);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        };
        timer1 = new Timer(50, al);
        timer1.start();
    }
	    
}
