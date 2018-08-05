package viergewinnt;

import java.util.*;


public class PlayerList{
	public int number_of_players;
	public Map<String, Player> players;
	ServerDatabase database;
	
	public PlayerList(){
		players = new HashMap<String, Player>();
		database = new ServerDatabase();
	}
	
	//Erzeugt einen neuen Spieler mit Namen name 
	public void newplayer(String socket, String name){
		players.put(name,new Player(socket));
		database.insertPlayer(name, "Online");
		number_of_players++;
	}
	
	
	//Pruft ob Spieler mit Namen name existiert
	public boolean spielerExistiert(String name){
		Player pl = (Player) players.get(name);
		if (pl == null) {return(true);}
		else {return(false);}
	}
	
    public int spielerBekannt(String name){
    	int playerId = database.getPlayerId(name);
    return(playerId);
    }
	
}
