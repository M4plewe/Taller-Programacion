<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.maple.tallerprogramacion.ServerGeneralClassesToMakeStuffWork.Forum" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Threadadit - Forum Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f6f7f8;
            margin: 0;
            padding: 0;
        }

        .header {
            background-color: #ffffff;
            padding: 10px;
            border-bottom: 1px solid #eaeaea;
            display: flex;
            justify-content: space-between;
            align-items: center;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            z-index: 1000;
        }

        .header-logo {
            font-size: 24px;
            font-weight: bold;
            color: #ff4500;
            flex: 1;
        }

        .header-search-container {
            display: flex;
            justify-content: center;
            flex: 2;
        }

        .header-search input {
            padding: 8px;
            border-radius: 4px;
            border: 1px solid #ccc;
            font-size: 14px;
            width: 250px;
        }

        .header-user {
            display: flex;
            justify-content: flex-end;
            flex: 1;
            align-items: center;
        }

        .header-user a {
            font-size: 14px;
            color: #333;
            text-decoration: none;
            padding: 10px;
            background-color: #ff4500;
            color: white;
            border-radius: 5px;
        }

        .header-user a:hover {
            background-color: #e03e00;
        }

        .content {
            padding: 80px 20px 20px;
            background-color: #f6f7f8;
            min-height: 100vh;
            width: 90%;
            max-width: 1400px;
            margin: 0 auto;
        }

        .forum-stats {
            background-color: #f1f3f5;
            padding: 20px;
            border-bottom: 1px solid #eaeaea;
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 10px;
            position: relative;
            top: 10px;
        }

        .forum-info-container {
            display: flex;
            align-items: center;
            flex-grow: 1;
        }

        .forum-stats-image {
            width: 100px;
            height: 100px;
            object-fit: cover;
            border-radius: 50%;
            margin-right: 20px;
        }

        .forum-stats-info {
            flex-grow: 2;
        }

        .forum-stats-info h1 {
            margin: 0;
            font-size: 24px;
            font-weight: bold;
        }

        .forum-stats-info span {
            display: block;
            color: #777;
        }

        .forum-stats-info p {
            margin-top: 5px;
            color: #555;
        }

        .forum-stat-numbers {
            text-align: center;
            flex-grow: 1;
        }

        .forum-stat-numbers div {
            margin-bottom: 5px;
            color: #333;
            font-size: 14px;
        }

        .forum-buttons {
            display: flex;
            align-items: center;
        }

        .leave-btn, .more-btn {
            background-color: #4da6ff;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 20px;
            cursor: pointer;
            margin-left: 10px;
            font-size: 14px;
        }

        .leave-btn:hover, .more-btn:hover {
            background-color: #3994e6;
        }

        .more-btn {
            background-color: #eaeaea;
            color: black;
            padding: 8px 16px;
        }

        .post {
            background-color: #fff;
            border: 1px solid #eaeaea;
            margin-bottom: 20px;
            padding: 15px;
            border-radius: 8px;
            display: flex;
            justify-content: space-between;
        }

        .post img {
            width: 120px;
            height: 120px;
            object-fit: cover;
            border-radius: 8px;
            margin-right: 20px;
        }

        .post-content {
            flex-grow: 1;
        }

        .post-title {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 5px;
        }

        .post-meta {
            font-size: 14px;
            color: #777;
            margin-bottom: 10px;
        }

        .post-comments {
            font-size: 14px;
            color: #555;
        }

        .post-controls {
            display: flex;
            align-items: center;
        }

        .vote-buttons {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }

        .vote-button {
            background-color: #f0f0f0;
            border: none;
            padding: 5px;
            margin: 3px;
            cursor: pointer;
            font-size: 14px;
            border-radius: 5px;
        }

        .vote-button.upvote {
            color: orange;
        }

        .vote-button.downvote {
            color: gray;
        }

        .post-section {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
        }

        .post-section h3 {
            font-size: 18px;
            margin-bottom: 10px;
        }

        .post-section .filters {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }

        .post-section .filters button {
            padding: 10px;
            border-radius: 5px;
            border: none;
            background-color: #f0f0f0;
            cursor: pointer;
            font-size: 14px;
        }

        .no-posts {
            text-align: center;
            font-size: 16px;
            color: #555;
        }

        .new-post-btn {
            position: fixed;
            bottom: 20px;
            right: 20px;
            background-color: #ff4500;
            color: white;
            border: none;
            padding: 15px;
            border-radius: 50%;
            font-size: 24px;
            cursor: pointer;
        }

        .user-dropdown {
            display: none;
            position: absolute;
            right: 0;
            top: 80px;
            right: 30px;
            background-color: #ffffff;
            color: black;
            border-radius: 12px;
            width: 250px;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
            padding: 10px 0;
        }

        .user-dropdown a {
            display: flex;
            align-items: center;
            padding: 10px 15px;
            text-decoration: none;
            color: black;
            font-size: 14px;
            border-bottom: 1px solid #ccc;
            background-color: transparent;
        }

        .user-dropdown a img {
            margin-right: 10px;
            border-radius: 50%;
        }

        .user-dropdown a:hover {
            background-color: transparent;
            color: black;
        }

        .user-dropdown a:last-child {
            border-bottom: none;
        }

        .user-dropdown .icon {
            margin-right: 10px;
            font-size: 18px;
        }
    </style>
