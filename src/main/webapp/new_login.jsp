<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Threadadit | Login</title>
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

        .go-back-button {
            position: absolute; /* Add this line */
            bottom: 20px; /* Add this line */
            left: 20px; /* Add this line */
            padding: 10px 20px;
            background-color: #0079d3;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 14px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
        }

        .go-back-button:hover {
            background-color: #005bb5;
        }

    </style>
</head>
<body>

<div class="login-container">
    <div class="logo-section">
        <img src="https://via.placeholder.com/120" alt="Threadadit Logo">
        <h1>Threadadit</h1>
        <p>The Internet Home Place, where many communities reside</p>
    </div>

    <form action="login-servlet" method="post">
    <div class="login-form">
        <h2>Welcome Back!</h2>
        <label for="email">Email</label>
        <input type="text" id="email" name="email" placeholder="your email! <3" required>

        <label for="password">Password</label>
        <input type="password" id="password" name="password" placeholder="**********" required>

        <button type="submit">Log in</button>

        <div class="forgot-signup">
            <a href="new_forgotpassword">Forgot Password</a>
            <a href="new_register">Signup</a>
        </div>
    </div>
    </form>

    <a href="/" class="go-back-button">Go Back</a> <!-- Add this line -->
</div>

</body>
</html>
