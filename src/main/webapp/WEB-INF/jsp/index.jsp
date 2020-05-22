<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: gaurav
  Date: 08/05/20
  Time: 11:39 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home:: doComplaint</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/button.css"/>
</head>
<body>
    <div style="text-align: center;padding-top: 200px">
        <br><br>
        <a href="${pageContext.request.contextPath}/admin/login"><button class="button">ADMIN</button></a>
        <br><br>
        <a href="${pageContext.request.contextPath}/student/login"><button class="button">Student</button></a>
    </div>
</body>
</html>
