package Devices;

import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Vector;


public class ThreadChannel extends Thread {

/* **ITA**
 * 
 * Un singolo canale, cioè un client che si connette al nostro server, è caratterizzato da un riferimento 
 * ad un oggetto Socket dove poter gestire lo stato della connessione e scrivere e leggere dei dati attraverso 
 * gli stream. La classe ThreadChannel inoltre è un Thread (estende la classe Thread) che conosce il suo gestore 
 * (ChannelsManager) dove poter inviare i messaggi che riceve sullo stream di output, senza doversi preoccupare 
 * del contenuto, poichè questo sarà compito nostro nell'implementazione di un protocollo in grado di interpretare 
 * il messaggio e compiere l'azione richiesta. Viene utilizzato il Thread poichè la fase di lettura di dati da uno 
 * stream risulta bloccante e quindi viene gestito nel metodo run() che, come sappiamo, rappresenta il flusso esecutivo 
 * del thread:
 *  
 */
 
  private final int LF = 10;
  private ChannelsManager manager;
  private Socket socket;
  private boolean login = false;

  public ThreadChannel(ChannelsManager manager, Socket socket) {
    this.manager = manager;
    this.socket = socket;
  }

  public void sendToPMS(String msg) {
	    try {
	      OutputStream outStream = socket.getOutputStream();
	      outStream.write(msg.getBytes());
	      outStream.flush();
	    }
	    catch(IOException ex) {System.out.println(ex.getStackTrace());}
	  }
  
  public void send(String msg) {
    try {
      msg+="\r\n";
      OutputStream outStream = socket.getOutputStream();
      outStream.write(msg.getBytes());
      outStream.flush();
    }
    catch(IOException ex) {}
    
   
  }

  public String receive() throws IOException {
    String line = "";
    InputStream inStream = socket.getInputStream();
    int read = inStream.read();
    while (read!=LF && read > -1) {
      line+=String.valueOf((char)read);
      read = inStream.read();
    }
    if (read==-1) return null;
    line+=String.valueOf((char)read);
    
	String line_to_print = line.replace("\n", "");
    System.out.println("*** JAVA-ThreadChannel -- A new command has been catched : " + line_to_print);

    	if(line.startsWith("initPMS") || line.startsWith("adaptStart") || line.startsWith("adaptFound") || line.startsWith("adaptNotFound") || line.startsWith("adaptFinish") || line.startsWith("finish"))  {
    		appendingFile("** "+new Timestamp(System.currentTimeMillis())+" **");
    		appendingFile("--"+line);
    	}
    	else if(line.startsWith("assign") || line.startsWith("readyToStart") || line.startsWith("start") || line.startsWith("finishedTask") || line.startsWith("ackCompl") || line.startsWith("release") || line.startsWith("endPMS"))
    		appendingFile("--"+line);
    	
    	else if(line.startsWith("invokePlanner")){   	
    		this.invokePlanner();
		
  }
    if(line.equalsIgnoreCase("finish")){
    	closeChannel();
    	manager.close();
    		
    }
    return line;
  }

  //Chiusura del ThreadChannel verso un client
  public void closeChannel() {
    try {
      interrupt();
      socket.close();
    } catch(Exception e) {}
  }

  public void run() {
    try {
    // Ci si mette in attesa continua dei messaggi del client
      while(true) {
        String msg = receive();
        if(msg!=null){
        	manager.processMessage(this, msg); // Alla ricezione di un nuovo messaggio inviato dal client, si passa il controllo al Channel Manager.
        } /*else {
          closeChannel();
          break;
        }*/
      }
    }
    catch(Exception ex) {}
  }

  public boolean isLogin() {
    return login;
  }

  public void setLogin(boolean login) {
    this.login = login;
  }
  
	private void invokePlanner() {
		
		try {			
				
			Process pr2 = Runtime.getRuntime().exec("gnome-terminal -e ./planner_script");
			pr2.waitFor();
			
		  }
		        catch(Exception e) {
		        	e.printStackTrace();}		
		
		Vector recovery_process_vector = new Vector();
		try {
			
			File file = new File("/home/and182/indigolog/Examples/aPMS/Planners/LPG-TD/solution.SOL");

			if(file.exists()){ 
				file.delete();			
				}
					
			while (!file.exists()){
				file = new File("/home/and182/indigolog/Examples/aPMS/Planners/LPG-TD/solution.SOL");
			}
			
			BufferedReader reader = new BufferedReader(new FileReader("/home/and182/indigolog/Examples/aPMS/Planners/LPG-TD/solution.SOL"));
			String line = reader.readLine();
			while(line!=null) {
			     line = reader.readLine();
			     if(line!=null && line.contains("(") && line.contains(")")){
			    	 recovery_process_vector.addElement(line);
			     }
			}
			//file.delete();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	 generateIndigologProcess(recovery_process_vector);

	}
	
	private void generateIndigologProcess(Vector v) {
		
		StringBuffer sb = new StringBuffer();
	    sb.append("[");
	    	    	
		Iterator it = v.iterator();
		while(it.hasNext()) {
			String task = (String) it.next();
			String[] taskArray = task.split("\\(");
			String tsk = taskArray[1];
			String[] taskArray2 = tsk.split("\\)");
			String[] workitem = taskArray2[0].split(" ");
			
			sb.append("recoveryTask(");
				sb.append(workitem[0].toLowerCase()+",");
				sb.append(workitem[1].toLowerCase()+",");
				sb.append("[");
			for(int k=2;k<workitem.length;k++) {
				sb.append(workitem[k].toLowerCase());
				if(k+1<workitem.length) sb.append(",");
			}
			sb.append("]");
			sb.append(")");
			if(it.hasNext()) sb.append(",");
			
		}		
		
		sb.append("]");
			
		send("planReady("+sb+")");
		scriviFile("recoveryProcess.pl", sb);
	}
	
	private static void appendingFile(String str) {

        try{
            FileWriter fstream = new FileWriter("test.txt",true);
            BufferedWriter fbw = new BufferedWriter(fstream);
            fbw.write(str);
            fbw.newLine();
            fbw.close();
        	}
		catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        	}
}
	
	private static void scriviFile(String nomeFile, StringBuffer buffer) {
		 
		  File file = null;
	      FileWriter fw = null;
		   
		   try {
			file = new File(nomeFile);
			fw = new FileWriter(file);
  		fw.write(buffer.toString());
  		fw.close();

		   }
		   catch(IOException e) {
		   e.printStackTrace();
		   }
		   }
	
}