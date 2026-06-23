const checkTodos = document.getElementById("checkTodos");
const checksHijos = document.querySelectorAll(".check-hijo");
checkTodos.addEventListener("change", function() {
  checksHijos.forEach(check => {
    check.checked = checkTodos.checked;
    toggleCredencial(check.getAttribute("data-id"), checkTodos.checked);
  });
});
checksHijos.forEach(check => {
  check.addEventListener("change", function() {
    const id = this.getAttribute("data-id");
    toggleCredencial(id, this.checked);
    if (!this.checked) {
      checkTodos.checked = false;
    } else {
      const todosMarcados = Array.from(checksHijos).every(c => c.checked);
      checkTodos.checked = todosMarcados;
    }
  });
});
function toggleCredencial(id, mostrar) {
  const card = document.getElementById("credencial-" + id);
  if (card) {
    card.style.display = mostrar ? "flex" : "none";
  }
}