import java.net.*;
import java.io.*;
import viergewinnt.*;

public class GameServer {
 
  	public static void main(String[] args) throws IOException {
    		ServerSocket serverSocket = null;
    		boolean listening = true;
    		SpielListe playlist = new SpielListe();
    		ServerDatabase database = new ServerDatabase();
    		
    		try {
      		serverSocket = new ServerSocket(10000);
      		
      		
    		}
		catch (IOException e) {
      			System.err.println("[Server] Port 10000 kann nicht geöffnet werden.");
				System.exit(-1);
		}

    		//Bei jedem Start des GameServers wird die Datenbank erstellt, sofern diese noch nicht existiert.
    		//Es werden die notwendigen Tabellen angelegt und hierbei auch Tabellen bereinigt.
			database.createNewDatabase();
			System.out.println("[Server] Erstelle Datenbank...");
			database.createNewTable("Spieler");
			System.out.println("[Server] Erzeuge Tabelle 'Spieler'...");
			database.createNewTable("Spiel");
			System.out.println("[Server] Erzeuge Tabelle 'Spiel'...");
			database.initializePlayer();
			System.out.println("[Server] Bereinige Tabelle 'Spieler'...");
			database.initializeSpiel();
			System.out.println("[Server] Bereinige Tabelle 'Spiel'...");
    		System.out.println("[Server] Der Server wurde gestartet und an folgenden Socket gebunden: "
				   + serverSocket.getInetAddress()
				   +":"+serverSocket.getLocalPort() );
				  
    		while (listening) {
    			//Durch diesen Aufruf wird ein ServerThread erstellt, welcher auf die Verbindungen von den Spielern wartet.
      			new ServerThread(serverSocket.accept(), playlist).start();
      			
    		}	

    		serverSocket.close();
  	}
}
