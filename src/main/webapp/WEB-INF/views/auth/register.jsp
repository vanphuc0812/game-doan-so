<%@ page import="cybersoft.javabackend.java18.gamedoanso.utils.UrlUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
    <title>Register</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<h1 class="text-center text-primary mt-5">REGISTER</h1>
		<div class="row mt-5">
			<div class="col-md-4 offset-md-4">
				<% if(request.getAttribute("errors") != null) { %>
				<div class="alert alert-danger" role="alert">
					${errors}
				</div>
				<%}%>
				<form action="<%=request.getContextPath() + UrlUtils.REGISTER%>" method="post">
					<div class="form-group">
						<label for="name">Name</label>
						<input type="text" class="form-control" name="name" id="name" aria-describedby="helpId"
							   required>
						<small id="helpId" class="form-text text-muted">Real name</small>
					</div><div class="form-group">
						<label for="username">Username</label>
						<input type="text" class="form-control" name="username" id="username" aria-describedby="usernameHelp"
							   required>
						<small id="usernameHelp" class="form-text text-muted">Username</small>
					</div><div class="form-group">
						<label for="password">Password</label>
						<input type="password" class="form-control" name="password" id="password" aria-describedby="passwordHelp"
							   required>
						<small id="passwordHelp" class="form-text text-muted">Password</small>
					</div>
					<button type="submit" class="btn btn-primary">Register</button>
					<br>
					<a href="<%=request.getContextPath() + UrlUtils.LOGIN%>">Have account? Login now!</a>

				</form>
			</div>
		</div>
	</div>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>
