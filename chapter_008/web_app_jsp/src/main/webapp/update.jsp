<%@ page import="ru.job4j.web_app_jsp.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <title>Form Update</title>
</head>
<body>
<% User user = (User) request.getAttribute("user"); %>
<form method='post' action='list'>
    <b>Name:</b><br>
    <input type='text' size='40' name='name' value='<%=user.getName()%>'/><br>
    <b>Login:</b><br>
    <input type='text' size='40' name='login' value='<%=user.getLogin()%>'/><br>
    <b>Email:</b><br>
    <input type='email' size='40' name='email' value='<%=user.getEmail()%>'/><br><br>
    <input type='submit' value='save'/>
    <input type='hidden' name='action' value='update'/><br>
    <input type='hidden' name='id' value='<%=user.getId()%>'/><br>
    <input type='hidden' name='created' value='<%=user.getCreateDate()%>'/><br>
</form>
</body>
</html>
