<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <title>Update user</title>
</head>
<body>

<form method='post' action='update_image' enctype="multipart/form-data">
    <b>Photo:</b><br>
    <div>
        <img src="get_image?name=${user.photoId}" height="100px"/>
    </div>
    <input type='hidden' name='photoId' value='${user.photoId}'>
    <input type='file' name='file'><br>
    <input type="submit" value="Загузить"/><br>
</form>

<form method='post' action='list'>
    <b>Name:</b><br>
    <input type='text' size='40' name='name' value='${user.name}'/><br>
    <b>Login:</b><br>
    <input type='text' size='40' name='login' value='${user.login}'/><br>
    <b>Email:</b><br>
    <input type='email' size='40' name='email' value='${user.email}'/><br><br>
    <input type='hidden' name='action' value='update'/>
    <input type='hidden' name='id' value='${user.id}'/>
    <input type="hidden" name='photoId' value='${user.photoId}'>
    <input type='hidden' name='created' value='${user.createDate}'/>
    <input type="submit" value="OK"/>
</form>
</body>
</html>
