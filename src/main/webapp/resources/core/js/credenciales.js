const checkTodos = document.getElementById('checkTodos');
const checksHijos = document.querySelectorAll('.check-hijo');

// Evento para "Todos"
checkTodos.addEventListener('change', function() {
    checksHijos.forEach(check => {
        check.checked = checkTodos.checked;
        toggleCredencial(check.getAttribute('data-id'), checkTodos.checked);
    });
});

// Evento para cada check individual
checksHijos.forEach(check => {
    check.addEventListener('change', function() {
        const id = this.getAttribute('data-id');
        toggleCredencial(id, this.checked);

        // Si desmarca uno, saca el check de "Todos"
        if (!this.checked) {
            checkTodos.checked = false;
        } else {
            // Si marca todos manualmente, activa el de "Todos"
            const todosMarcados = Array.from(checksHijos).every(c => c.checked);
            checkTodos.checked = todosMarcados;
        }
    });
});

function toggleCredencial(id, mostrar) {
    const card = document.getElementById('credencial-' + id);
    if (card) {
        card.style.display = mostrar ? 'flex' : 'none';
    }
}