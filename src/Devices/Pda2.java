package Devices;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;

import com.mxgraph.smartml.view.MainFrame;


public class Pda2 implements Runnable {
	   
	   public World world = null;	   
	   
	   private String service;
	   private Thread thread = null;
	   private int port = 5555;
	   
	   private Vector<Workitem> workList = null;
	   private Vector<Workitem> ad_workList = null; // Assegnato in caso di adattività
	   
	   private Workitem workitem = null;
	   
	 //Informazioni associate ad un workitem.
	   private String task = null;
	   private String id = null;
	   private Vector input = null;
	   private Vector exoutput = null;
	   private Vector wrk_types = null;

	 //Informazioni associate ad un workitem durante la fase di adaptation.
	   private String ad_task = null;
	   private String ad_id = null;
	   private Vector ad_input = null;
	   private Vector ad_exoutput = null;
	   private Vector ad_wrk_types = null;
	   
	 //Stringa contenente i comandi da inviare al PMS.  
	   private String s = new String("");
	   private StringBuffer toSend = new StringBuffer("");
	   private StringBuffer toAppend = new StringBuffer("");
	
	   private  JFrame mainFrame = null;
	   private  JPanel statusBar = null;
	   private  JLabel statusField = null;
	   private  JTextField statusColor = null;
	   private int pos_x = 0;
	   private int pos_y = 0;
	   
	  // Stato di connessione di un device //
	   private final int NULL = 0;
	   private final int DISCONNECTED = 1;
	   private final int DISCONNECTING = 2;
	   private final int BEGIN_CONNECT = 3;
	   private final int CONNECTED = 4;
	   
	   private int connectionStatus = DISCONNECTED;
	   
	   private final String statusMessages[] = {
			      " Error! Could not connect!", " Disconnected",
			      " Disconnecting...", " Connecting...", " Connected"
			   };
	   
	   private String statusString = statusMessages[connectionStatus];
	   
	   /*Tipi di dato predefiniti*/
	   private String boolean_type[] = {"true","false"};
	   private String integer_type[] = {"integer_type"};
	   
	   /*Tipi di dato associati all'esempio - devono essere definiti ad ogni esecuzione del programma*/
	   private String status_type[] = {"ok","fire","debris"};
	   private String location_type[] = {"loc00","loc01","loc02","loc03","loc10","loc13","loc20","loc23","loc30","loc31","loc32","loc33"};
	   private String hum_type[] = {"dry","normalhum","wet"};
	   private String noize_type[] = {"whisper","normalnoize","stereo","ambulance"};	   
	   private String temp_type[] = {"cold","chilly","normaltemp","warm"};
	   private String mq3_type[] = {"normalmq3","high","extra"};
	   private String hcho_type[] = {"normalhcho","voc","dangerhcho"};
	   private String mq2_type[] = {"normalmq2","smokemq2","dangermq2"};
	   private String mq5_type[] = {"normalmq5","smokemq5","dangermq5"};
	   
	   /*Vector contenente tutti i tipi di dato*/
	   private Vector allDataTypes = new Vector();
	      
	   private JScrollPane scrollOutputArea = null;
	   private JTextArea taskArea = null;
	   private String oldTaskArea = null;
	   
	   private JButton beginButton = null;
	   private JButton endButton = null;
	   
	   private boolean beginButtonEnabled = false;
	   private boolean endButtonEnabled = false;
	   private boolean outputListEnabled = false;
	   
	   private boolean beginButtonEnabled_old = false;
	   private boolean endButtonEnabled_old = false;
	   private boolean outputListEnabled_old = false;
	   
	   private boolean adaptivity = false;
	   
	   private Vector outputListVector = new Vector();
	   //public JComboBox outputList = null;
	   private String hostIP = "localhost";

	   private boolean isHost = true;
	   private ServerSocket hostServer = null;
	   private Socket socket = null;
	   private BufferedReader in = null;
	   private PrintWriter out = null;
	   
	   private JPanel outputPane = new JPanel();
	   	   	   
	   private ActionListener buttonListener = null;
	   
