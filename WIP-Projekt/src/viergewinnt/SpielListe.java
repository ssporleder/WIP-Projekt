package viergewinnt;

import viergewinnt.Spieler;

public class SpielListe{
	ServerDatabase database;
	
	public SpielListe(){
		database = new ServerDatabase();
	}
	
	//Erzeugt einen neuen Spieler mit Namen name 
	public int newplayer(String socket, String name, int playerId, String passwort){
		playerId = database.insertPlayer(name, "Online", socket, passwort);
		return (playerId);
	} 
	
	public int initPlayer(String socket, String name, int playerId, String passwort){
		playerId = database.updatePlayer(playerId, name, "Online", socket, passwort);
		return (playerId);
	}
		
	public boolean spielerNichtVerbunden(String name){
		String status = "";
		status = database.getPlayerStatus(name);
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
    	Spieler pl2 = new Spieler(playerId1, socket1);
    	Spieler pl1 = new Spieler(playerId2, socket2);
		pl1.setStatusPlayer(playerId1, "Spielt");
		pl2.setStatusPlayer(playerId2, "Spielt");
		pl2.setAmZugPlayer(playerId1, 1);
		pl1.setAmZugPlayer(playerId2, 0);
		pl1.setPlayerSpielId(playerId2, pl2.getPlayerSpielId(playerId1));
		database.updateSpielSpieler2(pl2.getPlayerSpielId(playerId1), pl2.getName(playerId2));
	}
	
	public void spielErstellen(String name, int playerId, String socket){
		Spieler pl = new Spieler(playerId, socket.toString());
		pl.setStatusPlayer(playerId,"Wartend");
		int spielId = database.insertSpiel(name, "Mitspieler gesucht");
		pl.setPlayerSpielId(playerId, spielId);
}
		
	//public void antwort(Spiel ourGame){
	//	Player pl2 = (Player) players.get(ourGame.spieler2);
	//	Player pl1 = (Player)  players.get(ourGame.spieler1);
	//	pl2.status1 = 0;
	//	pl1.status1 = 0;
	//	pl2.status = "Online";	
	//	pl1.status = "Online";
	//}

	public void spielAkzeptiert(int spielId){
		String status = "Gestartet";
		database.updateSpielStatus(spielId, status);
	}
	
	public void spielAbgelehnt(int spielId){
		database.updatePlayerAmZug(database.getPlayerId(database.getSpielSpieler1FromId(spielId)), 0);
		database.updatePlayerAmZug(database.getPlayerId(database.getSpielSpieler2FromId(spielId)), 0);	
		database.updatePlayerStatus(database.getPlayerId(database.getSpielSpieler1FromId(spielId)), "Online");
		database.updatePlayerStatus(database.getPlayerId(database.getSpielSpieler2FromId(spielId)), "Online");
		database.updatePlayerSpielId(database.getPlayerId(database.getSpielSpieler1FromId(spielId)), 0);
		database.updatePlayerSpielId(database.getPlayerId(database.getSpielSpieler2FromId(spielId)), 0);
		database.updateSpielStatus(spielId, "Abgelehnt");	
	}
    
    public void wechsel(int spielId){
		int change = 0; 
		change = database.getPlayerAmZugFromId(database.getPlayerId(database.getSpielSpieler1FromId(spielId)));
		database.updatePlayerAmZug(database.getPlayerId(database.getSpielSpieler1FromId(spielId)), database.getPlayerAmZugFromId(database.getPlayerId(database.getSpielSpieler2FromId(spielId))));
		database.updatePlayerAmZug(database.getPlayerId(database.getSpielSpieler2FromId(spielId)), change);
	}
    
