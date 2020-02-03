<%@ page import="ru.job4j.strong_mvc.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <title>Add user</title>
</head>
<body>

<form method='post' action='send_image' enctype="multipart/form-data">
    <b>Photo:</b><br>
    <div>
        <img src="get_image?name=${requestScope.photoId}" height="100px"/>
    </div>
    <input type='hidden' name='photoId' value='${requestScope.photoId}'>
    <input type='file' name='file'><br>
    <input type="submit" value="Загузить"/><br>
</form>
<br>
<form method='post' action='list'>
    <b>Name:</b><br>
    <input type='text' name='name' size='40'/><br>
    <b>Login:</b><br>
    <input type='text' name='login' size='40'/><br>
    <b>Email:</b><br>
    <input type='email' name='email' size='40'/><br>
    <b>Date created:</b><br>
    <input type='date' name='created' size='40'/><br>
    <input type='submit' value='OK'/>
    <input type='hidden' name='action' value='add'/><br>
    <input type="hidden" name='photoId' value='${requestScope.photoId}'>
    <input type='hidden' name='id' value='0'/><br>
</form>
</body>
</html>
