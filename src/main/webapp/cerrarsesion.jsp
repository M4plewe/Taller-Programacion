<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="refresh" content="3;url=index.jsp"> <!-- Redirige después de 3 segundos -->
    <title>Cerrar Sesión</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            color: #333;
            text-align: center;
            padding: 50px;
        }
        h1 {
            color: #4CAF50;
        }
    </style>
</head>
<body>
<%
    // Invalidar la sesión
    session.invalidate();
%>
<h1>Sesión cerrada con éxito</h1>
<p>Serás redirigido a la página de inicio en 3 segundos...</p>
</body>
</html>
