package viergewinnt;

public class Player{
	public String name;//Spieler name
	public String status;//Spieler status('Waiting','Playing','Online')
	public int status1;/*Speichert ob der spieler am zug ist(0) oder warten soll(1),
			   auch beim spiel start nutzlich*/
	public int score_wins = 0;//Speichert vie oft der Spieler gewonnen hat
	public int score_looses = 0;//Speichert vie oft der Spieler verloren hat
	public int wincondition =0;/*Hilfs Variable diespeichert ob der spieler 
				   verloren hat oder es eine Remize var*/
	public String socket;
	public int playerId;
	ServerDatabase database;
	public Spiel game;//Spiel
	
	//Erzeugt einen Spieler	
	public Player(String socket){
		
		this.status = "Online";
	 	this.socket = socket;
	 	//this.playerId = playerId;
	 }

	public String getName(int playerId) {
		name = database.getPlayerName(playerId);
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getStatus1() {
		return status1;
	}

	public void setStatus1(int status1) {
		this.status1 = status1;
	}

	public int getScore_wins() {
		return score_wins;
	}

	public void setScore_wins(int score_wins) {
		this.score_wins = score_wins;
	}

	public int getScore_looses() {
		return score_looses;
	}

	public void setScore_looses(int score_looses) {
		this.score_looses = score_looses;
	}

	public int getWincondition() {
		return wincondition;
	}

	public void setWincondition(int wincondition) {
		this.wincondition = wincondition;
	}

	public String getSocket() {
		return socket;
	}

	public void setSocket(String socket) {
		this.socket = socket;
	}
	
	
}
