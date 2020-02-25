<%@ page import="ru.job4j.webservice.service.Utils" %>
<%@ page import="ru.job4j.webservice.models.User" %>
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
                <li class="nav-item">
                    <a class="nav-link active" href="${pageContext.request.contextPath}/${user.role.role}/edit">Edit profile</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Remove profile</a>
                </li>
                <li class="nav-item ">
                    <a class="nav-link" href="signout">Sign out</a>
                </li>
            </ul>
            <hr>
        </div>
        <!--photo image-->
        <div class="col-sm-5  col-md-4">
            <!--row 2-->
            <!--img-->

            <!--upload img-->
            <form method='post' action='${pageContext.request.contextPath}/upload' enctype="multipart/form-data">
                <div class="form-group">
                    <div style="height: 200px">
<%--                        <img src="get_image?name=${user.photo}" height="200px"/>--%>
                    </div>
<%--                    <input type='hidden' name='photoid' value='${user.photo}'>--%>
                    <input class="form-control-file mt-1" type='file' name='file'>
                    <input type="submit" value="Upload"/>
                </div>
            </form>
            <form>

            </form>
        </div>

        <!--user profile-->
        <div class="col-sm-5 col-md-8">
            <ul class="list-group list-group-flush my-2">
                <li class="list-group-item">
                    <div class="row">
                        <div class="col-sm-12 col-md-2 font-weight-bold">Login:</div>
                        <div class="col">${user.login}</div>
                    </div>
                </li>
                <li class="list-group-item">
                    <div class="row">
                        <div class="col-sm-12 col-md-2 font-weight-bold">Email:</div>
                        <div class="col">${user.email}</div>
                    </div>
                </li>
                <li class="list-group-item">
                    <div class="row">
                        <div class="col-sm-12 col-md-2 font-weight-bold">Role:</div>
                        <div class="col">${user.role.role}</div>
                    </div>
                </li>
                <li class="list-group-item">
                    <div class="row">
                        <div class="col-sm-12 col-md-2 font-weight-bold">Created:</div>
                        <div class="col">
                            <%=
                            Utils.millisecondToStringDate(
                                    ((User) session.getAttribute("user")).getCreated())
                            %>
                        </div>
                    </div>
                </li>
            </ul>
        </div>

    </div>
</div>
</body>
</html>