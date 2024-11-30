<%@ page import="org.maple.tallerprogramacion.ServerGeneralClassesToMakeStuffWork.Post" %>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Fund Me - Main Page</title>

    <meta name="description" content="FundMe is a community-driven platform where users can share and discuss various topics.">
    <meta name="keywords" content="FundMe, forums, discussions, community">
    <meta name="author" content="Maple">
    <link rel="canonical" href="http://maplehugs.ddns.net/">

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
            cursor: pointer; /* Changes cursor to pointer to indicate it's clickable */
        }

        .post-link {
            display: block;
            text-decoration: none;
            color: inherit; /* Ensures it keeps the original text color */
        }

        .post-link a {
            text-decoration: underline; /* Only underlines internal links like usernames or forums */
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
        .container {
            max-width: 800px;
            margin: 150px auto 50px; /* Adjusted margin-top to match the height of the header */
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .post-title {
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 15px;
        }

        .post-meta {
            font-size: 14px;
            color: #666;
            margin-bottom: 20px;
        }

        .post-content {
            font-size: 16px;
            line-height: 1.5;
            margin-bottom: 30px;
        }

        .post-controls {
            display: flex;
            justify-content: space-between;
        }

        .vote-buttons button {
            background-color: #f0f0f0;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
        }

        .vote-buttons button:hover {
            background-color: #ddd;
        }

        .back-link {
            text-decoration: none;
            color: #0079d3;
            font-size: 14px;
        }

        .back-link:hover {
            text-decoration: underline;
        }

        .comment-box {
            width: 100%;
            min-height: 100px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            margin-bottom: 10px;
            resize: vertical;
        }

        .comment-submit {
            background: #0079D3;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
            margin-bottom: 20px;
        }

        .comment-submit:hover {
            background: #005fa3;
        }

        .comment {
            border-left: 2px solid #ccc;
            margin: 10px 0;
            padding-left: 10px;
        }

        .comment-header {
            display: flex;
            align-items: center;
            margin-bottom: 4px;
            font-size: 12px;
            color: #787C7E;
        }

        .username {
            color: #1A1A1B;
            font-weight: bold;
            margin-right: 8px;
        }

        .comment-content {
            color: #1A1A1B;
            line-height: 1.5;
            margin-bottom: 8px;
        }

        .comment-actions {
            display: flex;
            gap: 12px;
            color: #878A8C;
            font-size: 12px;
        }

        .action-button {
            background: none;
            border: none;
            color: #878A8C;
            cursor: pointer;
            display: flex;
            align-items: center;
            gap: 4px;
        }

        .action-button:hover {
            color: #1A1A1B;
        }

        .vote-count {
            margin: 0 4px;
        }

        .nested {
            margin-left: 20px;
        }



        .comment-box {
            width: 100%;
            min-height: 100px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            margin-bottom: 10px;
            resize: vertical;
        }

        .comment-submit {
            background: #0079D3;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
            margin-bottom: 20px;
        }

        .comment-submit:hover {
            background: #005fa3;
        }

        .comment {
            border-left: 2px solid #ccc;
            margin: 10px 0;
            padding-left: 10px;
        }

        .comment-header {
            display: flex;
            align-items: center;
            margin-bottom: 4px;
            font-size: 12px;
            color: #787C7E;
        }

        .username {
            color: #1A1A1B;
            font-weight: bold;
            margin-right: 8px;
        }

        .comment-content {
            color: #1A1A1B;
            line-height: 1.5;
            margin-bottom: 8px;
        }

        .comment-actions {
            display: flex;
            gap: 12px;
            color: #878A8C;
            font-size: 12px;
        }

        .action-button {
            background: none;
            border: none;
            color: #878A8C;
            cursor: pointer;
            display: flex;
            align-items: center;
            gap: 4px;
        }

        .action-button:hover {
            color: #1A1A1B;
        }

        .vote-count {
            margin: 0 4px;
        }

        .nested {
            margin-left: 20px;
        }

    </style>
</head>
<body>

<div class="header">
    <div class="header-logo">
        <a href="/" style="text-decoration: none; color: #ff4500;">Fund Me</a>
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
    <ul id="followedForums">
        <!-- Followed forums will be listed here -->
    </ul>

    <%
        }
    %>


    <h2>Top Threads</h2>
    <ul id="topThreads">

    </ul>
</div>

<div class="container">
    <%-- Display post details --%>
    <%
        Post post = (Post) request.getAttribute("post");
        if (post != null) {
    %>
    <div class="post-title"><%= post.getTitle() %></div>
    <div class="post-meta">
        By <a href="/user/<%= post.getUsername() %>">u/<%= post.getUsername() %></a> |
        in <a href="/forum/<%= post.getForumName() %>">/<%= post.getForumName() %></a> |
        Posted on <%= post.getCreatedAt() %>
    </div>
    <div class="post-content">
        <%= post.getContent() %>
    </div>
    <% } else { %>
    <div class="post-content">No post found for the given ID.</div>
    <% } %>

        <% if (session.getAttribute("currentUserId") != null) { %>
        <form action="/AddCommentServlet" method="post" style="display: inline;">
            <textarea name="content" class="comment-box" placeholder="What are your thoughts?"></textarea>
            <input type="hidden" name="user_id" value="<%= session.getAttribute("currentUserId") %>">
            <input type="hidden" name="post_id" value="<%= post.getId() %>">
            <input type="hidden" name="parent_id" value="0"> <!-- Adjust as needed -->
            <input type="hidden" name="has_parent" value="false"> <!-- Adjust as needed -->
            <button type="submit" class="comment-submit">Comment</button>
        </form>
        <% } %>

    <div class="comments-section" id="comments">
        <!-- Comments will be inserted here -->
    </div>
</div>

<script>
    let postId = '<%= post.getId() %>'; // Replace with the current post ID

    async function loadComments() {
        try {
            const response = await fetch("/load-comments?post_id=" + encodeURIComponent(postId));
            if (!response.ok) {
                throw new Error('Error loading comments');
            }
            const comments = await response.json();
            const commentsSection = document.getElementById('comments');
            commentsSection.innerHTML = ''; // Clear existing comments

            for (let i = 0; i < comments.length; i++) {
                commentsSection.innerHTML += createCommentHTML(comments[i], 0);
            }
        } catch (error) {
            console.error(error);
        }
    }

    function createCommentHTML(comment, level) {
        return `
            <div class="comment ` + (level > 0 ? 'nested' : '') + `" data-id="` + comment.id + `">
                <div class="comment-header">
                    <span class="username">` + comment.username + `</span>
                    <span class="timestamp">` + formatTime(comment.createdAt) + `</span>
                </div>
                <div class="comment-content">` + comment.content + `</div>
                <div class="comment-actions">
                    <button class="action-button vote-up">
                        ▲
                        <span class="vote-count">` + comment.votes + `</span>
                        ▼
                    </button>

                </div>
                ` + (comment.replies ? comment.replies.map(reply => createCommentHTML(reply, level + 1)).join('') : '') + `
            </div>
        `;
    }

    function formatTime(timestamp) {
        const date = new Date(timestamp);
        return date.toLocaleString();
    }

    document.addEventListener('DOMContentLoaded', () => {
        loadComments(); // Load comments on initial page load
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

<script>
    async function loadFollowedForums() {
        try {
            const response = await fetch('/load-following-forums');
            if (!response.ok) {
                throw new Error('Error loading followed forums');
            }
            const followedForums = await response.json();
            const followedForumsList = document.getElementById('followedForums');

            followedForumsList.innerHTML = ''; // Clear the list before adding new items

            followedForums.forEach(function (forum) {
                const listItem = document.createElement('li');
                listItem.innerHTML = '<a href="/forum/' + forum + '">' + forum + '</a>';
                followedForumsList.appendChild(listItem);
            });
        } catch (error) {
            console.error(error);
        }
    }

    document.addEventListener('DOMContentLoaded', () => {
        loadFollowedForums(); // Load followed forums on initial page load
    });
</script>

</body>
</html>