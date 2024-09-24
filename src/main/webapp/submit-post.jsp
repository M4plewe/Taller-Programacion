<%@ page import="java.io.IOException" %>
<%@ page import="jakarta.servlet.ServletException" %>
<%@ page import="jakarta.servlet.http.HttpServlet" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<%@ page import="jakarta.servlet.http.HttpServletResponse" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    session = request.getSession();
    String username = (String) session.getAttribute("username");
    String email = (String) session.getAttribute("email");
    String profileImage = (String) session.getAttribute("profileImage");

%>

<!DOCTYPE html>
<html>
<head>
    <title>Crear Post</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #e8f5e9;
            color: #333;
            text-align: center;
            margin: 0;
            padding: 0;
        }
        h1 {
            color: #004d00;
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
    // Verificar si el usuario está autenticado
    Integer userId = (Integer) session.getAttribute("user_id");
    if (userId == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Obtener parámetros del foro desde la URL
    String forumId = request.getParameter("forumId");
    if (forumId == null) {
        out.println("<p>Error: No se ha proporcionado un ID de foro.</p>");
        return;
    }

%>

<h1>Crear Post en el Foro</h1>

<!-- Formulario para crear un post -->
<form action="CrearPostServlet" method="post">
    <input type="hidden" name="forumId" value="<%= forumId %>">
    <input type="hidden" name="userId" value="<%= userId %>">

    <label for="title">Título del Post:</label>
    <input type="text" id="title" name="title" required>

    <label for="content">Contenido del Post:</label>
    <textarea id="content" name="content" rows="4" required></textarea>

    <button type="submit">Crear Post</button>
</form>

</body>
</html>
