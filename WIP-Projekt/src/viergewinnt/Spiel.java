package viergewinnt;
import java.lang.*;

import spielpaket.Spieler;
import spielpaket.SpielerListe;

//Spiel asdas
public class Spiel {
	int [][] feld;
	int status;
	String spieler1;
	String spieler2;
	
	public Spiel(String spieler1, String spieler2){
		this.feld = new int [7][6];
		this.status = 0;
		this.spieler1 = spieler1;
		this.spieler2 = spieler2;
	}
	
	public String aktion(String inputLine, String name, SpielerListe playlist){
		int a = 1;//Hilfsvariable die prüft ob die richtige (InputLine) eingegeben worden ist
		int x = 0;//x-Koordinate. Repräsentiert die Nummer der Spalte in dem Feld
		int y = -1;//
		int sign =0;//
		boolean wl;//
		
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
					
				//findet heraus welches Zeichen der Spieler am Zug hat
				if (name.equals(spieler1)){sign = 1;}
				else {sign = 2;};
			
				//
				if(a == 0) {
					/*Algoritmus der das Stein(Zeichen) in das Feld(field) hineingibt
					und dann pruft ob der Spieler gewonnen hat oder nicht*/
					while(y < 5 && feld[x][y+1] == 0){y = y + 1;};
					//Pruft ob die spalte nicht voll ist
					if (y != -1){
						feld[x][y] = sign;
						wl = pruefen(x,y, sign);//startet den Kontrol algoritmus
						if (wl == false){
							PlayerList.wechsel(spieler1,spieler2);//verschiebt die Rheienfolge
							return("Move accepted.");
						}
						else {PlayerList.sieg(spieler1,spieler2);return("You win");}
					}
					if (y == -1){return("This column is already full please enter other row.");};
				}
				else{return("You entered wrong number");};
			
			}
			//Wen das Feld voll ist gewinnt niemand
			else{PlayerList.keinSieg(spieler1,spieler2);return("The field is full nobody wins");}
			return("");
		}
	
	//Kontrollalgorithmus	
		private boolean pruefen(int x, int y,int sign){	
			int x1 = x;
			int y1 = y;	
			int inreihe = 0;//vie viel in der rheie sind
			
			//horizontal
			//check right
			while(x1>0 && feld[x1-1][y1] == sign){
				x1--;
				inreihe++; 
				if(inreihe == 3){return(true);};			
			}
			//check left
			x1 = x;
			y1 = y;
			while(x1<6 && feld[x1+1][y1] == sign){
						x1++;
						inreihe++; 
						if(inreihe == 3){return(true);};
			}
			inreihe = 0;
			
			//vertical
			//up		
			x1 = x;
			y1 = y;
			while(y1>0 && feld[x1][y1-1] == sign){
				y1--;
				inreihe++; 
				if(inreihe == 3){return(true);};			
			}
			//down
			x1 = x;
			y1 = y;
			while(y1<5 && feld[x1][y1+1] == sign){
				y1++;
				inreihe++; 
				if(inreihe == 3){return(true);};			
			}
			inreihe = 0;
		
			//
			//up right		
			x1 = x;
			y1 = y;
			while(x1>0 && y1>0 && feld[x1-1][y1-1] == sign){
				x1--;
				y1--;
				inreihe++; 
				if(inreihe == 3){return(true);};			
			}
			//down left
			x1 = x;
			y1 = y;
			while(x1<6 && y1<5 && feld[x1][y1] == sign){
				x1++;
				y1++;
				inreihe++; 
				if(inreihe == 3){return(true);};			
			}
			inreihe = 0;
		
			//
			//up left		
			x1 = x;
			y1 = y;
			while(x1<5 && y1>0 && feld[x1+1][y1-1] == sign){
				x1++;
				y1--;
				inreihe++; 
				if(inreihe == 3){return(true);};			
			}
			//down right
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
		public String zeigen(){
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
