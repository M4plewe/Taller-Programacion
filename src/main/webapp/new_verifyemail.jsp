<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Threadadit | Verify Email</title>
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

        .login-form p {
            font-size: 16px;
            color: #333;
        }

        .login-form .redirect-message {
            font-size: 14px;
            color: #333;
            margin-top: 10px;
        }

        .login-form .redirect-message span {
            font-weight: bold;
            color: #ff4500;
        }
    </style>
    <script>
        // Función para obtener el parámetro 'token' de la URL
        function getParameterByName(name) {
            const url = new URL(window.location.href);
            return url.searchParams.get(name);
        }

        // Función para hacer la solicitud de verificación del email
        function verifyEmail() {
            const token = getParameterByName('token');  // Obtener el token de la URL

            if (!token) {
                // Si no hay token, mostrar el mensaje de que se ha enviado un correo
                document.getElementById("message").innerText = "Te enviamos un correo de verificación.";
            } else {
                // Hacer una solicitud GET al servlet usando fetch
                fetch(`/verifyemailservice?token=` + token)
                    .then(response => response.text())  // Obtener la respuesta del servidor
                    .then(data => {
                        // Mostrar el mensaje devuelto por el servlet en la página
                        if (data === "Invalid token") {
                            document.getElementById("message").innerText = "Token incorrecta, inicia sesión de nuevo para que te llegue un nuevo correo de verificación.";
                        } else {
                            document.getElementById("message").innerText = data;

                            // Si la verificación fue exitosa, redirigir después de 5 segundos
                            if (data.includes("Email verified successfully")) {
                                setTimeout(function () {
                                    window.location.href = "/new_login";  // Redirigir a la nueva página de inicio de sesión
                                }, 5000);  // Redirigir después de 5 segundos
                            }
                        }
                    })
                    .catch(error => {
                        console.error("Error during verification:", error);
                        document.getElementById("message").innerText = "An error occurred during email verification.";
                    });
            }
        }

        // Ejecutar la función cuando la página se haya cargado
        window.onload = function() {
            verifyEmail();
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

    <div class="login-form">
        <h2>Email Verification</h2>
        <p id="message">Verifying your email, please wait...</p>
        <p id="redirect-message" class="redirect-message"></p>
    </div>
</div>

</body>
</html>
