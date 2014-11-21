package com.mxgraph.smartml.view;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.mxgraph.smartml.model.XMLParser;

public class DeployProcess {

	public JTextField booltype_textField;
	public JTextField integertype_textField;
	public JPanel panel; 
	public JPanel panel_1; 
	public JLabel lblBooleanType;
	public JLabel lblIntegerType;
	public JLabel lblPredefinedDataType;
	public JLabel lblUserDefinedData;
	public JScrollPane usertype_scrollPane;
	public JTextArea textArea;
	public JButton btnCancel;
	public JButton btnOK;
	
	
    private JPanel contentPane;
    private JTextArea logArea;
    private JLabel imageLabel;
    private ImageIcon[] images;
    private JButton startStopButton;
    private String[] path;
    private int counter;

    private Timer timer;

    private ActionListener timerAction = 
                            new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            counter %= path.length;
            imageLabel.setIcon(images[counter++]);
        }
    };

    private ActionListener buttonAction = 
                            new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (timer.isRunning()) {
                startStopButton.setText("Start");
                timer.stop();
            }
            else {
                startStopButton.setText("Stop");
                timer.start();
            }
        }
    };

    public DeployProcess() {
        imageLabel = new JLabel("Nothing to display yet...", JLabel.CENTER);
        images = new ImageIcon[5];
        path = new String[] {
            "Deployment in progress",
            ".",
            ".",
            ".",
            ".",
            ".",
            "Configuration files correctly parsed!",
            "Planning Domain created!",
            "The Initial State is ready!",
            "Indigolog file created!",
            "\nDONE!"
        };
        counter = 0;
    }

    public void displayGUI() {
        
    	final JFrame frame = new JFrame("Process Deployment");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(450, 450);        
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout());
				
		panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(440,230));
		panel_1.setLayout(new FlowLayout());
		panel_1.setBorder(new TitledBorder(null, "Deployement Area", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel_1);
		
		textArea = new JTextArea(9,30);
		
		usertype_scrollPane = new JScrollPane();
		usertype_scrollPane.setViewportView(textArea);
		usertype_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		usertype_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel_1.add(usertype_scrollPane);	
		
		btnOK = new JButton("OK");
		btnOK.setEnabled(false);
		
		btnOK.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                frame.dispose();
            }
            
            
        });
		
		panel_1.add(btnOK);
		
		
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

        new BackgroundTask().execute();
        timer = new Timer(10000, timerAction);
        timer.start();
    }

    private class BackgroundTask extends SwingWorker<ImageIcon[], String> {
        @Override
        protected ImageIcon[] doInBackground() {
            ImageIcon[] images = new ImageIcon[path.length];
            for (int i = 0; i < path.length; i++)
            {
                if(i<5) {
                	publish(path[i]);
                	try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
                if(i>=5) {
                	publish(path[i] + "\n");
                	try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
                if(i==path.length-1)
                	btnOK.setEnabled(true);
            }

            return images;
        }

        @Override
        protected void process(java.util.List<String> messages) {
            for (String message : messages)
                textArea.append(message);
        }

        @Override
        protected void done() {
            try {
                images = get();             
            } catch(Exception e) {e.printStackTrace();}
        }
    }

}