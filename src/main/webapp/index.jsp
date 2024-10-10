<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Threadadit - Main Page</title>
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

        .sidebar {
            width: 250px;
            background-color: #ffffff;
            padding: 15px;
            box-shadow: 2px 0px 5px rgba(0, 0, 0, 0.1);
            height: 100vh;
            position: fixed;
            top: 100px; /* Adjusted to be below the header */
            left: 0;
            overflow-y: auto;
        }

        .sidebar h2 {
            font-size: 18px;
            margin-bottom: 10px;
        }

        .sidebar ul {
            list-style: none;
            padding: 0;
        }

        .sidebar ul li {
            padding: 10px;
            border-bottom: 1px solid #eaeaea;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .sidebar ul li span {
            font-size: 14px;
            color: #333;
        }

        .content {
            margin-left: 270px; /* Adjusted so the content does not overlap the sidebar */
            padding: 100px 20px 20px; /* Adjusted padding to leave space for the header */
            background-color: #f6f7f8;
            min-height: 100vh;
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

        .post-content {
            flex-grow: 1;
        }

        .post-title {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 10px;
        }

        .post-meta {
            font-size: 12px;
            color: #777;
            margin-bottom: 5px;
        }

        .post-controls {
            text-align: center;
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

        .post-comments {
            font-size: 12px;
            color: #0079d3;
            cursor: pointer;
        }

        .post img {
            max-width: 120px;
            margin-right: 15px;
        }

        .new-post-btn {
            position: fixed;
            bottom: 20px;
            right: 20px;
            background-color: #ffffff;
            color: white;
            border: none;
            padding: 15px;
            border-radius: 50%;
            font-size: 24px;
            cursor: pointer;
        }

        /* Updated Dropdown Menu */
        .user-dropdown {
            display: none;
            position: absolute;
            right: 0;
            top: 80px;
            right: 30px;
            background-color: #ffffff; /* Changed to white */
            color: black; /* Changed text color to black for better contrast */
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
            color: black; /* Changed text color to black */
            font-size: 14px;
            border-bottom: 1px solid #ccc; /* Changed border color to light gray */
            background-color: transparent; /* Ensure background is transparent */
        }

        .user-dropdown a img {
            margin-right: 10px;
            border-radius: 50%;
        }

        .user-dropdown a:hover {
            background-color: transparent; /* Make hover background color transparent */
            color: black; /* Ensure text color remains black on hover */
        }

        .user-dropdown a:last-child {
            border-bottom: none;
        }

        /* Icons for each option */
        .user-dropdown .icon {
            margin-right: 10px;
            font-size: 18px;
        }



        /* Updated vote buttons */
        .user-dropdown .vote-button {
            background-color: transparent; /* Make buttons invisible */
            color: white; /* Make text white */
            border: none; /* Remove border */
            padding: 5px;
            margin: 3px;
            cursor: pointer;
            font-size: 14px;
            border-radius: 5px;
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

    </style>
</head>
<body>

<div class="header">
    <div class="header-logo">
        <a href="/" style="text-decoration: none; color: #ff4500;">Threadadit</a>
    </div>
    <div class="header-search-container">
        <div class="header-search">
            <form action="/browser" method="get">
                <div class="header-search">
                    <input type="text" id="searchQuery" name="q" placeholder="Ingrese un término de búsqueda">
                </div>
            </form>
        </div>
    </div>
    <div class="header-user" style="margin-right: 5%;">
        <%
            String currentUsername = (String) session.getAttribute("currentUsername");
            String currentProfileImage = (String) session.getAttribute("currentProfileImage");

            if (currentUsername != null && currentProfileImage != null) {
        %>
        <div style="display: flex; align-items: center;">
            <img src="/resources/<%= currentProfileImage %>" alt="Profile Image"
                 style="width: 40px; height: 40px; border-radius: 50%; margin-right: 15px;">
        </div>
        <a href="javascript:void(0);" onclick="toggleDropdown()" style="font-size: 24px; cursor: pointer; margin-left: 10px;">
            &#9776;
        </a>
        <div class="user-dropdown" id="userDropdown">
            <a href="/user/<%= currentUsername %>">
                <div style="display: flex; align-items: center;">
                    <img src="/resources/<%= currentProfileImage %>" alt="Profile Image" style="width: 20px; height: 20px; border-radius: 50%; margin-right: 5px;">
                    Ver perfil:
                </div>
                <div style="display: block; font-size: 12px; color: #666;"> u/<%= currentUsername %></div>
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

<div class="sidebar">
    <%
        if (currentUsername != null) {
    %>
    <h2>Create Forum</h2>
    <ul>
        <li><a href="new_createforum.jsp">Create a new forum</a></li>
    </ul>

    <h2>Followed Forums</h2>
    <ul>
        <!-- Followed forums will be listed here -->
    </ul>

    <%
        }
    %>


    <h2>Top Threads</h2>
    <ul id="topThreads">

    </ul>
</div>

<div class="content">
    <div>
        <div class="post-section">
            <div class="filters">
                <div>
                    <button onclick="setFilter('today')">Today</button>
                    <button onclick="setFilter('week')">Week</button>
                    <button onclick="setFilter('month')">Month</button>
                    <button onclick="setFilter('all')">All</button>
                </div>
            </div>
        </div>
    </div>
    <div id="postList">
        <!-- Posts will load here -->
        <p>Cargando posts...</p>
    </div>
</div>

<script>
    let offset = 0; // Start at 0
    const limit = 20; // Number of posts to load each time
    let currentFilter = 'all'; // Default filter

    async function loadPosts() {
        try {
            const response = await fetch("/load-posts?limit=" + limit + "&offset=" + offset + "&timeFilter=" + currentFilter);
            if (!response.ok) {
                throw new Error('Error al cargar los posts: ' + response.status);
            }
            const posts = await response.json();
            const postList = document.getElementById('postList');

            // Clear loading message if it's the first load
            if (offset === 0) {
                postList.innerHTML = ''; // Clear loading message
            }

            if (posts.length === 0) {
                // If no posts returned, we can stop loading more
                window.removeEventListener('scroll', handleScroll);
                return; // No more posts to load
            }

            // Accumulate post items
            let postItems = '';
            posts.forEach(function (aPost) {
                postItems += '<div class="post">' +
                    '<img src="https://via.placeholder.com/120" alt="Post Image">' +
                    '<div class="post-content">' +
                    '<div class="post-title">' + aPost.title + '</div>' +
                    '<div class="post-meta">By <a href="/user/' + aPost.username + '">u/' + aPost.username + '</a> | in <a href="/forum/' + aPost.forumName + '">/' + aPost.forumName + '</a> | ' + aPost.comments + ' Comments | ' + new Date(aPost.createdAt).toLocaleDateString() + '</div>' +
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

            // Insert all accumulated posts into the DOM at once
            postList.innerHTML += postItems;
            offset += limit; // Update the offset for the next load
        } catch (error) {
            console.error(error);
            document.getElementById('postList').innerHTML = '<p>Error al cargar los posts.</p>';
        }
    }

    // Function to set the current filter and reload posts
    function setFilter(filter) {
        currentFilter = filter; // Update current filter
        offset = 0; // Reset offset
        loadPosts(); // Reload posts with the selected filter
    }

    // Scroll handler to load more posts when at the bottom of the page
    function handleScroll() {
        if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 100) {
            loadPosts(); // Load more posts when reaching near the bottom
        }
    }

    // Load initial set of posts
    document.addEventListener('DOMContentLoaded', () => {
        loadPosts(); // Load posts on initial page load
        window.addEventListener('scroll', handleScroll); // Add scroll event listener
    });
</script>


<script>
    async function loadThreads() {
        try {
            const response = await fetch('/load-top-threads');
            if (!response.ok) {
                throw new Error('Error al cargar los threads');
            }
            const topThreads = await response.json();
            const topThreadsList = document.getElementById('topThreads');
            topThreadsList.innerHTML = ''; // Limpiar antes de añadir

            // Insertar los threads en el formato adecuado
            topThreads.forEach(function (forum) {
                const listItem = document.createElement('li');
                listItem.innerHTML = '<a href="/forum/' + forum.name + '">' + forum.name + '</a> <span>' + forum.postCount + '</span>';
                topThreadsList.appendChild(listItem);
            });
        } catch (error) {
            console.error(error);
        }
    }

    document.addEventListener('DOMContentLoaded', () => {
        loadThreads();
    });
</script>

<script>
    function toggleDropdown() {
        const dropdown = document.getElementById('userDropdown');
        dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
    }

    // Close dropdown if clicked outside
    window.onclick = function(event) {
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
