<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: gaurav
  Date: 08/05/20
  Time: 8:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register:: Admin</title>
    <link rel="stylesheet" type="text/css" href="../form.css">
    <link rel="stylesheet" type="text/css" href="../navigation.css">
</head>
<body>
<ul>
    <li><a href="/student/login">Home</a></li>
</ul>
<div style="text-align: center;">
    <h2 style="color: #555555"> Admin Registration </h2>
    <form:form  id = "adminregisterform" action="/admin/register" modelAttribute = "adminregisterform" method = "post">

        <div style="color: darkorange">${result}</div>
        <form:input type="text" path = "username" placeholder = "Enter Username" required = "true"/>
        <br>
        <form:input path="password" type = "password" placeholder = "Enter Password" required = "true"/>
        <br>
        <form:input type = "text" path = "mobile" placeholder = "Enter Mobile Number" required = "true"/>
        <br>
        <input type = "password" name = "rootpassword" placeholder = "Enter Root Password" required = "true"/>
        <br>
        <input value = "Register" type="submit"/>

    </form:form>
<br><br>
<a href="/admin/login"><button class="button">Login</button></a>
</div>
</body>
</html>