</head>
<body>

<div class="header">
    <div class="header-logo">
        <a href="/" style="text-decoration: none; color: #ff4500;">Threadadit</a>
    </div>
    <div class="header-search-container">
        <form action="/browser" method="get">
            <div class="header-search">
                <input type="text" id="searchQuery" name="q" placeholder="Ingrese un término de búsqueda">
            </div>
        </form>
    </div>
    <div class="header-user" style="margin-right: 5%;">
        <%
            String currentUsername = (String) session.getAttribute("currentUsername");
            String currentProfileImage = (String) session.getAttribute("currentProfileImage");

            if (currentUsername != null && currentProfileImage != null) {
        %>
        <div style="display: flex; align-items: center;">
            <%
                Forum forum = (Forum) request.getAttribute("forum");
            %>
            <a href="/uploadpost?forumName=<%= forum.getName() %>" style="font-size: 24px; margin-right: 10px; text-decoration: none; color: #ffffff;">📝</a>
            <img src="/resources/<%= currentProfileImage %>" alt="Profile Image"
                 style="width: 40px; height: 40px; border-radius: 50%; margin-right: 15px;">
        </div>
        <a href="javascript:void(0);" onclick="toggleDropdown()"
           style="font-size: 24px; cursor: pointer; margin-left: 10px;">
            &#9776;
        </a>
        <div class="user-dropdown" id="userDropdown">
            <a href="/user/<%= currentUsername %>">
                <div style="display: flex; align-items: center;">
                    <img src="/resources/<%= currentProfileImage %>" alt="Profile Image"
                         style="width: 20px; height: 20px; border-radius: 50%; margin-right: 5px;">
                    Ver perfil:
                </div>
                <div style="display: block; font-size: 12px; color: #666;"> u/<%= currentUsername %>
                </div>
            </a>
            <a href="/logout">
                <span class="icon">🚪</span> Cerrar sesión
            </a>
            <div style="border-top: 1px solid #444;"></div>
            <a href="/settings">
                <span class="icon">⚙️</span> Ajustes
            </a>
        </div>
        <%
        } else {
        %>
        <a href="/new_login">Login</a>
        <%
            }
        %>
    </div>
</div>

