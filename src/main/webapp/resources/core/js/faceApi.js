/* eslint-disable no-undef */
async function cargarModelosFaceApi() {
  const MODEL_URL = "https://raw.githubusercontent.com/justadudewhohacks/face-api.js/master/weights/";
  await faceapi.nets.ssdMobilenetv1.loadFromUri(MODEL_URL);
  console.log("Modelos de face-api cargados desde servidor remoto.");
}
cargarModelosFaceApi();
document.addEventListener("change", async function(e) {
  if (e.target && e.target.classList.contains("input-foto-hijo")) {
    const input = e.target;
    const archivos = input.files;
    if (!archivos || archivos.length === 0) return;
    const archivo = archivos[0];
    const formulario = input.closest("form");
    const contenedorError = formulario.querySelector(".contenedor-error-foto");
    const textoError = formulario.querySelector(".texto-error-foto");
    const btnGuardar = formulario.querySelector("button[type=\"submit\"]");
    contenedorError.style.display = "none";
    btnGuardar.disabled = true;
    try {
      const img = await faceapi.bufferToImage(archivo);
      const detecciones = await faceapi.detectAllFaces(img);
      if (detecciones.length === 0) {
        textoError.innerText = "No se detecto ningun rostro. Asegurate de que se vea bien de frente y con buena iluminacion.";
        contenedorError.style.display = "flex";
        input.value = "";
        btnGuardar.disabled = true;
      } else if (detecciones.length > 1) {
        textoError.innerText = "Se detecto mas de una persona. La foto debe ser de tipo carnet (unicamente el alumno).";
        contenedorError.style.display = "flex";
        input.value = "";
        btnGuardar.disabled = true;
      } else {
        btnGuardar.disabled = false;
      }
    } catch (error) {
      console.error("Error al procesar la imagen con face-api:", error);
      btnGuardar.disabled = false;
    }
  }
});