package demoProject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utility.Utility;
import bean.AlertDetail;
import bean.Inventory;
import bean.Order;
import bean.User;
import dao.InventoryDao;
import dao.OrderDao;
import dao.UserDao;

public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static HashMap<String, List<Inventory>> cartDetails = new HashMap<String, List<Inventory>>();
	
	public HomeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response){
	
		String method = request.getParameter("mode");
		
		try {
			
			if(method.equalsIgnoreCase("registerUser")){
				registerUser(request, response);
			}else if(method.equalsIgnoreCase("loginUser")){
				verifyUser(request, response);
			}else if(method.equalsIgnoreCase("logout")){
				invalidateSession(request.getSession());
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/Login.jsp");
			    rd.forward(request, response);
			}else{
				if(!checkUserLoggedIn(request.getSession())){
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/Login.jsp");
				    rd.forward(request, response);
				}else{
					if(method.equalsIgnoreCase("addToCart")){
						addItemToCart(request, response);
					}else if(method.equalsIgnoreCase("reviewOrderDetails")){
						displayCartDetails(request, response);
					}else if(method.equalsIgnoreCase("getInventoryDetails")){
						displayHomePage(request, response);
					}else if(method.equalsIgnoreCase("removeFromCart")){
						removeFromCart(request, response);
					}else if(method.equalsIgnoreCase("updateToCart")){
						updateCart(request, response);
					}else if(method.equalsIgnoreCase("checkout")){
						checkoutItems(request, response);
					}else if(method.equalsIgnoreCase("getOrders")){
						displayOrderDetails(request, response);
					}else if(method.equalsIgnoreCase("cancelOrder")){
						cancelOrder(request, response);
					}else if(method.equalsIgnoreCase("updateProfile")){
						updateProfile(request, response);
					}else if(method.equalsIgnoreCase("updateUserProfile")){
						updateUserProfile(request, response);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void updateUserProfile(HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		User user = new User(firstName, lastName, email, Utility.getEncryptedValue(password));
		
		boolean isSuccess = UserDao.updateCustomer(user);
		if(isSuccess){
			AlertDetail alertDetails = new AlertDetail("alert-success", "User details updated successfully!");
			request.setAttribute("alert", alertDetails);
			request.getSession().setAttribute("userName", user.getFirstName()+" "+user.getLastName());
		}else{
			AlertDetail alertDetails = new AlertDetail("alert-danger", "There is some problem in updation, please try again!");
			request.setAttribute("alert", alertDetails);
		}
		request.setAttribute("userData", user);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/Profile.jsp");
	    rd.forward(request, response);
	}

	private void updateProfile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId = getLoggedInUser(request.getSession());
		User user = UserDao.getCustomerDetails(userId);
		request.setAttribute("userData", user);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/Profile.jsp");
	    rd.forward(request, response);
	}

	private void cancelOrder(HttpServletRequest request,
			HttpServletResponse response)throws Exception {
		
		System.out.println(request.getParameter("orderId")+">>>>"+request.getParameter("itemId")+">>>"+request.getParameter("orderQuantity"));
		
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		int itemId = Integer.parseInt(request.getParameter("itemId"));
		int quantity = Integer.parseInt(request.getParameter("orderQuantity"));
		boolean isSuccess = OrderDao.cancelOrder(orderId, itemId, quantity);
		
		if(isSuccess){
			AlertDetail alertDetails = new AlertDetail("alert-success", "Order canceled successfully!");
			request.setAttribute("alert", alertDetails);
		}else{
			AlertDetail alertDetails = new AlertDetail("alert-danger", "There is some problem in order cancellation, please try again!");
			request.setAttribute("alert", alertDetails);
		}
		
		String userId = getLoggedInUser(request.getSession());
		List<Order> orderDetails = OrderDao.getOrderDetails(userId);
		request.setAttribute("orderData", orderDetails);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/Order.jsp");
	    rd.forward(request, response);
	}

	private void checkoutItems(HttpServletRequest request,
			HttpServletResponse response)throws Exception {
		String userId = getLoggedInUser(request.getSession());
		
		List<Inventory> itemList = cartDetails.get(userId);
		
		if(itemList != null && itemList.size() > 0){
			boolean isOrderSuccess = OrderDao.saveOrder(userId, itemList);
			if(isOrderSuccess){
				cartDetails.remove(userId);
				displayOrderDetails(request, response);
			}else{
				AlertDetail alertDetails = new AlertDetail("alert-warning", "There is some error in checkout, please try again!");
				request.setAttribute("alert", alertDetails);
				request.setAttribute("itemData", itemList);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/Cart.jsp");
			    rd.forward(request, response);
			}
		}else{
			AlertDetail alertDetails = new AlertDetail("alert-danger", "Empty cart, There are no items in cart to checkout!");
			request.setAttribute("alert", alertDetails);
			request.setAttribute("itemData", new ArrayList<Inventory>());
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/Cart.jsp");
		    rd.forward(request, response);
		}
	}

	private void displayOrderDetails(HttpServletRequest request,
			HttpServletResponse response)throws Exception {
		String userId = getLoggedInUser(request.getSession());
		List<Order> orderDetails = OrderDao.getOrderDetails(userId);
		request.setAttribute("orderData", orderDetails);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/Order.jsp");
	    rd.forward(request, response);
	}

	private void updateCart(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int itemId = Integer.parseInt(request.getParameter("itemId"));
		String name = request.getParameter("itemName");
		int price = Integer.parseInt(request.getParameter("itemPrice"));
		int orderedQuantity = Integer.parseInt(request.getParameter("itemQuantity"));
		int cost = price * orderedQuantity;
		
		String userId = getLoggedInUser(request.getSession());
		List<Inventory> itemList = null;
		if(cartDetails.containsKey(userId)){
			itemList = cartDetails.get(userId);
			for(Inventory itemDetail : itemList){
				if(itemDetail.getId() == itemId){
					itemDetail.setOrderedQuantity(orderedQuantity);
					itemDetail.setCost(cost);
				}
			}
		}
		
		AlertDetail alertDetails = new AlertDetail("alert-success", name+" successfully updated to the cart!");
		request.setAttribute("alert", alertDetails);
		request.setAttribute("itemData", itemList);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/Cart.jsp");
	    rd.forward(request, response);

	}

	private void removeFromCart(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int itemId = Integer.parseInt(request.getParameter("itemId"));
		String userId = getLoggedInUser(request.getSession());
		List<Inventory> itemData = cartDetails.get(userId);
		
		List<Inventory> newItemList = new ArrayList<>();
		
		for(Inventory item : itemData){
			if(item.getId() != itemId){
				newItemList.add(item);
			}
		}
		
		cartDetails.put(userId, newItemList);
		request.setAttribute("itemData", newItemList);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/Cart.jsp");
	    rd.forward(request, response);
	}

	private void displayCartDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String userId = getLoggedInUser(request.getSession());
		List<Inventory> itemData = cartDetails.get(userId);
		
		request.setAttribute("itemData", itemData);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/Cart.jsp");
	    rd.forward(request, response);
	}

	private void addItemToCart(HttpServletRequest request,
			HttpServletResponse response)throws Exception {
		int itemId = Integer.parseInt(request.getParameter("itemId"));
		String name = request.getParameter("itemName");
		String description = request.getParameter("itemDescription");
		int price = Integer.parseInt(request.getParameter("itemPrice"));
		int orderedQuantity = Integer.parseInt(request.getParameter("itemQuantity"));
		int availableQuantity = Integer.parseInt(request.getParameter("itemAvailable"));
		
		int cost = price * orderedQuantity;
		
		Inventory item = new Inventory();
		item.setId(itemId);
		item.setName(name);
		item.setDescription(description);
		item.setOrderedQuantity(orderedQuantity);
		item.setCost(cost);
		item.setAvailableQuantity(availableQuantity);
		item.setPrice(price);
		
		String userId = getLoggedInUser(request.getSession());
		
		if(cartDetails.containsKey(userId)){
			boolean isDuplicate = false;
			List<Inventory> itemList = cartDetails.get(userId);
			for(Inventory itemDetail : itemList){
				if(itemDetail.getId() == itemId){
					itemDetail.setOrderedQuantity(itemDetail.getOrderedQuantity() + orderedQuantity);
					itemDetail.setCost(itemDetail.getCost() + cost);
					isDuplicate = true;
				}
			}
			if(!isDuplicate){
				itemList.add(item);
			}
		}else{
			List<Inventory> itemList = new ArrayList<>();
			itemList.add(item);
			cartDetails.put(userId, itemList);
		}
		
		AlertDetail alertDetails = new AlertDetail("alert-success", name+" successfully added to the cart!");
		request.setAttribute("alert", alertDetails);
		displayHomePage(request, response);
	}

	private void verifyUser(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		User user = UserDao.getCustomerDetails(email);
		
		if(user != null && user.getPassword().equalsIgnoreCase(Utility.getEncryptedValue(password))){
			HttpSession session = request.getSession();
			session.setAttribute("userId", email);
			session.setAttribute("userName", user.getFirstName()+" "+user.getLastName());
			displayHomePage(request, response);
		}else{
			AlertDetail alertDetails = new AlertDetail("alert-danger", "Email or Password is incorrect!");
			request.setAttribute("alert", alertDetails);
		    RequestDispatcher rd = getServletContext().getRequestDispatcher("/Login.jsp");
		    rd.forward(request, response);
		}
	}

	private void displayHomePage(HttpServletRequest request,
			HttpServletResponse response)throws Exception {
		
		List<Inventory> itemData = InventoryDao.getItemDetails();
		request.setAttribute("itemData", itemData);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/HomePage.jsp");
	    rd.forward(request, response);
	}

	private void registerUser(HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		User user = UserDao.getCustomerDetails(email);
		
		if(user == null){
			user = new User(firstName, lastName, email, Utility.getEncryptedValue(password));
			boolean isSuccess = UserDao.registerUser(user);
			if(isSuccess){
				AlertDetail alertDetails = new AlertDetail("alert-success", "Registration successful");
				request.setAttribute("alert", alertDetails);
			    RequestDispatcher rd = getServletContext().getRequestDispatcher("/Login.jsp");
			    rd.forward(request, response);
			}else{
				AlertDetail alertDetails = new AlertDetail("alert-danger", "Error, please try again!");
				request.setAttribute("alert", alertDetails);
			    RequestDispatcher rd = getServletContext().getRequestDispatcher("/Register.jsp");
			    rd.forward(request, response);
			}
		}else{
			AlertDetail alertDetails = new AlertDetail("alert-danger", "Use different email, user already exist!");
			request.setAttribute("alert", alertDetails);
		    RequestDispatcher rd = getServletContext().getRequestDispatcher("/Register.jsp");
		    rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public boolean checkUserLoggedIn(HttpSession session){
		String userId = getLoggedInUser(session);
		if(userId.isEmpty())
			return false;
		else
			return true;
	}
	
	public String getLoggedInUser(HttpSession session){
		if(session != null){
			Object userId = session.getAttribute("userId");
			if(userId == null)
				return "";
			else
				return String.valueOf(userId);
		}else
			return "";
	}
	
	public void invalidateSession(HttpSession session){
		if(session != null){
			session.invalidate();
		}
	} 

}

