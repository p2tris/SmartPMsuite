package Devices;

import java.io.IOException;
import java.net.*;

public class PMServer extends Thread {
		
/* 
 * Questa classe si occupa unicamente di stare in ascolto su una data porta, tramite l'oggetto ServerSocket, 
 * e non appena riceve una connessione ne delega la gestione del Socket appena creato all'oggetto ChannelsManager e 
 * si rimette subito in ascolto per accettare nuove connessioni. La porta su cui rimanere in ascolto viene passata al 
 * costruttore della classe, invocato direttamente nella classe Main: 
 */


  private int port;
  private ServerSocket server;
  protected ChannelsManager manager;

  public PMServer(int port, ChannelsManager manager) throws IOException {
    this.port = port;
    this.manager = manager;
    server = new ServerSocket(port);
    try {
        SchemaUpload.upload();
    } catch (Exception e) {
 
        e.printStackTrace();
    }
  }

  public PMServer(int port) throws IOException {
    this(port, new ChannelsManager());
  }

  public int getPort() {
    return port;
  }
  
  public void closeConnection(){
	  manager.close();
  }
  
  public void close() {
	  try {
		server.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	  
  }

  public void run() {
    try {
      while(true) {
    	//Chiamata bloccante - tramite il metodo accept si attendono connessioni dal client. Tale metodo crea un oggetto
    	//Socket per ogni connessione  
    	Socket socket = server.accept();
        
    	//Il Channel Manager si occupa di creare un ThreadChannel per gestire la connessione con il client
    	manager.initialite(socket);
      }
    } catch(Exception ex) {
        System.out.println("SERVER SOCKET IS CLOSING");
   }
  }
}