<div class="content">
    <div class="forum-stats">
        <%
            Forum forum = (Forum) request.getAttribute("forum");
            if (forum != null) {
        %>
        <div class="forum-info-container">
            <%--            Recordar que al pasar a la nueva base de datos implementar imagenes --%>
            <%--            <img src="<%= forum.getImageUrl() %>" alt="Forum Image" class="forum-stats-image">--%>
            <div class="forum-stats-info">
                <h1><%= forum.getName() %></h1>
                <span>Since: <%= forum.getCreatedAt() %></span>
                <p><%= forum.getDescription() %></p>
            </div>
            <div class="forum-stat-numbers">
                <div><%= forum.getPostCount() %> posts</div>
            </div>
        </div>
        <div class="forum-buttons">
            <button class="leave-btn">Leave</button>
            <button class="more-btn">More</button>
        </div>
        <%
        } else {
        %>
        <div class="no-forum">Forum information not available.</div>
        <%
            }
        %>
    </div>

    <div class="post-section">
        <div class="filters">
            <div>
                <button id="filterBtn" value="today">Today</button>
                <button id="filterBtn" value="week">Week</button>
                <button id="filterBtn" value="month">Month</button>
                <button id="filterBtn" value="all">All</button>
            </div>
        </div>
        <h3>Posts</h3>
        <div id="postList">
            <!-- Los posts se cargarán aquí -->
            <p>Cargando posts...</p>
        </div>
    </div>
</div>

<script>
    let offset = 0; // Empieza en 0
    const limit = 20; // Número de posts a cargar cada vez
    let currentFilter = 'all'; // Filtro por defecto
    let forumName = "<%= forum.getName() %>"; // Nombre del foro que quieras cargar

    async function loadPosts() {
        try {
            if (!forumName || limit === undefined || offset === undefined || !currentFilter) {
                throw new Error('Invalid parameters for fetching posts');
            }

            const response = await fetch("/load-posts-by-forum?forumName=" + encodeURIComponent(forumName) + "&limit=" + limit + "&offset=" + offset + "&timeFilter=" + currentFilter);
            if (!response.ok) {
                throw new Error('Error al cargar los posts: ' + response.status);
            }

            const posts = await response.json();
            const postList = document.getElementById('postList');

            if (offset === 0) {
                postList.innerHTML = ''; // Limpiar el contenido si es la primera carga
            }

            if (posts.length === 0) {
                window.removeEventListener('scroll', handleScroll); // No hay más posts para cargar
                return;
            }

            let postItems = '';
            posts.forEach(function (aPost) {
                postItems += '<div class="post">' +
                    '<img src="https://via.placeholder.com/120" alt="Post Image">' +
                    '<div class="post-content">' +
                    '<div class="post-title">' + aPost.title + '</div>' +
                    '<div class="post-meta">By <a href="/user/' + aPost.username + '">u/' + aPost.username + '</a> | in <a href="/forum/' + aPost.forumName + '">/' + aPost.forumName + '</a> | ' + new Date(aPost.createdAt).toLocaleDateString() + '</div>' +
                    '<div class="post-comments">' + aPost.comments + ' Comments</div>' +
                    '</div>' +
                    '<div class="post-controls">' +
                    '<div class="vote-buttons">' +
                    '<button class="vote-button upvote">▲ ' + aPost.upvotes + '</button>' +
                    '<button class="vote-button downvote">▼</button>' +
                    '</div>' +
                    '</div>' +
                    '</div>';
            });

            postList.innerHTML += postItems;
            offset += limit; // Actualiza el offset para la próxima carga
        } catch (error) {
            console.error(error);
            document.getElementById('postList').innerHTML = '<p>Error al cargar los posts.</p>';
        }
    }

    function handleScroll() {
        if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 100) {
            loadPosts(); // Cargar más posts al llegar al final de la página
        }
    }

    document.addEventListener('DOMContentLoaded', () => {
        loadPosts(); // Cargar posts al cargar la página
        window.addEventListener('scroll', handleScroll); // Agregar el evento de scroll

        // Manejo de filtros
        const filterButtons = document.querySelectorAll('.filters button');
        filterButtons.forEach(button => {
            button.addEventListener('click', () => {
                currentFilter = button.value; // Obtener el valor del botón
                offset = 0; // Reiniciar el offset
                loadPosts(); // Cargar posts con el filtro seleccionado
            });
        });
    });
</script>


<script>
    function toggleDropdown() {
        const dropdown = document.getElementById('userDropdown');
        dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
    }

    window.onclick = function (event) {
        if (!event.target.matches('.header-user a') && !event.target.closest('#userDropdown')) {
            const dropdown = document.getElementById('userDropdown');
            if (dropdown.style.display === 'block') {
                dropdown.style.display = 'none';
            }
        }
    };
</script>

</body>
</html>