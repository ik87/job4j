<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang='en'>
<head>

    <meta charset='UTF-8'>
    <link href="css/style.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript"  src="js/script.js"/>

    <title>List</title>
    <script>

    </script>

</head>
<body>

<table border="1" cellpadding="3">
    <caption>Users table</caption>
    <tr align="center">
        <th width="100px">Photo</th>
        <th>Name</th>
        <th>Login</th>
        <th>Email</th>
        <th>Data of creation</th>
        <th colspan="2">Operation</th>
    </tr>

    <c:forEach var="user" items="${users}">
        <tr align="center">
            <td><img src="get_image?name=${user.photoId}" height="100px"/></td>
            <td>${user.login}</td>
            <td>${user.email}</td>
            <td>${user.createDate}</td>
            <td>
                <form action="edit" method="get">
                    <input type="hidden" name="id" value="${user.id}"/>
                    <input type="submit" value="edit"/>
                </form>
            </td>
            <td>
                <form onsubmit="del('${user.photoId}')" action="" method="post">
                    <input type="hidden" name="action" value="delete"/>
                    <input type="hidden" name="id" value="${user.id}"/>
                    <input type="submit"  value="del"/>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<form action="create" method="get">
    <input type="submit" value="Add new user"/>
</form>
</body>

</body>
</html>
