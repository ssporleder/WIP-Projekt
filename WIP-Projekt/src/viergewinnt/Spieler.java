
//Der folgende Code wurde durch Sebastian Sporleder erstellt.

package viergewinnt;

public class Spieler{
	public String name;
	public String status;
	public String socket;
	public int playerId;
	ServerDatabase database;
	public Spiel game;
	
	//Erzeugt einen Spieler	
	public Spieler(int playerId, String socket){
		
		database = new ServerDatabase();
		this.name = getName(playerId);
		this.status = database.getPlayerStatusFromId(playerId);
	 	this.socket = socket;
	 	this.playerId = playerId;
	 }
	
	//Methode um zu einer SpielerId den passenden Spielernamen zu ermitteln
	public String getName(int playerId) {
		name = database.getPlayerName(playerId);
		return name;
	}

	//Methode um zu einer SpielerId den passenden Status des Spielers zu ermitteln
	public String getStatus(int playerId) {
		status = database.getPlayerStatusFromId(playerId);
		return status;
	}
	
	//Methode um zu einer SpielerId den Status zu setzen
	public void setStatusPlayer(int playerId, String status){
		database.updatePlayerStatus(playerId, status);
	}
	
	//Methode um zu einer SpielerId das eingegebene Passwort zu setzen
	public void setStatusPassword(int playerId, String password){
		database.updatePlayerPassword(playerId, password);
	}
	
	//Methode um zu einer SpielerId die eingegebene locale zu hinterlegen
	public void setStatusLocale(int playerId, String locale){
		database.updatePlayerLocale(playerId, locale);
	}
	
	//Methode um zu einer SpielerId zu hinterlegen das dieser in einem Spiel am Zug ist.
	public void setAmZugPlayer(int playerId, int amZug){
		database.updatePlayerAmZug(playerId, amZug);
	}
	
	//Methode um zu ermitteln, welcher Spieler in einem Spiel "am Zug" ist.
	public int getPlayerAmZug(int playerId) {
		int ergebnis;
		ergebnis = database.getPlayerAmZugFromId(playerId);
		return ergebnis;
	}
	
	//Methode um zu einem Spiel Spieler1 und Spieler2 festzulegen.
	public void setPlayerSpielId(int playerId, int spielId){
		database.updatePlayerSpielId(playerId, spielId);
	}
	
	//Methode um herauszufinden welches Spiel ein Spieler zur Zeit spielt.
	public int getPlayerSpielId(int playerId){
		int spielId = database.getPlayerSpielId(playerId);
		return spielId;
	}
	
}
