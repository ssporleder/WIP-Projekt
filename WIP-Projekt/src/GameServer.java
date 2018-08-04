import java.net.*;
import java.io.*;
import viergewinnt.*;

public class GameServer {
	

  	public static void main(String[] args) throws IOException {
    		ServerSocket serverSocket = null;
    		boolean listening = true;
    		PlayerList playlist = new PlayerList();
    		final ServerDatabase database = new ServerDatabase();
    		
    		try {
      		serverSocket = new ServerSocket(10000);
      		database.createNewDatabase();
      		
    		}
		catch (IOException e) {
      			System.err.println("Port 10000 kann nicht geöffnet werden.");
      			System.exit(-1);
		}

    		System.out.println("Der Server wurde mit folgenden Parametern gestartet: "
				   + serverSocket.getInetAddress()
				   +":"+serverSocket.getLocalPort() );

				   
    		while (listening) {
      			new ServerThread(serverSocket.accept(), playlist).start();
      			
    		}	

    		serverSocket.close();
  	}
}
