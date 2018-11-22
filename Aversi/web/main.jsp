<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="templates/header.jsp"/>
    <form action="main.do" method="get">
        <label for="search_word">Search: </label>
        <input type="text" name="drug_search_name" id="search_word">
    </form>
    <br>

    <div class="container">
        <form action="buy" method="post" id="buy">
            <table class="table table-bordered table-striped">
                <tr>
                    <th>name</th>
                    <th>description</th>
                    <th>price</th>
                    <th>quantity</th>
                    <th>qty</th>
                </tr>
                <c:forEach items="${drugs}" var="drug">
                    <tr>
                        <td>${drug.name}</td>
                        <td>${drug.description}</td>
                        <td>${drug.price}</td>
                        <td>${drug.quantity}</td>
                        <td><input type="number" name="${drug.name}_quantity" min="0" max="${drug.quantity}"></td>
                    </tr>
                </c:forEach>
            </table>
            <input type="submit" name="submit" value="Submit" form="buy"/>
        </form>
        <br>
        <a href="logout">logout</a>
    </div>
<jsp:include page="templates/footer.jsp"/>
