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
        <div class="col">
            <ul class="nav">
                <li class="nav-item">
                    <a class="nav-link active"
                       href="">Add user</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="admin/profile?id=${adminDto.userId}">Profile</a>
                </li>
                <li class="nav-item ">
                    <a class="nav-link" href="signout">Sign out</a>
                </li>
            </ul>
        </div>

        <div class="col mt-4">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Photo</th>
                    <th scope="col">Login</th>
                    <th scope="col">Email</th>

                </tr>
                </thead>
                <tbody>
                <c:forEach items="${usersDto}" var="userDto" varStatus="theCount">
                    <tr>
                        <th scope="row">${theCount.index + 1}</th>
                        <td><img style="height: 50px" src="data:image/jpeg;base64,${userDto.photo}"/></td>
                        <td>${userDto.login}</td>
                        <td>${userDto.email}</td>
                    </tr>
                </c:forEach>
                <%-- <tr>
                     <th scope="row">2</th>
                     <td>img</td>
                     <td>Mark</td>
                     <td>mark@gmail.com</td>
                 </tr>
                 <tr>
                     <th scope="row">3</th>
                     <td>img</td>
                     <td>John</td>
                     <td>john@gmail.com</td>
                 </tr>--%>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>