package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector{

	private static final String MYSQL_DB_NAME = "TEST";
	private static final String MYSQL_URL = "jdbc:mysql://127.0.0.1:3306/";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "kunal";

	private static Connection con = null;
	
	private static void connectToMYSQL(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(MYSQL_URL+MYSQL_DB_NAME, USER_NAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getSQLInstance(){
		try {
			if(con == null || con.isClosed())
				connectToMYSQL();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public static void closeConn() {
		try {
			if(con.isValid(0)){
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}