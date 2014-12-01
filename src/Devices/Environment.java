package Devices;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;

//import sun.util.EmptyListResourceBundle;


public class Environment implements Runnable {
	   
	   public World world = null;	   
	   
	   private  String service = "Environment";
	   Thread thread = null;
	   private int port = 5555;
	   
	   private Vector<Workitem> workList = null;
	   private Vector<Workitem> ad_workList = null; // Assegnato in caso di adattività
	   
	   private Workitem workitem = null;
	   
   
	 //Stringa contenente i comandi da inviare al PMS.  
	   private String s = new String("");
	   private StringBuffer toSend = new StringBuffer("");
	   private StringBuffer toAppend = new StringBuffer("");
	
	   public  JFrame mainFrame = null;
	   public  JPanel statusBar = null;
	   public  JLabel statusField = null;
	   public  JTextField statusColor = null;
	   public int pos_x = 0;
	   public int pos_y = 0;
	   
	  // Stato di connessione di un device //
	   public final int NULL = 0;
	   public final int DISCONNECTED = 1;
	   public final int DISCONNECTING = 2;
	   public final int BEGIN_CONNECT = 3;
	   public final int CONNECTED = 4;
	   
	   public int connectionStatus = DISCONNECTED;
	   
	   public final String statusMessages[] = {
			      " Error! Could not connect!", " Disconnected",
			      " Disconnecting...", " Connecting...", " Connected"
			   };
	   
	   public String statusString = statusMessages[connectionStatus];
	   
   
	   /*Tipi di dato associati all'esempio - devono essere definiti ad ogni esecuzione del programma*/
	   public String location_type[] = {"loc00","loc01","loc02","loc03","loc10","loc13","loc20","loc23","loc30","loc31","loc32","loc33"};
	   public String exogenous_type[] = {"fire","photoLost","rockSlide"};
	   public JComboBox ex_type_box = null;
	   public JComboBox loc_type_box = null;
	   
	   /*Vector contenente tutti i tipi di dato*/
	   public Vector allDataTypes = new Vector();
	      
	   private JScrollPane scrollOutputArea = null;
	   public JTextArea taskArea = null;
	   public String oldTaskArea = null;

	   public JButton launchButton = null;
	   
	   public boolean outputListEnabled = false;
	   public boolean outputListEnabled_old = false;
	   
	   public boolean adaptivity = false;
	   
	   public Vector outputListVector = new Vector();
	   //public JComboBox outputList = null;
	   public String hostIP = "localhost";

	   public boolean isHost = true;
	   public ServerSocket hostServer = null;
	   public Socket socket = null;
	   public BufferedReader in = null;
	   public PrintWriter out = null;
	   
	   public JPanel outputPane = new JPanel();
	   
	   	   	   
	   ActionListener buttonListener = null;
	   
	   //public final static Pda2 tcpObj = new Pda2();
	   
