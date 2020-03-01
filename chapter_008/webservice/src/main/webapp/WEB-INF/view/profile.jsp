<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.job4j.webservice.service.Utils" %>
<%@ page import="ru.job4j.webservice.models.User" %>
<%@ page import="java.util.Arrays" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User profile</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <style>
        /*                        div {
                                    border: 1px solid cadetblue !important;
                                }*/
        .container-sm {
            max-width: 850px;
        }
    </style>
</head>
<body>

<div class="container-sm">
    <!--row 1-->
    <div class="row  m-3">
        <!--navigation-->
        <div class="col-12">
            <ul class="nav">
                <c:if test="${sessionScope.user.role.role eq 'admin'}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/${sessionScope.user.role.role}">Users list</a>
                    </li>
                </c:if>
                <li class="nav-item">
                    <a class="nav-link active"
                       href="${pageContext.request.contextPath}/${sessionScope.user.role.role}/edit?id=${userDto.userId}">Edit
                        profile</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Remove profile</a>
                </li>
                <li class="nav-item ">
                    <a class="nav-link" href="${pageContext.request.contextPath}/signout">Sign out</a>
                </li>
            </ul>
            <hr>
        </div>
        <!--photo image-->
        <div class="col-sm-5  col-md-4">
            <!--row 2-->
            <!--img-->
            <img class="rounded mx-auto d-block" style="width: 100%" src="data:image/jpeg;base64,${userDto.photo}"/>
            <!--upload img-->
            <form method='post' action='${pageContext.request.contextPath}/${sessionScope.user.role.role}/upload'
                  enctype="multipart/form-data">
                <div class="form-group">
                    <input type='hidden' name='id' value='${userDto.userId}'>
                    <input class="form-control-file mt-1" type='file' name='file'>
                    <input type="submit" value="Upload"/>
                </div>
            </form>
            <!--delete img-->
            <form method="post" action="${pageContext.request.contextPath}/${sessionScope.user.role.role}">
                <div class="form-group">
                    <input type='hidden' name='id' value='${userDto.userId}'>
                    <input type='hidden' name='action' value='deleteImg'>
                    <input type="submit" value="delete image"/>
                </div>
            </form>
        </div>

        <!--user profile-->
        <div class="col-sm-5 col-md-8">
            <ul class="list-group list-group-flush my-2">
                <li class="list-group-item">
                    <div class="row">
                        <div class="col-sm-12 col-md-2 font-weight-bold">Login:</div>
                        <div class="col">${userDto.login}</div>
                    </div>
                </li>
                <li class="list-group-item">
                    <div class="row">
                        <div class="col-sm-12 col-md-2 font-weight-bold">Email:</div>
                        <div class="col">${userDto.email}</div>
                    </div>
                </li>
                <li class="list-group-item">
                    <div class="row">
                        <div class="col-sm-12 col-md-2 font-weight-bold">Role:</div>
                        <div class="col">${userDto.role}</div>
                    </div>
                </li>
                <li class="list-group-item">
                    <div class="row">
                        <div class="col-sm-12 col-md-2 font-weight-bold">Created:</div>
                        <div class="col">${userDto.created}</div>
                    </div>
                </li>
            </ul>
        </div>

    </div>
</div>
</body>
</html>