<%--
  Created by IntelliJ IDEA.
  User: gaurav
  Date: 09/05/20
  Time: 5:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Your Complaints</title>
    <link rel="stylesheet" type="text/css" href="../navigation.css"/>
    <link rel="stylesheet" type="text/css" href="../button.css"/>
    <link rel="stylesheet" type="text/css" href="../table.css"/>
    <link rel="stylesheet" type="text/css" href="../form.css"/>
</head>
<body>
<ul>
    <li><a href="/student/yourcomplaints/">Home</a></li>
    <li><a href="/student/tradeView/">Rent/To-let</a></li>
    <li style = "float:right" class="active"><a href="/student/logout">Logout</a></li>
    <li style = "float:right"><a href="#contact">Your Profile</a></li>

</ul>

<div style="text-align: center;">
    <h1>${username} </h1>
    <h2>Your Complaints Are Below</h2>
    <br><br>
    <div style="color: darkorange">${status}</div>
    <form action="/addComplaint" method="post">
        <input type="text" placeholder="Enter New Complaint" name="issue">
        <input type="submit" value="Add">
    </form>
</div>
    <br><br>
        <table class="center" id = "complaints">
            <thead>
                <tr>
                    <th>Time Stamp</th>
                    <th>Issue</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${data}">
                    <tr >
                        <td>${item.timestamp}</td>
                        <td>${item.issue}</td>
                        <td>${item.status}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
</body>
</html>
