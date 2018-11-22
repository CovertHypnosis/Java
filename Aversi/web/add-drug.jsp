<jsp:include page="templates/header.jsp"/>
    <div class="container">
        <form action="edit.do" method="post">
            <table class="table table-bordered table-striped">
                <tr>
                    <td>Name: </td>
                    <td><input type="text" name="new_name"></td>
                </tr>
                <tr>
                    <td>Description</td>
                    <td><input type="text" name="desc"></td>
                </tr>
                <tr>
                    <td>price</td>
                    <td><input type="text" name="price"></td>
                </tr>
                <tr>
                    <td>qt: </td>
                    <td><input type="number" min="0" max="100" name="qt"></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" name="submit" value="register"></td>
                </tr>
            </table>
        </form>
    </div>
<jsp:include page="templates/footer.jsp"/>
