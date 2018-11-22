<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="templates/header.jsp"/>

<%--for some reason its not working from included header.jsp--%>
<%
    if (session.getAttribute("username") == null)
        response.sendRedirect("index.jsp");
%>
    <input type="button" value="add drug"
        onclick="window.location.href='add-drug.jsp'; return false;" />
    <div class="container">
        <table class="table table-bordered table-striped">
            <tr>
                <th>Name</th>
                <th>description</th>
                <th>price</th>
                <th>quantity</th>
                <th>action</th>
            </tr>
            <c:forEach var="drug" items="${drugs}">
                <c:url var="tempLink" value="edit.do">
                    <c:param name="drug_name" value="${drug.name}" />
                </c:url>

                <tr>
                    <td>${drug.name}</td>
                    <td>${drug.description}</td>
                    <td>${drug.price}</td>
                    <td>${drug.quantity}</td>
                    <td><a href="${tempLink}">Update</a></td>
                </tr>
            </c:forEach>
        </table>
        <a href="logout">logout</a>
    </div>
<jsp:include page="templates/footer.jsp"/>
