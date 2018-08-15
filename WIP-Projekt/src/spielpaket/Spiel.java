package spielpaket;

import viergewinnt.ServerDatabase;

public class Spiel{
	int [][] field;//
	String status;//
	String pl1name;//
	String pl2name;//
	ServerDatabase database;
	
	//
	public Spiel(String pl1name, String pl2name){
		
	database = new ServerDatabase();
	this.field  = new int [7][6];
	this.pl1name = pl1name;
	this.pl2name = pl2name;
	}
	
	public String getSpielStatus(int spielId){
			status = database.getSpielStatusFromId(spielId);
			return status; 
	}
	
	public String move(String inputLine, String name, SpielerListe playlist){
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
		if (full() == false){
				
			//findet heraus welches Zeichen der Spieler am Zug hat
			if (name.equals(pl1name)){sign = 1;}
			else {sign = 2;};
		
			//
			if(a == 0) {
				/*Algoritmus der das Stein(Zeichen) in das Feld(field) hineingibt
				und dann pruft ob der Spieler gewonnen hat oder nicht*/
				while(y < 5 && field[x][y+1] == 0){y = y + 1;};
				//Pruft ob die spalte nicht voll ist
				if (y != -1){
					field[x][y] = sign;
					wl = check(x,y, sign);//startet den Kontrol algoritmus
					if (wl == false){
						playlist.change(pl1name,pl2name);//verschiebt die Rheienfolge
						return("Move accepted.");
					}
					else {playlist.win(pl1name,pl2name);return("You win");}
				}
				if (y == -1){return("This column is already full please enter other row.");};
			}
			else{return("You entered wrong number");};
		
		}
		//Wen das Feld voll ist gewinnt niemand
		else{playlist.nowin(pl1name,pl2name);return("The field is full nobody wins");}
		return("");
	}
	
	//Kontroll Algoritmus	
	private boolean check(int x, int y,int sign){	
		int x1 = x;
		int y1 = y;	
		int inrow = 0;//vie viel in der rheie sind
		
		//horizontal
		//check right
		while(x1>0 && field[x1-1][y1] == sign){
			x1--;
			inrow++; 
			if(inrow == 3){return(true);};			
		}
		//check left
		x1 = x;
		y1 = y;
		while(x1<6 && field[x1+1][y1] == sign){
					x1++;
					inrow++; 
					if(inrow == 3){return(true);};
		}
		inrow = 0;
		
		//vertical
		//up		
		x1 = x;
		y1 = y;
		while(y1>0 && field[x1][y1-1] == sign){
			y1--;
			inrow++; 
			if(inrow == 3){return(true);};			
		}
		//down
		x1 = x;
		y1 = y;
		while(y1<5 && field[x1][y1+1] == sign){
			y1++;
			inrow++; 
			if(inrow == 3){return(true);};			
		}
		inrow = 0;
	
		//
		//up right		
		x1 = x;
		y1 = y;
		while(x1>0 && y1>0 && field[x1-1][y1-1] == sign){
			x1--;
			y1--;
			inrow++; 
			if(inrow == 3){return(true);};			
		}
		//down left
		x1 = x;
		y1 = y;
		while(x1<6 && y1<5 && field[x1][y1] == sign){
			x1++;
			y1++;
			inrow++; 
			if(inrow == 3){return(true);};			
		}
		inrow = 0;
	
		//
		//up left		
		x1 = x;
		y1 = y;
		while(x1<5 && y1>0 && field[x1+1][y1-1] == sign){
			x1++;
			y1--;
			inrow++; 
			if(inrow == 3){return(true);};			
		}
		//down right
		x1 = x;
		y1 = y;
		while(x1>0 && y1<5 && field[x1-1][y1+1] == sign){
			x1--;
			y1++;
			inrow++; 
			if(inrow == 3){return(true);};			
		}
		return(false);
	}
	
	//Schreibt Spielfeld aus
	public String write(){
		String str = new String();
		for (int i = 0; i < 6; i++){
			for( int j = 0; j < 7; j++){
				str = str +field[j][i];	
			
			}
			str = str+"\r\n";
		}
		return(str);
	}

	//Algoritmus der pruft ob das Feld voll ist	
	public boolean full(){
		for (int i = 0; i < 7; i++){
			if (field[i][0] == 0){return(false);}
		}	
		return(true);
	}

}









