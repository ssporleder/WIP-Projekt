package viergewinnt;
import java.net.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.io.*;
import viergewinnt.Spieler;
import viergewinnt.Spiel;

public class ServerThread extends Thread {
  private Socket socket = null;
  private ServerProtocol protocol;
  public String name;
  boolean exit = true;
  boolean authentifiziert = false;
  boolean bereitsVerbunden = false;
  boolean passwortRichtig = false;
  SpielListe playlist; 
  ServerDatabase database;
  ResourceBundle bundle = ResourceBundle.getBundle("msgkatalog");
  
  public  ServerThread(Socket socket, SpielListe playlist) {
    super("ServerThread");
    this.socket = socket;
    this.playlist = playlist;
    
    System.out.println("[Server] Verbindung akzeptiert  "
	+" for " + socket.getInetAddress()
	+ ":"+ socket.getPort());
    	
    //Klasse ServerProtocol wird instanziert
    protocol = new ServerProtocol();
    //Klasse ServerDatabase wird instanziert um auf die Methoden von ServerDatabase zugreigen zu können
    database = new ServerDatabase();
  }
  
  public void run() {

	    try {

	      // Ausgabe Printwriter, BufferedReader und InputstreamReader wird erzeugt
	      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	      BufferedReader in = new BufferedReader(
		  new InputStreamReader(socket.getInputStream()));

	      //In InputLine werden Eingaben eingelesen
	      String inputLine;
	      String name = "";
	      int playerId = 0;
	      String passwort;
	          
	      //Diese Schleife prüft, ob ein Spieler mit dem eingegebenen Namen bereits verbunden ist. Pro Spielername darf es nur eine Verbindung geben
	      while (bereitsVerbunden == false) {
		      out.println("[Server] Bitte geben Sie einen Spielernamen ein: ");
		      //Durch die Ausgabe von "#" wird dem SpielClient das Ende der Übertragung signalisiert.
		      out.println("#");
	      	  name = in.readLine();

	        	if (playlist.spielerNichtVerbunden(name) == true) {
	        		bereitsVerbunden = true;
	        	} else {out.println("[Server] Ein Spieler mit dem Namen " + name + " ist bereits verbunden.\r\n");
	        	out.println("#");}
	      } 
	    
	      //In dieser Schleife wird zunächst egeprüft, ob ein Spieler schon bekannt ist.
	      //Wenn der Spieler bekannt ist wird die Authentifizierung des Spielers gegen die Daten in der Spielertabelle durchgeführt. 
	      //Es wird Benutzername und Passwort überprüft.
	      while(authentifiziert == false) {  				
	    	  	//Prüfung, ob Spieler bereits in Datenbank vorhanden
	    	  	if (playlist.getPlayerId(name) != 0) {
	        		playerId = playlist.getPlayerId(name);
	        		out.println("\n\r[Server] Dieser Spieler ist bereits bekannt. Geben Sie Ihr Passwort an: ");
	        		out.println("#");
	        		//Schleife zur Überprüfung des Passworts
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
        		
        		// Wenn Spieler noch nicht vorhanden wird ein neues Passwort abgefragt und der Spieler in der Datenbank angelegt.
        		this.name = name;
        		
        		out.println("\r\n[Server] Bitte legen Sie ein Passwort fest:");
        		out.println("#");
        		passwort = in.readLine();
        		playerId = this.playlist.newplayer(socket.toString(),name,playerId,passwort);

	        	out.println("\r\n[Server] Der Spielername wurde festgelegt auf: " + name + "\n\r[Server] Die SpielerID lautet: " + playerId + "\n\r\r\n[Server] Willkommen auf dem 4Gewinnt Server.\r\n\r\n");
	        	
	        	
	        	System.out.println("[Server] Spielername festgelegt auf "
	        			+ name +" for " + socket.getInetAddress()
	        			+ ":"+ socket.getPort());
        		}
	    	  	authentifiziert = true;
	    	  	this.name = name;
	      }
      
	      //Ab hier ist der Spieler verbunden, authentifiziert und es wird die Hilfe ausgegeben
	      
	      Spieler pl = new Spieler(playerId, socket.toString());
	      pl.name=this.name;
	      Locale locale = new Locale(database.getPlayerLocale(name));
	      out.println(locale);
	      bundle = ResourceBundle.getBundle("msgkatalog", locale);
	      out.println(protocol.help(locale));
	      out.println("#");
	      
	      
	      //In dieser Schleife findet die eigentliche Logik des Servers statt. Abhängig vom Status des Spielers werden unterschiedliche Aktionen angeboten.
	      while(exit == true){
	    	  
	    	  	//Sobald ein Spieler ein Spiel startet wird er in dieser Schleife "Wartend" gestellt.
	    	  	while(pl.getStatus(playerId).equals("Wartend")){};
	    	  
	    	  	//Ist ein Spieler im Status "Online", so kann er durch eine Reihe von Kommandos Aktionen starten.
	    		if (pl.getStatus(playerId).equals("Online")){
	    			inputLine = null;
	    			//Es wird auf Eingaben des Spielers gewartet.
	    			while ((inputLine = in.readLine()) != null) {
	    		        
	    			//Eingaben werden angenommen und in Kleinbuchstaben umgewandelt.
	    			inputLine = inputLine.toLowerCase();
	    			 
	    			 //Bei Eingabe von "Exit" oder "Quit" wird die Verbindung beendet.
	    			if (inputLine.equals("exit")) {exit = false; break;}
	    			if (inputLine.equals("quit")) {exit = false; break;}
	    			 
	    			//Das eingegebene Kommando wird an die Instanz protocol der Klasse ServerProtocol übergeben und die Ausgabe dargestellt.
	    			out.println(protocol.processInput(inputLine, playerId, name, this.socket.toString(), playlist, locale));
	    			out.println("#");
	    		
	    			System.out.println(inputLine + " processed "
	    			 +" for " + socket.getInetAddress()
	    			 + ":"+ socket.getPort());
	    			break;
	    			}	 
	    		}
	    		
	    		//Sobald der zweite Spieler einem Spiel Beitritt beginnt hier die eigentliche Spiellogik
	    		if (pl.getStatus(playerId).equals("Spielt")){
	    			//Wenn sich das Spiel im Zustand "Mitspieler gesucht" befindet und der Spieler in der Spalte "amZug" = 1 ist, dann wird gefragt, ob er das Spiel spielen möchte:
	    			//TODO Ausgabe übersetzen.
	    			if (database.getSpielStatusFromId(pl.getPlayerSpielId(playerId)).equals("Mitspieler gesucht") == true && pl.getPlayerAmZug(playerId) == 1){out.println("You have been requested. Do you want to play:[y/n]");out.println("#");
	    				inputLine = in.readLine();
	    				inputLine = inputLine.toLowerCase();
	    				//Ablehnung des Spielers und somit wird das Spiel beendet.
	    				if (inputLine.equals("n")){playlist.spielAbgelehnt(pl.getPlayerSpielId(playerId));
	    				out.println(protocol.help(locale));out.println("#");}
	    				//Zustimmung des Spielers und somit Spielstart.
	    				if (inputLine.equals("y")){playlist.spielAkzeptiert(pl.getPlayerSpielId(playerId));}
	    			}
	    		
	    			//In dieser Bedingung und Schleife landet der anfragende Spieler und wartet auf die Entscheidung des angefragten Spielers.
	    			if (database.getSpielStatusFromId(pl.getPlayerSpielId(playerId)).equals("Mitspieler gesucht") == true && pl.getPlayerAmZug(playerId) == 0){
	    				while(database.getSpielStatusFromId(pl.getPlayerSpielId(playerId)).equals("Mitspieler gesucht") == true && !pl.getStatus(playerId).equals("Online")){};
	    				//TODO Ausgabe übersetzen.
	    				if (pl.getStatus(playerId).equals("Online")){out.println("Player refused");out.println(protocol.help(locale));out.println("#");}
	    			}
	    			
	    			//Hier beginnt das Spiel. Es wird nacheinander um Zug gebeten.
	    			if(database.getSpielStatusFromId(pl.getPlayerSpielId(playerId)).equals("Gestartet") == true){
	    				while (database.getSpielStatusFromId(pl.getPlayerSpielId(playerId)).equals("Gestartet") == true){
	    					//Ein Spieler wartet auf den Zug des anderen.
	    					if (pl.getPlayerAmZug(playerId) == 1) {while(pl.getPlayerAmZug(playerId) == 1){}}
	    					//Der Spieler der nicht wartet führt seinen Zug durch.
	    					if (pl.getPlayerAmZug(playerId) == 0 && database.getSpielStatusFromId(pl.getPlayerSpielId(playerId)).equals("Gestartet") == true) {
	    						
	    						Spiel sp = new Spiel(pl.getPlayerSpielId(playerId));
	    				
	    						//Das Spielfeld wird ausgegeben.
	    						out.println(sp.zeigen(pl.getPlayerSpielId(playerId)));
	    						//Der Spieler wird um den Zug gebeten.
	    						//TODO Ausgabe übersetzen.
	    						out.println("Make your move:[number of column 1..7]");
	    						out.println("#");
	    						inputLine = null;
	    						inputLine = in.readLine();
	    						//Der Zug wird an die Spielklasse gegeben und auf Rückmeldung der Klasse gewartet.
	    						out.println(sp.aktion(inputLine, pl.name, playlist, pl.getPlayerSpielId(playerId)));
	    						out.println("#");
	    					}	
	    					else{
	    					//Der Spieler der nicht am Zug war, erfährt hier bei Beendigung des Spiels, ob er verloren hat oder ob das Spiel unentschieden ausgegangen ist (Spielfed voll)
	    						//TODO Ausgabe übersetzen.
	    					if(database.getSpielUnentschieden(pl.getPlayerSpielId(playerId)) == 0){out.println("You have lost.");out.println("#");}
	    					//TODO Ausgabe übersetzen.
	    					else{out.println("The field is full nobody winns");out.println("#");}
	    					}
	    				}
	    			}
	    		}    	  
	      }
	    	    
	      out.println("[Server] Auf wiedersehen.");
	      out.println("#");
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
  
  
