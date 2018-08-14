import java.net.*;
import java.io.*;
import viergewinnt.*;
import java.util.Locale;
import java.util.ResourceBundle;


public class GameServer {
//test	
 
  	public static void main(String[] args) throws IOException {
    		ServerSocket serverSocket = null;
    		boolean listening = true;
    		PlayerList playlist = new PlayerList();
    		ServerDatabase database = new ServerDatabase();
    		//Locale.setDefault(new Locale("en", "EN"));
    		ResourceBundle bundle = ResourceBundle.getBundle("msgkatalog");
    		//System.out.println(bundle.getString("my.1"));
    		
    		try {
      		serverSocket = new ServerSocket(10000);
      		
      		
    		}
		catch (IOException e) {
      			//System.err.println("[Server] Port 10000 kann nicht geöffnet werden.");
				System.err.println(bundle.getString("my.2"));
				System.exit(-1);
		}

			database.createNewDatabase();
			System.out.println(bundle.getString("my.3"));
			database.createNewTable("Spieler");
			System.out.println("[Server] Erzeuge Tabelle 'Spieler'...");
			database.createNewTable("Spiel");
			System.out.println("[Server] Erzeuge Tabelle 'Spiel'...");
			//database.createNewTable("msgkatalog");
			//System.out.println("[Server] Erzeuge Tabelle 'MsgKatalog'...");
			database.initializePlayer();
			System.out.println("[Server] Bereinige Tabelle 'Spieler'...");
			//database.initializeMsgKatalog();
    		System.out.println("[Server] Der Server wurde gestartet und an folgenden Socket gebunden: "
				   + serverSocket.getInetAddress()
				   +":"+serverSocket.getLocalPort() );

				//database.getPlayerId("pxcxcx");
				//Test um zu verifizieren dass die Methode insertPlayer funktioniert
				//database.insertPlayer(1,"tester","Online");
				//database.getPlayerId("asdasdasd");
				  
    		while (listening) {
      			new ServerThread(serverSocket.accept(), playlist).start();
      			
    		}	

    		serverSocket.close();
  	}
}
