<%@page import="com.User" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>EG System Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<link rel="stylesheet" href="Views/users.css">
<script src="Components/jquery-3.2.1.js"></script>
<script src="Components/users.js"></script>

</head>
<body>
<div class="container">
 <div class="row">
 <div class="col-6">

<h1>User Management</h1>
<h4>Add New User Details</h4>
<form id="formUsers" name="formUsers" method="post" enctype="multipart/form-data">
	 First Name:
	<input id="firstName" name="firstName" type="text" class="form-control form-control-sm" placeholder="Enter First Name">
	<br> Last Name:
	<input id="lastName" name="lastName" type="text" class="form-control form-control-sm" placeholder="Enter Last Name">
	<br> Account No:
	<input id="accountNo" name="accountNo" type="text" class="form-control form-control-sm" placeholder="Enter Account No">
	<br> Address:
	<input id="address" name="address" type="text" class="form-control form-control-sm" placeholder="Enter Address">
	<br> Phone:
	<input id="phone" name="phone" type="text" class="form-control form-control-sm" placeholder="Enter Phone no">
	
	<br>
	<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-info">
	<input type="hidden" id="hidUserIdSave" name="hidUserIdSave" value="">
</form>
<br>
<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>

<br>
<h4>All Users Details</h4>
<div id="divUsersGrid">
	 <%
	 	User user = new User();
	 	out.print(user.readUser());
	 %>
</div>

</div>
</div>
</div>
</body>
</html>