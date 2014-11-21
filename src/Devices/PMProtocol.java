package Devices;

public abstract class PMProtocol {
	  protected ChannelsManager manager;
	  
	// Il costruttore Lega il protocollo che sarà usato per la comunicazione con il ChannelManager
	  public PMProtocol(ChannelsManager manager) {
	    this.manager = manager;
	    manager.setPMProtocol(this); //setto questo protocollo anche nel ChannelManager
	  }
	  /*
	   * Il metodo startMessage() viene invocato dal ChannelsManager quando inizializza il Socket, 
	   * quindi in questo metodo possiamo specificare un eventuale messaggio di benvenuto 
	   * da inviare al client che si è appena connesso. 
	   */
	  public abstract void startMessage(ThreadChannel ch);
	  
	  /*
	   * Il metodo parserMessage() viene invocato ogni volta che un client invia un messaggio al server, 
	   * quindi siamo sicuri che ogni stringa che un client invia passa attraverso questo metodo. 
	   * Avendo anche un riferimento al ChannelsManager possiamo, in piena libertà, 
	   * definire l'intera logica del protocollo.
	   */
	  public abstract void parserMessage(ThreadChannel channel, String msg);
	}