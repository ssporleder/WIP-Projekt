
//Der folgende Code wurde durch Sebastian Sporleder erstellt.

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
	
	//Initialisiert einen neuen Spieler bei einer neuen Verbindung mit den Parametern "Status", "Socket" und "Passwort"
	public int initPlayer(String socket, String name, int playerId, String passwort){
		playerId = database.updatePlayer(playerId, name, "Online", socket, passwort);
		return (playerId);
	}
		
	//MEthode die einen boolschen Wert zurückliefert, ob ein Spieler bereits verbunden ist oder nicht.
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
	
	
	//Methode um zu einer SpielerId den Spielernamen zu ermitteln.
    public int getPlayerId(String name){
    	int playerId = 0;
    	playerId = database.getPlayerId(name);
    return(playerId);
    }
    
    //Methode um zu einem Spieler den Socket zu ermitteln
    public String getPlayerSocket(String name){
    	String playerSocket = "";
    	playerSocket = database.getPlayerSocket(name);
    return(playerSocket);
    }
    
    //Methode um zu einem Spieler das Passwort zu ermitteln
    public String getPlayerPasswort(int id){
    	String playerPasswort = database.getPlayerPasswort(id);
    	return playerPasswort;
    }
    
    //Methode um aus der Datenbank die Spielerliste abzurufen
	public String listeSpieler(){
		String ergebnis = new String();
		ergebnis = database.listeSpieler();
		return ergebnis;
	}
	
	//Methode um aus der Datenbank die Spielliste abzurufen
	public String listeSpiele(){
		String ergebnis = new String();
		ergebnis = database.listeSpiele();
		return ergebnis;
	}
    
	//Beim Aufruf von starteSpiel werden die Spieler des Spiels mit dem Status "Spielt" versehen. Ausserdem wird in die Datenbank geschrieben welcher Spieler den erstne Zug macht. Ausserdem wird in die Tabelle Spiel im Spiel hinterlegt wer die Spieler des Spiels sind.
    public void starteSpiel(int playerId1, int playerId2, String socket1, String socket2){
    	Spieler pl2 = new Spieler(playerId1, socket1);
    	Spieler pl1 = new Spieler(playerId2, socket2);
		pl1.setStatusPlayer(playerId1, "Spielt");
		pl2.setStatusPlayer(playerId2, "Spielt");
		pl1.setAmZugPlayer(playerId2, 0);
		pl2.setAmZugPlayer(playerId1, 1);
		pl1.setPlayerSpielId(playerId2, pl2.getPlayerSpielId(playerId1));
		database.updateSpielSpieler2(pl2.getPlayerSpielId(playerId1), pl2.getName(playerId2));
	}
	
    //Ein neues Spiel wird erstellt. Hierzu wird der beginnende Spieler auf "Wartend" gesetzt. Ein Eintrag in die Spieltabelle erzeugt und der erste Spieler eingetragen.
	public void spielErstellen(String name, int playerId, String socket){
		Spieler pl = new Spieler(playerId, socket.toString());
		pl.setStatusPlayer(playerId,"Wartend");
		int spielId = database.insertSpiel(name, "Mitspieler gesucht");
		pl.setPlayerSpielId(playerId, spielId);
	}

	//Erzeugt ein neues Spiel gegen den Computer. Hierbei wird der Status des Spielers eingestellt ein Spiel in der Tabelle Spiel erzeugt und Spieler1 und Spieler2 festgelegt.
	public void spielErstellenComputer(String name, int playerId, String socket){
		Spieler pl = new Spieler(playerId, socket.toString());
		pl.setStatusPlayer(playerId,"Spielt gegen Computer");
		int spielId = database.insertSpiel(name, "Schwierigkeitsgrad wird eingestellt");
		pl.setPlayerSpielId(playerId, spielId);
		database.updateSpielSpieler2(spielId, "computer");
	}


	public void spielAkzeptiert(int spielId){
		//Durch diese zufallsZahl wird mit einer Chance von 50:50 festgelegt wer beginnt.
		int zufallsZahl = (Math.random()<0.5)?0:1;
		if (zufallsZahl == 0) {
			database.updatePlayerAmZug(database.getPlayerId(database.getSpielSpieler1FromId(spielId)), 1);
			database.updatePlayerAmZug(database.getPlayerId(database.getSpielSpieler2FromId(spielId)), 0);
		} else {
			database.updatePlayerAmZug(database.getPlayerId(database.getSpielSpieler1FromId(spielId)), 0);
			database.updatePlayerAmZug(database.getPlayerId(database.getSpielSpieler2FromId(spielId)), 1);
		}
		String status = "Gestartet";
		database.updateSpielStatus(spielId, status);
	}
	
	//Wird ein Spiel abgelehnt so werden die beiden Spieler wieder auf "Online" gesetzt, das Spiel mit dem Status "Abgelehnt" versehen.
	public void spielAbgelehnt(int spielId){
		database.updatePlayerAmZug(database.getPlayerId(database.getSpielSpieler1FromId(spielId)), 0);
		database.updatePlayerAmZug(database.getPlayerId(database.getSpielSpieler2FromId(spielId)), 0);	
		database.updatePlayerStatus(database.getPlayerId(database.getSpielSpieler1FromId(spielId)), "Online");
		database.updatePlayerStatus(database.getPlayerId(database.getSpielSpieler2FromId(spielId)), "Online");
		database.updatePlayerSpielId(database.getPlayerId(database.getSpielSpieler1FromId(spielId)), 0);
		database.updatePlayerSpielId(database.getPlayerId(database.getSpielSpieler2FromId(spielId)), 0);
		database.updateSpielStatus(spielId, "Abgelehnt");	
	}
    
	//Nach jedem Spielzug wird mit dieser Funktion der Wechsel der Spieler durchgeführt. In die Tabelle Spiel wird geschrieben wer am Zug ist.
    public void wechsel(int spielId){
		int change = 0; 
		change = database.getPlayerAmZugFromId(database.getPlayerId(database.getSpielSpieler1FromId(spielId)));
		database.updatePlayerAmZug(database.getPlayerId(database.getSpielSpieler1FromId(spielId)), database.getPlayerAmZugFromId(database.getPlayerId(database.getSpielSpieler2FromId(spielId))));
		database.updatePlayerAmZug(database.getPlayerId(database.getSpielSpieler2FromId(spielId)), change);
	}
    
    //Beim Sieg des Spieler1 wird das Spiel beendet, die Einträge in der Datenbank vorgenommen.
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
    
  //Beim Sieg des Spieler2 wird das Spiel beendet, die Einträge in der Datenbank vorgenommen.
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
	
    //Auch bei einem Unentschieden werden die Einträge in der jeweiligen Datenbank Tabelle Spieler und Spiel vorgenommen.
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
