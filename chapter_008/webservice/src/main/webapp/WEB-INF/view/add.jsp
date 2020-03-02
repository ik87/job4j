<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add user</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <style>
        .container-sm {
            max-width: 450px;
        }
    </style>
</head>
<body>
<div class="container-sm">
    <form method="post" action="${pageContext.request.contextPath}/signup">
        <div class="form-row">
            <div class="form-group col-md-12">
                <label for="inputLogin" >Login</label>
                <input type="text" class="form-control" id="inputLogin"  name="login">
            </div>
            <div class="form-group col-md-12">
                <label for="inputEmail4">Email</label>
                <input type="email" class="form-control" id="inputEmail4" name="email">
            </div>
            <c:if test="${user.role.role eq 'admin'}">
                <div class="form-group col-md-4">
                    <label for="inputRoleId">Role</label>
                    <select name="role_id" id="inputRoleId" class="form-control">
                        <option value="2" selected>User</option>
                        <option value="1">Admin</option>
                    </select>
                </div>
            </c:if>
            <div class="form-group col-md-12">
                <label for="inputPassword4">Password</label>
                <input type="password" class="form-control" id="inputPassword4" name="password">
            </div>
        </div>
        <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Cancel</a>
        <button type="submit" class="btn btn-primary">OK</button>
    </form>
</div>
</body>
</html>