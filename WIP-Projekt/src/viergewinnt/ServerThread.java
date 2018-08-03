package viergewinnt;
import java.net.*;
import java.io.*;


public class ServerThread extends Thread {
  private Socket socket = null;
  //private SpielProtokoll protocol;
  public String name;
  boolean exit = true;
  boolean spielerExistiert = false;
  PlayerList playlist; 

  
  public  ServerThread(Socket socket, PlayerList playlist) {
    super("ServerThread");
    this.socket = socket;
    this.playlist = playlist;
    
    System.out.println("Verbindung akzeptiert  "
	+" for " + socket.getInetAddress()
	+ ":"+ socket.getPort());
    	
    //protocol = new SpielProtokoll();
  
  }
  
  public void run() {

	    try {

	      // Buffer erzeugen
	      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	      BufferedReader in = new BufferedReader(
		  new InputStreamReader(socket.getInputStream()));


	      //String inputLine;
	      
	      while(spielerExistiert == false){ //Schleife die prufen soll ob Spieler existiert
	        	String name;
	        	out.println("Bitte geben Sie Ihrem Spielernamen ein: ");    
	        	name = in.readLine();
	        	if (playlist.spielerExistiert(name) == true){
	        		this.name = name;
	        	this.playlist.newplayer(socket.toString(),name);
	        		
	        		spielerExistiert = true;
	        	out.println("Der folgende Spielername wurde festgelegt auf: \r\n" + name);
	        	
	        	System.out.println("Spielername festgelegt auf "
	        			+ name +" for " + socket.getInetAddress()
	        			+ ":"+ socket.getPort());
	        		    	
	        	
	  	}
	  	else {out.println("This user already exist");}
	      }
	    }
	    catch (IOException e) {
	        e.printStackTrace();

  }
}
  
}
  
  
