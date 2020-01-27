<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang='en'>
<head>

    <meta charset='UTF-8'>
    <link href="css/style.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript"  src="js/script.js"/>

    <title>Add user</title>
    <script>

    </script>

</head>
<body>


<form action="sent_image" onchange="send(this)" enctype="multipart/form-data">
    <b>Photo:</b><br>
    <div class="image" id="image">
    </div>
    <input class="browser" type="file" name="file">
</form>
<button onclick='clean()'>clean</button>

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
    <input id='fileName' type='hidden' name='photoId' value=''/><br>
    <input type='hidden' name='id' value='0'/><br>
</form>
</body>
</html>
