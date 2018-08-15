package spielpaket;
import java.net.*;

import spielpaket.*;
import spielpaket.Spieler;
import spielpaket.Spiel;

import java.io.*;
import java.lang.*;

public class ServerThread2 extends Thread {
  private Socket socket = null;
  private SpielProtokoll protocol;
  public String name;
  boolean exit = true;
  boolean contact = false;
  SpielerListe playlist; 

  
  public  ServerThread2(Socket socket, SpielerListe playlist) {
    super("ServerThread");
    this.socket = socket;
    this.playlist = playlist;
    
    System.out.println("Connection accepted  "
	+" for " + socket.getInetAddress()
	+ ":"+ socket.getPort());
    	
    protocol = new SpielProtokoll();
  
  }

  public void run() {

    try {

      // Buffer erzeugen
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(
	  new InputStreamReader(socket.getInputStream()));


      String inputLine, outputLine;

      // Spieler name eingeben
      while(contact == false){ //Schleife die prufen soll ob Spieler existiert
      	String name;
      	out.println("Please enter your name: ");    
      	name = in.readLine();
      	if (playlist.contact(name) == true){
      		this.name = name;
		this.playlist.newplayer(socket.toString(),name);
      		
      		contact = true;
	}
	else {out.println("This user already exist");}
      }
      
      Spieler pl = (Spieler) playlist.players.get(name);
      pl.name=this.name;
            
      //In dieser Schleife findet alles statt
      while(exit == true){
	//Spieler wartet hier bis jemand ihm anschpricht (Sein status wird geendert)
        while(pl.status.equals("waiting")){};
	//Das Spiel       
	if (pl.status.equals("playing")){
		//Wenn jemand angeschprochen war dann muss er sich entscheiden ob er spielen will
		if (pl.game.status == 0 && pl.status1 == 1){out.println("You have been requested. Do you want to play:[y/n]");
			inputLine = in.readLine();
			inputLine = inputLine.toLowerCase();
			if (inputLine.equals("n")){playlist.reject(pl.game);pl.game = null;out.println(protocol.hello());}
			if (inputLine.equals("y")){playlist.accept(pl.game);}
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
			out.println(pl.game.write());//Schreibt die spalten aus
			out.println("Make your move:[number of column 1..7]");
			inputLine = null;
			inputLine = in.readLine();
			out.println(pl.game.move(inputLine, pl.name, playlist));//Zug
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
	
	//Befehle werden gemacht
	if (pl.status.equals("Online")){
		out.println(protocol.hello()); //Hilfe auschreiben
		inputLine = null;
		while ((inputLine = in.readLine()) != null) {
	        
		inputLine = inputLine.toLowerCase();//Befehl
		 
		 if (inputLine.equals("exit")) {exit = false; break;}//Scheife wird beendet
		 //Protocol.processInput fuhrt befehle aus
		 
		 
		 out.println(protocol.processInput(inputLine, name, this.socket.toString(), playlist));
		 System.out.println(inputLine + " processed "
		 +" for " + socket.getInetAddress()
		 + ":"+ socket.getPort());
		 break;
		 }
		 
	 	
	}
 
 	}
      
      //wenn der Spieler exit als Befehl eingibt eird die Werbindung geschlossen und der Player geloscht
   	out.println("Goodbye.");
	playlist.players.remove(name);
	out.close();
	in.close();
	socket.close();
         

      System.out.println("Connection closed  "
	  +" for " + socket.getInetAddress()
	  + ":"+ socket.getPort());
				     
  
    } 
    catch (IOException e) {
    e.printStackTrace();

    }

  }

}
