import java.net.*;
import java.io.*;
import viergewinnt.*;

public class GameServer {
//test	
 
  	public static void main(String[] args) throws IOException {
    		ServerSocket serverSocket = null;
    		boolean listening = true;
    		PlayerList playlist = new PlayerList();
    		ServerDatabase database = new ServerDatabase();
    		
    		try {
      		serverSocket = new ServerSocket(10000);
      		
      		
    		}
		catch (IOException e) {
      			System.err.println("[Server] Port 10000 kann nicht geöffnet werden.");
      			System.exit(-1);
		}

			database.createNewDatabase();
			database.createNewTable("Spieler");
			database.createNewTable("Spiel");
			database.createNewTable("msgkatalog");
			//database.initializeMsgKatalog();
    		System.out.println("[Server] Der Server wurde mit folgenden Parametern gestartet: "
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
