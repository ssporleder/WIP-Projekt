package viergewinnt;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class ServerDatabase {

	public static String USER_HOME = System.getProperty("user.home");
	public static String dbUrl = "jdbc:sqlite:" + USER_HOME + "/" + "ServerDatabase.db";
	
    public static void createNewDatabase() {
 
    	
        //String url = "jdbc:sqlite:" + USER_HOME + "/" + fileName;
 
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
 
    /**
     * @param args the command line arguments
     */
    
    public static void createNewTable(String tableName) {
        // SQLite connection string
    	
        //String url = "jdbc:sqlite:" + USER_HOME + "/" + fileName;
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName +"(\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	capacity real\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(dbUrl);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
	
	
}
