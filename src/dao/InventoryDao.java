package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import utility.DatabaseConnector;
import bean.Inventory;


public class InventoryDao {
	
	private static Connection conn;
	private static PreparedStatement pst;
	private static ResultSet rs;

	public static List<Inventory> getItemDetails(){
		List<Inventory> itemList = new ArrayList<Inventory>();
		try {
			conn = DatabaseConnector.getSQLInstance();
			String query = "SELECT * FROM Inventory";
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();
			
			while(rs.next()){
				Inventory item = new Inventory();
				item.setId(rs.getInt(1));
				item.setName(rs.getString(2));
				item.setDescription(rs.getString(3));
				item.setCategory(rs.getString(4));
				item.setPrice(rs.getInt(5));
				item.setAvailableQuantity(rs.getInt(6));
				
				itemList.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		DatabaseConnector.closeConn();
		
		return itemList;
	}
	
	public static Inventory getItem(int id){
		Inventory item = null;
		try {
			conn = DatabaseConnector.getSQLInstance();
			String query = "SELECT * FROM Inventory where id=?";
			pst = conn.prepareStatement(query);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			
			while(rs.next()){
				item = new Inventory();
				item.setId(rs.getInt(1));
				item.setName(rs.getString(2));
				item.setDescription(rs.getString(3));
				item.setCategory(rs.getString(4));
				item.setPrice(rs.getInt(5));
				item.setAvailableQuantity(rs.getInt(6));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}

	public static void updateInventory(Inventory item) {
		int newQuantity = item.getAvailableQuantity() - item.getOrderedQuantity();
		if(newQuantity > 0){
			conn = DatabaseConnector.getSQLInstance();
			try {
				conn = DatabaseConnector.getSQLInstance();
				String query = "UPDATE `test`.`Inventory` SET `quantity` = ? WHERE `id` = ?";
				pst = conn.prepareStatement(query);
				pst.setInt(1, newQuantity);
				pst.setInt(2, item.getId());
				pst.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void updateCancelInventory(int itemId, int quantity) {
		Inventory item = getItem(itemId);
		
		if(item != null){
			int newQuantity = item.getAvailableQuantity() + quantity;
			conn = DatabaseConnector.getSQLInstance();
			try {
				conn = DatabaseConnector.getSQLInstance();
				String query = "UPDATE `test`.`Inventory` SET `quantity` = ? WHERE `id` = ?";
				pst = conn.prepareStatement(query);
				pst.setInt(1, newQuantity);
				pst.setInt(2, itemId);
				pst.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}