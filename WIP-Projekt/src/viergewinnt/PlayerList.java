package viergewinnt;

import java.util.*;


public class PlayerList{
	public int number_of_players;
	public Map<String, Player> players;

	public PlayerList(){
		players = new HashMap<String, Player>();
	}
	
	//Erzeugt einen neuen Spieler mit Namen name 
	public void newplayer(String socket, String name){
		players.put(name,new Player(socket));
		number_of_players++;
	}
	
	//Pruft ob Spieler mit Namen name existiert
	public boolean spielerExistiert(String name){
		Player pl = (Player) players.get(name);
		if (pl == null) {return(true);}
		else {return(false);}
	}
	
}
