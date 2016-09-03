package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
	private Connection con = null;
	private static DatabaseManager instance = null;
	
	private DatabaseManager(){
		connect();
	}
	
	private static DatabaseManager getInstance(){
		if(instance == null){
			instance = new DatabaseManager();
		}
		return instance;
	}
	
	public static Connection getConnection(){
		return getInstance().con;
	}
	
	private void connect(){
		try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
		
		try {
            con=DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/testdb","SA","");
		} catch (SQLException e) {
            e.printStackTrace();
        }
	}
		
}
