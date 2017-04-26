<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>

<body>

    <div id="adminHome">
       <a href="manage_categories"> Manage Categories </a>  &nbsp; &nbsp;

       <a href="manage_suppliers"> Manage Suppliers </a>    &nbsp; &nbsp;

       <a href="manage_products"> Manage Products </a>    &nbsp; &nbsp;
       
    </div>

       <br>
<br>
<br>

<c:if test="${isUserClickedCategories==true}">

    <jsp:include page="Category.jsp"></jsp:include>

</c:if>

<c:if test="${isUserClickedSuppliers==true}">

    <jsp:include page="Supplier.jsp"></jsp:include>

</c:if>

<c:if test="${isUserClickedProducts==true}">

    <jsp:include page="Product.jsp"></jsp:include>

</c:if>


</body>
</html>