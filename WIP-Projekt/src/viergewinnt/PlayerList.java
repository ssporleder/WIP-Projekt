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
	public int newplayer(String socket, String name, int playerId, String passwort){
		players.put(name,new Player(socket));
		playerId = database.insertPlayer(name, "Online", socket, passwort);
		number_of_players++;
		return (playerId);
	} 
	
	
	//Pruft ob Spieler mit Namen name existiert
	public boolean spielerNichtVerbunden(String name){
		Player pl = (Player) players.get(name);
		if (pl == null) {return(true);}
		else {return(false);}
	}
	
    public int getPlayerId(String name){
    	int playerId = 0;
    	playerId = database.getPlayerId(name);
    return(playerId);
    }
    
    public String getPlayerPasswort(int id){
    	//String playerPasswort = "";
    	String playerPasswort = database.getPlayerPasswort(id);
    	return playerPasswort;
    }
    
	
}
