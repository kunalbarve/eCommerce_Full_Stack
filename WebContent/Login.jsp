<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>Login</title>
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

	<form name="actionForm" data-toggle="validator" action="HomeServlet"
		method="POST">


		<div class="col-md-4 col-md-offset-7">
			<br>
			<br>
			<div class="login-panel panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Sign In</h3>
				</div>

				<div class="panel-body">
					
					<c:if test="${alert != null}">
						<div class="alert ${alert.type}">
						  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						  ${alert.message}
						</div>
					</c:if>
					
					<div class="form-group">
						<label for="email" class="control-label">Email</label> <input
							type="email" class="form-control" id="email" name="email"
							placeholder="Email"
							data-error="Email address is invalid" required>
						<div class="help-block with-errors"></div>
					</div>
					
					<div class="form-group">
						<label for="password" class="control-label">Password</label> <input
							type="password" class="form-control" id="password" name="password"
							placeholder="Enter Password"
							required>
						<div class="help-block with-errors"></div>
					</div>
					
					<div class="form-group">
						<button type="submit"class="btn btn-primary">Login</button>
							&nbsp;&nbsp;&nbsp;
						<a href="#" onclick="register();return false;">Register</a>
						<input type="hidden" name="mode" value="loginUser" />
					</div>
				</div>

			</div>

		</div>
	</form>
</body>

<script type="text/javascript">
	function register(){
		window.location.replace("Register.jsp");
	}
</script>

</html>
