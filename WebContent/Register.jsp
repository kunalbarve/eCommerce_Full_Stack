<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>Register</title>
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
<body class="mainBackground">

	<form id="registerForm" name="actionForm" data-toggle="validator" action="HomeServlet"
		method="POST">


		<div class="col-md-4 col-md-offset-7">
			<br>
			<br>
			<div class="login-panel panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Sign Up</h3>
				</div>

				<div class="panel-body">
					
					<c:if test="${alert != null}">
						<div class="alert ${alert.type}">
						  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						  ${alert.message}
						</div>
					</c:if>
					
					<div class="form-group">
						<label for="firstName" class="control-label">First Name</label> <input
							type="text" class="form-control" id="firstName" name="firstName"
							placeholder="Enter First Name"
							required>
						<span id="emptyName" class="confirmMessage"></span>
					</div>

					<div class="form-group">
						<label for="lastName" class="control-label">Last Name</label> <input
							type="text" class="form-control" id="lastName" name="lastName"
							placeholder="Enter Last Name"
							required>
						<span id="emptyLastName" class="confirmMessage"></span>
					</div>


					<div class="form-group">
						<label for="email" class="control-label">Email</label> <input
							type="email" class="form-control" id="email" name="email"
							placeholder="Email"
							data-error="Email address is invalid" required>
						<span id="emptyEmail" class="confirmMessage"></span>
					</div>
					
					<div class="form-group">
						<label for="password" class="control-label">Password</label> <input
							type="password" class="form-control" id="password" name="password"
							placeholder="Enter Password"
							required>
						<span id="emptyPassword" class="confirmMessage"></span>
					</div>
					
					<div class="form-group">
						<label for="confirmPassword" class="control-label">Confirm Password</label> <input
							type="password" class="form-control" id="confirmPassword" name="confirmPassword"
							placeholder="Reenter Password" required>
							<span id="confirmMessage" class="confirmMessage"></span>
					</div>

					<div class="form-group">
						<div class="checkbox">
							<label> <input type="checkbox" id="terms"
								data-error="Please accept the aggrement." required> I
								agree to use the service.
							</label>
							<div class="help-block with-errors"></div>
						</div>
					</div>

					<div class="form-group">
							<button type="submit" class="btn btn-primary">Register</button>
							&nbsp;&nbsp;&nbsp;
							<a href="#" onclick="login();return false;">Cancel</a>
							<input type="hidden" name="mode" value="registerUser" />
					</div>
				</div>

			</div>

		</div>
	</form>
</body>

<script type="text/javascript">
	function login(){
		window.location.replace("Login.jsp");
	}
	
	function registerUser(){
		  var first = document.getElementById('firstName').value;
		  var last = document.getElementById('lastName').value;
		  var email = document.getElementById('email').value;
		  var pass1 = document.getElementById('password').value;
		  var pass2 = document.getElementById('confirmPassword').value;
		  var message = document.getElementById('confirmMessage');
		  var emptyName = document.getElementById('emptyName');
		  var emptyLast = document.getElementById('emptyLast');
		  var emptyEmail = document.getElementById('emptyEmail');
		  var emptyPassword = document.getElementById('emptyPassword');
		  
		  var badColor = "#ff0000";
		  
		  if(first == "" || last == "" || email == "" || pass1 == "" || pass2 == ""){
			  if(first == ""){
				  emptyName.style.color = badColor;
				  emptyName.innerHTML = "First Name is required";
			  }else{
				  emptyName.innerHTML = "";
			  }
			  
			  if(last == ""){
				  emptyLast.style.color = badColor;
				  emptyLast.innerHTML = "Last Name is required";
			  }else{
				  emptyLast.innerHTML = "";
			  }
			  
			  if(email == ""){
				  emptyEmail.style.color = badColor;
				  emptyEmail.innerHTML = "Email is required";
			  }else{
				  emptyEmail.innerHTML = "";
			  }
			  
			  if(paass1 == ""){
				  emptyPassword.style.color = badColor;
				  emptyPassword.innerHTML = "Password is required";
			  }else{
				  emptyPassword.innerHTML = "";
			  }
			  
			  if(pass2 == ""){
				  message.style.color = badColor;
				  message.innerHTML = "Confirm password is required";
			  }else{
				  message.innerHTML = "";
			  }
		  }else  if(pass1 != pass2){
		      message.style.color = badColor;
		      message.innerHTML = "Passwords Do Not Match!";
		  }else{
			  document.getElementById("registerForm").submit();
		  }
		  
		  
		    
		 
	}
</script>

</html>
