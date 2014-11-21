package Devices;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Cronometro extends JDialog
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JDialog frame;
    private JLabel labelmsg;
    private JLabel labelTime;
    private Timer timer;
    private long startTime;
    private int initialTime = 5;
    
    private String bflag = "true";
    
    private JFrame mainframe;
    private String msg;

    public Cronometro (JFrame a_frame, String message, String command, boolean bool) {
    	mainframe = a_frame;
    	msg = message;
    	if(command=="start"){
        SwingUtilities.invokeLater (new Runnable () {
            public void run () {
                new Cronometro (mainframe,msg,"true");
            }
        });
    	}
    	else if (command=="end"){
            SwingUtilities.invokeLater (new Runnable () {
                public void run () {
                    new Cronometro (mainframe,msg,"false");
                }
            });
        	}
    	else if (command=="close"){
            SwingUtilities.invokeLater (new Runnable () {
                public void run () {
                    new Cronometro (mainframe,msg,"close");
                }
            });
        	}
    }
    
    private Cronometro (JFrame fr,String str,String flag) 
    {
    	super.setLocationRelativeTo(fr);
    	    	
    	mainframe=fr;
        bflag = flag;
        
    	Point point = mainframe.getLocationOnScreen();
    	
    	Double x = point.getX()+20;
    	Double y = point.getY()+105;
    	
        int x1 = x.intValue();
        int y1 = y.intValue();

    	
    	frame = new JDialog (mainframe,"Message",true);
        frame.setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize (190, 110);
        
        frame.setLocation(x1,y1);
        
        

        JLabel label = new JLabel(new ImageIcon("classes/Screenshot.png"));
        label.setHorizontalAlignment (JLabel.CENTER);
        frame.add(label, BorderLayout.WEST);
        
        labelmsg = new JLabel(str);
        labelmsg.setFont (new Font ("SansSerif", Font.BOLD, 13));
        labelmsg.setHorizontalAlignment (JLabel.CENTER);
        frame.add (labelmsg, BorderLayout.CENTER);
        

        if(flag.equals("true")){
        labelTime = new JLabel ("Starting in 5 seconds");
        } else if (flag.equals("false")){
        labelTime = new JLabel ("Task completed in 5 seconds");
        }
        else if (bflag.equals("close")){
        	initialTime = 10;
        labelTime= new JLabel ("Closing in 10 seconds");	
        }
        labelTime.setFont (new Font ("SansSerif", Font.BOLD, 10));
        labelTime.setHorizontalAlignment (JLabel.CENTER);
        

        frame.add (labelTime, BorderLayout.SOUTH);

        timer = new Timer (50, new ActionListener () {
            public void actionPerformed (ActionEvent e) {
                long diffTime = System.currentTimeMillis () - startTime;

                int seconds = (int) (diffTime / 1000 % 60);
                int realTime = initialTime-seconds;

                String s = String.format ("%01d", realTime);
                if(realTime==-1) {
                	timer.stop ();
                	try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}

                	frame.dispose();
                }
                if(bflag.equals("true"))
                labelTime.setText ("Starting in "+ s + " seconds");
                else if(bflag.equals("false"))
                labelTime.setText ("Task completed in "+ s + " seconds");
                else if (bflag.equals("close"))
                labelTime.setText ("Closing in "+ s + " seconds");	
            }
        });

        	if(initialTime==5 || initialTime==10){
                startTime = System.currentTimeMillis ();
                timer.start ();
            }

        frame.setVisible (true);
    }
/*
    public static void main (String[] args)
    {
    	final JFrame frame1 = new JFrame();
        frame1.setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE);
        frame1.setSize (220, 140);
        frame1.setVisible(true);
    	
        SwingUtilities.invokeLater (new Runnable () {
            public void run () {
                new Cronometro (frame1);
            }
        });
        
    }*/
}