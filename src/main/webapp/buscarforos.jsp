<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Buscar Forums</title>

    <script>
        async function buscar() {
            const searchTerm = document.getElementById('searchInput').value;
            const url = `/TestFinalOMeMato/forums?search=` + encodeURIComponent(searchTerm);

            try {
                const response = await fetch(url);
                if (!response.ok) {
                    throw new Error('Error al cargar los forums: ' + response.status);
                }
                const forums = await response.json();
                const resultList = document.getElementById('resultList');
                resultList.innerHTML = ''; // Limpiar resultados previos

                if (forums.length === 0) {
                    resultList.innerHTML = '<li>No se encontraron forums.</li>';
                } else {
                    forums.forEach(forum => {
                        const listItem = document.createElement('li');
                        // Crear un enlace dinámico a forum.jsp con la ID del foro
                        const link = document.createElement('a');
                        link.href = `/TestFinalOMeMato/forum.jsp?id=`+forum.id; // Enlace al foro específico
                        link.textContent = forum.name; // Nombre del foro
                        listItem.appendChild(link);
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
<h1>Buscar Forums</h1>

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
