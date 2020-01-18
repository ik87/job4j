<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <title>Form Update</title>
</head>
<body>

<form method='post' action='list'>
    <b>Name:</b><br>
    <input type='text' size='40' name='name' value='${user.name}'/><br>
    <b>Login:</b><br>
    <input type='text' size='40' name='login' value='${user.login}'/><br>
    <b>Email:</b><br>
    <input type='email' size='40' name='email' value='${user.email}'/><br><br>
    <input type='submit' value='save'/>
    <input type='hidden' name='action' value='update'/><br>
    <input type='hidden' name='id' value='${user.id}'/><br>
    <input type='hidden' name='created' value='${user.createDate}'/><br>
</form>
</body>
</html>
