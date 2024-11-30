<%@ page import="org.maple.tallerprogramacion.ServerGeneralClassesToMakeStuffWork.Forum" %>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Fund Me - Main Page</title>
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
      padding: 100px 20px 20px; /* Adjusted padding to leave space for the header */
      background-color: #f6f7f8;
      min-height: 100vh;
      max-width: 800px;
      margin: 0 auto; /* Center the content */
    }

    .post-form {
      background-color: #fff;
      border: 1px solid #eaeaea;
      padding: 20px;
      border-radius: 8px;
      margin-bottom: 20px;
    }

    .select-community {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 20px;
    }

    .select-community select {
      padding: 10px;
      border-radius: 5px;
      border: 1px solid #ccc;
      font-size: 14px;
    }

    .select-community .drafts-link {
      color: #0079d3;
      text-decoration: none;
      font-size: 14px;
    }

    .select-community .drafts-link:hover {
      text-decoration: underline;
    }

    .post-form label {
      display: block;
      margin-bottom: 5px;
      font-size: 14px;
      color: #333;
    }

    .post-form input,
    .post-form textarea {
      width: 100%;
      padding: 10px;
      margin-bottom: 20px;
      border-radius: 5px;
      border: 1px solid #ccc;
      font-size: 14px;
    }

    .post-form button {
      padding: 10px 20px;
      background-color: #ff4500;
      color: white;
      border: none;
      border-radius: 5px;
      font-size: 16px;
      cursor: pointer;
    }

    .post-form button:hover {
      background-color: #e03e00;
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
    <a href="/" style="text-decoration: none; color: #ff4500;">Fund Me</a>
  </div>
  <div class="header-search-container">
    <div class="header-search">
      <input type="text" placeholder="Find community">
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

<div class="content">
  <form class="post-form" action="CrearPostServlet" method="post">
    <%
      Forum forum = (Forum) request.getAttribute("forum");
    %>
    <div class="current-forum">
      Right now, posting in <%= forum.getName() %>
    </div>

    <input type="hidden" name="currentUserId" value="<%= session.getAttribute("currentUserId") %>">
    <input type="hidden" name="currentUsername" value="<%= session.getAttribute("currentUsername") %>">
    <input type="hidden" name="forumName" value="<%= forum.getName() %>">

    <label for="title">Title</label>
    <input type="text" id="title" name="title" placeholder="Title" maxlength="300" required>

    <label for="content">Content</label>
    <textarea id="content" name="content" placeholder="Write your post..." required></textarea>

    <button type="submit" id="submit-btn">Post</button>
  </form>
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