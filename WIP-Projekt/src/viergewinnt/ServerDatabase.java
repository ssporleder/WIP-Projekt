package viergewinnt;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ServerDatabase {

	public String USER_HOME = System.getProperty("user.home");
	public String dbUrl = "jdbc:sqlite:" + USER_HOME + "/" + "ServerDatabase.db";
	
	
    public void createNewDatabase()  {
 
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("[Server] The driver name is " + meta.getDriverName());
                System.out.println("[Server] Die Datenbank 'ServerDatabase' wurde im Benutzerverzeichnis angelegt.");
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
  
    public void createNewTable(String tableName) {

    	String sql = null;
        
        // SQL statement um die Tabelle "Spieler" zu erstellen.
        if(tableName == "Spieler") {
    	sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	status text NOT NULL,\n"
                + "	socket text NOT NULL\n,"
                + "	gewonnen integer NULL\n,"
                + "	verloren integer NULL\n,"
                + " passwort text NOT NULL\n,"
                + " locale text\n,"
                + " spielid integer NULL,"
                + " amZug integer NULL"
                + ");";
        }
        
        // SQL statement um die Tabelle "Spiel" zu erstellen.
        if(tableName == "Spiel") {
    	sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	Spieler1 text NOT NULL,\n"
                + "	Spieler2 text,\n"
                + "	status text NOT NULL\n,"
                + " feld text NULL\n,"
                + " unentschieden integer NULL\n,"
                + " gewinner text NULL\n"
                + ");";
        }
        
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        
        }
    }
    
    public int insertPlayer(String name, String status, String socket, String passwort)  {
    	String sql = "INSERT INTO Spieler(id,name,status,socket,gewonnen,verloren,passwort,locale) VALUES(null,?,?,?,?,?,?,?)";
    	int playerId = 0;
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
        	PreparedStatement pstmt = conn.prepareStatement(sql);
        	int gewonnen = 0;	
            int verloren = 0;
            String locale = "de_DE";
            pstmt.setString(1, name);
            pstmt.setString(2, status);
            pstmt.setString(3, socket);
            pstmt.setInt(4, gewonnen);
            pstmt.setInt(5, verloren);
            pstmt.setString(6, passwort);
            pstmt.setString(7, locale);
            pstmt.executeUpdate();
            playerId = getPlayerId(name);
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }
		return (playerId);  	
    }
    
    public int updatePlayer(int id, String name, String status, String socket, String passwort)  {
    	String sql = "UPDATE Spieler set status=?,socket=? WHERE id=?";
    	int playerId = 0;
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
        	PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setString(2, socket);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
            playerId = getPlayerId(name);
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }
		return (playerId);  	
    }
  
    public void updatePlayerStatus(int id, String status)  {
    	String sql = "UPDATE Spieler set status=? WHERE id=?";
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
        	PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }  	
    }
    
    public void updatePlayerPassword(int spielerId, String password)  {
    	String sql = "UPDATE Spieler set passwort=? WHERE id=?";
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
        	PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, password);
            pstmt.setInt(2, spielerId);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }  	
    }
    
    public void updatePlayerLocale(int spielerId, String locale)  {
    	String sql = "UPDATE Spieler set locale=? WHERE id=?";
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
        	PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, locale);
            pstmt.setInt(2, spielerId);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }  	
    }
    
    public void updatePlayerAmZug(int playerId, int amZug)  {
    	String sql = "UPDATE Spieler set amZug=? WHERE id=?";
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
        	PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, amZug);
            pstmt.setInt(2, playerId);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }  	
    }
    
    public void updatePlayerSpielId(int playerId, int spielId)  {
    	String sql = "UPDATE Spieler set spielId=? WHERE id=?";
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
        	PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, spielId);
            pstmt.setInt(2, playerId);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }  	
    }
    
    public int getPlayerId(String name) {
    	String sql = "SELECT id FROM Spieler WHERE name = " + "'" + name + "';";
    	int playerId = 0;
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	while (rs.next()) {
        	playerId = ((Number) rs.getObject(1)).intValue();
        	}
    {
    }
    
    } catch (SQLException e) {
		e.printStackTrace();
	}
		return playerId;
    }
    
    public String getPlayerStatus(String name) {
    	String sql = "SELECT status FROM Spieler WHERE name = " + "'" + name + "';";
    	String ergebnis = "";
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	while (rs.next()) {
        	ergebnis = ((String) rs.getObject(1));
        	}
    {
    }
    
    } catch (SQLException e) {
		e.printStackTrace();
	}
		return ergebnis;
    }
    
    public String getPlayerSocket(String name) {
    	String sql = "SELECT socket FROM Spieler WHERE name = " + "'" + name + "';";
    	String ergebnis = "";
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	while (rs.next()) {
        	ergebnis = ((String) rs.getObject(1));
        	}
    {
    }
    
    } catch (SQLException e) {
		e.printStackTrace();
	}
		return ergebnis;
    }
    
    public String getPlayerLocale(String name) {
    	String sql = "SELECT locale FROM Spieler WHERE name = " + "'" + name + "';";
    	String ergebnis = "";
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	while (rs.next()) {
        	ergebnis = ((String) rs.getObject(1));
        	}
    {
    }
    
    } catch (SQLException e) {
		e.printStackTrace();
	}
		return ergebnis;
    }
    
    public String getPlayerStatusFromId(int playerId) {
    	String sql = "SELECT status FROM Spieler WHERE id = " + "'" + playerId + "';";
    	String ergebnis = "";
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	while (rs.next()) {
        	ergebnis = ((String) rs.getObject(1));
        	}
    {
    }
    
    } catch (SQLException e) {
		e.printStackTrace();
	}
		return ergebnis;
    }
    
    public int getPlayerAmZugFromId(int playerId) {
    	String sql = "SELECT amZug FROM Spieler WHERE id = " + "'" + playerId + "';";
    	int ergebnis = '0';
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	while (rs.next()) {
        	ergebnis = ((Integer) rs.getObject(1));
        	}
    {
    }
    
    } catch (SQLException e) {
		e.printStackTrace();
	}
		return ergebnis;
    }
    
    public int getPlayerSpielId(int playerId) {
    	String sql = "SELECT spielId FROM Spieler WHERE id = " + "'" + playerId + "';";
    	int ergebnis = '0';
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	while (rs.next()) {
        	ergebnis = ((Integer) rs.getObject(1));
        	}
    {
    }
    
    } catch (SQLException e) {
		e.printStackTrace();
	}
		return ergebnis;
    }
    
    public int getAnzahlGewonnen(int playerId) {
    	String sql = "SELECT gewonnen FROM Spieler WHERE id = " + "'" + playerId + "';";
    	int anzahl = 0;
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	while (rs.next()) {
        	anzahl = ((Number) rs.getObject(1)).intValue();
        	}
    {
    }
    
    } catch (SQLException e) {
		e.printStackTrace();
	}
		return anzahl;
    }
    
    public int getAnzahlVerloren(int playerId) {
    	String sql = "SELECT verloren FROM Spieler WHERE id = " + "'" + playerId + "';";
    	int anzahl = 0;
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	while (rs.next()) {
        	anzahl = ((Number) rs.getObject(1)).intValue();
        	}
    {
    }
    
    } catch (SQLException e) {
		e.printStackTrace();
	}
		return anzahl;
    }
    
    public void updatePlayerGewonnen(int playerId, int gewonnen)  {
    	String sql = "UPDATE Spieler set gewonnen=? WHERE id=?";
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
        	PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, gewonnen);
            pstmt.setInt(2, playerId);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }  	
    }
    
    public void updateSpielUnentschieden(int playerId, int unentschieden)  {
    	String sql = "UPDATE Spiel set unentschieden=? WHERE id=?";
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
        	PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, unentschieden);
            pstmt.setInt(2, playerId);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }  	
    }
    
    public void updatePlayerVerloren(int playerId, int verloren)  {
    	String sql = "UPDATE Spieler set verloren=? WHERE id=?";
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
        	PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, verloren);
            pstmt.setInt(2, playerId);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }  	
    }
    
    public String getPlayerName(int playerId) {
    	String sql = "SELECT name FROM Spieler WHERE id = " + "'" + playerId + "';";
    	String name = "";
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	while (rs.next()) {
        	name = ((String) rs.getObject(1));
        	}
    {
    }
    
    } catch (SQLException e) {
		e.printStackTrace();
	}
		return name;
		
    }
		
	    public String getPlayerPasswort(int playerId) {
	    	String sql = "SELECT passwort FROM Spieler WHERE id = " + "'" + playerId + "';";
	    	String passwort = "";
	    	
	        try (Connection conn = DriverManager.getConnection(dbUrl)) {
	    		Statement stmt = conn.createStatement();
	        	ResultSet rs = stmt.executeQuery(sql);
	        	while (rs.next()) {
	        	passwort = ((String) rs.getObject(1));
	        	}
	    {
	    }
	    
	    } catch (SQLException e) {
			e.printStackTrace();
		}
			return passwort;	
    } 
	    
	    public String getSpielStatusFromId(int spielId) {
	    	String sql = "SELECT status FROM Spiel WHERE id = " + "'" + spielId + "';";
	    	String status = "";
	    	
	        try (Connection conn = DriverManager.getConnection(dbUrl)) {
	    		Statement stmt = conn.createStatement();
	        	ResultSet rs = stmt.executeQuery(sql);
	        	while (rs.next()) {
	        	status = ((String) rs.getObject(1));
	        	}
	    {
	    }
	    
	    } catch (SQLException e) {
			e.printStackTrace();
		}
			return status;	
    } 
	    
	    public String getSpielSpieler1FromId(int spielId) {
	    	String sql = "SELECT Spieler1 FROM Spiel WHERE id = " + "'" + spielId + "';";
	    	String pl = "";
	    	
	        try (Connection conn = DriverManager.getConnection(dbUrl)) {
	    		Statement stmt = conn.createStatement();
	        	ResultSet rs = stmt.executeQuery(sql);
	        	while (rs.next()) {
	        	pl = ((String) rs.getObject(1));
	        	}
	    {
	    }
	    
	    } catch (SQLException e) {
			e.printStackTrace();
		}
			return pl;	
    } 
	    
	    public String getSpielSpieler2FromId(int spielId) {
	    	String sql = "SELECT Spieler2 FROM Spiel WHERE id = " + "'" + spielId + "';";
	    	String pl = "";
	    	
	        try (Connection conn = DriverManager.getConnection(dbUrl)) {
	    		Statement stmt = conn.createStatement();
	        	ResultSet rs = stmt.executeQuery(sql);
	        	while (rs.next()) {
	        	pl = ((String) rs.getObject(1));
	        	}
	    {
	    }
	    
	    } catch (SQLException e) {
			e.printStackTrace();
		}
			return pl;	
    }    
	    
	    public String listeSpieler() {
	    	String sql = "SELECT name, status, socket, gewonnen, verloren, spielId FROM Spieler;";
	    	String name = "";
	    	
	        try (Connection conn = DriverManager.getConnection(dbUrl)) {
	    		Statement stmt = conn.createStatement();
	        	ResultSet rs = stmt.executeQuery(sql);
	        	while (rs.next()) {
	        	name = name + "[Server] Name: " + ((String) rs.getObject(1)) + " Status: " + ((String) rs.getObject(2))+ " Socket: " + ((String) rs.getObject(3))+ " Gewonnen/Verloren: " + ((Integer) rs.getObject(4)) + "/" + ((Integer) rs.getObject(5)) + " Befindet sich im Spiel: " + ((Integer) rs.getObject(5)) + "\r\n";
	        	}
	    {
	    }
	    
	    } catch (SQLException e) {
			e.printStackTrace();
		}
			return name;
	    }    
	    
	    public String listeSpiele() {
	    	String sql = "SELECT id, Spieler1, Spieler2, status, gewinner FROM Spiel;";
	    	String ergebnis = "";
	    	
	        try (Connection conn = DriverManager.getConnection(dbUrl)) {
	    		Statement stmt = conn.createStatement();
	        	ResultSet rs = stmt.executeQuery(sql);
	        	while (rs.next()) {
	        	ergebnis = ergebnis + "[Server] SpielID: " + ((Integer) rs.getObject(1)) + " Spieler1: " + ((String) rs.getObject(2))+ " Spieler2: " + ((String) rs.getObject(3)) + " Status: " + ((String) rs.getObject(4)) + " Gewinner: " + ((String) rs.getObject(5)) + "\r\n";
	        	}
	    {
	    }
	    
	    } catch (SQLException e) {
			e.printStackTrace();
		}
			return ergebnis;
			
	    } 
	    
	    public int insertSpiel(String pl1, String status)  {
	    	String sql = "INSERT INTO Spiel(id,Spieler1,status,feld,unentschieden) VALUES(null,?,?,?,?)";
	    	int spielId = 0;
	    	int unentschieden = 0;
	    	String feld = "[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,0,0]";
	    	
	        try (Connection conn = DriverManager.getConnection(dbUrl)) {
	        	PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        	pstmt.setString(1, pl1);
	            pstmt.setString(2, status);
	            pstmt.setString(3, feld);
	            pstmt.setInt(4, unentschieden);
	            pstmt.executeUpdate();
	            ResultSet keys = pstmt.getGeneratedKeys();
	            keys.next();
	            spielId = keys.getInt(1);
	            keys.close();
	            pstmt.close();
	            conn.close();
	        } catch (SQLException e) {
	        	System.out.println(e.getMessage());
	        }  	return spielId;
	    }
	    
	    public void updateSpielStatus(int spielId, String status)  {
	    	String sql = "UPDATE Spiel set Status=? WHERE id=?";
	    	
	        try (Connection conn = DriverManager.getConnection(dbUrl)) {
	        	PreparedStatement pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, status);
	            pstmt.setInt(2, spielId);
	            pstmt.executeUpdate();
	            
	        } catch (SQLException e) {
	        	System.out.println(e.getMessage());
	        }  	
	    }
	    
	    public void updateSpielGewinner(int spielId, String spieler)  {
	    	String sql = "UPDATE Spiel set gewinner=? WHERE id=?";
	    	
	        try (Connection conn = DriverManager.getConnection(dbUrl)) {
	        	PreparedStatement pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, spieler);
	            pstmt.setInt(2, spielId);
	            pstmt.executeUpdate();
	            
	        } catch (SQLException e) {
	        	System.out.println(e.getMessage());
	        }  	
	    }
	    
	    public void updateSpielFeld(int spielId, String feld)  {
	    	String sql = "UPDATE Spiel set feld=? WHERE id=?";
	    	
	        try (Connection conn = DriverManager.getConnection(dbUrl)) {
	        	PreparedStatement pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, feld);
	            pstmt.setInt(2, spielId);
	            pstmt.executeUpdate();
	            
	        } catch (SQLException e) {
	        	System.out.println(e.getMessage());
	        }  	
	    }
	    
	    public String getSpielFeld(int spielId) {
	    	String sql = "SELECT feld FROM Spiel WHERE id = " + "'" + spielId + "';";
	    	String feld = "";
	    	
	        try (Connection conn = DriverManager.getConnection(dbUrl)) {
	    		Statement stmt = conn.createStatement();
	        	ResultSet rs = stmt.executeQuery(sql);
	        	while (rs.next()) {
	        	feld = ((String) rs.getObject(1));
	        	}
	    {
	    }
	    
	    } catch (SQLException e) {
			e.printStackTrace();
		}
			return feld;	
    } 
	  
	    public int getSpielUnentschieden(int spielId) {
	    	String sql = "SELECT unentschieden FROM Spiel WHERE id = " + "'" + spielId + "';";
	    	int unentschieden = 0;
	    	
	        try (Connection conn = DriverManager.getConnection(dbUrl)) {
	    		Statement stmt = conn.createStatement();
	        	ResultSet rs = stmt.executeQuery(sql);
	        	while (rs.next()) {
	        	unentschieden = ((Integer) rs.getObject(1));
	        	}
	    {
	    }
	    
	    } catch (SQLException e) {
			e.printStackTrace();
		}
			return unentschieden;	
    } 
	    
	    public void updateSpielSpieler2(int spielId, String name)  {
	    	String sql = "UPDATE Spiel set Spieler2=? WHERE id=?";
	    	
	        try (Connection conn = DriverManager.getConnection(dbUrl)) {
	        	PreparedStatement pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, name);
	            pstmt.setInt(2, spielId);
	            pstmt.executeUpdate();
	            
	        } catch (SQLException e) {
	        	System.out.println(e.getMessage());
	        }  	
	    }
	    
	    
	    public void initializePlayer() {
	    	String sql = "SELECT id FROM Spieler WHERE status != 'Offline';";
	    	String sql2 = "UPDATE Spieler set status='Offline',socket='nicht verbunden' WHERE id=?";
	    	int playerId = 0;
	    	
	        try (Connection conn = DriverManager.getConnection(dbUrl)) {
	    		Statement stmt = conn.createStatement();
	        	ResultSet rs = stmt.executeQuery(sql);
	        	while (rs.next()) {
	        	playerId = ((Number) rs.getObject(1)).intValue();
	        	try (Connection conn2 = DriverManager.getConnection(dbUrl)) {
	        	PreparedStatement pstmt = conn.prepareStatement(sql2);
	            pstmt.setInt(1, playerId);
	            pstmt.executeUpdate();
	        	}
	        	}
	    {
	    }
	    
	    } catch (SQLException e) {
			e.printStackTrace();
			}
	    }
	    
	    public void initializeSpiel() {
	    	String sql = "SELECT id FROM Spiel WHERE status != 'Beendet';";
	    	String sql2 = "UPDATE Spiel set status='abgebrochen' WHERE id=?";
	    	int id = 0;
	    	
	        try (Connection conn = DriverManager.getConnection(dbUrl)) {
	    		Statement stmt = conn.createStatement();
	        	ResultSet rs = stmt.executeQuery(sql);
	        	while (rs.next()) {
	        		id = ((Number) rs.getObject(1)).intValue();
	        	try (Connection conn2 = DriverManager.getConnection(dbUrl)) {
	        		PreparedStatement pstmt = conn.prepareStatement(sql2);
	        		pstmt.setInt(1, id);
	        		pstmt.executeUpdate();
	        	}
	        	}
	    {
	    }
	    
	    } catch (SQLException e) {
			e.printStackTrace();
		 	}
	    }
	    
    
}
