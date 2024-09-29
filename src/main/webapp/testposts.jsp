<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Posts Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
        }
        h1 {
            color: #4caf50;
            text-align: center;
        }
        .post-list {
            margin: 20px auto;
            max-width: 600px;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
        .post {
            border-bottom: 1px solid #ddd;
            padding: 10px 0;
        }
        .post:last-child {
            border-bottom: none;
        }
        .post h3 {
            color: #333;
        }
        .post p {
            color: #666;
        }
        .login-link {
            position: absolute;
            top: 10px;
            right: 10px;
            background-color: #4caf50;
            color: white;
            padding: 10px 15px;
            text-decoration: none;
            border-radius: 5px;
        }
        .login-link:hover {
            background-color: #45a049;
        }
    </style>
</head>
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

<script>
    async function loadPosts() {
        try {
            const response = await fetch('/TestFinalOMeMato/posts');
            if (!response.ok) {
                throw new Error('Error al cargar los posts: ' + response.status);
            }
            const posts = await response.json();
            //console.log(posts);
            /*
            [{"id":1,"userId":2,"forumId":1,"title":"Tu título aquí","content":"El contenido de tu post aquí","createdAt":"2024-09-14 15:58:33"}]
             */
            const postList = document.getElementById('postList');
            postList.innerHTML = ''; // Limpiar el mensaje de carga

            if (posts.length === 0) {
                postList.innerHTML = '<li>No hay posts disponibles.</li>';
            } else {
                posts.forEach(aPost => {
                    //postList.innerHTML = '<li>${aPost["id"]}</li>';
                    postList.innerHTML = aPost.id + ", "+ aPost.title +", "+aPost.content;


                });
            }
        } catch (error) {
            console.error(error);
            document.getElementById('postList').innerHTML = '<li>Error al cargar los posts.</li>';
        }
    }

    // Cargar posts al inicio
    document.addEventListener('DOMContentLoaded', loadPosts);
</script>

</body>
</html>
