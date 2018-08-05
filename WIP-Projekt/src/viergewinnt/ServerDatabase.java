package viergewinnt;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;


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
                + "	status text NOT NULL\n"
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
        
        
        
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
                Statement stmt = conn.createStatement();
            // create a new table
            stmt.execute(sql);
        
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        
        }
    }
    
    public String insertPlayer(String name, String status)  {
    	String sql = "INSERT INTO Spieler(id,name,status) VALUES(null,?,?)";
    	
    	
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
        		PreparedStatement pstmt = conn.prepareStatement(sql);
            //INSERT Into
        	//int id = getNextIdPlayer();	
            //int id = 3333;
        	//pstmt.setInt(1, 0);
            pstmt.setString(1, name);
            pstmt.setString(2, status);
            pstmt.executeUpdate();
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }
		return ("[Server] Spieler hinzugefügt");  	
    }
    
    //Wird nicht mehr benötigt
    //public int getNextIdPlayer() {
    //	String sql = "SELECT max(id) FROM Spieler;";
    //	int nextId = 0;
    	
    //    try (Connection conn = DriverManager.getConnection(dbUrl)) {
    //		Statement stmt = conn.createStatement();
    //    	ResultSet rs = stmt.executeQuery(sql);
    //    	nextId = ((Number) rs.getObject(1)).intValue();
    //    	nextId = nextId + 1;
    //    	System.out.println(nextId);
    //{
    //}
    
    //} catch (SQLException e) {
		// TODO Auto-generated catch block
	//	e.printStackTrace();
	//}
	//	return nextId;
    //}
        
}
