<%@ page import="org.maple.tallerprogramacion.ServerGeneralClassesToMakeStuffWork.User" %>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Threadadit - User Page</title>
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
            margin: 80px auto;
        }

        .profile-section {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            text-align: center;
            margin-bottom: 20px;
        }

        .profile-section h2 {
            font-size: 24px;
        }

        .profile-section .karma, .profile-section .post-info {
            font-size: 14px;
            margin-top: 10px;
        }

        .profile-section select {
            margin-top: 10px;
            padding: 10px;
            width: 100%;
            border-radius: 5px;
            border: 1px solid #ddd;
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
<div class="content">
    <div class="profile-section">
        <%
            User user = (User) request.getAttribute("user");
            if (user != null) {
        %>
        <img src="/resources/<%= user.getAvatar() %>" alt="Profile Image" style="width: 100px; height: 100px; border-radius: 50%;">

        <h2>u/<%= user.getUsername() %></h2>
        <div class="karma">Karma: 0</div>
        <div class="post-info">
            Email: <%= user.getEmail() %> <br>
            Bio: <%= user.getBio() %> <br>
            Joined: <%= user.getRegistrationDate() %>
        </div>
        <select>
            <option value="action">Choose an action</option>
        </select>
        <%
        } else {
        %>
        <div class="no-user">User information not available.</div>
        <%
            }
        %>
    </div>

    <div class="post-section">
        <div class="filters">
            <div>
                <button>Today</button>
                <button>Week</button>
                <button>Month</button>
                <button>All</button>
            </div>
            <div>
                <button>Hot</button>
                <button>New</button>
                <button>Top</button>
            </div>
        </div>
        <div class="no-posts">
            No posts with this filter were found, Be the first to add one!
        </div>
    </div>
</div>

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