<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration page</title>
    <link rel="stylesheet" href="styles/bootstrap4/css/bootstrap.css">
    <link rel="stylesheet" href="styles/bootstrap4/css/bootstrap.min.css">
    <link rel="stylesheet" href="styles/style.css">
</head>
<body>
<div class="jumbotron">
    <h3>Registration</h3>
    <form action="main.do" method="post">
        <c:if test="${not empty errMessage}">
            <c:out value="${errMessage}"/>
        </c:if>
        <table id="register">
            <tr>
                <td>Username: </td>
                <td><input type="text" name="user_name" minlength="5" maxlength="20" required></td>
            </tr>
            <tr>
                <td>Password: </td>
                <td><input type="password" name="user_pass" minlength="5" maxlength="20" required></td>
            </tr>
            <tr>
                <td>ID: </td>
                <td><input type="text" name="user_id" required></td>
            </tr>
            <tr>
                <td>First name: </td>
                <td><input type="text" name="user_first_name" required></td>
            </tr>
            <tr>
                <td>Last name: </td>
                <td><input type="text" name="user_last_name" required></td>
            </tr>
            <tr>
                <td>Age: </td>
                <td><input type="number" name="user_age" min="5" max="130" required></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" name="user_submit"></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
