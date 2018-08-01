package spielpaket;
import java.io.*;

import spielpaket.*;
public class SpielProtokoll {

	

	public String processInput (String inputLine, String name, String socket, SpielerListe playlist) {
		
		if (inputLine.equals("help")) {
			return (hello());
		}
		//Schreibt alle Spieler aus die sich auf dem Server befinden und ihre Statuse 
		if (inputLine.equals("show players")) {
			return(playlist.writelist());
		} 
		//Endert Spieler Status auf Waiting
		if (inputLine.equals("wait for game")){playlist.wait(name);return("Waiting for request");}
		
		/*Startet das spiel. Das inputLine wird in zwei Str
		ing geteilt. 1. befehl 2. Spieler mit dem er Spielen mochte*/				
      		if (inputLine.length() > 11) {
		  if (inputLine.substring(0,11).equals("start game:")) {
			Spieler pl2 = (Spieler) playlist.players.get(inputLine.substring(12));
			Spieler pl1 = (Spieler) playlist.players.get(name);
			//Pruft ob der eingegebene Spieler existiert
			if (pl2 != null){
				//Pruft ob der eingegebene Spieler nicht er selbst ist
				if (!pl2.name.equals(pl1.name)){	
					//Pruft ob der eingegebene Spieler auf ein Spiel wartet
					if (pl2.status.equals("waiting")){
			        	  //Spiel wird gestartet
					  playlist.start(pl2.name, name);
					  return("game started waiting for player to accept");
					}
		  			else {return("Player istn't waiting for game");}
				}
				else {return("you cannot play with you");}
			}
			else {return("such user does not exist");}
		  } 
		}
		return("");	
	}  
      
          
	//Reprezentiert Hilfe 
	public String hello () {
		return ("Wellcome to 4 winns server\n"
	        	 +"Say 'show players' for showing all connected players\n"
	        	 +"Say 'help' for help\n"
	                 +"Say 'start game: player' to start a geme\n"
	                 +"Say 'exit' to quit\n"
	                 +"Say 'wait for game' to wait for game\n"
		       );
	}	

}
