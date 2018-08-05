import java.net.*;
import java.io.*;
import viergewinnt.*;

public class GameServer {
	

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

    		System.out.println("[Server] Der Server wurde mit folgenden Parametern gestartet: "
				   + serverSocket.getInetAddress()
				   +":"+serverSocket.getLocalPort() );
				database.createNewDatabase();
				database.createNewTable("Spieler");
				database.createNewTable("Spiel");
				//Test um zu verifizieren dass die Methode insertPlayer funktioniert
				//database.insertPlayer(1,"tester","Online");

				  
    		while (listening) {
      			new ServerThread(serverSocket.accept(), playlist).start();
      			
    		}	

    		serverSocket.close();
  	}
}
