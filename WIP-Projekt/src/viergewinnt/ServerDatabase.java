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
 

    	
        //String url = "jdbc:sqlite:" + USER_HOME + "/" + fileName;
 
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
 
    /**
     * @param args the command line arguments
     * @throws SQLException 
     */
    
    public void createNewTable(String tableName) {

    	String sql = null;
        
        // SQL statement for creating a new table
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
        
        // SQL statement for creating a new table
        if(tableName == "Spiel") {
    	sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	Spieler1 text NOT NULL,\n"
                + "	Spieler2 text,\n"
                + "	status text NOT NULL\n"
                + ");";
        }
        
        if(tableName == "msgkatalog") {
        	sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
                    + "	id integer PRIMARY KEY,\n"
                    + "	de text NOT NULL,\n"
                    + "	en text NOT NULL\n"
                    + ");";
            }
        
        
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
                Statement stmt = conn.createStatement();
            // create a new table
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
            //INSERT Into
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
            //INSERT Into
        	//int id = getNextIdPlayer();	
            //int id = 3333;
        	//pstmt.setInt(1, 0);
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
            //INSERT Into
        	//int id = getNextIdPlayer();	
            //int id = 3333;
        	//pstmt.setInt(1, 0);
            pstmt.setString(1, status);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }  	
    }
    
    public void updatePlayerAmZug(int playerId, int amZug)  {
    	String sql = "UPDATE Spieler set amZug=? WHERE id=?";
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
        		PreparedStatement pstmt = conn.prepareStatement(sql);
            //INSERT Into
        	//int id = getNextIdPlayer();	
            //int id = 3333;
        	//pstmt.setInt(1, 0);
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
            //INSERT Into
        	//int id = getNextIdPlayer();	
            //int id = 3333;
        	//pstmt.setInt(1, 0);
            pstmt.setInt(1, spielId);
            pstmt.setInt(2, playerId);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }  	
    }
    
    public int getPlayerId(String name) {
    	String sql = "SELECT id FROM Spieler WHERE name = " + "'" + name + "';";
    	//System.out.println(sql);
    	int playerId = 0;
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	while (rs.next()) {
        	playerId = ((Number) rs.getObject(1)).intValue();
        	}
        	//System.out.println(playerId);
    {
    }
    
    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return playerId;
    }
    
    public String getPlayerStatus(String name) {
    	String sql = "SELECT status FROM Spieler WHERE name = " + "'" + name + "';";
    	//System.out.println(sql);
    	String ergebnis = "";
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	while (rs.next()) {
        	ergebnis = ((String) rs.getObject(1));
        	}
        	//System.out.println(playerId);
    {
    }
    
    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return ergebnis;
    }
    
    public String getPlayerSocket(String name) {
    	String sql = "SELECT socket FROM Spieler WHERE name = " + "'" + name + "';";
    	//System.out.println(sql);
    	String ergebnis = "";
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	while (rs.next()) {
        	ergebnis = ((String) rs.getObject(1));
        	}
        	//System.out.println(playerId);
    {
    }
    
    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return ergebnis;
    }
    
    public String getPlayerLocale(String name) {
    	String sql = "SELECT locale FROM Spieler WHERE name = " + "'" + name + "';";
    	//System.out.println(sql);
    	String ergebnis = "";
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	while (rs.next()) {
        	ergebnis = ((String) rs.getObject(1));
        	}
        	//System.out.println(playerId);
    {
    }
    
    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return ergebnis;
    }
    
    public String getPlayerStatusFromId(int playerId) {
    	String sql = "SELECT status FROM Spieler WHERE id = " + "'" + playerId + "';";
    	//System.out.println(sql);
    	String ergebnis = "";
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	while (rs.next()) {
        	ergebnis = ((String) rs.getObject(1));
        	}
        	//System.out.println(playerId);
    {
    }
    
    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return ergebnis;
    }
    
    public int getPlayerAmZugFromId(int playerId) {
    	String sql = "SELECT amZug FROM Spieler WHERE id = " + "'" + playerId + "';";
    	//System.out.println(sql);
    	int ergebnis = '0';
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	while (rs.next()) {
        	ergebnis = ((Integer) rs.getObject(1));
        	}
        	//System.out.println(playerId);
    {
    }
    
    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return ergebnis;
    }
    
    public int getPlayerSpielId(int playerId) {
    	String sql = "SELECT spielId FROM Spieler WHERE id = " + "'" + playerId + "';";
    	//System.out.println(sql);
    	int ergebnis = '0';
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	while (rs.next()) {
        	ergebnis = ((Integer) rs.getObject(1));
        	}
        	//System.out.println(playerId);
    {
    }
    
    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return ergebnis;
    }
    
    public int getAnzahlGewonnen(int playerId) {
    	String sql = "SELECT gewonnen FROM Spieler WHERE id = " + "'" + playerId + "';";
    	//System.out.println(sql);
    	int anzahl = 0;
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	while (rs.next()) {
        	anzahl = ((Number) rs.getObject(1)).intValue();
        	}
        	//System.out.println(playerId);
    {
    }
    
    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return anzahl;
    }
    
    public int getAnzahlVerloren(int playerId) {
    	String sql = "SELECT verloren FROM Spieler WHERE id = " + "'" + playerId + "';";
    	//System.out.println(sql);
    	int anzahl = 0;
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	while (rs.next()) {
        	anzahl = ((Number) rs.getObject(1)).intValue();
        	}
        	//System.out.println(playerId);
    {
    }
    
    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return anzahl;
    }
    
    
    public String getPlayerName(int playerId) {
    	String sql = "SELECT name FROM Spieler WHERE id = " + "'" + playerId + "';";
    	//System.out.println(sql);
    	String name = "";
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	while (rs.next()) {
        	name = ((String) rs.getObject(1));
        	}
        	//System.out.println(name);
    {
    }
    
    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return name;
		
    }
		
	    public String getPlayerPasswort(int playerId) {
	    	String sql = "SELECT passwort FROM Spieler WHERE id = " + "'" + playerId + "';";
	    	//System.out.println(sql);
	    	String passwort = "";
	    	
	        try (Connection conn = DriverManager.getConnection(dbUrl)) {
	    		Statement stmt = conn.createStatement();
	        	ResultSet rs = stmt.executeQuery(sql);
	        	while (rs.next()) {
	        	passwort = ((String) rs.getObject(1));
	        	}
	        	//System.out.println(name);
	    {
	    }
	    
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return passwort;	
    } 
	    
	    public String getSpielStatusFromId(int spielId) {
	    	String sql = "SELECT status FROM Spiel WHERE id = " + "'" + spielId + "';";
	    	//System.out.println(sql);
	    	String status = "";
	    	
	        try (Connection conn = DriverManager.getConnection(dbUrl)) {
	    		Statement stmt = conn.createStatement();
	        	ResultSet rs = stmt.executeQuery(sql);
	        	while (rs.next()) {
	        	status = ((String) rs.getObject(1));
	        	}
	        	//System.out.println(name);
	    {
	    }
	    
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return status;	
    } 
	    
	    public String getMessageFromKatalog(int id, int lang) {
	    	
	    	String sql = "";
	    	if (lang == 0) {
	    	sql = "SELECT de FROM msgkatalog WHERE id = " + "'" + id+ "';";
	    	}
	    	if (lang == 1) {
	    	sql = "SELECT en FROM msgkatalog WHERE id = " + "'" + id+ "';";
	    	}
	    	
	    	//System.out.println(sql);
	    	String msg = "";
	    	
	        try (Connection conn = DriverManager.getConnection(dbUrl)) {
	    		Statement stmt = conn.createStatement();
	        	ResultSet rs = stmt.executeQuery(sql);
	        	while (rs.next()) {
	        	msg = ((String) rs.getObject(1));
	        	}
	        	//System.out.println(name);
	    {
	    }
	    
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return msg;	
    } 
	    
	    
	    public String listeSpieler() {
	    	String sql = "SELECT name, status, socket, gewonnen, verloren FROM Spieler;";
	    	//System.out.println(sql);
	    	String name = "";
	    	
	        try (Connection conn = DriverManager.getConnection(dbUrl)) {
	    		Statement stmt = conn.createStatement();
	        	ResultSet rs = stmt.executeQuery(sql);
	        	while (rs.next()) {
	        	name = name + "Name: " + ((String) rs.getObject(1)) + " Status: " + ((String) rs.getObject(2))+ " Socket: " + ((String) rs.getObject(3))+ " Gewonnen/Verloren: " + ((Integer) rs.getObject(4)) + "/" + ((Integer) rs.getObject(5)) + "\r\n";
	        	}
	        	//System.out.println(name);
	    {
	    }
	    
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return name;
			
	    }    
	    
	    public String listeSpiele() {
	    	String sql = "SELECT id, Spieler1, Spieler2, status FROM Spiel;";
	    	//System.out.println(sql);
	    	String ergebnis = "";
	    	
	        try (Connection conn = DriverManager.getConnection(dbUrl)) {
	    		Statement stmt = conn.createStatement();
	        	ResultSet rs = stmt.executeQuery(sql);
	        	while (rs.next()) {
	        	ergebnis = ergebnis + "SpielID: " + ((Integer) rs.getObject(1)) + " Spieler1: " + ((String) rs.getObject(2))+ " Spieler2: " + ((String) rs.getObject(3))+ " Status: " + ((String) rs.getObject(4)) + "\r\n";
	        	}
	        	//System.out.println(name);
	    {
	    }
	    
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return ergebnis;
			
	    }
    
	    public void initializeMsgKatalog() {
	    	
	    	//String sql = "SELECT de FROM msgkatalog WHERE id = " + "'" + id+ "';";
	    	String sql = "LOAD DATA LOCAL INFILE 'USER_HOME/text.txt' INTO TABLE msgkatalog FIELDS TERMINATED BY ';' ENCLOSED BY '\"'  ESCAPED BY '\\\\' LINES TERMINATED BY '\\r\\n' IGNORE 1 LINES; ";
	    	
	    	//System.out.println(sql);
	    	
	    	
	        try (Connection conn = DriverManager.getConnection(dbUrl)) {
	    		Statement stmt = conn.createStatement();
	    		stmt.execute(sql);
	        	
	        	//System.out.println(name);
	    {
	    }
	    
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    } 
	    
	    public int insertSpiel(String pl1, String status)  {
	    	String sql = "INSERT INTO Spiel(id,Spieler1,status) VALUES(null,?,?)";
	    	int spielId = 0;
	    	
	        try (Connection conn = DriverManager.getConnection(dbUrl)) {
	        		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	            //INSERT Into
	        	pstmt.setString(1, pl1);
	            pstmt.setString(2, status);
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
	    
	    
	    public void initializePlayer() {
	    	String sql = "SELECT id FROM Spieler WHERE status != 'Offline';";
	    	String sql2 = "UPDATE Spieler set status='Offline',socket='nicht verbunden' WHERE id=?";
	    	//System.out.println(sql);
	    	int playerId = 0;
	    	
	        try (Connection conn = DriverManager.getConnection(dbUrl)) {
	    		Statement stmt = conn.createStatement();
	        	ResultSet rs = stmt.executeQuery(sql);
	        	while (rs.next()) {
	        	playerId = ((Number) rs.getObject(1)).intValue();
	        	try (Connection conn2 = DriverManager.getConnection(dbUrl)) {
	        		PreparedStatement pstmt = conn.prepareStatement(sql2);
	            //INSERT Into
	        	//int id = getNextIdPlayer();	
	            //int id = 3333;
	        	//pstmt.setInt(1, 0);
	            pstmt.setInt(1, playerId);
	            pstmt.executeUpdate();
	        	}
	        	}
	        	//System.out.println(playerId);
	    {
	    }
	    
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	    }
	    
	    public void initializeSpiel() {
	    	String sql = "SELECT id FROM Spiel WHERE status != 'Beendet';";
	    	String sql2 = "UPDATE Spiel set status='abgebrochen' WHERE id=?";
	    	//System.out.println(sql);
	    	int id = 0;
	    	
	        try (Connection conn = DriverManager.getConnection(dbUrl)) {
	    		Statement stmt = conn.createStatement();
	        	ResultSet rs = stmt.executeQuery(sql);
	        	while (rs.next()) {
	        	id = ((Number) rs.getObject(1)).intValue();
	        	try (Connection conn2 = DriverManager.getConnection(dbUrl)) {
	        		PreparedStatement pstmt = conn.prepareStatement(sql2);
	            //INSERT Into
	        	//int id = getNextIdPlayer();	
	            //int id = 3333;
	        	//pstmt.setInt(1, 0);
	            pstmt.setInt(1, id);
	            pstmt.executeUpdate();
	        	}
	        	}
	        	//System.out.println(playerId);
	    {
	    }
	    
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 	}
	    }
	    
    
}
