package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import utility.DatabaseConnector;
import bean.Inventory;
import bean.Order;


public class OrderDao {
	
	private static Connection conn;
	private static PreparedStatement pst;
	private static ResultSet rs;

	public static boolean saveOrder(String userId, List<Inventory> itemList) {
		boolean isSuccess = false;
		conn = DatabaseConnector.getSQLInstance();
		try {
			conn = DatabaseConnector.getSQLInstance();
			for(Inventory item : itemList){
				String query = "INSERT INTO `test`.`Order` (`itemId`,`email`,`orderedAt`,`status`,`quantity`,`price`) VALUES (?,?,?,?,?,?)";
				pst = conn.prepareStatement(query);
				pst.setInt(1, item.getId());
				pst.setString(2, userId);
				pst.setDate(3, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
				pst.setString(4, "PENDING");
				pst.setInt(5, item.getOrderedQuantity());
				pst.setInt(6, item.getPrice());
				pst.execute();
				
				InventoryDao.updateInventory(item);
			}
			isSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		DatabaseConnector.closeConn();
		return isSuccess;
	}

	public static List<Order> getOrderDetails(String userId) {
		List<Order> orderList = new ArrayList<Order>();
		try {
			conn = DatabaseConnector.getSQLInstance();
			String query = "SELECT o.*, i.name, i.category FROM `test`.`Order` o JOIN `test`.`Inventory` i ON o.itemId = i.id WHERE email = ?";
			pst = conn.prepareStatement(query);
			pst.setString(1, userId);
			rs = pst.executeQuery();
			
			while(rs.next()){
				Order order =  new Order();
				order.setId(rs.getInt(1));
				order.setItemId(rs.getInt(2));
				order.setOrderedBy(rs.getString(3));
				order.setOrderedAt(rs.getDate(4));
				order.setStatus(rs.getString(5));
				order.setQuantity(rs.getInt(6));
				order.setPrice(rs.getInt(7));
				order.setName(rs.getString(8));
				order.setCategory(rs.getString(9));
				order.setCost(rs.getInt(7) * rs.getInt(6));
				
				orderList.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		DatabaseConnector.closeConn();
		
		return orderList;
	}

	public static boolean cancelOrder(int orderId, int itemId, int quantity) {
		boolean isSuccess = false;
		
		conn = DatabaseConnector.getSQLInstance();
		try {
			conn = DatabaseConnector.getSQLInstance();
			String query = "UPDATE `test`.`Order` SET `status` = ? WHERE `id` = ?";
			pst = conn.prepareStatement(query);
			pst.setString(1, "CANCELLED");
			pst.setInt(2, orderId);
			pst.executeUpdate();
			
			InventoryDao.updateCancelInventory(itemId, quantity);
			
			isSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		DatabaseConnector.closeConn();
		
		return isSuccess;
	}
}