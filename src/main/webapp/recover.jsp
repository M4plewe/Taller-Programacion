<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Password Recovery</title>
</head>
<body>
<h2>Password Recovery</h2>
<form action="sendPasswordRecoveryEmail" method="post">
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required>
    <button type="submit">Recover Password</button>
</form>
</body>
</html>