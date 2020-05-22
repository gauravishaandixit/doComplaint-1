<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dhruvin
  Date: 18/05/20
  Time: 6:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Rents</title>
    <link rel="stylesheet" type="text/css" href="../navigation.css"/>
    <link rel="stylesheet" type="text/css" href="../button.css"/>
    <link rel="stylesheet" type="text/css" href="../table.css"/>
    <link rel="stylesheet" type="text/css" href="../form.css"/>
</head>

<body>
<ul>
    <li><a href="/student/yourcomplaints/">Home</a></li>
    <li><a href="/student/rentView/">Rent/To-let</a></li>
    <li><a href="/getAllRents/">All Rents</a></li>
    <li style = "float:right" class="active"><a href="/student/logout">Logout</a></li>
    <li style = "float:right"><a href="#contact">Your Profile</a></li>

</ul>
<form action="/addRent" method="post" enctype="multipart/form-data">
    <input type="text" placeholder="Enter item name" name="item_name">
    <input type="text" placeholder="Enter Short description" name="shortDesc">
    <input type="text" placeholder="Enter description" name="description">
    <input type="text" placeholder="Enter price" name="price">
    <input type="file" name="image">
    <input type="submit" value="Add">
</form>
<br><br>
<table class="center" id = "complaints">
    <thead>
    <tr>
        <th>count</th>
        <th>Time Stamp</th>
        <th>Issue</th>
        <th>Status</th>
        <th>Image</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${data}">
        <tr >
            <td>1</td>
            <td>${item.timestamp}</td>
            <td>${item.item_name}</td>
            <td>${item.description}</td>
            <td><img src="${item.img_url}" width="100" height="100"></td>
        </tr>
        <tr><img src="${item.img_url}" width="100" height="100"></tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
