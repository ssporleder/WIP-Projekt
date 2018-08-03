package viergewinnt;

import java.lang.*;
import java.util.*;

import viergewinnt.*;

public class PlayerList{
	public int number_of_players;
	public Map players;

	public PlayerList(){
		players = new HashMap();
	}
	
	//Pruft ob Spieler mit Namen name existiert
	public boolean contact(String name){
		Player pl = (Player) players.get(name);
		if (pl == null) {return(true);}
		else {return(false);}
	}
	
}
