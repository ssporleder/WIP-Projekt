package viergewinnt;
import java.net.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.io.*;
import viergewinnt.Player;

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
  ResourceBundle bundle = ResourceBundle.getBundle("msgkatalog");
  
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
      
	      //Player pl = (Player) playlist.players.get(name);
	      Player pl = new Player(playerId, socket.toString());
	      pl.name=this.name;
	      Locale locale = new Locale(database.getPlayerLocale(name));
	      //Locale locale = new Locale("en", "EN");
	      out.println(locale);
	      bundle = ResourceBundle.getBundle("msgkatalog", locale);
	      //Locale.setDefault(new Locale(); 
	      //out.println(bundle.getString("my.1"));
	      out.println(protocol.help(locale));
	      out.println("#");
	      
	      while(exit == true){
	    	  
	   
	    	  	
	    	  	while(pl.getStatus(playerId).equals("Wartend")){};
	    	  
	    	  	
	    		if (pl.getStatus(playerId).equals("Online")){
	    			inputLine = null;
	    			//Es wird auf Eingaben gewartet
	    			while ((inputLine = in.readLine()) != null) {
	    		        
	    			//Eingaben werden angenommen und in Kleinbuchstaben umgewandelt
	    			inputLine = inputLine.toLowerCase();
	    			 
	    			 //Die folgenden if-Bedingungen beenden die Verbindung
	    			 if (inputLine.equals("exit")) {exit = false; break;}
	    			 if (inputLine.equals("quit")) {exit = false; break;}
	    			 
	    			 
	    			 out.println(protocol.processInput(inputLine, playerId, name, this.socket.toString(), playlist, locale));
	    			 out.println("#");
	    		
	    			 System.out.println(inputLine + " processed "
	    			 +" for " + socket.getInetAddress()
	    			 + ":"+ socket.getPort());
	    			 break;
	    			 }
	    			 
	    		}
	    		
	    		if (pl.getStatus(playerId).equals("Spielt")){
	    			//Wenn jemand angeschprochen war dann muss er sich entscheiden ob er spielen will
	    			if (pl.game.status == 0 && pl.getPlayerAmZug(playerId) == 1){out.println("You have been requested. Do you want to play:[y/n]");
	    				inputLine = in.readLine();
	    				inputLine = inputLine.toLowerCase();
	    				if (inputLine.equals("n")){playlist.spielAbgelehnt(pl.game);pl.game = null;out.println(protocol.help(locale));}
	    				if (inputLine.equals("y")){playlist.spielAkzeptiert(pl.game);}
	    			}
	    		
	    			if(pl.game != null){
	    			if (pl.game.status == 0 && pl.status1 == 0){
	    				//Sieler der fragte muss hier warten bis der andere Spieler seine antwort gibt 
	    				while(pl.game != null && pl.game.status == 0 && !pl.status.equals("Online")){};
	    				if (pl.status.equals("Online")){out.println("Player refused");}
	    			}
	    			}
	    			//Das spiel starten und die Spieler mache ihre zuge bis jemang gewonnen hat oder die Spalten voll sind
	    			if(pl.game != null){
	    			while (pl.game.status == 1){
	    				//Spieler wartet bis der ander sein Zug gemacht hat und sein Status1 geendert wird
	    				if (pl.status1 == 1) {while(pl.status1 == 1){}}
	    				//Spier Zug
	    				if (pl.status1 == 0 && pl.game.status == 1) {
	    				out.println(pl.game.zeigen());//Schreibt die spalten aus
	    				out.println("Make your move:[number of column 1..7]");
	    				inputLine = null;
	    				inputLine = in.readLine();
	    				out.println(pl.game.aktion(inputLine, pl.name, playlist));//Zug
	    				}
	    				else{
	    					/*Der Spieler der nicht am Zug war erfahrt hier wenn das Spiel endet
	    					ob er verloren hat oder das Spielfed voll war.*/
	    					if(pl.wincondition == 0){out.println("You have lost.");}
	    					else{out.println("The field is full nobody winns");}
	    				}
	    			}
	    			
	    			}
	    			
	    			if(pl.game != null){pl.game = null;}//Zestort das Spiel
	    		
	    		}
	    
	    	  
	      }
	    
	    
	   	out.println("[Server] Auf wiedersehen.");
	   	out.println("#");
	   	// Auskommentiert bis vollständig auf SQL umgestellt.
	   	//		playlist.players.remove(name);
		pl.setStatusPlayer(playerId, "Offline");
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
  
  
