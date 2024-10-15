<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Threadadit - Main Page</title>
    <style>

        /* Forum Styles */
        .forum {
            background-color: #fff;
            border: 1px solid #eaeaea;
            margin-bottom: 20px;
            padding: 15px;
            border-radius: 8px;
            display: flex;
            justify-content: space-between;
            cursor: pointer; /* Changes cursor to pointer to indicate it's clickable */
        }

        .forum img {
            width: 120px; /* Adjust the width here if needed */
            height: 120px; /* Adjust the height here if needed */
            object-fit: cover;
            border-radius: 8px;
            margin-right: 20px;
        }

        .forum-content {
            flex-grow: 1;
        }

        .forum-name {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 5px;
        }

        .forum-meta {
            font-size: 14px;
            color: #777;
            margin-bottom: 10px;
        }

        .forum-controls {
            display: flex;
            align-items: center;
        }


        /* User Styles */
        .user {
            background-color: #fff;
            border: 1px solid #eaeaea;
            margin-bottom: 20px;
            padding: 15px;
            border-radius: 8px;
            display: flex;
            justify-content: flex-start;
            align-items: center;
            cursor: pointer; /* Changes cursor to pointer to indicate it's clickable */
        }

        .user-profile {
            display: flex;
            flex-direction: row;
            align-items: center;
            margin-right: 20px;
        }

        .user-profile img {
            width: 80px;
            height: 80px;
            object-fit: cover;
            border-radius: 50%;
        }

        .user-content {
            flex-grow: 1;
        }

        .user-username {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 5px;
        }

        .user-username a {
            text-decoration: none;
            color: #0073e6;
        }

        .user-meta {
            font-size: 14px;
            color: #777;
            margin-bottom: 10px;
        }

        .user-controls {
            margin-left: auto;
        }

        .message-button {
            background-color: #0073e6;
            color: white;
            padding: 8px 16px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .message-button:hover {
            background-color: #005bb5;
        }

        /* Post Styles */
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

        /* Vote Button Styles */
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


        /* General Styles */
        body {
            font-family: Arial, sans-serif;
            background-color: #f6f7f8;
            margin: 0;
            padding: 0;
        }

        /* Header Styles */
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
            align-items: center;
            margin-right: 5%;
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

        /* Content Styles */
        .content {
            padding: 100px 20px 20px; /* Adjusted padding to leave space for the header */
            background-color: #f6f7f8;
            min-height: 100vh;
            max-width: 800px;
            margin: 0 auto; /* Center the content */
        }

        /* Post Styles */
        .post {
            background-color: #fff;
            border: 1px solid #eaeaea;
            margin-bottom: 20px;
            padding: 15px;
            border-radius: 8px;
            display: flex;
            justify-content: space-between;
            align-items: flex-start; /* Align items to the start for a better layout */
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


        /* User Dropdown Styles */
        .user-dropdown {
            display: none;
            position: absolute;
            right: 30px;
            top: 80px;
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

        /* Post Section Styles */
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

        /* Forum Styles */
        .forum {
            background-color: #ffffff;
            border: 1px solid #eaeaea;
            margin-bottom: 20px;
            padding: 15px;
            border-radius: 8px;
            display: flex;
            flex-direction: column; /* Stack children vertically */
            max-width: 100%; /* Ensure full width */
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* Optional shadow for depth */
        }


        .forum-description {
            font-size: 14px; /* Consistent font size */
            color: #777;
            margin-bottom: 10px; /* Space between description and content */
        }

        /* User Styles */
        .user {
            background-color: #f9f9f9; /* Light background for user section */
            border: 1px solid #eaeaea;
            border-radius: 8px;
            padding: 15px;
            display: flex;
            align-items: center; /* Center the content vertically */
            margin-bottom: 20px; /* Space between users */
            max-width: 100%; /* Full width */
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* Optional shadow */
        }

        .user img {
            width: 50px; /* Smaller size for user image */
            height: 50px;
            object-fit: cover;
            border-radius: 50%; /* Circular profile image */
            margin-right: 15px; /* Space between image and text */
        }

        #resultList {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            margin: 0 auto;
            padding: 20px;
            max-width: 800px; /* Adjust as needed */
        }

        #resultList .user, #resultList .forum, #resultList .post {
            width: 100%; /* Ensure full width */
            max-width: 800px; /* Adjust as needed */
            min-height: 150px; /* Set a minimum height */
            box-sizing: border-box; /* Include padding and border in the element's total width and height */
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
            <input type="text" id="searchQuery" placeholder="Ingrese un término de búsqueda">
        </div>
        <script>
            // Function to get query parameter by name
            function getQueryParam(name) {
                const urlParams = new URLSearchParams(window.location.search);
                return urlParams.get(name);
            }

            // Set the search input value with the query parameter
            document.addEventListener('DOMContentLoaded', () => {
                const searchQuery = getQueryParam('q');
                if (searchQuery) {
                    document.getElementById('searchQuery').value = searchQuery;
                }
            });
        </script>
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
    <div>
        <div class="post-section">
            <div class="filters">
                <div>
                    <button onclick="setTimeFilter('today')">Today</button>
                    <button onclick="setTimeFilter('week')">Week</button>
                    <button onclick="setTimeFilter('month')">Month</button>
                    <button onclick="setTimeFilter('all')">All</button>
                </div>
                <div>
                    <button onclick="setTypeFilter('forums')">Forums</button>
                    <button onclick="setTypeFilter('posts')">Posts</button>
                    <button onclick="setTypeFilter('users')">Users</button>
                </div>
            </div>
        </div>
    </div>

    <ul id="resultList"></ul>
</div>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Buscar Posts</title>

    <script>
        let offset = 0; // Start at 0
        const limit = 20; // Number of items to load each time
        let currentTypeFilter = 'posts'; // Default type filter
        let currentTimeFilter = 'all'; // Default time filter

        async function loadSearchResults() {
            try {
                const searchQuery = document.getElementById('searchQuery').value;

                // Reset offset and remove previous results if starting a new search
                if (offset === 0) {
                    document.getElementById('resultList').innerHTML = ''; // Clear previous results
                }

                // Updated fetch URL using concatenation
                const response = await fetch("/search?type=" + currentTypeFilter + "&q=" + encodeURIComponent(searchQuery) + "&time=" + currentTimeFilter + "&limit=" + limit + "&offset=" + offset);

                if (!response.ok) {
                    throw new Error('Error loading search results: ' + response.status);
                }

                const data = await response.json();
                const resultList = document.getElementById('resultList');

                if (data.results.length === 0) {
                    // Stop loading more if no results returned
                    window.removeEventListener('scroll', handleScroll);
                    return;
                }

                // Accumulate result items
                let resultItems = '';
                data.results.forEach(function (item) {
                    if (currentTypeFilter === 'posts') {
                        // Post template
                        resultItems += '<div class="post" onclick="redirectToPost(' + item.id + ')">' +
                            '<img src="https://via.placeholder.com/120" alt="Post Image">' +
                            '<div class="post-content">' +
                            '<div class="post-title">' + item.title + '</div>' +
                            '<div class="post-meta">By <a href="/user/' + item.username + '" onclick="event.stopPropagation()">u/' + item.username + '</a> | in <a href="/forum/' + item.forumName + '" onclick="event.stopPropagation()">/' + item.forumName + '</a> | ' + item.comments + ' Comments | ' + new Date(item.createdAt).toLocaleDateString() + '</div>' +
                            '<div class="post-comments">' + item.comments + ' Comments</div>' +
                            '</div>' +
                            '<div class="post-controls">' +
                            '<div class="vote-buttons">' +
                            '<button class="vote-button upvote" onclick="event.stopPropagation()">▲ ' + item.upvotes + '</button>' +
                            '<button class="vote-button downvote" onclick="event.stopPropagation()">▼</button>' +
                            '</div>' +
                            '</div>' +
                            '</div>';
                    } else if (currentTypeFilter === 'forums') {
                        // Forum template (similar to posts layout)
                        resultItems += '<div class="post" onclick="redirectToForum(\'' + item.name + '\')">' +
                            '<img src="https://via.placeholder.com/120" alt="Forum Image">' +

                            // Forum content container similar to post-content
                            '<div class="post-content">' +
                            // Forum title (similar to post title)
                            '<div class="post-title">' + item.name + '</div>' +

                            // Forum meta information (created by user and number of posts)
                            '<div class="post-meta">Created by <a href="/user/' + item.creatorUsername + '" onclick="event.stopPropagation()">u/' + item.creatorUsername + '</a> | ' + item.postCount + ' Posts</div>' +

                            // Forum controls (join button)
                            '<div class="post-controls">' +
                            '<div class="join-button-container">' +
                            '<button class="join-button" onclick="event.stopPropagation()">Join</button>' +
                            '</div>' +
                            '</div>' +
                            '</div>' +
                            '</div>';
                    } else if (currentTypeFilter === 'users') {
                        // User template (similar to posts layout)
                        resultItems += '<div class="post" onclick="redirectToUser(\'' + item.username + '\')">' +
                            '<img src="/resources/' + item.avatar + '" alt="User Profile Image">' +

                            // User content container similar to post-content
                            '<div class="post-content">' +
                            // User username (similar to post title)
                            '<div class="post-title">' +
                            '<a href="/user/' + item.username + '">' + item.username + '</a>' +
                            '</div>' +

                            // User meta information (post count and registration date)
                            '<div class="post-meta">' +
                            item.postCount + ' Posts | Joined ' + item.registrationDate +
                            '</div>' +

                            // User controls (message button)
                            '<div class="post-controls">' +
                            '<button class="message-button" onclick="event.stopPropagation()">Message</button>' +
                            '</div>' +
                            '</div>' +
                            '</div>';
                    }
                });

                // Insert accumulated results into the DOM
                resultList.innerHTML += resultItems;
                offset += limit; // Update offset for next load
            } catch (error) {
                console.error(error);
                document.getElementById('resultList').innerHTML = '<p>Error loading search results.</p>';
            }
        }

        // Function to set the type filter and reload results
        function setTypeFilter(filter) {
            currentTypeFilter = filter;
            offset = 0; // Reset offset for new search
            loadSearchResults();
        }

        // Function to set the time filter and reload results
        function setTimeFilter(filter) {
            currentTimeFilter = filter;
            offset = 0; // Reset offset for new search
            loadSearchResults();
        }

        // Scroll handler to load more results when at the bottom of the page
        function handleScroll() {
            if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 100) {
                loadSearchResults(); // Load more results when near the bottom
            }
        }

        // Redirection function
        function redirectToPost(id) {
            window.location.href = 'http://maplehugs.ddns.net/post/' + id;
        }

        function redirectToUser(username) {
            window.location.href = 'http://maplehugs.ddns.net/user/' + username;
        }

        function redirectToForum(name) {
            window.location.href = 'http://maplehugs.ddns.net/forum/' + name;
        }

        // Load initial set of results on page load
        document.addEventListener('DOMContentLoaded', () => {
            loadSearchResults(); // Load search results on initial load
            window.addEventListener('scroll', handleScroll); // Add scroll listener
        });
    </script>

    <script>
        function toggleDropdown() {
            const dropdown = document.getElementById('userDropdown');
            dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
        }

        // Close dropdown if clicked outside
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
