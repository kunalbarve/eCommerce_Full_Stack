<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
	<title>HomePage</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<link rel="stylesheet" href="css/style.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
	<script src="js/utility.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body class="innerBackground">
	<jsp:include page="header.jsp" />

	<form>
		<br> <br> <br> <br>
		<div class="col-md-12">

			<div class="login-panel panel panel-default">
				<div class="panel-body">
				
					<h3>Select Products</h3><br>
					
					<c:if test="${alert != null}">
						<div class="alert ${alert.type}">
						  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						  ${alert.message}
						</div>
					</c:if>
					
					<table class="table table-hover table-striped">
						<thead>
							<tr>
								<th>Name</th>
								<th>Description</th>
								<th>Category</th>
								<th>Price</th>
								<th>Available Quantity</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${itemData}">
								<tr>
									<td>${item.name}</td>
									<td>${item.description}</td>
									<td>${item.category}</td>
									<td>$${item.price}</td>
									<td>${item.availableQuantity}</td>
									<td>
										<button type="button" class="btn btn-info" data-toggle="modal" data-target="#myModal"
										 onclick='updateModal("${item.id}", "${item.name}", "${item.description}", "${item.price}", "${item.availableQuantity}");'>Add to Cart</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

			</div>

		</div>
	</form>

	<jsp:include page="footer.jsp" />
	
	
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h3 class="modal-title" id="myModalLabel">Product Details</h3>
	      </div>
	      <div class="modal-body">
	      		<form id="cartForm" name="actionForm" data-toggle="validator" action="HomeServlet" method="POST">
	      		  
	      		  <input type="hidden" class="form-control" id="itemId" name="itemId">
      			  
      			  <div>
      			  	<img id="itemImage" src="" height="150" width="200">
      			  </div>
      			 
      			  <div class="form-group">
		          	<label for="itemName" class="control-label">Name:</label>
		            <input type="text" class="form-control" id="itemName" name="itemName" readonly="readonly">
		          </div>
		          
		          <div class="form-group">
		            <label for="itemDescription" class="control-label">Description:</label>
		            <input type="text" class="form-control" id="itemDescription" name="itemDescription" readonly="readonly">
		          </div>
		          
		          <div class="form-group">
		            <label for="itemPrice" class="control-label">Price:</label>
		            <input type="text" class="form-control" id="itemPrice" name="itemPrice" readonly="readonly">
		          </div>
		          
		          <div class="form-group">
		            <label for="itemAvailable" class="control-label">Available Items:</label>
		            <input type="text" class="form-control" id="itemAvailable" name="itemAvailable" readonly="readonly">
		          </div>
		          
		          <div class="form-group">
		            <label for="itemQuantity" class="control-label">Enter Quantity:</label>
		            <input type="number" class="form-control" id="itemQuantity" name="itemQuantity">
		            <span id="quantityMissing" class="confirmMessage"></span>
		          </div>
		          
		          <input type="hidden" name="mode" value="addToCart" />
		        </form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" onclick="saveToCart();" class="btn btn-primary">Save</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div>
</body>

<script type="text/javascript">


function updateModal(id, name, description, price, availableQuantity){
	var itemId = document.getElementById('itemId');
	var itemName = document.getElementById('itemName');
	var itemDescription = document.getElementById('itemDescription');
	var itemPrice = document.getElementById('itemPrice');
	var itemAvailable = document.getElementById('itemAvailable');
	var quantity = document.getElementById('itemQuantity');
	var message = document.getElementById('quantityMissing');
	var image = document.getElementById('itemImage');
	
	var imageName = name+".jpeg";
	
	image.src = "images/"+imageName;
	
	itemId.value = id;
	itemName.value = name;
	itemDescription.value = description;
	itemPrice.value = price;
	itemAvailable.value = availableQuantity;
	quantity.value = "";
	message.innerHTML = "";
}

function saveToCart(){
	  var itemAvailable = document.getElementById('itemAvailable');
	  var quantity = document.getElementById('itemQuantity');
	  var message = document.getElementById('quantityMissing');
	  
	  message.style.color = "#ff0000";
	    
	  if(quantity.value == ""){
	      message.innerHTML = "Enter quantity you want to buy.";
	  }else if(Number(quantity.value) > Number(itemAvailable.value)){
		  message.innerHTML = "Can not order more than available items.";
	  }else{
		  document.getElementById("cartForm").submit();
	  }
}
</script>
</html>