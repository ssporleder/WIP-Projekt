package viergewinnt;

public class ServerProtocol {

	public String processInput (String inputLine, int playerId, String name, String socket, PlayerList playlist) {
		
		if (inputLine.equals("hilfe")) {
			return (help());
		} 
		
		if (inputLine.equals("version")) {
			return (version());
		}
		
		if (inputLine.equals("einstellungen")) {
			return (einstellungen());
		}

		if (inputLine.equals("spiel")) {
			return (spiel());
		}
		
		//Auskommentier bis vollständig auf SQL umgestellt
		if (inputLine.equals("spieler")){
			playlist.spielErstellen(name, playerId, socket);
			return("[Server] Warte auf anderen Spieler");
		}
		
		if (inputLine.equals("liste")) {
			return(playlist.listeSpieler());
		} 
		
		return("[Server] Dieser Befehl ist nicht bekannt.\r\n[Server] Rufen Sie die Hilfe mit 'hilfe' auf.\r\n");
	}
	          
	//Die folgenden Befehle stehen zur Verfügung und werden über 'hilfe' angezeigt.
	public String help() {
		return ("[Server] Folgende Befehle stehen zur Verfuegung:\r\n"
	        	 +"[Server] 'hilfe' um diese Hilfe anzuzeigen\r\n"
	        	 +"[Server] 'spiel' zeigt das Spielmenue an.\r\n"
				 +"[Server] 'einstellungen' um die Einstellungsmoeglichkeiten anzuzeigen\r\n"
	        	 +"[Server] 'version' um eine Ausgabe zur Version, Autor, Impressum und Kontakt zu erhalten\r\n"
				 +"[Server] 'exit' oder 'quit' beendet die Verbindung.\r\n"
	             );
	}	

	public String version() {
		return ("[Server] Programmname: 4Gewinnt\r\n"
	        	 +"[Server] Version: 0.1\r\n"
	        	 +"[Server] Autoren: Thomas Burmeister, Alexander Franke, Patrick Ostapowicz, Paul Siemens, Sebastian Sporleder\r\n"
				 +"[Server] Impressum: \r\n"
				 +"[Server] Kontakt: \r\n"
	             );
	}
	
	public String einstellungen() {
		return ("[Server] Folgende Einstellungen stehen zur Verfuegung:\r\n"
	           +"[Server] 'passwort' Passwortaenderung\r\n"
	           +"[Server] 'sprache' Festlegung der ausgegebenen Sprache\r\n"
		       );

	}
	
	public String spiel() {
		return ("[Server] 'spieler' Spiel gegen Spieler starten\r\n"
	           +"[Server] 'computer' Spiel gegen Computer starten\r\n"
	           +"[Server] 'liste' Listet die verbundenen Spieler auf\r\n"
		       );

	}
	
}
