// Carga de modelos de Inteligencia Artificial al iniciar la página
async function cargarModelosFaceApi() {
    // URL pública y abierta de GitHub que aloja los modelos sin trabas de CORS
    const MODEL_URL = 'https://raw.githubusercontent.com/justadudewhohacks/face-api.js/master/weights/';

    await faceapi.nets.ssdMobilenetv1.loadFromUri(MODEL_URL);
    console.log("Modelos de face-api cargados desde servidor remoto.");
}
cargarModelosFaceApi();

// Escuchamos de forma global a cualquier input de foto de hijo (creación o edición)
document.addEventListener('change', async function (e) {
    if (e.target && e.target.classList.contains('input-foto-hijo')) {
        const input = e.target;
        const archivos = input.files;
        if (!archivos || archivos.length === 0) return;

        const archivo = archivos[0];

        // Buscamos los elementos del formulario correspondiente de forma relativa
        const formulario = input.closest('form');
        const contenedorError = formulario.querySelector('.contenedor-error-foto');
        const textoError = formulario.querySelector('.texto-error-foto');
        const btnGuardar = formulario.querySelector('button[type="submit"]');

        // Ocultamos errores previos y bloqueamos botón temporalmente
        contenedorError.style.display = 'none';
        btnGuardar.disabled = true;
        try {
            // Procesamos la imagen en memoria
            const img = await faceapi.bufferToImage(archivo);
            const detecciones = await faceapi.detectAllFaces(img);

            if (detecciones.length === 0) {
                textoError.innerText = "No se detectó ningún rostro. Asegurate de que se vea bien de frente y con buena iluminación.";
                contenedorError.style.display = 'flex'; // 'flex' mantiene la alineación del icono con el texto
                input.value = ''; // Limpiamos el input para obligar a subir una correcta
                btnGuardar.disabled = true; // SE QUEDA BLOQUEADO por seguridad
            } else if (detecciones.length > 1) {
                textoError.innerText = "Se detectó más de una persona. La foto debe ser de tipo carnet (únicamente el alumno).";
                contenedorError.style.display = 'flex';
                input.value = '';
                btnGuardar.disabled = true; // SE QUEDA BLOQUEADO por seguridad
            } else {
                // Pasó la validación perfectamente: hay exactamente 1 rostro.
                btnGuardar.disabled = false;
            }
        }catch (error) {
            console.error("Error al procesar la imagen con face-api:", error);

            // ACÁ MANEJAMOS EL ERROR EN LA INTERFAZ:
            textoError.innerText = "No se pudo procesar la imagen. Intentá con otro formato o una foto menos pesada.";
            contenedorError.style.display = 'flex'; // Muestra el cartel .alerta-error nativo
            input.value = ''; // Limpiamos el archivo fallido
            btnGuardar.disabled = true; // Bloqueamos por seguridad para que suba otra
        }
    }
});