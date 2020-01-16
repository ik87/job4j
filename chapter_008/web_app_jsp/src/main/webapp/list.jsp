<%@ page import="ru.job4j.web_app_jsp.model.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <title>Title</title>
</head>
<body>

<%

    List<User> result = (List<User>)request.getAttribute("result");
    StringBuilder sb = new StringBuilder();
    for (User user : result) {
        sb.append(
                String.format("<tr align='center'><td>%s</td><td>%s</td><td>%s</td><td>%s</td>\n" +
                                "        <td>\n" +
                                "            <form action='edit' method='get'>\n" +
                                "                <input type='hidden' name='id' value='" + user.getId() + "'/>\n" +
                                "                <input  type='submit' value='edit'/>\n" +
                                "            </form>\n" +
                                "        </td>\n" +
                                "        <td>\n" +
                                "            <form action='' method='post'>\n" +
                                "                <input type='hidden' name='action' value='delete'/>\n" +
                                "                <input type='hidden' name='id' value='" + user.getId() + "'/>\n" +
                                "                <input type='submit' value='del'/>\n" +
                                "            </form>\n" +
                                "        </td>\n" +
                                "    </tr>",
                        user.getName(),
                        user.getLogin(),
                        user.getEmail(),
                        user.getCreateDate()));
    }

%>

<table border='1' cellpadding='3'>
    <caption>Users table</caption>
    <tr align='center'>
        <th>Name</th>
        <th>Login</th>
        <th>Email</th>
        <th>Data of creation</th>
        <th colspan='2'>Operation</th>
    </tr>
    <%=sb.toString()%>
</table>
<form action='create' method='get'>
    <input type='submit' value='Add new user'/>
</form></body>

</body>
</html>
