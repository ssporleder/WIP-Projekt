package viergewinnt;

import spielpaket.SpielerListe;

public class GameProtocol {

	public String processInput (String inputLine, String name, String socket, SpielerListe playlist) {
		
		if (inputLine.equals("hilfe")) {
			return (help());
		}
		return("");	
	}
	          
	//Reprezentiert Hilfe 
	public String help () {
		return ("Folgende Kommandos stehen zur Verfuegung:\r\n"
	        	 +"'hilfe' um diese Hilfe anzuzeigen\r\n"
	             );
	}	

}
