function cambiar(btn, delta) {
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