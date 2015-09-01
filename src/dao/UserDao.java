package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import utility.DatabaseConnector;
import bean.User;


public class UserDao {
	
	private static Connection conn;
	private static PreparedStatement pst;
	private static ResultSet rs;
	
	public static boolean registerUser(User user) {
		boolean isSuccess = false;
		conn = DatabaseConnector.getSQLInstance();
		try {
			conn = DatabaseConnector.getSQLInstance();
			String query = "INSERT INTO Customer VALUES(?,?,?,?)";
			pst = conn.prepareStatement(query);
			pst.setString(1, user.getEmail());
			pst.setString(2, user.getFirstName());
			pst.setString(3, user.getLastName());
			pst.setString(4, user.getPassword());
			pst.execute();
			isSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		DatabaseConnector.closeConn();
		return isSuccess;
	}

	public static User getCustomerDetails(String email) {
		User user = null;
		try {
			conn = DatabaseConnector.getSQLInstance();
			String query = "SELECT * FROM Customer WHERE email = ?";
			pst = conn.prepareStatement(query);
			pst.setString(1, email);
			rs = pst.executeQuery();
			
			while(rs.next()){
				user = new User();
				user.setEmail(rs.getString(1));
				user.setFirstName(rs.getString(2));
				user.setLastName(rs.getString(3));
				user.setPassword(rs.getString(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		DatabaseConnector.closeConn();
		
		return user;
	}

	public static boolean updateCustomer(User user) {
		boolean isSuccess = false;
		conn = DatabaseConnector.getSQLInstance();
		try {
			conn = DatabaseConnector.getSQLInstance();
			String query = "UPDATE Customer SET firstName = ?, lastName = ?, password=? WHERE email = ?";
			pst = conn.prepareStatement(query);
			pst.setString(1, user.getFirstName());
			pst.setString(2, user.getLastName());
			pst.setString(3, user.getPassword());
			pst.setString(4, user.getEmail());
			pst.executeUpdate();
			isSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		DatabaseConnector.closeConn();
		return isSuccess;
	}
	
}