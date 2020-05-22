<%--
  Created by IntelliJ IDEA.
  User: gaurav
  Date: 08/05/20
  Time: 5:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Login:: doComplaint</title>
    <link rel="stylesheet" type="text/css" href="../form.css"/>
</head>
<br>
<div style="text-align: center;">
    <h2 style="color: #555555"> Admin Login </h2>
    <a style="float: right" href="/student/login"><button class="button">Student</button></a>
    <br><br>
    <br><br>
    <br><br>
    <div style="color: darkorange">${nouser}</div>
<form:form id = "adminloginform" method = "post" action = "/admin/logincheck" modelAttribute = "adminloginform">
    <form:input path = "username" type = "text" placeholder = "Enter Username" name = "username" required = "true"/>
    <br>
    <form:input path = "password" type = "password" placeholder = "Enter Password" required = "true"/>
    <br>
    <input type = "submit" value = "Login" >
</form:form>
    <br><br>
<a href = "/admin/register"><button class="button">Register</button></a>
</div>
</body>
</html>
