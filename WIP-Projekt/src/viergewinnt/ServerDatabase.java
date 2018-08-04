package viergewinnt;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class ServerDatabase {

	public String USER_HOME = System.getProperty("user.home");
	public String dbUrl = "jdbc:sqlite:" + USER_HOME + "/" + "ServerDatabase.db";
	
    public void createNewDatabase() {
 

    	
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
        
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        
        
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        
        }
        }
    
}