package viergewinnt;

import java.util.*;
import viergewinnt.Player;


//PLAYERLIST asdasdas
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
	
	public int initPlayer(String socket, String name, int playerId, String passwort){
		players.put(name,new Player(socket));
		playerId = database.updatePlayer(playerId, name, "Online", socket, passwort);
		number_of_players++;
		return (playerId);
	}
	
	public void setStatusPlayer(String socket, int playerId, String status){
		database.updatePlayerStatus(playerId, status, socket);
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
    
    public void start(String name2, String name1){
    	Player sp1 = (Player)  players.get(name2);
    	Player sp2 = (Player)  players.get(name1);
		sp2.status1 = 1;
		sp1.status1 = 0;
		sp1.game = new Spiel(name1,name2);
		sp2.game = sp1.game;
		sp1.status = "playing";
		sp2.status = "playing";
	}
	
	public void warten(String name){
		Player sp = (Player)  players.get(name);
		sp.status = "waiting";
	}
		
	public void antwort(Spiel ourGame){
		Player sp2 = (Player) players.get(ourGame.spieler2);
		Player sp1 = (Player)  players.get(ourGame.spieler1);
		sp2.status1 = 0;
		sp1.status1 = 0;
		sp2.status = "Online";	
		sp1.status = "Online";
		
	}

	public void accept(Spiel ourGame){
		Player sp1 = (Player) players.get(ourGame.spieler1);
		sp1.game.status = 1;
	}
    
    public void wechsel(String name1, String name2){
		int change = 0; 
		Player sp1 = (Player) players.get(name1);
		Player sp2 = (Player) players.get(name2);
		change = sp1.status1;
		sp1.status1 = sp2.status1;
		sp2.status1 = change;
	
	}
    
    public void sieg(String name1, String name2){
    	Player sp1 = (Player) players.get(name1);
		Player sp2 = (Player) players.get(name2);
		sp2.status1 = 0;
		sp1.status1 = 0;
		sp1.game.status = 0;
		sp2.status = "Online";	
		sp1.status = "Online";
		sp1.score_wins++;
		sp2.score_looses++;
	}
	
	public void keinSieg(String name1, String name2){
		Player sp1 = (Player) players.get(name1);
		Player sp2 = (Player) players.get(name2);
		sp2.status1 = 0;
		sp1.status1 = 0;
		sp1.game.status = 0;
		sp2.status = "Online";	
		sp1.status = "Online";
		sp2.wincondition = 1;
	}
	
}
