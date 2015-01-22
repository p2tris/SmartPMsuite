package Devices;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.*;
import java.util.List;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.mxgraph.smartml.model.Constants;

public class DefaultPMProtocol extends PMProtocol {

  private Map<String, Command> commands;

  {
    commands = new HashMap<String, Command>();
    commands.put("list", new List());
    //commands.put("msg",  new Msg());
    //commands.put("time", new Time());
    commands.put("user", new User());
    //commands.put("quit", new Quit());
  }

  public DefaultPMProtocol(ChannelsManager manager) {
    super(manager);
  }

  public void parserMessage(ThreadChannel ch, String str) {
	  String str_to_print = str.replace("\n", "");
	  System.out.println("*** JAVA-DefaultPMProtocol -- The thread named '"+ch.getName()+"' has sent the command : " + str_to_print);
	  
	  if(str.charAt(0)=='/' || str.startsWith("initPMS")) {
	      if(str.startsWith("initPMS"))
	    	  str="/user apms";
		  Pattern pattern = Pattern.compile("\\/([^\\s]+)\\s(.*)");
	      Matcher match = pattern.matcher(str);
	      match.find();
	      String command = match.group(1);
	      if (commands.containsKey(command)) {
	        Command cmd = commands.get(command);
	        cmd.execute(ch, match);
	    } 
	  }	
	  else if(ch.getName().equalsIgnoreCase("apms")){
		  manageCommand(str);
	  }
	  else if(str.startsWith("pingTerminal")){
		  manageCommand(str);
	  }
	  else if(ch.isLogin()){
		  sendToPMS(str);
	   }
	  else if(Constants.getModality_of_execution().equalsIgnoreCase("execution") && (str.startsWith("readyToStart") || str.startsWith("finishedTask")))
		  sendToPMS(str);
	   }
  
