<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" href="styles/bootstrap4/css/bootstrap.css">
    <link rel="stylesheet" href="styles/bootstrap4/css/bootstrap.min.css">
    <link rel="stylesheet" href="styles/style.css">
</head>
<body class="bg-light">
    <%
        if (session.getAttribute("username") != null && (Integer) session.getAttribute("status") == 0)
            response.sendRedirect("main.do");

        if (session.getAttribute("username") != null && (Integer) session.getAttribute("status") == 1)
            response.sendRedirect("edit.do");
    %>
    <div class="container jumbotron">
        <h2>Welcome to <span class="name">AVERSI</span></h2>
        <%--<hr>--%>
        <p>Please login: </p>
        <c:if test="${not empty errMessage}">
            <p class="alert-danger"><c:out value="${errMessage}"/></p>
        </c:if>
        <form action="/login" method="post">

            <table>
                <tr>
                    <td><lable for="user_name">Username:</lable></td>
                    <td><input type="text" name="user_name" id="user_name" minlength="5" maxlength="20"></td>
                </tr>
                <tr>
                    <td><label for="user_password">Password:</label></td>
                    <td><input type="password" id="user_password" name="user_password" minlength="5" maxlength="20"></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input class="please_login btn btn-primary" type="submit" value="login"></td>
                </tr>
                <tr>
                    <td><a href="register.jsp">register</a></td>
                    <td></td>
                </tr>
            </table>
        </form>
    </div>
    <div class="footer-copyright text-center py-3">&copy 2018 Copyright:
        <a href="https://aversi.ge">Aversi.ge</a>
    </div>
</body>
</html>
