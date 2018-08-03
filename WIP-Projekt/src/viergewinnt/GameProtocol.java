package viergewinnt;

public class GameProtocol {

	public String processInput (String inputLine, String name, String socket, PlayerList playlist) {
		
		if (inputLine.equals("hilfe")) {
			return (help());
		} 
		return("Dieser Befehl ist nicht bekannt.\r\nRufen Sie die Hilfe mit 'hilfe' auf.\r\n");	
	}
	          
	//Die folgenden Befehle stehen zur Verfügung und werden über 'hilfe' angezeigt.
	public String help () {
		return ("Folgende Befehle stehen zur Verfuegung:\r\n"
	        	 +"'hilfe' um diese Hilfe anzuzeigen\r\n"
				 +"'exit' oder 'quit' beendet die Verbindung.\r\n"
	             );
	}	

}
