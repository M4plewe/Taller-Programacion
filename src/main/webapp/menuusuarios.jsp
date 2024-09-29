<%@ page import="java.io.File" %>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Menú de Usuario</title>
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
        img {
            border-radius: 50%; /* Imagen de perfil redonda */
            border: 2px solid #004d00; /* Borde verde oscuro */
        }
        ul {
            list-style-type: none;
            padding: 0;
        }
        li {
            margin: 10px 0;
        }
        a {
            text-decoration: none;
            color: #4caf50; /* Verde medio para los enlaces */
            font-size: 18px;
            padding: 10px 20px;
            border: 1px solid #4caf50;
            border-radius: 4px;
        }
        a:hover {
            background-color: #4caf50;
            color: white;
        }
        .search-form {
            margin: 20px 0;
        }
        .search-form input[type="text"] {
            padding: 10px;
            width: 200px;
            border: 1px solid #4caf50;
            border-radius: 4px;
        }
        .search-form input[type="submit"] {
            padding: 10px 20px;
            border: 1px solid #4caf50;
            border-radius: 4px;
            background-color: #4caf50;
            color: white;
            cursor: pointer;
        }
        .search-form input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

<%
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    String username = (String) session.getAttribute("username");
    String email = (String) session.getAttribute("email");
    String profileImage = (String) session.getAttribute("profileImage");
%>

<h1>Bienvenido, <%= username %>!</h1>
<img src="resources/<%= profileImage %>" alt="Imagen de Perfil" width="150" height="150"/>
<p>Email: <%= email %></p>

<!-- Opciones del menú de usuario -->
<ul>
    <li><a href="crearforos.jsp">Crear Forum</a></li>
    <li><a href="editarperfil.jsp">Editar Perfil</a></li>
    <li><a href="cerrarsesion.jsp">Cerrar Sesión</a></li>
    <li><a href="buscarforos.jsp">Buscar Forums</a></li> <!-- Nuevo botón para buscar Forums -->
    <li><a href="testposts.jsp">Test Posts</a></li>
</ul>

<!-- Mostrar posts -->
<h2>Posts Recientes</h2>
<ul>

    <li>No hay posts disponibles.</li>

</ul>
</body>
</html>