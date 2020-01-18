<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <title>Add user</title>
</head>
<body>
<form method='post' action='list'>
    <b>Name:</b><br>
    <input type='text' name='name' size='40'/><br>
    <b>Login:</b><br>
    <input type='text' name='login' size='40'/><br>
    <b>Email:</b><br>
    <input type='email' name='email' size='40'/><br>
    <b>Date created:</b><br>
    <input type='date' name='created' size='40'/><br><br>
    <input type='submit' value='OK'/>
    <input type='hidden' name='action' value='add'/><br>
    <input type='hidden' name='id' value='0'/><br>
</form>
</body>
</html>
