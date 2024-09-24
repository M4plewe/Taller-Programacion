<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <script>
        function toggleGender() {
            var genderInput = document.getElementById("gender");
            if (genderInput.value === "male") {
                genderInput.value = "female";
                document.getElementById("genderButton").innerText = "Female";
            } else {
                genderInput.value = "male";
                document.getElementById("genderButton").innerText = "Male";
            }
        }
    </script>
</head>
<body>
<% String error = request.getParameter("error"); %>
<% if (error != null) { %>
<p style="color: red;"><%= error %></p>
<% } %>
<form action="register-servlet" method="post">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required><br>
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required><br>
    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br>
    <input type="hidden" id="gender" name="gender" value="male">
    <button type="button" id="genderButton" onclick="toggleGender()">Male</button><br>
    <input type="submit" value="Register">
</form>
</body>
</html>