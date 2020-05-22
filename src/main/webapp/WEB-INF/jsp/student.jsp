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
    <title>Student Login:: doComplaint</title>
    <link rel="stylesheet" type="text/css" href="../form.css">
</head>
<body>
<br>
<div style="text-align: center;">
    <h2 style="color: #555555"> Student Login </h2>
    <a style="float: right" href="/admin/login"><button class="button">Admin</button></a>
    <br><br>
    <br>
    <br>
    <br><br>
    <div style="color: darkorange">${nouser}</div>
    <form:form id = "studentloginform" method = "post" action = "/student/logincheck" modelAttribute = "studentloginform">
        <form:input path = "username" type = "text" placeholder = "Enter Username" required = "true"/>
        <br>
        <form:input path = "password" type = "password" placeholder = "Enter Password" required = "true"/>
        <br>
        <input type = "submit" value = "Login">
    </form:form>
    <br><br>
    <a href = "/student/register"><button class="button">Register</button></a>
</div>
</body>
</html>