    public void siegSpieler1(int spielId){
    	database.updateSpielStatus(spielId, "Beendet");
    	database.updateSpielGewinner(spielId, database.getSpielSpieler1FromId(spielId));
    	database.updatePlayerAmZug(database.getPlayerId(database.getSpielSpieler1FromId(spielId)), 0);
		database.updatePlayerAmZug(database.getPlayerId(database.getSpielSpieler2FromId(spielId)), 0);	
		database.updatePlayerStatus(database.getPlayerId(database.getSpielSpieler1FromId(spielId)), "Online");
		database.updatePlayerStatus(database.getPlayerId(database.getSpielSpieler2FromId(spielId)), "Online");
		int tmp1 = database.getAnzahlGewonnen(database.getPlayerId(database.getSpielSpieler1FromId(spielId)));
		int tmp2 = database.getAnzahlVerloren(database.getPlayerId(database.getSpielSpieler2FromId(spielId)));
		tmp1++;
		tmp2++;
		database.updatePlayerGewonnen(database.getPlayerId(database.getSpielSpieler1FromId(spielId)), tmp1);
		database.updatePlayerVerloren(database.getPlayerId(database.getSpielSpieler2FromId(spielId)), tmp2);
		database.updatePlayerSpielId(database.getPlayerId(database.getSpielSpieler1FromId(spielId)), 0);
		database.updatePlayerSpielId(database.getPlayerId(database.getSpielSpieler2FromId(spielId)), 0);
		database.updateSpielUnentschieden(database.getPlayerId(database.getSpielSpieler2FromId(spielId)), 0);
	}
    
    public void siegSpieler2(int spielId){
    	database.updateSpielStatus(spielId, "Beendet");
    	database.updateSpielGewinner(spielId, database.getSpielSpieler2FromId(spielId));
    	database.updatePlayerAmZug(database.getPlayerId(database.getSpielSpieler1FromId(spielId)), 0);
    	database.updatePlayerAmZug(database.getPlayerId(database.getSpielSpieler2FromId(spielId)), 0);	
    	database.updatePlayerStatus(database.getPlayerId(database.getSpielSpieler1FromId(spielId)), "Online");
    	database.updatePlayerStatus(database.getPlayerId(database.getSpielSpieler2FromId(spielId)), "Online");
    	int tmp1 = database.getAnzahlGewonnen(database.getPlayerId(database.getSpielSpieler2FromId(spielId)));
    	int tmp2 = database.getAnzahlVerloren(database.getPlayerId(database.getSpielSpieler1FromId(spielId)));
    	tmp1++;
    	tmp2++;
    	database.updatePlayerGewonnen(database.getPlayerId(database.getSpielSpieler2FromId(spielId)), tmp1);
    	database.updatePlayerVerloren(database.getPlayerId(database.getSpielSpieler1FromId(spielId)), tmp2);
    	database.updatePlayerSpielId(database.getPlayerId(database.getSpielSpieler1FromId(spielId)), 0);
    	database.updatePlayerSpielId(database.getPlayerId(database.getSpielSpieler2FromId(spielId)), 0);
    	database.updateSpielUnentschieden(database.getPlayerId(database.getSpielSpieler2FromId(spielId)), 0);
    	}
	
	public void keinSieg(int spielId){
		database.updateSpielStatus(spielId, "Beendet");
		database.updatePlayerAmZug(database.getPlayerId(database.getSpielSpieler1FromId(spielId)), 0);
		database.updatePlayerAmZug(database.getPlayerId(database.getSpielSpieler2FromId(spielId)), 0);	
		database.updatePlayerStatus(database.getPlayerId(database.getSpielSpieler1FromId(spielId)), "Online");
		database.updatePlayerStatus(database.getPlayerId(database.getSpielSpieler2FromId(spielId)), "Online");
		database.updatePlayerSpielId(database.getPlayerId(database.getSpielSpieler1FromId(spielId)), 0);
		database.updatePlayerSpielId(database.getPlayerId(database.getSpielSpieler2FromId(spielId)), 0);
		database.updateSpielUnentschieden(database.getPlayerId(database.getSpielSpieler2FromId(spielId)), 1);
	}
	
}
