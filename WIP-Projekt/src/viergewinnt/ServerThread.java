package viergewinnt;
import java.net.*;
import java.io.*;


public class ServerThread extends Thread {
  private Socket socket = null;
  private GameProtocol protocol;
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
    	
    protocol = new GameProtocol();
  
  }
  
  public void run() {

	    try {

	      // Buffer erzeugen
	      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	      BufferedReader in = new BufferedReader(
		  new InputStreamReader(socket.getInputStream()));


	      String inputLine;
	      
	      //Bei Verbindungsaufbau wird überprüft, ob der Spieler schon existiert. Fall er nicht existiert wird dieser angelegt
	      while(spielerExistiert == false){ //Schleife die prufen soll ob Spieler existiert
	        	String name;
	        	//Eine Ausgabe an den verbundenen Spieler wird angezeit
	        	out.println("Bitte geben Sie einen Spielernamen ein: ");    
	        	name = in.readLine();
	        	if (playlist.spielerExistiert(name) == true){
	        		this.name = name;
	        	this.playlist.newplayer(socket.toString(),name);
	        		
	        		spielerExistiert = true;
	        	out.println("\r\nDer Spielername wurde festgelegt auf: \r\n\r\n" + name + "\n\r\r\nWillkommen auf dem 4Gewinnt Server.\r\n\r\n");
	        	out.println(protocol.help());
	        	
	        	System.out.println("Spielername festgelegt auf "
	        			+ name +" for " + socket.getInetAddress()
	        			+ ":"+ socket.getPort());
	        		    	
	        	
	  	}
	  	else {out.println("Ein Spieler mit dem Namen " + name + " ist bereits verbunden.\r\n");}
	      }
	      
	      Player pl = (Player) playlist.players.get(name);
	      pl.name=this.name;
	      
	      while(exit == true){
	      
	    	  	//Wenn Spielerstatus "Online" dann wird der foglende Ablauf durchgeführt
	    		if (pl.status.equals("Online")){
	    			inputLine = null;
	    			//Es wird auf Eingaben gewartet
	    			while ((inputLine = in.readLine()) != null) {
	    		        
	    			//Eingaben werden angenommen und in Kleinbuchstaben umgewandelt
	    			inputLine = inputLine.toLowerCase();
	    			 
	    			 //Die folgenden if-Bedingungen beenden die Verbindung
	    			 if (inputLine.equals("exit")) {exit = false; break;}
	    			 if (inputLine.equals("quit")) {exit = false; break;}
	    			 
	    			 //Hier werden die Befehle an die Klasse GameProtocol übergeben wo die eigentliche Steuerung vorgenommen wird.
	    			 out.println(protocol.processInput(inputLine, name, this.socket.toString(), playlist));
	    			 //Ausgabe auf Console
	    			 System.out.println(inputLine + " processed "
	    			 +" for " + socket.getInetAddress()
	    			 + ":"+ socket.getPort());
	    			 break;
	    			 }
	    			 
	    		}
	    	  
	      }
	    
	    
	   	out.println("Auf wiedersehen.");
		playlist.players.remove(name);
		out.close();
		in.close();
		socket.close();
 
	    

	      System.out.println("Verbindung beendet  "
		  +" for " + socket.getInetAddress()
		  + ":"+ socket.getPort());}
	    
	    catch (IOException e) {
	        e.printStackTrace();

  }
}
  
}
  
  
