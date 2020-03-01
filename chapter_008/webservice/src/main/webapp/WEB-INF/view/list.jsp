<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Users list</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

</head>
<body>
<div class="container">
    <div class="row-cols-1">

<%--navigation menu--%>
        <div class="col">
            <ul class="nav">
                <li class="nav-item">
                    <a class="nav-link active"
                       href="admin/add">Add user</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="admin/profile">Profile</a>
                </li>
                <li class="nav-item ">
                    <a class="nav-link" href="signout">Sign out</a>
                </li>
            </ul>
        </div>

<%--table users--%>
        <div class="col mt-4">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Photo</th>
                    <th scope="col">Role</th>
                    <th scope="col">Login</th>
                    <th scope="col">Email</th>

                </tr>
                </thead>
                <tbody>
                <c:forEach items="${usersDto}" var="userDto" varStatus="theCount">
                    <tr>
                        <th scope="row">${theCount.index + 1}</th>
                        <td><img style="height: 50px" src="data:image/jpeg;base64,${userDto.photo}"/></td>
                        <td>${userDto.role}</td>
                        <td>${userDto.login}</td>
                        <td>${userDto.email}</td>
                        <td>
                            <a href="admin/profile?id=${userDto.userId}">[profile]</a>
                            <a href="admin/edit?id=${userDto.userId}">[edit]</a>
                            <form action="admin" method="post">
                                <input type="hidden" name="id" value="${userDto.userId}" >
                                <input type="hidden" name="action" value="remove">
                                <input type="submit" value="remove">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>