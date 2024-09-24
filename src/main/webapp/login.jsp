<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Iniciar Sesión</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #e8f5e9; /* Fondo verde claro */
            color: #333;
            text-align: center;
            margin: 0;
            padding: 0;
        }
        h1 {
            color: #004d00; /* Verde muy oscuro para el título */
        }
        form {
            background-color: #ffffff; /* Fondo blanco para el formulario */
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Sombra suave */
            padding: 20px;
            max-width: 400px;
            margin: 50px auto;
        }
        label {
            display: block;
            margin: 10px 0 5px;
            font-size: 16px;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 5px 0 20px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #4caf50; /* Verde medio para el botón */
            color: white;
            border: none;
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
            border-radius: 4px;
        }
        input[type="submit"]:hover {
            background-color: #45a049; /* Verde más oscuro al pasar el ratón */
        }
        .link {
            display: block;
            margin: 10px 0;
            color: #4caf50;
            text-decoration: none;
        }
        .link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<h1>Iniciar Sesión</h1>
<form action="login-servlet" method="post">
    <label for="username">Nombre de Usuario o Correo Electrónico:</label>
    <input type="text" id="username" name="username" required><br>
    <label for="password">Contraseña:</label>
    <input type="password" id="password" name="password" required><br>
    <input type="submit" value="Iniciar Sesión">
</form>
<a href="register.jsp" class="link">Registrarse</a>
<a href="recover.jsp" class="link">¿Olvidaste tu contraseña?</a>
</body>
</html>