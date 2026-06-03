function cambiar(btn, delta) {//con esto controlo la experiencia del usuario, que se actualice el stock mientras esta comprando
    //si tengo 10 alfajores y al hijo 1 le puso 7, para el hijo 2 solo tendra 3 alfajores dispobinles
    const control = btn.parentElement;
    const fila = btn.closest('tr');
    const span = control.querySelector('.qty-num');
    const input = control.querySelector('.qty-input');
    const stock = parseInt(control.dataset.stock);

    const totalActual = Array.from(fila.querySelectorAll('.qty-num'))
        .reduce((sum, s) => sum + parseInt(s.textContent), 0);

    const valorActual = parseInt(span.textContent);
    const nuevoValor = valorActual + delta;

    if (nuevoValor < 0) return;
    if (delta > 0 && totalActual >= stock) return;

    span.textContent = nuevoValor;
    input.value = nuevoValor;
}

function eliminarFila(btn) {
    const fila = btn.closest('tr');
    const productoId = fila.dataset.productoId;
    fetch('/spring/carrito/eliminar', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: 'productoId=' + productoId
    }).then(() => fila.remove());
}

document.getElementById('formDistribucion').addEventListener('submit', function(e) {
    e.preventDefault();
    const filas = document.querySelectorAll('tbody tr');
    let hayError = false;

    filas.forEach(fila => {
        const nums = fila.querySelectorAll('.qty-num');
        const total = Array.from(nums).reduce((s, n) => s + parseInt(n.textContent), 0);
        if (total === 0) {
            hayError = true;
            fila.classList.add('fila-error');
        } else {
            fila.classList.remove('fila-error');
        }
    });

    if (hayError) {
        let alerta = document.querySelector('.alerta-error');
        if (!alerta) {
            alerta = document.createElement('div');
            alerta.className = 'alerta-error';
            document.querySelector('.tabla-wrap').before(alerta);
        }
        alerta.textContent = 'Hay productos sin asignar. Asigná al menos 1 unidad por producto o eliminalo.';
        alerta.scrollIntoView({behavior: 'smooth', block: 'center'});
    } else {
        this.submit();
    }
});