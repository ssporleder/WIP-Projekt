package Test;

import java.net.*;
import java.io.*;
import spielpaket.*;

public class VierGewinntServer {
	

  	public static void main(String[] args) throws IOException {
    		ServerSocket serverSocket = null;
    		boolean listening = true;
		SpielerListe playlist = new SpielerListe();
		
    		try {
      		serverSocket = new ServerSocket(10000);
    		}
		catch (IOException e) {
      			System.err.println("Port 10000 kann nicht geöffnet werden.");
      			System.exit(-1);
		}

    		System.out.println("Server gestartet "
				   + serverSocket.getInetAddress()
				   +":"+serverSocket.getLocalPort() );

				   
    		while (listening) {
      			new ServerThread(serverSocket.accept(), playlist).start();
    		}	

    		serverSocket.close();
  	}
}
