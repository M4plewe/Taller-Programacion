<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Threadadit | Register</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f6f7f8;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #eef1f5;
        }

        .login-container {
            display: flex;
            justify-content: space-between;
            width: 800px;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            padding: 40px;
        }

        .logo-section {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        .logo-section img {
            width: 120px;
        }

        .logo-section h1 {
            font-size: 36px;
            margin: 20px 0;
            color: #ff4500;
        }

        .logo-section p {
            font-size: 16px;
            color: #666;
        }

        .login-form {
            width: 300px;
            display: flex;
            flex-direction: column;
        }

        .login-form h2 {
            font-size: 24px;
            margin-bottom: 20px;
            color: #333;
        }

        .login-form label {
            font-size: 14px;
            margin-bottom: 5px;
            color: #333;
        }

        .login-form input {
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 5px;
            border: 1px solid #ccc;
            font-size: 14px;
        }

        .login-form button {
            padding: 10px;
            background-color: #ff4500;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .login-form button:hover {
            background-color: #e03e00;
        }

        .login-form .forgot-signup {
            display: flex;
            justify-content: space-between;
            margin-top: 10px;
            font-size: 14px;
        }

        .forgot-signup a {
            color: #0079d3;
            text-decoration: none;
        }

        .forgot-signup a:hover {
            text-decoration: underline;
        }

        .error-message {
            color: red;
            margin-bottom: 20px;
        }

        .loading-spinner {
            display: none;
            border: 4px solid #f3f3f3;
            border-radius: 50%;
            border-top: 4px solid #3498db;
            width: 20px;
            height: 20px;
            animation: spin 2s linear infinite;
            margin-left: 10px;
        }

        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
    </style>
    <script>
        function togglePasswordVisibility() {
            var passwordInput = document.getElementById("password");
            if (passwordInput.type === "password") {
                passwordInput.type = "text";
            } else {
                passwordInput.type = "password";
            }
        }

        function showLoadingSpinner() {
            var button = document.querySelector(".login-form button");
            button.innerHTML = 'Registering <div class="loading-spinner" id="loading-spinner"></div>';
            document.getElementById("loading-spinner").style.display = "block";
        }

        function validateForm() {
            var password = document.getElementById("password").value;
            if (password.length < 8) {
                document.getElementById("error-message").innerText = "Password must be at least 8 characters long.";
                return false;
            }
            return true;
        }

        window.onload = function() {
            const urlParams = new URLSearchParams(window.location.search);
            const errorMessage = urlParams.get('error');
            if (errorMessage) {
                document.getElementById("error-message").innerText = errorMessage;
            }
        }
    </script>
</head>
<body>

<div class="login-container">
    <div class="logo-section">
        <img src="https://via.placeholder.com/120" alt="Threadadit Logo">
        <h1>Threadadit</h1>
        <p>The Internet Home Place, where many communities reside</p>
    </div>
    <form action="register-servlet" method="post" onsubmit="return validateForm()">
        <div class="login-form">
            <h2>Welcome Back!</h2>

            <div id="error-message" class="error-message"></div>

            <label for="username">Username</label>
            <input type="text" id="username" placeholder="your username c:" name="username" required>

            <label for="email">Email</label>
            <input type="text" id="email" placeholder="your email here! <3" name="email" required>

            <label for="password">Password</label>
            <input type="password" id="password" placeholder="your super secret password ¬¬" name="password" required>

            <label>Gender</label>
            <div id="gender">
                <label>
                    <input type="radio" name="gender" value="male" required> Male
                </label>
                <label>
                    <input type="radio" name="gender" value="female" required> Female
                </label>
            </div>

            <label>
                <input type="checkbox" onclick="togglePasswordVisibility()"> Show Password
            </label>

            <button type="submit">Register</button>

            <div class="forgot-signup">
                <a href="new_forgotpassword">Forgot Password</a>
                <a href="new_login">Back to login?</a>
            </div>
        </div>
    </form>
</div>

</body>
</html>