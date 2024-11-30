<%@ page import="org.maple.tallerprogramacion.ServerGeneralClassesToMakeStuffWork.Forum" %>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fund Me - User Settings</title>
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
            padding: 100px 20px 20px;
            background-color: #f6f7f8;
            min-height: 100vh;
            max-width: 800px;
            margin: 0 auto;
        }

        .settings-container {
            background-color: #fff;
            border: 1px solid #eaeaea;
            padding: 20px;
            border-radius: 8px;
        }

        .settings-category {
            margin-bottom: 30px;
        }

        .settings-category h2 {
            font-size: 18px;
            margin-bottom: 10px;
            color: #333;
        }

        .settings-category label {
            display: block;
            margin-bottom: 5px;
            font-size: 14px;
            color: #333;
        }

        .settings-category input,
        .settings-category select {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border-radius: 5px;
            border: 1px solid #ccc;
            font-size: 14px;
        }

        .settings-category button {
            padding: 10px 20px;
            background-color: #ff4500;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
        }

        .settings-category button:hover {
            background-color: #e03e00;
        }

        .profile-image {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-bottom: 20px;
        }

        .profile-image img.centered-image {
            width: 150px; /* Adjust the size as needed */
            height: 150px; /* Adjust the size as needed */
            border-radius: 50%;
            margin-bottom: 10px;
        }

        .profile-image img {
            margin-right: 10px;
            width: 50px;
            height: 50px;
            border-radius: 50%;
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


    </style>
</head>
<body>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Settings</title>
</head>
<body>

<div class="header">
    <div class="header-logo">
        <a href="/" style="text-decoration: none; color: #ff4500;">Fund Me</a>
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

    <h1>Update Settings</h1>

    <%
        String errorMessage = request.getParameter("error");
        String successMessage = request.getParameter("message");

        if (errorMessage != null) {
    %>
    <div style="color: red;"><%= errorMessage %>
    </div>
    <%
        }

        if (successMessage != null) {
    %>
    <div style="color: green;"><%= successMessage %>
    </div>
    <%
        }
    %>

    <div class="settings-container">
        <!-- Change Name -->
        <div class="settings-category">
            <h2>Change Name</h2>
            <form action="/update-name-servlet" method="post">
                <label for="newName">New Name</label>
                <input type="text" id="newName" placeholder="<%= session.getAttribute("currentUsername") %>"
                       name="newName" required>
                <button type="submit">Update Name</button>
            </form>
        </div>

        <div class="settings-category">
            <h2>Change Password</h2>
            <form id="passwordForm" action="UpdatePasswordServlet" method="post" onsubmit="return validatePasswords()">


                <label for="newPassword">Current Password (Required for change)</label>
                <input type="password" id="currentPassword" placeholder="your actual password" name="currentPassword" required>

                <label for="newPassword">New Password</label>
                <input type="password" id="newPassword" placeholder="your new password" name="newPassword" required>

                <label for="confirmPassword">Confirm New Password</label>
                <input type="password" id="confirmPassword" placeholder="confirm your new password"
                       name="confirmPassword" required>

                <!-- Show Password Checkbox -->
                <div style="display: flex; align-items: center; margin-bottom: 10px;">
                    <label for="showPasswords" style="font-size: 12px; color: gray;">Show Passwords</label>
                </div>
                <div style="display: flex; ; margin-right: auto;">
                    <input type="checkbox" id="showPasswords" onclick="togglePasswordVisibility()"
                           style="margin-right: 5px;">
                </div>
                <button type="submit" style="margin-top: 15px;">Update Password</button>
            </form>
            <div id="passwordError" style="color: red; display: none;">Passwords do not match</div>
        </div>


        <!-- Change Gender -->
        <div class="settings-category">
            <h2>Change Gender</h2>
            <form action="UpdateGenderServlet" method="post">
                <label for="gender">Gender (Current gender: <%= session.getAttribute("currentGender") %>)</label>
                <select id="gender" name="gender" required>
                    <option value="male">Male</option>
                    <option value="female">Female</option>
                </select>
                <button type="submit">Update Gender</button>
            </form>
        </div>

        <!-- Change Profile Image -->
        <div class="settings-category">
            <h2>Change Profile Image</h2>
            <form action="UpdateProfileImageServlet" method="post" enctype="multipart/form-data">
                <div class="profile-image">
                    <img src="/resources/<%= currentProfileImage %>" alt="Current Profile Image" class="centered-image">
                    <input type="file" id="profileImage" name="profileImage" accept="image/*" required>
                </div>
                <button type="submit">Update Profile Image</button>
            </form>
        </div>

        <!-- Change Email -->
        <div class="settings-category">
            <h2>Change Email</h2>
            <form action="UpdateEmailServlet" method="post">
                <label for="newEmail">New Email</label>
                <input type="email" id="newEmail" name="newEmail" required>
                <button type="submit">Update Email</button>
            </form>
        </div>
    </div>
</div>

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

<script>
    function validatePasswords() {
        const newPassword = document.getElementById('newPassword').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        const passwordError = document.getElementById('passwordError');

        if (newPassword.length <= 8) {
            passwordError.textContent = 'New password must be longer than 8 characters';
            passwordError.style.display = 'block';
            return false;
        }

        if (newPassword !== confirmPassword) {
            passwordError.textContent = 'Passwords do not match';
            passwordError.style.display = 'block';
            return false;
        }

        passwordError.style.display = 'none';
        return true;
    }

    function togglePasswordVisibility() {
        const passwordFields = document.querySelectorAll('#newPassword, #confirmPassword');
        passwordFields.forEach(field => {
            field.type = field.type === 'password' ? 'text' : 'password';
        });
    }
</script>

</body>
</html>
