function activarEdicion() {
    document.getElementById('perfil-datos').classList.add('en-edicion');
}

function cancelarEdicion() {
    document.getElementById('perfil-datos').classList.remove('en-edicion');
    document.getElementById('fotoPreview').src = document.getElementById('avatarImg').src;
    document.getElementById('inputFoto').value = document.getElementById('inputFoto').defaultValue;
}

function previsualizarFoto(url) {
    if (url && url.startsWith('http')) {
        document.getElementById('fotoPreview').src = url;
    }
}
