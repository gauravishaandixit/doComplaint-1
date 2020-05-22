<%--
  Created by IntelliJ IDEA.
  User: gaurav
  Date: 11/05/20
  Time: 10:09 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>All Complaints</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/navigation.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/button.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/table.css"/>
</head>
<body>
<ul>
    <li><a href="/admin/complaints">Home</a></li>
    <li style = "float:right" class="active"><a href="/admin/logout">Logout</a></li>
    <li style = "float:right"><a href="#contact">Your Profile</a></li>
</ul>
<br>
<div style="text-align: center">
    <h1 style="color: #555555;">Welcome ${username}</h1>
    <h2 style="color: #555555"> All Complaints</h2>
</div>
<div>
    <a href="/admin/complaints"><button class="button">Unresolved</button></a>
    <a href="/admin/complaints/all"><button class="button">All</button></a>
</div>
<br>
<table class="center" id = "complaints">
    <thead>
    <tr>
        <th>ID</th>
        <th>Time Stamp</th>
        <th>Complainant</th>
        <th>Room</th>
        <th>Mobile Number</th>
        <th>Issue</th>
        <th>Status</th>
        <th>Change</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${data}">
        <tr>
            <td>${item.id}</td>
            <td>${item.timestamp}</td>
            <td>${item.username}</td>
            <td>${item.roomnumber}</td>
            <td>${item.mobilenumber}</td>
            <td>${item.issue}</td>
            <td>${item.status}</td>
            <td><a href = "/admin/update/${item.id}"><button class="button">Update Status</button></a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
