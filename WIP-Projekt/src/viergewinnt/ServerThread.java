package viergewinnt;
import java.net.*;
import java.io.*;


public class ServerThread extends Thread {
  private Socket socket = null;
  private ServerProtocol protocol;
  public String name;
  boolean exit = true;
  boolean authentifiziert = false;
  boolean bereitsVerbunden = false;
  boolean passwortRichtig = false;
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
    	
    protocol = new ServerProtocol();
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
		      out.println("#");
	    	  //out.println(database.getMessageFromKatalog(1, lang));
	      	  name = in.readLine();

	        	if (playlist.spielerNichtVerbunden(name) == true) {
	        		bereitsVerbunden = true;
	        	} else {out.println("[Server] Ein Spieler mit dem Namen " + name + " ist bereits verbunden.\r\n");
	        	out.println("#");}
	      } 
	    	
	      while(authentifiziert == false) {  				
	    	  	if (playlist.getPlayerId(name) != 0) {
	        		playerId = playlist.getPlayerId(name);
	        		out.println("\n\r[Server] Dieser Spieler ist bereits bekannt. Geben Sie Ihr Passwort an: ");
	        		out.println("#");
	        	
	        		while(passwortRichtig == false) {
	        			String tmp_passwort;
	        			tmp_passwort = in.readLine();
	        			String tmp = playlist.getPlayerPasswort(playerId);
	        				if(tmp.equals(tmp_passwort) == true){
	        					this.name = name;
	        					playerId = this.playlist.getPlayerId(name);
	        					this.playlist.initPlayer(socket.toString(),name,playerId,tmp_passwort);
	        					passwortRichtig = true;	        					
		        			} else {
		        				out.println("\r\n[Server] Die eingegebene Passwort stimmt nicht. Bitte erneut versuchen.");
		        				out.println("#");
		        			}
		        		} 
	        		authentifiziert = true;
        		} else {
        		
        		
        		this.name = name;
        		
        		out.println("\r\n[Server] Bitte legen Sie ein Passwort fest:");
        		out.println("#");
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
	      out.println(protocol.help());
	      out.println("#");
	      
	      while(exit == true){
	    	  	
	    	  
	    	  
	    	  	while(pl.status.equals("Wartend")){
	    	  		
	    	  	};
	    	  	
	    	  	
	    		if (pl.status.equals("Online")){
	    			inputLine = null;
	    			//Es wird auf Eingaben gewartet
	    			while ((inputLine = in.readLine()) != null) {
	    		        
	    			//Eingaben werden angenommen und in Kleinbuchstaben umgewandelt
	    			inputLine = inputLine.toLowerCase();
	    			 
	    			 //Die folgenden if-Bedingungen beenden die Verbindung
	    			 if (inputLine.equals("exit")) {exit = false; break;}
	    			 if (inputLine.equals("quit")) {exit = false; break;}
	    			 
	    			 
	    			 out.println(protocol.processInput(inputLine, playerId, name, this.socket.toString(), playlist));
	    			 out.println("#");
	    		
	    			 System.out.println(inputLine + " processed "
	    			 +" for " + socket.getInetAddress()
	    			 + ":"+ socket.getPort());
	    			 break;
	    			 }
	    			 
	    		}
	    	  
	      }
	    
	    
	   	out.println("[Server] Auf wiedersehen.");
	   	out.println("#");
		playlist.players.remove(name);
		playlist.setStatusPlayer("nicht verbunden", playerId, "Offline");
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
  
  
