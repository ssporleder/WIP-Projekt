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
	//public Spiel game;//Spiel
	
	//Erzeugt einen Spieler	
	public Player(String socket){
	 	this.status = "Online";
	 	this.socket = socket;
	 }
	
	
}
