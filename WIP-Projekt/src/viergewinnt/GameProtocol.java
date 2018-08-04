package viergewinnt;

public class GameProtocol {

	public String processInput (String inputLine, String name, String socket, PlayerList playlist) {
		
		if (inputLine.equals("hilfe")) {
			return (help());
		} 
		
		if (inputLine.equals("version")) {
			return (version());
		}
		
		
		return("Dieser Befehl ist nicht bekannt.\r\nRufen Sie die Hilfe mit 'hilfe' auf.\r\n");	
	}
	          
	//Die folgenden Befehle stehen zur Verfügung und werden über 'hilfe' angezeigt.
	public String help () {
		return ("Folgende Befehle stehen zur Verfuegung:\r\n"
	        	 +"'hilfe' um diese Hilfe anzuzeigen\r\n"
	        	 +"'version' um eine Ausgabe zur Version, Autor, Impressum und Kontakt zu erhalten\r\n"
				 +"'exit' oder 'quit' beendet die Verbindung.\r\n"
	             );
	}	

	public String version () {
		return ("Programmname: 4Gewinnt\r\n"
	        	 +"Version: 0.1\r\n"
	        	 +"Autoren: Thomas Burmeister, Alexander Franke, Patrick Ostapowicz, Paul Siemens, Sebastian Sporleder\r\n"
				 +"Impressum: \r\n"
				 +"Kontakt: \r\n"
	             );
	}
}
