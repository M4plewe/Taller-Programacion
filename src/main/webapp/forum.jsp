<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Foro</title>
    <script>
        async function loadForumData() {
            const urlParams = new URLSearchParams(window.location.search);
            const forumId = urlParams.get('id');

            try {
                const response = await fetch('/TestFinalOMeMato/forum-data?id=' + forumId);
                if (!response.ok) {
                    throw new Error('Error al cargar la información del foro: ' + response.status);
                }

                const data = await response.json();

                // Actualizar el nombre y descripción del foro
                document.getElementById('forumName').textContent = data.name;
                document.getElementById('forumDescription').textContent = data.description;

                // Limpiar la lista de posts antes de añadir nuevos
                const postList = document.getElementById('postList');
                postList.innerHTML = '';  // Aseguramos que no se acumulen posts viejos

                // Agregar cada post al listado
                if (data.posts.length > 0) {
                    data.posts.forEach(function (aPost) {
                        const listItem = document.createElement('li');
                        listItem.innerHTML =
                            '<h3>' + aPost.title + '</h3>' +
                            '<p>' + aPost.content + '</p>' +
                            '<p>Publicado el: ' + aPost.createdAt + '</p>';
                        postList.appendChild(listItem);
                    });
                } else {
                    postList.innerHTML = '<li>No hay posts en este foro.</li>';
                }
            } catch (error) {
                console.error(error);
                document.getElementById('postList').innerHTML = '<li>Error al cargar los posts.</li>';
            }
        }

        // Cargar datos al inicio
        document.addEventListener('DOMContentLoaded', loadForumData);
    </script>
</head>
<body>
<h1 id="forumName"></h1>
<p id="forumDescription"></p>

<!-- Botón para crear un nuevo post -->
<button onclick="window.location.href='/TestFinalOMeMato/submit-post.jsp?forumId=' + new URLSearchParams(window.location.search).get('id')">
    Crear Post
</button>

<h2>Posts:</h2>
<ul id="postList"></ul>
</body>
</html>
