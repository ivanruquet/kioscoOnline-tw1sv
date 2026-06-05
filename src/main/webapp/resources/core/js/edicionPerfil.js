/* eslint-disable no-unused-vars */
function activarEdicion() {
    document.getElementById("perfil-datos").classList.add("en-edicion");
}

function cancelarEdicion() {
    document.getElementById("perfil-datos")
        .classList.remove("en-edicion");

    const preview = document.getElementById("fotoPreview");

    preview.src = preview.dataset.original;

    document.getElementById("inputFoto").value = "";
}

function previsualizarFoto(url) {
    if (url && url.startsWith("http")) {
        document.getElementById("fotoPreview").src = url;
    }
}

document.getElementById("inputFoto")
    .addEventListener("change", function (event) {

        const archivo = event.target.files[0];

        if (archivo) {
            const lector = new FileReader();

            lector.onload = function (e) {
                document.getElementById("fotoPreview").src =
                    e.target.result;
            };

            lector.readAsDataURL(archivo);
        }
    });