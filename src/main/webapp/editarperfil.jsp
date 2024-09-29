<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<%@ page import="jakarta.servlet.http.HttpServletResponse" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Perfil</title>
</head>
<body>
<h1>Editar Perfil</h1>

<%
    session = request.getSession();
    String username = (String) session.getAttribute("username");
    String email = (String) session.getAttribute("email");
    String profileImage = (String) session.getAttribute("profileImage");
    String error = request.getParameter("error");
    Boolean isVerified = (session != null) ? (Boolean) session.getAttribute("verified") : false;
%>

<form action="update-profile-servlet" method="post" enctype="multipart/form-data">
    <input type="hidden" name="action" value="update">
    <label for="username">Nuevo Nombre de Usuario:</label>
    <input type="text" id="username" name="username" value="<%= username %>" required><br>

    <label for="email">Nuevo Correo Electrónico:</label>
    <input type="email" id="email" name="email" value="<%= email %>" required><br>

    <label for="profileImage">Nueva Imagen de Perfil:</label>
    <input type="file" id="profileImage" name="profileImage"><br>

    <button type="submit">Actualizar Perfil</button>
</form>

<form action="update-profile-servlet" method="post">
    <input type="hidden" name="action" value="delete">
    <button type="submit" onclick="return confirm('¿Estás seguro de que quieres eliminar tu perfil?');">Eliminar Perfil</button>
</form>

<p><a href="menuusuarios.jsp">Volver al inicio</a></p>
</body>
</html>
