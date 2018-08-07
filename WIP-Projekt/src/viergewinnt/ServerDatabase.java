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
                + " passwort text NOT NULL\n"
                + ");";
        }
        // SQL statement for creating a new table
        if(tableName == "Spiel") {
    	sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	Spieler1 text NOT NULL,\n"
                + "	Spieler2 text NOT NULL,\n"
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
    	String sql = "INSERT INTO Spieler(id,name,status,socket,passwort) VALUES(null,?,?,?,?)";
    	int playerId = 0;
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
        		PreparedStatement pstmt = conn.prepareStatement(sql);
            //INSERT Into
        	//int id = getNextIdPlayer();	
            //int id = 3333;
        	//pstmt.setInt(1, 0);
            pstmt.setString(1, name);
            pstmt.setString(2, status);
            pstmt.setString(3, socket);
            pstmt.setString(4, passwort);
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
    
}
