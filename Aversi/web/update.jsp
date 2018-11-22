<jsp:include page="templates/header.jsp"/>
    <div class="container">
        <form action="edit.do" method="post">
            <table class="table table-bordered table-striped">
                <input type="hidden" name="original_name" value="${drugUpdate.name}">
                <tr>
                    <td>Name: </td>
                    <td><input type="text" name="new_name" value="${drugUpdate.name}"></td>
                </tr>
                <tr>
                    <td>Description: </td>
                    <td><input type="text" name="desc" value="${drugUpdate.description}"></td>
                </tr>
                <tr>
                    <td>Price: </td>
                    <td><input type="text" name="price" value="${drugUpdate.price}"></td>
                </tr>
                <tr>
                    <td>Qt: </td>
                    <td><input type="number" name="qt" min="0" max="100" value="${drugUpdate.quantity}"></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" name="submit" value="update"></td>
                </tr>
            </table>

            <a href="edit.do">back</a>
        </form>
    </div>
<jsp:include page="templates/footer.jsp"/>
