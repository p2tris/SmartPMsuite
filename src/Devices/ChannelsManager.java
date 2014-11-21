package Devices;

import java.util.*;
import java.io.IOException;
import java.net.Socket;

public class ChannelsManager {
	
	
/* **ENG**
* 
* The class ChannelsManager is a container that stores all the references of the TreadChannel objects being logged in the Chat.
* Such a class is the heart of our Server, since it initializes the Socket by assigning it to a ThreadChannel. 
* Moreover, it provides the bridge between the protocol and the remaining part of the server. 
* All the clients connected to the Server (but that are not still logged) can interact
* only with the Server itself, not with the other clients. 
*
*/
	
/* **ITA**
* 
* La classe ChannelsManager rappresenta un contenitore dove vengono mantenuti tutti i riferimenti dei diversi ThreadChannel
* relativi ai client che si sono loggati nella chat. Questa classe è il cuore del nostro server, poichè provvede 
* ad inizializzare il Socket affidandolo ad un ThreadChannel, ed inoltre fa da tramite tra il protocollo ed il resto 
* del server. I client che sono collegati al server, ma che non hanno ancora effettuato una sessione di login, 
* rimangono collegati, possono inviare messaggi, ma non vengono inseriti nel contenitore (HashMap) e quindi non
* possono interagire con gli altri client, ma solo con il server. 
* Le modalità di login saranno specificate nell' implementazione del protocollo.
* 
*/
	
  private Socket s = null;
  protected Map <String, ThreadChannel> channels_Map;
  protected PMProtocol protocol = null;

  public ChannelsManager() {
	  channels_Map = new HashMap<String, ThreadChannel>();
  }

  //Metodo utilizzato nel momento in cui si invoca il costruttore di ChatProtocol.
  //Si lega il ChannelManager al protocollo stabilito di Chat.
  public void setPMProtocol(PMProtocol protocol) {
    this.protocol = protocol;
  }

  public void initialite(Socket socket) {
	  
	s = socket;
	  
	//The ChannelManager builds a ThreadChannel object to manage the connection with the client
    ThreadChannel channel = new ThreadChannel(this, socket);
    
	//The Run() method of ThreadChannel is invoked 
    channel.start();
    
    //A welcome message is sent to the client 
    protocol.startMessage(channel);
  }

  public synchronized boolean addChannel(String name,ThreadChannel channel) {
    if(!channels_Map.containsKey(name)) {
    channels_Map.put(name, channel);
      return true;
    } else return false;
  }

 
  //When a new message is received by the client, the management of the command is passed to the protocol.
    public synchronized void processMessage(ThreadChannel ch, String str) {
    protocol.parserMessage(ch, str);
  }

  public synchronized void removeChannel(String name) {
    if(channels_Map.containsKey(name)) {
      ThreadChannel ch = channels_Map.remove(name);
      ch.interrupt();
    }
  }

  public synchronized Set<String> getAllName() {
    return channels_Map.keySet();
  }

  public synchronized ThreadChannel getChannel(String name) {
    if(channels_Map.containsKey(name))
    return channels_Map.get(name);
    else return null;
  }
  
  public void close(){
	  try {
		s.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
  }
}