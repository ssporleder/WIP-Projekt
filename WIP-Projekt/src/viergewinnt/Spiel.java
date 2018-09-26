package viergewinnt;

import java.util.Locale;
import java.util.ResourceBundle;

//Die Klasse Spiel wurde bis auf die beiden Methoden (deserializeFeld() und serializeFeld()) implementiert von Patrick Ostapowicz

public class Spiel {
	int [][] feld;
	int status;
	int spielId;
	String spieler1;
	String spieler2;
	ResourceBundle bundle = ResourceBundle.getBundle("msgkatalog");
	ServerDatabase database = new ServerDatabase();
	
	public Spiel(int spielId){
		
		this.spielId = spielId;
		this.feld = new int [7][6];
		this.status = 0;
		this.spieler1 = database.getSpielSpieler1FromId(spielId);
		this.spieler2 = database.getSpielSpieler2FromId(spielId);
	}
	

	
	public String aktion(String inputLine, String name, SpielListe playlist, Integer spielId){
		int a = 1;//Hilfsvariable die prüft ob die richtige (InputLine) eingegeben worden ist
		int x = 0;//x-Koordinate. Repräsentiert die Nummer der Spalte in dem Feld
		int y = -1;//
		int sign =0;//
		boolean wl;//
		Locale locale = new Locale(database.getPlayerLocale(name));
		bundle = ResourceBundle.getBundle("msgkatalog", locale);
		
	//Entscheidet Anhand der InputLine um welche Spalte es sich	handelt			
			if (inputLine.equals("1")){x = 0;a = 0;}
			if (inputLine.equals("2")){x = 1;a = 0;}
			if (inputLine.equals("3")){x = 2;a = 0;}
			if (inputLine.equals("4")){x = 3;a = 0;}
			if (inputLine.equals("5")){x = 4;a = 0;}
			if (inputLine.equals("6")){x = 5;a = 0;}
			if (inputLine.equals("7")){x = 6;a = 0;}
			
			//Prüft, ob das Spielfeld nicht voll ist. Wenn nicht geht es weiter
			if (voll() == false){
					
				//Legt fest, dass Spieler 1 das Zeichen 1 bekommt.
				if (name.equals(spieler1)){sign = 1;}
				else {sign = 2;};
			
				//
				if(a == 0) {
					/*Algoritmus, der das Stein(Zeichen) in das Feld(feld) hineingibt
					und dann prüft, ob der Spieler gewonnen hat oder nicht*/
					while(y < 5 && feld[x][y+1] == 0){y = y + 1;};
					//Überprüft, ob die Spalte nicht voll ist
					if (y != -1){
					feld[x][y] = sign;
					database.updateSpielFeld(spielId, serializeFeld(feld));
						
						wl = pruefen(x,y, sign);
						if (wl == false){
							playlist.wechsel(spielId);
							return(bundle.getString("my.27"));
						}
						else {
							if (name.equals(spieler1)) {
							playlist.siegSpieler1(spielId);return(bundle.getString("my.28"));
							} else {
							playlist.siegSpieler2(spielId);return(bundle.getString("my.29"));	
							}
						}
					}
					if (y == -1){return(bundle.getString("my.30"));};
				}
				else{return(bundle.getString("my.31"));};
			
			}
			//Wenn das Feld voll ist gewinnt keiner
			else{
				playlist.keinSieg(spielId);
				return(bundle.getString("my.26"));}
			return("");
		}

	
	//Implementiert durch Sebastian Sporleder
	//Die folgende Methode serialisiert ein eingegebenes Array, damit dieses in die Datenbank als String geschrieben werden kann
	private String serializeFeld(int [][] feld)
	{
		String s = "";
		
		for (int y2 = 0; y2 < 6; y2++)
		{
			if (y2 > 0) s += ",";
			s += "[";

			for (int x2 = 0; x2 < 7; x2++)
			{
				if (x2 > 0) s += ",";
				s += String.valueOf(feld[x2][y2]); 
			}
		
			s += "]"; 
		}
		
		return s;
	}

	//Implementiert durch Sebastian Sporleder
	//Die folgende Methode deserialisiert einen eingegebenen String zu einem Array, damit dieses für den nächsten Spielzug und die Darstellung des Ergebnisses verwendet werden kann.
	private void deserializeFeld(int [][] feld, String s)
	{
		String[] ys = s.substring(1, s.length() - 1).split("],.");
		
		for (int y2 = 0; y2 < 6; y2++)
		{
			String[] xs = ys[y2].split(","); 

			for (int x2 = 0; x2 < 7; x2++)
			{
				feld[x2][y2] = Integer.parseInt(xs[x2]); 
			}
		}
	}

	
	
	//Kontrollalgorithmus	
		private boolean pruefen(int x, int y,int sign){	
			int x1 = x;
			int y1 = y;	
			int inreihe = 0;
			deserializeFeld(feld, database.getSpielFeld(spielId));
			
			
			while(x1>0 && feld[x1-1][y1] == sign){
				x1--;
				inreihe++; 
				if(inreihe == 3){return(true);};			
			}
			x1 = x;
			y1 = y;
			while(x1<6 && feld[x1+1][y1] == sign){
						x1++;
						inreihe++; 
						if(inreihe == 3){return(true);};
			}
			inreihe = 0;
					
			x1 = x;
			y1 = y;
			while(y1>0 && feld[x1][y1-1] == sign){
				y1--;
				inreihe++; 
				if(inreihe == 3){return(true);};			
			}
			
			x1 = x;
			y1 = y;
			while(y1<5 && feld[x1][y1+1] == sign){
				y1++;
				inreihe++; 
				if(inreihe == 3){return(true);};			
			}
			inreihe = 0;
				
			x1 = x;
			y1 = y;
			while(x1>0 && y1>0 && feld[x1-1][y1-1] == sign){
				x1--;
				y1--;
				inreihe++; 
				if(inreihe == 3){return(true);};			
			}

			x1 = x;
			y1 = y;
			while(x1<6 && y1<5 && feld[x1][y1] == sign){
				x1++;
				y1++;
				inreihe++; 
				if(inreihe == 3){return(true);};			
			}
			inreihe = 0;
			
			x1 = x;
			y1 = y;
			while(x1<5 && y1>0 && feld[x1+1][y1-1] == sign){
				x1++;
				y1--;
				inreihe++; 
				if(inreihe == 3){return(true);};			
			}


			x1 = x;
			y1 = y;
			while(x1>0 && y1<5 && feld[x1-1][y1+1] == sign){
				x1--;
				y1++;
				inreihe++; 
				if(inreihe == 3){return(true);};			
			}
			return(false);
		}
	
	//Darstellung des Spielfelds
		public String zeigen(int spielId){
			deserializeFeld(feld, database.getSpielFeld(spielId));		
			String str = new String();
			for (int i = 0; i < 6; i++){
				for( int j = 0; j < 7; j++){
					str = str +feld[j][i];	
				
				}
				str = str+"\r\n";
			}
			return(str);
		}
	
	//Prüfalgorithmus: Prüft ob Feld belegt ist
		public boolean voll(){
			for (int i = 0; i < 7; i++){
				if (feld[i][0] == 0){return(false);}
			}	
			return(true);
		}
		
}
