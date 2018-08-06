package viergewinnt;
import java.net.*;
import java.io.*;


public class ServerThread extends Thread {
  private Socket socket = null;
  private GameProtocol protocol;
  public String name;
  boolean exit = true;
  boolean authentifiziert = false;
  boolean bereitsVerbunden = false;
  boolean passwortRichtig = false;
  boolean richtigeId = false;
  PlayerList playlist; 
  int lang = 0;
  ServerDatabase database;

  
  public  ServerThread(Socket socket, PlayerList playlist) {
    super("ServerThread");
    this.socket = socket;
    this.playlist = playlist;
    
    System.out.println("[Server] Verbindung akzeptiert  "
	+" for " + socket.getInetAddress()
	+ ":"+ socket.getPort());
    	
    protocol = new GameProtocol();
    database = new ServerDatabase();
  
  }
  
  public void run() {

	    try {

	      // Buffer erzeugen
	      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	      BufferedReader in = new BufferedReader(
		  new InputStreamReader(socket.getInputStream()));


	      String inputLine;
	      String name = "";
	      int playerId = 0;
	      String passwort;
	      
	      
	      
	      while (bereitsVerbunden == false) {
		      out.println("[Server] Bitte geben Sie einen Spielernamen ein: ");
	    	  //out.println(database.getMessageFromKatalog(1, lang));
	      	  name = in.readLine();

	        	if (playlist.spielerNichtVerbunden(name) == true) {
	        		bereitsVerbunden = true;
	        	} else {out.println("[Server] Ein Spieler mit dem Namen " + name + " ist bereits verbunden.\r\n");}
	      } 
	    	
	      while(authentifiziert == false) {  				
	    	  	if (playlist.getPlayerId(name) != 0) {
	        		playerId = playlist.getPlayerId(name);
	        		out.println("\n\r[Server] Dieser Spieler ist bereits bekannt. Geben Sie Ihr Passwort an: ");
	        	
	        		while(passwortRichtig == false) {
	        			String tmp_passwort;
	        			tmp_passwort = in.readLine();
	        			String tmp = playlist.getPlayerPasswort(playerId);
	        			System.out.println(tmp+tmp_passwort);
	        				if(tmp_passwort == tmp){
	        					this.name = name;
	        					passwortRichtig = true;	        					
		        			} else {
		        				out.println("\r\n[Server] Die eingegebene Passwort stimmt nicht. Bitte erneut versuchen.");
		        			}
		        		} 
	        		authentifiziert = true;
        		} else {
        		
        		
        		this.name = name;
        		
        		out.println("\r\n[Server] Bitte legen Sie ein Passwort fest:");
        		passwort = in.readLine();
        		playerId = this.playlist.newplayer(socket.toString(),name,playerId,passwort);

	        	out.println("\r\n[Server] Der Spielername wurde festgelegt auf: " + name + "\n\r[Server] Die SpielerID lautet: " + playerId + "\n\r\r\n[Server] Willkommen auf dem 4Gewinnt Server.\r\n\r\n");
	        	//out.println(protocol.help());
	        	
	        	System.out.println("[Server] Spielername festgelegt auf "
	        			+ name +" for " + socket.getInetAddress()
	        			+ ":"+ socket.getPort());
        		}
	    	  	authentifiziert = true;
	    	  	this.name = name;
	      }
      
	      Player pl = (Player) playlist.players.get(name);
	      pl.name=this.name;
	      
	      while(exit == true){
	      
	    	  	//Dies ist die Hauptspielschleife!
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
	    
	    
	   	out.println("[Server] Auf wiedersehen.");
		playlist.players.remove(name);
		out.close();
		in.close();
		socket.close();
 
	    

	      System.out.println("[Server] Verbindung beendet  "
		  +" for " + socket.getInetAddress()
		  + ":"+ socket.getPort());}
	    
	    catch (IOException e) {
	        e.printStackTrace();

  }
}
  
}
  
  
