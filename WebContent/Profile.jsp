<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>User Profile</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

</head>
<body class="innerBackground">

	<jsp:include page="header.jsp" />
	
	<form id="userForm" name="actionForm" data-toggle="validator" action="HomeServlet"
		method="POST">
		
		
		<div class="col-md-10 col-md-offset-1">
		<br> <br> <br><br><br>
			<div class="login-panel panel panel-default">

				<div class="panel-body">
				
					<h3>Update Profile</h3><br>
					
					<c:if test="${alert != null}">
						<div class="alert ${alert.type}">
						  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						  ${alert.message}
						</div>
					</c:if>
					
					<div class="form-group">
						<label for="email" class="control-label">Email</label> <input
							type="email" class="form-control" id="email" name="email" readonly="readonly" value="${userData.email}">
					</div>
				
					<div class="form-group">
						<label for="firstName" class="control-label">First Name</label> <input
							type="text" class="form-control" id="firstName" name="firstName"
							value="${userData.firstName}"
							required>
						<div class="help-block with-errors"></div>
					</div>

					<div class="form-group">
						<label for="lastName" class="control-label">Last Name</label> <input
							type="text" class="form-control" id="lastName" name="lastName"
							value="${userData.lastName}"
							required>
						<div class="help-block with-errors"></div>
					</div>

					<div class="form-group">
						<label for="password" class="control-label">New Password</label> <input
							type="password" class="form-control" id="password" name="password"
							placeholder="Enter Password"
							required>
						<div class="help-block with-errors"></div>
					</div>
					
					<div class="form-group">
						<label for="confirmPassword" class="control-label">Confirm Password</label> <input
							type="password" class="form-control" id="confirmPassword" name="confirmPassword"
							placeholder="Reenter Password" required>
							<span id="confirmMessage" class="confirmMessage"></span>
					</div>
					
					<div class="form-group">
							<button type="submit" class="btn btn-primary">Update</button>
							<input type="hidden" name="mode" value="updateUserProfile" />
					</div>
				</div>

			</div>

		</div>
	</form>
	
	<jsp:include page="footer.jsp" />

</body>

<script type="text/javascript">
	function updateUser(){
		  var pass1 = document.getElementById('password');
		  var pass2 = document.getElementById('confirmPassword');
		  var message = document.getElementById('confirmMessage');
		  
		  var badColor = "#ff0000";
		    
		  if(pass1.value != pass2.value){
		      message.style.color = badColor;
		      message.innerHTML = "Passwords Do Not Match!";
		  }else{
			  document.getElementById("userForm").submit();
		  }
	}
</script>

</html>
