<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Buscar Posts</title>

    <script>
        async function buscar() {
            // Obtener el término de búsqueda
            const searchTerm = document.getElementById('searchInput').value;

            // Definir la URL de búsqueda (se usará el servlet de posts)
            const url = `/TestFinalOMeMato/posts?search=` + encodeURIComponent(searchTerm);

            // Realizar la solicitud
            try {
                const response = await fetch(url);
                if (!response.ok) {
                    throw new Error('Error al cargar los posts: ' + response.status);
                }
                const posts = await response.json();

                // Mostrar los resultados
                const resultList = document.getElementById('resultList');
                resultList.innerHTML = ''; // Limpiar resultados previos

                if (posts.length === 0) {
                    resultList.innerHTML = '<li>No se encontraron posts.</li>';
                } else {
                    posts.forEach(aPost => {
                        const listItem = document.createElement('li');
                        listItem.textContent = aPost.id + ', ' + aPost.title + ', ' + aPost.content;
                        resultList.appendChild(listItem);
                    });
                }
            } catch (error) {
                console.error(error);
                document.getElementById('resultList').innerHTML = '<li>Error al realizar la búsqueda.</li>';
            }
        }
    </script>
</head>
<body>
<h1>Buscar Posts</h1>

<!-- Enlaces para redirigir a otras páginas -->
<div>
    <a href="buscarposts.jsp" class="link">Buscar Posts</a>
    <a href="buscarforos.jsp" class="link">Buscar Foros</a>
</div>

<!-- Campo de búsqueda -->
<input type="text" id="searchInput" placeholder="Ingrese un término de búsqueda">
<button onclick="buscar()">Buscar</button>

<!-- Resultados -->
<ul id="resultList"></ul>
</body>
</html>
