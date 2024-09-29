<%@ page import="java.util.List" %>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Posts Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #1a1a1b; /* Fondo oscuro */
            color: #d7dadc;
            margin: 0;
            padding: 0;
        }

        h1 {
            color: #d7dadc;
            text-align: center;
            margin-top: 20px;
        }

        .post-list {
            margin: 20px auto;
            max-width: 800px;
            padding: 20px;
            background-color: #282828; /* Fondo de la lista de posts */
            border-radius: 8px;
            box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.2);
        }

        .post {
            background-color: #1e1e1e; /* Fondo de cada post */
            border: 1px solid #343536;
            border-radius: 8px;
            padding: 15px;
            margin-bottom: 15px;
            transition: transform 0.2s;
        }

        .post:hover {
            transform: translateY(-5px);
        }

        .post h3 {
            color: #d7dadc;
            margin-bottom: 10px;
            font-size: 1.5em;
        }

        .post p {
            color: #818384;
            margin-bottom: 8px;
        }

        .post .meta {
            font-size: 0.9em;
            color: #a0a0a0;
        }

        .login-link {
            position: absolute;
            top: 10px;
            right: 10px;
            background-color: #0079d3; /* Color del botón de login */
            color: white;
            padding: 10px 15px;
            text-decoration: none;
            border-radius: 5px;
        }

        .login-link:hover {
            background-color: #1484d6;
        }
    </style>
</head>

<script>
    async function loadPosts() {
        try {
            const response = await fetch('/TestFinalOMeMato/load-posts');
            if (!response.ok) {
                throw new Error('Error al cargar los posts: ' + response.status);
            }
            const posts = await response.json();
            const postList = document.getElementById('postList');
            postList.innerHTML = ''; // Limpiar el mensaje de carga

            if (posts.length === 0) {
                postList.innerHTML = '<li>No hay posts disponibles.</li>';
            } else {
                // Usa un acumulador para agregar los posts
                let postItems = '';
                posts.forEach(function(aPost) {
                    postItems += '<li class="post">' +
                        '<h3>' + aPost.title + '</h3>' +
                        '<p>' + aPost.content + '</p>' +
                        '<p class="meta">Publicado el: ' + aPost.createdAt + '</p>' +
                        '</li>';
                });
                // Inserta todos los posts acumulados en el DOM de una sola vez
                postList.innerHTML = postItems;
            }
        } catch (error) {
            console.error(error);
            document.getElementById('postList').innerHTML = '<li>Error al cargar los posts.</li>';
        }
    }

    // Cargar posts al inicio
    document.addEventListener('DOMContentLoaded', loadPosts);
</script>

<body>

<%
    Integer userId = (Integer) session.getAttribute("user_id");
    if (userId == null) {
%>
<a href="login.jsp" class="login-link">Login</a>
<%
    }
%>

<h1>Recent Posts</h1>

<div class="post-list">
    <ul id="postList">
        <li>Cargando posts...</li>
    </ul>
</div>

</body>
</html>
