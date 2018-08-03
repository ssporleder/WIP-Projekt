package viergewinnt;

public class GameProtocol {

	public String processInput (String inputLine, String name, String socket, PlayerList playlist) {
		
		if (inputLine.equals("hilfe")) {
			return (help());
		} 
		return("Dieser Befehl ist nicht bekannt.\r\nRufen Sie die Hilfe mit 'hilfe' auf.\r\n");	
	}
	          
	//Reprezentiert Hilfe 
	public String help () {
		return ("Folgende Befehle stehen zur Verfuegung:\r\n"
	        	 +"'hilfe' um diese Hilfe anzuzeigen\r\n"
				 +"'exit' beendet die Verbindung.\r\n"
	             );
	}	

}
