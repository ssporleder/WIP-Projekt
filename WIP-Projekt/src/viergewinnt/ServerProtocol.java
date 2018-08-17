package viergewinnt;

import java.util.Locale;
import java.util.ResourceBundle;


public class ServerProtocol {

	Spiel pl;
	
	public String processInput (String inputLine, int playerId, String name, String socket, PlayerList playlist, Locale locale) {
		
		//database = new ServerDatabase();
		//Locale locale = new Locale(database.getPlayerLocale(name));
		ResourceBundle bundle = ResourceBundle.getBundle("msgkatalog", locale);
		
		
		if (inputLine.equals(bundle.getString("my.4"))) {
			return (help(locale));
		} 
		
		if (inputLine.equals(bundle.getString("my.5"))) {
			return (version(locale));
		}
		
		if (inputLine.equals(bundle.getString("my.6"))) {
			return (einstellungen(locale));
		}

		if (inputLine.equals(bundle.getString("my.7"))) {
			return (spiel(locale));
		}
		
		if (inputLine.equals(bundle.getString("my.8"))){
			playlist.spielErstellen(name, playerId, socket);
			return(bundle.getString("my.9"));
		}
		
		if (inputLine.equals(bundle.getString("my.10"))) {
			return(playlist.listeSpieler());
		} 
		
		if (inputLine.equals(bundle.getString("my.11"))) {
			return(playlist.listeSpiele());
		}
				
      	if (inputLine.length() > 11) {
		  if (inputLine.substring(0,11).equals("start game:")) {
			Player pl2 = new Player(playlist.getPlayerId(inputLine.substring(12)), (playlist.getPlayerSocket(inputLine.substring(12))));
			Player pl1 = new Player(playerId, socket.toString());
				if (!pl2.name.equals(pl1.name)){	
					if (pl2.status.equals("Wartend")){
					  playlist.starteSpiel(pl2.playerId, playerId, pl2.socket, socket);
					  return(bundle.getString("my.17"));
					}
		  			else {return(bundle.getString("my.18"));}
				}
				else {return(bundle.getString("my.19"));}
		  }
      	}
		
		
		
		return(bundle.getString("my.12"));
	}
	          
	//Die folgenden Befehle stehen zur Verfügung und werden über 'hilfe' angezeigt.
	public String help(Locale locale) {

		ResourceBundle bundle = ResourceBundle.getBundle("msgkatalog", locale);
		/*return ("[Server] Folgende Befehle stehen zur Verfuegung:\r\n"
	        	 +"[Server] 'hilfe' um diese Hilfe anzuzeigen\r\n"
	        	 +"[Server] 'spiel' zeigt das Spielmenue an.\r\n"
				 +"[Server] 'einstellungen' um die Einstellungsmoeglichkeiten anzuzeigen\r\n"
	        	 +"[Server] 'version' um eine Ausgabe zur Version, Autor, Impressum und Kontakt zu erhalten\r\n"
				 +"[Server] 'exit' oder 'quit' beendet die Verbindung.\r\n"
	             );*/
		return(bundle.getString("my.13"));
	}	

	public String version(Locale locale) {
		
		ResourceBundle bundle = ResourceBundle.getBundle("msgkatalog", locale);
		
		/*return ("[Server] Programmname: 4Gewinnt\r\n"
	        	 +"[Server] Version: 0.1\r\n"
	        	 +"[Server] Autoren: Thomas Burmeister, Alexander Franke, Patrick Ostapowicz, Paul Siemens, Sebastian Sporleder\r\n"
				 +"[Server] Impressum: \r\n"
				 +"[Server] Kontakt: \r\n"
	             );*/
		return(bundle.getString("my.14"));
	}
	
	public String einstellungen(Locale locale) {
		
		ResourceBundle bundle = ResourceBundle.getBundle("msgkatalog", locale);
		
		/*return ("[Server] Folgende Einstellungen stehen zur Verfuegung:\r\n"
	           +"[Server] 'passwort' Passwortaenderung\r\n"
	           +"[Server] 'sprache' Festlegung der ausgegebenen Sprache\r\n"
		       );*/
		return(bundle.getString("my.15"));

	}
	
	public String spiel(Locale locale) {
		
		ResourceBundle bundle = ResourceBundle.getBundle("msgkatalog", locale);
		
		/*return ("[Server] 'spieler' Spiel gegen Spieler starten\r\n"
	           +"[Server] 'computer' Spiel gegen Computer starten\r\n"
	           +"[Server] 'liste spieler' Listet die verbundenen Spieler auf\r\n"
	           +"[Server] 'liste spiele' Listet die laufenden Spiele auf\r\n"
		       );*/
		return(bundle.getString("my.16"));

	}
	
}