	   public Environment(String srv,int x, int y, World w) {
		   world = w;
		   service=srv;
		   pos_x = x;
		   pos_y = y;
		   initGUI();
		   
		   //Riempiamo il Vector contenente tutti i tipi di dato
		   allDataTypes.add(location_type);	   
	   }
	   
	
private void initGUI() {   
		
	statusField = new JLabel();
    statusField.setText(statusMessages[DISCONNECTED]);
    statusColor = new JTextField(1);
    statusColor.setBackground(Color.red);
    statusColor.setEditable(false);
    statusBar = new JPanel(new BorderLayout());
    statusBar.add(statusColor, BorderLayout.WEST);
    statusBar.add(statusField, BorderLayout.CENTER);
    
    /*----------------------------------------------*/
    
	JPanel optionPane = new JPanel();
	optionPane.setLayout(new BorderLayout());
	JPanel buttonPane = new JPanel();



    launchButton = new JButton("Launch it");
    launchButton.setEnabled(true);
 	launchButton.setActionCommand("Launch");
    
    buttonPane.add(launchButton);
    
    JLabel ex_label = new JLabel("EXOGENOUS EVENT:");
    ex_type_box = new JComboBox(exogenous_type);
    JLabel empty_label_1 = new JLabel("");
    
    JLabel input_label = new JLabel("INPUT:");
    loc_type_box = new JComboBox(location_type);
    JLabel empty_label_2 = new JLabel("");
    
    ex_label.setPreferredSize(new Dimension(140,15));
    ex_type_box.setPreferredSize(new Dimension(100,20));
    empty_label_1.setPreferredSize(new Dimension(230,20));
    
    input_label.setPreferredSize(new Dimension(50,15));
    loc_type_box.setPreferredSize(new Dimension(70,20));
    empty_label_2.setPreferredSize(new Dimension(340,20));
    
    /*----------------------------------------------*/
    
    //Gestione del readyToStart e finishedTask
    buttonListener = new ActionListener() {
         
    public void actionPerformed(ActionEvent e) {
    	  if (e.getActionCommand().equals("Launch")) {
    		  String s = ex_type_box.getSelectedItem().toString()+"("+loc_type_box.getSelectedItem().toString()+")";
    		  System.out.println("*** JAVA-Environment -- Environment is sending the exogenous event" + " : "+s);
   		  
    		  sendString(s);
    		  
    		  
	        if(adaptivity==true){

      	  }
	      else if (adaptivity==false) {
	      }
       	  }       	  

       		  
         	  /* update the simulator environment */
         	  /*
         	  if(world!=null){
         	   world.appendInfo("finishedTask("+service+","+task+","+input+","+output+")");
      		    if(task.equalsIgnoreCase("go"))
      		    	world.changeServicePosition(service, output);
         	  }
      		  /* ************************ */
        
         }
         };

    launchButton.addActionListener(buttonListener);
 
    /*----------------------------------------------*/
    
    outputPane = new JPanel();
    outputPane.setLayout(new FlowLayout());
    outputListEnabled = false;
    
    outputPane.setPreferredSize(new Dimension(500,100));
    outputPane.add(ex_label);
    outputPane.add(ex_type_box);
    outputPane.add(empty_label_1);
    outputPane.add(input_label);
    outputPane.add(loc_type_box);
    outputPane.add(empty_label_2);
    
    optionPane.add(outputPane,BorderLayout.NORTH);  
    optionPane.add(buttonPane,BorderLayout.CENTER);
    optionPane.add(statusBar,BorderLayout.SOUTH);     
         
	JPanel mainPane = new JPanel(new BorderLayout());
	
    mainPane.add(optionPane, BorderLayout.SOUTH);
    //mainPane.add(taskTextPane, BorderLayout.NORTH);
    	
    mainFrame = new JFrame("ENVIRONMENT");
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setContentPane(mainPane);
    mainFrame.setPreferredSize(new Dimension(500, 200));
    mainFrame.setLocation(pos_x, pos_y);
    mainFrame.pack();
    mainFrame.setVisible(true);
    
    connect();
}

private void sendString(String s) {
    synchronized (toSend) {    	
       toSend.append(s + "\n");
       }
 }


protected void connect() {
    try {
      socket = new Socket("", port);
      thread = new Thread(this);
   	  thread.start();
    } catch(Exception ex) {
    	System.out.println("Eccezione");
    }
}

protected void stateDisconnect() {
    try {
      if(socket!=null && socket.isConnected()) socket.close();
    }catch(Exception ex) {}
    if(thread!=null) thread.interrupt();
  }

public void run() {
	 System.out.println("*** JAVA-Environment -- The '" + service + "' is logging in to the server with the command : " + "/user "+service);
	 toSend.append("/user "+service+"\r\n"); 
	try {
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    out = new PrintWriter(socket.getOutputStream(), true);     
		}
		catch (IOException e) {
			System.out.println("Eccezione");
		}
    try {
      while(true) {
        parseMessage();
      }
    } catch(Exception ex) {
      stateDisconnect();
    }
  }

private void parseMessage() {
    while (true) {
         switch (connectionStatus) {
         case DISCONNECTED:
                changeStatusTS(BEGIN_CONNECT, true);
                changeStatusTS(CONNECTED, true);
                break;
         case CONNECTED:
             try {
                // Send data
            	if (toSend.length() != 0) {
            		  out.print(toSend); 
                      out.flush();
                   toSend.setLength(0);
                   toSend = new StringBuffer();
                   changeStatusTS(NULL, true);
                }
                // Receive data
                if (in.ready()) {
             	  s = in.readLine();
             	  if(s.substring(0)!="")
                  System.out.println("*** JAVA-Environment -- " + service + " has received from the aPMS the command :" + s);
                   if ((s != null) &&  (s.length() != 0)) {
                      // Check if it is the end of a trasmission
                   if (s.equalsIgnoreCase("end")) {
                         changeStatusTS(DISCONNECTED, true);
                      }
                      else if(s.startsWith("release")  && adaptivity==true ) {
                      	    taskArea.setText(" No Task Assigned");
                  	  }                     
                      else if(s.startsWith("adaptStart")) {
                    	  adaptivity=true;
                    	  launchButton.setEnabled(false);
                    	  mainFrame.repaint();
                    	  
                    	  /* update the simulator environment 
                    	  if(world!=null){
				          world.appendInfo("AdaptStart");
                    	  }
			       		  /* ************************ */

                      }
                      else if(s.startsWith("adaptFinish")) {
                     	 adaptivity=false;
                     	 launchButton.setEnabled(true);
                         mainFrame.repaint();
                   	
                   	if(world!=null){
                   	  	  /* update the simulator environment */
                   	  	   world.appendInfo("AdaptFinish");
                   	  	  /* ************************ */       	  
                    }
                      }
                   	else if(s.startsWith("end")) {
                    	  
                    	  System.out.println(service + " : " + service + " is sending : abort");
                    	  sendString("abort");
                    	  
                    	  new Cronometro(mainFrame,"<html>The process has been<br></br>ended correctly</html>","close",true);
                		  try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
		
							e.printStackTrace();
						}
                    	
                    	System.exit(0);
                        }
                      

                      else {
                         //appendToChatBox("INCOMING: " + s + "\n");
                         changeStatusTS(NULL, true);
                      }
                   
                }
             }
             }
             catch (IOException e) {
                cleanUp();
                changeStatusTS(DISCONNECTED, false);
             }
             break;
	 		}
	 }
}
	 
		private void changeStatusTS(int newConnectStatus, boolean noError) {
		      if (newConnectStatus != NULL) {
		         connectionStatus = newConnectStatus;
		      }
		      // If there is no error, display the appropriate status message
		      if (noError) {
		         statusString = statusMessages[connectionStatus];
		      }
		      // Otherwise, display error message
		      else {
		         statusString = statusMessages[NULL];
		      }
		      // Call the run() routine (Runnable interface) on the
		      // error-handling and GUI-update thread
		      	refresh();
		   }
	   
	   public void refresh() {
		   switch (connectionStatus) {
		   	   case BEGIN_CONNECT:
		   		statusColor.setBackground(Color.orange);
		   		statusField.setText(statusString);
		   		break;
		   	   case CONNECTED:
			   	statusColor.setBackground(Color.green);
			   	statusField.setText(statusString);
			   	break;
		   	   case DISCONNECTED:
				statusColor.setBackground(Color.red);
			   	statusField.setText(statusString);
			 	break;
		   }
		   mainFrame.repaint();
	   }
	   
	   // Cleanup for disconnect
	   private  void cleanUp() {
	      try {
	         if (hostServer != null) {
	            hostServer.close();
	            hostServer = null;
	         }
	      }
	      catch (IOException e) { hostServer = null; }

	      try {
	         if (socket != null) {
	            socket.close();
	            socket = null;
	         }
	      }
	      catch (IOException e) { socket = null; }

	      try {
	         if (in != null) {
	            in.close();
	            in = null;
	         }
	      }
	      catch (IOException e) { in = null; }

	      if (out != null) {
	         out.close();
	         out = null;
	      }
	   }
	   
	  	
}