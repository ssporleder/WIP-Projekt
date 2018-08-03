package spielpaket;
import java.lang.*;
import java.util.*;

import spielpaket.*;

public class SpielerListe{
	public int number_of_players;
	public Map players;
	
	//Erzeugt playerlist mit einer HashMap von Players
	public SpielerListe(){
		players = new HashMap();
	}
	
	//Erzeugt einen neuen Spieler mit Namen name 
	public void newplayer(String socket, String name){
		players.put(name,new Spieler(socket));
		number_of_players++;
	}
	
	//Pruft ob Spieler mit Namen name existiert
	public boolean contact(String name){
		Spieler pl = (Spieler) players.get(name);
		if (pl == null) {return(true);}
		else {return(false);}
	}
	
	//Schreibt alle Spieler aus die sich auf dem Server befinden und ihre Statuse 
	public String writelist(){
		String str = new String();
		Iterator it = players.keySet().iterator();


		while(it.hasNext()){
			String aKey = (String) it.next();
			Spieler pl = (Spieler) players.get(aKey);
			str = str +"Socket: "+pl.socket+" Name: "+ pl.name +" Status: "+ pl.status+" Score(wins/looses): "+ pl.score_wins+"/"+pl.score_looses+"\r\n"; 	
		}
		return str;
	}
	
	//Diese Metoden endern die Spieler und Spiel Statuse 
	public void start(String name2, String name1){
		Spieler pl2 = (Spieler) players.get(name2);
		Spieler pl1 = (Spieler) players.get(name1);
		pl2.status1 = 1;
		pl1.status1 = 0;
		pl1.game = new Spiel(name1,name2);
		pl2.game = pl1.game;
		pl1.status = "playing";
		pl2.status = "playing";
	}
	
	public void wait(String name){
		Spieler pl = (Spieler) players.get(name);
		pl.status = "waiting";
	}
		
	public void reject(Spiel ourGame){
		Spieler pl2 = (Spieler) players.get(ourGame.pl2name);
		Spieler pl1 = (Spieler) players.get(ourGame.pl1name);
		pl2.status1 = 0;
		pl1.status1 = 0;
		pl2.status = "Online";	
		pl1.status = "Online";
		
	}

	public void accept(Spiel ourGame){
		Spieler pl1 = (Spieler) players.get(ourGame.pl1name);
		pl1.game.status = 1;
	}

	public void change(String name1, String name2){
		int change = 0; 
		Spieler pl1 = (Spieler) players.get(name1);
		Spieler pl2 = (Spieler) players.get(name2);
		change = pl1.status1;
		pl1.status1 = pl2.status1;
		pl2.status1 = change;
	
	}

	public void win(String name1, String name2){
		Spieler pl1 = (Spieler) players.get(name1);
		Spieler pl2 = (Spieler) players.get(name2);
		pl2.status1 = 0;
		pl1.status1 = 0;
		pl1.game.status = 0;
		pl2.status = "Online";	
		pl1.status = "Online";
		pl1.score_wins++;
		pl2.score_looses++;
	}
	
	public void nowin(String name1, String name2){
		Spieler pl1 = (Spieler) players.get(name1);
		Spieler pl2 = (Spieler) players.get(name2);
		pl2.status1 = 0;
		pl1.status1 = 0;
		pl1.game.status = 0;
		pl2.status = "Online";	
		pl1.status = "Online";
		pl2.wincondition = 1;
	}
	
		
}
