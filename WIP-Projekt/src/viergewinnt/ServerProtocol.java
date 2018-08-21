package viergewinnt;

import java.util.Locale;
import java.util.ResourceBundle;


public class ServerProtocol {

	Spiel pl;
	
	public String processInput (String inputLine, int playerId, String name, String socket, SpielListe playlist, Locale locale) {
		
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
		
		if (inputLine.equals("computer")){
			playlist.spielErstellenComputer(name, playerId, socket);
			return(bundle.getString("my.43"));
		}
		
		if (inputLine.equals(bundle.getString("my.10"))) {
			return(playlist.listeSpieler());
		} 
		
		if (inputLine.equals(bundle.getString("my.11"))) {
			return(playlist.listeSpiele());
		}
			
		if (inputLine.startsWith(bundle.getString("my.33"))) {
			if (bundle.getString("my.33").equals("start game:")) {
				Spieler pl2 = new Spieler(playlist.getPlayerId(inputLine.substring(12)), (playlist.getPlayerSocket(inputLine.substring(12))));
				Spieler pl1 = new Spieler(playerId, socket.toString());
					if (!pl2.name.equals(pl1.name)){	
						if (pl2.status.equals("Wartend")){
						  playlist.starteSpiel(pl2.playerId, playerId, pl2.socket, socket);
						  return(bundle.getString("my.17"));
						}
			  			else {return(bundle.getString("my.18"));}
					}
					else {return(bundle.getString("my.19"));}
			} 
			if (bundle.getString("my.33").equals("starte spiel:")) {
				Spieler pl2 = new Spieler(playlist.getPlayerId(inputLine.substring(14)), (playlist.getPlayerSocket(inputLine.substring(14))));
				Spieler pl1 = new Spieler(playerId, socket.toString());
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
		
		if (inputLine.startsWith(bundle.getString("my.32"))) {
			Spieler pl1 = new Spieler(playerId, socket.toString());
			pl1.setStatusPassword(playerId, inputLine.substring(10));
			return(bundle.getString("my.35"));
		}
		
		if (inputLine.startsWith(bundle.getString("my.34"))) {
			Spieler pl1 = new Spieler(playerId, socket.toString());
			if (bundle.getString("my.34").equals("language:")) {
				if (inputLine.substring(10).equals("de")){
					pl1.setStatusLocale(playerId, "de_DE");
					return(bundle.getString("my.37"));
				}
				if (inputLine.substring(10).equals("en")){
					return(bundle.getString("my.36"));
				}
				else {
					return(bundle.getString("my.38"));
				}
			}
			if (bundle.getString("my.34").equals("sprache:")) {
				if (inputLine.substring(9).equals("de")){
					return(bundle.getString("my.36"));
				}
				if (inputLine.substring(9).equals("en")){
					pl1.setStatusLocale(playerId, "en_EN");
					return(bundle.getString("my.37"));
				}
				else {
					return(bundle.getString("my.38"));
				}
			}
			
		}
		
		
      	/*if (inputLine.length() > 11) {
      	//TODO "start game" übersetzen.
		  if (inputLine.substring(0,11).equals("start game:")) {
			Spieler pl2 = new Spieler(playlist.getPlayerId(inputLine.substring(12)), (playlist.getPlayerSocket(inputLine.substring(12))));
			Spieler pl1 = new Spieler(playerId, socket.toString());
				if (!pl2.name.equals(pl1.name)){	
					if (pl2.status.equals("Wartend")){
					  playlist.starteSpiel(pl2.playerId, playerId, pl2.socket, socket);
					  return(bundle.getString("my.17"));
					}
		  			else {return(bundle.getString("my.18"));}
				}
				else {return(bundle.getString("my.19"));}
		  }
      	}*/
		
		
		
		return(bundle.getString("my.12"));
	}
	          
	//Die folgenden Befehle stehen zur Verfügung und werden über 'hilfe' angezeigt.
	public String help(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle("msgkatalog", locale);
		return(bundle.getString("my.13"));
	}	

	public String version(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle("msgkatalog", locale);
		return(bundle.getString("my.14"));
	}
	
	public String einstellungen(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle("msgkatalog", locale);
		return(bundle.getString("my.15"));
	}
	
	public String spiel(Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle("msgkatalog", locale);
		return(bundle.getString("my.16"));

	}
	
}
