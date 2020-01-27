<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang='en'>
<head>

    <meta charset='UTF-8'>
    <link href="css/style.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript"  src="js/script.js"/>

    <title>Update user</title>
    <script>

    </script>

</head>
<body onload="loadFile('${user.photoId}')">


<form action="sent_image" onchange="send(this)" enctype="multipart/form-data">
    <b>Photo:</b><br>
    <div class="image" id="image">
    </div>
    <input class="browser" type="file" name="file">
</form>
<button onclick='clean()'>clean</button>

<form method='post' action='list'>
    <b>Name:</b><br>
    <input type='text' size='40' name='name' value='${user.name}'/><br>
    <b>Login:</b><br>
    <input type='text' size='40' name='login' value='${user.login}'/><br>
    <b>Email:</b><br>
    <input type='email' size='40' name='email' value='${user.email}'/><br><br>
    <input type='hidden' name='action' value='update'/><br>
    <input type='hidden' name='id' value='${user.id}'/><br>
    <input id='fileName' type='hidden' name='photoId' value=''/><br>
    <input type='hidden' name='created' value='${user.createDate}'/><br>
</form>
</body>
</html>
