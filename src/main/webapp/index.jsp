<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>cloudant</title>
</head>
<body>
	<h3>Cloudant NoSQL</h3>

    <form action="Image" method="GET" enctype="multipart/form-data">
            <input type="text" name="imageurl" /><br>
       
            <input type="submit" class="btn" value="Extract Information" />
    </form>
	<% if (request.getAttribute("age") != null) { %>
       	<div><%= request.getAttribute("age") %></div>
    <% } %> 
	
	<% if (request.getAttribute("gender") != null) { %>
       	<div><%= request.getAttribute("gender") %></div>
    <% } %> 
</body>
</html>