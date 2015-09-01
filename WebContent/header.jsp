<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true" %>
<html>
<head>
	<meta http-equiv="Content-Type"
		content="text/html; charset=windows-1256">
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<link rel="stylesheet" href="css/style.css">
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
	<script src="js/utility.js"></script>
</head>
<body>
	<nav class="navbar navbar-default navbar-fixed-top topnav"
		role="navigation">
		<div class="topnav" style="backgroud-color: #888888;">
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1" style="margin-left: 50px;">
				<ul class="nav navbar-nav">
					<li><a href="#" onclick="createRequest('/DemoProject/HomeServlet', {mode: 'getInventoryDetails'}, 'post'); return false;"><i
							class="glyphicon glyphicon-home"></i> Home</a></li>

					<li><a href="" onclick="createRequest('/DemoProject/HomeServlet', {mode: 'updateProfile'}, 'post'); return false;"><i
							class="glyphicon glyphicon-user"></i> Profile</a></li>
							
					<li><a href="" onclick="createRequest('/DemoProject/HomeServlet', {mode: 'reviewOrderDetails'}, 'post'); return false;"><i
							class="glyphicon glyphicon-shopping-cart"></i> My Cart</a></li>
					
					<li><a href="" onclick="createRequest('/DemoProject/HomeServlet', {mode: 'getOrders'}, 'post'); return false;"><i
							class="glyphicon glyphicon-list"></i> View Orders</a></li>
					
					
				</ul>

				<ul class="nav navbar-nav navbar-right">
					<li style="margin-top: 15px; margin-right: 10px; color: #500000;">Welcome ${sessionScope.userName}</li>
					
					<li style="margin-right: 60px;"><a href="" onclick="createRequest('/DemoProject/HomeServlet', {mode: 'logout'}, 'post'); return false;"><i
							class="glyphicon glyphicon-off"></i> Logout</a></li>
				</ul>
			</div>
		</div>
	</nav>
</body>

<script type="text/javascript">
	function goHome(){
		window.location.replace("Home.jsp");
	}
</script>

</html>