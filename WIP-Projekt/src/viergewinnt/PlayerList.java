package viergewinnt;

import viergewinnt.Player;
import viergewinnt.Spiel;


public class PlayerList{
	public int number_of_players;
	//public Map<String, Player> players;
	ServerDatabase database;
	
	public PlayerList(){
		//players = new HashMap<String, Player>();
		database = new ServerDatabase();
	}
	
	//Erzeugt einen neuen Spieler mit Namen name 
	public int newplayer(String socket, String name, int playerId, String passwort){
		playerId = database.insertPlayer(name, "Online", socket, passwort);
		//players.put(name,new Player(playerId, socket));
		number_of_players++;
		return (playerId);
	} 
	
	public int initPlayer(String socket, String name, int playerId, String passwort){
		playerId = database.updatePlayer(playerId, name, "Online", socket, passwort);
		//players.put(name,new Player(playerId, socket));
		number_of_players++;
		return (playerId);
	}
	
	
	
	
	//Pruft ob Spieler bereits verbunden ALT
	//public boolean spielerNichtVerbunden(String name){
	//	Player pl = (Player) players.get(name);
	//	if (pl == null) {return(true);}
	//	else {return(false);}
	//}
	
	public boolean spielerNichtVerbunden(String name){
		String status = "";
		status = database.getPlayerStatus(name);
		//String tmp_status = "Offline"; 
		if (status.equals("") || status.equals("Offline")) {
			return true;
		}
			else {
				return false;
			}
	}
	
	
    public int getPlayerId(String name){
    	int playerId = 0;
    	playerId = database.getPlayerId(name);
    return(playerId);
    }
    
    public String getPlayerSocket(String name){
    	String playerSocket = "";
    	playerSocket = database.getPlayerSocket(name);
    return(playerSocket);
    }
    
    public String getPlayerPasswort(int id){
    	//String playerPasswort = "";
    	String playerPasswort = database.getPlayerPasswort(id);
    	return playerPasswort;
    }
    

	public String listeSpieler(){
		String ergebnis = new String();
		ergebnis = database.listeSpieler();
		
		return ergebnis;
	}
	
	public String listeSpiele(){
		String ergebnis = new String();
		ergebnis = database.listeSpiele();
		
		return ergebnis;
	}
    
    public void starteSpiel(int playerId1, int playerId2, String socket1, String socket2){
    	Player pl1 = new Player(playerId1, socket1);
    	Player pl2 = new Player(playerId2, socket2);
		pl1.setStatusPlayer(playerId1, "Spielt");
		pl2.setStatusPlayer(playerId2, "Spielt");
		pl1.status1 = 1;
		pl2.status1 = 0;
		pl1.game = new Spiel(pl1.getName(playerId1),pl2.getName(playerId2));
		pl2.game = pl1.game;
	//	pl1.status = "playing";
	//	pl2.status = "playing";
	}
	
	public void spielErstellen(String name, int playerId, String socket){
		Player pl = new Player(playerId, socket.toString());
		//pl.status = "Wartend";
		//String status = "Wartend";
		pl.setStatusPlayer(playerId,"Wartend");
		database.insertSpiel(name, "Mitspieler gesucht");
	}
		
	//public void antwort(Spiel ourGame){
	//	Player pl2 = (Player) players.get(ourGame.spieler2);
	//	Player pl1 = (Player)  players.get(ourGame.spieler1);
	//	pl2.status1 = 0;
	//	pl1.status1 = 0;
	//	pl2.status = "Online";	
	//	pl1.status = "Online";
	//}

	public void spielAkzeptiert(Spiel ourGame){
	//	Player pl1 = (Player) players.get(ourGame.spieler1);
	//	pl1.game.status = 1;
	}
	
	public void spielAbgelehnt(Spiel ourGame){
	//	Spieler pl2 = (Spieler) players.get(ourGame.pl2name);
	//	Spieler pl1 = (Spieler) players.get(ourGame.pl1name);
	//	pl2.status1 = 0;
	//	pl1.status1 = 0;
	//	pl2.status = "Online";	
	//	pl1.status = "Online";
		
	}
    
    //public void wechsel(String name1, String name2){
	//	int change = 0; 
	//	Player pl1 = (Player) players.get(name1);
	//	Player pl2 = (Player) players.get(name2);
	//	change = pl1.status1;
	//	pl1.status1 = pl2.status1;
	//	pl2.status1 = change;
	//
	//}
    
    //public void sieg(String name1, String name2){
    //	Player pl1 = (Player) players.get(name1);
	//	Player pl2 = (Player) players.get(name2);
	//	pl2.status1 = 0;
	//	pl1.status1 = 0;
	//	pl1.game.status = 0;
	//	pl2.status = "Online";	
	//	pl1.status = "Online";
	//	pl1.anzahlGewonnen++;
	//	pl2.anzahlVerloren++;
	//}
	
	//public void keinSieg(String name1, String name2){
	//	Player pl1 = (Player) players.get(name1);
	//	Player pl2 = (Player) players.get(name2);
	//	pl2.status1 = 0;
	//	pl1.status1 = 0;
	//	pl1.game.status = 0;
	//	pl2.status = "Online";	
	//	pl1.status = "Online";
	//	pl2.wincondition = 1;
	//}
	
}
