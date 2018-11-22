<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" href="styles/bootstrap4/css/bootstrap.css">
    <link rel="stylesheet" href="styles/bootstrap4/css/bootstrap.min.css">
    <link rel="stylesheet" href="styles/style.css">
</head>
<body class="bg-transparent">
    <%
        if (session.getAttribute("username") == null)
            response.sendRedirect("index.jsp");
    %>
<h2>Welcome <span class="name"><%=session.getAttribute("username")%></span> </h2>