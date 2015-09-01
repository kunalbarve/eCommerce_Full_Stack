<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
	<title>Order Details</title>
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
				
					<h3>Order Details</h3><br>
					<br>					
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
								<th>Category</th>
								<th>Quantity</th>
								<th>Cost</th>
								<th>Date</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="order" items="${orderData}">
								<tr>
									<td>${order.name}</td>
									<td>${order.category}</td>
									<td>${order.quantity}</td>
									<td>$${order.cost}</td>
									<td>${order.orderedAt}</td>
									<td>
										<c:if test="${order.status == 'PENDING'}">
											<button type="button" class="btn btn-warning"  data-toggle="modal" data-target="#cancelOrderModal"
												onclick='cancelOrder("${order.id}", "${order.itemId}", "${order.quantity}");'>Cancel</button>
										</c:if> 
										<c:if test="${order.status == 'DELIVERED'}">
											${order.status}
										</c:if>
										<c:if test="${order.status == 'CANCELLED'}">
											${order.status}
										</c:if>
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
	
	<div class="modal fade" id="cancelOrderModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-body">
	      		<form id="orderForm" name="actionForm" data-toggle="validator" action="HomeServlet" method="POST">
	      			<input type="hidden" id="orderId" name="orderId">
	      			<input type="hidden" id="itemId" name="itemId">
	      			<input type="hidden" id="orderQuantity" name="orderQuantity">
	      			<h4><strong>Do you really want to cancel this pending order?</strong></h4>
	      			<input type="hidden" name="mode" value="cancelOrder" />
	      		</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" onclick="submitCancelOrder();" class="btn btn-primary">Yes</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
	      </div>
	    </div>
	  </div>
	</div>
</body>
<script type="text/javascript">
function cancelOrder(orderId, itemId, quantity){
	var order = document.getElementById('orderId');
	var item = document.getElementById('itemId');
	var orderQuantity = document.getElementById('orderQuantity');
	order.value = orderId;
	item.value = itemId;
	
	orderQuantity.value = quantity;
}

function submitCancelOrder(){
	 document.getElementById("orderForm").submit();
}

</script>
</html>