package viergewinnt;

public class Player{
	public String name;//Spieler name
	public String status;//Spieler status('Waiting','Playing','Online')
	public int status1;/*Speichert ob der spieler am zug ist(0) oder warten soll(1),
			   auch beim spiel start nutzlich*/
	public int anzahlGewonnen = 0;//Speichert vie oft der Spieler gewonnen hat
	public int anzahlVerloren = 0;//Speichert vie oft der Spieler verloren hat
	public int wincondition =0;/*Hilfs Variable diespeichert ob der spieler 
				   verloren hat oder es eine Remize var*/
	public String socket;
	public int playerId;
	ServerDatabase database;
	public Spiel game;//Spiel
	
	//Erzeugt einen Spieler	
	public Player(int playerId, String socket){
		
		database = new ServerDatabase();
		this.name = getName(playerId);
		this.status = database.getPlayerStatusFromId(playerId);
	 	this.socket = socket;
	 	this.playerId = playerId;
	 	//this.status = database.getPlayerStatusFromId(playerId);
	 	this.anzahlGewonnen = database.getAnzahlGewonnen(playerId);
	 	this.anzahlVerloren = database.getAnzahlGewonnen(playerId);
	 	//this.game = new Spiel(database.getPlayerSpielId(playerId));
	 }
	
	public Player (Player pl) {
	}
	

	public String getName(int playerId) {
		name = database.getPlayerName(playerId);
		return name;
	}

	public String getStatus(int playerId) {
		status = database.getPlayerStatusFromId(playerId);
		return status;
	}
	
	public void setStatusPlayer(int playerId, String status){
		database.updatePlayerStatus(playerId, status);
	}
	
	public void setAmZugPlayer(int playerId, int amZug){
		database.updatePlayerAmZug(playerId, amZug);
	}
	
	public int getPlayerAmZug(int playerId) {
		int ergebnis;
		ergebnis = database.getPlayerAmZugFromId(playerId);
		return ergebnis;
	}
	
	public void setPlayerSpielId(int playerId, int spielId){
		database.updatePlayerSpielId(playerId, spielId);
	}
	
	public int getPlayerSpielId(int playerId){
		int spielId = database.getPlayerSpielId(playerId);
		return spielId;
	}
	
}
