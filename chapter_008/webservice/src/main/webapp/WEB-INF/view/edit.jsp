<%@ page import="ru.job4j.webservice.service.Utils" %>
<%@ page import="ru.job4j.webservice.models.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit user</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <style>
/*                                div {
                                    border: 1px solid cadetblue !important;
                                }*/
        .container-sm {
            max-width: 450px;
        }
    </style>
</head>
<body>
<div class="container-sm">
    <form method="post" action="${pageContext.request.contextPath}/${userDto.role}">
        <div class="form-row">
            <div class="form-group col-md-12">
                <label for="inputLogin" >Login</label>
                <input type="text"
                       value="${userDto.login}"
                       class="form-control" id="inputLogin"  name="login">
            </div>
            <div class="form-group col-md-12">
                <label for="inputEmail4">Email</label>
                <input type="email"
                       value="${userDto.email}"
                       class="form-control" id="inputEmail4" name="email">
            </div>
            <div class="form-group col-md-12">
                <label for="inputPassword4">Password</label>
                <input type="password"
                       value="${userDto.password}"
                       class="form-control" id="inputPassword4" name="password">
            </div>
        </div>
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="${userDto.userId}">
        <a href="${pageContext.request.contextPath}" class="btn btn-secondary">Cancel</a>
        <button type="submit" class="btn btn-primary">Save</button>
    </form>
</div>
</body>
</html>