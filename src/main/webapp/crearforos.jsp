<%@ page import="java.io.IOException" %>
<%@ page import="jakarta.servlet.ServletException" %>
<%@ page import="jakarta.servlet.http.HttpServlet" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<%@ page import="jakarta.servlet.http.HttpServletResponse" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Crear Forum</title>
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
            display: inline-block;
            text-align: left;
            margin-top: 20px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #fff;
        }
        label {
            display: block;
            margin-bottom: 10px;
        }
        input, textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        button {
            background-color: #4caf50;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

<%
    Integer userId = (Integer) session.getAttribute("user_id");
    if (userId == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String username = (String) session.getAttribute("username");
    String email = (String) session.getAttribute("email");
    String profileImage = (String) session.getAttribute("profileImage");
%>

<h1>Crear Forum</h1>
<form action="CrearForumServlet" method="post">
    <label for="name">Nombre del Forum:</label>
    <input type="text" id="name" name="name" required>
    <label for="description">Descripción:</label>
    <textarea id="description" name="description" rows="4" required></textarea>
    <button type="submit">Crear Forum</button>
</form>
</body>
</html>
