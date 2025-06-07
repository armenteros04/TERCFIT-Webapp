htmx.on('htmx:configRequest', (event) => {
    event.preventDefault();

    // Obtenemos el término de búsqueda
    const searchTerm = event.detail.parameters.search ? event.detail.parameters.search.toLowerCase() : '';

    // Filtramos los resultados
    const filteredExercises = ejercicios.filter(exercise => {
        return exercise.nombre.toLowerCase().includes(searchTerm) ||
            exercise.grupo_muscular.toLowerCase().includes(searchTerm) ||
            exercise.descripcion.toLowerCase().includes(searchTerm);
    });

    // Guardamos los videos que estaban reproduciéndose antes de actualizar
    const playingVideoIds = [];
    document.querySelectorAll('.exercise-video').forEach(video => {
        if (!video.paused) {
            const exerciseId = video.closest('.exercise-card').dataset.exerciseId;
            playingVideoIds.push(exerciseId);
        }
    });

    // Generamos el HTML de los resultados
    let resultsHtml = '';

    if (filteredExercises.length > 0) {
        resultsHtml = '<div class="exercise-grid">';
        filteredExercises.forEach(exercise => {
            resultsHtml += `
                <div class="exercise-card" data-exercise-id="${exercise.id}">
                    <div class="exercise-video-container">
                        <video class="exercise-video" id="video-${exercise.id}" preload="metadata" controls>
                            <source src="${exercise.video}" type="video/mp4">
                            Tu navegador no soporta videos HTML5.
                        </video>
                    </div>
                    <div class="exercise-info">
                        <div class="exercise-name">${exercise.nombre}</div>
                        <div class="muscle-group">${exercise.grupo_muscular}</div>
                        <p class="exercise-description">${exercise.descripcion}</p>
                    </div>
                </div>
            `;
        });
        resultsHtml += '</div>';
    } else {
        resultsHtml = '<div class="exercise-grid"><div class="no-results">No se encontraron ejercicios</div></div>';
    }

    // Actualizamos el contenido
    document.getElementById('results').innerHTML = resultsHtml;

    // Restauramos el estado de reproducción de los videos que estaban reproduciéndose
    playingVideoIds.forEach(id => {
        const video = document.querySelector(`.exercise-card[data-exercise-id="${id}"] .exercise-video`);
        if (video) {
            // Reproducimos el video sin sonido inicialmente para evitar problemas con los navegadores
            video.muted = true;
            video.play().then(() => {
                video.muted = false; // Restauramos el sonido después de que comience la reproducción
            }).catch(err => {
                console.log('Error al restaurar la reproducción:', err);
            });
        }
    });
});

// Función para pausar todos los demás videos cuando se reproduce uno
document.addEventListener('play', function(e) {
    const videos = document.querySelectorAll('.exercise-video');
    for (let i = 0; i < videos.length; i++) {
        if (videos[i] !== e.target) {
            videos[i].pause();
        }
    }
}, true);

// Optimización para evitar cargar todos los videos al mismo tiempo
document.addEventListener('DOMContentLoaded', () => {
    // Observador de intersección para cargar los videos solo cuando sean visibles
    const videoObserver = new IntersectionObserver((entries, observer) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                const container = entry.target;
                const video = container.querySelector('video');

                // Establecer el src del video solo cuando sea visible
                if (video && !video.hasAttribute('src-loaded')) {
                    const source = video.querySelector('source');
                    if (source) {
                        video.setAttribute('src-loaded', 'true');
                        // Ya no necesitamos observar este contenedor
                        observer.unobserve(container);
                    }
                }
            }
        });
    }, {
        root: null,
        rootMargin: '0px',
        threshold: 0.1
    });

    // Observar todos los contenedores de video
    document.addEventListener('htmx:afterOnLoad', () => {
        document.querySelectorAll('.exercise-video-container').forEach(container => {
            videoObserver.observe(container);
        });
    });

    // Función para renderizar los ejercicios al cargar la página
    function renderExercises(exercises) {
        let resultsHtml = '<div class="exercise-grid">';
        exercises.forEach(exercise => {
            resultsHtml += `
            <div class="exercise-card" data-exercise-id="${exercise.id}">
                <div class="exercise-video-container">
                    <video class="exercise-video" id="video-${exercise.id}" preload="metadata" controls>
                        <source src="${exercise.video}" type="video/mp4">
                        Tu navegador no soporta videos HTML5.
                    </video>
                </div>
                <div class="exercise-info">
                    <div class="exercise-name">${exercise.nombre}</div>
                    <div class="muscle-group">${exercise.grupo_muscular}</div>
                    <p class="exercise-description">${exercise.descripcion}</p>
                </div>
            </div>
        `;
        });
        resultsHtml += '</div>';
        document.getElementById('results').innerHTML = resultsHtml;
    }

// Llamar a la función al cargar la página
    document.addEventListener('DOMContentLoaded', () => {
        renderExercises(ejercicios); // `ejercicios` viene de data.js
    });
    // También observar los contenedores iniciales
    document.querySelectorAll('.exercise-video-container').forEach(container => {
        videoObserver.observe(container);
    });
});