  public void manageCommand(String str) {
	
	  if(str.startsWith("assign") || str.startsWith("start") || str.startsWith("ackCompl") || str.startsWith("release")) {
		  String dv = new String();
		  String [] s1 = str.split("\\(");
		  String [] s2 = s1[1].split(",");
		  dv=s2[0];
		  if(manager.getAllName().contains("smartpm_terminal")){
		     dv="smartpm_terminal";
		  }
		  
		  
		  if(Constants.getModality_of_execution().equalsIgnoreCase("execution")) {
		  // -- Added by Pätris 2014 --
		    // For invoking php script to send also to real devices
		    HttpClient httpClient = new DefaultHttpClient();
		    // replace with your url
			HttpPost httpPost = new HttpPost("http://www.dis.uniroma1.it/~smartpm/webtool/incomingCommands.php"); 
			
			
			//Post Data
			java.util.List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
			nameValuePair.add(new BasicNameValuePair("string", str));
			
			//Encoding POST data
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
			} catch (UnsupportedEncodingException e) {
				// log exception
				e.printStackTrace();
			}
			
			//making POST request.
			try {
				HttpResponse response = httpClient.execute(httpPost);
				// write response to log
				System.out.println("*** Http Post Response:" + response.toString());
			} catch (ClientProtocolException e) {
				// Log exception
				e.printStackTrace();
			} catch (IOException e) {
				// Log exception
				e.printStackTrace();
			}
		  }
			// --
		  else if(Constants.getModality_of_execution().equalsIgnoreCase("simulation")) {
		  
			  ThreadChannel channel = manager.getChannel(dv);
			  if(channel.isLogin()) channel.send(str);
		  }
	  }
	  else if (str.startsWith("pingTerminal")) {
		  String dv = new String();
		  if(manager.getAllName().contains("smartpm_terminal")){
			     dv="smartpm_terminal";
			     ThreadChannel channel = manager.getChannel(dv);
				  if(channel.isLogin()) channel.send(str);
			  }
			  
	  }
	  else {
		  broadcast(str);
	  }
      
      
  }
  
  private void sendToPMS(String str) {      
	  
	  if(str.substring(0,5).equalsIgnoreCase("abort")){
		  ThreadChannel channel = manager.getChannel("apms");
		  if(channel.isLogin()) channel.send("abort");  
	  }
	  else{
	  ThreadChannel channel = manager.getChannel("apms");
	  int j = str.lastIndexOf(")");
	  String sent = str.substring(0, j+1);
      if(channel.isLogin()) channel.send(sent);  
	  }
  }

  /*
   * Invia la stringa msg a tutti i client, 
   * il flag mysend
   * indica se inviarlo anche al mittente 
   * 
   */
  protected void broadcast(String msg) {
	  
	 if(Constants.getModality_of_execution().equalsIgnoreCase("execution")) {

		 // -- Added by Pätris 2014 --
		    // For invoking php script to send also to real devices
		    HttpClient httpClient = new DefaultHttpClient();
		    // replace with your url
			HttpPost httpPost = new HttpPost("http://www.dis.uniroma1.it/~smartpm/webtool/incomingCommands.php"); 
			
			//Post Data
			java.util.List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
			nameValuePair.add(new BasicNameValuePair("string", msg));
			
			//Encoding POST data
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
			} catch (UnsupportedEncodingException e) {
				// log exception
				e.printStackTrace();
			}
			
			//making POST request.
			try {
				HttpResponse response = httpClient.execute(httpPost);
				// write response to log
				System.out.println("*** Http Post Response:" + response.toString());
			} catch (ClientProtocolException e) {
				// Log exception
				e.printStackTrace();
			} catch (IOException e) {
				// Log exception
				e.printStackTrace();
			}
		  
	  }
	  else if (Constants.getModality_of_execution().equalsIgnoreCase("simulation")) {
		    Set <String> set =  manager.getAllName();
		    for(String str : set) {
		      if(!str.equalsIgnoreCase("apms")) {
		        ThreadChannel channel = manager.getChannel(str);
		        if(channel.isLogin()) channel.send(msg);
		        
		      }
		   }
    }
  }
  
  //Si invia il messaggio di benvenuto al client  
  public void startMessage(ThreadChannel ch) {
    //ch.send("assign(fb1,[workitem(go,id_1,wagon5,wagon5)])");
  }

  
 //Ciascuna classe che implementa l'interfaccia Commands deve sviluppare il metodo execute
  private class User implements Command {
	     public void execute(ThreadChannel channel, Matcher match) {
	       if(!channel.isLogin()) {
	         String name= match.group(2);
	         if (name.length()==0) {
	           channel.send("The syntax of the command is incorrect!");
	         } else if(manager.addChannel(name.toLowerCase(), channel)) {
	           channel.setName(name);
	           channel.setLogin(true);
	           System.out.println("*** JAVA-DefaultPMProtocol -- Hello Service '"+channel.getName()+"', you are now correctly logged in!");
	         } else {
	           System.out.println("*** JAVA-DefaultPMProtocol -- Attention - The name is already in use!");
	         }
	       } else {
	           System.out.println("*** JAVA-DefaultPMProtocol -- Attention - You are already logged!");
	       }
	     }
	   }  
  
  /*
   private class Msg implements Command {
     public void execute(ThreadChannel channel, Matcher match) {
        if (channel.isLogin()) {
           broadcast(channel.getName().toLowerCase(),
             "#"+channel.getName()+" "+match.group(2), true);
        } else {
           channel.send("Non ti sei ancora loggato");
        }
     }
   }

   private class Time implements Command {
      public void execute(ThreadChannel channel, Matcher match) {
        channel.send(new Date().toString());
      }
   }

   private class Quit implements Command {
     public void execute(ThreadChannel channel, Matcher match) {
       channel.send("Bye Bye");
       broadcast(channel.getName().toLowerCase(),
         "L'utente "+channel.getName()+ " si e' disconnesso!!", false);
       manager.removeChannel(channel.getName().toLowerCase());
       channel.closeChannel();
     }
   }
*/
   private class List implements Command {
     public void execute(ThreadChannel channel, Matcher match) {
       Set <String> names = manager.getAllName();
       if (names.isEmpty()) {
         channel.send("Nessun utente collegato");
       } else {
         channel.send("********** Lista degli utenti connessi **********");
         for(String name:names)
           channel.send(name);
       }
     }
  }
}