	   public Pda2(String srv, int x, int y, World w) {
		   
		   //set the name of the service
		   service=srv; 
		   
		   //set the position of the GUI on the screen (x-axis and y-axis)
		   pos_x = x; 
		   pos_y = y; 
		   
		   //set the 'world' environment (valid only for the Emergency Management example - in the other cases it must be set to null)
		   world = w;
		   
		   //initialize the GUI of the PDA of the service
		   initGUI();
		   
		   //connect the PDA of the service to the socket 5555, where the SmartPM engine sends the Indigolog primitive actions
		   connect();
		   
		   //Riempiamo il Vector contenente tutti i tipi di dato
		   allDataTypes.add(boolean_type);
		   allDataTypes.add(integer_type);
		   allDataTypes.add(status_type);
		   allDataTypes.add(location_type);	   
		   allDataTypes.add(hum_type);
		   allDataTypes.add(noize_type);	
		   allDataTypes.add(mq2_type);	
		   allDataTypes.add(mq3_type);	
		   allDataTypes.add(mq5_type);	
		   allDataTypes.add(temp_type);
		   allDataTypes.add(hcho_type);
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
		    
		    taskArea = new JTextArea();
		    taskArea.setLineWrap(false);
		    taskArea.setEditable(false);
		    taskArea.append(" No Task Assigned");
		    JScrollPane taskTextPane = new JScrollPane(taskArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		    taskTextPane.setPreferredSize(new Dimension(120,140));
		    Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		    Border loweredbevel = BorderFactory.createLoweredBevelBorder();
			Border compound = BorderFactory.createCompoundBorder(raisedbevel,loweredbevel);
			taskTextPane.setBorder(compound);
			JPanel optionPane = new JPanel();
			optionPane.setLayout(new BorderLayout());
			JPanel buttonPane = new JPanel();
			beginButton = new JButton("Start It");
			beginButton.setActionCommand("StartTask");
		    beginButton.setEnabled(false);
		    beginButtonEnabled = false;
		    endButton = new JButton("EndTask");
		    endButton.setEnabled(false);
		    endButtonEnabled = false;
		    buttonPane.add(beginButton);
		    buttonPane.add(endButton);
		     
		    //Management of the beginButton (readyToStart) and of the endButton (finishedTask)
		    buttonListener = new ActionListener() {		         
			    public void actionPerformed(ActionEvent e) {
			    	  if(e.getActionCommand().equals("StartTask")) {
					        if(adaptivity==true){
				       		String s = "readyToStart("+service+","+ad_id+","+ad_task+")";
					        System.out.println("*** JAVA-PDA2 : Service '" + service + "' is sending the command : "+s);
					        sendString(s);
				      	   }
					       else if (adaptivity==false) {
					       	String s = "readyToStart("+service+","+id+","+task+")";
					        System.out.println("*** JAVA-PDA2 -- Service '" +  service + "' is sending the command : "+s);
					        sendString(s);
					       }
			       	  }       	  
			       	  else if (e.getActionCommand().equals("EndTask")) {
			       		String s = new String();
			       		boolean integerFormat = true;
			       		 
			   			String output = "[";
			       			 
			       		Iterator it = outputListVector.iterator();
			            	while(it.hasNext()){
			           		  Object o = it.next();
			           		  if(o.getClass().getName().equalsIgnoreCase("javax.swing.JComboBox")) {
			           		  JComboBox box = (JComboBox) o;
			           		  if(it.hasNext())		  
			           		  output=output+(String)box.getSelectedItem()+",";
			           		  else
			           		  output=output+(String)box.getSelectedItem();
			           		  }
			           		  else if(o.getClass().getName().equalsIgnoreCase("javax.swing.JTextField")) { //L'output i-esimo è un intero, perciò JTextField
			           		  JTextField field = (JTextField) o; 
			           		  if(it.hasNext())		  
			              	  output=output+(String)field.getText()+",";
			              	  else
			              	  output=output+(String)field.getText();
			           		  try{
			           		  Integer.parseInt(field.getText());
			           		  }
			           		  catch(NumberFormatException ex) 
			           		  {
			           			integerFormat = false;
			           		  }
			           		  }           		  
			           	  }
			       		  output = output+"]";
			       		  if(adaptivity==false) {
			       			  s = "finishedTask("+service+","+id+","+task+","+output+")";
			       		  }
			       		  else {
			       			  s = "finishedTask("+service+","+ad_id+","+ad_task+","+output+")";  
			       		  }        	  

/* ************************ UPDATE the simulator environment - valid only for the emergency management domain ************************ */
			
			       		  if(world!=null){
			       			 /*
			       			 if(adaptivity==false)
			       				world.appendInfo("finishedTask("+service+","+task+","+input.elementAt(0)+","+output+")");
			       			 else   	 {		
			       			 	world.appendInfo("finishedTask("+service+","+ad_task+","+input.elementAt(0)+","+output+")");
			      			 }
			         		 */
				           	 if(adaptivity==false) {
				       			if(task.equalsIgnoreCase("go") || task.equalsIgnoreCase("move")){
				       				String[] spl = output.split("\\]");
				       				String[] spl_1 = spl[0].split("\\[");
				      		    	world.changeServicePosition(service, spl_1[1]);
				      		    }
				           	 }
				       		 else  {
				       			if(ad_task.equalsIgnoreCase("go") || ad_task.equalsIgnoreCase("move")){
				       				String[] spl = output.split("\\]");
				       				String[] spl_1 = spl[0].split("\\[");
				      		    	world.changeServicePosition(service, spl_1[1]);
				      		    }
				       		 }
			      		 }
			      		    
			/* ********************************************************************************************************** */
			       		  		         			
			       		  if(integerFormat){
			       		  
			       		  System.out.println("*** JAVA-PDA2 -- Service '" + service + "' is sending the command : "+s);
			       		  sendString(s);
			                            
			        	  outputListVector.removeAllElements();
			        	  endButton.setEnabled(false);
			        	  endButtonEnabled = false;
			        	  outputListEnabled=false;
			        	  outputPane.removeAll();
			        	  outputPane.revalidate();
			       	  	  }
			       		  else{
			       			JOptionPane.showMessageDialog(null, "alert", "alert", JOptionPane.ERROR_MESSAGE);
			       		  }
				  }
			         
			         }
		         };
		    
		    beginButton.addActionListener(buttonListener);
		    endButton.addActionListener(buttonListener);
		    outputPane = new JPanel();
		    outputPane.setLayout(new FlowLayout());
		    outputListEnabled = false;
		    
		    outputPane.setPreferredSize(new Dimension(20,200));
		    
		    JScrollPane outputScrollPane = new JScrollPane(outputPane,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		 
		     
		    optionPane.add(buttonPane,BorderLayout.NORTH);
		    optionPane.add(outputScrollPane,BorderLayout.CENTER);     
		         
			JPanel mainPane = new JPanel(new BorderLayout());
			
		    mainPane.add(statusBar, BorderLayout.SOUTH);
		    mainPane.add(optionPane, BorderLayout.CENTER);
		    mainPane.add(taskTextPane, BorderLayout.NORTH);
		
		    mainFrame = new JFrame("SERVICE : "+service);
		    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    mainFrame.setContentPane(mainPane);
		    mainFrame.setPreferredSize(new Dimension(280, 340));
		    mainFrame.setLocation(pos_x, pos_y);
		    mainFrame.pack();
		    mainFrame.doLayout();
		    mainFrame.setVisible(true);
		    
		    
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
    	ex.printStackTrace();
    }
}

protected void stateDisconnect() {
    try {
      if(socket!=null && socket.isConnected()) socket.close();
    }catch(Exception ex) {}
    if(thread!=null) thread.interrupt();
  }

public void run() {
	 System.out.println("*** JAVA-PDA2 -- Service '" + service + "' is logging in to the server with the command : " + "/user "+service);
	 toSend.append("/user "+service+"\r\n"); 
	 
	try {
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    out = new PrintWriter(socket.getOutputStream(), true);     
		}
		catch (IOException e) {
			System.out.println("Exception found");
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
             	  if(s.substring(0)!="") {
             		 System.out.println("*** JAVA-PDA2 -- Service '" + service + "' has received from the aPMS the command : " + s);
             	  }
                  if ((s != null) &&  (s.length() != 0)) {
                      // Check if it is the end of a trasmission
                   if (s.equalsIgnoreCase("end")) {
                         changeStatusTS(DISCONNECTED, true);
                      }
                      
                      /* ---------------- Adattività ------------------ */

                    	  if(s.startsWith("assign") && adaptivity==true) {
                        	  ad_workList = new Vector<Workitem>();
                        	  String [] s1 = s.split("workitem");
                        	  int arrlenght = s1.length;
                        	                  	  
                        	  for(int i = 1;i<=arrlenght-1;i++){
                         		 if(i==arrlenght-1){ //Ultimo workitem della worklist
                         			 String [] sfinal = s1[i].split("\\(");
                         			 int slenght = sfinal[1].length();                    			                    			 
                         			 String sfinal1 = sfinal[1].substring(0,slenght-3);
                         			 String [] workitemArray = sfinal1.split(",");
                         			 String workitemTASK = workitemArray[0];
                         			 String workitemID = workitemArray[1];
                         			 int z = 0;                    			
                         			 Vector workitemINPUTLIST = new Vector();
                         			 Vector workitemOUTPUTLIST = new Vector();
                         			 	for(z = 2;z<=workitemArray.length-1;z++){
                         			 		if(workitemArray[z].contains("[") && workitemArray[z].contains("]")){
                         			 			if(workitemArray[z].substring(1,workitemArray[z].length()-1).equalsIgnoreCase(""))
                         			 				workitemINPUTLIST.addElement("no inputs");	
                         			 			else
                         			 				workitemINPUTLIST.addElement(workitemArray[z].substring(1,workitemArray[z].length()-1));
                         			 			z++;
                         			 			break;
                         			 		}
                         			 		else if(workitemArray[z].contains("[")){
                         			 			workitemINPUTLIST.addElement(workitemArray[z].substring(1,workitemArray[z].length()));
                         			 		}
                         			 		else if (workitemArray[z].contains("]")){
                         			 			workitemINPUTLIST.addElement(workitemArray[z].substring(0,workitemArray[z].length()-1));
                         			 			z++;
                         			 			break;
                         			 		}
                         			 		else {
                         			 			workitemINPUTLIST.addElement(workitemArray[z].substring(0,workitemArray[z].length()));
                         			 		}
                         			 	}
                         			    while(z<=workitemArray.length-1) {
                         			 		if(workitemArray[z].contains("[") && workitemArray[z].contains("]")){
                         			 			if(workitemArray[z].substring(1,workitemArray[z].length()-1).equalsIgnoreCase(""))
                         			 				workitemOUTPUTLIST.addElement("no outputs");
                         			 			else
                         			 				workitemOUTPUTLIST.addElement(workitemArray[z].substring(1,workitemArray[z].length()-1));
                         			 			break;
                         			 		}
                         			 		else if(workitemArray[z].contains("[")){
                         			 			workitemOUTPUTLIST.addElement(workitemArray[z].substring(1,workitemArray[z].length()));
                         			 			z++;
                         			 		}
                         			 		else if (workitemArray[z].contains("]")){
                         			 			workitemOUTPUTLIST.addElement(workitemArray[z].substring(0,workitemArray[z].length()-1));
                         			 			break;
                         			 		}
                         			 		else {
                         			 			workitemOUTPUTLIST.addElement(workitemArray[z].substring(0,workitemArray[z].length()));
                         			 			z++;
                         			 		}
                         			 	} 	
                         			    
                         			    ad_workList.add(new Workitem(workitemTASK, workitemID, workitemINPUTLIST, workitemOUTPUTLIST, allDataTypes));
                         			 }
                         		 else{
                         			 String [] sint = s1[i].split("\\(");
                         			 int slenght = sint[1].length();                    			                    			 
                         			 String sint1 = sint[1].substring(0,slenght-2);
                         			 String [] workitemArray = sint1.split(",");
                         			 
                         			 String workitemTASK = workitemArray[0];
                         			 String workitemID = workitemArray[1];
                         			 int z = 0;                    			
                         			 Vector workitemINPUTLIST = new Vector();
                         			 Vector workitemOUTPUTLIST = new Vector();
                         			 	for(z = 2;z<=workitemArray.length-1;z++){
                         			 		if(workitemArray[z].contains("[") && workitemArray[z].contains("]")){
                         			 			if(workitemArray[z].substring(1,workitemArray[z].length()-1).equalsIgnoreCase(""))
                         			 				workitemINPUTLIST.addElement("no inputs");	
                         			 			else
                         			 				workitemINPUTLIST.addElement(workitemArray[z].substring(1,workitemArray[z].length()-1));
                         			 			z++;
                         			 			break;
                         			 		}
                         			 		else if(workitemArray[z].contains("[")){
                         			 			workitemINPUTLIST.addElement(workitemArray[z].substring(1,workitemArray[z].length()));
                         			 		}
                         			 		else if (workitemArray[z].contains("]")){
                         			 			workitemINPUTLIST.addElement(workitemArray[z].substring(0,workitemArray[z].length()-1));
                         			 			z++;
                         			 			break;
                         			 		}
                         			 		else {
                         			 			workitemINPUTLIST.addElement(workitemArray[z].substring(0,workitemArray[z].length()));
                         			 		}
                         			 	}
                         			    while(z<=workitemArray.length-1) {
                         			 		if(workitemArray[z].contains("[") && workitemArray[z].contains("]")){
                         			 			if(workitemArray[z].substring(1,workitemArray[z].length()-1).equalsIgnoreCase(""))
                         			 				workitemOUTPUTLIST.addElement("no outputs");
                         			 			else
                         			 				workitemOUTPUTLIST.addElement(workitemArray[z].substring(1,workitemArray[z].length()-1));
                         			 			break;
                         			 		}
                         			 		else if(workitemArray[z].contains("[")){
                         			 			workitemOUTPUTLIST.addElement(workitemArray[z].substring(1,workitemArray[z].length()));
                         			 			z++;
                         			 		}
                         			 		else if (workitemArray[z].contains("]")){
                         			 			workitemOUTPUTLIST.addElement(workitemArray[z].substring(0,workitemArray[z].length()-1));
                         			 			break;
                         			 		}
                         			 		else {
                         			 			workitemOUTPUTLIST.addElement(workitemArray[z].substring(0,workitemArray[z].length()));
                         			 			z++;
                         			 		}
                         			 	} 		          
                         			    
                         			    ad_workList.add(new Workitem(workitemTASK, workitemID, workitemINPUTLIST, workitemOUTPUTLIST, allDataTypes));
                         	 
                         		 }
                         	  }
                        	  
                        	  workitem = ad_workList.elementAt(0);
                        	  ad_task = workitem.getTask();
                        	  ad_id = workitem.getId();
                        	  ad_input = workitem.getInputsList();
                        	  ad_exoutput = workitem.getOutputsList();
                        	  ad_wrk_types = workitem.getOutputTypes();
                        	  ad_workList.removeElementAt(0);
                        	  
                        	  JOptionPane.showMessageDialog(taskArea, "A new task has just\nbeen assigned to you");
                        	  taskArea.setText("");
                        	  taskArea.append(" TASK = "+ad_task+"\n");
                        	  taskArea.append(" INPUT = "+ad_input+"\n");
                        	  taskArea.append(" EXPECTED OUTPUT = "+ad_exoutput+"\n"+"\n");
                        	  beginButton.setEnabled(true);
                        	  beginButtonEnabled=true;
                        	  mainFrame.repaint();                      	  
                        	  
                        	  
                        	  /*
                        	  if(service!="robot"){
                        	  JOptionPane.showMessageDialog(taskArea, "A new task has just\nbeen assigned to you");
                        	  taskArea.setText("");
                        	  taskArea.append(" TASK = "+ad_task+"\n");
                        	  taskArea.append(" INPUT = "+ad_input+"\n");
                        	  taskArea.append(" EXPECTED OUTPUT = "+ad_exoutput+"\n"+"\n");
                        	  beginButton.setEnabled(true);
                        	  beginButtonEnabled=true;
                        	  mainFrame.repaint();     
                        	  
                        	  /* update the simulator environment 
                        	  if(world!=null){
					          world.appendInfo("assign("+service+","+ad_task+","+ad_input+")");
                        	  }
                        	  }
					                                 	  
                        	  else{ //if service == robot
                        		  new Cronometro(mainFrame,"<html>A new task has just<br></br>been assigned to you</html>","start",true);
                        		  
                        		  try {
									Thread.sleep(6000);
								} catch (InterruptedException e) {
				
									e.printStackTrace();
								}
	                        	  taskArea.setText("");
	                        	  taskArea.append(" TASK = "+ad_task+"\n");
	                        	  taskArea.append(" INPUT = "+ad_input+"\n");
	                        	  taskArea.append(" EXPECTED OUTPUT = "+ad_exoutput+"\n"+"\n");
	                        	  beginButton.setEnabled(false);
	                        	  beginButtonEnabled=false;
	                        	  mainFrame.repaint();
	                        	  
	                        	  /* update the simulator environment 
	                        	  if(world!=null){
						          world.appendInfo("assign("+service+","+ad_task+","+ad_input+")");
	                        	  }
						          /* ************************ 
	                        	  
								String s = "readyToStart("+service+","+ad_id+","+ad_task+")";
						        System.out.println(service + " : " + service + " is sending "+s);
						  		        
						        sendString(s);
						        
                      		  try {
									Thread.sleep(5000);
								} catch (InterruptedException e) {
								
									e.printStackTrace();
								}
								new Cronometro(mainFrame,"<html>The task is<br></br>under execution</html>","end",true);
                        	  }*/
                    	  }
                          
                          else if(s.startsWith("start") && adaptivity==true ) {
                        	  
                        	  taskArea.append("UNDER EXECUTION");
                        	  endButton.setEnabled(true);
                        	  endButtonEnabled=true;
                            	  beginButton.setEnabled(false);
                            	  beginButtonEnabled = false;
                            	  
                            	  outputListVector.removeAllElements();           	  
                            	                     	  
                            	  Iterator it_2 = ad_wrk_types.iterator();                    		  
                            		
                            	  while(it_2.hasNext()){
                            		  String[] singletype = (String[]) it_2.next();
                            		  if(singletype[0].equalsIgnoreCase("integer_type")){
                            			  outputListVector.addElement(new JTextField("Integer"));
                            		  }
                            		  else if(singletype[0].equalsIgnoreCase("no_outputs")){
                            			  outputListVector.addElement(new JLabel("NO OUTPUTS REQUIRED"));
                            		  }
                            		  else {
                            			  JComboBox cbox = new JComboBox();
                            			  for (int i=0; i<singletype.length; i++){
                            				  cbox.addItem(singletype[i]);
                            			  }
                            			  outputListVector.addElement(cbox);
                            			  }
                            	  }
                    	  
                            	  Iterator it = outputListVector.iterator();
                            	 
                        		  int con = 1;
                        		  outputPane.removeAll();
                            	  while(it.hasNext()){
                            		  Object o = it.next();     
                            		  if(o.getClass().getName().equalsIgnoreCase("javax.swing.JComboBox")) {
                            		  JComboBox box = (JComboBox) o;
                            		  box.setEnabled(true);
                            		  box.setPreferredSize(new Dimension(115, 25));
                            		  outputPane.add(new JLabel("OUTPUT #"+con));
                            		  outputPane.add(box);                    		  
                            		  }
                            		  else if (o.getClass().getName().equalsIgnoreCase("javax.swing.JLabel")) {
                            		  JLabel label = (JLabel) o;
                            		  label.setEnabled(true);
                            		  outputPane.add(label); 
                            		  }
                            		  else //L'output i-esimo è un intero, perciò JTextField
                            		  {
                            		  JTextField field = (JTextField) o; 
                            		  field.setEnabled(true);
                            		  field.setPreferredSize(new Dimension(115, 25));
                            		  outputPane.add(new JLabel("OUTPUT #"+con));
                            		  outputPane.add(field);
                            		  }
                            		  con++;
                            	  }
                        		  outputPane.revalidate();
                            	  /* update the simulator environment 
                            	  if(world!=null){
        				          world.appendInfo("start("+service+","+task+")");
                            	  }
        			       		  /* ************************ */
                            	                   	  
                            	  outputListEnabled = true;
                            	  mainFrame.repaint();
                        	  }
                        	  /*
                        	  else { //if service == robot
                        		  taskArea.append("UNDER EXECUTION");
                        		  endButton.setEnabled(false);
                        		  endButtonEnabled=false;
                            	  beginButton.setEnabled(false);
                            	  beginButtonEnabled = false;
                            	  outputListVector.removeAllElements();
                            	  
                            	  /* update the simulator environment 
                            	  if(world!=null){
        				          world.appendInfo("start("+service+","+ad_task+")");
                            	  }
        				          /* ************************ 
                            	  
                            	  /* in caso di adattività, il task può solo terminare correttamente 
                            	  //outputList.addItem(ad_exoutput);
                            	  Iterator it = outputListVector.iterator();
                            	  	 while(it.hasNext())
                            	  		 ((JComboBox) it.next()).setEnabled(false);
                            	  
                            	  outputListEnabled = false;
                            	  
                            	  try {
  									Thread.sleep(7000);
  								} catch (InterruptedException e) {
  								
  									e.printStackTrace();
  								}
                            	  
                            	  String s = "finishedTask("+service+","+ad_id+","+ad_task+","+ad_input+","+ad_exoutput+","+ad_exoutput+")";
  						          System.out.println(service + " : " + service + " is sending "+s);
  						          
  						        /* update the simulator environment 
  						        if(world!=null){
  						          world.appendInfo("finishedTask("+service+","+ad_task+","+ad_input+","+ad_exoutput+")");
    				       		    if(ad_task.equalsIgnoreCase("go"))
    				       		    	world.changeServicePosition(service, ad_exoutput);
  						      }
    				       		    /* ************************ 
  						          
  						          sendString(s);
  						                              	  
                            	  mainFrame.repaint();
                        }*/
                 	  
                    	  
                          else if(s.startsWith("ackCompl") && adaptivity==true) {
                        	  outputListVector.removeAllElements();
                        	  endButton.setEnabled(false);
                        	  endButtonEnabled = false;
                        	  Iterator it = outputListVector.iterator();
                        	  	 while(it.hasNext())
                        	  		 ((JComboBox) it.next()).setEnabled(false);
                        	 
                        	  outputListEnabled = false;                    	  
                        	  
                        	  if(ad_workList.size()>0){
                            	  Workitem w = ad_workList.elementAt(0);
                            	  ad_task = w.getTask();
                            	  ad_id = w.getId();
                            	  ad_input = w.getInputsList();
                            	  ad_exoutput = w.getOutputsList();
                            	  ad_workList.removeElementAt(0);
                            	  JOptionPane.showMessageDialog(taskArea, "A new task has just\nbeen assigned to you");
                            	  taskArea.setText("");
                            	  
                            	  taskArea.append(" TASK = "+ad_task+"\n");
                            	  taskArea.append(" INPUT = "+ad_input+"\n");
                            	  taskArea.append(" EXPECTED OUTPUT = "+ad_exoutput+"\n"+"\n");
                            	  beginButton.setEnabled(true);
                            	  beginButtonEnabled = true;
                            	  mainFrame.repaint();
                            	  }
                          }
                          else if(s.startsWith("release")  && adaptivity==true ) {
                      	    taskArea.setText(" No Task Assigned");
                  	  }                     
                      /* ---------------- Fine Adattività ------------------- */
                      
                   	  /* ---------------- Normale Esecuzione ------------------- */
                      else if(s.startsWith("assign")) {
                    	                      	  
                    	  workList = new Vector<Workitem>();
                    	  String [] s1 = s.split("workitem");
                    	  int arrlenght = s1.length;
                    	  for(int i = 1;i<=arrlenght-1;i++){
                    		 if(i==arrlenght-1){ //Ultimo workitem della worklist
                    			 String [] sfinal = s1[i].split("\\(");
                    			 int slenght = sfinal[1].length();                    			                    			 
                    			 String sfinal1 = sfinal[1].substring(0,slenght-3);
                    			 String [] workitemArray = sfinal1.split(",");
                    			 String workitemTASK = workitemArray[0];
                    			 String workitemID = workitemArray[1];
                    			 int z = 0;                    			
                    			 Vector workitemINPUTLIST = new Vector();
                    			 Vector workitemOUTPUTLIST = new Vector();
                    			 	for(z = 2;z<=workitemArray.length-1;z++){
                    			 		if(workitemArray[z].contains("[") && workitemArray[z].contains("]")){
                    			 			if(workitemArray[z].substring(1,workitemArray[z].length()-1).equalsIgnoreCase(""))
                    			 				workitemINPUTLIST.addElement("no inputs");	
                    			 			else
                    			 				workitemINPUTLIST.addElement(workitemArray[z].substring(1,workitemArray[z].length()-1));
                    			 			z++;
                    			 			break;
                    			 		}
                    			 		else if(workitemArray[z].contains("[")){
                    			 			workitemINPUTLIST.addElement(workitemArray[z].substring(1,workitemArray[z].length()));
                    			 		}
                    			 		else if (workitemArray[z].contains("]")){
                    			 			workitemINPUTLIST.addElement(workitemArray[z].substring(0,workitemArray[z].length()-1));
                    			 			z++;
                    			 			break;
                    			 		}
                    			 		else {
                    			 			workitemINPUTLIST.addElement(workitemArray[z].substring(0,workitemArray[z].length()));
                    			 		}
                    			 	}
                    			    while(z<=workitemArray.length-1) {
                    			 		if(workitemArray[z].contains("[") && workitemArray[z].contains("]")){
                    			 			if(workitemArray[z].substring(1,workitemArray[z].length()-1).equalsIgnoreCase(""))
                    			 				workitemOUTPUTLIST.addElement("no outputs");
                    			 			else
                    			 				workitemOUTPUTLIST.addElement(workitemArray[z].substring(1,workitemArray[z].length()-1));
                    			 			break;
                    			 		}
                    			 		else if(workitemArray[z].contains("[")){
                    			 			workitemOUTPUTLIST.addElement(workitemArray[z].substring(1,workitemArray[z].length()));
                    			 			z++;
                    			 		}
                    			 		else if (workitemArray[z].contains("]")){
                    			 			workitemOUTPUTLIST.addElement(workitemArray[z].substring(0,workitemArray[z].length()-1));
                    			 			break;
                    			 		}
                    			 		else {
                    			 			workitemOUTPUTLIST.addElement(workitemArray[z].substring(0,workitemArray[z].length()));
                    			 			z++;
                    			 		}
                    			 	} 	
                    			    
                    			    workList.add(new Workitem(workitemTASK, workitemID, workitemINPUTLIST, workitemOUTPUTLIST, allDataTypes));
                    			 }
                    		 else{
                    			 String [] sint = s1[i].split("\\(");
                    			 int slenght = sint[1].length();                    			                    			 
                    			 String sint1 = sint[1].substring(0,slenght-2);
                    			 String [] workitemArray = sint1.split(",");
                    			 
                    			 String workitemTASK = workitemArray[0];
                    			 String workitemID = workitemArray[1];
                    			 int z = 0;                    			
                    			 Vector workitemINPUTLIST = new Vector();
                    			 Vector workitemOUTPUTLIST = new Vector();
                    			 	for(z = 2;z<=workitemArray.length-1;z++){
                    			 		if(workitemArray[z].contains("[") && workitemArray[z].contains("]")){
                    			 			if(workitemArray[z].substring(1,workitemArray[z].length()-1).equalsIgnoreCase(""))
                    			 				workitemINPUTLIST.addElement("no inputs");	
                    			 			else
                    			 				workitemINPUTLIST.addElement(workitemArray[z].substring(1,workitemArray[z].length()-1));
                    			 			z++;
                    			 			break;
                    			 		}
                    			 		else if(workitemArray[z].contains("[")){
                    			 			workitemINPUTLIST.addElement(workitemArray[z].substring(1,workitemArray[z].length()));
                    			 		}
                    			 		else if (workitemArray[z].contains("]")){
                    			 			workitemINPUTLIST.addElement(workitemArray[z].substring(0,workitemArray[z].length()-1));
                    			 			z++;
                    			 			break;
                    			 		}
                    			 		else {
                    			 			workitemINPUTLIST.addElement(workitemArray[z].substring(0,workitemArray[z].length()));
                    			 		}
                    			 	}
                    			    while(z<=workitemArray.length-1) {
                    			 		if(workitemArray[z].contains("[") && workitemArray[z].contains("]")){
                    			 			if(workitemArray[z].substring(1,workitemArray[z].length()-1).equalsIgnoreCase(""))
                    			 				workitemOUTPUTLIST.addElement("no outputs");
                    			 			else
                    			 				workitemOUTPUTLIST.addElement(workitemArray[z].substring(1,workitemArray[z].length()-1));
                    			 			break;
                    			 		}
                    			 		else if(workitemArray[z].contains("[")){
                    			 			workitemOUTPUTLIST.addElement(workitemArray[z].substring(1,workitemArray[z].length()));
                    			 			z++;
                    			 		}
                    			 		else if (workitemArray[z].contains("]")){
                    			 			workitemOUTPUTLIST.addElement(workitemArray[z].substring(0,workitemArray[z].length()-1));
                    			 			break;
                    			 		}
                    			 		else {
                    			 			workitemOUTPUTLIST.addElement(workitemArray[z].substring(0,workitemArray[z].length()));
                    			 			z++;
                    			 		}
                    			 	} 		          
                    			    
                    			    workList.add(new Workitem(workitemTASK, workitemID, workitemINPUTLIST, workitemOUTPUTLIST, allDataTypes));
                    	 
                    		 }
                    	  }
                   	  
                    	  workitem = workList.elementAt(0);
                    	  task = workitem.getTask();
                    	  id = workitem.getId();
                    	  input = workitem.getInputsList();
                    	  exoutput = workitem.getOutputsList();
                    	  wrk_types = workitem.getOutputTypes();
                    	  workList.removeElementAt(0);
                    	  JOptionPane.showMessageDialog(taskArea, "A new task has just\nbeen assigned to you");
                    	  taskArea.setText("");
                    	  taskArea.append(" TASK = "+task+"\n");
                    	  taskArea.append(" INPUT = "+input+"\n");
                    	  taskArea.append(" EXPECTED OUTPUT = "+exoutput+"\n"+"\n");
                    	  beginButton.setEnabled(true);
                    	  beginButtonEnabled=true;
                    	  mainFrame.repaint();
                    	  
                    	  /* update the simulator environment 
                    	  if(world!=null){
				          world.appendInfo("assign("+service+","+task+","+input+")");
                    	  }
				          /* ************************ */
                      }
                      else if(s.startsWith("start")) {
                    	  taskArea.append("UNDER EXECUTION");
                		  endButton.setEnabled(true);
                		  endButtonEnabled=true;
                    	  beginButton.setEnabled(false);
                    	  beginButtonEnabled = false;
                    	  
                    	  outputListVector.removeAllElements();           	  
                    	                     	  
                    	  Iterator it_2 = wrk_types.iterator();                    		  
                    		
                    	  while(it_2.hasNext()){
                    		  String[] singletype = (String[]) it_2.next();
                    		  if(singletype[0].equalsIgnoreCase("integer_type")){
                    			  outputListVector.addElement(new JTextField("Integer"));
                    		  }
                    		  else if(singletype[0].equalsIgnoreCase("no_outputs")){
                    			  outputListVector.addElement(new JLabel("NO OUTPUTS REQUIRED"));
                    		  }
                    		  else {
                    			  JComboBox cbox = new JComboBox();
                    			  for (int i=0; i<singletype.length; i++){
                    				  cbox.addItem(singletype[i]);
                    			  }
                    			  outputListVector.addElement(cbox);
                    			  }
                    	  }
            	  
                    	  Iterator it = outputListVector.iterator();
                    	 
                		  int con = 1;
                		  outputPane.removeAll();
                    	  while(it.hasNext()){
                    		  Object o = it.next();     
                    		  if(o.getClass().getName().equalsIgnoreCase("javax.swing.JComboBox")) {
                    		  JComboBox box = (JComboBox) o;
                    		  box.setEnabled(true);
                    		  box.setPreferredSize(new Dimension(115, 25));
                    		  outputPane.add(new JLabel("OUTPUT #"+con));
                    		  outputPane.add(box);                    		  
                    		  }
                    		  else if (o.getClass().getName().equalsIgnoreCase("javax.swing.JLabel")) {
                    		  JLabel label = (JLabel) o;
                    		  label.setEnabled(true);
                    		  outputPane.add(label); 
                    		  }
                    		  else //L'output i-esimo è un intero, perciò JTextField
                    		  {
                    		  JTextField field = (JTextField) o; 
                    		  field.setEnabled(true);
                    		  field.setPreferredSize(new Dimension(115, 25));
                    		  outputPane.add(new JLabel("OUTPUT #"+con));
                    		  outputPane.add(field);
                    		  }
                    		  con++;
                    	  }
                		  outputPane.revalidate();
                    	  /* update the simulator environment 
                    	  if(world!=null){
				          world.appendInfo("start("+service+","+task+")");
                    	  }
			       		  /* ************************ */
                    	                   	  
                    	  outputListEnabled = true;
                    	  mainFrame.repaint();
                	  }
                      else if(s.startsWith("ackCompl")) {
                    	  outputListVector.removeAllElements();          	  
                          
                    	  endButton.setEnabled(false);
                    	  endButtonEnabled = false;
                    	  
                    	  outputListEnabled = false;                    	  
                    	  
                    	  if(workList.size()>0){
                    	  Workitem w = workList.elementAt(0);
                    	  task = w.getTask();
                    	  id = w.getId();
                    	  input = w.getInputsList();
                    	  exoutput = w.getOutputsList();
                    	  workList.removeElementAt(0);
                    	  JOptionPane.showMessageDialog(taskArea, "A new task has just\nbeen assigned to you");
                    	  taskArea.setText("");
                    	  
                    	  taskArea.append(" TASK = "+task+"\n");
                    	  taskArea.append(" INPUT = "+input+"\n");
                    	  taskArea.append(" EXPECTED OUTPUT = "+exoutput+"\n"+"\n");
                    	  beginButton.setEnabled(true);
                    	  beginButtonEnabled = true;
                    	  mainFrame.repaint();
                    	  }
                      }
                      else if(s.startsWith("release")) {
                  	    taskArea.setText(" No Task Assigned");
              	  }
                      else if(s.startsWith("adaptStart")) {
                    	  adaptivity=true;
                    	  beginButton.setEnabled(false);
                    	  endButton.setEnabled(false);
                    	  
                    	  Iterator it = outputListVector.iterator();
                    	  while(it.hasNext()) {
                    		  Object obj = it.next();
                           	  if(obj.getClass().getName().equalsIgnoreCase("javax.swing.JComboBox")) 
                    		   		  ((JComboBox) obj).setEnabled(false);
                           	  else if(obj.getClass().getName().equalsIgnoreCase("javax.swing.JTextField")) 
                           		  	  ((JTextField) obj).setEnabled(false);
                    	  }                	  
                    	  oldTaskArea = taskArea.getText();
                    	  taskArea.append("\n"+"\n"+"ADAPTATION IN PROGRESS");
                    	  mainFrame.repaint();
                    	  
                    	  /* update the simulator environment 
                    	  if(world!=null){
				          world.appendInfo("AdaptStart");
                    	  }
			       		  /* ************************ */
                    	  
                    	  beginButtonEnabled_old = beginButtonEnabled;
                    	  endButtonEnabled_old = endButtonEnabled;
                    	  outputListEnabled_old = outputListEnabled;

                      }
                      else if(s.startsWith("adaptFinish")) {
                     	 adaptivity=false;
                     	 beginButton.setEnabled(beginButtonEnabled_old);
                   	  	 endButton.setEnabled(endButtonEnabled_old);
                   	  
                   	  	 Iterator it = outputListVector.iterator();
                   	   	  while(it.hasNext()) {
                		  Object obj = it.next();
                       	  if(obj.getClass().getName().equalsIgnoreCase("javax.swing.JComboBox")) 
                		   		  ((JComboBox) obj).setEnabled(outputListEnabled_old);
                       	  else if(obj.getClass().getName().equalsIgnoreCase("javax.swing.JTextField")) 
                       		  	  ((JTextField) obj).setEnabled(outputListEnabled_old);
                   	   	  }   
                   	   taskArea.setText("");
                 	   taskArea.append(oldTaskArea);
                 	   mainFrame.repaint();
                   	
                 	  /* update the simulator environment */
                   	/*if(world!=null){
                   	  	   world.appendInfo("AdaptFinish");     	  
                    }*/
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