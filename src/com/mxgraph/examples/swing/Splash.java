package com.mxgraph.examples.swing;

import javax.swing.*;
import java.awt.*;

public class Splash extends JWindow {
  BorderLayout borderLayout1 = new BorderLayout();
  JLabel imageLabel = new JLabel();
  JPanel southPanel = new JPanel();
  FlowLayout southPanelFlowLayout = new FlowLayout();
  JProgressBar progressBar = new JProgressBar();
  ImageIcon imageIcon;

  public Splash(ImageIcon imageIcon) {
    this.imageIcon = imageIcon;
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  // note - this class created with JBuilder
  void jbInit() throws Exception {
    imageLabel.setIcon(imageIcon);
    this.getContentPane().setLayout(borderLayout1);
    
    progressBar.setPreferredSize(new Dimension(300, 20));
    southPanel.setLayout(southPanelFlowLayout);
    //southPanel.setBackground(Color.BLACK);
   // imageLabel.setBorder(BorderFactory.createLineBorder(Color.black));
    //southPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    this.getContentPane().add(imageLabel, BorderLayout.CENTER);
    this.getContentPane().add(southPanel, BorderLayout.SOUTH);
    
    ((JComponent) this.getContentPane()).setBorder(BorderFactory.createLineBorder(Color.black));
    
    southPanel.add(progressBar, null);
    
    this.pack();
  }

  public void setProgressMax(int maxProgress)
  {
    progressBar.setMaximum(maxProgress);
  }

  public void setProgress(int progress)
  {
    final int theProgress = progress;
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        progressBar.setValue(theProgress);
      }
    });
  }

  public void setProgress(String message, int progress)
  {
    final int theProgress = progress;
    final String theMessage = message;
    setProgress(progress);
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        progressBar.setValue(theProgress);
        setMessage(theMessage);
      }
    });
  }

  public void setScreenVisible(boolean b)
  {
    final boolean boo = b;
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        setVisible(boo);
      }
    });
  }

  private void setMessage(String message)
  {
    if (message==null)
    {
      message = "";
      progressBar.setStringPainted(false);
    }
    else
    {
      progressBar.setStringPainted(true);
    }
    progressBar.setString(message);
  }
}