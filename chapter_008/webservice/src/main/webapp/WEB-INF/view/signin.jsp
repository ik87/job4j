<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">


    <!-- Custom styles for this template -->
    <link href="css/signin.css" rel="stylesheet">

    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }
    </style>

    <title>service entrance</title>
</head>
<body class="text-center">
<c:if test="${error != null}">
    <div class='fixed-top alert alert-danger ' role='alert'>
        <c:out value="${error}"/>
    </div>
</c:if>
<form class="form-signin" action="" method="post">

    <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
    <label for="inputLogin" class="sr-only">Login</label>
    <input class="form-control" type="text" id="inputLogin" placeholder="login" name="login" required autofocus>
    <label for="inputPassword" class="sr-only">Password</label>
    <input class="form-control" type="password" id="inputPassword" placeholder="password" name="password" required>
    <input class="btn btn-lg btn-primary btn-block" type="submit" value="Sign in">
    <input type="hidden" name="action" value="signin">
    <a class="btn btn-lg btn-secondary btn-block" href="signup">Sign up</a>
</form>
</body>
</html>
