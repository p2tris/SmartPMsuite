package com.mxgraph.examples.swing;

import javax.swing.UIManager;
import javax.swing.ImageIcon;

public class SplashScreenMain {

  Splash screen;

  public SplashScreenMain() {
    // initialize the splash screen
    splashScreenInit();
    // do something here to simulate the program doing something that
    // is time consuming
    for (int i = 0; i <= 100; i++)
    {
      for (long j=0; j<100000; ++j)
      {
        String poop = " " + (j + i);
      }
      // run either of these two -- not both
      screen.setProgress(i+"%", i);  // progress bar with a message
      //screen.setProgress(i);           // progress bar with no message
    }
    splashScreenDestruct();
  }

  private void splashScreenDestruct() {
    screen.setScreenVisible(false);
  }

  private void splashScreenInit() {
    ImageIcon myImage = new ImageIcon("smartPM-LOGO.jpg");
    screen = new Splash(myImage);
    screen.setLocationRelativeTo(null);
    screen.setProgressMax(100);
    screen.setScreenVisible(true);
  }


}